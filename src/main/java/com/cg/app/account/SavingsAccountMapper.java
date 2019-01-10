package com.cg.app.account;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class SavingsAccountMapper implements RowMapper<SavingsAccount> {

	@Override
	public SavingsAccount mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		return new SavingsAccount(rs.getInt("account_id"), rs.getString("account_hn"),
				rs.getDouble("account_bal"), rs.getBoolean("salary"));
		 
	}

}
