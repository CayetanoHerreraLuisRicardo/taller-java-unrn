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
public class ProductoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductoController() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		
		//Servlet en s�
		try{
			String accion=request.getParameter("accion");
			if(accion==null || accion.isEmpty()){
				response.sendRedirect("vista/web_mensaje.jsp?mensaje=El sistema no reconoce esta Acci�n");
			}else{
				//
				//Listar
				//
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
					getServletContext().getRequestDispatcher("vista/productoListar.jsp").forward(request, response);
				}
				//
				//Insertar al carrito
				//
				if (accion.equals("carritoAdd")) {
					ProductoDao daoProd = new ProductoDao();
					Pedido pedido = (Pedido) session.getAttribute("carrito");
					Producto prod=daoProd.buscar(Integer.valueOf(request.getParameter("id")));
					if(pedido.getProductos().add(prod)){
						Double precio=(Double) session.getAttribute("total");
						Double cantTemp=prod.getPrecio();
						precio=precio+cantTemp;
						session.setAttribute("total", precio);
						Boolean exito=true;
						request.setAttribute("exito", exito);
						String error="Se agreg� un nuevo producto a su carrito.";
						request.setAttribute("error", error);
					}
					else{
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="El producto no pudo ser encontrado.";
						request.setAttribute("error", error);
					}
					getServletContext().getRequestDispatcher("/ProductoController?accion=lista&cat="+request.getParameter("cat")).forward(request, response);
				}
				//
				//Eliminar del carrito
				//
				if (accion.equals("carritoDel")){
					Pedido pedido = (Pedido) session.getAttribute("carrito");
					List<Producto> carritoProds = pedido.getProductos();
					for(Producto prod : carritoProds){
						if(prod.getId() == Integer.parseInt(request.getParameter("id"))){
							carritoProds.remove(prod);
							Double cantTemp=prod.getPrecio();
							Double precio=(Double) session.getAttribute("total");
							precio=precio-cantTemp;
							session.setAttribute("total", precio);
							Boolean exito=true;
							request.setAttribute("exito", exito);
							String error="Se borr� un producto de su carrito.";
							request.setAttribute("error", error);
							break;
						}else{
							Boolean exito=false;
							request.setAttribute("exito", exito);
							String error="El producto no pudo ser encontrado en su carrito.";
							request.setAttribute("error", error);
						}
					}
					getServletContext().getRequestDispatcher("/ProductoController?accion=lista&cat="+request.getParameter("cat")).forward(request, response);
				}
				//
				//Guardar
				//
				if(accion.equals("guardar")){
					//Verifica el rol del usuario
					Usuario usuario=(Usuario) session.getAttribute("usuario");
					Rol rol=usuario.getRol();
					if(rol.getId() != 1 || rol.getId() == null){
						response.sendRedirect("HomeController");
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="Ud. no es administrador, no puede realizar dicha acci�n.";
						request.setAttribute("error", error);
						return;
					}
					//Guardar
					if(existeProducto(request.getParameter("v_nombre"))){
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error = "El nombre de este producto ya existe, por favor ingrese otro e intente de nuevo.";
						session.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/vista/productoAlta.jsp").forward(request, response);
						return;
					}
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
						String error="Se carg� correctamente el producto.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/vista/productoAlta.jsp").forward(request, response);
						return;
					}else{
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="Hubo un problema al cargar el producto, int�ntelo de nuevo m�s tarde.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/vista/productoAlta.jsp").forward(request, response);
					}
				}
				//
				//Eliminar
				//
				if(accion.equals("eliminar")){
					//Verifica el rol del usuario
					Usuario usuario=(Usuario) session.getAttribute("usuario");
					Rol rol=usuario.getRol();
					if(rol.getId() != 1 || rol.getId() == null){
						response.sendRedirect("HomeController");
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="Ud. no es administrador, no puede realizar dicha acci�n.";
						request.setAttribute("error", error);
						return;
					}
					//Eliminar
					String prodID=request.getParameter("prodID");
					Integer id=Integer.parseInt(prodID);
					ProductoDao pDao=new ProductoDao();
					Integer borrar=pDao.eliminar(id);
					if(borrar != -1){
						Boolean exito=true;
						request.setAttribute("exito", exito);
						String error="Se borr� el producto correctamente.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/ProductoController?accion=lista&cat="+request.getParameter("cat")).forward(request, response);
					}else{
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="El producto no pudo ser borrado.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/ProductoController?accion=lista&cat="+request.getParameter("cat")).forward(request, response);
					}
				}
				//
				//Modificar
				//
				if(accion.equals("modificar")){
					//Verifica el rol del usuario
					Usuario usuario=(Usuario) session.getAttribute("usuario");
					Rol rol=usuario.getRol();
					if(rol.getId() != 1 || rol.getId() == null){
						response.sendRedirect("HomeController");
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="Ud. no es administrador, no puede realizar dicha acci�n.";
						request.setAttribute("error", error);
						return;
					}
					//Modificar
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
						String error="Se modific� el producto correctamente.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/ProductoController?accion=lista&cat="+request.getParameter("cat")).forward(request, response);
					}else{
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="El producto no pudo ser modificado.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/ProductoController?accion=lista&cat="+request.getParameter("cat")).forward(request, response);
					}
				}
			}
			//
			//Buscar
			//
			//if (accion.equals("buscar")) {
			//	ProductoDao daoproducto = new ProductoDao();
			//	List<Producto> listaproducto = daoproducto.buscador(request.getParameter("txtbuscar"),Integer.valueOf(request.getParameter("categoria")));
			//	request.setAttribute("listaproducto", listaproducto);
			//	getServletContext().getRequestDispatcher("/eliminarproducto.jsp").forward(request, response);
			//}
		}catch (Exception e){
			e.printStackTrace();
			response.sendRedirect("/vista/web_mensaje.jsp?mensaje="+e.getMessage());
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

	private Boolean existeProducto(String nom) throws Exception {
		ProductoDao daoproducto = new ProductoDao();
		List<Producto> listaproductos = daoproducto.listar(null);
		if (listaproductos == null) {
			return false;
		} else {
			for (Producto prod : listaproductos) {
				if (prod.getNombre().equals(nom)) {
					return true;
				}
			}
		}
		return false;
	}
}
