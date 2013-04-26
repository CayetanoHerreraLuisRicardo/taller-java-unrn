package controlador;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelo.Categoria;
import modelo.Rol;
import modelo.Usuario;
import dao.CategoriaDao;

/**
 * Servlet implementation class CategoriaController
 */
public class CategoriaController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CategoriaController() {
        super();
        // TODO Auto-generated constructor stub
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();		
		//Servlet en sí
		try{
			String accion=request.getParameter("accion");
			if(accion==null || accion.isEmpty()){
				Boolean exito=false;
				request.setAttribute("exito", exito);
				String error="El sistema no reconoce esta Acción.";
				request.setAttribute("error", error);
				response.sendRedirect("/home.jsp");
			}else{
				//
				//Listar
				//
				if(accion.equals("lista")){
					CategoriaDao daoCat=new CategoriaDao();
					List<Categoria> listaCategoria=daoCat.listar();
					request.setAttribute("listacategorias", listaCategoria);
					getServletContext().getRequestDispatcher("/listaCategorias.jsp").forward(request, response);
				}
				//
				//Guardar
				//
				if(accion.equals("guardar")){
					//Verifica el rol del usuario
					Usuario usuario=(Usuario) session.getAttribute("usuario");
					if(usuario==null){
						response.sendRedirect("HomeController");
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="Ud. no es administrador, no puede realizar dicha acción.";
						request.setAttribute("error", error);
						return;
					}
					Rol rol=usuario.getRol();
					if(rol.getId() != 1 || rol.getId() == null){
						//Nota: Para los errores, dejémosle el boolean *exito* - para poder diferenciar los errores de los exitos en el CSS
						response.sendRedirect("HomeController");
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="Ud. no es administrador, no puede realizar dicha acción.";
						request.setAttribute("error", error);
						return;
					}
					//Lista las Categorías
					@SuppressWarnings("unchecked")
					List<Categoria>categorias=(List<Categoria>) session.getAttribute("listacategorias");
					for(int i=0;i<categorias.size();i++){
						Categoria catTemp=categorias.get(i);
						if(catTemp.getNombre().contains(request.getParameter("v_nombre"))){
							Boolean exito=false;
							request.setAttribute("exito", exito);
							String error="No se puede cargar una categoría ya existente.";
							request.setAttribute("error", error);
							getServletContext().getRequestDispatcher("/categoriaAlta.jsp").forward(request, response);
							return;
						}
					}
					Categoria cat=new Categoria();
					cat.setNombre(request.getParameter("v_nombre"));
					CategoriaDao catDao=new CategoriaDao();
					Integer id=catDao.guardar(cat);
					if(id != -1){
						Boolean exito=true;
						request.setAttribute("exito", exito);
						String error="Se cargó correctamente la categoría.";
						request.setAttribute("error", error);
						List<Categoria> listaCategoria=catDao.listar();
						session.setAttribute("listacategorias", listaCategoria);
						getServletContext().getRequestDispatcher("/categoriaAlta.jsp").forward(request, response);
						return;
					}else{
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="Hubo un problema con la recuperación del identificador de la categoría. Posible error en la carga.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/categoriaAlta.jsp").forward(request, response);
					}
				}
				//
				//Eliminar
				//
				if(accion.equals("eliminar")){
					//Verifica el rol del usuario
					Usuario usuario=(Usuario) session.getAttribute("usuario");
					if(usuario==null){
						response.sendRedirect("HomeController");
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="Ud. no es administrador, no puede realizar dicha acción.";
						request.setAttribute("error", error);
						return;
					}
					Rol rol=usuario.getRol();
					if(rol.getId() != 1 || rol.getId() == null){
						response.sendRedirect("HomeController");
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="Ud. no es administrador, no puede realizar dicha acción.";
						request.setAttribute("error", error);
						return;
					}
					//Listar categorías
					String cats=request.getParameter("cat");
					Integer id=Integer.parseInt(cats);
					CategoriaDao catDao=new CategoriaDao();
					Integer borrar=catDao.eliminar(id);
					if(borrar != -1){
						Boolean exito=true;
						request.setAttribute("exito", exito);
						String error="Se borró la categoria correctamente.";
						request.setAttribute("error", error);
						List<Categoria> listaCategoria=catDao.listar();
						session.setAttribute("listacategorias", listaCategoria);
						getServletContext().getRequestDispatcher("/categoriaEliminar.jsp").forward(request, response);
					}else{
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="La categoría no pudo ser borrada.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/categoriaEliminar.jsp").forward(request, response);
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
						String error="Ud. no es administrador, no puede realizar dicha acción.";
						request.setAttribute("error", error);
						return;
					}
					Rol rol=usuario.getRol();
					if(rol.getId() != 1 || rol.getId() == null){
						response.sendRedirect("HomeController");
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="Ud. no es administrador, no puede realizar dicha acción.";
						request.setAttribute("error", error);
						return;
					}
					//Lista las Categorías
					@SuppressWarnings("unchecked")
					List<Categoria>categorias=(List<Categoria>) session.getAttribute("listacategorias");
					for(int i=0;i<categorias.size();i++){
						Categoria catTemp=categorias.get(i);
						if(catTemp.getNombre().contains(request.getParameter("v_nombre"))){
							Categoria cat=new Categoria();
							cat.setNombre(request.getParameter("v_nombre_m"));
							cat.setId(catTemp.getId());
							CategoriaDao catDao=new CategoriaDao();
							Integer modif=catDao.modificar(cat);
							if(modif != -1){
								Boolean exito=true;
								request.setAttribute("exito", exito);
								String error="Se modificó correctamente la categoría.";
								request.setAttribute("error", error);
								List<Categoria> listaCategoria=catDao.listar();
								session.setAttribute("listacategorias", listaCategoria);
							}else{
								Boolean exito=false;
								request.setAttribute("exito", exito);
								String error="Hubo un problema al acceso a la Base de Datos.";
								request.setAttribute("error", error);
							}
							getServletContext().getRequestDispatcher("/categoriaModif.jsp").forward(request, response);
							return;
						}
					}
					Boolean exito=false;
					request.setAttribute("exito", exito);
					String error="La categoría no existe. Cárguela previo a intentar modificarla.";
					request.setAttribute("error", error);
					getServletContext().getRequestDispatcher("/categoriaModif.jsp").forward(request, response);
					return;
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
			response.sendRedirect("/web_mensaje.jsp?mensaje=El sistema no reconoce esta Acción");
		}else{
			//Nota: nunca lo usé.
			if(accion.equals("guardar") || accion.equals("modificar")){
				Boolean exito=false;
				request.setAttribute("exito", exito);
				String error= "Ud está intentando realizar una operación no permitida.";
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

}
