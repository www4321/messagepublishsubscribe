package com.bupt.mapper;

import java.util.List;

import com.bupt.entity.User;

public interface UserMapper {
	
	List<User> getAll();
	
	User getOne(String userName);

	void insert(User user);

	void update(User user);

	void delete(String userName);
}
