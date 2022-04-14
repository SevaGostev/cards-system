package com.gmail.vmgostev.cardssystem.account.storage.impl;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import com.gmail.vmgostev.cardssystem.account.AccountInfo;
import com.gmail.vmgostev.cardssystem.account.storage.AccountStorage;
import com.gmail.vmgostev.cardssystem.account.storage.StorageException;

public class AccountStorageTrivial implements AccountStorage {
	
	private final Accounts accounts;
	
	
	
	public AccountStorageTrivial(Path serializedDataPath) {
		
		if(Files.isReadable(serializedDataPath)) {
			
			Accounts input;
			
			try(FileInputStream fileIn = new FileInputStream(serializedDataPath.toFile());
					ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
				input = (Accounts) objectIn.readObject();
				
			} catch (Throwable e) {
				throw new StorageException(e);
			}
			
			this.accounts = input;
		}
		else {
			this.accounts = new Accounts();
		}
	}
	
	@Override
	public AccountInfo getAccountByID(long id) {
		
		return accounts.accountIDs.get(Long.valueOf(id));
	}

	@Override
	public AccountInfo getAccountByEmail(String email) {
		
		return accounts.accountEmails.get(email);
	}

	@Override
	public long createAccount(AccountInfo newInfo) {
		
		AccountInfo info = new AccountInfo(newInfo, generateID());
		accounts.accountIDs.put(Long.valueOf(info.id()), info);
		accounts.accountEmails.put(info.email(), info);
		return info.id();
	}
	
	@Override
	public void updateAccount(AccountInfo newInfo) throws StorageException {
		
		Long ido = Long.valueOf(newInfo.id());
		
		if(!accounts.accountIDs.containsKey(ido)) {
			throw new StorageException("Tried to update account that doesn't exist");
		}
		
		accounts.accountIDs.put(ido, newInfo);
		accounts.accountEmails.put(newInfo.email(), newInfo);
	}
	
	private long generateID() {
		
		long newID = accounts.nextID++;
		return newID;
	}
	
	private static class Accounts implements Serializable {
		
		private long nextID;
		private final Map<Long, AccountInfo> accountIDs;
		private final Map<String, AccountInfo> accountEmails;
		
		private Accounts() {
			this.accountIDs = new HashMap<Long, AccountInfo>();
			this.accountEmails = new HashMap<String, AccountInfo>();
			this.nextID = 1L;
		}
	}

}
