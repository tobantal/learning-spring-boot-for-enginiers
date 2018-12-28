package com.webservice.model;

import java.time.LocalDate;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class User {
	
	private static final String FORMAT_TO_STRING = 
			"User{userUid=%s, firstName=%s, lastName=%s, gender=%s, age=%s, email=%s}";

	private final UUID userUid;
	private final String firstName;
	private final String lastName;
	private final Gender gender;
	private final Integer age;
	private final String email;

	public User(
			@JsonProperty("userUid") UUID userUid, 
			@JsonProperty("firstName") String firstName, 
			@JsonProperty("lastName") String lastName, 
			@JsonProperty("gender") Gender gender, 
			@JsonProperty("age") Integer age, 
			@JsonProperty("email") String email) {
		super();
		this.userUid = userUid;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.age = age;
		this.email = email;
	}
	
	
	public static User newUser(UUID userUid, User user) {
		return new User(userUid, user.getFirstName(), user.getLastName(), 
				user.getGender(), user.getAge(), user.getEmail());
	}
	
	public String getFullName() {
		return firstName + " " + lastName;
	}
	
	public int getDateOfBirth() {
		return LocalDate.now().minusYears(age).getYear();
	}
	
	@JsonProperty("id")
	public UUID getId() {
		return userUid;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Gender getGender() {
		return gender;
	}

	public Integer getAge() {
		return age;
	}

	public String getEmail() {
		return email;
	}

	public enum Gender {
		MALE, FEMALE
	}

	@Override
	public String toString() {
		return String.format(FORMAT_TO_STRING, userUid, firstName, lastName, gender, age, email);
	}

}
