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
		//Servlet en sí
		try{
			String accion=request.getParameter("accion");
			if(accion==null || accion.isEmpty()){
				response.sendRedirect("/vista/web_mensaje.jsp?mensaje=El sistema no reconoce esta Acción");
			}else{
				//Nota: nunca lo usé.
				if(accion.equals("lista")){
					UsuarioDao daoUser=new UsuarioDao();
					List<Usuario> listaUsuario=daoUser.listar();
					request.setAttribute("listausuarios", listaUsuario);
					getServletContext().getRequestDispatcher("/vista/usuarioListar.jsp").forward(request, response);
				}
				//
				//Guardar
				//
				if(accion.equals("guardar")){
					//Verifica el rol del usuario
					Usuario usuario=(Usuario) session.getAttribute("usuario");
					Rol rol=usuario.getRol();
					if(rol.getId() != 1 || rol.getId() == null){
						response.sendRedirect("HomeController");
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="Ud. no es administrador, no puede realizar dicha acción.";
						request.setAttribute("error", error);
						return;
					}
					//Lista los Usuarios
					UsuarioDao usDao=new UsuarioDao();
					List<Usuario>usuarios=usDao.listar();
					//Nota: tu existe.
					for(int i=0;i<usuarios.size();i++){
						Usuario usTemp=usuarios.get(i);
						if(usTemp.getUser().contains(request.getParameter("v_user"))){
							Boolean exito=false;
							request.setAttribute("exito", exito);
							String error="El nick ya está usado por otra persona, por favor, ingrese otro.";
							request.setAttribute("error", error);
							getServletContext().getRequestDispatcher("/vista/usuarioAlta.jsp").forward(request, response);
							return;
						}
					}
					Usuario us=new Usuario();
					us.setNombre(request.getParameter("v_nombre"));
					us.setApellido(request.getParameter("v_apellido"));
					us.setUser(request.getParameter("v_user"));
					us.setPass(request.getParameter("v_pass"));
					us.setMail(request.getParameter("v_mail"));
					rol=new Rol();
					rol.setId(2);
					us.setRol(rol);
					Integer id=usDao.guardar(us);
					if(id != -1){
						Boolean exito=true;
						request.setAttribute("exito", exito);
						String error="Se cargó correctamente el usuario.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/vista/usuarioAlta.jsp").forward(request, response);
						return;
					}else{
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="Hubo un problema al crear el usuario.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/vista/usuarioAlta.jsp").forward(request, response);
					}
				}
				//
				//Eliminar
				//
				if(accion.equals("eliminar")){
					//Verifica el rol del usuario
					Usuario usuario=(Usuario) session.getAttribute("usuario");
					Rol rol=usuario.getRol();
					if(rol.getId() != 1 || rol.getId() == null){
						response.sendRedirect("HomeController");
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="Ud. no es administrador, no puede realizar dicha acción.";
						request.setAttribute("error", error);
						return;
					}
					//Listar usuarios
					String users=request.getParameter("us");
					Integer id=Integer.parseInt(users);
					UsuarioDao usDao=new UsuarioDao();
					Integer borrar=usDao.eliminar(id);
					if(borrar != -1){
						Boolean exito=true;
						request.setAttribute("exito", exito);
						String error="El usuario ha sido borrado correctamente.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/vista/usuarioEliminar.jsp").forward(request, response);
					}else{
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="El usuario no pudo ser borrado.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/vista/usuarioEliminar.jsp").forward(request, response);
					}
				}
				//Modificar
				if(accion.equals("modificar")){
					//Verifica el rol del usuario
					Usuario usuario=(Usuario) session.getAttribute("usuario");
					Rol rol=usuario.getRol();
					if(rol.getId() != 1 || rol.getId() == null){
						response.sendRedirect("HomeController");
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="Ud. no es administrador, no puede realizar dicha acción.";
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
								if(us.getId()==null){
									Boolean exito=false;
									request.setAttribute("error", exito);
									String error="El id es nulo, no se puede modificar un usuario si su identificador es nulo.";
									request.setAttribute("error", error);
									getServletContext().getRequestDispatcher("/vista/usuarioModif.jsp").forward(request, response);
									return;
								}
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
									String error="El usuario se modificó correctamente.";
									request.setAttribute("error", error);
									session.setAttribute("usuario", us);
								}else{
									Boolean exito=false;
									request.setAttribute("exito", exito);
									String error="Hubo un problema al acceso a la Base de Datos.";
									request.setAttribute("error", error);
								}
								getServletContext().getRequestDispatcher("/vista/usuarioModif.jsp").forward(request, response);
								return;
							}else{
								Boolean exito=false;
								request.setAttribute("exito", exito);
								String error="La contraseña antigua es incorrecta.";
								request.setAttribute("error", error);
								getServletContext().getRequestDispatcher("/vista/usuarioModif.jsp").forward(request, response);
								return;
							}
						}
					}
					Boolean exito=false;
					request.setAttribute("exito", exito);
					String error="El usuario no existe. Cárguelo previo a intentar modificarlo.";
					request.setAttribute("error", error);
					getServletContext().getRequestDispatcher("/vista/usuarioModif.jsp").forward(request, response);
					return;
				}
				//
				//Iniciar sesión
				//
				if(accion.equals("loginUsuario")){
					//Nota: podríamos tener variaciones en la nomenclatura de variables
					Integer id=validarUsuario(request.getParameter("v_user"), request.getParameter("v_pass"));
					if(id != -1){
						Usuario usuario = new Usuario();
						UsuarioDao daousuario = new UsuarioDao();
						usuario = daousuario.buscar(id);
						session.setAttribute("usuario", usuario);
						//Nota: podría variar
						getServletContext().getRequestDispatcher("/vista/home.jsp?nombre=" + request.getParameter("v_user")).forward(request, response);
					}
					else{
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error= "El usuario y/o contraseña son incorrectos.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/vista/home.jsp").forward(request, response);
					}
				}
				//
				//Cerrar Sesión
				//
				else if(accion.equals("cerrarSesion")){ 
					session.invalidate();
					getServletContext().getRequestDispatcher("/vista/home.jsp").forward(request, response);
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			response.sendRedirect("/vista/web_mensaje.jsp?mensaje="+e.getMessage());
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
}
