package com.business.bank;

import junit.framework.TestCase;

public class AccountJUnit3Test extends TestCase {
	
	AccountJUnit3 account;

	protected void setUp() throws Exception {
		account = new AccountJUnit3(10000);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testAccountJUnit3() {
	}

	public void testGetBalance() {
		assertEquals(10000, account.getBalance());
	}

	public void testWithdraw() {
		account.withdraw(1000);
		assertEquals(9000, account.getBalance());
	}

	public void testDeposit() {
		account.deposit(1000);
		assertEquals(11000, account.getBalance());
	}

}
