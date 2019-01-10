package com.cg.app.validations;

import java.util.logging.Logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.moneymoney.exception.InsufficientFundsException;

@Aspect
@Component
public class SavingsAccountSpringValidation {

	public Logger logger = Logger.getLogger(SavingsAccountSpringValidation.class.getName());

	@Around("execution(* com.cg.app.account.service.SavingsAccountServiceImpl.deposit(..))")
	public void servingsAccountDepositeValidation(ProceedingJoinPoint pjp) throws Throwable {
		logger.info("6534534");
		logger.info("Function Name is : " + pjp.getSignature());
		Object[] params = pjp.getArgs();
		System.out.println("dtfgdfgdf");
		double amount = (Double) params[0];
		if (amount > 0) {
			pjp.proceed();
		} else {
			throw new InsufficientFundsException("Invalid Input or Insufficinet funds!!!");
		}

	}

}
