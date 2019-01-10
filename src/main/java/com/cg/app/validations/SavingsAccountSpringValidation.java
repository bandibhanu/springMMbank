package com.cg.app.validations;

import java.util.logging.Logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.cg.app.account.SavingsAccount;
import com.moneymoney.exception.InsufficientFundsException;

@Aspect
@Component
public class SavingsAccountSpringValidation {

	public Logger logger = Logger.getLogger(SavingsAccountSpringValidation.class.getName());

	@Around("execution(* com.cg.app.account.service.SavingsAccountServiceImpl.deposit(..))")
	public void servingsAccountDepositeValidation(ProceedingJoinPoint pjp) throws Throwable {
		logger.info("DepositeValidation");
		Object[] params = pjp.getArgs();
		SavingsAccount savingsAccount = (SavingsAccount) params[0];
		logger.info("Function Name is : " + pjp.getSignature());

		double amount = (Double) params[1];
		if (amount > 0 && savingsAccount != null) {
			pjp.proceed();
		} else if (savingsAccount == null) {
			logger.info("account number not existed");
		} else {
			logger.info("Deposit amount should be positive");
		}
	}

	@Around("executions(*com.cg.app.account.service.SavingsAccountServiceImpl.withdraw(..))")
	public void servingsAccountWithdrawValidation(ProceedingJoinPoint pjp) throws Throwable {

		Object[] params = pjp.getArgs();
		SavingsAccount savingsAccount = (SavingsAccount) params[0];
		if (savingsAccount != null) {
			double currentBalance = savingsAccount.getBankAccount().getAccountBalance();
			double amount = (Double) params[1];
			if (amount > 0 && currentBalance >= amount) {
				pjp.proceed();
			}

		}
	}
}
