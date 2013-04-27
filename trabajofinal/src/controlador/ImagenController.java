package controlador;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javazoom.upload.MultipartFormDataRequest;
import javazoom.upload.UploadBean;
import javazoom.upload.UploadException;
import javazoom.upload.UploadFile;
import modelo.Categoria;
import modelo.Producto;
import modelo.Rol;
import modelo.Usuario;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FilePart;
import com.oreilly.servlet.multipart.MultipartParser;
import com.oreilly.servlet.multipart.Part;

import dao.ProductoDao;

/**
 * Servlet implementation class imagenController
 */
public class ImagenController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImagenController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			processRequest(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			processRequest(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session=request.getSession();
		MultipartFormDataRequest mrequest = new MultipartFormDataRequest(request);
		//cargo las extenciones de las imagenes
		String[] ext = {"image/bmp", "image/png", "image/jpg", "image/gif", "image/JPEG", "image/jpeg"};
		List<String> listaext = new ArrayList<String>();
		listaext.addAll(Arrays.asList(ext));
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
		//Guardar
		String nombre = mrequest.getParameter("v_nombre");
		Producto prod=new Producto();
		prod.setNombre(mrequest.getParameter("v_nombre"));
		prod.setDescripcion(mrequest.getParameter("v_desc"));
		prod.setPrecio(Double.parseDouble(mrequest.getParameter("v_precio")));
		//GUARDAR IMAGEN EN SERVIDOR
		String urlimg = null;
		UploadBean upBean = null;
		try {
			Hashtable files = mrequest.getFiles();
			if ((files != null) && (!files.isEmpty())) {
				UploadFile file = (UploadFile) files.get("v_img_url");
				if(listaext.contains(file.getContentType())){
					upBean = new UploadBean();
					upBean.setFolderstore("D:\\");
					upBean.store(mrequest, "v_img_url");
					urlimg = "img/" + file.getFileName();
					System.out.println("imagen: " + "D:\\" + urlimg);
					System.out.println("nombre: " + urlimg);
					System.out.println("tipo: " + file.getContentType());
					System.out.println("tamanio: " + file.getFileSize());
				}
				else{
					System.out.println("error lpm");
					Boolean exito=false;
					request.setAttribute("exito", exito);
					String error="La extencion del archivo que intenta guardar es incorrecta";
					request.setAttribute("error", error);
					getServletContext().getRequestDispatcher("/productoAlta.jsp").forward(request, response);
					return;
				}
			}
			else{
				urlimg = "img/nodisponible.jpg";
				System.out.println("<li>No uploaded files");
			}
		} catch (UploadException exc) {
			System.out.println("Error en lo primero: " + exc.getMessage());
		}
		prod.setImgURL(mrequest.getParameter(urlimg));
		//sigo con la carga de producto
		Categoria cat=new Categoria();
		cat.setId(Integer.parseInt(mrequest.getParameter("v_cat_id")));
		prod.setCategoria(cat);
		ProductoDao prodDao=new ProductoDao();
		Integer id=prodDao.guardar(prod);
		if(id != -1){
			Boolean exito=true;
			request.setAttribute("exito", exito);
			String error="Se cargó correctamente el producto.";
			request.setAttribute("error", error);
			getServletContext().getRequestDispatcher("/productoAlta.jsp").forward(request, response);
			return;
		}else{
			Boolean exito=false;
			request.setAttribute("exito", exito);
			String error="Hubo un problema al cargar el producto, inténtelo de nuevo más tarde.";
			request.setAttribute("error", error);
			getServletContext().getRequestDispatcher("/productoAlta.jsp").forward(request, response);
		}
		
	}
}



