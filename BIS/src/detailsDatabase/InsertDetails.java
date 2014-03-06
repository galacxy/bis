package detailsDatabase;

import java.sql.*;
import java.util.*;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;


public class InsertDetails {
	
	static Connection conn;
	HashMap<Object, Object> pwdMessage;
	
	public InsertDetails() throws SQLException
	{
		conn  = (Connection) DriverManager
		          .getConnection("jdbc:mysql://localhost/bis?"
		              + "user=rohit&password=password");
		pwdMessage = new HashMap<Object, Object>();
		pwdMessage.put(0,"Password must consist of minimum 8 characters.");
		pwdMessage.put(1,"Password must contain at least one uppercase character.");
		pwdMessage.put(2,"Password must contain at least one lowercase character.");
		pwdMessage.put(3,"Password must contain at least one digit.");
	}
	
	public char retProfile(String profile)
	{
		char p='n';
		if(profile.equalsIgnoreCase("user"))
			p = 'u';
		else if(profile.equalsIgnoreCase("admin"))
			p='a';
		else if(profile.equalsIgnoreCase("farmer"))
			p='f';
		return p;
	}
	public int userExists(String username, char profile) throws SQLException
	{
		Statement stmt = (Statement) conn.createStatement();
		int flag = 0;
		ResultSet result;
		String query = "select username from userdetails where username='"+username+"' and profile='"+profile+"'";
		result = stmt.executeQuery(query);
		if(result.isBeforeFirst())
			{
				flag  = 1;
			}
		return flag;
		
	}
	public int checkpassword(String pass)
	{
		int flag =-1;
		if(pass.length()<8)
		{
			flag=0;
		}
		else 
		{
		    boolean upper = false;
		    boolean lower = false;
		    boolean number = false;
		    for (char c : pass.toCharArray()) 
		    {
		      if (Character.isUpperCase(c)) 
		      {
		        upper = true;
		      } 
		      else if (Character.isLowerCase(c)) 
		      {
		        lower = true;
		      }
		      else if (Character.isDigit(c)) 
		      {
		        number = true;
		      }
		    }
		    if (!upper) 
		    {
		      flag = 1;
		    }
		    else if (!lower) 
		    {
		    	flag=2;
		    }
		    else if (!number) 
		    {
		    	flag=3;
		    }
		}
		return flag;
	}
	public HashMap<String, String> newUserInsert(HashMap<String, String> ParaList ) throws SQLException
	{
		String value;
		char profile;
		int flag;
		profile = retProfile(ParaList.get("profile"));
		value = (String) ParaList.get("username");
		flag = userExists(value,profile);
		if(flag==1)
		{
			ParaList.put("message","User Already Exists!");
		}
		else 
		{
			flag = checkpassword(ParaList.get("password"));
			switch(flag)
			{
				case 0:ParaList.put("message", (String) pwdMessage.get(0));
						break;
				case 1:ParaList.put("message", (String) pwdMessage.get(1));
						break;
				case 2:ParaList.put("message", (String) pwdMessage.get(2));
						break;
				case 3:ParaList.put("message", (String) pwdMessage.get(3));
						break;
			}
		}
		if(ParaList.get("message")==null)
		{
			ParaList.put("message","Details can be inserted");
		}
		return ParaList;
	}
	///////////////////////////////////////////////////////////////////////////////
	public static void main(String args[]) throws SQLException ///////////////////
	{
		InsertDetails n = new InsertDetails();
		if(conn!=null)
		{
			System.out.println("Done");
			HashMap<String, String> m = new HashMap<String, String>();
			m.put("username", "rohit");
			m.put("profile", "user");
			m.put("password", "123456789Po");
			m.put("message",null);
			m  = n.newUserInsert(m);
			System.out.print(m.get("message"));
		}
		else
		{
			System.out.print("Not Done");
		}
	}

}
