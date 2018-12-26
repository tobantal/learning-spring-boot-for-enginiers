package com.webservice.model;

import java.util.UUID;

public class User {

	private UUID userUid;
	private String firstName;
	private String lastName;
	private Gender gender;
	private Integer age;
	private String email;

	public User(UUID userUid, String firstName, String lastName, Gender gender, Integer age, String email) {
		super();
		this.userUid = userUid;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.age = age;
		this.email = email;
	}
	
	public User() {}

	public void setUserUid(UUID userUid) {
		this.userUid = userUid;
	}

	public UUID getUserUid() {
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
		return "User{userUid=" + userUid + ", firstName=" + firstName + ", lastName=" + lastName + ", gender=" + gender
				+ ", age=" + age + ", email=" + email + "}";
	}

}
