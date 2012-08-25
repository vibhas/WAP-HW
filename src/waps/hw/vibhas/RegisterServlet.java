package waps.hw.vibhas;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import org.apache.derby.jdbc.AutoloadedDriver;
import javax.servlet.http.HttpSession;

@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("register.jsp");
	}
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		Map<String, String> errors = new HashMap<String, String>();

		String userName = request.getParameter("reqUserName");
		String name = request.getParameter("name");
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");

		if (userName == "") {
			errors.put("reqUserName", "Username cannot be blank.");
		}

		if (name == "") {
			errors.put("name", "Name cannot be blank.");
		}

		if (password1 == "") {
			errors.put("password1", "Password cannot be blank.");
		}

		if (!password1.equals(password2)) {
			errors.put("password2", "Both the password should match.");
		}

		int flag = 0;
		if (errors.isEmpty()) {
			DbUtilities db = new DbUtilities();
			try {
			
				String query = "SELECT * FROM users WHERE username = '"
						+ userName + "'";
				
				ResultSet result = db.retrieveData(query);
				if(result == null)
				{
					flag = 1;
				}
				else if (!result.next()) {
					String hashPassword = Utilities.getHash(password1);
					
					query = "INSERT INTO users (name, username, password) VALUES ('"
							+ name
							+ "', '"
							+ userName
							+ "', '"
							+ hashPassword
							+ "')";
					
						result = db.createNewDataWithGeneratedKeys(query);
					if(result == null)
					{
						flag = 1;
					}				
					else if (result.next()) {
						int id = result.getInt(1);
						System.out.println("Registered ID is " + id);
				
						session.setAttribute("id", id);
						session.setAttribute("name", name);
					}
					
				}

				else {
					errors.put("reqUserName",
							"Username already taken. Please try something else.");
				}

			} catch (Exception e) {
				e.printStackTrace();
				flag = 1;
			}finally{
				db.closeConnection();
			}
		}

		if(flag == 1)
		{
			request.setAttribute(
					"DbError",
			"Registration could not be successful because of some error. Please try again later.");
			request.getRequestDispatcher("register.jsp").forward(request,
					response);
		}
		else if (errors.isEmpty()) {
			response.sendRedirect("home");
		} else {		
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("register.jsp").forward(request,
					response);
		}
	}

}
