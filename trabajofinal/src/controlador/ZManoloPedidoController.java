package controladores;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelos.Pedido;
import modelos.Producto;
import modelos.Usuario;
import daos.PedidoDao;
import daos.ProductoDao;

/**
 * Servlet implementation class PedidoController
 */
public class ZManoloPedidoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PedidoController() {
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
	    		}
	    		else if (accion.equals("listado")) {
	    			PedidoDao daopedido = new PedidoDao();
	    			List<Pedido> listapedido = daopedido.listar(null);
	    			request.setAttribute("listapedido", listapedido);
	    			getServletContext().getRequestDispatcher("/listarpedido.jsp").forward(request, response);
	    		}
	    		else if (accion.equals("crearPedido")){//CREAR PEDIDO
	    			PedidoDao daopedido = new PedidoDao();
					Pedido pedido = (Pedido) sesion.getAttribute("carrito");
					Usuario usu = (Usuario) sesion.getAttribute("usulog");
					int idusu = usu.getId();
					int idped = Integer.valueOf(request.getParameter("idPed"));
					List<Producto> listaprod = pedido.getProductos();
					java.util.Date date = new java.util.Date();
					java.text.DateFormat formfecha = new java.text.SimpleDateFormat ("yyyy-MM-dd");
					String fecha = formfecha.format(date);
					Boolean flag = daopedido.insertar(idped, "Pendiente", fecha, fecha,	idusu, listaprod);
					if (flag) {
						String mensaje="El pedido se creó correctamente";
      					sesion.setAttribute("mensaje", mensaje);
						System.out.println("Producto Registrado");
						getServletContext().getRequestDispatcher("/comprar.jsp").forward(request, response);
					} else {
						System.out.println("Problema al crear pedido");
						String error = "Ocurrio un problema. Por favor verifique la informacion y llene los campos correctamente";
						sesion.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/comprar.jsp").forward(request, response);
					}
				}

	    	} catch (Exception error) {
	    		error.printStackTrace();
	    		response.sendRedirect("vistas/web_mensaje.jsp?mensaje=" + error.getMessage());
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

}
