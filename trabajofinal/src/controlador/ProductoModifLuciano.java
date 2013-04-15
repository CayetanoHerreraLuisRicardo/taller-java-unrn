package controlador;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelo.Categoria;
import modelo.Producto;

import dao.ProductoDao;

/**
 * Servlet implementation class ProductoModif
 */
public class ProductoModifLuciano extends HttpServlet {
	private static final long serialVersionUID = 1L;
	   
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProductoModifLuciano() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		//Verifica Existencia del usuario
		if(session.getAttribute("usuario") == null){
			response.sendRedirect("HomeController");
			return;
		}
		@SuppressWarnings("unchecked")
		Enumeration<String> verif=session.getAttributeNames();
		Boolean contiene=false;
		while(verif.hasMoreElements()){
			String elem=verif.nextElement();
			if(elem.contains("listacategorias")){
				contiene=true;
			}
		}if(contiene == false){
			response.sendRedirect("HomeController");
			return;
		}
		//Servlet en sí
		Integer prodID=Integer.parseInt(request.getParameter("id"));
		ProductoDao prodDao=new ProductoDao();
		Producto prod=prodDao.buscar(prodID);
		if(prod != null){
			request.setAttribute("v_nombre", prod.getNombre());
			request.setAttribute("v_desc", prod.getDescrip());
			request.setAttribute("v_precio", prod.getPrecio());
			request.setAttribute("v_img_url", prod.getImgURL());
			Categoria cat=prod.getCategoria();
			request.setAttribute("v_cat_id", cat.getId());
			getServletContext().getRequestDispatcher("/productoModif.jsp?cat="+request.getParameter("cat")+"&id="+prodID).forward(request, response);
		}
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
