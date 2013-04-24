package dao;

import java.sql.*;
import java.util.*;

import modelo.*;
import modelos.Usuario;

public class UsuarioDao extends BaseDao {
	private ResultSet usuarioID;
	private List<Usuario> usuarios;

	@Override
	public List<Usuario> listar() {
		ArrayList<Usuario> users=new ArrayList<Usuario>();
		try {
			String sqlSent="SELECT * FROM usuario";
			usuarioID=consultar(sqlSent);
			while(usuarioID.next()){
				Usuario usuario=new Usuario();
				usuario.setId(usuarioID.getInt("id"));
				usuario.setNombre(usuarioID.getString("nombre"));
				usuario.setApellido(usuarioID.getString("apellido"));
				usuario.setMail(usuarioID.getString("mail"));
				usuario.setUser(usuarioID.getString("user"));
				usuario.setPass(usuarioID.getString("pass"));
				Integer id=usuarioID.getInt("rol_id");
				RolDao rol=new RolDao();
				usuario.setRol(rol.buscar(id));
				usuario.setPedidos(null);
				users.add(usuario);
			}
			desconectar();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		usuarios=users;
		return usuarios;
	}

	@Override
	public Integer guardar(Object us) {
		Integer id=null;
		Rol rol=((Usuario)us).getRol();
		String sqlSent="INSERT INTO `taller`.`usuario` (`user`,`pass`,`nombre`,`apellido`,`mail`,`rol_id`) VALUES " +
				"('"+((Usuario)us).getUser()+"','"+((Usuario)us).getPass()+
				"','"+((Usuario)us).getNombre()+"','"+((Usuario)us).getApellido()+"','"+((Usuario)us).getMail()+"',"+rol.getId()+")";
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
	public Integer eliminar(Integer id){
		String sqlSent="DELETE FROM `taller`.`usuario` WHERE id="+id;
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
	public Integer modificar(Object us) {
		Integer id=null;
		String sqlSent="UPDATE `taller`.`usuario` SET " +
				"user='"+((Usuario)us).getUser()+"', " +
				"pass='"+((Usuario)us).getPass()+"', " +
				"nombre='"+((Usuario)us).getNombre()+"', " +
				"apellido='"+((Usuario)us).getApellido()+"', " +
				"mail='"+((Usuario)us).getMail()+"' " +
				"WHERE id="+((Usuario)us).getId();
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
	public Usuario buscar(Integer id) {
		Usuario usuario=new Usuario();
		try {
			String sqlSent="SELECT * FROM usuario WHERE id="+id;
			usuarioID=consultar(sqlSent);
			if(usuarioID.next()){
				usuario.setId(id);
				usuario.setUser(usuarioID.getString("user"));
				usuario.setPass(usuarioID.getString("pass"));
				usuario.setNombre(usuarioID.getString("nombre"));
				usuario.setApellido(usuarioID.getString("apellido"));
				usuario.setMail(usuarioID.getString("mail"));
				usuario.setRol(new RolDao().buscar(usuarioID.getInt("rol_id")));
				return usuario;
			}else{
				usuario=null;
				return usuario;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			usuario=null;
			return usuario;
		}
	}
	
	public List<Usuario> buscador(String cond) throws SQLException, Exception{
		String sentenciaSQL = "SELECT * FROM usuario WHERE nombre like '%" + cond + "%' " +
							"or apellido like '%admin%'	or mail like '%" + cond + "%' " +
							"or nomusuario like '%" + cond + "%' " +
							"or rol like '%" + cond + "%' " +
							"ORDER BY id ASC";
		System.out.println(sentenciaSQL);
		Usuario usuario = null;
		ResultSet filasConsulta = consultar(sentenciaSQL);
		while (filasConsulta.next()) {
			usuario = new Usuario();
			usuario.setId(filasConsulta.getInt("id"));
			usuario.setNombre(filasConsulta.getString("nombre"));
			usuario.setApellido(filasConsulta.getString("apellido"));
			usuario.setMail(filasConsulta.getString("mail"));
			usuario.setNombUsuario(filasConsulta.getString("nomusuario"));
			usuario.setPassword(filasConsulta.getString("password"));
			usuario.setRol(new RolDao().buscar(usuarioID.getInt("rol_id")));
			lista.add(usuario);
						
		}
		cerrarConexion();
		return lista;
	}
}
