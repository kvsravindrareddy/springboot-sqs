package com.veera.models;

public class SignupEvent {
	private String signupTime;
	private String userName;
	private String email;
	public String getSignupTime() {
		return signupTime;
	}
	public void setSignupTime(String signupTime) {
		this.signupTime = signupTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public SignupEvent(String signupTime, String userName, String email) {
		super();
		this.signupTime = signupTime;
		this.userName = userName;
		this.email = email;
	}
	@Override
	public String toString() {
		return "SignupEvent [signupTime=" + signupTime + ", userName=" + userName + ", email=" + email + "]";
	}
	
	
	
	
}
