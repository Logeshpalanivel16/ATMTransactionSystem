package com.person;
/**
 * The Manager class represents a bank manager, inheriting from BankEmployee.
 * It provides functionality for inserting new customer accounts into the database.
 * @author Logesh Palanivel(Expleo)
 * @since 21 Feb 2024
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.account.Account;
import com.inputdetails.DataBaseConnect;

public class Manager extends BankEmployee{
	private int roleid;
	public Manager(String storedPassword, String firstName) {
	    super(storedPassword, firstName);
	
	}
	public int getRoleid() {
		return roleid;
	}

	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}
	
    // Inserts customer, account, and card details into the database.
	public static void insertAccout() {
		try {
			Scanner input= new Scanner(System.in);
			System.out.println("Enter the customer Details");
			int customerId = validateCustomerId();
			System.out.println("Enter the First Name");
	        String firstname = input.next();
	        System.out.println("Enter the Second Name");
	        String lastname = input.next();
	        String phoneNumber = validatePhoneNumber();
	        System.out.println("Enter the Address");
	        String address = input.next(); 
	        String emailid = validateEmail();
			Customer customer = new Customer(customerId,firstname,lastname,phoneNumber,address,emailid);
			List<Customer> insertcustomer = new ArrayList<>();
			insertcustomer.add(customer);
			input.nextLine();
			System.out.println("Enter the Account Details");
			int accountId = validateAccountId();
			
		    String accountType = validateAccountType();
		    
		    double balance = valuebalance();
		    
			Account account = new Account(accountId,customerId,accountType,balance);
			List<Account> insertaccount = new ArrayList<>();
			insertaccount.add(account);
			
			System.out.println("Enter the Card Details");
			int cardId = validateCardId();
		    String cardNumber = validateCardNumber();
		    String pin = validatePin();
			Card card = new Card(cardNumber,customerId,cardId,pin);
			List<Card> insertcard = new ArrayList<>();
			insertcard.add(card);
			
			boolean isCustomers =DataBaseConnect.insertCustomers(insertcustomer);
			boolean isAccount = DataBaseConnect.insertAccounts(insertaccount);
			boolean isCard = DataBaseConnect.insertCards(insertcard);
			System.out.println("Account has beeen created!!!!");
			if(isCustomers &&isAccount && isCard) {
				System.out.println("Account has been created!!!!");
		    } else {
		        System.out.println("Failed to Account");
		        }
			
		}catch(Exception e) {
			System.out.println("Not able to Create Account ");
		}
		
	}
	private static double valuebalance() {
		Scanner input= new Scanner(System.in);
		System.out.println("Enter the inital balance");
		double balance = input.nextDouble();
		return balance;
		}
	//  the user to enter an email address and validates it against the pattern for Gmail addresses.
	public static String validateEmail() {
		Scanner input= new Scanner(System.in);
		System.out.println("Enter the email id");
	    while (true) {
	        String emailid = input.next();
	        if (emailid.matches("^[a-zA-Z0-9._%+-]+@gmail\\.com$")) {

	            return emailid;
	        } else {
	            System.out.println("Invalid email address. Please enter a valid gmail");
	        }
	    }
	}
	// the user to enter a phone number and validates it against a pattern for 10-digit numbers.
	public static String validatePhoneNumber() {
		Scanner input= new Scanner(System.in);
		System.out.println("Enter the Phone Number");
		while(true) {
			String phoneNumber = input.next();
			if(phoneNumber.matches("\\d{10}")){
				return phoneNumber;
			}
			else {
				System.out.println("Invalid Phone Number.Please enter a valid 10 digit number");
			}
		}
	}
	// the user to enter a customer ID and validates it against a pattern for 5-digit numbers.
	public static int validateCustomerId() {
		Scanner input= new Scanner(System.in);

		System.out.println("Enter the customer id(5-digit)");
        while (true) {
            String CustomerId = input.next();
            if (CustomerId.matches("\\d{5}")) {
                return Integer.parseInt(CustomerId);
            } else {
                System.out.println("Invalid customer id. Please enter a valid 5 digit number");
            }
        }
    }
	// the user to enter an account ID and validates it against a pattern for 10-digit numbers.
	public static int validateAccountId() {
	    Scanner input = new Scanner(System.in);

	    System.out.println("Enter the account number ");
	    while (true) {
	        String accountId = input.next(); 
	        if (accountId.matches("\\d{10}")) {
	            return Integer.parseInt(accountId);
	        } else {
	            System.out.println("Invalid account id. Please enter a valid 10-digit integer.");
	        }
	    }
	}
	// the user to enter an account type and validates it to be either "Current" or "Savings".
	public static String validateAccountType() {
		Scanner input= new Scanner(System.in);
        System.out.println("Enter the Account Type 'Current' or 'Savings'");
        while (true) {
            String accountType = input.nextLine();
            if (accountType.equalsIgnoreCase("Current") || accountType.equalsIgnoreCase("Savings")) {
                return accountType;
            } else {
                System.out.println("Invalid account type. Please enter 'Current' or 'Savings'.");
            }
        }
    }
	//  the user to enter a card ID and validates it against a pattern for 10-digit numbers.
	public static int validateCardId() {
		Scanner input= new Scanner(System.in);
        System.out.println("Enter the cardId (10 - digit)");
        while (true) {
            String cardId = input.next();
            if (cardId.matches("\\d{10}")) {
                return Integer.parseInt(cardId);
            } else {
                System.out.println("Invalid card id. Please enter a valid 5-digit integer.");
            }
        }
    }
	// the user to enter a card number and validates it against a pattern for 10-digit numbers.
	public static String validateCardNumber() {
		Scanner input= new Scanner(System.in);

        System.out.println("Enter the card No (10-digit)");
        while (true) {
            String cardNumber = input.next();
            if (cardNumber.matches("\\d{10}")) {
                return cardNumber;
            } else {
                System.out.println("Invalid card number. Please enter a valid 10-digit number.");
            }
        }
    }
	//  the user to enter a PIN and validates it against a pattern for 4-digit numbers.
	public static String validatePin() {
		Scanner input= new Scanner(System.in);

        System.out.println("Enter the Pin (4-digit number)");
        while (true) {
            String pin = input.next();
            if (pin.matches("\\d{4}")) {
                return pin;
            } else {
                System.out.println("Invalid PIN. Please enter a valid 4-digit number.");
            }
        }
    }
	
}

	
