package com.main;
/**
 * Class DrivetAtm representing the main driver program for the ATM simulation.
 */

import com.Exception.InvalidInputException;
import java.util.Scanner;
import com.inputdetails.Validation;

public class DrivetAtm {

	public static void main(String[] args)  {
		try {
			
			System.out.println("==================================================");
	        System.out.println("|            Welcome to ACAC Bank!               |");
	        System.out.println("|         Your Trusted Partner in Banking        |");
	        System.out.println("|    We're here to help you with all your        |");
	        System.out.println("|             banking needs.                     |");
	        System.out.println("|       Thank you for choosing ACAC Bank!        |");
	        System.out.println("==================================================");
	        Thread.sleep(4000);
	        System.out.println("Want to create Account,Contact  Manager..");
			
		}catch (InterruptedException e) {
	        e.printStackTrace();
	    }
		    
        Scanner input = new Scanner(System.in); 
		boolean errorOccured = false;

        do{
        	try {
        		errorOccured = false;
        		try {
        			Thread.sleep(4000);
        		}catch (InterruptedException e) {
        	        e.printStackTrace();
        	    }
        	System.out.println("=======================================");
        	System.out.println("|        Please Select your User      |");
        	System.out.println("|                Type!!               |");
        	System.out.println("|   1. Customer                       |");
        	System.out.println("|   2. ATM Technician                 |");
        	System.out.println("|   3. Manager                        |");
        	System.out.println("|   4. Exit                           |");
        	System.out.println("|                                     |");
        	System.out.println("=======================================");
        	System.out.println("Enter Your Choice");
        	
 
             int   userType = input.nextInt();
             if (userType < 1 || userType > 4) {
                 throw new InvalidInputException("Invalid option ");
             }

            switch (userType) {
                case 1:
                	System.out.println("Please Insert Your Card");               	
                    if (Validation.customerLogin()) {
                    	Validation.customerMenu();
                    }
                    break;
                case 2:
                	
                	if (Validation.getEmployeePassword()) { 
                        Validation.employemenu(); 
                    }
                    break;
                case 3:
                	if(Validation.getManagerPassword()) {
                		Validation.managermenu();
                	}
                    break;
                case 4:
                	System.out.println("____________________________________");
                	System.out.println("|                                  |");
                	System.out.println("|  Thank you for Banking with us!!!|");
                	System.out.println("|__________________________________|");

                    System.exit(0);
                    break;

            }
	}catch (InvalidInputException e) {
		errorOccured = true;
        System.out.println(e.getMessage());
        input.nextLine(); 
    }catch (Exception e) {
    	errorOccured = true;
        System.out.println("Please enter the valid numbers");
        input.nextLine();
    }
} while (errorOccured);
}
}
