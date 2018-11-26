package com.webservice.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webservice.dao.UserDao;
import com.webservice.model.User;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	public UserService(UserDao userDao) {
		this.userDao = userDao;
	}

	public List<User> getAllUsers() {
		return userDao.selectAllUsers();
	}

	public Optional<User> getUser(UUID userUid) {
		return userDao.selectUserByUserUid(userUid);
	}

	public int updateUser(User user) {
		Optional<User> optionalUser = getUser(user.getUserUid());
		if (optionalUser.isPresent()) {
			userDao.updateUser(user);
			return 1;
		}
		return -1;
	}

	public int removeUser(UUID userUid) {
		Optional<User> optionalUser = getUser(userUid);
		if (optionalUser.isPresent()) {
			userDao.deleteUserByUserUid(userUid);
			return 1;
		}
		return -1;
	}

	public int insertUser(User user) {
		return userDao.insertUser(UUID.randomUUID(), user);
	}
}
