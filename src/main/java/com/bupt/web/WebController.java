package com.bupt.web;



import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;


import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class WebController {
	private static Logger log = LoggerFactory.getLogger(WebController.class);
	private static int i = 0;
	@RequestMapping("/index")
    public String greeting(Map<String, Object> model) {
		log.info("times : "+i);
        model.put("message", "www1234");
        return "index";
    }

}
