

import java.io.IOException;
import java.io.*;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class login
 */
@WebServlet("/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String loginUser = "root";
		String loginPassword = "281313";
		
		String loginUrl = "jdbc:mysql://localhost:3306/moviedb";

        response.setContentType("text/html");    // Response mime type

        // Output stream to STDOUT
        PrintWriter out = response.getWriter();
        
        try
        {
           //Class.forName("org.gjt.mm.mysql.Driver");
           Class.forName("com.mysql.jdbc.Driver").newInstance();

           Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPassword);
           // Declare our statement
           Statement statement = dbcon.createStatement();

           String email = request.getParameter("email");
           String pwdin = request.getParameter("password");
           String query = "SELECT * from customers where email = '" + email + "'";

           // Perform the query
           ResultSet rs = statement.executeQuery(query);
           while(rs.next())
           {
        	   String pwd = rs.getString(7);
        	   String first_name = rs.getString(2);
        	   if (pwd.equals(pwdin))
        	   {
        		   out.println("<script type=\"text/javascript\">");  
                   out.println("alert('Welcome " + first_name + "!');");
                   out.println("location='main.html';");
                   out.println("</script>");
                   
                   dbcon.close();
                   rs.close();
                   statement.close();
        		   return;
        	   }
           }
           out.println("<script type=\"text/javascript\">");  
           out.println("alert('Wrong email address or password');");
           out.println("location='login.html';");
           out.println("</script>");
           
           dbcon.close();
           rs.close();
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
