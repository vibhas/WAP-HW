package waps.hw.vibhas;

import java.sql.*;
import java.util.*;

/** Creates "myDatabase" DB and "employees" table.
 *  <p>
 *  From <a href="http://courses.coreservlets.com/Course-Materials/">the
 *  coreservlets.com tutorials on servlets, JSP, Struts, JSF, Ajax, GWT, 
 *  Spring, Hibernate/JPA, and Java programming</a>.
 */

public class EmbeddedDbCreator {
  // Driver class not needed in JDBC 4.0 (Java SE 6)
  private String driver = "org.apache.derby.jdbc.EmbeddedDriver";
  private String protocol = "jdbc:derby:"; 
  private String username = "someuser";
  private String password = "somepassword";
  private String dbName = "C:/Users/Vibhas/Documents/workspace/WA-HW-Vibhas/miniblog";
  //private String tableName = "users";
  private Properties userInfo;
  
  public EmbeddedDbCreator() {
    userInfo = new Properties();
    userInfo.put("user", username);
    userInfo.put("password", password);
  }
  
  public void createDatabase() {
	  
    try {
      String dbUrl = protocol + dbName + ";create=true";
      Connection connection = 
        DriverManager.getConnection(dbUrl, username, password);//userInfo);
      Statement statement = connection.createStatement();
      //String format = "VARCHAR(20)";
      String tableDescription = "CREATE TABLE users (id INT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), name VARCHAR(50), username VARCHAR(50), password VARCHAR(50), PRIMARY KEY (id))";
      statement.execute(tableDescription);
     
      tableDescription = "CREATE TABLE message (user_id INT, id INT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), content VARCHAR(2000), timestamp timestamp, PRIMARY KEY (id), FOREIGN KEY (user_id) REFERENCES users(id))";
      statement.execute(tableDescription);
      connection.close();
    } catch (SQLException sqle) {
      // If table already exists, then skip everything else
    	sqle.printStackTrace();
    }
  }
  
  public void showUsersTable() {	    
    try {
    	System.out.println("Users Table \n");
      String dbUrl = protocol + dbName;
      Connection connection;
      connection = DriverManager.getConnection(dbUrl, userInfo);
      Statement statement = connection.createStatement();
      String query =
        String.format("SELECT * FROM users");
      ResultSet resultSet = statement.executeQuery(query);
      while(resultSet.next()) {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String username1 = resultSet.getString("username");
        String password1 = resultSet.getString("password");
        System.out.printf("%d %s %s %s \n",
                          id, name, username1, password1);
      }
      connection.close();
    } catch (SQLException sqle) {
      sqle.printStackTrace();
    }  
  }
  
  public void showMessageTable() {  
	    try {
	    	System.out.println("Message Table \n");
	      String dbUrl = protocol + dbName;
	      Connection connection;
	      connection = DriverManager.getConnection(dbUrl, userInfo);
	      Statement statement = connection.createStatement();
	      String query =
	        String.format("SELECT * FROM message");
	      ResultSet resultSet = statement.executeQuery(query);
	      while(resultSet.next()) {
	    	int userId = resultSet.getInt("user_id");
	        int id = resultSet.getInt("id");
	        String content = resultSet.getString("content");
	        Timestamp timeStamp = resultSet.getTimestamp("timestamp");
	        
	        System.out.printf("%d %d %s %s \n",
	                          userId, id, content, timeStamp.toString());
	      }
	      connection.close();
	    } catch (SQLException sqle) {
	      sqle.printStackTrace();
	    }	
	  }
  
  
  public static void main(String[] args) {
    EmbeddedDbCreator tester = new EmbeddedDbCreator();
    tester.createDatabase();
    tester.showUsersTable();
    tester.showMessageTable();
  }
}
