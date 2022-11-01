package com.ncs.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CarDetails {
	private String model;
	private String type;
	private String registrationNumber;
	
	Connection con;
	PreparedStatement pstmt;
	ResultSet res;
	
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRegistrationNumber() {
		return registrationNumber;
	}
	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}
	public CarDetails(String model, String type, String registrationNumber) {
		super();
		this.model = model;
		this.type = type;
		this.registrationNumber = registrationNumber;
	}
	
	public CarDetails() {
		
	}
	
	public ArrayList<CarDetails> viewCarDetails(int user_id) {
		ArrayList<CarDetails> carDetails = new ArrayList<CarDetails>();
		try {
			if(con == null || con.isClosed()) {
				DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample_ncs_db", "java_developer", "Password123!");
			}
			
			String s = "select * from customer_car_db where customer_id=?";
			
			pstmt = con.prepareStatement(s);
			pstmt.setInt(1, user_id);
			res = pstmt.executeQuery();
			
			while(res.next()) {
				String carModel = res.getString(3);
				String carType = res.getString(4);
				String carRegNum = res.getString(5);
				CarDetails car = new CarDetails(carModel, carType, carRegNum);
				carDetails.add(car);
			}
			return carDetails;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
