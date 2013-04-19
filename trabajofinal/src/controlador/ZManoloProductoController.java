package controladores;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.util.List;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javazoom.upload.MultipartFormDataRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import org.apache.commons.fileupload.FileItem;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;

import modelos.Pedido;
import modelos.Producto;
import modelos.Usuario;

import daos.ProductoDao;
import daos.UsuarioDao;

/**
 * Servlet implementation class ProductoController
 * 
 * @param <ServletFileUpload>
 */
public class ZManoloProductoController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private String dirUploadFiles;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProductoController() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		HttpSession sesion = request.getSession();
		try {
			String accion = request.getParameter("accion");
			if (accion == null || accion.isEmpty()) {
				response.sendRedirect("vistas/web_mensaje.jsp?mensaje=El sistema no reconoce esta Accion");
			} else if (accion.equals("listado")) {// LISTAR TODOS LOS PRODUCTOS
				ProductoDao daoproducto = new ProductoDao();
				List<Producto> listaproducto = daoproducto.listar(null);
				request.setAttribute("listaproductos", listaproducto);
				getServletContext()
						.getRequestDispatcher("/listarproductos.jsp").forward(
								request, response);
			} else if (accion.equals("listadoPorCategoria")) {// LISTAR SEGUN LA
																// CATEGORIA
																// ELEGIDA
				ProductoDao daoproducto = new ProductoDao();
				List<Producto> listaproducto = daoproducto.listar(Integer
						.valueOf(request.getParameter("id")));
				request.setAttribute("listaproductos", listaproducto);
				getServletContext()
						.getRequestDispatcher("/listarproductos.jsp").forward(
								request, response);

			} else if (accion.equals("insertarcarrito")) { // INSERTAR PRODUCTO
															// AL CARRITO
				ProductoDao daoproducto = new ProductoDao();
				Pedido pedido = (Pedido) sesion.getAttribute("carrito");
				if (pedido.getProductos().add(
						daoproducto.buscar(Integer.valueOf(request
								.getParameter("id")))))
					System.out.println("producto ingresado");
				List<Producto> listaproducto = daoproducto.listar(Integer
						.valueOf(request.getParameter("catid")));
				request.setAttribute("listaproductos", listaproducto);
				getServletContext()
						.getRequestDispatcher("/listarproductos.jsp").forward(
								request, response);
			} else if (accion.equals("eliminarcarrito")) { // ELIMINAR PRODUCTO
															// DEL CARRITO
				Pedido pedido = (Pedido) sesion.getAttribute("carrito");
				List<Producto> prodencarrito = pedido.getProductos();
				for (Producto prod : prodencarrito) {
					if (prod.getId() == Integer.parseInt(request
							.getParameter("id"))) {
						prodencarrito.remove(prod);
						System.out.println("producto removido");
						break;
					} else
						System.out.println("error al remover el producto");
				}
				getServletContext()
						.getRequestDispatcher("/consultarpedido.jsp").forward(
								request, response);
			} else if (accion.equals("Buscar")) { // BUSCADOR DE PRODUCTOS
				ProductoDao daoproducto = new ProductoDao();
				List<Producto> listaproducto = daoproducto.buscador(
						request.getParameter("txtbuscar"),
						Integer.valueOf(request.getParameter("categoria")));
				request.setAttribute("listaproducto", listaproducto);
				getServletContext().getRequestDispatcher(
						"/eliminarproducto.jsp").forward(request, response);

			} else if (accion.equals("eliminarProducto")) { // ELIMINAR PRODUCTO
				ProductoDao daoproducto = new ProductoDao();
				if (daoproducto.eliminar(Integer.valueOf(request
						.getParameter("id")))) {
					String mensaje = "El producto se elimino correctamente";
					sesion.setAttribute("mensaje", mensaje);
					getServletContext().getRequestDispatcher(
							"/buscarproducto.jsp").forward(request, response);
				} else {
					System.out.println("Problema al eliminar producto");
					String error = "Ocurrio un problema. Por favor verifique la informacion y llene los campos correctamente";
					sesion.setAttribute("error", error);
					getServletContext().getRequestDispatcher(
							"/buscarproducto.jsp").forward(request, response);
				}
			} else if (accion.equals("Agregar")) { // REGISTRAR PRODUCTO
				ProductoDao daoproducto = new ProductoDao();
				if (existeProducto(request.getParameter("txtnombre"))) {
					System.out.println("El usuario existe");
					String error = "El nombre de usuario ya existe, por favor ingrese otro e intente de nuevo";
					sesion.setAttribute("error", error);
					getServletContext().getRequestDispatcher(
							"/registrarusuario.jsp").forward(request, response);
				} else {
					Double precio = Double.valueOf(request.getParameter("txtprecio"));
					Boolean flag = daoproducto.insertar(
							request.getParameter("txtnombre"),
							request.getParameter("txtdesc"),
							precio,
							Integer.valueOf(request.getParameter("categoria")),
							null);
					if (flag) {
						String mensaje="El producto se creó correctamente";
      					sesion.setAttribute("mensaje", mensaje);
						System.out.println("Producto Registrado");
						getServletContext().getRequestDispatcher("/crearproducto.jsp").forward(request, response);
					} else {
						System.out.println("Problema al crear producto");
						String error = "Ocurrio un problema. Por favor verifique la informacion y llene los campos correctamente";
						sesion.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/crearproducto.jsp").forward(request, response);
					}
				}
			}

		} catch (Exception error) {
			error.printStackTrace();
			response.sendRedirect("vistas/web_mensaje.jsp?mensaje="
					+ error.getMessage());
		} finally {
			out.close();
		}
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	public Boolean existeProducto(String nomb) throws Exception {
		ProductoDao daoproducto = new ProductoDao();
		List<Producto> listaproductos = daoproducto.listar(null);
		if (listaproductos == null) {
			return false;
		} else {
			for (Producto prod : listaproductos) {
				if (prod.getNombre().equals(nomb)) {
					return true;
				}
			}
		}
		return false;
	}
}
