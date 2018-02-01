package com.bupt.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bupt.entity.User;
import com.bupt.mapper.UserMapper;

@RestController
public class UserController {
	protected Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserMapper userMapper;
	
	@RequestMapping("/getUsers")
	public List<User> getUsers() {
		List<User> users=userMapper.getAll();
		return users;
	}
	
    @RequestMapping("/getUser")
    public User getUser(String userName) {
    	User user=userMapper.getOne(userName);
        return user;
    }
    
    @RequestMapping(value = "/add", method= RequestMethod.POST)
    public String save(User user) {
    	logger.info("insert success...........");
    	userMapper.insert(user);
    	logger.info("insert success");
    	return "insert success";
    }
    
    @RequestMapping(value="update")
    public void update(User user) {
    	userMapper.update(user);
    }
    
    @RequestMapping(value="/delete/{username}")
    public void delete(@PathVariable("username") String username) {
    	userMapper.delete(username);
    }
    
    
}
