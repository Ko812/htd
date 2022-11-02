package com.ncs.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CarModel {
	private int user_id;
	
	public CarModel(int user_id, String username, String carModel, String carType, String carRegistrationNumber,
			String serviceRequest, String serviceStatus) {
		super();
		this.user_id = user_id;
		this.username = username;
		this.carModel = carModel;
		this.carType = carType;
		this.carRegistrationNumber = carRegistrationNumber;
		this.serviceRequest = serviceRequest;
		this.serviceStatus = serviceStatus;
	}

	public String getServiceRequest() {
		return serviceRequest;
	}

	public void setServiceRequest(String serviceRequest) {
		this.serviceRequest = serviceRequest;
	}

	public String getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	private String username;
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	private String carModel;
	private String carType;
	private String carRegistrationNumber;
	private String serviceRequest;
	private String serviceStatus;
	
	public CarModel(int user_id, String carModel, String carType, String carRegistrationNumber,
			String serviceRequest, String serviceStatus) {
		super();
		this.user_id = user_id;
		this.carModel = carModel;
		this.carType = carType;
		this.carRegistrationNumber = carRegistrationNumber;
		this.serviceRequest = serviceRequest;
		this.serviceStatus = serviceStatus;
	}

	Connection con;
	ResultSet res;
	PreparedStatement pstmt;
	
	static Connection conStat;
	static ResultSet resStat;
	static PreparedStatement pstmtStat;
	
	public int getUserId() {
		return this.user_id;
	}

	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public String getCarRegistrationNumber() {
		return carRegistrationNumber;
	}

	public void setCarRegistrationNumber(String carRegistrationNumber) {
		this.carRegistrationNumber = carRegistrationNumber;
	}
	
	public CarModel() {
		
	}
	
	public String getCustomerUsername(int customer_id) {
		try {
			if(con == null || con.isClosed()) {
				DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample_ncs_db", "root", "root");
			}
			String s = "select user_name from customer_database where customer_id=?";
			pstmt = con.prepareStatement(s);
			pstmt.setInt(1, customer_id);
			res = pstmt.executeQuery();
			if(res.next()) {
				return res.getString(1);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int addCarDetails() {
		try {
			if(con == null || con.isClosed()) {
				DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample_ncs_db", "root", "root");
			}
			String username = getCustomerUsername(this.user_id);
			
			String q = "select * from customer_car_db where reg_num=?";
			pstmt = con.prepareStatement(q);
			pstmt.setString(1, carRegistrationNumber);
			res = pstmt.executeQuery();
			if(res.next()) {
				return -2;
			}
			else {
				String s = "insert into customer_car_db (user_name, model, type, reg_num, service_request, service_status, customer_id) values (?, ?, ?, ?, ?, ?, ?)";
				
				pstmt = con.prepareStatement(s);
				pstmt.setString(1, username);
				pstmt.setString(2, carModel);
				pstmt.setString(3, carType);
				pstmt.setString(4, carRegistrationNumber);
				pstmt.setString(5, serviceRequest);
				pstmt.setString(6, serviceStatus);
				pstmt.setInt(7, user_id);
				
				int outcome = pstmt.executeUpdate();
				return outcome;
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public int updateCarDetails(String originalCarRegistrationNumber) {
		try {
			if(con == null || con.isClosed()) {
				DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample_ncs_db", "root", "root");
			}
			
			String s = "update customer_car_db set model=?, type=?, reg_num=? where customer_id=? AND reg_num=?";
			
			pstmt = con.prepareStatement(s);
			pstmt.setString(1, carModel);
			pstmt.setString(2, carType);
			pstmt.setString(3, carRegistrationNumber);
			pstmt.setInt(4, user_id);
			pstmt.setString(5, originalCarRegistrationNumber);
			
			int outcome = pstmt.executeUpdate();
			return outcome;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public int requestService() {
		try {
			if(con == null || con.isClosed()) {
				DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample_ncs_db", "root", "root");
			}
			String q = "select service_status from customer_car_db where customer_id=? AND reg_num=?";
			pstmt = con.prepareStatement(q);
			pstmt.setInt(1, user_id);
			pstmt.setString(2, carRegistrationNumber);
			res = pstmt.executeQuery();
			if(res.next() && !res.getString(1).equals("false")) {
				return -2;
			}
			else {
				String s = "update customer_car_db set service_request=?, service_status=? where customer_id=? AND reg_num=?";
				
				pstmt = con.prepareStatement(s);
				pstmt.setString(1, serviceRequest);
				pstmt.setString(2, serviceStatus);
				pstmt.setInt(3, user_id);
				pstmt.setString(4, carRegistrationNumber);
				
				int outcome = pstmt.executeUpdate();
				return outcome;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public int updateServiceStatus() {
		try {
			if(con == null || con.isClosed()) {
				DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample_ncs_db", "root", "root");
			}
			String s = "update customer_car_db set service_status=? where customer_id=? AND reg_num=?";
			pstmt = con.prepareStatement(s);
			pstmt.setString(1, serviceStatus);
			pstmt.setInt(2, user_id);
			pstmt.setString(3, carRegistrationNumber);
			return pstmt.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public ArrayList<CarModel> viewCarDetails(int user_id){
		ArrayList<CarModel> carsArray = new ArrayList<CarModel>();
		try {
			if(con == null || con.isClosed()) {
				DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample_ncs_db", "root", "root");
			}
			
			String s = "select * from customer_car_db where customer_id=?";
			
			pstmt = con.prepareStatement(s);
			pstmt.setInt(1, user_id);
			res = pstmt.executeQuery();
			
			while(res.next()) {
				String carModel = res.getString(3);
				String carType = res.getString(4);
				String carRegNum = res.getString(5);
				String serviceRequest = res.getString(6);
				String serviceStatus = res.getString(7);
				CarModel car = new CarModel(user_id, carModel, carType, carRegNum, serviceRequest, serviceStatus);
				carsArray.add(car);
			}
			return carsArray;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static ArrayList<CarModel> viewAllCarServiceRequests(){
		ArrayList<CarModel> carsArray = new ArrayList<CarModel>();
		try {
			if(conStat == null || conStat.isClosed()) {
				DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
				conStat = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample_ncs_db", "root", "root");
			}
			
			String s = "select * from customer_car_db where service_request<>? AND service_status<>?";
			
			pstmtStat = conStat.prepareStatement(s);
			pstmtStat.setString(1, "false");
			pstmtStat.setString(2, "false");
			resStat = pstmtStat.executeQuery();
			
			while(resStat.next()) {
	
				String username = resStat.getString(2);
				String carModel = resStat.getString(3);
				String carType = resStat.getString(4);
				String carRegNum = resStat.getString(5);
				String serviceRequest = resStat.getString(6);
				String serviceStatus = resStat.getString(7);
				int user_id = resStat.getInt(8);
				CarModel car = new CarModel(user_id, username, carModel, carType, carRegNum, serviceRequest, serviceStatus);
				carsArray.add(car);
			}
			return carsArray;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return carsArray;
	}
	
	public int deleteCar() {
		try {
			if(con == null || con.isClosed()) {
				DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample_ncs_db", "root", "root");
			}
			
			String s = "delete from customer_car_db where customer_id=? AND reg_num=?";
			
			pstmt = con.prepareStatement(s);
			pstmt.setInt(1, user_id);
			pstmt.setString(2, carRegistrationNumber);
			
			int outcome = pstmt.executeUpdate();
			return outcome;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
