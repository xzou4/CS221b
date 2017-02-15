

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
import javax.servlet.http.HttpSession;
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
		

        response.setContentType("text/html");    // Response mime type

        // Output stream to STDOUT
        PrintWriter out = response.getWriter();
        
        HttpSession session = request.getSession();
        userInfo info = (userInfo)session.getAttribute("userInfo");
        
        if (!helper.isValidUser(info.getEmail(), info.getPassword()))
        {
        	out.println("<script type=\"text/javascript\">");  
            out.println("alert('Wrong email address or password');");
            out.println("location='login.html';");
            out.println("</script>");
        }
        else
        {
	        out.println("<a href='shoppingcart.jsp'>Shopping Cart</a>");
	        out.println("<a ALIGN='left' href='shoppingcart.jsp'>Shopping Cart</a>" +
 				   "<H1 ALIGN='CENTER'>Looking for a Movie?</H1>" +
 				   "<FORM ACTION='search' METHOD='POST'> <CENTER>" +
 				   "Title: <INPUT TYPE='text' NAME='title'><BR>" +
 		     
 		     		"Year: <INPUT TYPE='TEXT' NAME='year'><BR>" +
 		     
 		     		"Director: <INPUT TYPE='TEXT' NAME='director'><BR>"+
 		     
 		     		"Star's first name: <INPUT TYPE='TEXT' NAME='first_name'><BR>" +

 		     		"Star's last name: <INPUT TYPE='TEXT' NAME='last_name'><BR>" +
 		     
 		       		"<INPUT TYPE='SUBMIT' VALUE='Search'></CENTER></FORM>" +
		
		 		   "<H1 ALIGN='center'>Or Browse?</H1>" +
		 		   "<FORM ACTION='genrelink' METHOD='POST'>  <CENTER>" +
		 		   "<select name='param'>" +
		 		   "<option value = '48001'>Classic</option>" +
		 		   "<option value = '150005'>Coming-of-Age-Drama</option>" +
		 		   "<option value = '150008'>Gangster</option>" +
		 		   "<option value = '422607'>Suspense</option>" +
		 		   "<option value = '422610'>Indie</option>" +
		 		   "<option value = '492206'>Biograph</option>" +
		 		   "<option value = '525208'>spy</option>" +
		 		   "<option value = '525210'>James Bond</option>" +
		 		   "<option value = '631003'>Roman</option>" +
		 		   "<option value = '764003'>Animation</option>" +
		 		   "<option value = '764013'>Musical</option>" +
		 		   "<option value = '764014'>Mystery</option>" +
		 		   "<option value = '764017'>Sport</option>" +
		 		   "<option value = '788009'>Documentary</option>" +
		 		   "<option value = '788010'>Musical/Performing Arts</option>" +
		 		   "<option value = '855203'>Crime</option>" +
		 		   "<option value = '855209'>Music</option>" +
		 		   "<option value = '872006'>History</option>" +
		 		   "<option value = '872010'>War</option>" +
		 		   "<option value = '872012'>Family</option>" +
		 		   "<option value = '907000'>Action</option>" +
		 		   "<option value = '907001'>Adventure</option>" +
		 		  
		 		   "<option value = '907002'>Drama</option>" +
		 		   "<option value = '907003'>Comedy</option>" +
		 		   "<option value = '907004'>Horror</option>" +
		 		   "<option value = '907005'>Thriller</option>" +
		 		   "<option value = '907006'>Foreign</option>" +
		 		   "<option value = '907007'>Sci-Fi</option>" +
		 		   "<option value = '907008'>Romance</option>" +
		 		   "<option value = '907009'>Fantasy</option>" +
		 		   "</select>" +
		 		   "    <INPUT TYPE='SUBMIT' VALUE='Browse'>" +
		 		    " </CENTER>" +
		 		   "</FORM>" +
		 		   "<center><a href = 'browse'>Browser by title?</a>");
	        try
	        {
	           
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
