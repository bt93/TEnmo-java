package com.techelevator.tenmo.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.Account;

/*******************************************************************************************************
 * This is where you code Application Services required by your solution
 * 
 * Remember:  theApp ==> ApplicationServices  ==>  Controller  ==>  DAO
********************************************************************************************************/

public class TenmoApplicationServices {
	
	private RestTemplate restTemplate = new RestTemplate();
	private static final String API_BASE_URL = "http://localhost:8080/";
	
	public double getCurrentUsersBalance(String authToken) {
		HttpEntity entity = new HttpEntity<>(authHeaders(authToken));
		ResponseEntity<Account> response = restTemplate.exchange(API_BASE_URL + "account/balance", HttpMethod.GET, entity, Account.class);
		return response.getBody().getBalance();
	}
	
	public List<Account> getAllAccountsToTransfer(String authToken) {
		HttpEntity entity = new HttpEntity<>(authHeaders(authToken));
		ResponseEntity<Account[]> response = restTemplate.exchange(API_BASE_URL + "account/all", HttpMethod.GET, entity, Account[].class);
		return Arrays.asList(response.getBody());
	}

	private HttpHeaders authHeaders(String authToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(authToken);
		
		return headers;
	}
}
