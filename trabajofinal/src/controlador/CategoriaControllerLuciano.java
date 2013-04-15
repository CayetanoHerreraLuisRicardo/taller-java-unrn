package controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelo.*;
import dao.*;

/**
 * Servlet implementation class CategoriaController
 */
public class CategoriaControllerLuciano extends HttpServlet {
	private static final long serialVersionUID = 1L;
	   
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CategoriaControllerLuciano() {
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
				//
				//Listar
				//
				if(accion.equals("lista")){
					CategoriaDao daoCat=new CategoriaDao();
					List<Categoria> listaCategoria=daoCat.listar();
					request.setAttribute("listacategorias", listaCategoria);
					getServletContext().getRequestDispatcher("/WebController?url=/listaCategorias.jsp").forward(request, response);
				}
				//
				//Guardar
				//
				if(accion.equals("guardar")){
					//Lista las Categorías
					@SuppressWarnings("unchecked")
					List<Categoria>categorias=(List<Categoria>) session.getAttribute("listacategorias");
					if(categorias==null){
						List<Categoria> cats=new ArrayList<Categoria>();
						Categoria cat=new Categoria();
						cat.setNombre(request.getParameter("v_nombre"));
						CategoriaDao catDao=new CategoriaDao();
						Integer id=catDao.guardar(cat);
						if(id != -1){
							Boolean exito=true;
							request.setAttribute("exito", exito);
							String error="Se cargó correctamente la categoría.";
							request.setAttribute("error", error);
							cat.setId(id);
							cats.add(cat);
							session.setAttribute("listacategorias", cats);
							getServletContext().getRequestDispatcher("/WebController?url=/categoriaAlta.jsp").forward(request, response);
							return;
						}else{
							Boolean exito=false;
							request.setAttribute("exito", exito);
							String error="Hubo un problema con la recuperación del identificador de la categoría. Posible error en la carga.";
							request.setAttribute("error", error);
							getServletContext().getRequestDispatcher("/WebController?url=/categoriaAlta.jsp").forward(request, response);
						}
					}else{
						for(int i=0;i<categorias.size();i++){
							Categoria catTemp=categorias.get(i);
							if(catTemp.getNombre().contains(request.getParameter("v_nombre"))){
								Boolean exito=false;
								request.setAttribute("exito", exito);
								String error="No se puede cargar una categoría ya existente.";
								request.setAttribute("error", error);
								getServletContext().getRequestDispatcher("/WebController?url=/categoriaAlta.jsp").forward(request, response);
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
							getServletContext().getRequestDispatcher("/WebController?url=/categoriaAlta.jsp").forward(request, response);
							return;
						}else{
							Boolean exito=false;
							request.setAttribute("exito", exito);
							String error="Hubo un problema con la recuperación del identificador de la categoría. Posible error en la carga.";
							request.setAttribute("error", error);
							getServletContext().getRequestDispatcher("/WebController?url=/categoriaAlta.jsp").forward(request, response);
						}
					}
				}
				//
				//Eliminar
				//
				if(accion.equals("eliminar")){
					//Listar categorías
					String cats=request.getParameter("cat");
					Integer id=Integer.parseInt(cats);
					Categoria cat=new Categoria();
					cat.setId(id);
					CategoriaDao catDao=new CategoriaDao();
					Integer borrar=catDao.eliminar(cat);
					if(borrar != -1){
						Boolean exito=true;
						request.setAttribute("exito", exito);
						String error="Se borró la categoria correctamente.";
						request.setAttribute("error", error);
						List<Categoria> listaCategoria=catDao.listar();
						session.setAttribute("listacategorias", listaCategoria);
						getServletContext().getRequestDispatcher("/WebController?url=/categoriaEliminar.jsp").forward(request, response);
					}else{
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="La categoría no pudo ser borrada.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/WebController?url=/categoriaEliminar.jsp").forward(request, response);
					}
				}
				//
				//Modificar
				//
				if(accion.equals("modificar")){
					//Lista las Categorías
					@SuppressWarnings("unchecked")
					List<Categoria>categorias=(List<Categoria>) session.getAttribute("listacategorias");
					if(categorias==null){
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="La lista de Categorías está vacía. Previo a la modificación de una, cárguela.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/WebController?url=/categoriaModif.jsp").forward(request, response);
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
									String error="El id es nulo, no se puede modificar una Categoría si su identificador es nulo.";
									request.setAttribute("error", error);
									getServletContext().getRequestDispatcher("/WebController?url=/categoriaModif.jsp").forward(request, response);
									return;
								}
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
								getServletContext().getRequestDispatcher("/WebController?url=/categoriaModif.jsp").forward(request, response);
								return;
							}
						}
						Boolean exito=false;
						request.setAttribute("exito", exito);
						String error="La categoría no existe. Cárguela previo a intentar modificarla.";
						request.setAttribute("error", error);
						getServletContext().getRequestDispatcher("/WebController?url=/categoriaModif.jsp").forward(request, response);
						return;
					}
				}
				//
				//Buscar
				//
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
