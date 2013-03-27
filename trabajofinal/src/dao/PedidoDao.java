package dao;

import java.sql.*;
import java.util.*;
import modelo.*;

public class PedidoDao extends BaseDao {
	private ResultSet pedidoResult;
	private UsuarioDao userDao;
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
				pedido.setFechaEntrega(pedidoResult.getString("fecha_entrega"));
				pedido.setFechaPedido(pedidoResult.getString("fecha_pedido"));
				//Cargar usuario
				pedido.setUsuario(userDao.buscar(pedidoResult.getInt("usuario_id")));
				pedido=Pedido.cargarProds(pedido);
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
		String sqlSent="INSERT INTO `taller`.`pedido` (`usuario_id`,`fecha_pedido`,`estado`,`fecha_entrega`) VALUES " +
			"("+usuario.getId()+",'"+((Pedido) ped).getFechaPedido()+"','"+((Pedido) ped).getFechaEntrega()+"');";
		try {
			resultadoInicial=modificar(sqlSent);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(resultadoInicial != -1){
			List<Producto> prod=((Pedido)ped).getProductos();
			for(int a=0;a<prod.size();a++){
				String sqlSentProd="INSERT INTO `taller`.`pedido_producto` (`pedido_id`,`producto_id`) VALUES ("+resultadoInicial+","+prod.get(a).getId()+")";
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
		ped=Pedido.cargarProds(ped);
		List<Producto> prod=ped.getProductos();
		for(int a=0;a<prod.size();a++){
			String sqlSentProd="DELETE FROM `taller`.`pedido_producto` WHERE pedido_id="+id;
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
			String sqlSent="DELETE FROM `taller`.`pedido` WHERE pedido_id="+id;
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
		String sqlSent="UPDATE `taller`.`pedido` SET " +
			"usuario_id="+user.getId()+", " +
			"fecha_pedido="+((Pedido)ped).getFechaPedido()+", " +
			"fecha_entrega="+((Pedido)ped).getFechaEntrega()+" " +
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
				pedido.setFechaEntrega(pedidoResult.getString("fecha_entrega"));
				pedido.setFechaPedido(pedidoResult.getString("fecha_pedido"));
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

}
