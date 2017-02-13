

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
           //if (request.getParameter("Search") != null)
           //{

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
           
           /*
           while(movies.next())
           {
        	   ///1. create title hyperlink, 2. find stars in a given movie and create star urls
        	   Statement statement_tmp = dbcon.createStatement();
        	   String query2 = "select star_id from stars_in_movies where movie_id like " + movies.getInt(1);
               ResultSet starids = statement_tmp.executeQuery(query2);
        	   
        	   String titleurl = "'titlelink?param=" + movies.getString(2) + "'>";
        	   String starurl = new String("");
        	   while (starids.next())
		   		{
        		   Statement statement_tmp2 = dbcon.createStatement();
        		   int starid = starids.getInt(1);
        		   String star_search_q = "select first_name, last_name from stars where id like " + starid + ";";
        		   ResultSet starname = statement_tmp2.executeQuery(star_search_q);
        		   starname.next();
        		   String star_firstname = starname.getString(1), star_lastname = starname.getString(2);
        		   starurl += "<a href = 'starlink?param=" + starid + "'>" + star_firstname + " " + star_lastname + "</a>, ";
        		   starname.close();
        		   statement_tmp2.close();
		   		}
        	   starurl = starurl.substring(0, starurl.length() - 2);
        	   
        	   
        	   ///find genres for a given movie
        	   String genres = new String();
        	   Statement stm_genres = dbcon.createStatement();
        	   String query_genre = "select genre_id from genres_in_movies where movie_id like " + movies.getInt(1) + ";";
        	   ResultSet genres_set = stm_genres.executeQuery(query_genre);
        	   while (genres_set.next())
        	   {
        		   int genre_id = genres_set.getInt(1);
        		   Statement stm_genre_name = dbcon.createStatement();
        		   ResultSet genre_name = stm_genre_name.executeQuery("select name from genres where id like " + genre_id + ";");
        		   genre_name.next();
        		   String name = genre_name.getString(1);
        		   genres += name + ", ";
        		   stm_genre_name.close();
        		   genre_name.close();
        	   }
        	   genres = genres.substring(0, genres.length() -2);
        	   stm_genres.close();
        	   genres_set.close();
        	   
        	   out.println("<BODY BGCOLOR=\"#FDF5E6\">\n" +
        			   		"<TABLE BORDER=1 ALIGN=\"CENTER\">\n" +
        			   		"<TR BGCOLOR=\"#FFAD00\">\n" +
        			   		"  <TH>Info Type<TH>Value\n" +
        			   		"<TR>\n" +
        			   		"  <TD>ID\n" +
        			   		"  <TD>" + movies.getInt(1) + "\n" +
        			   		"<TR>\n" +
        			   		"  <TD>title\n" +
        			   		"  <TD><a href = " + titleurl + movies.getString(2) + "</a>\n" +
        			   		"<TR>\n" +
        			   		"  <TD>Year\n" +
        			   		"  <TD>" + movies.getInt(3) + "\n" +
        			   		"<TR>\n" +
        			   		"  <TD>Director\n" +
        			   		"  <TD>" + movies.getString(4) + "\n" +
        			   		"<TR>\n" +
        			   		"  <TD>Stars\n" +
        			   		"  <TD>" + starurl + "\n" +
        			   		"<TR>\n" +
        			   		"  <TD>Genres\n" +
        			   		"  <TD>" + genres + "\n" +
        			   		
        			   		"</TR>"+               
        			   		"</TABLE>\n"); 
        	   starids.close();
        	   statement_tmp.close();
           }
           out.println("</BODY></HTML>"); 
           
           */
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
