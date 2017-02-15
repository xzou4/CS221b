

import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class register
 */
@WebServlet("/register")
public class register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		response.setContentType("text/html");    // Response mime type

        // Output stream to STDOUT
        PrintWriter out = response.getWriter();
        
        try
        {
           //Class.forName("org.gjt.mm.mysql.Driver");
           
           Connection dbcon = helper.createConnection();
           // Declare our statement
           String id = request.getParameter("id");
           String first_name = request.getParameter("first_name");
           String last_name = request.getParameter("last_name");
           String email = request.getParameter("email");
           String password = request.getParameter("password");
           String queryCC = "INSERT INTO creditcards values (?,?,?,?);";
           String query = "INSERT INTO customers values (?,?,?,?,?, ?,?);";
           
           PreparedStatement statement = dbcon.prepareStatement(queryCC);
           statement.setString(1, id);
           statement.setString(2, first_name);
           statement.setString(3, last_name);
           SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
           java.sql.Date date = new java.sql.Date(df.parse("01/01/1900").getTime());
           statement.setDate(4, date);
           statement.executeUpdate();
           
           // Perform the query
           statement = dbcon.prepareStatement(query);
           statement.setInt(1, Integer.parseInt(id));
           statement.setString(2, first_name);
           statement.setString(3, last_name);
           statement.setString(4, id);
           statement.setString(5, "");
           statement.setString(6, email);
           statement.setString(7, password);
           
           statement.executeUpdate();
           
           userInfo info = new userInfo(email, password);
           HttpSession session = request.getSession();
           session.setAttribute("userInfo", info);
           
           response.sendRedirect("search");
           
           
           dbcon.close();
           statement.close();
        }
        catch (Exception e)
        {
        	out.println(e);
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
