package com.person;
/**
 * The BankEmployee class represents a bank employee.
 * It contains information such as employee ID, first name, last name, gender,
 * phone number, address, email, and password.
 * @author Logesh Palanivel(Expleo)
 * @since 21 Feb 2024
 */
public class BankEmployee {
	private int employeeid;
	private String first_name;
	private String last_name;
	private String gender;
	private long phoneNumber;
	private String address;
	private String email;
	private String password;
	public BankEmployee(int employeeid, String first_name, String last_name, String gender, long phoneNumber,
			String address, String email) {
		super();
		this.employeeid = employeeid;
		this.first_name = first_name;
		this.last_name = last_name;
		this.gender = gender;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.email = email;
	}
	public BankEmployee( String password,String first_name) {
		super();
		this.password = password;
		
		this.first_name = first_name;
	}
	
	public int getEmployeeid() {
		return employeeid;
	}
	public void setEmployeeid(int employeeid) {
		this.employeeid = employeeid;
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
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(long phoneNumber) {
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	

}
