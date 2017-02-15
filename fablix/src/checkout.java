

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class checkout
 */
@WebServlet("/checkout")
public class checkout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public checkout() {
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
           Statement statement = dbcon.createStatement();
           String sumprice = request.getParameter("sum");
           String id = request.getParameter("id");
           String first_name = request.getParameter("first_name");
           String last_name = request.getParameter("last_name");
           
           SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
           java.sql.Date expiration = new java.sql.Date(df.parse(request.getParameter("expiration")).getTime());
           
           // Perform the query
           ResultSet rs = statement.executeQuery("select * from creditcards where id like " + id + ";");
           if (!rs.next())
           {
        	   printError(out);
        	   return;
           }
           if (rs.getString(2).equals(first_name) && rs.getString(3).equals(last_name)
        		   && rs.getDate(4).equals(expiration))
           {
        	   out.println("<script type=\"text/javascript\">");  
        	   out.println("alert('Welcome " + first_name + "!');");
               out.println("location='main.html';");
               out.println("</script>");
               
           }
           else
           {        
        	   printError(out);
        	   return;
           }
           dbcon.close();
           rs.close();
           statement.close();
        }
        catch (java.text.ParseException e)
        {
        	printError(out);
     	    return;
        }
        catch (Exception e)
        {
        	out.println(e);
        }
	}

	private void printError(PrintWriter out)
	{
		out.println("<script type=\"text/javascript\">");  
        out.println("alert('Wrong email address or password');");
        out.println("location='checkout.html';");
        out.println("</script>");
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
