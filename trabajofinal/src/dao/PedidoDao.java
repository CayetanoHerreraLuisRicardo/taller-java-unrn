package dao;

import java.sql.*;
import java.util.*;

import modelo.*;

public class PedidoDao extends BaseDao {
	private ResultSet pedidoResult;
	private UsuarioDao userDao=new UsuarioDao();
	private List<Pedido> pedidos=new ArrayList<Pedido>();

	@Override
	public List<Pedido> listar() {//No se carga la lista de productos
		try {
			String sqlSent="SELECT * FROM pedido";
			pedidoResult=consultar(sqlSent);
			while(pedidoResult.next()){
				//Inicialización de pedidos
				Pedido pedido=new Pedido();
				pedido.setId(pedidoResult.getInt("id"));
				pedido.setFechaCompra(pedidoResult.getString("fecha_compra"));
				//Cargar usuario
				pedido.setUsuario(userDao.buscar(pedidoResult.getInt("usuario_id")));
				pedido=cargarProds(pedido);
				pedidos.add(pedido);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pedidos;
	}

	@Override
	public Integer guardar(Object ped) {
		Integer resultadoInicial=null;
		Integer resultadoFinal=null;
		//Por cada producto en el objeto pedido, se crea una tupla en la tabla pedidio_producto
		Usuario usuario=((Pedido) ped).getUsuario();
		String sqlSent="INSERT INTO `pedido` (`usuario_id`,`fecha_compra`) VALUES " +
			"("+usuario.getId()+",'"+((Pedido) ped).getFechaCompra()+"');";
		try {
			resultadoInicial=modificar(sqlSent);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(resultadoInicial != -1){
			Hashtable<Producto,Integer> prodsTable=((Pedido)ped).getProductos();
			Enumeration<Producto>prods=prodsTable.keys();
			while(prods.hasMoreElements()){
				Producto prod=prods.nextElement();
				String sqlSentProd="INSERT INTO `pedido_producto` (`pedido_id`,`producto_id`,`cantProds`) VALUES ("+resultadoInicial+","+prod.getId()+","+prodsTable.get(prod)+")";
				try {
					resultadoFinal=modificar(sqlSentProd);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					System.out.println("Problemas al insertar una fila en pedido_producto");
					e1.printStackTrace();
					break;
				}
			}
		}
		return resultadoFinal;
	}

	@Override
	public Integer eliminar(Integer id) {
		Integer resultado=null;
		//Se recorre pedido_producto, y luego de borrar todas las coincidencias, se borra la fila de la tabla pedido
		Pedido ped=new Pedido();
		ped.setId(id);
		ped=cargarProds(ped);
		Hashtable<Producto,Integer> prodsTable=((Pedido)ped).getProductos();
		for(int a=0;a<prodsTable.size();a++){
			String sqlSentProd="DELETE FROM `pedido_producto` WHERE pedido_id="+id;
			try {
				resultado=modificar(sqlSentProd);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				System.out.println("Problemas al borrar una fila en pedido_producto");
				e1.printStackTrace();
				break;
			}
		}
		if(resultado != -1){
			String sqlSent="DELETE FROM `pedido` WHERE pedido_id="+id;
				try {
					resultado=modificar(sqlSent);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
		return resultado;
	}

	@Override
	public Integer modificar(Object ped) {
		Integer resultado=null;
		Usuario user=((Pedido)ped).getUsuario();
		String sqlSent="UPDATE `pedido` SET " +
			"usuario_id="+user.getId()+", " +
			"fecha_compra="+((Pedido)ped).getFechaCompra()+", " +
			"WHERE id="+((Pedido) ped).getId();
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
	public Pedido buscar(Integer id) {
		Pedido pedido=new Pedido();
		try {
			String sqlSent="SELECT * FROM pedido WHERE id="+id;
			pedidoResult=consultar(sqlSent);
			if(pedidoResult.next()){
				//Inicialización del pedido
				pedido.setId(id);
				pedido.setFechaCompra(pedidoResult.getString("fecha_compra"));
				//Cargar usuario
				pedido.setUsuario(userDao.buscar(pedidoResult.getInt("usuario_id")));
				return pedido;
			}else{
				pedido=null;
				return pedido;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			pedido=null;
			return pedido;
		}
	}

	private Pedido cargarProds(Pedido ped){
		//Creación de la lista de productos
		try{
			ResultSet p_pID=dao.BaseDao.consultar("Select * FROM pedido_producto WHERE pedido_id="+ped.getId());
			Hashtable<Producto,Integer> pList=new Hashtable<Producto,Integer>();
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
				Integer cant=p_pID.getInt("cantProds");
				pList.put(prod, cant);
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
