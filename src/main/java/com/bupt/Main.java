package com.bupt;

import java.io.InputStream;
import java.util.Properties;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.web.bind.annotation.RestController;


@RestController
@SpringBootApplication
@MapperScan("com.bupt.mapper")
public class Main {

    public static void main(String[] args) throws Exception {
    	
    	Properties properties = new Properties();
    	InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("application.properties");
    	properties.load(inputStream);
    	SpringApplication springApplication = new SpringApplication(Main.class);
    	springApplication.setDefaultProperties(properties);
    	springApplication.run(args);
    }

}
