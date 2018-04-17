package com.tagtheagency.portal;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.security.Principal;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lowagie.text.DocumentException;
import com.tagtheagency.portal.model.Pitch;
import com.tagtheagency.portal.model.PitchImage;
import com.tagtheagency.portal.model.PitchPage;
import com.tagtheagency.portal.pitch.dto.PitchDTO;
import com.tagtheagency.portal.pitch.dto.PitchImageDTO;
import com.tagtheagency.portal.pitch.dto.PitchPageDTO;
import com.tagtheagency.portal.pitch.dto.PitchPageType;
import com.tagtheagency.portal.pitch.service.PitchCreator;
import com.tagtheagency.portal.pitch.service.PitchManager;
import com.tagtheagency.portal.pitch.service.StorageService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/pitch/")
public class PitchController {
	
	@Autowired private PitchManager pitchManager;
	@Autowired private StorageService storageService;
	@Autowired private ModelMapper modelMapper;
	@Autowired private PitchCreator pitchCreator;
	
	@GetMapping("/")
	public List<PitchDTO> getPitches() {
		System.out.println("Matching to slash, returning all");
		List<Pitch> pitches = pitchManager.getAllPitches();
		return pitches.stream().map(this::convertToDto).collect(Collectors.toList());
	}
	
	
	@PostMapping("create")
	public ResponseEntity<?> createPitch(ModelMap model, Principal principal) {
		Pitch newPitch = pitchManager.createPitch("Untitled", principal.getName());
		return ResponseEntity.ok(deepConvertToDto(newPitch));
	}

	
	@GetMapping("/{id}")
	public ResponseEntity<?> getPitchJSON(ModelMap model, @PathVariable int id) {
		Pitch pitch = pitchManager.getById(id);
		if (pitch == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(deepConvertToDto(pitch));
	}
	
	@PostMapping("{id}") 
	@ResponseBody
	public PitchDTO updatePitch(@PathVariable int id, @RequestBody Map<String, String> params, Principal principal) {
		Pitch pitch = pitchManager.getById(id);
		pitch.setTitle(params.get("title"));
		pitch.setModifiedDate(new Date());
		pitch.setModifiedUser(principal.getName());
		pitchManager.updatePitch(pitch);
		return convertToDto(pitch);
	}
	
	@GetMapping("/pageTypes")
	public ResponseEntity<?> getPageTypes() {
		//List<PitchPageType> types = Arrays.asList(PitchPageType.values());
		return ResponseEntity.ok(PitchPageType.values());
	}
	
	@PostMapping("{pitch}/create")
	public ResponseEntity<?> createPitchPage(@PathVariable int pitch) {
		Pitch actualPitch = pitchManager.getById(pitch);
		if (actualPitch == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(convertToDto(pitchManager.createPage(actualPitch)));
	}
	
	@PostMapping("{pitch}/{page}/title")
	@ResponseBody
	public PitchPageDTO updatePageTitle(@PathVariable int pitch, @PathVariable int page, @RequestBody PitchPageDTO dto) {
		PitchPage domainPage = pitchManager.getPageById(page);
		if (domainPage.getPitch().getId() != pitch) {
			return null;
		}
		domainPage.setTitle(dto.getTitle());
		pitchManager.updatePitchPage(domainPage);
		return convertToDto(domainPage);
	}
	
	@PostMapping("{pitch}/{page}")
	@ResponseBody
	public PitchPageDTO updatePage(@PathVariable int pitch, @PathVariable int page, @RequestBody PitchPageDTO dto) {
		PitchPage domainPage = pitchManager.getPageById(page);
		if (domainPage.getPitch().getId() != pitch) {
			return null;
		}
		domainPage.setTitle(dto.getTitle());
		domainPage.setOrder(dto.getOrder());
		domainPage.setText(dto.getText());
		domainPage.setImplementation(dto.getImplementation());
		pitchManager.updatePitchPage(domainPage);
		return convertToDto(domainPage);
	}
	
	
	
	
	
	@PostMapping("{pitch}/{page}/text")
	@ResponseBody
	public PitchPageDTO updatePageText(@PathVariable int pitch, @PathVariable int page, @RequestBody PitchPageDTO dto) {
		PitchPage domainPage = pitchManager.getPageById(page);
		if (domainPage.getPitch().getId() != pitch) {
			return null;
		}
		domainPage.setText(dto.getText());
		pitchManager.updatePitchPage(domainPage);

		return convertToDto(domainPage);
	}
	
	@PostMapping("{pitch}/{page}/implementation")
	@ResponseBody
	public PitchPageDTO updatePageType(@PathVariable int pitch, @PathVariable int page, @RequestBody PitchPageDTO dto) {
		PitchPage domainPage = pitchManager.getPageById(page);
		if (domainPage.getPitch().getId() != pitch) {
			return null;
		}
		
		domainPage.setImplementation(dto.getImplementation());
		pitchManager.updatePitchPage(domainPage);

		return convertToDto(domainPage);
	}
	
	@GetMapping("{id}/{title}.pdf") 
	@ResponseBody
	public void generate(@PathVariable int id, @PathVariable String title, HttpServletResponse response) {
		Pitch pitch = pitchManager.getById(id);
		
		response.setContentType("application/pdf");
		response.addHeader("Content-Disposition", "inline; filename="+title+".pdf");
		
		try {
			pitchCreator.createPitchPDF(response.getOutputStream(), pitch);
			response.setStatus(200);
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
	}
	
	private PitchPageDTO convertToDto(PitchPage page) {
		PitchPageDTO dto = modelMapper.map(page, PitchPageDTO.class);
		return dto;
	}
	
	private PitchDTO deepConvertToDto(Pitch pitch) {
		PitchDTO pitchDto = modelMapper.map(pitch, PitchDTO.class);
		return pitchDto;
	}
	private PitchDTO convertToDto(Pitch pitch) {
		
		PitchDTO dto = new PitchDTO();
		dto.setId(pitch.getId());
		dto.setCreatedDate(pitch.getCreatedDate());
		dto.setTitle(pitch.getTitle());
		dto.setCreatedUser(pitch.getCreatedUser());
		dto.setModifiedUser(pitch.getModifiedUser());
		return dto;
	}
	
	@GetMapping("{pitch}/{page}/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<InputStreamResource> getFile(@PathVariable int pitch, @PathVariable int page, @PathVariable String filename) throws IOException { 
		System.out.println("Matched get file");
		Resource resource = storageService.loadAsResource(pitch, page, filename);
		
		String mime = URLConnection.guessContentTypeFromName(filename);
		
		return ResponseEntity.ok()
	            .contentLength(resource.contentLength())
	            .contentType(MediaType.parseMediaType(mime))
	            .body(new InputStreamResource(resource.getInputStream()));
		
	}
	
	@GetMapping(value = "{pitch}/{page}/files64/{filename:.+}")
	public ResponseEntity<?> getFileBase64(@PathVariable int pitch, @PathVariable int page, @PathVariable String filename) throws IOException { 
		System.out.println("Matched get file");
		Resource resource = storageService.loadAsResource(pitch, page, filename);
		
		BufferedImage img = ImageIO.read(resource.getInputStream());
		int width, height;
		if (img.getWidth() > img.getHeight() * 2) {
			width = 200;
			height = -1;
		} else {
			width = -1;
			height = 100;
		}
		
		Image thumbnail = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		BufferedImage bufferedThumbnail = new BufferedImage(thumbnail.getWidth(null),
		                                                    thumbnail.getHeight(null),
		                                                    BufferedImage.TYPE_INT_RGB);
		bufferedThumbnail.getGraphics().drawImage(thumbnail, 0, 0, null);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bufferedThumbnail, "jpeg", baos);
				
		String data = "data:image/jpeg;base64,";
		byte[] imageBytes = baos.toByteArray();
		data += Base64.getEncoder().encodeToString(imageBytes);
		System.out.println(data);
		
		return ResponseEntity.ok(data);
		
	}
	
	
	
	@PostMapping("{pitchId}/{page}/uploadFile")
	public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file, @PathVariable int pitchId, @PathVariable int page,
			RedirectAttributes redirectAttributes) throws IOException {

		String filename = storageService.store(file, pitchId, page);
		
		Dimension d = storageService.getImageDimensions(pitchId, page, filename);
		
		PitchPage actualPage = pitchManager.getPageById(page);
		PitchImageDTO dto = convertToDto(pitchManager.createImage(actualPage, 400, 50, d.getWidth(), d.getHeight(), filename));

		return ResponseEntity.ok(dto);
		
	}
	
	@PutMapping("{pitch}/{page}/image")
	@ResponseBody
	public void updateImage(@PathVariable int pitch, @PathVariable int page, @RequestBody PitchImageDTO image) {
		pitchManager.updateImage(convertToEntity(pitchManager.getPageById(page), image));
	}

	@DeleteMapping("{pitch}/{page}/image/{image}")
	@ResponseBody
	public void deleteImageFromPage(@PathVariable int pitch, @PathVariable int page, @PathVariable int image) throws Exception {
		PitchPage actualPage = pitchManager.getPageById(page);
		
		Optional<PitchImage> actualImage = actualPage.getImages().stream().filter(i -> i.getId() == image).findFirst();
		String filename = actualImage.orElseThrow(Exception::new).getFilename();

		pitchManager.deleteImageFromPage(actualPage, image);
		storageService.deleteUploadedFile(actualPage, filename);
	}

//	@DeleteMapping("{pitch}/{page}/image/unlinked/{filename:.+}")
//	@ResponseBody
//	public void deleteImageFromPage(@PathVariable int pitch, @PathVariable int page, @PathVariable String filename) {
//		PitchPage actualPage = pitchManager.getPageById(page);
//
//		if (actualPage.getImages().stream().anyMatch(image -> image.getFilename().equals(filename))) {
//			System.out.println("Filename in use!");
//			return;
//		}
//		storageService.deleteUploadedFile(actualPage, filename);
//	}

	private PitchImage convertToEntity(PitchPage page, PitchImageDTO imageDto) {
		PitchImage image = modelMapper.map(imageDto, PitchImage.class);
		image.setPage(page);
		return image;
	}
	
	private PitchImageDTO convertToDto(PitchImage image) {
	    PitchImageDTO imageDto = modelMapper.map(image, PitchImageDTO.class);
	    return imageDto;
	}

}
//
//import java.io.IOException;
//import java.net.URLConnection;
//import java.security.Principal;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//
//import javax.servlet.annotation.MultipartConfig;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.io.IOUtils;
//import org.modelmapper.ModelMapper;
//import org.modelmapper.PropertyMap;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.InputStreamResource;
//import org.springframework.core.io.Resource;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import com.lowagie.text.DocumentException;
//import com.tagtheagency.portal.model.Pitch;
//import com.tagtheagency.portal.model.PitchImage;
//import com.tagtheagency.portal.model.PitchPage;
//import com.tagtheagency.portal.pitch.dto.PitchDTO;
//import com.tagtheagency.portal.pitch.dto.PitchImageDTO;
//import com.tagtheagency.portal.pitch.dto.PitchPageDTO;
//import com.tagtheagency.portal.pitch.dto.PitchPageType;
//import com.tagtheagency.portal.pitch.pages.TextAndImagePage;
//import com.tagtheagency.portal.pitch.service.PitchCreator;
//import com.tagtheagency.portal.pitch.service.PitchManager;
//import com.tagtheagency.portal.pitch.service.StorageService;
//
//@Controller
//@RequestMapping("/pitch/")
//public class PitchController {
//	
//	@Autowired private PitchManager pitchManager;
//	@Autowired private StorageService storageService;
//	@Autowired private ModelMapper modelMapper;
//	@Autowired private PitchCreator pitchCreator;
//		
//	@RequestMapping("")
//	public String getMainPage(ModelMap model) {
//		List<Pitch> pitches = pitchManager.getAllPitches();
//		model.put("pitches", pitches);
//		return "pitchDashboard";
//	}
//	
//	@GetMapping("{id}")
//	public String getPitch(ModelMap model, @PathVariable int id) {
//		Pitch pitch = pitchManager.getById(id);
//		if (pitch == null) {
//			return "redirect:/pitch/";
//		}
//		model.put("pitch", convertToDto(pitch));
//		model.put("pageTypes", PitchPageType.values());
//		model.put("scripts", Arrays.asList("dropzone.js", "pitch.js", "pdf.min.js", "interact.js"));//Collections.singletonList("pitch.js"));
//		return "pitch";
//	}
//	
//	@GetMapping("{id}/raw")
//	@ResponseBody
//	public PitchDTO getPitchJSON(ModelMap model, @PathVariable int id) {
//		Pitch pitch = pitchManager.getById(id);
//		if (pitch == null) {
//			return null;
//		}
//		return convertToDto(pitch);
//	}
//	
//	@GetMapping("{id}/newPage")
//	@ResponseBody
//	public PitchPageDTO createPage(ModelMap model, @PathVariable int id) {
//		Pitch pitch = pitchManager.getById(id);
//		if (pitch == null) {
//			return null;
//		}
//		return convertToDto(pitchManager.createPage(pitch));
//
//	}
//	
//	@GetMapping("{id}/{title}.pdf") 
//	@ResponseBody
//	public void generate(@PathVariable int id, @PathVariable String title, HttpServletResponse response) {
//		Pitch pitch = pitchManager.getById(id);
//		
//		
////		creator.setName(pitch.getTitle());
////		
////		pitch.getPages().forEach(builder::addPage);
////		
//		response.setContentType("application/pdf");
//		response.addHeader("Content-Disposition", "inline; filename="+title+".pdf");
//		
//		try {
//			pitchCreator.createPitchPDF(response.getOutputStream(), pitch);
////			builder.createPitch(response.getOutputStream());
//			response.setStatus(200);
//		} catch (DocumentException | IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//
//	@PostMapping("{id}") 
//	@ResponseBody
//	public PitchDTO updatePitch(@PathVariable int id, @RequestBody PitchDTO pitchDto) {
//		Pitch pitch = convertToEntity(pitchDto);
//		pitchManager.updatePitch(pitch);
//		return convertToDto(pitch);
//	}
//	
//	@PostMapping("{pitch}/{page}/title")
//	@ResponseBody
//	public PitchPageDTO updatePageTitle(@PathVariable int pitch, @PathVariable int page, @RequestBody PitchPageDTO dto) {
//		PitchPage domainPage = pitchManager.getPageById(page);
//		if (domainPage.getPitch().getId() != pitch) {
//			return null;
//		}
//		domainPage.setTitle(dto.getTitle());
//		pitchManager.updatePitchPage(domainPage);
//		return convertToDto(domainPage);
//	}
//	
//	@PostMapping("{pitch}/{page}/text")
//	@ResponseBody
//	public PitchPageDTO updatePageText(@PathVariable int pitch, @PathVariable int page, @RequestBody PitchPageDTO dto) {
//		PitchPage domainPage = pitchManager.getPageById(page);
//		if (domainPage.getPitch().getId() != pitch) {
//			return null;
//		}
//		domainPage.setText(dto.getText());
//		pitchManager.updatePitchPage(domainPage);
//
//		return convertToDto(domainPage);
//	}
//	
//	@PostMapping("{pitch}/{page}/type")
//	@ResponseBody
//	public PitchPageDTO updatePageType(@PathVariable int pitch, @PathVariable int page, @RequestBody PitchPageDTO dto) {
//		PitchPage domainPage = pitchManager.getPageById(page);
//		if (domainPage.getPitch().getId() != pitch) {
//			return null;
//		}
//		
//		domainPage.setImplementation(dto.getImplementation());
//		pitchManager.updatePitchPage(domainPage);
//
//		return convertToDto(domainPage);
//	}
//
//	@GetMapping("{pitch}/{page}/files")
//	@ResponseBody
//	public List<String> getAllFiles(@PathVariable int pitch, @PathVariable int page) { 
//		return storageService.getUploadedFiles(pitch, page);
//	}
//	
//	@GetMapping("{pitch}/{page}/files/{filename:.+}")
//	@ResponseBody
//	public ResponseEntity<InputStreamResource> getFile(@PathVariable int pitch, @PathVariable int page, @PathVariable String filename) throws IOException { 
//		Resource resource = storageService.loadAsResource(pitch, page, filename);
//		
//		String mime = URLConnection.guessContentTypeFromName(filename);
//		
//		return ResponseEntity.ok()
//	            .contentLength(resource.contentLength())
//	            .contentType(MediaType.parseMediaType(mime))
//	            .body(new InputStreamResource(resource.getInputStream()));
//		
//	}
//		
//	@RequestMapping("create")
//	public String createPitch(ModelMap model, Principal principal) {
//		Pitch newPitch = pitchManager.createPitch("", principal.getName());
//		return "redirect:/pitch/"+newPitch.getId();
//	}
//	
//    @PostMapping("{pitchId}/{page}/uploadFile")
//    public @ResponseBody byte[] handleFileUpload(@RequestParam("file") MultipartFile file, @PathVariable int pitchId, @PathVariable int page,
//            RedirectAttributes redirectAttributes) throws IOException {
//    	
//        storageService.store(file, pitchId, page);
//        redirectAttributes.addFlashAttribute("message",
//                "You successfully uploaded " + file.getOriginalFilename() + "!");
//
//        Resource resource = storageService.loadAsResource(pitchId, page, file.getOriginalFilename());
//        return IOUtils.toByteArray(resource.getInputStream());
//    }
//    
//    @PostMapping("{pitch}/{page}/image")
//    @ResponseBody
//    public PitchImageDTO createImage(@PathVariable int pitch, @PathVariable int page, @RequestBody PitchImageDTO image) {
//    	PitchPage actualPage = pitchManager.getPageById(page);
//    	return convertToDto(pitchManager.createImage(actualPage, image.getX(), image.getY(), image.getW(), image.getH(), image.getFilename()));
//    }
//	
//    @PutMapping("{pitch}/{page}/image")
//    @ResponseBody
//    public void updateImage(@PathVariable int pitch, @PathVariable int page, @RequestBody PitchImageDTO image) {
//    	pitchManager.updateImage(convertToEntity(pitchManager.getPageById(page), image));
//    }
//	
//    @DeleteMapping("{pitch}/{page}/image/{image}")
//    @ResponseBody
//    public void deleteImageFromPage(@PathVariable int pitch, @PathVariable int page, @PathVariable int image) {
//    	PitchPage actualPage = pitchManager.getPageById(page);
//    	pitchManager.deleteImageFromPage(actualPage, image);
//    }
//	
//    @DeleteMapping("{pitch}/{page}/image/unlinked/{filename:.+}")
//    @ResponseBody
//    public void deleteImageFromPage(@PathVariable int pitch, @PathVariable int page, @PathVariable String filename) {
//    	PitchPage actualPage = pitchManager.getPageById(page);
//    	
//    	if (actualPage.getImages().stream().anyMatch(image -> image.getFilename().equals(filename))) {
//    		System.out.println("Filename in use!");
//    		return;
//    	}
//    	
//    	storageService.deleteUploadedFile(actualPage, filename);
//    }
//	
//	
//	private PitchDTO convertToDto(Pitch pitch) {
//	    PitchDTO pitchDto = modelMapper.map(pitch, PitchDTO.class);
//	    return pitchDto;
//	}
//
//	
//	private PitchImageDTO convertToDto(PitchImage image) {
//	    PitchImageDTO imageDto = modelMapper.map(image, PitchImageDTO.class);
//	    return imageDto;
//	}
//
//	private PitchImage convertToEntity(PitchPage page, PitchImageDTO imageDto) {
//		System.out.println("Converting to entity: ");
//		System.out.println(page.getTitle());
//		System.out.println(imageDto.getFilename());
//		
////		PitchImage image = new PitchImage();
////		image.setId(imageDto.getId());
////		image.setFilename(imageDto.getFilename());
////		image.setX(imageDto.getX());
////		image.setY(imageDto.getY());
////		image.setW(imageDto.getW());
////		image.setH(imageDto.getH());
//		
//	    PitchImage image = modelMapper.map(imageDto, PitchImage.class);
//	    image.setPage(page);
//	    return image;
//	}
//	
//	private Pitch convertToEntity(PitchDTO pitchDto) {
//	    Pitch pitch = modelMapper.map(pitchDto, Pitch.class);
//	    pitch.setModifiedDate(new Date());
//
//	    if (pitchDto.getId() > 0) {
//	    	Pitch oldPitch = pitchManager.getById(pitchDto.getId());
//	    	pitch.setCreatedDate(oldPitch.getCreatedDate());
//	    	pitch.setCreatedUser(oldPitch.getCreatedUser());
//	    }
//	    return pitch;
//	}
//	
//	
//	private PitchPageDTO convertToDto(PitchPage page) {
//		PitchPageDTO dto = modelMapper.map(page, PitchPageDTO.class);
////		dto.setClazz(page.getClass().toString());
//	    return dto;
//	}
//}
