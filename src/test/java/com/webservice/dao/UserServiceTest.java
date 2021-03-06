package com.webservice.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.BDDMockito.*;

import com.webservice.model.User;
import com.webservice.model.User.Gender;
import com.webservice.service.UserService;
import com.google.common.collect.ImmutableList;

public class UserServiceTest {
	
	@Mock
	private FakeDataDao fakeDataDao;
	
	private UserService userService;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		userService = new UserService(fakeDataDao);
	}
	
	@Test
	public void shouldGetAllUsers() throws Exception {
		UUID annaUserUid = UUID.randomUUID();
	    User anna = new User(annaUserUid, "anna",
	        "montana", Gender.FEMALE, 30, "anna@gmail.com");
	    ImmutableList<User> users = new ImmutableList.Builder<User>()
	    		.add(anna)
	    		.build();
	    given(fakeDataDao.selectAllUsers()).willReturn(users);
	    
		List<User> allUsers = userService.getAllUsers(Optional.empty());
		assertThat(allUsers).hasSize(1);
		
	    assertUserField(anna);
	}
	
	@Test
	public void shouldGetAllUsersByGender() throws Exception {
		UUID annaUserUid = UUID.randomUUID();
	    User anna = new User(annaUserUid, "anna",
	        "montana", Gender.FEMALE, 30, "anna@gmail.com");

	    UUID joeUserUid = UUID.randomUUID();
	    User joe = new User(joeUserUid, "Joe", "Jones",
	        Gender.MALE, 22, "joe.jones@gmail.com");
	    
	    ImmutableList<User> users = new ImmutableList.Builder<User>()
	    		.add(anna)
	    		.add(joe)
	    		.build();
	    given(fakeDataDao.selectAllUsers()).willReturn(users);
	    
	    List<User> filterMale = userService.getAllUsers(Optional.ofNullable("MALE"));
	    assertThat(filterMale).containsOnly(joe);
	    
	    List<User> filterFemale = userService.getAllUsers(Optional.ofNullable("FEMALE"));
	    assertThat(filterFemale).containsOnly(anna);
	}
	
	@Test
	public void shouldThrowExceptionWhenGenderIsNotValid() {
	    assertThatThrownBy(()->userService.getAllUsers(Optional.ofNullable("xyz")))
	    .isInstanceOf(IllegalStateException.class)
	    .hasMessage("Invalid gender value");
	}
	
	@Test
	public void shouldGetUser() throws Exception {
		UUID annaUserUid = UUID.randomUUID();
		User anna = new User(annaUserUid, "anna",
		        "montana", Gender.FEMALE, 30, "anna@gmail.com");
		
		given(fakeDataDao.selectUserByUserUid(annaUserUid))
			.willReturn(Optional.ofNullable(anna));
		
		Optional<User> userOptional = userService.getUser(annaUserUid);
		assertThat(userOptional.isPresent()).isTrue();
		User user = userOptional.get();
		assertUserField(user);
	}
	
	@Test
	public void shouldUpdateUser() throws Exception {
		UUID annaUserUid = UUID.randomUUID();
		User anna = new User(annaUserUid, "anna",
		        "montana", Gender.FEMALE, 30, "anna@gmail.com");
		
		UUID joeUserUid = UUID.randomUUID();
	    User joe = new User(joeUserUid, "Joe", "Jones",
	        Gender.MALE, 22, "joe.jones@gmail.com");
		
		given(fakeDataDao.selectUserByUserUid(annaUserUid)).willReturn(Optional.ofNullable(anna));
		given(fakeDataDao.updateUser(anna)).willReturn(1);
		given(fakeDataDao.updateUser(joe)).willReturn(-1);
		
		ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
				
		int updateResult = userService.updateUser(anna);
		verify(fakeDataDao).selectUserByUserUid(annaUserUid);
		verify(fakeDataDao).updateUser(captor.capture());
		assertUserField(captor.getValue());
		assertThat(updateResult).isEqualTo(1);
		
		int updateFailResult = userService.updateUser(joe);
		assertThat(updateFailResult).isEqualTo(-1);
	}
	
	@Test
	public void shouldRemoveUser() throws Exception {
		UUID annaUserUid = UUID.randomUUID();
		User anna = new User(annaUserUid, "anna",
		        "montana", Gender.FEMALE, 30, "anna@gmail.com");	
		given(fakeDataDao.selectUserByUserUid(annaUserUid)).willReturn(Optional.ofNullable(anna));
		given(fakeDataDao.deleteUserByUserUid(annaUserUid)).willReturn(1);
		
		int deleteResult = userService.removeUser(annaUserUid);		
		verify(fakeDataDao).selectUserByUserUid(annaUserUid);
		verify(fakeDataDao).deleteUserByUserUid(annaUserUid);
		assertThat(deleteResult).isEqualTo(1);
		
		UUID joeUserUid = UUID.randomUUID();
	    given(fakeDataDao.deleteUserByUserUid(joeUserUid)).willReturn(-1);
	    assertThat(userService.removeUser(joeUserUid)).isEqualTo(-1);
	}

	@Test
	public void shouldInsertUser() throws Exception {
		User anna = new User(null, "anna","montana", Gender.FEMALE, 30, "anna@gmail.com");
		
		given(fakeDataDao.insertUser(any(UUID.class), eq(anna))).willReturn(1);
		int insertResult = userService.insertUser(anna);
		assertThat(insertResult).isEqualTo(1);
		
		ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
		verify(fakeDataDao).insertUser(any(UUID.class), captor.capture());
		User user = captor.getValue();
		assertThat(user).isEqualTo(anna);
	}
	
	private void assertUserField(User user) {
	    assertThat(user.getAge()).isEqualTo(30);
	    assertThat(user.getFirstName()).isEqualTo("anna");
	    assertThat(user.getLastName()).isEqualTo("montana");
	    assertThat(user.getGender()).isEqualTo(Gender.FEMALE);
	    assertThat(user.getEmail()).isEqualTo("anna@gmail.com");
	    assertThat(user.getId()).isNotNull();
	    assertThat(user.getId()).isInstanceOf(UUID.class);
	}
}
