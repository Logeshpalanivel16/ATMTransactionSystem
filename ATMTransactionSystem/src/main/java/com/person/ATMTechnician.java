package com.person;
/**
 * The ATMTechnician class represents an ATM technician who is a type of bank employee.
 * and extends the BankEmployee class.
 * @author Logesh Palanivel(Expleo)
 * @since 21 Feb 2024
 */
public class ATMTechnician extends BankEmployee{
	private int staffid;
	public ATMTechnician(int employeeid, String first_name, String last_name, String gender, long phoneNumber,
			String address, String email, int staffid) {
		super(employeeid, first_name, last_name, gender, phoneNumber, address, email);
		this.staffid = staffid;
	}
	public int getStaffid() {
		return staffid;
	}
	public void setStaffid(int staffid) {
		this.staffid = staffid;
	}

}
