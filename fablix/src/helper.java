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
}
