package controlador;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.multipart.FilePart;
import com.oreilly.servlet.multipart.MultipartParser;
import com.oreilly.servlet.multipart.Part;

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
			System.out.println(request.getParameter("accion"));
			PrintWriter out = response.getWriter();
			if(accion==null || accion.isEmpty()){
				response.sendRedirect("/web_mensaje.jsp?mensaje=El sistema no reconoce esta Acción");
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
					Usuario usuario=(Usuario) session.getAttribute("usuario");
					if(usuario==null){
						response.sendRedirect("HomeController");
						return;
					}
					ProductoDao daoProd = new ProductoDao();
					Pedido pedido = (Pedido) session.getAttribute("carrito");
					Hashtable<Producto,Integer>total=(Hashtable<Producto,Integer>)session.getAttribute("total");
					Producto prod=daoProd.buscar(Integer.valueOf(request.getParameter("id")));
					//Busca dentro del carrito si ya esta cargado el mismo producto
					Hashtable<Producto,Integer>prodsTable=pedido.getProductos();
					Enumeration<Producto> prods=prodsTable.keys();
					while(prods.hasMoreElements()){
						Producto tempProd=prods.nextElement();
						if(tempProd.getId()==prod.getId()){
							Integer cant=total.get(tempProd);
							cant++;
							total.put(tempProd, cant);
							session.setAttribute("total", total);
							pedido.getProductos().put(tempProd, cant);
							session.setAttribute("carrito", pedido);
							Boolean exito=true;
							request.setAttribute("exito", exito);
							String error="Se cargó un nuevo producto a su carrito.";
							request.setAttribute("error", error);
							getServletContext().getRequestDispatcher("/ProductoController?accion=lista&cat="+request.getParameter("cat")).forward(request, response);
							return;
						}
					}
					pedido.getProductos().put(prod,0);
					total.put(prod, 0);
					session.setAttribute("total", total);
					session.setAttribute("carrito", pedido);
					Boolean exito=true;
					request.setAttribute("exito", exito);
					String error="Se agregó un nuevo producto a su carrito.";
					request.setAttribute("error", error);
					getServletContext().getRequestDispatcher("/ProductoController?accion=lista&cat="+request.getParameter("cat")).forward(request, response);
				}
				//
				//Eliminar del carrito
				//
				if (accion.equals("carritoDel")){
					Usuario usuario=(Usuario) session.getAttribute("usuario");
					if(usuario==null){
						response.sendRedirect("HomeController");
						return;
					}
					Pedido pedido = (Pedido) session.getAttribute("carrito");
					Hashtable<Producto,Integer>prodsTable=pedido.getProductos();
					Hashtable<Producto,Integer>total=(Hashtable<Producto,Integer>)session.getAttribute("total");
					Enumeration<Producto> prods=prodsTable.keys();
					while(prods.hasMoreElements()){
						Producto prod=prods.nextElement();
						//Existe?
						if(prod.getId() == Integer.parseInt(request.getParameter("id"))){
							//Tiene mós de un producto?
							if(prodsTable.get(prod)>1){
								prodsTable.put(prod,prodsTable.get(prod)-1);
								total.put(prod,prodsTable.get(prod)-1);
								session.setAttribute("total", total);
								pedido.setProductos(prodsTable);
								session.setAttribute("carrito", pedido);
							}//Solo tenia uno
							else{
								prodsTable.remove(prod);
								total.remove(prod);
								session.setAttribute("total", total);
								pedido.setProductos(prodsTable);
								session.setAttribute("carrito", pedido);
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
				//Guardar
				//
				if(accion.equals("guardar")){
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
					//Guardar
					if(existeProducto(request.getParameter("v_nombre"))){
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error = "El nombre de este producto ya existe, por favor ingrese otro e intente de nuevo.";
						session.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/productoAlta.jsp").forward(request, response);
						return;
					}
					//GUARDAR IMAGEN EN SERVIDOR
					String urlimg = "";
					try{
						MultipartParser mp = new MultipartParser(request, 1024*100);
						Part parte;
						String[] extenciones = {"image/gif", "image/pgn", "image/jpg"}; 
						List<String> listaext = Arrays.asList(extenciones) ;
							
						while((parte = mp.readNextPart()) != null){
							if(parte.isFile()){
								FilePart filepart = (FilePart) parte;
								if(listaext.contains(filepart.getContentType())){
									System.out.println(filepart.getFileName());
									System.out.println(filepart.getFilePath());
									System.out.println(filepart.getContentType());
									//cambiar url de la variable dir
									File dir = new File("C:\\Users\\Luciano Graziani\\Dropbox\\UNRN - Materias\\workspace\\trabajofinal\\WebContent\\img");
									filepart.writeTo(dir);
									urlimg ="img/"+ filepart.getFileName();
								}
								else{
									//ver si esto esta bien xD
									Boolean exito=false;
									request.setAttribute("exito", exito);
									String error = "La extencion del archivo que intenta subir es incorrecta";
									session.setAttribute("error", error);
									getServletContext().getRequestDispatcher("/productoAlta.jsp").forward(request, response);
									return;
								}

							}
						}
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						out.close();
					}
					//CAMBIAR v_img_url por la variable urlimg
					Producto prod=new Producto();
					prod.setNombre(request.getParameter("v_nombre"));
					prod.setDescripcion(request.getParameter("v_desc"));
					prod.setPrecio(Double.parseDouble(request.getParameter("v_precio")));
					prod.setImgURL(request.getParameter(urlimg));
					Categoria cat=new Categoria();
					cat.setId(Integer.parseInt(request.getParameter("v_cat_id")));
					prod.setCategoria(cat);
					ProductoDao prodDao=new ProductoDao();
					Integer id=prodDao.guardar(prod);
					if(id != -1){
						Boolean exito=true;
						request.setAttribute("exito", exito);
						String error="Se cargó correctamente el producto.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/productoAlta.jsp").forward(request, response);
						return;
					}else{
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="Hubo un problema al cargar el producto, inténtelo de nuevo más tarde.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/productoAlta.jsp").forward(request, response);
					}
				}
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
			response.sendRedirect("/web_mensaje.jsp?mensaje=El sistema no reconoce esta Acción");
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
