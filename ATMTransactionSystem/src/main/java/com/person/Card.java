package com.person;

public class Card {
	private String cardNo;
	private int customerId;
	private int cardid;
	private String pin;
	public Card(String cardNo, int customerId, int cardid, String pin) {
		super();
		this.cardNo = cardNo;
		this.customerId = customerId;
		this.cardid = cardid;
		this.pin = pin;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public int getCardid() {
		return cardid;
	}
	public void setCardid(int cardid) {
		this.cardid = cardid;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	
}
