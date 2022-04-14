package com.gmail.vmgostev.cardssystem.account;

public record AccountInfo(
		long id,
		String name,
		String email,
		boolean isActivated)
{
	public AccountInfo(AccountInfo source, long newID) {
		this(newID,
				source.name,
				source.email,
				source.isActivated);
	}
	
	public AccountInfo(AccountInfo source, boolean isActivated) {
		this(source.id,
				source.name,
				source.email,
				isActivated);
	}
}
