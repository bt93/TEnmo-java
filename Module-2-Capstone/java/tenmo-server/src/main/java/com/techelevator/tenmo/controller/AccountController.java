package com.techelevator.tenmo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.Account;

@RestController
@RequestMapping("/account")
@PreAuthorize("isAuthenticated()")
public class AccountController {

	private AccountDAO accountDAO;
	private UserDAO userDAO;
	
	public AccountController(AccountDAO accountDAO, UserDAO userDAO) {
		this.accountDAO = accountDAO;
		this.userDAO = userDAO;
	}
	
	@RequestMapping(path = "/balance", method = RequestMethod.GET)
	public Account getCurrentUserAccount(Principal userInfo) {
		long userId = userDAO.findIdByUsername(userInfo.getName());
		
		return accountDAO.getAccountByUserId(userId);
	}
	
	@RequestMapping(path = "/all", method = RequestMethod.GET)
	public List<Account> getAllAccountsToTransfer(Principal userInfo) {
		long userId = userDAO.findIdByUsername(userInfo.getName());
		
		return accountDAO.getAccountsToTransfer(userId);
	}
}
