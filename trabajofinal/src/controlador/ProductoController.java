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

	@SuppressWarnings("unchecked")
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		
		//Servlet en si
		try{
			String accion=request.getParameter("accion");
			if(accion==null || accion.isEmpty()){
				Boolean exito=false;
				request.setAttribute("exito", exito);
				String error="El sistema no reconoce esta Acción.";
				request.setAttribute("error", error);
				getServletContext().getRequestDispatcher("/HomeController").forward(request, response);
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
					getServletContext().getRequestDispatcher("/productoListar.jsp").forward(request, response);
				}
				//
				//Insertar al carrito
				//
				if (accion.equals("carritoAdd")) {
					ProductoDao daoProd = new ProductoDao();
					Hashtable<Producto,Integer>productos=(Hashtable<Producto,Integer>) session.getAttribute("carrito");
					if(productos == null){
						getServletContext().getRequestDispatcher("HomeController").forward(request, response);
						return;
					}
					Producto prod=daoProd.buscar(Integer.valueOf(request.getParameter("prodID")));
					//Busca dentro del carrito si ya esta cargado el mismo producto
					if(productos.size()>0){
						Enumeration<Producto> prods=productos.keys();
						while(prods.hasMoreElements()){
							Producto tempProd=prods.nextElement();
							if(tempProd.getId()==prod.getId()){
								Integer cant=productos.get(tempProd);
								cant++;
								productos.put(tempProd, cant);
								session.setAttribute("carrito", productos);
								Boolean exito=true;
								request.setAttribute("exito", exito);
								String error="Se cargó un nuevo producto a su carrito.";
								request.setAttribute("error", error);
								getServletContext().getRequestDispatcher("/ProductoController?accion=lista&cat="+request.getParameter("cat")).forward(request, response);
								return;
							}
						}
						productos.put(prod,1);
						session.setAttribute("carrito", productos);
						Boolean exito=true;
						request.setAttribute("exito", exito);
						String error="Se agregó un nuevo producto a su carrito.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/ProductoController?accion=lista&cat="+request.getParameter("cat")).forward(request, response);
					}else{
						productos.put(prod,1);
						session.setAttribute("carrito", productos);
						Boolean exito=true;
						request.setAttribute("exito", exito);
						String error="Se agregó un nuevo producto a su carrito.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/ProductoController?accion=lista&cat="+request.getParameter("cat")).forward(request, response);
					}
				}
				//
				//Eliminar del carrito
				//
				if (accion.equals("carritoDel")){
					Hashtable<Producto,Integer>productos=(Hashtable<Producto,Integer>) session.getAttribute("carrito");
					if(productos == null){
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="No puede eliminar un producto si el carrito está vacío.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/HomeController").forward(request, response);
						return;
					}
					Enumeration<Producto> prods=productos.keys();
					while(prods.hasMoreElements()){
						Producto prod=prods.nextElement();
						//Existe?
						if(prod.getId() == Integer.parseInt(request.getParameter("prodID"))){
							//Tiene mós de un producto?
							if(productos.get(prod)>1){
								productos.put(prod,productos.get(prod)-1);
								session.setAttribute("carrito", productos);
								Boolean exito=true;
								request.setAttribute("exito", exito);
								String error="Se borró un producto de su carrito.";
								request.setAttribute("error", error);
							}//Solo tenia uno
							else{
								productos.remove(prod);
								session.setAttribute("carrito", productos);
								Boolean exito=true;
								request.setAttribute("exito", exito);
								String error="Se borró un producto de su carrito.";
								request.setAttribute("error", error);
								break;
							}
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
				//Eliminar del carrito
				//
				if (accion.equals("carritoSupr")){
					Hashtable<Producto,Integer>productos=(Hashtable<Producto,Integer>) session.getAttribute("carrito");
					if(productos == null){
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="No puede eliminar un producto si el carrito está vacío.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/HomeController").forward(request, response);
						return;
					}
					session.setAttribute("carrito",new Hashtable<Producto,Integer>());
					Boolean exito=false;
					request.setAttribute("exito", exito);
					String error="Se vació el carrito.";
					request.setAttribute("error", error);
					getServletContext().getRequestDispatcher("/HomeController").forward(request, response);
					return;
				}
				//
				//Guardar
				//
				////// El guardado del producto se realiza en ImagenController
				//
				//Eliminar
				//
				if(accion.equals("eliminar")){
					//Verifica el rol del usuario
					Usuario usuario=(Usuario) session.getAttribute("usuario");
					if(usuario==null){
						response.sendRedirect("HomeController");
						return;
					}
					Rol rol=usuario.getRol();
					if(rol.getId() != 1 || rol.getId() == null){
						response.sendRedirect("HomeController");
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="Ud. no es administrador, no puede realizar dicha acción.";
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
						String error="Se borró el producto correctamente.";
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
					if(usuario==null){
						response.sendRedirect("HomeController");
						return;
					}
					Rol rol=usuario.getRol();
					if(rol.getId() != 1 || rol.getId() == null){
						response.sendRedirect("HomeController");
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="Ud. no es administrador, no puede realizar dicha acción.";
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
						String error="Se modificó el producto correctamente.";
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
			
			/*if (accion.equals("buscar")) {
				ProductoDao daoproducto = new ProductoDao();
				List<Producto> listaproducto = daoproducto.buscador(request.getParameter("txtbuscar"),Integer.valueOf(request.getParameter("categoria")));
				request.setAttribute("listaproducto", listaproducto);
				getServletContext().getRequestDispatcher("/productoEliminar.jsp").forward(request, response);
			}*/
		}catch (Exception e){
			e.printStackTrace();
			response.sendRedirect("/web_mensaje.jsp?mensaje="+e.getMessage());
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String accion=request.getParameter("accion");
		if(accion==null || accion.isEmpty()){
			Boolean exito=false;
			request.setAttribute("exito", exito);
			String error="El sistema no reconoce esta Acción.";
			request.setAttribute("error", error);
			getServletContext().getRequestDispatcher("/HomeController").forward(request, response);
		}else{
			//Nota: nunca lo usó.
			if(accion.equals("guardar") || accion.equals("modificar")){
				Boolean exito=false;
				request.setAttribute("exito", exito);
				String error= "Ud estó intentando realizar una operación no permitida.";
				request.setAttribute("error", error);
				getServletContext().getRequestDispatcher("/home.jsp").forward(request, response);
				return;
			}
		}
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
