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
		//Nota: No deber�a ser necesario, ya que el HomeController no carga la lista de categor�as, lo hace *panel_izq.jsp*
		//@SuppressWarnings("unchecked")
		//Enumeration<String> verif=session.getAttributeNames();
		//Boolean contiene=false;
		//while(verif.hasMoreElements()){
		//String elem=verif.nextElement();
		//if(elem.contains("listacategorias")){
		//	contiene=true;
		//}
		//}if(contiene == false){
		//response.sendRedirect("HomeController");
		//return;
		//}
		
		//Servlet en s�
		try{
			String accion=request.getParameter("accion");
			if(accion==null || accion.isEmpty()){
				//Nota: Falta crear el JSP para los mensajes.
				response.sendRedirect("/vista/web_mensaje.jsp?mensaje=El sistema no reconoce esta Acci�n");
			}else{
				//
				//Listar
				//
				if(accion.equals("lista")){
					CategoriaDao daoCat=new CategoriaDao();
					List<Categoria> listaCategoria=daoCat.listar();
					request.setAttribute("listacategorias", listaCategoria);
					getServletContext().getRequestDispatcher("/vista/listaCategorias.jsp").forward(request, response);
				}
				//
				//Guardar
				//
				if(accion.equals("guardar")){
					//Verifica el rol del usuario
					Usuario usuario=(Usuario) session.getAttribute("usuario");
					Rol rol=usuario.getRol();
					if(rol.getId() != 1 || rol.getId() == null){
						//Nota: Para los errores, dej�mosle el boolean *exito* - para poder diferenciar los errores de los exitos en el CSS
						response.sendRedirect("HomeController");
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="Ud. no es administrador, no puede realizar dicha acci�n.";
						request.setAttribute("error", error);
						return;
					}
					//Lista las Categor�as
					@SuppressWarnings("unchecked")
					List<Categoria>categorias=(List<Categoria>) session.getAttribute("listacategorias");
					
					//Si no hay categor�as
					if(categorias==null){
						List<Categoria> cats=new ArrayList<Categoria>();
						Categoria cat=new Categoria();
						cat.setNombre(request.getParameter("v_nombre"));
						CategoriaDao catDao=new CategoriaDao();
						Integer id=catDao.guardar(cat);
						if(id != -1){
							Boolean exito=true;
							request.setAttribute("exito", exito);
							String error="Se carg� correctamente la categor�a.";
							request.setAttribute("error", error);
							cat.setId(id);
							cats.add(cat);
							session.setAttribute("listacategorias", cats);
							getServletContext().getRequestDispatcher("/vista/categoriaAlta.jsp").forward(request, response);
							return;
						}else{
							Boolean exito=false;
							request.setAttribute("exito", exito);
							String error="Hubo un problema con la recuperaci�n del identificador de la categor�a. Posible error en la carga.";
							request.setAttribute("error", error);
							getServletContext().getRequestDispatcher("/vista/categoriaAlta.jsp").forward(request, response);
						}
					}else{
						//Esto ser�a tu funcion existe
						for(int i=0;i<categorias.size();i++){
							Categoria catTemp=categorias.get(i);
							if(catTemp.getNombre().contains(request.getParameter("v_nombre"))){
								Boolean exito=false;
								request.setAttribute("exito", exito);
								String error="No se puede cargar una categor�a ya existente.";
								request.setAttribute("error", error);
								getServletContext().getRequestDispatcher("/vista/categoriaAlta.jsp").forward(request, response);
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
							String error="Se carg� correctamente la categor�a.";
							request.setAttribute("error", error);
							List<Categoria> listaCategoria=catDao.listar();
							session.setAttribute("listacategorias", listaCategoria);
							getServletContext().getRequestDispatcher("/vista/categoriaAlta.jsp").forward(request, response);
							return;
						}else{
							Boolean exito=false;
							request.setAttribute("exito", exito);
							String error="Hubo un problema con la recuperaci�n del identificador de la categor�a. Posible error en la carga.";
							request.setAttribute("error", error);
							getServletContext().getRequestDispatcher("/vista/categoriaAlta.jsp").forward(request, response);
						}
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
						String error="Ud. no es administrador, no puede realizar dicha acci�n.";
						request.setAttribute("error", error);
						return;
					}
					//Listar categor�as
					String cats=request.getParameter("cat");
					Integer id=Integer.parseInt(cats);
					CategoriaDao catDao=new CategoriaDao();
					Integer borrar=catDao.eliminar(id);
					if(borrar != -1){
						Boolean exito=true;
						request.setAttribute("exito", exito);
						String error="Se borr� la categoria correctamente.";
						request.setAttribute("error", error);
						List<Categoria> listaCategoria=catDao.listar();
						session.setAttribute("listacategorias", listaCategoria);
						getServletContext().getRequestDispatcher("/vista/categoriaEliminar.jsp").forward(request, response);
					}else{
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="La categor�a no pudo ser borrada.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/vista/categoriaEliminar.jsp").forward(request, response);
					}
				}
				//
				//Modificar
				//
				if(accion.equals("modificar")){
					//Verifica el rol del usuario
					Usuario usuario=(Usuario) session.getAttribute("usuario");
					Rol rol=usuario.getRol();
					if(rol.getId() != 1 || rol.getId() == null){
						response.sendRedirect("HomeController");
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="Ud. no es administrador, no puede realizar dicha acci�n.";
						request.setAttribute("error", error);
						return;
					}
					//Lista las Categor�as
					@SuppressWarnings("unchecked")
					List<Categoria>categorias=(List<Categoria>) session.getAttribute("listacategorias");
					if(categorias==null){
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="La lista de Categor�as est� vac�a. Previo a la modificaci�n de una, c�rguela.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/vista/categoriaModif.jsp").forward(request, response);
					}else{
						for(int i=0;i<categorias.size();i++){
							Categoria catTemp=categorias.get(i);
							if(catTemp.getNombre().contains(request.getParameter("v_nombre"))){
								Categoria cat=new Categoria();
								cat.setNombre(request.getParameter("v_nombre_m"));
								cat.setId(catTemp.getId());
								CategoriaDao catDao=new CategoriaDao();
								if(cat.getId()==null){
									Boolean exito=false;
									request.setAttribute("error", exito);
									String error="El id es nulo, no se puede modificar una Categor�a si su identificador es nulo.";
									request.setAttribute("error", error);
									getServletContext().getRequestDispatcher("/vista/categoriaModif.jsp").forward(request, response);
									return;
								}
								Integer modif=catDao.modificar(cat);
								if(modif != -1){
									Boolean exito=true;
									request.setAttribute("exito", exito);
									String error="Se modific� correctamente la categor�a.";
									request.setAttribute("error", error);
									List<Categoria> listaCategoria=catDao.listar();
									session.setAttribute("listacategorias", listaCategoria);
								}else{
									Boolean exito=false;
									request.setAttribute("exito", exito);
									String error="Hubo un problema al acceso a la Base de Datos.";
									request.setAttribute("error", error);
								}
								getServletContext().getRequestDispatcher("/vista/categoriaModif.jsp").forward(request, response);
								return;
							}
						}
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="La categor�a no existe. C�rguela previo a intentar modificarla.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/vista/categoriaModif.jsp").forward(request, response);
						return;
					}
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

}
