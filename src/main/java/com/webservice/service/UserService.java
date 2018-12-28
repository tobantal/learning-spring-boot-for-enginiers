package com.webservice.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

	public List<User> getAllUsers(Optional<String> genderOptional) {
		List<User> users = userDao.selectAllUsers();
		if(!genderOptional.isPresent()) {
			return users;
		}
		try {
			final User.Gender gender =User.Gender.valueOf(genderOptional.get().toUpperCase());
			return users.stream()
					.filter(u->u.getGender().equals(gender))
					.collect(Collectors.toList());
		} catch(Exception e) {
			throw new IllegalStateException("Invalid gender value", e);
		}		
	}

	public Optional<User> getUser(UUID userUid) {
		return userDao.selectUserByUserUid(userUid);
	}

	public int updateUser(User user) {
		Optional<User> optionalUser = getUser(user.getId());
		if (optionalUser.isPresent()) {
			return userDao.updateUser(user);
		}
		return -1;
	}

	public int removeUser(UUID userUid) {
		Optional<User> optionalUser = getUser(userUid);
		if (optionalUser.isPresent()) {
			return userDao.deleteUserByUserUid(userUid);
		}
		return -1;
	}

	public int insertUser(User user) {
		UUID userUid = UUID.randomUUID();
		User newUser = User.newUser(userUid, user);
		return userDao.insertUser(userUid, newUser);
	}
}
