package cn.hf.manage.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api")
public class TestController {

	
	@RequestMapping(value="/testController", method=RequestMethod.GET)
	public ResponseEntity<Void> test(@RequestParam("serviceId")String serviceId,
			@RequestParam("taskId")String taskId,
			@RequestParam("phoneNumber")String phoneNumber){
		System.out.println("serviceId=" + serviceId);
		System.out.println("taskId=" + taskId);
		System.out.println("phoneNumber=" + phoneNumber);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	
}
