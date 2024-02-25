package com.Exception;

@SuppressWarnings("serial")
public class InvalidWithdrawalAmountException extends Exception {
    public InvalidWithdrawalAmountException(String message) {
        super(message);
    }
}
