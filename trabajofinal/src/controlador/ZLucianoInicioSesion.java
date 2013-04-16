package controlador;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UsuarioDao;

import modelo.Usuario;

/**
 * Servlet implementation class InicioSesion
 */
public class ZLucianoInicioSesion extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ZLucianoInicioSesion() {
		super();
		// TODO Auto-generated constructor stub
	}
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user=request.getParameter("v_nombre");
		String pass=request.getParameter("v_pass");
		Usuario usuario=new Usuario();
		UsuarioDao userDao=new UsuarioDao();
		List<Usuario>usuarios;
		usuarios=userDao.listar();
		for(int i=0;i<usuarios.size();i++){
			usuario=usuarios.get(i);
			if(usuario.getUser().equals(user)){
				if(usuario.getPass().equals(pass)){
					HttpSession session = request.getSession();
					session.setAttribute("usuario", usuario);
					getServletContext().getRequestDispatcher("/home.jsp").forward(request, response);
					return;
				}
			}
		}
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
