

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class shoppingCartController
 */
@WebServlet("/shoppingCartController")
public class shoppingCartController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public shoppingCartController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		int id = Integer.parseInt(request.getParameter("id"));
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		List<item> cart = new ArrayList<>();
		if (action.equals("order_now"))
		{
			if (session.getAttribute("cart") == null)
			{
				cart = new ArrayList<>();
				cart.add(new item(id));
			}
			else
			{
				cart = (ArrayList<item>)session.getAttribute("cart");
				int indicator = contains(id, cart);
				if (indicator == -1)
					cart.add(new item(id));
				else
					cart.get(indicator).addQuantity(1);
			}
		}
		else if (action.equals("delete"))
		{
			cart = (ArrayList<item>)session.getAttribute("cart");
			int index = contains(id, cart);
			cart.remove(cart.get(index));
		}
		
		
		session.setAttribute("cart", cart);
		request.getRequestDispatcher("shoppingcart.jsp").forward(request, response);
	}
	
	private int contains(int id, List<item> cart)
	{
		for (int i = 0; i < cart.size(); i++)
		{
			if (cart.get(i).getId() == id)
				return i;
		}
		return -1;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		String action = request.getParameter("action");
		String[] quantity = request.getParameterValues("quantity");
		if (action.equals("update"))
		{
			HttpSession session = request.getSession();
			List<item> cart = (ArrayList<item>)session.getAttribute("cart");
			for (int i = 0; i < cart.size(); i++)
			{
				cart.get(i).setQuantity(Integer.parseInt(quantity[i]));
			}
			
			session.setAttribute("cart", cart);
			request.getRequestDispatcher("shoppingcart.jsp").forward(request, response);
		}
	}

}
