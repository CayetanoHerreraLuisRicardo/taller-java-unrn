package dao;

import java.sql.*;
import java.util.*;

import modelo.*;

public class ProductoDao extends BaseDao {
	private ResultSet productoResult;
	private CategoriaDao catDao=new CategoriaDao();
	private List<Producto> productos=new ArrayList<Producto>();

	@Override
	public List<Producto> listar() { //Se listan sin categoría.
		try {
			String sqlSent="SELECT * FROM producto";
			productoResult=consultar(sqlSent);
			while(productoResult.next()){
				Producto prod=new Producto();
				prod.setId(productoResult.getInt("id"));
				prod.setNombre(productoResult.getString("nombre"));
				prod.setDescripcion(productoResult.getString("descrip"));
				prod.setImgURL(productoResult.getString("img_url"));
				prod.setPrecio(productoResult.getDouble("precio"));
				prod.setCategoria(catDao.buscar(productoResult.getInt("categoria_id")));
				productos.add(prod);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return productos;
	}
	
	public List<Producto> listar(Categoria cat) { //Se listan sin categoría.
		try {
			String sqlSent="SELECT * FROM producto WHERE categoria_id="+cat.getId();
			productoResult=consultar(sqlSent);
			while(productoResult.next()){
				Producto prod=new Producto();
				prod.setId(productoResult.getInt("id"));
				prod.setNombre(productoResult.getString("nombre"));
				prod.setDescripcion(productoResult.getString("descrip"));
				prod.setImgURL(productoResult.getString("img_url"));
				prod.setPrecio(productoResult.getDouble("precio"));
				prod.setCategoria(cat);
				productos.add(prod);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return productos;
	}

	@Override
	public Integer guardar(Object prod) {
		Integer id=null;
		Categoria cat=((Producto) prod).getCategoria();
		String sqlSent="INSERT INTO `producto` (`nombre`,`descrip`,`precio`,`categoria_id`,`img_url`) VALUES " +
				"('"+((Producto) prod).getNombre()+"','"+((Producto) prod).getDescripcion()+"',"+((Producto) prod).getPrecio()+","+cat.getId()+",'"+((Producto) prod).getImgURL()+"')";
		try {
			id=modificar(sqlSent);
			return id;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return id;
		}
	}

	@Override
	public Integer eliminar(Integer id) {
		Integer resultado=null;
		String sqlSent="DELETE FROM `producto` WHERE id="+id;
		try {
			resultado=modificar(sqlSent);
			return resultado;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return resultado;
		}
	}

	@Override
	public Integer modificar(Object prod) {
		Integer resultado=null;
		Categoria cat=((Producto) prod).getCategoria();
		String sqlSent="UPDATE `producto` SET " +
			"nombre='"+((Producto) prod).getNombre()+"'," +
			"descrip='"+((Producto) prod).getDescripcion()+"', " +
			"precio='"+((Producto) prod).getPrecio()+"', " +
			"categoria_id='"+(cat.getId())+"' " +
			"WHERE id="+((Producto) prod).getId();
		try {
			resultado=modificar(sqlSent);
			return resultado;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return resultado;
		}
	}

	@Override
	public Producto buscar(Integer id) {
		try {
			String sqlSent="SELECT * FROM producto WHERE id="+id;
			productoResult=consultar(sqlSent);
			Producto producto=new Producto();
			if(productoResult.next()){
				producto.setId(id);
				producto.setNombre(productoResult.getString("nombre"));
				producto.setDescripcion(productoResult.getString("descrip"));
				producto.setPrecio(productoResult.getDouble("precio"));
				producto.setImgURL(productoResult.getString("img_url"));
				producto.setCategoria(catDao.buscar(productoResult.getInt("categoria_id")));
				return producto;
			}else{
				producto=null;
				return producto;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Producto producto=new Producto();
			producto=null;
			return producto;
		}
	}
	
	public List<Producto> buscador(String cond, int categoria) throws SQLException, Exception{
		String sentenciaSQL = "SELECT p.* FROM producto p, categoria c " +
							"WHERE p.categoria_id = c.id and c.id = '" + categoria + "' " +
							"and p.nombre like '%" + cond + "%' " +
							"ORDER BY p.id ASC"; 
		System.out.println(sentenciaSQL);
		Producto producto = null;
		ResultSet filasConsulta = consultar(sentenciaSQL);
		while (filasConsulta.next()) {
			producto = new Producto();
			producto.setId(filasConsulta.getInt("id"));
			producto.setNombre(filasConsulta.getString("nombre"));
			producto.setDescripcion(filasConsulta.getString("descrip"));
			producto.setPrecio(filasConsulta.getDouble("precio"));
			producto.setImgURL("img_url");
			producto.setCategoria(catDao.buscar(filasConsulta.getInt("categoria_id")));
			productos.add(producto);
						
		}
		return productos;
	}
}
