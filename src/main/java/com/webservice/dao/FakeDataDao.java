package com.webservice.dao;

import org.springframework.stereotype.Repository;

import com.webservice.model.User;
import com.webservice.model.User.Gender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FakeDataDao implements UserDao {

  private Map<UUID, User> database;

  public FakeDataDao() {
    database = new HashMap<>();
    UUID joeUserUid = UUID.randomUUID();
    database.put(joeUserUid, new User(joeUserUid, "Joe", "Jones",
        Gender.MALE, 22, "joe.jones@gmail.com"));
  }

  @Override
  public List<User> selectAllUsers() {
    return new ArrayList<>(database.values());
  }

  @Override
  public Optional<User> selectUserByUserUid(UUID userUid) {
    return Optional.ofNullable(database.get(userUid));
  }

  @Override
  public int updateUser(User user) {
    database.put(user.getId(), user);
    return 1;
  }

  @Override
  public int deleteUserByUserUid(UUID userUid) {
    database.remove(userUid);
    return 1;
  }

  @Override
  public int insertUser(UUID userUid, User user) {
    database.put(userUid, user);
    return 1;
  }
}
