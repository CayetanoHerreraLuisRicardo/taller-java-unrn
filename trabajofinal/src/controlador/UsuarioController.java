package controlador;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelo.Rol;
import modelo.Usuario;
import dao.UsuarioDao;

/**
 * Servlet implementation class UsuarioController
 */
public class UsuarioController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UsuarioController() {
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
					UsuarioDao daoUser=new UsuarioDao();
					List<Usuario> listaUsuario=daoUser.listar();
					request.setAttribute("listausuarios", listaUsuario);
					getServletContext().getRequestDispatcher("/usuarioListar.jsp").forward(request, response);
				}
				//
				//Guardar
				//
				if(accion.equals("guardar")){
					Usuario usuario=(Usuario) session.getAttribute("usuario");
					if(usuario==null){
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="Ud. no es administrador, no puede realizar dicha acci�n.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/HomeController").forward(request, response);
						return;
					}
					//Lista los Usuarios
					UsuarioDao usDao=new UsuarioDao();
					List<Usuario>usuarios=usDao.listar();
					//Nota: funci�n Manuel existe.
					for(int i=0;i<usuarios.size();i++){
						Usuario usTemp=usuarios.get(i);
						if(usTemp.getUser().contains(request.getParameter("v_user")) || usTemp.getUser().contains(request.getParameter("v_mail"))){
							Boolean exito=false;
							request.setAttribute("exito", exito);
							String error="El nick o mail ya est� usado por otra persona, por favor, ingrese otro.";
							request.setAttribute("error", error);
							getServletContext().getRequestDispatcher("/usuarioAlta.jsp").forward(request, response);
							return;
						}
					}
					Usuario us=new Usuario();
					us.setNombre(request.getParameter("v_nombre"));
					us.setApellido(request.getParameter("v_apellido"));
					if(validarNick(request.getParameter("v_user")))us.setUser(request.getParameter("v_user"));
					else{
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="Por favor, evite poner caracteres no aceptados.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/usuarioAlta.jsp").forward(request, response);
						return;
					}
					if(validarPass(request.getParameter("v_pass")))us.setPass(request.getParameter("v_pass"));
					else{
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="Por favor, evite poner caracteres no aceptados.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/usuarioAlta.jsp").forward(request, response);
						return;
					}
					us.setMail(request.getParameter("v_mail"));
					Rol rol=new Rol();
					rol.setId(2);
					us.setRol(rol);
					Integer id=usDao.guardar(us);
					if(id != -1){
						Boolean exito=true;
						request.setAttribute("exito", exito);
						String error="Se guard� correctamente el usuario.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/usuarioAlta.jsp").forward(request, response);
						return;
					}else{
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="Hubo un problema al crear el usuario.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/usuarioAlta.jsp").forward(request, response);
					}
				}
				//
				//Buscar usuarios
				//
				else if(accion.equals("buscar")){
        			UsuarioDao daousuario= new UsuarioDao();
        			List<Usuario> listaUsuario = daousuario.buscador(request.getParameter("v_buscar"));
        			request.setAttribute("listausuarios", listaUsuario);
        			getServletContext().getRequestDispatcher("/usuarioListar.jsp").forward(request, response);
        		}
				//
				//Eliminar
				//
				if(accion.equals("eliminar")){
					//Verifica el rol del usuario
					Usuario usuario=(Usuario) session.getAttribute("usuario");
					if(usuario==null){
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="Ud. no es administrador, no puede realizar dicha acci�n.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/HomeController").forward(request, response);
						return;
					}
					Rol rol=usuario.getRol();
					if(rol.getId() != 1 || rol.getId() == null){
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="Ud. no es administrador, no puede realizar dicha acci�n.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/HomeController").forward(request, response);
						return;
					}
					String users=request.getParameter("userID");
					Integer id=Integer.parseInt(users);
					if(id==usuario.getId()){
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="No puede borrar una cuenta con sesi�n activa.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/UsuarioController?accion=lista").forward(request, response);
						return;
					}
					UsuarioDao usDao=new UsuarioDao();
					Integer borrar=usDao.eliminar(id);
					if(borrar != -1){
						Boolean exito=true;
						request.setAttribute("exito", exito);
						String error="El usuario ha sido borrado correctamente.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/UsuarioController?accion=lista").forward(request, response);
					}else{
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="El usuario no pudo ser borrado.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/UsuarioController?accion=lista").forward(request, response);
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
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="Ud. no es administrador, no puede realizar dicha acci�n.";
						request.setAttribute("error", error);
						return;
					}
					Rol rol=usuario.getRol();
					if(rol.getId() == null){
						response.sendRedirect("HomeController");
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="Ud. no es administrador, no puede realizar dicha acci�n.";
						request.setAttribute("error", error);
						return;
					}
					//Lista los Usuarios
					UsuarioDao usDao=new UsuarioDao();
					List<Usuario>usuarios=usDao.listar();
					for(int i=0;i<usuarios.size();i++){
						Usuario usTemp=usuarios.get(i);
						if(usTemp.getUser().equals(request.getParameter("v_user"))){
							if(usTemp.getPass().equals(request.getParameter("v_pass"))){
								Usuario us=new Usuario();
								us.setId(usTemp.getId());
								us.setUser(request.getParameter("v_user"));
								us.setPass(request.getParameter("v_pass1"));
								us.setPedidos(usTemp.getPedidos());
								us.setRol(usTemp.getRol());
								us.setNombre(request.getParameter("v_nombre_m"));
								us.setApellido(request.getParameter("v_apellido_m"));
								us.setMail(request.getParameter("v_mail_m"));
								Integer modif=usDao.modificar(us);
								if(modif != -1){
									Boolean exito=true;
									request.setAttribute("exito", exito);
									String error="El usuario se modific� correctamente.";
									request.setAttribute("error", error);
									session.setAttribute("usuario", us);
								}else{
									Boolean exito=false;
									request.setAttribute("exito", exito);
									String error="Hubo un problema al acceso a la Base de Datos.";
									request.setAttribute("error", error);
								}
								getServletContext().getRequestDispatcher("/usuarioModif.jsp").forward(request, response);
								return;
							}else{
								Boolean exito=false;
								request.setAttribute("exito", exito);
								String error="La contrase�a antigua es incorrecta.";
								request.setAttribute("error", error);
								getServletContext().getRequestDispatcher("/usuarioModif.jsp").forward(request, response);
								return;
							}
						}
					}
					Boolean exito=false;
					request.setAttribute("exito", exito);
					String error="El usuario no existe. C�rguelo previo a intentar modificarlo.";
					request.setAttribute("error", error);
					getServletContext().getRequestDispatcher("/usuarioModif.jsp").forward(request, response);
					return;
				}
				//
				//Admin modifica usuario
				//
				if(accion.equals("adminEdit")){
					//Verifica el rol del usuario
					Usuario usuario=(Usuario) session.getAttribute("usuario");
					if(usuario==null){
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="Ud. no es administrador, no puede realizar dicha acci�n.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/HomeController").forward(request, response);
						return;
					}
					Rol rol=usuario.getRol();
					if(rol.getId() == null){
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="Ud. no es administrador, no puede realizar dicha acci�n.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/HomeController").forward(request, response);
						return;
					}
					//Lista los Usuarios
					UsuarioDao usDao=new UsuarioDao();
					List<Usuario>usuarios=usDao.listar();
					for(int i=0;i<usuarios.size();i++){
						Usuario usTemp=usuarios.get(i);
						Integer id=Integer.parseInt(request.getParameter("userID"));
						if(usTemp.getId() == id){
							request.setAttribute("usuarioTemp", usTemp);
							getServletContext().getRequestDispatcher("/usuarioAdminModif.jsp").forward(request, response);
						}
					}
				}
				if(accion.equals("adminModif")){
					//Verifica el rol del usuario
					Usuario usuario=(Usuario) session.getAttribute("usuario");
					if(usuario==null){
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="Ud. no es administrador, no puede realizar dicha acci�n.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/HomeController").forward(request, response);
						return;
					}
					Rol rol=usuario.getRol();
					if(rol.getId() == null){
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="Ud. no es administrador, no puede realizar dicha acci�n.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/HomeController").forward(request, response);
						return;
					}
					//Lista los Usuarios
					UsuarioDao usDao=new UsuarioDao();
					List<Usuario>usuarios=usDao.listar();
					for(int i=0;i<usuarios.size();i++){
						Usuario usTemp=usuarios.get(i);
						if(usTemp.getUser().equals(request.getParameter("v_user"))){
							Usuario us=new Usuario();
							us.setId(usTemp.getId());
							us.setUser(request.getParameter("v_user"));
							us.setPass(request.getParameter("v_pass1"));
							us.setPedidos(usTemp.getPedidos());
							us.setRol(usTemp.getRol());
							us.setNombre(request.getParameter("v_nombre_m"));
							us.setApellido(request.getParameter("v_apellido_m"));
							us.setMail(request.getParameter("v_mail_m"));
							Integer modif=usDao.modificar(us);
							if(modif != -1){
								Boolean exito=true;
								request.setAttribute("exito", exito);
								String error="El usuario se modific� correctamente.";
								request.setAttribute("error", error);
							}else{
								Boolean exito=false;
								request.setAttribute("exito", exito);
								String error="Hubo un problema al acceso a la Base de Datos.";
								request.setAttribute("error", error);
							}
							getServletContext().getRequestDispatcher("/UsuarioController?accion=lista").forward(request, response);
							return;
						}
					}
					Boolean exito=false;
					request.setAttribute("exito", exito);
					String error="El usuario no existe. C�rguelo previo a intentar modificarlo.";
					request.setAttribute("error", error);
					getServletContext().getRequestDispatcher("/UsuarioController?accion=lista").forward(request, response);
					return;
				}
				//
				//Iniciar sesi�n
				//
				if(accion.equals("logIn")){
					//Nota: podr�amos tener variaciones en la nomenclatura de variables
					Integer id=validarUsuario(request.getParameter("v_user"), request.getParameter("v_pass"));
					if(id != -1){
						Usuario usuario = new Usuario();
						UsuarioDao daousuario = new UsuarioDao();
						usuario = daousuario.buscar(id);
						session.setAttribute("usuario", usuario);
						getServletContext().getRequestDispatcher("/home.jsp").forward(request, response);
					}
					else{
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error= "El usuario y/o contrase�a son incorrectos.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/home.jsp").forward(request, response);
					}
				}
				//
				//Cerrar Sesi�n
				//
				else if(accion.equals("logOut")){ 
					session.invalidate();
					getServletContext().getRequestDispatcher("/HomeController").forward(request, response);
				}
			}
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
			String error="El sistema no reconoce esta Acci�n.";
			request.setAttribute("error", error);
			getServletContext().getRequestDispatcher("/HomeController").forward(request, response);
		}else{
			//Nota: nunca lo us�.
			if(accion.equals("guardar") || accion.equals("modificar")){
				Boolean exito=false;
				request.setAttribute("exito", exito);
				String error= "Ud est� intentando realizar una operaci�n no permitida.";
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
	protected Integer validarUsuario(String nombre, String pass) throws Exception{
		UsuarioDao daousuario = new UsuarioDao();
		List<Usuario> listausuarios = daousuario.listar();
		if (listausuarios == null){
			return -1;
		}
		else{
			for (Usuario usu : listausuarios) {
				if(usu.getUser().equals(nombre) && usu.getPass().equals(pass)){
					return usu.getId();
				}
			}
		}
		return -1;
	}
	private Boolean validarNick(String nick){
		char[] c=nick.toCharArray();
		Boolean exito=false;
		for(int i=0;i<c.length;i++){
			if((c[i] > 96 && c[i] < 123) || (c[i] >= 48 && c[i] <= 57))exito=true;
			else return false;
		}
		return exito;
	}
	private Boolean validarPass(String pass){
		char[] c=pass.toCharArray();
		Boolean exito=false;
		for(int i=0;i<c.length;i++){
			if((c[i] > 33 && c[i] < 35) || (c[i] > 64 && c[i] < 91) || (c[i] > 96 && c[i] < 123) || (c[i] >= 48 && c[i] <= 57))exito=true;
			else return false;
		}
		return exito;
	}
}
