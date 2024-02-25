package com.inputdetails;
/**
 * The class DataBaseConnect responsible for connecting to the database and performing various operations.
 * @author Logesh Palanivel(Expleo)
 * @since 20 Feb 2024
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.account.Account;
import com.person.BankEmployee;
import com.person.Card;
import com.person.Customer;
import com.person.Manager;
import com.transaction.Transaction;

public class DataBaseConnect {
	private static Connection con;
    static String url = "jdbc:oracle:thin:@localhost:1521:xe";
    static String user ="SYSTEM";
    static String password = "Logesh@16";
    static {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    //Retrieves the PIN associated with the specified card ID from the database
    public static int getPinFromDatabase(int cardId) {
        int pin = 0;
        final String select = "SELECT pin FROM DATABASE.card WHERE card_id = ?";
        try (PreparedStatement stat = con.prepareStatement(select)) {
            stat.setInt(1, cardId);
            try (ResultSet resultSet = stat.executeQuery()) {
                if (resultSet.next()) {
                    pin = resultSet.getInt("pin");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pin; 
    }
    //Retrieves the first name of the customer associated with the specified card ID from the database.
    public static String getCustomerFirstName(int cardId) {
        String firstName = null;
        final String select = "SELECT c.first_name FROM DATABASE.customer c JOIN DATABASE.card cd ON c.customer_id = cd.customer_id WHERE cd.card_id = ?";
        try (PreparedStatement stat = con.prepareStatement(select)) {
            stat.setInt(1, cardId);
            try (ResultSet resultSet = stat.executeQuery()) {
                if (resultSet.next()) {
                    firstName = resultSet.getString("first_name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return firstName;
    }
    //Validates the bank employee with the specified employee ID and role ID from the database.
    public static BankEmployee validateEmployee(int empId) {
        String storedPassword = null; 
        String firstName = null;
        final String select = "SELECT password, first_name FROM DATABASE.bankemployee WHERE employee_id = ? AND role_id = 2"; 
        try (PreparedStatement stat = con.prepareStatement(select)) {
            stat.setInt(1, empId);
            try (ResultSet resultSet = stat.executeQuery()) {
                if (resultSet.next()) {
                    storedPassword = resultSet.getString("password");
                    firstName = resultSet.getString("first_name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new BankEmployee(storedPassword, firstName);
    }
    //Validates the manager with the specified employee ID and role ID from the database.
    public static Manager validateManager(int empId) {

        String storedPassword = null;
        String firstName = null;
        final String select = "SELECT password, first_name FROM DATABASE.bankemployee WHERE employee_id = ? AND role_id = 1"; 
        try (PreparedStatement stat = con.prepareStatement(select)) {
            stat.setInt(1, empId);
            try (ResultSet resultSet = stat.executeQuery()) {
                if (resultSet.next()) {
                    storedPassword = resultSet.getString("password");
                    firstName = resultSet.getString("first_name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Manager(storedPassword,firstName);
    }
    //Retrieves transaction details from the database and returns them as a list of Transactions.
    public static ArrayList<Transaction> viewTransactionDetails() {
    	ArrayList<Transaction> transactionList = new ArrayList<>();
    	final String query = "SELECT * FROM DATABASE.transaction";
    	try {
            PreparedStatement stat = con.prepareStatement(query);
            ResultSet resultSet =stat.executeQuery();

            while (resultSet.next()) {
                int transactionId = resultSet.getInt("transaction_id");
                int accountId = resultSet.getInt("account_id");
                String transactionType = resultSet.getString("transaction_type");
                double amount = resultSet.getDouble("amount");
                Timestamp transactionDate = resultSet.getTimestamp("transaction_date");
                int customerId = resultSet.getInt("customer_id");
                Transaction transaction = new Transaction(transactionId,accountId,transactionType,amount,transactionDate,customerId);
                transactionList.add(transaction);              
    	}
    }
    	catch (SQLException e) {
            e.printStackTrace();
        } 
    	return transactionList;
}
    //Retrieves the account type associated with the given card ID from the database
    public static String getAccountType(int cardId) {
    	
        String accountType = null;
        try (PreparedStatement stat = con.prepareStatement("SELECT account_type FROM DATABASE.card c JOIN DATABASE.account a ON c.customer_id = a.customer_id WHERE c.card_id = ?")) {
        	stat.setInt(1, cardId);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    accountType = rs.getString("account_type");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accountType;
    }
    //Retrieves the account ID associated with the given customer ID from the database.
    public static int getAccountId(int customerId) {
        int accountId = 0;
        try (PreparedStatement stat = con.prepareStatement("SELECT account_id FROM DATABASE.account WHERE customer_id = ?")) {
        	
        	stat.setInt(1, customerId);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    accountId = rs.getInt("account_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accountId;
    }
    //Retrieves the ATM ID associated with the given customer ID from the database.
    public static int getATMId(int customerid) {
        int atmId = 0;
        try (PreparedStatement stat = con.prepareStatement("SELECT DISTINCT atm.atm_id FROM DATABASE.atm JOIN DATABASE.transaction ON atm.atm_id = transaction.atm_id JOIN DATABASE.account ON transaction.account_id = account.account_id WHERE account.customer_id = ?")) {
        	stat.setInt(1, customerid);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    atmId = rs.getInt("atm_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return atmId;
    }
    //Retrieves the customer ID associated with the given card ID from the database.
    public static int getCustomerId(int cardId) {
        int customerId = 0;
        try (PreparedStatement stat = con.prepareStatement("SELECT customer_id FROM DATABASE.card WHERE card_id = ?")) {
        	stat.setInt(1, cardId);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    customerId = rs.getInt("customer_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerId;
    }
    //Retrieves the account balance associated with the given account ID from the database
    public static Account getAccountBalance(int accountId) {
        double balance = 0;
        try (PreparedStatement stat = con.prepareStatement("SELECT balance FROM DATABASE.account WHERE account_id = ?")) {
        	stat.setInt(1, accountId);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    balance = rs.getDouble("balance");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Account account = new Account(balance);
        return account;
    }
    //Records a list of transactions in the database.
    public static void recordTransaction(List<Transaction> transactionList) {
        try (PreparedStatement stat = con.prepareStatement("INSERT INTO DATABASE.transaction (account_id, transaction_type, amount, customer_id) VALUES (?, ?, ?, ?)")) {
        	for (Transaction transaction : transactionList) {
        		stat.setInt(1, transaction.getAccountId());
        		stat.setString(2, transaction.getTransactionType());
        		stat.setDouble(3, transaction.getAmount());
        		stat.setInt(4, transaction.getCustomerId());
                stat.executeUpdate();
                
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //Changes the PIN for a given card ID.
    public static void changePin(int cardId, String newPin) {
        try (PreparedStatement stat = con.prepareStatement("UPDATE DATABASE.card SET pin = ? WHERE card_id = ?")) {
            stat.setString(1, newPin);
            stat.setInt(2, cardId);
            int rowsAffected = stat.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("PIN successfully changed.");
            } else {
                System.out.println("Failed to change PIN");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //  Updates the balance of an account with the specified account ID.
    public static void updateAccountBalance(int accountId, double newBalance) {
        try (PreparedStatement stmt = con.prepareStatement("UPDATE DATABASE.account SET balance = ? WHERE account_id = ?")) {
            stmt.setDouble(1, newBalance);
            stmt.setInt(2, accountId);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //Retrieves the latest transaction details for a given card ID.
    public static ArrayList<Transaction> transaction(int cardid) {
        ArrayList<Transaction> transactions = new ArrayList<>();
    	try(PreparedStatement stat = con.prepareStatement("SELECT * FROM (SELECT t.transaction_id, t.account_id, t.transaction_type, t.amount, t.transaction_date, t.customer_id FROM DATABASE.transaction t JOIN DATABASE.card c ON t.customer_id = c.customer_id WHERE c.card_id = ? ORDER BY t.transaction_date DESC) WHERE ROWNUM <= 5")){
    		stat.setInt(1, cardid);
            
            try (ResultSet rs = stat.executeQuery()) {
                while (rs.next()) {
                    int transactionId = rs.getInt("transaction_id");
                    int accountId = rs.getInt("account_id");
                    String transactionType = rs.getString("transaction_type");
                    double amount = rs.getDouble("amount");
                    Timestamp transactionDate = rs.getTimestamp("transaction_date");
                    int customerId = rs.getInt("customer_id");
                    Transaction transaction = new Transaction(transactionId, accountId, transactionType, amount, transactionDate, customerId);
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }      
        return transactions;
}
    //  Inserts a list of customers into the database.
    public static boolean insertCustomers(List<Customer> customers) {
        String query = "INSERT INTO DATABASE.customer (customer_id, first_name, last_name, address, phone_number, email) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stat = con.prepareStatement(query)) {
            for (Customer customer : customers) {
                stat.setInt(1, customer.getCustomer_id());
                stat.setString(2, customer.getFirst_name());
                stat.setString(3, customer.getLast_name());
                stat.setString(4, customer.getAddress());
                stat.setString(5, customer.getPhoneNumber());
                stat.setString(6, customer.getEmail());
                int rowsAffected = stat.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Inserted Customer Details ");
                } else {
                    System.out.println("Failed to Customer Details");
                    return false;
                }

            }
            return true;
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        } 
    }
    //  Inserts a list of accounts into the database.
    public static boolean insertAccounts(List<Account> accounts) {
        String query = "INSERT INTO DATABASE.account (account_id, customer_id, account_type, balance) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stat = con.prepareStatement(query)) {
            for (Account account : accounts) {
                stat.setInt(1, account.getAccount_id());
                stat.setInt(2, account.getCustomer_id());
                stat.setString(3, account.getAccount_type());
                stat.setDouble(4, account.getBalance());
                int rowsAffected = stat.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Inserted Account Details");
                } else {
                    System.out.println("Failed to insert Account Details");
                    return false;
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

}
    //Inserts a list of cards into the database
    public static boolean insertCards(List<Card> cards) {
        String query = "INSERT INTO DATABASE.card (card_id, customer_id, card_number, pin) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stat = con.prepareStatement(query)) {
            for (Card card : cards) {
                stat.setInt(1, card.getCardid());
                stat.setInt(2, card.getCustomerId());
                stat.setString(3, card.getCardNo());
                stat.setString(4, card.getPin());
                int rowsAffected = stat.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Inserted Card Details");
                } else {
                    System.out.println("Failed to insert Card Details");
                    return false;
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // Retrieves the most recent transaction for a specified card ID.
    public static Transaction getTransactionNotify(int cardId) {
    

            try(PreparedStatement stat = con.prepareStatement("SELECT * FROM (SELECT t.transaction_id, t.transaction_type, t.amount, t.transaction_date FROM DATABASE.transaction t JOIN DATABASE.account a ON t.account_id = a.account_id JOIN DATABASE.card c ON a.customer_id = c.customer_id WHERE c.card_id = ? ORDER BY t.transaction_date DESC) WHERE ROWNUM <= 1")) {
                                                                                                              
                stat.setInt(1, cardId);
                try (ResultSet rs = stat.executeQuery()) {

                while(rs.next()) {
                    int transactionId = rs.getInt("transaction_id");
                    String transactionType = rs.getString("transaction_type");
                    Timestamp transactionDate = rs.getTimestamp("transaction_date");
                    double amount = rs.getDouble("amount");
                    return new Transaction(transactionId, transactionType, transactionDate, amount);
                }
                

                }

           
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
			return null;
    }
    //Checks whether a card ID exists in the database
    public static boolean isValidCardId(int cardId) {
             boolean isValid = false;
            String query = "SELECT * FROM DATABASE.card WHERE card_id = ?";
            try (PreparedStatement statement = con.prepareStatement(query)) {
                statement.setInt(1, cardId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    isValid = resultSet.next(); 
                }
            }
         catch (SQLException e) {
            e.printStackTrace();
        }
        return isValid;
    } 
    //Retrieves the customer ID associated with a given account ID from the database.
    public static int getCustomerId2(int accountId) {
        int customerId = 0;
        try (PreparedStatement stat = con.prepareStatement("SELECT c.customer_id FROM DATABASE.account a JOIN DATABASE.customer c ON a.customer_id = c.customer_id WHERE a.account_id = ?")) {

            stat.setInt(1, accountId);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    customerId = rs.getInt("customer_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerId;
    }
    //Retrieves the card ID associated with a given customer ID from the database.
    public static int getCardId(int customerId) {
        int cardId = 0;
        try (PreparedStatement stat = con.prepareStatement("SELECT card_id FROM DATABASE.card WHERE customer_id = ?")) {

            stat.setInt(1, customerId);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    cardId = rs.getInt("card_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cardId;
    }
}
	

    


