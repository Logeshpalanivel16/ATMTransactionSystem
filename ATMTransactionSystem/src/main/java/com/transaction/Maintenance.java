package com.transaction;
/**
 * The Maintenance class provides methods for servicing the ATM machine, replenishing cash, performing maintenance, and troubleshooting issues.
 * @author Logesh Palanivel(Expleo)
 * @since 20 Feb 2024
 */

public class Maintenance {
	// Simulates the process of servicing the ATM machine, including identifying and resolving machine issues.
	public static void serviceMachine() {
	    System.out.println("Servicing the ATM machine.....");
	    try {
	        Thread.sleep(5000); 
	        System.out.println("Machine issues are being identified");
	        Thread.sleep(5000);
	        System.out.println("Issues are identified....");
	        Thread.sleep(5000); 
	        System.out.println("The machine is being serviced");
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }
	}
	//Simulates the process of managing cash in the ATM machine, including validating cash, filling it, and completing cash management.
	public static void replenishCash() {
	    System.out.println("Managing cash...");
	    try {
	        Thread.sleep(5000); 
	        System.out.println("The total cash in the machine is being validated......");
	        Thread.sleep(5000);
	        System.out.println("The cash needs to be filled.....");
	        Thread.sleep(5000);
	        System.out.println("The cash is being loaded....");
	        Thread.sleep(5000); 
	        System.out.println("Cash management completed........");
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }
	}
	// Simulates the process of performing maintenance tasks on the ATM machine, including security checks, functionality testing, and error log analysis.
	public static void performMaintenance() {
	    System.out.println("Performing security checks.....");
	    try {
	        Thread.sleep(5000);
	        System.out.println("Security checks completed.....");
	        Thread.sleep(5000); 
	        System.out.println("Testing functionality...");
	        Thread.sleep(5000); 
	        System.out.println("Functionality testing completed.");
	        Thread.sleep(5000); 
	        System.out.println("Analyzing error logs...");
	        Thread.sleep(5000); 
	        System.out.println("Error log analysis completed.");
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }
	}
	// Simulates the process of troubleshooting technical issues on the ATM machine, including identifying and resolving issues.
	public static void troubleshootIssues() {
	    System.out.println("Troubleshooting technical issues on the ATM machine...");
	    try {
	        Thread.sleep(5000);
	        System.out.println("Identifying the cause of the issues.....");
	        Thread.sleep(5000); 
	        System.out.println("Attempting to resolve the issues.....");
	        Thread.sleep(5000); 
	        System.out.println("Technical issues have been resolved....");
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }
	}

}
