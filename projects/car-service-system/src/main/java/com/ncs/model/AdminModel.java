package com.ncs.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminModel {
	private String username;
	private String password;
	
	PreparedStatement pstmt;
	Connection con;
	ResultSet res;
	
	public AdminModel(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	public AdminModel() {
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int login(String username, String password) {
		try {
			if(con == null || con.isClosed()) {
				DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample_ncs_db", "root", "root");
			}
			
			String s = "select * from admin_database where user_name=?";
			
			pstmt = con.prepareStatement(s);
			pstmt.setString(1, username);
			
			res = pstmt.executeQuery();
			if(res.next()) {
				this.username = res.getString(1);
				if(res.getString(2).equals(password)) {
					return 1;
				}
				else {
					return -3;
				}
			}
			else {
				return -2;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
