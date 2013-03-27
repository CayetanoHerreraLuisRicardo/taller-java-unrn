package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import modelo.*;

public class RolDao extends BaseDao {
	private ResultSet rolResult;
	private List<Rol> roles;

	@Override
	public Rol buscar(Integer id) {
		Rol rol = new Rol();
		try {
			String sqlSent = "SELECT * FROM rol WHERE id=" + id;
			rolResult = consultar(sqlSent);
			if (rolResult.next()) {
				rol.setId(id);
				rol.setNombre(rolResult.getString("nombre"));
				desconectar();
				return rol;
			} else {
				rol = null;
				return rol;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rol = null;
			try {
				desconectar();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return rol;
		}
	}

	@Override
	public List<Rol> listar() {
		ArrayList<Rol> list = new ArrayList<Rol>();
		try {
			String sqlSent = "SELECT * FROM rol";
			rolResult = consultar(sqlSent);
			while (rolResult.next()) {
				Rol rol = new Rol();
				rol.setId(rolResult.getInt("id"));
				rol.setNombre(rolResult.getString("nombre"));
				list.add(rol);
			}
			desconectar();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		roles = list;
		return roles;
	}

	@Override
	public Integer guardar(Object rol) {
		Integer resultado = null;
		String sqlSent = "INSERT INTO rol (`nombre`) VALUES ('"
				+ ((Rol) rol).getNombre() + "')";
		try {
			resultado = modificar(sqlSent);
			System.out.println("ID: " + resultado);
			return resultado;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Resultado: " + resultado);
			e.printStackTrace();
			return resultado;
		}
	}

	@Override
	public Integer eliminar(Integer id) {
		Integer resultado = null;
		String sqlSent = "DELETE FROM rol WHERE id=" + id;
		try {
			resultado = modificar(sqlSent);
			return resultado;
		} catch (Exception e) {
			System.out.println("Resultado: " + resultado);
			// TODO Auto-generated catch block
			e.printStackTrace();
			return resultado;
		}
	}

	@Override
	public Integer modificar(Object rol) {
		Integer resultado = null;
		String sqlSent = "UPDATE rol SET nombre='" + ((Rol) rol).getNombre()
				+ "' WHERE id=" + ((Rol) rol).getId();
		try {
			resultado = modificar(sqlSent);
			return resultado;
		} catch (Exception e) {
			System.out.println("Resultado: " + resultado);
			// TODO Auto-generated catch block
			e.printStackTrace();
			return resultado;
		}
	}

}
