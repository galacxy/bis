package detailsDatabase;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class loginvalidation {
	
	static Connection conn;
	
	public loginvalidation() throws SQLException
	{
		conn  = (Connection) DriverManager
		          .getConnection("jdbc:mysql://localhost/bis?"
		              + "user=rohit&password=password");
	}
	// if login is successful return all related information
	public HashMap<String,String> loginvalidity(String username, String pwd) throws SQLException
	{
		Statement stmt = (Statement) conn.createStatement();
		HashMap<String, String> user = new HashMap<String,String>();
		ResultSet result;
		String query = "select * from userdetails where username='"+username+"' and password='"+pwd+"'";
		result = stmt.executeQuery(query);
		if(result.isBeforeFirst())
			{
				while(result.next())
				{
					user.put("username",result.getString("username"));
					user.put("name",result.getString("name"));
					user.put("message", "Login Successful");
				}
			}
		else
		{
			user.put("message", "Incorrect Username or password");
		}
		return user;
	}
	public HashMap<String,String> isLoginValid(HashMap<String,String> logindetail) throws SQLException
	{
		String usr = logindetail.get("username");
		String pwd = logindetail.get("password");
		logindetail = loginvalidity(usr,pwd);
		return logindetail;
		
	}
	
	public static void main(String[] args) throws SQLException {
		HashMap<String,String> user = new HashMap<String, String>();
		user.put("username","ruchit");
		user.put("password", "1234");
		user = new loginvalidation().isLoginValid(user);
		System.out.println(user.get("message"));
	}

}
