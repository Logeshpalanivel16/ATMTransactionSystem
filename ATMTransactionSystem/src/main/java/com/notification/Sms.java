package com.notification;
/**
 * The Sms class implements the Notification interface to send notifications via SMS.
 * @author Logesh Palanivel(Expleo)
 * @since 21 Feb 2024
 */

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import com.inputdetails.DataBaseConnect;
import com.transaction.Transaction;


public  class Sms implements Notification {

	//  Sends a notification to the user's mobile via SMS about the latest transaction.

    public void sendNotification(int cardId) {
    	try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        System.out.println("*******************************************************");
        System.out.println("Notification is send to your Mobile via SMS as below...");
        System.out.println("*******************************************************");
        Transaction transaction = DataBaseConnect.getTransactionNotify(cardId);
        try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        if (transaction != null) {
            System.out.println("Dear ACAC User, your A/c " + cardId + " " + transaction.getTransactionType() + " by Rs" + transaction.getAmount() + " on " + formatDate(transaction.getTransactionDate()) + " for " + transaction.getTransactionType() + " transfer to the "  + " Ref No " + transaction.getTransactionId() + ".");
            System.out.println("If not done by you, forward this SMS to 9223008333 / Call 1800111109 or 09449112211 to block CARD -ACAC");
            System.out.println("****************************************************");
        } else {
            System.out.println("No transaction found");
        }
    }
    // Formats the given timestamp into a string of format "ddMMMyy".
    private String formatDate(Timestamp timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMMyy");
        return sdf.format(timestamp);
    }
}


