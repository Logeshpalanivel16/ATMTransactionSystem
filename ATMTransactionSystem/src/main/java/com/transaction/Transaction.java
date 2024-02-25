package com.transaction;
/**
 * The Transaction class represents a banking transaction, including withdrawal, deposit, and transfer operations.
 * It also provides methods for updating PIN, withdrawing cash, depositing cash, and transferring funds.
 * @author Logesh Palanivel(Expleo)
 * @since 20 Feb 2024
 */
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.Exception.InsufficientBalanceException;
import com.Exception.InvalidWithdrawalAmountException;
import com.account.Account;
import com.inputdetails.DataBaseConnect;

public class Transaction {
	private int transactionId;

	private int customerId;
	private int accountId;
    private String transactionType;
    private double amount;
    private Timestamp transactionDate;
    private static List<Transaction> transactionList = new ArrayList<>();
	
    public Transaction(int transactionId, int accountId,String transactionType, double amount, Timestamp transactionDate, int customerId) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.customerId = customerId;
    }
    

	public Transaction( int customerId,int accountId, String transactionType, double amount) {
		super();

		this.customerId = customerId;
		this.accountId = accountId;
		this.accountId = accountId;
		this.transactionType = transactionType;
		this.amount =amount;
	}
	
	public Transaction(int transactionId, String transactionType, double amount, Timestamp transactionDate) {
		this.transactionId = transactionId;
		this.transactionType = transactionType;
		this.amount = amount;
		this.transactionDate = transactionDate;
	}

	

	public Transaction(int transactionId, String transactionType, Timestamp transactionDate, double amount) {
		this.transactionId = transactionId;
		this.transactionType = transactionType;
		this.transactionDate = transactionDate;
		this.amount = amount;
	}


	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}


	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Timestamp getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Timestamp transactionDate) {
		this.transactionDate = transactionDate;
	}
	//Prints the list of transactions with details.
	public static void printTransactions(ArrayList<Transaction> transactionList) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    
	    System.out.println("-------------------------------------------------------------------------------------------------------------------------------");
	    System.out.printf("%-20s %-20s %-15s %-20s %-20s\n",
	            "Transaction ID", "Account ID", "Transaction Type", "Amount", "Transaction Date");
	    System.out.println("-------------------------------------------------------------------------------------------------------------------------------");
	    
	    for (Transaction transaction : transactionList) {
	        System.out.printf("%-20s %-20s %-15s %-20s %-20s\n",
	                transaction.getTransactionId(), transaction.getAccountId(), 
	                transaction.getTransactionType(), transaction.getAmount(), 
	                dateFormat.format(transaction.getTransactionDate()));
	    }
	    

	    System.out.println("-------------------------------------------------------------------------------------------------------------------------------");
	}
	
    // Updates the PIN associated with a card ID.
	public static  void updatePin(int cardId) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter new PIN:");
	    String newPin = scanner.next();
	    if (!Atm.isValidPinFormat(newPin)) {
            System.out.println("Invalid PIN format. Please enter a 4-digit PIN.");
            return;
        }
	    System.out.println("Confirm new PIN:");
	    String confirmPin = scanner.next();
	    if (!Atm.isValidPinFormat(confirmPin)) {
            System.out.println("Invalid PIN format. Please enter a 4-digit PIN.");
            return;
        }
    
        if (!newPin.equals(confirmPin)) {
            System.out.println("PINs do not match. Please try again.");
            return; 
        }
        DataBaseConnect.changePin(cardId, newPin);
	}
	// Withdraws cash from the account associated with a card ID.
	public static void withdraw(int cardId,double amount)throws InsufficientBalanceException,InvalidWithdrawalAmountException {
		
		String transactionType = "Withdrawal";
		int customerId = DataBaseConnect.getCustomerId(cardId);
		int accountId = DataBaseConnect.getAccountId(customerId);
		Account account =DataBaseConnect.getAccountBalance(accountId);
		

		double balance = account.getBalance();
		 if (amount > balance) {
			 throw new InsufficientBalanceException("Insufficient balance.");
		       
		 }
		 if (amount < 200 || amount % 100 != 0) {
			 throw new InvalidWithdrawalAmountException("Withdrawal amount should be at least 200 and in multiples of 100.");
		    }
		 //amount denominations calculation
		 int remainingAmount = (int) amount;
		    int num500 = remainingAmount / 500;
		    remainingAmount %= 500;
		    int num200 = remainingAmount / 200;
		    remainingAmount %= 200;
		    int num100 = remainingAmount / 100;
		    remainingAmount %= 100;
		 DataBaseConnect.updateAccountBalance(accountId, balance - amount);
		 Transaction transaction = new Transaction(customerId,accountId,transactionType,amount);
	     transactionList.add(transaction);
	     DataBaseConnect.recordTransaction(transactionList);     
		 System.out.println("Amount withdrawn successfully!");
		    if (num500 > 0) {
		        System.out.println("500 notes: " + num500);
		    }
		    if (num200 > 0) {
		        System.out.println("200 notes: " + num200);
		    }
		    if (num100 > 0) {
		        System.out.println("100 notes: " + num100);
		    }
	}
	//Deposits cash into the account associated with a card ID.
	public static void deposit(int cardId,double amount) {
		String transactionType = "Deposit";
		int customerId = DataBaseConnect.getCustomerId(cardId);
		int accountId = DataBaseConnect.getAccountId(customerId);
		Account account =DataBaseConnect.getAccountBalance(accountId);
		String accountType = DataBaseConnect.getAccountType(cardId);
		double depositAmount = amount;
		double balance = account.getBalance();
		
		//for savings and currrent account calculations
		if (accountType.equalsIgnoreCase("savings")) {
            double interestRate = 0.01; 
            double interest = amount * interestRate;
            depositAmount += interest;
        } else if (accountType.equalsIgnoreCase("current")) {
            double transactionFee = 2.00; 
            depositAmount -= transactionFee;
        }
			
		DataBaseConnect.updateAccountBalance(accountId, balance + depositAmount);
		Transaction transaction = new Transaction(customerId,accountId,transactionType,depositAmount);
	    transactionList.add(transaction);
	     DataBaseConnect.recordTransaction(transactionList);
		System.out.println("Amount deposited successfully!!");
	
		
	}

    //Transfers funds from the account associated with a card ID to a destination account.
	public static void transferTo(int destinationId, double amountToSend, int cardId) {
		String transactionType = "Transfer";
		int customerId = DataBaseConnect.getCustomerId(cardId);
		int accountId = DataBaseConnect.getAccountId(customerId);
		Account account =DataBaseConnect.getAccountBalance(accountId);
		double balance = account.getBalance();
		 if (amountToSend > balance) {
		        System.out.println("Insufficient balance for transfer");
		        return;
		 }
		 
		 double transferAmount = amountToSend;
		 String accountType = DataBaseConnect.getAccountType(cardId);

	        if (accountType.equalsIgnoreCase("savings")) {
	            double transferFee = 1.00; 
	            transferAmount += transferFee;
	        }
		 
		DataBaseConnect.updateAccountBalance(accountId, balance - transferAmount);
		Transaction transaction = new Transaction(customerId,accountId,transactionType,transferAmount);
	    transactionList.add(transaction);
	    DataBaseConnect.recordTransaction(transactionList);
		 
	    
	    //destination account details
		int destinationcustomerId = DataBaseConnect.getCustomerId(destinationId);
		int destinationaccountId = DataBaseConnect.getAccountId(destinationcustomerId);
		Account destinationaccount =DataBaseConnect.getAccountBalance(destinationaccountId);
		double destinationBalance = destinationaccount.getBalance();
		DataBaseConnect.updateAccountBalance(destinationaccountId, destinationBalance + amountToSend);
		
		System.out.println("Amount Transferred successfully!!");
		

		 
	}

}


