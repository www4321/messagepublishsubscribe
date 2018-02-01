package com.mq.test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Student {
	@Value("${name:我的第一个SpringBoot}") 
	private String name;
	//@Value("${name:我的第一个SpringBoot}") 
	private String age;
	public Student(){
		name = "ww1234";
		age = "www1234";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}

}
