package com.techelevator.tenmo.model;

public class Transfer {
	private long transferId;
	private long trasferTypeId;
	private String transferType;
	private long transferStatusId;
	private String transferStatus;
	private long accountFrom;
	private long accountTo;
	private double amount;
	
	public long getTransferId() {
		return transferId;
	}
	public void setTransferId(long transferId) {
		this.transferId = transferId;
	}
	public long getTrasferTypeId() {
		return trasferTypeId;
	}
	public void setTrasferTypeId(long trasferTypeId) {
		this.trasferTypeId = trasferTypeId;
	}
	public String getTransferType() {
		return transferType;
	}
	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}
	public long getTransferStatusId() {
		return transferStatusId;
	}
	public void setTransferStatusId(long transferStatusId) {
		this.transferStatusId = transferStatusId;
	}
	public String getTransferStatus() {
		return transferStatus;
	}
	public void setTransferStatus(String transferStatus) {
		this.transferStatus = transferStatus;
	}
	public long getAccountFrom() {
		return accountFrom;
	}
	public void setAccountFrom(long accountFrom) {
		this.accountFrom = accountFrom;
	}
	public long getAccountTo() {
		return accountTo;
	}
	public void setAccountTo(long accountTo) {
		this.accountTo = accountTo;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}

	
}
