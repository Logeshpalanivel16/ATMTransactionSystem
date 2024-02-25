package com.account;
/**
* class Bank gives the information related to a bank, including its unique identifier, name, city,
* IFSC code, and branch name.
* @author Logesh Palanivel(Expleo)
 * @since 19 Feb 2024
 */
public class Bank {
	private int bank_id;
	private String bank_name;
	private String bank_city;
	private String ifsc_code;
	private String branch_name;
	public int getBank_id() {
		return bank_id;
	}
	public void setBank_id(int bank_id) {
		this.bank_id = bank_id;
	}
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}
	public String getBank_city() {
		return bank_city;
	}
	public void setBank_city(String bank_city) {
		this.bank_city = bank_city;
	}
	public String getIfsc_code() {
		return ifsc_code;
	}
	public void setIfsc_code(String ifsc_code) {
		this.ifsc_code = ifsc_code;
	}
	public String getBranch_name() {
		return branch_name;
	}
	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}
	

}
