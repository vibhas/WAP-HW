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
  
  public EmbeddedDbCreator() {
    
  }
  
  public void createDatabase() {
	  
    try {
      DbUtilities db = new DbUtilities();
      String tableDescription = "CREATE TABLE users (id INT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), name VARCHAR(50), username VARCHAR(50), password VARCHAR(50), PRIMARY KEY (id))";
      db.updateData(tableDescription);
     
      tableDescription = "CREATE TABLE message (user_id INT, id INT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), content VARCHAR(2000), timestamp timestamp, PRIMARY KEY (id), FOREIGN KEY (user_id) REFERENCES users(id))";
      db.updateData(tableDescription);
      db.closeConnection();
    } catch (Exception e) {
    	e.printStackTrace();
    }
  }
  
  public void showUsersTable() {	    
    try {
    	System.out.println("Users Table \n");
      DbUtilities db = new DbUtilities();
      String query ="SELECT * FROM users";
      ResultSet resultSet = db.retrieveData(query);
      while(resultSet.next()) {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String username1 = resultSet.getString("username");
        String password1 = resultSet.getString("password");
        System.out.printf("%d %s %s %s \n",
                          id, name, username1, password1);
      }
      db.closeConnection();
    } catch (Exception e) {
      e.printStackTrace();
    }  
  }
  
  public void showMessageTable() {  
	    try {
	    	System.out.println("Message Table \n");
	      DbUtilities db = new DbUtilities();
	      String query = "SELECT * FROM message";
	      ResultSet resultSet = db.retrieveData(query);
	      while(resultSet.next()) {
	    	int userId = resultSet.getInt("user_id");
	        int id = resultSet.getInt("id");
	        String content = resultSet.getString("content");
	        Timestamp timeStamp = resultSet.getTimestamp("timestamp");
	        
	        System.out.printf("%d %d %s %s \n",
	                          userId, id, content, timeStamp.toString());
	      }
	      db.closeConnection();
	    } catch (SQLException e) {
	      e.printStackTrace();
	    }	
	  }
  
  
  public static void main(String[] args) {
    EmbeddedDbCreator tester = new EmbeddedDbCreator();
    tester.createDatabase();
    tester.showUsersTable();
    tester.showMessageTable();
  }
}
