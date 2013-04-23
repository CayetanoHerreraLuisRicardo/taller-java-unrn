package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelo.Pedido;
import modelo.Producto;
import modelo.Rol;
import modelo.Usuario;
import dao.ProductoDao;
import dao.UsuarioDao;

/**
 * Servlet implementation class UsuarioController
 */
public class ZManoloUsuarioController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private	int idusuario = 0;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ZManoloUsuarioController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void processRequest(HttpServletRequest request,
        	HttpServletResponse response) throws ServletException, IOException {
        	response.setContentType("text/html;charset=UTF-8");
        	PrintWriter out = response.getWriter();
        	HttpSession sesion = request.getSession();
        	Enumeration<String> verif=sesion.getAttributeNames();
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

        	try {
        		String accion = request.getParameter("accion");
        		if (accion == null || accion.isEmpty()) {
        				response.sendRedirect("vistas/web_mensaje.jsp?mensaje=El sistema no reconoce esta Accion");
        		}
        		//REGISTRAR USUARIO --- modificado ver
        		else if (accion.equals("registrarUsuario")) {
        			Usuario usuario=(Usuario) sesion.getAttribute("usuario");
					Rol rol=usuario.getRol();
        			if(rol.getId() != 1){
						response.sendRedirect("HomeController");
						String error="Ud. no es administrador, no puede realizar dicha acci�n.";
						request.setAttribute("error", error);
						return;
					}
        			UsuarioDao daousuario= new UsuarioDao();
        			if(existeUsuario(request.getParameter("txtnombre"))){
        				System.out.println("El usuario existe");
        				String error="El nombre de usuario ya existe, por favor ingrese otro e intente de nuevo";
        				sesion.setAttribute("error", error);
        				getServletContext().getRequestDispatcher("/registrarusuario.jsp").forward(request, response);
        			}
        			else{
        				Usuario us = new Usuario();
        				us.setNombre("txtnombre");
        				us.setApellido("txtapellido");
        				us.setMail("txtmail");
        				us.setUser("txtusuario");
        				us.setPass("txtpassword");
        				rol = new Rol();
        				rol.setId(2);
        				us.setRol(rol);
        				Integer flag = daousuario.guardar(us);
            			if(flag != -1){
            				String nombre="" + request.getParameter("txtapellido")+ ", " + request.getParameter("txtnombre");
            				System.out.println("Usuario Registrado");
            				getServletContext().getRequestDispatcher("/usuarioregistrado.jsp?nombre=" + nombre).forward(request, response);
            			}
            			else{
            				System.out.println("Problema al crear el usuario");
            				String error="Ocurrio un problema. Por favor verifique la informacion y llene los campos correctamente";
            				sesion.setAttribute("error", error);
            				getServletContext().getRequestDispatcher("/registrarusuario.jsp").forward(request, response);
            			}
        			}
        			
        		}
        		// FIN REGISTRAR USUARIO
        		//INICIAR SESION
        		else if(accion.equals("loginUsuario")){
        			if(validarUsuario(request.getParameter("nombusuario"), request.getParameter("passusuario"))){
        				Usuario usuario = new Usuario();
        				UsuarioDao daousuario = new UsuarioDao();
        				usuario = daousuario.buscar(idusuario);
        				sesion.setAttribute("usuario", usuario);
        				getServletContext().getRequestDispatcher("/home.jsp?nombre=" + request.getParameter("nombusuario")).forward(request, response);
        			}
        			else{
        				String error= "El usuario y/o contrase�a son incorrectos";
        				sesion.setAttribute("error", error);
        				getServletContext().getRequestDispatcher("/home.jsp").forward(request, response);
        			}
        		}
        		//FIN INICIAR SESION
        		//EDITAR USUARIO
        		else if(accion.equals("modificarUsuario")){
        			
        		}
        		//CERRAR SESION
        		else if(accion.equals("cerrarSesion")){ 
        			sesion.invalidate();
        			getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
        		}
        		//FIN CERRAR SESION
        		//CREAR USUARIO
        		else if (accion.equals("crearUsuario")) {
        			Usuario usuario=(Usuario) sesion.getAttribute("usuario");
					Rol rol=usuario.getRol();
					if(rol.getId() != 1 || rol.getId() == null){
						response.sendRedirect("HomeController");
						String error="Ud. no es administrador, no puede realizar dicha acci�n.";
						request.setAttribute("error", error);
						return;
					}
        			UsuarioDao daousuario= new UsuarioDao();
        			if(existeUsuario(request.getParameter("txtnombre"))){
        				System.out.println("El usuario existe");
        				String error="El nombre de usuario ya existe, por favor ingrese otro e intente de nuevo";
        				sesion.setAttribute("error", error);
        				getServletContext().getRequestDispatcher("/registrarusuario.jsp").forward(request, response);
        			}
        			else{
        				Usuario us = new Usuario();
        				us.setNombre("txtnombre");
        				us.setApellido("txtapellido");
        				us.setMail("txtmail");
        				us.setUser("txtusuario");
        				us.setPass("txtpassword");
        				rol = new Rol();
        				rol.setId(Integer.valueOf(request.getParameter("rol")));
        				us.setRol(rol);
        				Integer flag = daousuario.guardar(us);
        				if(flag != -1){
            				String mensaje="Usuario registrado con exito";
            				sesion.setAttribute("mesnaje", mensaje);
            				System.out.println("Usuario Registrado");
            				getServletContext().getRequestDispatcher("/registrarusuario.jsp").forward(request, response);
            			}
            			else{
            				System.out.println("Problema al crear el usuario");
            				String error="Ocurrio un problema. Por favor verifique la informacion y llene los campos correctamente";
            				sesion.setAttribute("error", error);
            				getServletContext().getRequestDispatcher("/registrarusuario.jsp").forward(request, response);
            			}
        			}        			
        		}
        		//FIN CREAR USUARIO
        		//EDITAR PASSWORD
        		else if(accion.equals("editarPassword")){ 
        			UsuarioDao daousuario= new UsuarioDao();
        			String pass = request.getParameter("txtpasswordnuevo");
        			Usuario usu = (Usuario) sesion.getAttribute("usuario");
        			if(usu.getPass().equals(request.getParameter("txtpasswordviejo"))){
	        			if(pass.length() < 6){
	        				String error="El password debe tener por lo menos 6 caracteres";
	        				sesion.setAttribute("error", error);
	        				getServletContext().getRequestDispatcher("/micuenta.jsp").forward(request, response);
	        			}
	        			else{
	        				if(daousuario.cambiarPassword(pass, usu.getId())){
	        					String mensaje = "El password se cambio correctamente";
	        					sesion.setAttribute("mensaje", mensaje);
	        					getServletContext().getRequestDispatcher("/micuenta.jsp").forward(request, response);
	        				}
	        				else{
	        					System.out.println("Problema al cambiar el password del usuario");
	            				String error="Ocurrio un problema. Por favor verifique la informacion y llene los campos correctamente";
	            				sesion.setAttribute("error", error);
	            				getServletContext().getRequestDispatcher("/micuenta.jsp").forward(request, response);
	        				}
	        			}
        			}
        			else{
        				String error="Password incorrecto";
        				sesion.setAttribute("error", error);
        				getServletContext().getRequestDispatcher("/micuenta.jsp").forward(request, response);
        			}
        		}
        		//FIN EDITAR PASSWORD
        		else if(accion.equals("Buscar")){ //BUSCADOR DE USUARIOS
        			UsuarioDao daousuario= new UsuarioDao();
        			List<Usuario> listausuario = daousuario.buscador(request.getParameter("txtbuscar"));
        			request.setAttribute("listausuario", listausuario);
        			getServletContext().getRequestDispatcher("/eliminarusuario.jsp").forward(request, response);
        		}
        		else if(accion.equals("eliminarusuario")){
        			Usuario usuario=(Usuario) sesion.getAttribute("usuario");
					Rol rol=usuario.getRol();
					if(rol.getId() != 1 || rol.getId() == null){
						response.sendRedirect("HomeController");
						String error="Ud. no es administrador, no puede realizar dicha acci�n.";
						request.setAttribute("error", error);
						return;
					}
        			UsuarioDao daousuario= new UsuarioDao();
        			Integer flag = daousuario.eliminar(Integer.valueOf(request.getParameter("id")));
        			if(flag != -1){
    					String mensaje = "El usuario se elimino correctamente";
    					sesion.setAttribute("mensaje", mensaje);
    					getServletContext().getRequestDispatcher("/buscador.jsp").forward(request, response);
    				}
    				else{
    					System.out.println("Problema al cambiar el password del usuario");
        				String error="Ocurrio un problema. Por favor verifique la informacion y llene los campos correctamente";
        				sesion.setAttribute("error", error);
        				getServletContext().getRequestDispatcher("/buscador.jsp").forward(request, response);
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
        	
        protected Boolean validarUsuario(String nombre, String pass) throws Exception{
        	UsuarioDao daousuario = new UsuarioDao();
			List<Usuario> listausuarios = daousuario.listar();
			if (listausuarios == null){
				return false;
			}
			else{
				for (Usuario usu : listausuarios) {
					if(usu.getUser().equals(nombre) && usu.getPass().equals(pass)){
						idusuario = usu.getId();
						return true;
					}
				}
			}
			return false;
        }
       
        public Boolean existeUsuario(String nomb) throws Exception{
        	UsuarioDao daousuario = new UsuarioDao();
			List<Usuario> listausuarios = daousuario.listar();
			if (listausuarios == null){
				return false;
			}
			else{
				for (Usuario usu : listausuarios) {
					if(usu.getUser().equals(nomb)){
						return true;
					}
				}
			}
			return false;
        }

}
