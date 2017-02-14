

import java.io.IOException;
import java.io.*;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import fablix/printMovies;


/**
 * Servlet implementation class search
 */
@WebServlet("/search")
public class search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public search() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//String loginUser = "root";
		//String loginPassword = "281313";
		
		//String loginUrl = "jdbc:mysql://localhost:3306/moviedb";

        response.setContentType("text/html");    // Response mime type

        // Output stream to STDOUT
        PrintWriter out = response.getWriter();
        
        try
        {
           //Class.forName("org.gjt.mm.mysql.Driver");
           //Class.forName("com.mysql.jdbc.Driver").newInstance();

           //Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPassword);
           
           Connection dbcon = helper.createConnection();
           
           String title = request.getParameter("title");
           String year = request.getParameter("year");
           String director = request.getParameter("director");
           String firstname = request.getParameter("first_name");
           String lastname = request.getParameter("last_name");
           
           ///find movies which satisfy search conditions
           Statement statement = dbcon.createStatement();
           String query = new String();
           if (title.length() == 0 && year.length() == 0 && director.length() == 0 &&
        		   firstname.length() == 0 && lastname.length() == 0)
        	   query = "select * from movies";
           else
           {
        	   query = "select * from movies where(";
           
	           if (title.trim().length() != 0)
	        	   query += "title like '%" + title + "%' and";
	           if (year.trim().length() != 0)
	        	   query += " year like '" + year + "' and";
	           if (director.trim().length() != 0)
	        	   query += " director like '%" + director + "%' and";
	           if (firstname.trim().length() != 0 && lastname.trim().length() != 0)
	        	   query += " first_name like '" + firstname + "' and last_name like '" + lastname + "'";
	           else if (firstname.trim().length() != 0)
	        	   query += " first_name like '%" + firstname + "%'";
	           else if (lastname.trim().length() != 0)
	        	   query += " last_name like '%" + lastname + "%'";
	           
	           if (query.substring(query.length() - 3, query.length()).equals("and"))
	        	   query = query.substring(0, query.length() - 3);
	           query += ");";
           }
           ResultSet movies = statement.executeQuery(query);
           
           printMovies.print(movies, dbcon, out);
           
           
           
           movies.close();
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
