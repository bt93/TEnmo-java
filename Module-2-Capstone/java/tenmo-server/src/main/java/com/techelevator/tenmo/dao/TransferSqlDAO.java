package com.techelevator.tenmo.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
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
		
		// Sets the transfer Id to be used elsewere in the program.
		newTransfer.setTransferId(getCurrentTransferId());
		
		return newTransfer;
	}
	
	public Transfer getTransferByTransferId(long transferId, long userId) {
		Transfer transfer = null;
		
		String sql = "SELECT t.*, tt.transfer_type_desc, ts.transfer_status_desc "
				+ "FROM transfers t "
				+ "JOIN transfer_types tt ON t.transfer_type_id = tt.transfer_type_id "
				+ "JOIN transfer_statuses ts ON t.transfer_status_id = ts.transfer_status_id "
				+ "WHERE t.transfer_id = ? "
				+ "AND t.account_from = ? "
				+ "OR t.account_to = ?;";
		
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transferId, userId, userId);
		
		if (result.next()) {
			transfer = mapRowToTransfer(result);
		}
		
		return transfer;
	}
	
	public List<Transfer> getAllTransfersByUser(long userId) {
		List<Transfer> transfers = new ArrayList<Transfer>();
		
		String sql = "SELECT t.*, tt.transfer_type_desc, ts.transfer_status_desc "
				+ "FROM transfers t "
				+ "JOIN transfer_types tt ON t.transfer_type_id = tt.transfer_type_id "
				+ "JOIN transfer_statuses ts ON t.transfer_status_id = ts.transfer_status_id "
				+ "WHERE t.account_from = ? "
				+ "OR t.account_to = ?;";
		
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId, userId);
		
		while (result.next()) {
			transfers.add(mapRowToTransfer(result));
		}
		
		return transfers;
	}
	
	// Gets the id for the inserted transfer
	private long getCurrentTransferId() {
		SqlRowSet currentIdResult = jdbcTemplate.queryForRowSet("SELECT currval('seq_transfer_id')");
		
		if (currentIdResult.next()) {
			return currentIdResult.getLong(1);
		} else {
			throw new RuntimeException("Something went wrong trying to get the id for the new transfer.");
		}
	}
	
	private Transfer mapRowToTransfer(SqlRowSet result) {
		Transfer transfer = new Transfer();
		
		transfer.setTransferId(result.getLong("transfer_id"));
		transfer.setTrasferTypeId(result.getLong("transfer_type_id"));
		transfer.setTransferType(result.getString("transfer_type_desc"));
		transfer.setTransferStatusId(result.getLong("transfer_status_id"));
		transfer.setTransferStatus(result.getString("transfer_status_desc"));
		transfer.setAccountFrom(result.getLong("account_from"));
		transfer.setAccountTo(result.getLong("account_to"));
		transfer.setAmount(result.getDouble("amount"));
		
		return transfer;
	}
}
