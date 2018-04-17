package com.tagtheagency.portal.pitch.service;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.tagtheagency.portal.model.Pitch;
import com.tagtheagency.portal.model.PitchPage;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public String store(MultipartFile file, int pitch, int page) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        filename = filename.replaceAll("[^a-zA-Z0-9-_\\.]", "_");
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            Path dir = this.rootLocation.resolve(Integer.toString(pitch)+File.separator+Integer.toString(page));
            Files.createDirectories(dir);
            Files.copy(file.getInputStream(), dir.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
            
            return filename;
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path));
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(int pitch, int page,String filename) {
    	Path dir = this.rootLocation.resolve(Integer.toString(pitch)+File.separator+Integer.toString(page));
        return dir.resolve(filename);
    }

    @Override
    public Resource loadAsResource(int pitch, int page, String filename) {
        try {
        	filename = filename.replaceAll("[^a-zA-Z0-9-_\\.]", "_");
            Path file = load(pitch, page, filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }
    
    @Override 
    public Dimension getImageDimensions(int pitch, int page, String filename) {
        try {
        	filename = filename.replaceAll("[^a-zA-Z0-9-_\\.]", "_");
            Path file = load(pitch, page, filename);
            if (file.toFile().exists() && file.toFile().canRead()) {
            	BufferedImage image = ImageIO.read(file.toFile());
            	return new Dimension(image.getWidth(), image.getHeight());
            } else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);

            }
        }
        catch (IOException e) {
        	e.printStackTrace();
            throw new StorageFileNotFoundException("Could not read file as image: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
    
    @Override
    public List<String> getUploadedFiles(int pitch, int page) {
    	Path dir = this.rootLocation.resolve(Integer.toString(pitch)+File.separator+Integer.toString(page));
    	if (dir.toFile().exists())
    		return Arrays.asList(dir.toFile().list());
    	else 
    		return Collections.emptyList();
    }
    
    @Override
    public void deleteUploadedFile(PitchPage actualPage, String filename) {
    	
    	filename = filename.replaceAll("[^a-zA-Z0-9-_\\.]", "_");
        Path file = load(actualPage.getPitch().getId(), actualPage.getId(), filename);
    	
        try {
			Files.delete(file);
		} catch (IOException e) {
			throw new StorageException("Could not delete file", e);
		}
   	
    }
}