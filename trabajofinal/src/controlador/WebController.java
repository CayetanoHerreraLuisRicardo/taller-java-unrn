package controlador;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelo.Categoria;
import modelo.Producto;
import dao.CategoriaDao;

/**
 * Servlet implementation class WebController
 */
public class WebController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	   
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WebController() {
		super();
		// TODO Auto-generated constructor stub
	}
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		
		//Servlet en sí
		
		//Controla la facturación del carrito
		String tema=request.getParameter("tema");
		if(tema==null || tema.isEmpty()){
			getServletContext().getRequestDispatcher(request.getParameter("url")).forward(request, response);
			return;
		}
		if(tema.equals("compra")){
			@SuppressWarnings("unchecked")
			List<Producto>carrito=(List<Producto>) session.getAttribute("carrito");
			if(carrito==null || carrito.size()==0){
				Boolean exito=false;
				request.setAttribute("exito", exito);
				String error="No se puede facturar una compra vacía.";
				request.setAttribute("error", error);
				String url="/home.jsp";
				getServletContext().getRequestDispatcher(url).forward(request, response);
				return;
			}else{
				getServletContext().getRequestDispatcher(request.getParameter("url")).forward(request, response);
				return;
			}
		}
		//
		//Fin cosas particulares de una página
		//
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

}
