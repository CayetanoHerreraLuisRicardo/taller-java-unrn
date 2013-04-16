package controlador;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelo.*;
import dao.*;

/**
 * Servlet implementation class HomeController
 */
public class ZLucianoHomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ZLucianoHomeController() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Lista las Categorías
		CategoriaDao daoCat=new CategoriaDao();
		List<Categoria> listaCategoria=daoCat.listar();
		HttpSession session = request.getSession();
		session.setAttribute("listacategorias", listaCategoria);
		//Servlet en sí
		//Creación del usuario estándar
		Usuario user=new Usuario();
		RolDao rol=new RolDao();
		user.setRol(rol.buscar(3));
		user.setUser(null);
		user.setApellido(null);
		user.setId(null);
		user.setMail(null);
		user.setNombre(null);
		user.setPass(null);
		user.setPedidos(null);
		session.setAttribute("usuario", user);
		getServletContext().getRequestDispatcher("/home.jsp").forward(request, response);
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
