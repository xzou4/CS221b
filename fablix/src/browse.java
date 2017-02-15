

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
 * Servlet implementation class browse
 */
@WebServlet("/browse")
public class browse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public browse() {
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
        out.println("<a href='shoppingcart.jsp'>Shopping Cart</a>");
        try
        {
           //Class.forName("org.gjt.mm.mysql.Driver");
           Class.forName("com.mysql.jdbc.Driver").newInstance();

           Connection dbcon = helper.createConnection();
           // Declare our statement
           //if (request.getParameter("Search") != null)
           //{

           
           
           ///find movies which satisfy search conditions
           Statement statement = dbcon.createStatement();
           
           ResultSet movies = statement.executeQuery("select * from movies order by title");
           
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
