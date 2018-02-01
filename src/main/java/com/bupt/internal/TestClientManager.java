package com.bupt.internal;




import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestClientManager {

	
	@RequestMapping(value = "/netty", method= RequestMethod.GET)
	public String get(){

		
		return "www1234";
	}

}
