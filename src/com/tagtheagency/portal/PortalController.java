//package com.tagtheagency.portal;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
//
//import com.tagtheagency.portal.pitch.service.PitchManager;
//import com.tagtheagency.portal.pitch.service.StorageService;
//
//@Controller
//@RequestMapping("/*")
//public class PortalController {
//	
//	@Autowired private PitchManager pitchManager;
//	@Autowired private StorageService storageService;
//	
//	@Autowired
//	private RequestMappingHandlerMapping requestMappingHandlerMapping;
//
//	@RequestMapping( value = "endpoints", method = RequestMethod.GET )
//	public String getEndPointsInView( Model model )	{
//	    model.addAttribute( "endPoints", requestMappingHandlerMapping.getHandlerMethods().keySet() );
//	    return "endpoints";
//	}
//	
//	@RequestMapping(value = "/errors", method = RequestMethod.GET)
//    public String renderErrorPage(HttpServletRequest httpRequest, ModelMap model) {
//        
//        String errorMsg = "";
//        int httpErrorCode = getErrorCode(httpRequest);
//        System.out.println("error code: "+httpErrorCode);
// 
//        switch (httpErrorCode) {
//            case 400: {
//                errorMsg = "Http Error Code: 400. Bad Request";
//                break;
//            }
//            case 401: {
//                errorMsg = "Http Error Code: 401. Unauthorized";
//                break;
//            }
//            case 404: {
//                errorMsg = "Http Error Code: 404. Resource not found";
//                break;
//            }
//            case 500: {
//                errorMsg = "Http Error Code: 500. Internal Server Error";
//                System.out.println("Got a 500, looking it up");
//                Throwable t = (Throwable) httpRequest.getAttribute("javax.servlet.error.exception");
//                if (t != null) {
//                	t.printStackTrace();
//                }
//                break;
//            }
//        }
//        model.put("errorMsg", errorMsg);
//        return "errors";
//    }
//     
//    private int getErrorCode(HttpServletRequest httpRequest) {
//    	if (httpRequest.getAttribute("javax.servlet.error.status_code") == null) {
//    		return -1;
//    	}
//        return (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");
//    }
//	
//    @RequestMapping("/pitch")
//    public String pitch() {
//    	return "redirect:pitch/";
//    }
//    
//	@RequestMapping("/hello")
//	@ResponseBody
//	public TestMessage showMessage(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model) {
//		return new TestMessage(name);
//	}
//	
//	@RequestMapping("")
//	public String getMainPage() {
//		System.out.println("Matched to slash, getting login");
//		return "login";
//	}
//	
//	@RequestMapping("/dashboard")
//	public String getDashboard() {
//		return "dashboard";
//	}
//	
//	@RequestMapping("/login")
//	public String login() {
//		System.out.println("Matched to login, getting login");
//		return "login";
//	}
//
//	
//	class TestMessage {
//		private String name;
//		
//		public TestMessage(String name) {
//			this.name = name;
//		}
//		public String getName() {
//			return name;
//		}
//		public void setName(String name) {
//			this.name = name;
//		}
//	}
//}
