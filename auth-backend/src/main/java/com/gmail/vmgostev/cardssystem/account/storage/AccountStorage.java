package com.gmail.vmgostev.cardssystem.account.storage;

import com.gmail.vmgostev.cardssystem.account.AccountInfo;

public interface AccountStorage {

	AccountInfo getAccountByID(long id);
	
	AccountInfo getAccountByEmail(String email);
	
	long createAccount(String name, String email);
	
	void updateAccount(AccountInfo newInfo);
}
