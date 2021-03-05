package com.techelevator.tenmo.dao;

import java.util.List;

import com.techelevator.tenmo.model.Account;

public interface AccountDAO {

	/*
	 * Gets the current users account
	 * 
	 * Takes a long - userId
	 * Returns an Account
	 */
	public Account getAccountByUserId(long userId);
	
	/*
	 * Gets a list of all the accounts EXCEPT the current user account
	 * 
	 * Takes a long - userId
	 * Returns a list of Accounts
	 */
	public List<Account> getAccountsToTransfer(long userId);
	
	/*
	 * Updates both accounts for a transfer
	 * 
	 * Takes 2 Accounts: accountFrom and accountTo
	 * 				double: amount
	 * Returns nothing
	 */
	public void transferMoney(Account accountFrom, Account accountTo, double amount);
}
