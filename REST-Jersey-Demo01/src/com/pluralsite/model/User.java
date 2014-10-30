package com.pluralsite.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {
String id;
String firstname;
String lastname;

public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getFirstname() {
	return firstname;
}
public void setFirstname(String firstname) {
	this.firstname = firstname;
}
public String getLastname() {
	return lastname;
}
public void setLastname(String lastname) {
	this.lastname = lastname;
}


}
