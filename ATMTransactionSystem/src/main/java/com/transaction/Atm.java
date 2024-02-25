package com.transaction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Atm {
	private int atmid;
	private String location;
    private static boolean authenticated = false;
	public Atm() {
		super();
	}
	public Atm(int atmid, String location) {
		super();
		this.atmid = atmid;
		this.location = location;
	}
	public int getAtmid() {
		return atmid;
	}
	public void setAtmid(int atmid) {
		this.atmid = atmid;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	} 
	public static boolean isAuthenticated() {
        return authenticated;
    }
	public static boolean authenticatePin(int pin) {
        Scanner scanner = new Scanner(System.in);
        boolean isAuthenticated = false;
        final int maxAttempts = 3;
        int attempts = 0;

        while (attempts < maxAttempts && !isAuthenticated) {
            System.out.println("Enter Your PIN");
            String enteredPinStr = scanner.next();
            if (isValidPinFormat(enteredPinStr)) {
                int enteredPin = Integer.parseInt(enteredPinStr);
                if (enteredPin == pin) {
                    System.out.println("Your Pin is correct.");
                    isAuthenticated = true;
                } else {
                	attempts++;
                	if(attempts==maxAttempts) {
                		System.out.println("Incorrect PIN");
                	}else {
                		System.out.println("Incorrect PIN. Attempts remaining: " + (maxAttempts - attempts));
                	}
                          
                }
            } else {
                System.out.println("Invalid PIN format. Please enter a 4-digit PIN.");
            }
        }
        if (!isAuthenticated) {
            System.out.println("Maximum attempts reached.");
            System.out.println("____________________________________");
	    	System.out.println("|                                  |");
	    	System.out.println("|  Thank you for Banking with us!!!|");
	    	System.out.println("|__________________________________|");
        	System.exit(0);

        }
        return isAuthenticated;
	}
	public static boolean isValidPinFormat(String pin) {
        Pattern pinPattern = Pattern.compile("\\d{4}");
        Matcher matcher = pinPattern.matcher(pin);
        return matcher.matches();
    }
	public static boolean authenticatePassword(String passwordDatabase) {
	    Scanner scanner = new Scanner(System.in);
	    boolean isAuthenticated = false;
	    final int maxAttempts = 3;
	    int attempts = 0;

	    while (attempts < maxAttempts && !isAuthenticated) {
	        System.out.println("Enter Your PIN");
	        String enteredPassword = scanner.next();
	        if (isValidPinFormat(enteredPassword) && enteredPassword.equals(passwordDatabase)) {
	            System.out.println("Your password is correct.");
	            isAuthenticated = true;
	        } else {
	            attempts++;
	            if(attempts==maxAttempts) {
            		System.out.println("Incorrect Password");
            	}else {
            		System.out.println("Incorrect Password. Attempts remaining: " + (maxAttempts - attempts));
            	}
                      
            }
	        }
	    if (!isAuthenticated) {
	        System.out.println("Maximum attempts reached.");
	        System.out.println("____________________________________");
	    	System.out.println("|                                  |");
	    	System.out.println("|  Thank you for Banking with us!!!|");
	    	System.out.println("|__________________________________|");
	        System.exit(0);
	    }

	    return isAuthenticated;
	}

	
	public static void printReceipt(ArrayList<Transaction> transactions) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    System.out.println("                                              ACAC BANK                                                                        ");
	    System.out.println("-------------------------------------------------------------------------------------------------------------------------------");
	    System.out.printf("%-20s %-20s %-15s %-20s %-20s\n",
	            "Transaction ID", "Account ID", "Transaction Type", "Amount", "Transaction Date");
	    System.out.println("-------------------------------------------------------------------------------------------------------------------------------");

	    for (Transaction transaction : transactions) {
	        System.out.printf("%-20s %-20s %-15s %-20s %-20s\n",
	                transaction.getTransactionId(), transaction.getAccountId(), 
	                transaction.getTransactionType(), transaction.getAmount(), 
	                dateFormat.format(transaction.getTransactionDate()));
	    }

	    System.out.println("-------------------------------------------------------------------------------------------------------------------------------");
	}




}


