package waps.hw.vibhas;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/home")
public class WelcomeServlet extends HttpServlet {

	DbUtilities db;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		if (!Utilities.checkSessionForUser(session)) {
			response.sendRedirect("login.jsp");
		} else
			fetchAllMessages(request, response);
		// forwardPage(request, response);

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int id = (Integer) session.getAttribute("id");

		String post = request.getParameter("post").trim();
		if (!Utilities.checkSessionForUser(session)) {
			response.sendRedirect("login.jsp");
		} else if (post != null && post != "") {
			String query = "INSERT INTO message (user_id, content, timestamp) VALUES ("
					+ id
					+ ", '"
					+ post
					+ "', "
					+ "CURRENT_TIMESTAMP)";
			db = new DbUtilities();
			Boolean result = db.updateData(query);
			if (result == false) {
				session.setAttribute(
						"Error",
						"The action could not be completed because of some error. Please try again later.");
			}
			db.closeConnection();

			fetchAllMessages(request, response);
		} else
			fetchAllMessages(request, response);

		// forwardPage(request, response);
	}

	private void fetchAllMessages(HttpServletRequest request,
			HttpServletResponse response) {
		SimpleDateFormat monthDayYearformatter = new SimpleDateFormat(
				"MMMMM dd, yyyy h:mm a");
		ArrayList<Message> messageList = new ArrayList<Message>();

		db = new DbUtilities();
		try {

			String query = "SELECT message.id, message.content, message.timestamp, users.name FROM message INNER JOIN users ON message.user_id = users.id ORDER BY message.timestamp DESC";

			ResultSet result = db.retrieveData(query);// statement.executeQuery(query);

			if (result != null) {
				while (result.next()) {
					Message message = new Message(result.getInt("id"),
							result.getString("content"),
							result.getTimestamp("timestamp"),
							result.getString("name"));
					messageList.add(message);
					System.out.println(result.getInt("id")
							+ " "
							+ result.getString("content")
							+ " "
							+ result.getTimestamp("timestamp").toString()
							+ " "
							+ monthDayYearformatter.format(result
									.getTimestamp("timestamp")) + " "
							+ result.getString("name"));
				}
			}
			request.setAttribute("messageList", messageList);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			request.getSession().setAttribute(
							"Error",
							"The action could not be completed because of some error. Please try again later.");
		}
		finally
		{
			db.closeConnection();
		}

		forwardPage(request, response);
	}

	private void forwardPage(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			request.getRequestDispatcher("welcome.jsp").forward(request,
					response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
