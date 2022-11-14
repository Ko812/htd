package com.ncs.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

/*************************************
Loader to load the employer, jobs and job
seekers data into the database.

Place the csv files in a convenient location,
and run the relevant methods to load.

Depending on your database definition, if 
you matched foreign keys between, say for example,
employers and jobs, then load employers data first,
followed by jobs, then finally job seekers data.

*Pending generating job applications data.*

**************************************/
public class Loader {
	
	public static PreparedStatement pstmt;
	public static Scanner reader = new Scanner(System.in);
	
	
	public static void main(String[] args) {
		
	}
	
	public static void loadEmployersData(String fullPath, String dbUsername, String password) {
		try {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/job_portal", dbUsername, password);
			
			//e.g. fullPath = "c:\\job-portal\\job_portal_employer_data.csv";

			FileReader fr = new FileReader(fullPath);
			BufferedReader br = new BufferedReader(fr);
			
			String x = br.readLine();

			while((x = br.readLine()) != null) {
				System.out.println(x);
				String[] line = x.split(",");
				String s = "insert into employers (company_name, user_name, password, company_email) values (?, ?, ?, ?)";
				pstmt = con.prepareStatement(s);
				pstmt.setString(1, line[1]);
				pstmt.setString(2, line[2].toUpperCase());
				pstmt.setString(3, Encryption.encrypt(line[3]));
				pstmt.setString(4, line[4].toUpperCase());
				int out = pstmt.executeUpdate();
				System.out.println("Executed " + pstmt);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void loadJobSeekersData(String fullPath, String dbUsername, String password) {
		try {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/job_portal", dbUsername, password);
			
			// e.g. fullPath = "c:\\job-portal\\job_portal_job_seeker_data.csv";

			FileReader fr = new FileReader(fullPath);
			BufferedReader br = new BufferedReader(fr);
			
			String x = br.readLine();
			int i = 0;
			while((x = br.readLine()) != null) {
				System.out.println(x);
				String[] line = x.split(",");
				String s = "insert into job_seekers (first_name, last_name, user_name, password, email, NRIC, contact, years_of_exp, academic_1) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
				pstmt = con.prepareStatement(s);
				pstmt.setString(1, line[1]);
				pstmt.setString(2, line[2]);
				pstmt.setString(3, line[3]);
				pstmt.setString(4, line[4]);
				pstmt.setString(5, line[5]);
				pstmt.setString(6, line[6]);
				pstmt.setString(7, line[7]);
				pstmt.setInt(8, Integer.parseInt(line[8]));
				pstmt.setString(9, line[9]);
				int out = pstmt.executeUpdate();
				System.out.println(++i + "th execution " + pstmt);
			}
			br.close();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void loadJobsData(String fullPath, String dbUsername, String password) {
		try {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/job_portal", dbUsername, password);
			
			// e.g. fullPath = "c:\\job-portal\\job_portal_job_data.csv";

			FileReader fr = new FileReader(fullPath);
			BufferedReader br = new BufferedReader(fr);
			
			String x = br.readLine();

			while((x = br.readLine()) != null) {
				System.out.println(x);
				String[] line = x.split(",");
				String s = "insert into jobs (date_posted, job_role, job_desc, salary, years_of_exp, employer_id) values (?, ?, ?, ?, ?, ?)";
				pstmt = con.prepareStatement(s);
				pstmt.setDate(1, Date.valueOf(line[1]));
				pstmt.setString(2, line[2].toUpperCase());
				pstmt.setString(3, line[3]);
				pstmt.setInt(4, Integer.parseInt(line[4]));
				pstmt.setInt(5, Integer.parseInt(line[5]));
				pstmt.setInt(6, Integer.parseInt(line[6]));
				int out = pstmt.executeUpdate();
				System.out.println("Executed " + pstmt);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
