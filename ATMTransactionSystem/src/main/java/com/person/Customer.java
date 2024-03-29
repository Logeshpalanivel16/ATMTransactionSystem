package com.person;
/**
*The Customer class represents a bank customer.
*It contains information such as customer ID, first name, last name, phone number, address, and email.
* @author Logesh Palanivel(Expleo)
* @since 21 Feb 2024
 */
public class Customer {
	private int customer_id;
	private String first_name;
	private String last_name;
	private String phoneNumber;
	private String address;
	private String email;
	public Customer() {
		super();
	}
	public Customer(int customer_id, String first_name, String last_name,String phoneNumber,
			String address, String email) {
		super();
		this.customer_id = customer_id;
		this.first_name = first_name;
		this.last_name = last_name;

		this.phoneNumber = phoneNumber;
		this.address = address;
		this.email = email;
	}
	//appropriate getters and setters
	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	

}
