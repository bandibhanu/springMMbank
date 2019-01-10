package com.cg.app.account.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


import com.cg.app.account.SavingsAccount;
import com.cg.app.account.SavingsAccountMapper;
import com.moneymoney.exception.AccountNotFoundException;

@Repository
@Primary
public class SavingsAccountJDBCDAO implements SavingsAccountDAO {
	
	@Autowired
	private JdbcTemplate template;

	@Override
	public SavingsAccount createNewAccount(SavingsAccount account) throws ClassNotFoundException, SQLException {
		template.update("INSERT INTO account VALUES(?,?,?,?,?,?)",new Object[] {account.getBankAccount().getAccountNumber(), 
				account.getBankAccount().getAccountHolderName(),account.getBankAccount().getAccountBalance(),
				account.isSalary(),null,"SA"});
		return account;
		
	}

	@Override
	public SavingsAccount getAccountById(int accountNumber)throws ClassNotFoundException, SQLException, AccountNotFoundException {
		SavingsAccount savingsAccount=template.queryForObject("SELECT * FROM account where account_id=?",
				new Object[] {accountNumber}, new SavingsAccountMapper());		
		return savingsAccount;	
	}

	@Override
	public SavingsAccount getAccountByName(String accountHolderName) throws ClassNotFoundException, SQLException {
		
		SavingsAccount savingsAccount = template.queryForObject("SELECT * FROM account where account_hn=?",new Object[] {accountHolderName}, new SavingsAccountMapper());
		return savingsAccount;
	
	}

	@Override
	public SavingsAccount deleteAccount(int accountNumber) throws ClassNotFoundException, SQLException {
		template.update("DELETE FROM ACCOUNT  where account_id=?",new Object[] {accountNumber});
		return null;
		
	}

	@Override
	public List<SavingsAccount> getAllSavingsAccount() throws ClassNotFoundException, SQLException {
	
	return template.query("SELECT * FROM account",new SavingsAccountMapper());
		 
	}

	@Override
	public void commit() throws SQLException {
		
		
	}

	@Override
	public double checkCurrentBalance(int accountNumber)
			throws ClassNotFoundException, SQLException, AccountNotFoundException {
		SavingsAccount account= template.queryForObject("SELECT * FROM account where account_id=?",new Object[] {accountNumber},new SavingsAccountMapper());
		return account.getBankAccount().getAccountBalance();
		 
	}

	@Override
	public void updateBalance(int accountNumber, double currentBalance) throws ClassNotFoundException, SQLException {
		template.update("UPDATE ACCOUNT SET account_bal=? where account_id=?",new Object[] {currentBalance,accountNumber});
		
	}

	@Override
	public List<SavingsAccount> sortByname() throws ClassNotFoundException, SQLException{
	  return template.query("SELECT * FROM account order By account_hn",new Object[] {},new SavingsAccountMapper());
		 
	
		
	}

	@Override
	public List<SavingsAccount> sortByBalance() throws ClassNotFoundException, SQLException {
		
	 return template.query("SELECT * FROM account order By account_hn",new Object[] {},new SavingsAccountMapper());
	}


	@Override
	public SavingsAccount getAccountByName(int accountHolderName) throws ClassNotFoundException, SQLException {
		SavingsAccount savingsAccount=template.queryForObject("SELECT * FROM account where account_hn=?",
				new Object[] {accountHolderName}, new SavingsAccountMapper());		
		return savingsAccount;	
		

	}

	@Override
	public SavingsAccount updateAccount(SavingsAccount account) throws ClassNotFoundException, SQLException {
		template.update("UPDATE account set account_id=?,account_hn=?,account_bal=?,salary=?,od_limit=?,account_type=? where account_id=?",new Object[] { account.getBankAccount().getAccountNumber(),account.getBankAccount().getAccountHolderName(),
				account.getBankAccount().getAccountBalance(),account.isSalary(),null,"SA",account.getBankAccount().getAccountNumber()});
	
		return account;
	}

	@Override
	public SavingsAccount serachAccount(int accountNumber) throws SQLException, ClassNotFoundException {
		
		SavingsAccount savingsAccount=template.queryForObject("SELECT * from account where account_id=?", new Object[]{accountNumber},new SavingsAccountMapper());
		 return savingsAccount;
	
	}

}
