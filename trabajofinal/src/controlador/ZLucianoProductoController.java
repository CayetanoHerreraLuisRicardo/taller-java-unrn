package controlador;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelo.*;
import dao.*;

/**
 * Servlet implementation class ProductoController
 */
public class ZLucianoProductoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ZLucianoProductoController() {
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
		try{
			String accion=request.getParameter("accion");
			if(accion==null || accion.isEmpty()){
				response.sendRedirect("vistas/web_mensaje.jsp?mensaje=El sistema no reconoce esta Acción");
			}else{
				if(accion.equals("lista")){
					ProductoDao daoProd=new ProductoDao();
					Integer catID=Integer.parseInt(request.getParameter("cat"));
					List<Producto> listaProducto=new ArrayList<Producto>();
					if(catID == null){
						listaProducto=daoProd.listar();
					}else{
						Categoria cat=new Categoria();
						cat.setId(catID);
						listaProducto=daoProd.listar(cat);
					}
					request.setAttribute("listaproductos", listaProducto);
					getServletContext().getRequestDispatcher("/WebController?url=/productoListar.jsp").forward(request, response);
				}
				//Guardar
				if(accion.equals("guardar")){
					Producto prod=new Producto();
					prod.setNombre(request.getParameter("v_nombre"));
					prod.setDescripcion(request.getParameter("v_desc"));
					prod.setPrecio(Double.parseDouble(request.getParameter("v_precio")));
					prod.setImgURL(request.getParameter("v_img_url"));
					Categoria cat=new Categoria();
					cat.setId(Integer.parseInt(request.getParameter("v_cat_id")));
					prod.setCategoria(cat);
					ProductoDao prodDao=new ProductoDao();
					Integer id=prodDao.guardar(prod);
					if(id != -1){
						Boolean exito=true;
						request.setAttribute("exito", exito);
						String error="Se cargó correctamente la categoría.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/WebController?url=/categoriaAlta.jsp").forward(request, response);
						return;
					}else{
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="Hubo un problema con la recuperación del identificador de la categoría. Posible error en la carga.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/WebController?url=/categoriaAlta.jsp").forward(request, response);
					}
				}
				//Eliminar
				if(accion.equals("eliminar")){
					String prodID=request.getParameter("prodID");
					Integer id=Integer.parseInt(prodID);
					ProductoDao pDao=new ProductoDao();
					Integer borrar=pDao.eliminar(id);
					if(borrar != -1){
						Boolean exito=true;
						request.setAttribute("exito", exito);
						String error="Se borró el producto correctamente.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/WebController?url=/ProductoController?accion=lista&cat="+request.getParameter("cat")).forward(request, response);
					}else{
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="El producto no pudo ser borrado.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/WebController?url=/ProductoController?accion=lista&cat="+request.getParameter("cat")).forward(request, response);
					}
				}
				//Modificar
				if(accion.equals("modificar")){
					String prodID=request.getParameter("id");
					Integer id=Integer.parseInt(prodID);
					Producto prod=new Producto();
					prod.setId(id);
					prod.setNombre(request.getParameter("v_nombre"));
					prod.setDescripcion(request.getParameter("v_desc"));
					prod.setPrecio(Double.parseDouble(request.getParameter("v_precio")));
					prod.setImgURL(request.getParameter("v_img_url"));
					Categoria cat=new Categoria();
					cat.setId(Integer.parseInt(request.getParameter("v_cat_id")));
					prod.setCategoria(cat);
					ProductoDao pDao=new ProductoDao();
					Integer borrar=pDao.modificar(prod);
					if(borrar != -1){
						Boolean exito=true;
						request.setAttribute("exito", exito);
						String error="Se modificó el producto correctamente.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/WebController?url=/ProductoController?accion=lista&cat="+request.getParameter("cat")).forward(request, response);
					}else{
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="El producto no pudo ser modificado.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/WebController?url=/ProductoController?accion=lista&cat="+request.getParameter("cat")).forward(request, response);
					}
				}
				//
				//Buscar
				//
				//if(accion.equals("buscar")){
				//}
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
