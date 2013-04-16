package controladores;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.Session;

import daos.CategoriaDao;
import daos.ProductoDao;

import modelos.Categoria;
import modelos.Pedido;
import modelos.Producto;
import modelos.Usuario;

/**
 * Servlet implementation class HomeController
 */
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //PrintWriter out = response.getWriter();
        try{
        	HttpSession sesion = request.getSession();
	        CategoriaDao daocategoria = new CategoriaDao();
	        //ProductoDao daoproducto = new ProductoDao();
	        List<Categoria> listacategorias = daocategoria.listar();
	        sesion.setAttribute("listacategorias", listacategorias);
	        sesion.setAttribute("error", new String());
	        sesion.setAttribute("mensaje", new String());
	        sesion.setAttribute("directorio", getServletContext().getRealPath("/imgProd"));
	        if(sesion.getAttribute("carrito")==null){
	        	sesion.setAttribute("carrito", new Pedido());
	        	sesion.setAttribute("usulog", null);
	        }
	        getServletContext().getRequestDispatcher("/home.jsp").forward(request, response);
        }
        catch(Exception e){
        	e.printStackTrace();        
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
