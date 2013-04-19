package controladores;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelos.Categoria;
import daos.CategoriaDao;


/**
 * Servlet implementation class CategoriaController
 */
public class ZManoloCategoriaController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CategoriaController() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void processRequest(HttpServletRequest request,
        	HttpServletResponse response) throws ServletException, IOException {
        	response.setContentType("text/html;charset=UTF-8");
        	PrintWriter out = response.getWriter();
        	HttpSession sesion = request.getSession();

        	try {
        		String accion = request.getParameter("accion");
        		if (accion == null || accion.isEmpty()) {
        				response.sendRedirect("vistas/web_mensaje.jsp?mensaje=El sistema no reconoce esta Accion");
        		}
        		else if (accion.equals("crearCategoria")) {//CREAR CATEGORIA
        			CategoriaDao daocategoria = new CategoriaDao();
        			if(existe(request.getParameter("txtnombre"))){
        				String error="El nombre de categoria ya existe, por favor ingrese otro e intente de nuevo";
    					sesion.setAttribute("error", error);
    					getServletContext().getRequestDispatcher("/crearcategoria.jsp").forward(request, response);
        			}
        			else{
          				Boolean flag = daocategoria.insertar(request.getParameter("txtnombre"));
          				if(flag){
          					String mensaje="La categoria se cre� correctamente";
          					sesion.setAttribute("mensaje", mensaje);
          					System.out.println("Categoria Insertada");
          					List<Categoria> listacategorias = daocategoria.listar();
          					sesion.setAttribute("listacategorias", listacategorias); 
          					getServletContext().getRequestDispatcher("/crearcategoria.jsp").forward(request, response);
          				}
          				else{
          					System.out.println("Problema al crear la categoria");
          					String error="Ocurrio un problema. Por favor verifique la informacion y llene los campos correctamente";
          					sesion.setAttribute("error", error);
          					getServletContext().getRequestDispatcher("/crearcategoria.jsp").forward(request, response);
          				}
        			}
        			
        		}
        		else if(accion.equals("seleccionCategoria")){//PASO ANTES DE EDITAR CATEGORIA
        			CategoriaDao daocategoria = new CategoriaDao();
        			Categoria cat = daocategoria.buscar(Integer.valueOf(request.getParameter("id")));
        			sesion.setAttribute("categoria", cat);
        			getServletContext().getRequestDispatcher("/editarcategoria.jsp").forward(request, response);
        			System.out.println(cat);
        		}
        		else if(accion.equals("Modificar")){ //EDITAR CATEGORIA
        			CategoriaDao daocategoria = new CategoriaDao();
        			Categoria cat = (Categoria) sesion.getAttribute("categoria");
        			String nuevo = request.getParameter("txtnombrenuevo");
        			System.out.println(nuevo);
        			if(!existe(nuevo)){
        				if(daocategoria.editar(nuevo, cat.getId())){
	        				String mensaje="La categoria se modific� correctamente";
	      					sesion.setAttribute("mensaje", mensaje);
	      					System.out.println("Categoria Modificada");
	      					List<Categoria> listacategorias = daocategoria.listar();
	      			        sesion.setAttribute("listacategorias", listacategorias);
	      			        sesion.setAttribute("categoria", null);
	      					getServletContext().getRequestDispatcher("/editarcategoria.jsp").forward(request, response);
	      				}
	      				else{
	      					System.out.println("Problema al modificar la categoria");
	      					String error="Ocurrio un problema. Por favor verifique la informacion y llene los campos correctamente";
	      					sesion.setAttribute("error", error);
	      					getServletContext().getRequestDispatcher("/editarcategoria.jsp").forward(request, response);
	      				}
        			}
        			else{
        				String error="El nombre de categoria ya existe, por favor ingrese otro e intente de nuevo";
    					sesion.setAttribute("error", error);
    					getServletContext().getRequestDispatcher("/editarcategoria.jsp").forward(request, response);
        			}
        		}
        		else if(accion.equals("seleccionEliminarCategoria")){//PASO ANTES DE EDITAR CATEGORIA
        			CategoriaDao daocategoria = new CategoriaDao();
        			Categoria cat = daocategoria.buscar(Integer.valueOf(request.getParameter("id")));
        			sesion.setAttribute("categoria", cat);
        			getServletContext().getRequestDispatcher("/eliminarcategoria.jsp").forward(request, response);
        			System.out.println(cat);
        		}
        		else if(accion.equals("Eliminar")){//ELIMINAR CATEGORIA
        			CategoriaDao daocategoria = new CategoriaDao();
        			Categoria cat = (Categoria) sesion.getAttribute("categoria");
        			if(daocategoria.eliminar(cat.getId())){
        				String mensaje="La categoria se elimin� correctamente";
      					sesion.setAttribute("mensaje", mensaje);
      					System.out.println("Categoria Eliminada");
      					List<Categoria> listacategorias = daocategoria.listar();
      			        sesion.setAttribute("listacategorias", listacategorias);
      			        sesion.setAttribute("categoria", null);
      					getServletContext().getRequestDispatcher("/eliminarcategoria.jsp").forward(request, response);
      				}
      				else{
      					System.out.println("Problema al eliminar la categoria");
      					String error="Ocurrio un problema. Por favor verifique la informacion y llene los campos correctamente";
      					sesion.setAttribute("error", error);
      					getServletContext().getRequestDispatcher("/eliminarcategoria.jsp").forward(request, response);
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
        	
        protected Boolean existe(String nomb) throws Exception{
        	CategoriaDao daocategoria = new CategoriaDao();
			List<Categoria> listacategorias = daocategoria.listar();
			if (listacategorias == null){
				return false;
			}
			else{
				for (Categoria cat : listacategorias) {
					if(cat.getNombre().equals(nomb)){
						return true;
					}
				}
			}
			return false;
			
		}
}
