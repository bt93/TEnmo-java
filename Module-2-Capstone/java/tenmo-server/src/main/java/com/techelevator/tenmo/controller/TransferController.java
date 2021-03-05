package com.techelevator.tenmo.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;

@RestController
@RequestMapping("/transfer")
@PreAuthorize("isAuthenticated()")
public class TransferController {
	private AccountDAO accountDAO;
	private UserDAO userDAO;
	private TransferDAO transferDAO;
	
	public TransferController(AccountDAO accountDAO, UserDAO userDAO, TransferDAO transferDAO) {
		this.accountDAO = accountDAO;
		this.userDAO = userDAO;
		this.transferDAO = transferDAO;
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public Transfer getTransferById(@PathVariable long id, Principal userInfo) {
		long userId = userDAO.findIdByUsername(userInfo.getName());
		return transferDAO.getTransferByTransferId(id, userId);
	}
	
	@RequestMapping(path = "/all", method = RequestMethod.GET)
	public List<Transfer> getAllUserTransfers(Principal userInfo) {
		long userId = userDAO.findIdByUsername(userInfo.getName());
		return transferDAO.getAllTransfersByUser(userId);
	}
	
	@RequestMapping(path = "/send", method = RequestMethod.POST)
	public Transfer sendTransfer(@Valid @RequestBody Transfer transfer, Principal userInfo) {
		long currentUserId = userDAO.findIdByUsername(userInfo.getName());
		Account accountFrom = accountDAO.getAccountByUserId(currentUserId);
		Account accountTo = accountDAO.getAccountByUserId(transfer.getAccountTo());
		
		if (!accountFrom.equals(null) && !accountTo.equals(null)) {
			transfer.setAccountFrom(accountFrom.getAccountId());
			transfer.setAccountTo(accountTo.getAccountId());
			transfer.setTrasferTypeId(2);
			
			if (transfer.getAmount() > accountFrom.getBalance()) {
				transfer.setTransferStatusId(3);
				transferDAO.createTransfer(transfer);
			} else {
				transfer.setTransferStatusId(2);
				transferDAO.createTransfer(transfer);
				accountDAO.transferMoney(accountFrom, accountTo, transfer.getAmount());
			}
		}
		
		
		return transfer;
	}
}
