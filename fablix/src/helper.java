import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class helper {
	
	public static Connection createConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		String loginUser = "root";
		String loginPassword = "281313";
		
		String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
		
		Class.forName("com.mysql.jdbc.Driver").newInstance();

        Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPassword);
        
        return dbcon;
	}
	
	public static String getMovieName(int id)
	{
		try
		{
			Connection dbcon = createConnection();
			
			Statement statement = dbcon.createStatement();
			ResultSet title = statement.executeQuery("select title from movies where id like " + id + ";");
			title.next();
			String name = title.getString(1);
			dbcon.close();
			title.close();
			return name;
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return "";
	}
	
	public static boolean isValidUser(String email, String password)
	{
		try
		{
			Connection dbcon = createConnection();
	        // Declare our statement
	        Statement statement = dbcon.createStatement();
	
	        String query = "SELECT password from customers where email = '" + email + "'";
	
	        // Perform the query
	        ResultSet rs = statement.executeQuery(query);
	        String pwd = rs.getString(1);
	        dbcon.close();
            rs.close();
            statement.close();
	        return pwd.equals(password);
		}
		catch(Exception e)
		{
			return false;
		}
	}
}
