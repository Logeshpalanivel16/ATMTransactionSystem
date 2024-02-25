package com.inputdetails;
/**
 * Class Validation responsible for validating user input and providing various banking functionalities.
 */
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import com.account.Account;
import com.notification.Sms;
import com.person.BankEmployee;
import com.person.Manager;
import com.transaction.Atm;
import com.transaction.Maintenance;
import com.transaction.Transaction;
import com.Exception.InsufficientBalanceException;
import com.Exception.InvalidInputException;
import com.Exception.InvalidWithdrawalAmountException;
import com.notification.Notification;

public class Validation {
	private static int cardId;
	public static boolean customerLogin() {
        Scanner input = new Scanner(System.in);
        boolean errorOccured = false;
        do {
        	try {
        	System.out.println("Enter Your Cardid ");
            cardId = input.nextInt();
            cardinsert();
            int pinFromDatabase = DataBaseConnect.getPinFromDatabase(cardId);
    
            if (pinFromDatabase != 0) {
            	if (Atm.authenticatePin(pinFromDatabase)) {
            		String firstName = DataBaseConnect.getCustomerFirstName(cardId);
            	    System.out.println("Welcome, " + firstName + "!");
                    return true;
                
            }
        } else {
            System.out.println("Invalid card number.Please Try again!!");
        	errorOccured = true;
        }
        	}catch (Exception e) {
	        	errorOccured = true; 
	            System.out.println("Invalid option");
	            input.nextLine(); 
	        }
        }while(errorOccured);
        return false;
    }
	public static void customerMenu() {
        Scanner input = new Scanner(System.in);

		boolean errorOccured = false;
		do {
			try {
				
        System.out.println("*********************************");
        System.out.println("*      Select operation:        *");
        System.out.println("*       1. Savings              *");
        System.out.println("*       2. Current              *");
        System.out.println("*********************************");

        
        String accountType = DataBaseConnect.getAccountType(cardId);
        int customerOption = input.nextInt();
        if (customerOption <1 || customerOption > 2) {
            throw new InputMismatchException("Invalid option"); 
        }
        switch (customerOption) {
            case 1:
                if(accountType.equalsIgnoreCase("savings")) {
                	System.out.println("Procced for savings operation");
                	transactionMenu();
                }
                else {
                	System.out.println("Your Account is not present in Savings");
                	;
                	customerMenu() ;
                }
                	
            break;
            case 2:
            	if(accountType.equalsIgnoreCase("current")) {
                	System.out.println("Procced for current operation");
                	transactionMenu();
                }
                else {
                	System.out.println("Your Account is not present in current");
                	
                	customerMenu() ;
                }
                
                
                
        }
        break;
        }
			catch (InputMismatchException e) {
	        	errorOccured = true; 
	            System.out.println("Invalid option");
	            input.nextLine(); 
	        }
			catch (Exception e) {
	        	errorOccured = true;
	            System.out.println("Please enter the valid numbers");
	            input.nextLine();
	        }
	    } while (errorOccured);
         
     
    }
	public static boolean getEmployeePassword() {
	    Scanner input = new Scanner(System.in);
	    boolean errorOcured = false;
	    do {
	    	try {
	    	System.out.println("Enter Employee ID for ATM Technician : ");
	        int empId = input.nextInt();
	        BankEmployee employeeInfo = DataBaseConnect.validateEmployee(empId);
	        if (employeeInfo.getPassword() != null) {
	        	String passwordDatabase = employeeInfo.getPassword();
	            String firstName = employeeInfo.getFirst_name();
                
                if (Atm.authenticatePassword(passwordDatabase)) {
                	System.out.println("Welcome, " + firstName + "!");
                }
	            return true;
	        }
	     else {
	        System.out.println("Your Employee ID does not match!!");
        	errorOcured = true;
	    }
	    	}catch (Exception e) {
	    		errorOcured = true;
	            System.out.println("Please enter the valid numbers");
	            input.nextLine();
	        }
	    }while(errorOcured);
	    return false;
	}


	public static boolean getManagerPassword() {
	    Scanner input = new Scanner(System.in);
	    boolean errorOcured = false;
	    do {
	    	try {
	    		System.out.println("Enter Employee ID for Manger : ");
			    int empId = input.nextInt();
			    Manager managerInfo = DataBaseConnect.validateManager(empId);
			    if (managerInfo.getPassword() != null) {
			        String passwordDatabase = managerInfo.getPassword();
			        String firstName = managerInfo.getFirst_name();
			        System.out.println(passwordDatabase); 
			        if (Atm.authenticatePassword(passwordDatabase)) {
			            System.out.println("Welcome, Manager " + firstName + "!");
			            return true;
			        }
			    } else {
			        System.out.println("Your Employee ID does not match!!");
			        errorOcured = true;

			    }
	    	}catch (Exception e) {
	    		errorOcured = true;
	            System.out.println("Please enter the valid numbers");
	            input.nextLine();
	        } 	
	    }while(errorOcured); 
	    return false;
	}
    
	public static void transactionMenu() throws InvalidWithdrawalAmountException, InsufficientBalanceException {

		Scanner input = new Scanner(System.in);
		boolean errorOccured = false;
		do {
		do {
		try {
		errorOccured = false;
		System.out.println("╔══════════════════════════════════════════════╗");
		System.out.println("║             Select Operation:                ║");
		System.out.println("╠══════════════════════════════════════════════╣");
		System.out.println("║   1. Withdraw                                ║");
		System.out.println("║   2. Deposit                                 ║");
		System.out.println("║   3. Balance Enquiry                         ║");
		System.out.println("║   4. Change PIN                              ║");
		System.out.println("║   5. Transfer Money to Other Account         ║");
		System.out.println("║   6. Mini Satement                           ║");
		System.out.println("║   7. Exit                                    ║");
		System.out.println("╚══════════════════════════════════════════════╝");

		int transactionmenu = input.nextInt();
		if (transactionmenu < 1 || transactionmenu > 7) {
            throw new InvalidInputException("Invalid option");
        }
		boolean exit = false;
		switch(transactionmenu) {
		case 1:
			System.out.println("Enter the amount to withdraw");
		    double withdrawAmount = input.nextDouble();

			try {
		        Transaction.withdraw(cardId, withdrawAmount);
		        Notification notified = new Sms();
		        try {
		        	notified.sendNotification(cardId);
		        }catch(Exception e) {
		        	e.printStackTrace();
		        }
		        
		        print();
		        backMenu();
		    } catch (Exception e) {
		        System.out.println(e.getMessage());
		    }
			break;
		case 2:
				System.out.println("Enter the amount to deposit");
				double depositamount = input.nextInt();
				if (validatePin(cardId)) {
					Transaction.deposit(cardId, depositamount);	
					Notification notified = new Sms();
			        try {
			        	notified.sendNotification(cardId);
			        }catch(Exception e) {
			        	e.printStackTrace();
			        }
				    print();
				    backMenu();

			   } else {
				   System.out.println("Incorrect PIN. Transaction canceled.");
		           exit = true;
		           backMenu();
		    }
			break;
		case 3:
				int customerId = DataBaseConnect.getCustomerId(cardId);
				int accountId = DataBaseConnect.getAccountId(customerId);
				Account accout = DataBaseConnect.getAccountBalance(accountId);		
			    String transactionType = "Balance Inquiry";
			    Transaction balanceEnquiryTransaction = new Transaction(customerId, accountId, transactionType, accout.getBalance());
		        List<Transaction> balanceEnquiryList = new ArrayList<>();
		        balanceEnquiryList.add(balanceEnquiryTransaction);
		        DataBaseConnect.recordTransaction(balanceEnquiryList);
		        System.out.println("Do you want display balance in screen or not?");
		        System.out.println("Press 1 to display or 2 to print the receipt");
		        int balanceDisplay = input.nextInt();
		        if(balanceDisplay==1)
		        	System.out.println("Your Account Balance is " + accout.getBalance());
		        else
		        	printbalance();
		        Notification notified = new Sms();
		        try {
		        	notified.sendNotification(cardId);
		        }catch(Exception e) {
		        	e.printStackTrace();
		        }
	            backMenu();

		case 4:
			if (validatePin(cardId)) {
				Transaction.updatePin(cardId);
			
			    int customerId1 = DataBaseConnect.getCustomerId(cardId);
		        int accountId1 = DataBaseConnect.getAccountId(customerId1);

		        String transactionType1 = "PIN Change";
		        double amount = 0;
		        Transaction pinChangeTransaction = new Transaction(customerId1, accountId1, transactionType1, amount);
		        List<Transaction> pinChangeList = new ArrayList<>();
		        pinChangeList.add(pinChangeTransaction);
		        DataBaseConnect.recordTransaction(pinChangeList);
		        Notification notified1 = new Sms();
		        try {
		        	notified1.sendNotification(cardId);
		        }catch(Exception e) {
		        	e.printStackTrace();
		        }
	            print();
	            backMenu();
	           
			} else {
		        System.out.println("Incorrect PIN. Transaction canceled.");
		        exit = true;
		        backMenu();
		    }
		case 5:
				System.out.println("Enter the Person Account Number");
				int accountNumber =input.nextInt();
				int customerid = DataBaseConnect.getCustomerId2(accountNumber);
				int cardid = DataBaseConnect.getCardId(customerid);
			    if (DataBaseConnect.isValidCardId(cardid)) {
			    	System.out.println("Enter the amount");
			    	double amountToSend = input.nextDouble();
			    	Transaction.transferTo(cardid,amountToSend,cardId);
			    	Notification notified1 = new Sms();
			        try {
			        	notified1.sendNotification(cardId);
			        }catch(Exception e) {
			        	e.printStackTrace();
			        }
		            print();
		            backMenu();
			    } else {
		            System.out.println("Account does not exist. Transaction canceled.");
		            exit = true;
		            backMenu();
		        }
			
		    
		case 6:
				ArrayList<Transaction> transaction1 = DataBaseConnect.transaction(cardId);
				Atm.printReceipt(transaction1);
				backMenu();
				
		case 7:
			System.out.println("____________________________________");
			System.out.println("|                                  |");
			System.out.println("|  Thank you for Banking with us!!!|");
			System.out.println("|__________________________________|");

			System.exit(0);
			
		}
		
		}
		catch (InvalidInputException e) {
        	errorOccured = true; 
            System.out.println(e.getMessage());
            input.nextLine(); 
            backMenu();
        }
    } while (errorOccured);
		}while(true);
	}
	public static void backMenu() throws InvalidWithdrawalAmountException, InsufficientBalanceException {
		System.out.println("Do you want to continue. If yes press 1 else press 0 ");
		Scanner sc = new Scanner(System.in);
		int opt=sc.nextInt();
		if (opt ==1) {
			transactionMenu();
		}
		else {
			System.out.println("____________________________________");
			System.out.println("|                                  |");
			System.out.println("|  Thank you for Banking with us!!!|");
			System.out.println("|__________________________________|");

			System.exit(0);
		}
			
	}

	public static void employemenu() {
		Scanner input = new Scanner(System.in);
		boolean errorOccured = false;
		do {
		do {
	        try {
	            errorOccured = false; 
	            System.out.println("===========================================");
	            System.out.println("|             ATM Operations              |");
	            System.out.println("===========================================");
	            System.out.println("|          Select Operation:              |");
	            System.out.println("|          1. Service Machine             |");
	            System.out.println("|          2. Replenish Cash              |");
	            System.out.println("|          3. Perform Maintenance         |");
	            System.out.println("|          4. Troubleshoot Issues         |");
	            System.out.println("|          5. Exit                        |");
	            System.out.println("===========================================");

	            int technicianOption = input.nextInt();
	            if (technicianOption < 1 || technicianOption > 5) {
                    throw new InvalidInputException("Invalid option");
                }
	            boolean exit = false;
	            switch (technicianOption) {
	                case 1:
	                    Maintenance.serviceMachine();
	                    exit = back();
	                    break;

	                case 2:
	                    Maintenance.replenishCash();
	                    exit = back();
	                    break;
	                case 3:
	                    Maintenance.performMaintenance();
	                    exit = back();
	                    break;
	                case 4:
	                    Maintenance.troubleshootIssues();
	                    exit = back();
	                    break;
	                case 5:
	                	System.out.println("____________________________________");
	                	System.out.println("|                                  |");
	                	System.out.println("|  Thank you for Banking with us!!!|");
	                	System.out.println("|__________________________________|");

	                    System.exit(0);
	            }
	        } catch (InvalidInputException e) {
	        	errorOccured = true; 
                System.out.println(e.getMessage());
                input.nextLine(); 
            }
	        catch (Exception e) {
	        	errorOccured = true;
	            System.out.println("Please enter the valid numbers");
	            input.nextLine();
	        }
	    } while (errorOccured);
		}while(true);
		}
	
	public static void managermenu() {
		Scanner input = new Scanner(System.in);
		boolean errorOccured = false;
		do {
		do {
		try {
			errorOccured = false;
			System.out.println("*************************************");
			System.out.println("*        Select Operation           *");
			System.out.println("* 1. Transaction Of the  ATM        *");
			System.out.println("* 2.  New Account For   Customer    *");
			System.out.println("* 3.        Exit                    *");
			System.out.println("*************************************");

		int managerOption = input.nextInt();
		if (managerOption < 1 || managerOption > 3) {
            throw new InvalidInputException("Invalid option");
        }
		boolean exit = false;
	    switch (managerOption) {
	        case 1:
	        	ArrayList<Transaction> transactionList = DataBaseConnect.viewTransactionDetails();
	            Transaction.printTransactions(transactionList);
	            exit = back();
	            break;
	        case 2:
	        	Manager.insertAccout();
	        	exit = back();
	            break;
	        case 3:
            	System.out.println("____________________________________");
            	System.out.println("|                                  |");
            	System.out.println("|  Thank you for Banking with us!!!|");
            	System.out.println("|__________________________________|");
                System.exit(0);      	
	}
		}
		catch (InvalidInputException e) {
        	errorOccured = true; 
            System.out.println(e.getMessage());
            input.nextLine(); 
        }
		catch (Exception e) {
        	errorOccured = true;
            System.out.println("Please enter the valid numbers");
            input.nextLine();
        }
    } while (errorOccured);
		}while(true);
		
	}
	public static boolean back() {
	    System.out.println("Do you want to continue or exit? Enter 1 to return to menu, Enter 2 to exit");
	    Scanner input = new Scanner(System.in);
	    int back = input.nextInt();
	    if (back == 2) {
	    	System.out.println("____________________________________");
	    	System.out.println("|                                  |");
	    	System.out.println("|  Thank you for Banking with us!!!|");
	    	System.out.println("|__________________________________|");

	        System.exit(0);
	    } else if (back != 1) {
	        System.out.println("Returning to the menu.");
	        return false;
	    }
	    return true; 
	}

	public static void print() {	
        Scanner input = new Scanner(System.in);
		ArrayList<Transaction> transaction1 = DataBaseConnect.transaction(cardId);
		 System.out.println("Do you want to print the Receipt (Enter 1 to print or 2 to continue");
		 int option = input.nextInt(); 
		 if(option==1) {
			 Atm.printReceipt(transaction1);
		 }
		
	}
	public static void printbalance() {	
        Scanner input = new Scanner(System.in);
		ArrayList<Transaction> transaction1 = DataBaseConnect.transaction(cardId);
			 Atm.printReceipt(transaction1);
		
	}
	public static boolean validatePin(int cardId) {
	    Scanner input = new Scanner(System.in);
	    String enteredPin;
	    while (true) {
	        System.out.println("Enter your 4-digit PIN:");
	        enteredPin = input.nextLine();
	        if (enteredPin.matches("\\d{4}")) {
	            break; 
	        } else {
	            System.out.println("Invalid PIN. Please enter a 4-digit PIN.");
	        }
	    }

	    int storedPin = DataBaseConnect.getPinFromDatabase(cardId); 
	    return enteredPin.equals(String.valueOf(storedPin));
	}
	public static void cardinsert() {
		try {
	        
	        System.out.println("Validating your Card...");
	        Thread.sleep(4000);
	        System.out.println("Your Card is being Verified...");
	        Thread.sleep(3000);
	        System.out.println("Your Card details are fetched...");
	        Thread.sleep(4000); 
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }
	}

	
}
