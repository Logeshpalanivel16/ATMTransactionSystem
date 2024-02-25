package com.account;
/**
 * class Account represents a bank account. 
 * It provides methods to retrieve and update account information.
 * @author Logesh Palanivel(Expleo)
 * @since 19 Feb 2024
 */
public class Account {
	 private int account_id;
	 private int customer_id;
	 private String account_type;
	 private double balance;
	 
	public Account(int account_id, int customer_id, String account_type, double balance) {
		this.account_id = account_id;
		this.setCustomer_id(customer_id);
		this.account_type = account_type;
		this.balance = balance;
	}
	public Account(double balance) {
		this.balance = balance;
	}
	public Account(int account_id, String account_type, double balance) {
		super();
		this.account_id = account_id;
		this.account_type = account_type;
		this.balance = balance;
	}
	public int getAccount_id() {
		return account_id;
	}
	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}
	public String getAccount_type() {
		return account_type;
	}
	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	
}


