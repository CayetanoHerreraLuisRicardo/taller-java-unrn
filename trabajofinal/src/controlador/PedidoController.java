package controlador;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelo.*;
import dao.*;

/**
 * Servlet implementation class PedidoController
 */
public class PedidoController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PedidoController() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		//Servlet en s�
		try{
			String accion=request.getParameter("accion");
			if(accion==null || accion.isEmpty()){
				Boolean exito=false;
				request.setAttribute("exito", exito);
				String error="El sistema no reconoce esta Acci�n.";
				request.setAttribute("error", error);
				getServletContext().getRequestDispatcher("/HomeController").forward(request, response);
			}else{
				//
				//Lista
				//
				if(accion.equals("lista")){
					Usuario user=(Usuario) session.getAttribute("usuario");
					PedidoDao daoPedido=new PedidoDao();
					List<Pedido> listaPedido=daoPedido.listar();
					List<Pedido> pedsUser=new ArrayList<Pedido>();
					for(Pedido pedTemp:listaPedido){
						if(pedTemp.getUsuario().getId()==user.getId()){
							pedsUser.add(pedTemp);
						}
					}
					if(pedsUser.size()==0){
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="No ha realizado una compra en nuestra web.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/home.jsp").forward(request, response);
						return;
					}
					request.setAttribute("pedidos", pedsUser);
					getServletContext().getRequestDispatcher("/pedidoListar.jsp").forward(request, response);
				}
				//
				//Guardar
				//
				if(accion.equals("guardar")){
					//Verifica el rol del usuario
					Usuario usuario=(Usuario) session.getAttribute("usuario");
					if(usuario == null){
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="Ud. es un invitado, inicie sesi�n antes de intentar realizar una compra.";
						request.setAttribute("error", error);
						response.sendRedirect("HomeController");
						return;
					}
					@SuppressWarnings("unchecked")
					Hashtable<Producto,Integer>productos=(Hashtable<Producto,Integer>) session.getAttribute("carrito");
					session.removeAttribute("carrito");//Previene repagos de una misma factura
					usuario=(Usuario) session.getAttribute("usuario");
					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
					Date date=new Date();
					String fechaPedido=dateFormat.format(date);
					String fechaEntrega=dateFormat.format(date);
					PedidoDao pedDao=new PedidoDao();
					Pedido pedido=new Pedido();
					pedido.setFechaEntrega(fechaEntrega);
					pedido.setFechaPedido(fechaPedido);
					pedido.setProductos(productos);
					pedido.setUsuario(usuario);
					Integer carga=pedDao.guardar(pedido);
					//Reseteo del carrito
					Hashtable<Producto,Integer>carrito=new Hashtable<Producto,Integer>();
					session.setAttribute("carrito",carrito);
					if(carga != -1){
						Boolean exito=true;
						request.setAttribute("exito", exito);
						String error="Su compra ha sido realizada con �xito.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/home.jsp").forward(request, response);
						return;
					}else{
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="Hubo un error en la facturaci�n, por favor intente de nuevo m�s tarde.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/home.jsp").forward(request, response);
					}
				}
				//Eliminar
				if(accion.equals("eliminar")){
					//Verifica el rol del usuario
					Usuario usuario=(Usuario) session.getAttribute("usuario");
					Rol rol=usuario.getRol();
					if(rol.getId() != 1){
						response.sendRedirect("HomeController");
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="Ud. no es administrador, no puede realizar dicha acci�n.";
						request.setAttribute("error", error);
						return;
					}
					PedidoDao daoPedido=new PedidoDao();
					Pedido pedido=(Pedido) request.getAttribute("pedido");
					Integer resultado=daoPedido.eliminar(pedido.getId());
					if(resultado != -1){
						//No se qu� debe avisar en cada caso
					}
				}
				//Modificar
				if(accion.equals("modificar")){
					//Verifica el rol del usuario
					Usuario usuario=(Usuario) session.getAttribute("usuario");
					Rol rol=usuario.getRol();
					if(rol.getId() != 1){
						response.sendRedirect("HomeController");
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="Ud. no es administrador, no puede realizar dicha acci�n.";
						request.setAttribute("error", error);
						return;
					}
					PedidoDao daoPedido=new PedidoDao();
					Integer resultado=daoPedido.modificar(request.getAttribute("pedido"));
					if(resultado != -1){
						//No se qu� debe avisar en cada caso
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
