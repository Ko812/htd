package com.ncs.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CarAccessory {
	String name;
	float price;
	int quantity;
	int customer_id;
	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	boolean checked = true;
	
	static Connection con;
	static PreparedStatement pstmt;
	static ResultSet res;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public boolean getChecked() {
		return this.checked;
	}

	public void setChecked() {
		this.checked = !this.checked;
	}

	public CarAccessory(String name, float price, int quantity, int customer_id) {
		super();
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.customer_id = customer_id;
	}
	
	public static float getTotal(ArrayList<CarAccessory> items) {
		float total = 0;
		for(CarAccessory a : items) {
			if(a.checked) {
				total += a.price * a.quantity;
			}
		}
		return total;
	}
	
	public static int sendOrder(ArrayList<CarAccessory> items) {
		int total = 0;
		for(CarAccessory acc : items) {
			if(acc.checked) {
				String s = "insert into Orders (item, quantity, customer_id) values (?, ?, ?)";
				try {
					if(con == null || con.isClosed()) {
						DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
						con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample_ncs_db", "root", "root");
					}
					pstmt = con.prepareStatement(s);
					pstmt.setString(1, acc.getName());
					pstmt.setInt(2, acc.getQuantity());
					pstmt.setInt(3, acc.getCustomer_id());
					total += pstmt.executeUpdate();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return total;
	}
}
