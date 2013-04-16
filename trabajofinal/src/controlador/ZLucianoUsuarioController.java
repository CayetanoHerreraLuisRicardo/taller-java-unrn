package controlador;

import java.io.*;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelo.*;
import dao.*;

/**
 * Servlet implementation class UsuarioControll
 */
public class ZLucianoUsuarioController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ZLucianoUsuarioController() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		//Verifica Existencia del usuario
		if(session.getAttribute("usuario") == null){
			response.sendRedirect("HomeController");
			return;
		}
		@SuppressWarnings("unchecked")
		Enumeration<String> verif=session.getAttributeNames();
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
		//Servlet en sí
		try{
			String accion=request.getParameter("accion");
			if(accion==null || accion.isEmpty()){
				response.sendRedirect("vistas/web_mensaje.jsp?mensaje=El sistema no reconoce esta Acción");
			}else{
				if(accion.equals("lista")){
					UsuarioDao daoUser=new UsuarioDao();
					List<Usuario> listaUsuario=daoUser.listar();
					request.setAttribute("listausuarios", listaUsuario);
					getServletContext().getRequestDispatcher("/WebController?url=/listaUsuarios.jsp").forward(request, response);
				}
				//Guardar
				if(accion.equals("guardar")){
					//Verifica el rol del usuario
					Usuario usuario=(Usuario) session.getAttribute("usuario");
					Rol rol=usuario.getRol();
					if(rol.getId() != 1){
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
					if(usuarios==null){
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
							getServletContext().getRequestDispatcher("/WebController?url=/usuarioAlta.jsp").forward(request, response);
							return;
						}else{
							Boolean exito=false;
							request.setAttribute("exito", exito);
							String error="Hubo un problema al crear el usuario.";
							request.setAttribute("error", error);
							getServletContext().getRequestDispatcher("/WebController?url=/usuarioAlta.jsp").forward(request, response);
						}
					}else{
						for(int i=0;i<usuarios.size();i++){
							Usuario usTemp=usuarios.get(i);
							if(usTemp.getUser().contains(request.getParameter("v_user"))){
								Boolean exito=false;
								request.setAttribute("exito", exito);
								String error="El nick ya está usado por otra persona, por favor, ingrese otro.";
								request.setAttribute("error", error);
								getServletContext().getRequestDispatcher("/WebController?url=/usuarioAlta.jsp").forward(request, response);
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
							getServletContext().getRequestDispatcher("/WebController?url=/usuarioAlta.jsp").forward(request, response);
							return;
						}else{
							Boolean exito=false;
							request.setAttribute("exito", exito);
							String error="Hubo un problema al crear el usuario.";
							request.setAttribute("error", error);
							getServletContext().getRequestDispatcher("/WebController?url=/usuarioAlta.jsp").forward(request, response);
						}
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
						getServletContext().getRequestDispatcher("/WebController?url=/usuarioEliminar.jsp").forward(request, response);
					}else{
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="El usuario no pudo ser borrado.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/WebController?url=/usuarioEliminar.jsp").forward(request, response);
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
						String error="Ud. no es administrador, no puede realizar dicha acción.";
						request.setAttribute("error", error);
						return;
					}
					//Lista los Usuarios
					UsuarioDao usDao=new UsuarioDao();
					List<Usuario>usuarios=usDao.listar();
					if(usuarios==null){
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="La lista de usuarios está vacía. Previo a la modificación de uno, cárguelo.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/WebController?url=/usuarioModif.jsp").forward(request, response);
					}else{
						for(int i=0;i<usuarios.size();i++){
							Usuario usTemp=usuarios.get(i);
							if(usTemp.getUser().equals(request.getParameter("v_user"))){
								if(usTemp.getPass().equals(request.getParameter("v_pass"))){
									Usuario us=new Usuario();
									us.setUser(request.getParameter("v_user"));
									us.setPass(request.getParameter("v_pass1"));
									us.setPedidos(usTemp.getPedidos());
									us.setRol(usTemp.getRol());
									us.setNombre(request.getParameter("v_nombre_m"));
									us.setApellido(request.getParameter("v_apellido_m"));
									us.setMail(request.getParameter("v_mail_m"));
									us.setId(usTemp.getId());
									if(us.getId()==null){
										Boolean exito=false;
										request.setAttribute("error", exito);
										String error="El id es nulo, no se puede modificar un usuario si su identificador es nulo.";
										request.setAttribute("error", error);
										getServletContext().getRequestDispatcher("/WebController?url=/usuarioModif.jsp").forward(request, response);
										return;
									}
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
									getServletContext().getRequestDispatcher("/WebController?url=/usuarioModif.jsp").forward(request, response);
									return;
								}else{
									Boolean exito=false;
									request.setAttribute("exito", exito);
									String error="La contraseña antigua es incorrecta.";
									request.setAttribute("error", error);
									getServletContext().getRequestDispatcher("/WebController?url=/usuarioModif.jsp").forward(request, response);
									return;
								}
							}
						}
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="El usuario no existe. Cárguelo previo a intentar modificarlo.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/WebController?url=/usuarioModif.jsp").forward(request, response);
						return;
					}
				}
				//Buscar
				//if(accion.equals("buscar")){
				//}
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
