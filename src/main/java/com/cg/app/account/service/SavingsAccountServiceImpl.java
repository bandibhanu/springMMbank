package com.cg.app.account.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.app.account.SavingsAccount;
import com.cg.app.account.dao.SavingsAccountDAO;
import com.cg.app.account.dao.SavingsAccountDAOImpl;
import com.cg.app.account.factory.AccountFactory;
import com.cg.app.account.service.SavingsAccountService;
import com.cg.app.account.util.DBUtil;
import com.moneymoney.exception.AccountNotFoundException;
import com.moneymoney.exception.InsufficientFundsException;
import com.moneymoney.exception.InvalidInputException;
@Service
public class SavingsAccountServiceImpl implements SavingsAccountService {

	private AccountFactory factory;
	@Autowired
	private SavingsAccountDAO savingsAccountDAO;

	public SavingsAccountServiceImpl( SavingsAccountDAO savingsAccountDAO) {
		factory = AccountFactory.getInstance();
		//savingsAccountDAO = new SavingsAccountDAOImpl();
		this.savingsAccountDAO = savingsAccountDAO;
	}

	
	public SavingsAccount createNewAccount(String accountHolderName, double accountBalance, boolean salary)
			throws ClassNotFoundException, SQLException {
		SavingsAccount account = factory.createNewSavingsAccount(accountHolderName, accountBalance, salary);
		savingsAccountDAO.createNewAccount(account);
		return null;
	}
	
	
	public List<SavingsAccount> getAllSavingsAccount() throws ClassNotFoundException, SQLException {
		return savingsAccountDAO.getAllSavingsAccount();
	}

	
	public void deposit(SavingsAccount account, double amount) throws ClassNotFoundException, SQLException {
		
			double currentBalance = account.getBankAccount().getAccountBalance();
			currentBalance += amount;
			savingsAccountDAO.updateBalance(account.getBankAccount().getAccountNumber(), currentBalance);
			//savingsAccountDAO.commit();
		
	}
	
	public void withdraw(SavingsAccount account, double amount) throws ClassNotFoundException, SQLException {
		double currentBalance = account.getBankAccount().getAccountBalance();
		if (amount > 0 && currentBalance >= amount) {
			currentBalance -= amount;
			savingsAccountDAO.updateBalance(account.getBankAccount().getAccountNumber(), currentBalance);
			//savingsAccountDAO.commit();
		} else {
			throw new InsufficientFundsException("Invalid Input or Insufficient Funds!");
		}
	}

	@Transactional
	public void fundTransfer(SavingsAccount sender, SavingsAccount receiver, double amount)
			throws ClassNotFoundException, SQLException {
		
			withdraw(sender, amount);
			deposit(receiver, amount);
			
		
	}

	
	public SavingsAccount getAccountById(int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException {
		return savingsAccountDAO.getAccountById(accountNumber);
	}
	
	
	public SavingsAccount getAccountByName(String accountHolderName) throws  SQLException 
	{
		try {
			return savingsAccountDAO.getAccountByName(accountHolderName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public SavingsAccount deleteAccount(int account_id) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return savingsAccountDAO.deleteAccount(account_id);
	}
	
	public double checkCurrentBalance(int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException

	{
		return savingsAccountDAO.checkCurrentBalance(accountNumber);
	}

	
	public List<SavingsAccount> sortByname() throws ClassNotFoundException, SQLException {
		return savingsAccountDAO.sortByname();
		
	}

	
	public List<SavingsAccount> sortByBalance() throws ClassNotFoundException, SQLException {
		
		 return savingsAccountDAO.sortByBalance();
	}
	

	public SavingsAccount updateAccount(SavingsAccount account) throws ClassNotFoundException, SQLException {

		return savingsAccountDAO.updateAccount(account);

	}


}
