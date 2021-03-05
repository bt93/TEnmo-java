package com.techelevator.tenmo.dao;

import java.util.List;

import com.techelevator.tenmo.model.Transfer;

public interface TransferDAO {
	/*
	 * Creates a new transfer
	 * 
	 * Takes Transfer
	 * Retruns Transfer with ID
	 */
	public Transfer createTransfer(Transfer transfer);
	
	/*
	 * Gets a transfer by its id by the current user ID
	 * 
	 * Takes transferId and the userID
	 * Returns a transfer
	 */
	public Transfer getTransferByTransferId(long transferId, long userId);
	
	/*
	 * Gets a list of all the users transfers
	 * Takes the userId
	 * Returns a list of Transfers
	 */
	public List<Transfer> getAllTransfersByUser(long userId);
}
