package com.cg.app.account.ui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cg.app.account.SavingsAccount;
import com.cg.app.account.service.SavingsAccountService;
import com.cg.app.account.service.SavingsAccountServiceImpl;
import com.cg.app.account.util.DBUtil;
import com.moneymoney.exception.AccountNotFoundException;

@Component
public class AccountCUI {
	private static Scanner scanner = new Scanner(System.in);
	@Autowired
	private SavingsAccountService savingsAccountService;
	public void start() throws ClassNotFoundException, SQLException,
			AccountNotFoundException {

		do {
			System.out.println("****** Welcome to Money Money Bank********");
			System.out.println("1. Open New Savings Account");
			System.out.println("2. Update Account");
			System.out.println("3. Close Account");
			System.out.println("4. Search Account");
			System.out.println("5. Withdraw");
			System.out.println("6. Deposit");
			System.out.println("7. FundTransfer");
			System.out.println("8. Check Current Balance");
			System.out.println("9. Get All Savings Account Details");
			System.out.println("10. Sort Accounts");
			System.out.println("11. Exit");
			System.out.println();
			System.out.println("Make your choice: ");

			int choice = scanner.nextInt();

			performOperation(choice);

		} while (true);
	}

	private void performOperation(int choice)
			throws ClassNotFoundException, SQLException,
			AccountNotFoundException {
		switch (choice) {
		case 1:

			acceptInput("SA");
			break;

		case 2:
			updateAccount();
			break;

		case 3:
			closeAccount();
			break;

		case 4:

			searchAccount();
			break;

		case 5:
			withdraw();
			break;

		case 6:
			deposit();
			break;

		case 7:
			fundTransfer();
			break;

		case 8:
			try {
				checkCurrentBalance();
			} catch (AccountNotFoundException e1) {

				e1.printStackTrace();
			}
			break;

		case 9:
			showAllAccounts();
			break;

		case 10:
			sortAccount();
			break;

		case 11:
			try {
				DBUtil.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.exit(0);
			break;
		default:
			System.err.println("Invalid Choice!");
			break;
		}

	}

	private void updateAccount() throws ClassNotFoundException,
			SQLException, AccountNotFoundException {
		System.out.println("Enter the account number to update: ");
		int accountNumber = scanner.nextInt();
		SavingsAccount savingAccount = null;
		try {
			savingAccount = savingsAccountService.getAccountById(accountNumber);
		} catch (ClassNotFoundException | SQLException

		| AccountNotFoundException e) {
			e.printStackTrace();
		}
		do {

			System.out.println("Enter which field to update: ");
			System.out.println("1. Account holder name.");
			System.out.println("2. Salary Field.");
			
			
			System.out.println("Make Your choioce: ");

			int choice = scanner.nextInt();
			updateField(savingAccount, choice);
		} while (true);
	}

	private void updateField(SavingsAccount savingAccount, int choice)
			throws ClassNotFoundException, SQLException,
			AccountNotFoundException {

		switch (choice) {

		case 1:
			System.out.println("Enter the name to update: ");
			String updatedName = scanner.next();
			savingAccount.getBankAccount().setAccountHolderName(updatedName);
			try {
				savingAccount = savingsAccountService
						.updateAccount(savingAccount);
				System.out.println(savingAccount.toString());
				break;
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		case 2:
			System.out.println("Change the salary field(true/false)");
			boolean updatedSalaryField = scanner.nextBoolean();
			savingAccount.setSalary(updatedSalaryField);
			try {
				savingAccount = savingsAccountService
						.updateAccount(savingAccount);
				System.out.println(savingAccount.toString());
				break;
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}

		case 3:
			start();
			break;

		default:
			System.out.println("Invalid Choice.");
		}

	}

	private void searchAccount() throws ClassNotFoundException,
			SQLException, AccountNotFoundException {
		System.out.println("1.Enter for search by Id");
		System.out.println("2.Enter for search by name");
		int searchType = scanner.nextInt();
		switch (searchType) {
		case 1:
			System.out.println("Enter the account_Id");
			int account_id = scanner.nextInt();
			SavingsAccount getAccountById;
			getAccountById = savingsAccountService.getAccountById(account_id);
			System.out.println(getAccountById);
			break;
		case 2:
			System.out.println("Enter the account_hn");
			String account_hn = scanner.next();
			SavingsAccount getAccountByHn;
			getAccountByHn = savingsAccountService.getAccountByName(account_hn);
			System.out.println(getAccountByHn);
			break;

		}

	}

	private void sortAccount() throws ClassNotFoundException,
			SQLException {

		List<SavingsAccount> savingsAccountList = new ArrayList<SavingsAccount>();
		System.out.println("enter 1 for sort by name");
		System.out.println("enter 2 for sort by balance");

		int orderType = scanner.nextInt();
		switch (orderType) {
		case 1:
			try {
				savingsAccountList = savingsAccountService.sortByname();
				for (SavingsAccount savingsAccount : savingsAccountList) {
					System.out.println(savingsAccount);
				}
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 2:
			savingsAccountList = savingsAccountService.sortByBalance();
			for (SavingsAccount savingsAccount : savingsAccountList) {
				System.out.println(savingsAccount);
			}
			break;

		}
	}

	private void checkCurrentBalance() throws ClassNotFoundException,
			SQLException, AccountNotFoundException {
		System.out.println("Enter the account number");
		int account_id = scanner.nextInt();
		double accountBalance = savingsAccountService
				.checkCurrentBalance(account_id);
		System.out.println(accountBalance);
	}

	private void closeAccount() throws ClassNotFoundException,
			SQLException {

		System.out.println("Enter the accout number");
		int account_id = scanner.nextInt();
		savingsAccountService.deleteAccount(account_id);
	}

	private void fundTransfer() throws SQLException {
		System.out.println("Enter Account Sender's Number: ");
		int senderAccountNumber = scanner.nextInt();
		System.out.println("Enter Account Receiver's Number: ");
		int receiverAccountNumber = scanner.nextInt();
		System.out.println("Enter Amount: ");
		double amount = scanner.nextDouble();
		try {
			SavingsAccount senderSavingsAccount = savingsAccountService
					.getAccountById(senderAccountNumber);
			SavingsAccount receiverSavingsAccount = savingsAccountService
					.getAccountById(receiverAccountNumber);
			savingsAccountService.fundTransfer(senderSavingsAccount,
					receiverSavingsAccount, amount);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		DBUtil.commit();
	}
	
	private void deposit() {
		System.out.println("Enter Account Number: ");
		int accountNumber = scanner.nextInt();
		System.out.println("Enter Amount: ");
		double amount = scanner.nextDouble();
		SavingsAccount savingsAccount = null;
		try {
			savingsAccount = savingsAccountService
					.getAccountById(accountNumber);
			savingsAccountService.deposit(savingsAccount, amount);
			DBUtil.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			try {
				DBUtil.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			try {
				DBUtil.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	private void withdraw() {
		System.out.println("Enter Account Number: ");
		int accountNumber = scanner.nextInt();
		System.out.println("Enter Amount: ");
		double amount = scanner.nextDouble();
		SavingsAccount savingsAccount = null;
		try {
			savingsAccount = savingsAccountService
					.getAccountById(accountNumber);
			savingsAccountService.withdraw(savingsAccount, amount);
			DBUtil.commit();
		} catch (ClassNotFoundException | SQLException
				| AccountNotFoundException e) {
			try {
				DBUtil.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (Exception e) {
			try {
				DBUtil.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	private void sortMenu(String sortWay) {
		do {
			System.out.println("+++++Ways of Sorting+++++++");
			System.out.println("1. Account Number");
			System.out.println("2. Account Holder Name");
			System.out.println("3. Account Balance");
			System.out.println("4. Exit from Sorting");

			int choice = scanner.nextInt();

		} while (true);

	}

	private void showAllAccounts() {
		List<SavingsAccount> savingsAccounts;
		try {
			savingsAccounts = savingsAccountService.getAllSavingsAccount();
			for (SavingsAccount savingsAccount : savingsAccounts) {
				System.out.println(savingsAccount);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

	private void acceptInput(String type) {
		if (type.equalsIgnoreCase("SA")) {
			System.out.println("Enter your Full Name: ");
			String accountHolderName = scanner.nextLine();
			accountHolderName = scanner.nextLine();
			System.out
					.println("Enter Initial Balance(type na for Zero Balance): ");
			String accountBalanceStr = scanner.next();
			double accountBalance = 0.0;
			if (!accountBalanceStr.equalsIgnoreCase("na")) {
				accountBalance = Double.parseDouble(accountBalanceStr);
			}
			System.out.println("Salaried?(y/n): ");
			boolean salary = scanner.next().equalsIgnoreCase("n") ? false
					: true;
			createSavingsAccount(accountHolderName, accountBalance, salary);
		}
	}

	private void createSavingsAccount(String accountHolderName,
			double accountBalance, boolean salary) {
		try {
			savingsAccountService.createNewAccount(accountHolderName,
					accountBalance, salary);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
