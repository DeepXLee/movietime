package com.max.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.max.dao.UserDao;
import com.max.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	
	@Override
	public String queryUserByName(String userName) {
		return userDao.queryUserByName(userName);
	}

}
