package com.techelevator.tenmo.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.model.Transfer;

@Component
public class TransferSqlDAO implements TransferDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public TransferSqlDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public Transfer createTransfer(Transfer newTransfer) {
		
		String sql = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount) "
				+ "VALUES (?, ?, ?, ?, ?)";
		
		jdbcTemplate.update(sql, newTransfer.getTrasferTypeId(),
								 newTransfer.getTransferStatusId(),
								 newTransfer.getAccountFrom(),
								 newTransfer.getAccountTo(),
								 newTransfer.getAmount());
		
		return newTransfer;
	}
}
