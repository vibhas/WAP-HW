package waps.hw.vibhas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DbUtilities {
	private static String protocol = "jdbc:derby:";
	//Please edit dbName according to the location of the DB
	private static String dbName = "C:/Users/Vibhas/Documents/GitHub/WAP-HW/miniblog";
	private static String dbusername = "someuser";
	private static String dbpassword = "somepassword";
	private static Properties userInfo;
	private static String dbUrl = protocol + dbName;
	private Connection connection;
	private static String driver = "org.apache.derby.jdbc.EmbeddedDriver";

	public DbUtilities()
	{
		if (connection == null) 
		{
			userInfo = new Properties();
			userInfo.put("user", dbusername);
			userInfo.put("password", dbpassword);
			try 
			{
				Class.forName(driver);
				//System.out.println("driver");
				connection = DriverManager.getConnection(dbUrl, userInfo);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	public ResultSet retrieveData(String query)
	{
		ResultSet result = null;
		try
		{
		Statement statement = connection.createStatement();
		result = statement.executeQuery(query);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;		
	}
	
	public Boolean updateData(String query)
	{
		try
		{
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
			return true;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public ResultSet createNewDataWithGeneratedKeys(String query)
	{
		ResultSet result = null;
		try
		{
			Statement statement = connection.createStatement();
			statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			result = statement.getGeneratedKeys();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public void closeConnection()
	{
		if(connection!=null)
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
/*
	private void getConnectionInstance() {
		if (connection == null) 
		{
			userInfo = new Properties();
			userInfo.put("user", dbusername);
			userInfo.put("password", dbpassword);
			dbUrl = protocol + dbName;
			try 
			{
				Class.forName(driver);
				System.out.println("driver");
				connection = DriverManager.getConnection(dbUrl, userInfo);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}*/
	
}
