package com.techelevator.tenmo.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.model.Account;

@Component
public class AccountSqlDAO implements AccountDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public AccountSqlDAO(JdbcTemplate template) {
		this.jdbcTemplate = template;
	}
	
	public Account getAccountByUserId(long userId) {
		Account userAccount = null;
		
		String sql = "SELECT accounts.*, users.username "
				+ "FROM accounts "
				+ "JOIN users ON accounts.user_id = users.user_id "
				+ "WHERE accounts.user_id = ?;";
		
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
		
		if (result.next()) {
			userAccount = mapRowToAccount(result);
		}
		
		return userAccount;
	}
	
	public List<Account> getAccountsToTransfer(long userId) {
		List<Account> accounts = new ArrayList<Account>();
		
		String sql = "SELECT accounts.*, users.username "
				+ "FROM accounts "
				+ "JOIN users ON accounts.user_id = users.user_id "
				+ "WHERE accounts.user_id != ?;";
		
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
		
		while (result.next()) {
			accounts.add(mapRowToAccount(result));
		}
		
		return accounts;
	}

	private Account mapRowToAccount(SqlRowSet result) {
		Account accountToMap = new Account();
		
		accountToMap.setAccountId(result.getInt("account_id"));
		accountToMap.setUserId(result.getInt("user_id"));
		accountToMap.setBalance(result.getDouble("balance"));
		accountToMap.setUserName(result.getString("username"));
		
		return accountToMap;
	}
	
	
	
}
