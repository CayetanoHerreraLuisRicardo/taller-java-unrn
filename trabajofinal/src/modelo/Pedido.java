package modelo;

import java.sql.ResultSet;
import java.util.*;

public class Pedido {
	private Integer id;
	private String fechaPedido;
	private String fechaEntrega;
	private Usuario usuario;
	private List<Producto> productos=new ArrayList<Producto>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFechaPedido() {
		return fechaPedido;
	}

	public void setFechaPedido(String fechaPedido) {
		this.fechaPedido = fechaPedido;
	}

	public String getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(String fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}
	
	public Pedido(){}

	public static Pedido cargarProds(Pedido ped){
		//Creación de la lista de productos
		try{
			ResultSet p_pID=dao.BaseDao.consultar("Select * FROM pedido_producto WHERE id="+ped.getId());
			List<Producto> pList=new ArrayList<Producto>();
			while(p_pID.next()){
				ResultSet prodID=dao.BaseDao.consultar("Select * FROM producto WHERE id="+p_pID.getInt("producto_id"));
				prodID.next();
				Producto prod=new Producto();
				prod.setId(prodID.getInt("id"));
				prod.setDescripcion(prodID.getString("descrip"));
				prod.setNombre(prodID.getString("nombre"));
				prod.setPrecio(prodID.getDouble("precio"));
					ResultSet catID=dao.BaseDao.consultar("Select * FROM categoria WHERE id="+prodID.getInt("categoria_id"));
					catID.next();
					Categoria cat=new Categoria();
					cat.setId(catID.getInt("id"));
					cat.setNombre(catID.getString("nombre"));
				pList.add(prod);
			}
			ped.setProductos(pList);
			return ped;
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ped.setProductos(null);
			return ped;
		}
	}
}