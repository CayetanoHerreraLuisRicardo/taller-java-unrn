package controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelo.*;
import dao.*;

/**
 * Servlet implementation class CategoriaController
 */
public class CarritoControllerLuciano extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CarritoControllerLuciano() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
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
		try{
			String accion=request.getParameter("accion");
			if(accion==null || accion.isEmpty()){
				response.sendRedirect("vistas/web_mensaje.jsp?mensaje=El sistema no reconoce esta Acción");
			}else{
				//
				//Agregar
				//
				if(accion.equals("agregar")){
					//Lista las Categorías
					@SuppressWarnings("unchecked")
					List<Producto>productos=(List<Producto>) session.getAttribute("carrito");
					if(productos==null){
						List<Producto> prods=new ArrayList<Producto>();
						String prodID=request.getParameter("prodID");
						Integer id=Integer.parseInt(prodID);
						ProductoDao pDao=new ProductoDao();
						Producto prod=pDao.buscar(id);
						if(prod != null){
							Boolean exito=true;
							request.setAttribute("exito", exito);
							String error="Se agregó un nuevo producto a su carrito.";
							request.setAttribute("error", error);
							Double cantTemp=prod.getPrecio();
							session.setAttribute("total", cantTemp);
							prods.add(prod);
							session.setAttribute("carrito", prods);
							getServletContext().getRequestDispatcher("/WebController?url=/ProductoController?accion=lista&cat="+request.getParameter("cat")).forward(request, response);
						}else{
							Boolean exito=false;
							request.setAttribute("exito", exito);
							String error="El producto no pudo ser encontrado.";
							request.setAttribute("error", error);
							getServletContext().getRequestDispatcher("/WebController?url=/ProductoController?accion=lista&cat="+request.getParameter("cat")).forward(request, response);
						}
					}else{
						if(productos.size() == 20){
							Boolean exito=false;
							request.setAttribute("exito", exito);
							String error="El carrito está lleno, para continuar comprando, primero facture.";
							request.setAttribute("error", error);
							getServletContext().getRequestDispatcher("/WebController?url=/ProductoController?accion=lista&cat="+request.getParameter("cat")).forward(request, response);
						}
						String prodID=request.getParameter("prodID");
						Integer id=Integer.parseInt(prodID);
						ProductoDao pDao=new ProductoDao();
						Producto prod=pDao.buscar(id);
						if(prod != null){
							Boolean exito=true;
							request.setAttribute("exito", exito);
							String error="Se agregó un nuevo producto a su carrito.";
							request.setAttribute("error", error);
							Double cantTemp=prod.getPrecio();
							Double precio=(Double) session.getAttribute("total");
							precio=precio+cantTemp;
							session.setAttribute("total", precio);
							productos.add(prod);
							session.setAttribute("carrito", productos);
							getServletContext().getRequestDispatcher("/WebController?url=/ProductoController?accion=lista&cat="+request.getParameter("cat")).forward(request, response);
						}else{
							Boolean exito=false;
							request.setAttribute("exito", exito);
							String error="El producto no pudo ser encontrado.";
							request.setAttribute("error", error);
							getServletContext().getRequestDispatcher("/WebController?url=/ProductoController?accion=lista&cat="+request.getParameter("cat")).forward(request, response);
						}
					}
				}
				//
				//Quitar
				//
				if(accion.equals("quitar")){
					//Lista las Categorías
					@SuppressWarnings("unchecked")
					List<Producto>productos=(List<Producto>) session.getAttribute("carrito");
					if(productos==null || productos.size()==0){
							Boolean exito=false;
							request.setAttribute("exito", exito);
							String error="No se puede eliminar un producto del carrito si éste está vacío.";
							request.setAttribute("error", error);
							getServletContext().getRequestDispatcher("/WebController?url=/ProductoController?accion=lista&cat="+request.getParameter("cat")).forward(request, response);
					}else{
						String prodID=request.getParameter("prodID");
						Integer id=Integer.parseInt(prodID);
						ProductoDao pDao=new ProductoDao();
						Producto prod=pDao.buscar(id);
						if(prod != null){
							for(int i=0;i<productos.size();i++){
								Producto prodTemp=productos.get(i);
								if(prodTemp.getId() == prod.getId()){
									Boolean exito=true;
									request.setAttribute("exito", exito);
									String error="Se borró un producto de su carrito.";
									request.setAttribute("error", error);
									Double cantTemp=prod.getPrecio();
									Double precio=(Double) session.getAttribute("total");
									precio=precio-cantTemp;
									session.setAttribute("total", precio);
									productos.remove(i);
									session.setAttribute("carrito", productos);
									getServletContext().getRequestDispatcher("/WebController?url=/ProductoController?accion=lista&cat="+request.getParameter("cat")).forward(request, response);
									return;
								}else{
									Boolean exito=false;
									request.setAttribute("exito", exito);
									String error="El producto no pudo ser encontrado en su carrito.";
									request.setAttribute("error", error);
								}
							}
						}else{
							Boolean exito=false;
							request.setAttribute("exito", exito);
							String error="El producto no existe.";
							request.setAttribute("error", error);
							getServletContext().getRequestDispatcher("/WebController?url=/ProductoController?accion=lista&cat="+request.getParameter("cat")).forward(request, response);
						}
						getServletContext().getRequestDispatcher("/WebController?url=/ProductoController?accion=lista&cat="+request.getParameter("cat")).forward(request, response);
					}
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			response.sendRedirect("vistas/web_mensaje.jsp?mensaje="+e.getMessage());
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
