package com.ncs.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Model {
	private String name;
	private String username;
	private String password;
	private String email;
	public int customer_id;
	
	PreparedStatement pstmt;
	Connection con;
	ResultSet res;
	
	static Connection conStat;
	static ResultSet resStat;
	static PreparedStatement pstmtStat;
	
	public Model(String name, String username, String password, String email) {
		super();
		this.name = name;
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	public Model() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public int register() {
		try {
			if(con == null || con.isClosed()) {
				DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample_ncs_db", "root", "root");
			}
						
			String v = "select * from customer_database where user_name=?";
			
			pstmt = con.prepareStatement(v);
			pstmt.setString(1, username);
			
			res = pstmt.executeQuery();
			
			if(res.next()) {
				return -1;
			}
			else {
				String s = "insert into customer_database (name, user_name, password, email) values (?,?,?,?)";
				pstmt = con.prepareStatement(s);
				pstmt.setString(1, name);
				pstmt.setString(2, username);
				pstmt.setString(3, password);
				pstmt.setString(4, email);
				
				int rows = pstmt.executeUpdate();
				return rows;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public Integer login(String username, String password) {
		try {
			if(con == null || con.isClosed()) {
				DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample_ncs_db", "root", "root");
			}
			
			String s = "select * from customer_database where user_name=?";
			
			pstmt = con.prepareStatement(s);
			pstmt.setString(1, username);
			
			res = pstmt.executeQuery();
			if(res.next()) {
				this.name = res.getString(2);
				this.email = res.getString(5);
				if(res.getString(4).equals(password)) {
					return Integer.parseInt(res.getString(1));
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
	
	public int resetPassword(int user_id, String oldPassword, String newPassword, String cfmPassword) {
		try {
			if(con == null || con.isClosed()) {
				DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample_ncs_db", "root", "root");
			}
			
			String s = "select * from customer_database where customer_id=?";
			
			pstmt = con.prepareStatement(s);
			pstmt.setInt(1, user_id);
			
			res = pstmt.executeQuery();
			if(res.next()) {
				this.name = res.getString(2);
				this.email = res.getString(5);
				if(res.getString(4).equals(oldPassword)) {
					String s1 = "update customer_database set password=? where customer_id=?";
					pstmt = con.prepareStatement(s1);
					pstmt.setString(1, newPassword);
					pstmt.setInt(2, user_id);
					return pstmt.executeUpdate();
				}
				else {
					System.out.println("password mismatched.");
					return -3;
				}
			}
			else {
				System.out.println("Cannot find user " + username);
				return -2;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("Unknown error when resetting password.");
		return -1;
	}
	
	public boolean userEmailExists(String emailInput) {
		try {
			if(con == null || con.isClosed()) {
				DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample_ncs_db", "root", "root");
			}
			String s = "select email from customer_database where email=?";
			pstmt = con.prepareStatement(s);
			pstmt.setString(1, emailInput);
			res = pstmt.executeQuery();
			return res.next();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void viewCustomer(String username) {
		try {
			if(con == null || con.isClosed()) {
				DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample_ncs_db", "root", "root");
			}
			
			String s = "select * from customer_database where user_name=?";
			
			pstmt = con.prepareStatement(s);
			pstmt.setString(1, username);
			
			res = pstmt.executeQuery();
			if(res.next()) {
				this.customer_id = res.getInt(1);
				this.name = res.getString(2);
				this.username = res.getString(3);
				this.email = res.getString(5);
			}
			else {
				System.out.println("Cannot find user " + username);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void viewCustomer(int user_id) {
		try {
			if(con == null || con.isClosed()) {
				DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample_ncs_db", "root", "root");
			}
			
			String s = "select * from customer_database where customer_id=?";
			
			pstmt = con.prepareStatement(s);
			pstmt.setInt(1, user_id);
			
			res = pstmt.executeQuery();
			if(res.next()) {
				this.customer_id = user_id;
				this.name = res.getString(2);
				this.username = res.getString(3);
				this.email = res.getString(5);
			}
			else {
				System.out.println("Cannot find user " + username);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean usernameExists(String un) {
		try {
			if(con == null || con.isClosed()) {
				DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample_ncs_db", "root", "root");
			}
			String s = "select * from customer_database where user_name=?";
			pstmt = con.prepareStatement(s);
			pstmt.setString(1, un);
			res = pstmt.executeQuery();
			return res.next();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public int updateDetails(String oldUsername) {
		try {
			if(con == null || con.isClosed()) {
				DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample_ncs_db", "root", "root");
			}
			if(!oldUsername.equals(username) && usernameExists(username)) {
				return -2;
			}
			
			String s = "update customer_database set user_name=?, name=?, email=? where user_name=?";
			
			pstmt = con.prepareStatement(s);
			pstmt.setString(1, username);
			pstmt.setString(2, name);
			pstmt.setString(3, email);
			pstmt.setString(4, oldUsername);
			int outcome1 = pstmt.executeUpdate();
			
			String s2 = "update customer_car_db set user_name=? where user_name=?";
			pstmt = con.prepareStatement(s2);
			pstmt.setString(1, username);
			pstmt.setString(2, oldUsername);
			int outcome2 = pstmt.executeUpdate();
			return outcome1 + outcome2;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public void deleteAccount() {
		
		try {
			if(con == null || con.isClosed()) {
				DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample_ncs_db", "root", "root");
			}
			// delete account from personal details
			
			String s1 = "delete from customer_database where user_name=?";
			
			pstmt = con.prepareStatement(s1);
			pstmt.setString(1, username);
			
			int outcome1 = pstmt.executeUpdate();
			
			// delete car from car database
			String s2 = "delete from customer_car_db where user_name=?";
			
			pstmt = con.prepareStatement(s2);
			pstmt.setString(1, username);
			
			int outcome2 = pstmt.executeUpdate();
			return;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Model> viewAllCustomers(){
		try {
			if(conStat == null || conStat.isClosed()) {
				DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
				conStat = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample_ncs_db", "root", "root");
			}
			
			// Select all accounts in customer database
			
			String s = "select * from customer_database";
			
			pstmtStat = conStat.prepareStatement(s);
			resStat = pstmtStat.executeQuery();
			ArrayList<Model> customersArray = new ArrayList<Model>();
			while(resStat.next()) {
				String name = resStat.getString(2);
				String username = resStat.getString(3);
				String email = resStat.getString(5);
				Model customer = new Model(name, username, "", email);
				customer.customer_id = resStat.getInt(1);
				customersArray.add(customer);
			}
			return customersArray;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
