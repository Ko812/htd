import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerModel {
	private String name;
	private String username;
	private String password;
	private String email;
	
	PreparedStatement pstmt;
	Connection con;
	ResultSet res;
	
	public CustomerModel(String name, String username, String password, String email) {
		super();
		this.name = name;
		this.username = username;
		this.password = password;
		this.email = email;
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
	
	int register() {
		try {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample_ncs_db", "root", "root");
			
			String v = "select * from customer_db where cun=?";
			pstmt = con.prepareStatement(v);
			pstmt.setString(1, username);
			
			res = pstmt.executeQuery();
			
			if(res.next()) {
				return -1;
			}
			else {
				String s = "insert into customer_db values (?,?,?,?)";
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
}
