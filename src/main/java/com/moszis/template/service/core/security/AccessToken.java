package com.moszis.template.service.core.security;

public class AccessToken {
	private String guid;
	private String application;
	private String userPrincipal;
	private String assignedTo;
	private String username;
	private String domain;
	private boolean isService;
	private String name;
	private String emailAddress;
	private String token;
	
	public String getGuid() {
		return guid;
	}
	
	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	public String getApplication() {
		return application;
	}
	
	public void setApplication(String application) {
		this.application = application;
	}
	
	public String getUserPrincipal() {
		return userPrincipal;
	}
	
	public void setUserPrincipal(String userPrincipal) {
		this.userPrincipal = userPrincipal;
	}
	
	public String getAssignedTo() {
		return assignedTo;
	}
	
	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getDomain() {
		return domain;
	}
	
	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	public boolean isService() {
		return isService;
	}
	
	public void setService(boolean isService) {
		this.isService = isService;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
	
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "AccessToken [guid=" + guid + ", application=" + application + ", userPrincipal=" + userPrincipal
				+ ", assignedTo=" + assignedTo + ", username=" + username + ", domain=" + domain + ", isService="
				+ isService + ", name=" + name + ", emailAddress=" + emailAddress + ", token=" + token + "]";
	}

}
