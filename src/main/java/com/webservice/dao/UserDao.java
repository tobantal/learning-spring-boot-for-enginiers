package com.webservice.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.webservice.model.User;

public interface UserDao {

  List<User> selectAllUsers();

  Optional<User> selectUserByUserUid(UUID userUid);

  int updateUser(User user);

  int deleteUserByUserUid(UUID userUid);

  int insertUser(UUID userUid, User user);
}
