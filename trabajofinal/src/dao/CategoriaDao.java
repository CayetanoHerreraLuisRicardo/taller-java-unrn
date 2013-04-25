package dao;

import java.sql.*;
import java.util.*;

import modelo.*;

public class CategoriaDao extends BaseDao {
	private ResultSet categoriaResult;
	private List<Categoria> categorias;
	
	@Override
	public List<Categoria> listar(){
		ArrayList<Categoria> list=new ArrayList<Categoria>();
		try {
			String sqlSent="SELECT * FROM categoria ORDER BY nombre";
			categoriaResult=consultar(sqlSent);
			while(categoriaResult.next()){
				Categoria categoria=new Categoria();
				categoria.setId(categoriaResult.getInt("id"));
				categoria.setNombre(categoriaResult.getString("nombre"));
				list.add(categoria);
			}
			desconectar();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		categorias=list;
		return categorias;
	}

	@Override
	public Integer guardar(Object cat) {
		Integer resultado=null;
		String sqlSent="INSERT INTO categoria (`nombre`) VALUES ('"+((Categoria) cat).getNombre()+"')";
		try {
			resultado=modificar(sqlSent);
			System.out.println("ID: "+resultado);
			return resultado;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Resultado: "+resultado);
			e.printStackTrace();
			return resultado;
		}
	}

	@Override
	public Integer eliminar(Integer id) {
		Integer resultado=null;
		String sqlSent="DELETE FROM categoria WHERE id="+id;
		try {
			resultado=modificar(sqlSent);
			return resultado;
		} catch (Exception e) {
			System.out.println("Resultado: "+resultado);
			// TODO Auto-generated catch block
			e.printStackTrace();
			return resultado;
		}
	}

	@Override
	public Integer modificar(Object cat) {
		Integer resultado=null;
		String sqlSent="UPDATE categoria SET nombre='"+((Categoria) cat).getNombre()+"' WHERE id="+((Categoria) cat).getId();
		try {
			resultado=modificar(sqlSent);
			return resultado;
		} catch (Exception e) {
			System.out.println("Resultado: "+resultado);
			// TODO Auto-generated catch block
			e.printStackTrace();
			return resultado;
		}
	}

	@Override
	public Categoria buscar(Integer id){
		try {
			String sqlSent="SELECT * FROM categoria WHERE id="+id;
			categoriaResult=consultar(sqlSent);
			Categoria categoria=new Categoria();
			if(categoriaResult.next()){
				categoria.setId(id);
				categoria.setNombre(categoriaResult.getString("nombre"));
				return categoria;
			}else{
				categoria=null;
				return categoria;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Categoria categoria=new Categoria();
			categoria=null;
			return categoria;
		}
	}

}
