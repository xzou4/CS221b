

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class starlink
 */
@WebServlet("/starlink")
public class starlink extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public starlink() {
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

           int id = Integer.parseInt(request.getParameter("param"));
           //find star info
           Statement statement = dbcon.createStatement();
           String query = "select * from stars where id like '" + id + "';";
           ResultSet star = statement.executeQuery(query);
           
           star.next();
           
           ///find movies acted by a star
           String titleurls = new String();
           Statement stm_movieid = dbcon.createStatement();
           ResultSet movieid_set = stm_movieid.executeQuery("select movie_id from stars_in_movies where star_id like " + star.getInt(1) +";");
           while(movieid_set.next())
           {
        	   int movieid = movieid_set.getInt(1);
        	   Statement stm_movietitle = dbcon.createStatement();
        	   ResultSet moviename_set = stm_movietitle.executeQuery("select title from movies where id like " + movieid + ";");
        	   moviename_set.next();
        	   titleurls += "<a href = 'titlelink?param=" + movieid + "'>" + moviename_set.getString(1) + "</a>, ";
        	   stm_movietitle.close();
        	   moviename_set.close();
           }
           titleurls = titleurls.substring(0, titleurls.length() - 2);
           stm_movieid.close();
           movieid_set.close();
           
           out.println("<BODY BGCOLOR=\"#FDF5E6\">\n" +
    			   		"<TABLE BORDER=1 ALIGN=\"CENTER\">\n" +
    			   		"<TR BGCOLOR=\"#FFAD00\">\n" +
    			   		"  <TH>Info Type<TH>Value\n" +
    			   		"<TR>\n" +
    			   		"  <TD>ID\n" +
    			   		"  <TD>" + star.getInt(1) + "\n" +
    			   		"<TR>\n" +
    			   		"  <TD>Name\n" +
    			   		"  <TD>" + star.getString(2) + " " + star.getString(3) + "\n" +
    			   		"<TR>\n" +
    			   		"  <TD>Date of Birth\n" +
    			   		"  <TD>" + star.getDate(4) + "\n" +
    			   		"<TR>\n" +
    			   		"  <TD>Photo\n" +
    			   		"  <TD>" + star.getString(5) + "\n" +
    			   		"<TR>\n" +
    			   		"  <TD>Movies\n" +
    			   		"  <TD>" + titleurls + "\n" +
    			   		
    			   		"</TR>"+               
    			   		"</TABLE>\n"); 
           out.println("</BODY></HTML>"); 
           star.close();
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
