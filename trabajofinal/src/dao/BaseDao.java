package dao;

import java.sql.*;
import java.util.*;

public abstract class BaseDao {
	protected static String host = "localhost";
	protected static String db = "taller";
	protected static int puertoServidorBD = 3306;					// puerto en el que escucha el servidor
	protected static String user = "root";							// usuario del servidor de base de datos
	protected static String pass = "123";							// password correspondiente al usuario
	protected static String server = "jdbc:mysql://"+host+"/"+db;	// variable utilizada para construir la url de conexión.
	private static Connection conexion;
	private static PreparedStatement sentencia;
	private static ResultSet filasConsulta;
	
	public static Connection conectar() throws Exception{
		try {
			conexion=DriverManager.getConnection(server,user,pass);
			System.out.println("Conexion con "+server+" iniciada ... OK");
			return conexion;
		}catch (SQLException ex) {
			System.out.println("Imposible realizar conexion con "+server+" ... FAIL");
		}
		return null;
		}
	public static ResultSet consultar(String consulta) throws Exception{
		try{
			conectar();
			sentencia=conexion.prepareStatement(consulta);
			filasConsulta=sentencia.executeQuery();
			System.out.println("Consulta realizada ... OK");
			return filasConsulta;
		}catch(SQLException ex){
			throw new SQLException("Error al ejecutar sentencia DB Conexion "+
				ex.getMessage());
		}
	}
	public static Integer modificar(String consulta) throws Exception{
		Integer resultado;
		try{
			conectar();
			sentencia=conexion.prepareStatement(consulta,Statement.RETURN_GENERATED_KEYS);
			resultado=sentencia.executeUpdate();
			filasConsulta=sentencia.getGeneratedKeys();
			if(filasConsulta.next()){
				resultado=filasConsulta.getInt(1);
			}
			System.out.println("Consulta realizada ... OK");
			return resultado;
		}catch(SQLException ex){
			System.out.println("Error al ejecutar sentencia DB Conexion "+
				ex.getMessage());
			resultado=-1;
			return resultado;
		}
	}
	public static void desconectar() throws Exception{
		try{
			conexion.close();
			System.out.println("Cerrar conexion con "+server+" ... OK");
		}catch (SQLException ex) {
			System.out.println("Imposible cerrar conexion ... FAIL");
		}
	}
	@SuppressWarnings("rawtypes")
	abstract public List listar();
	abstract public Integer guardar(Object obj);
	abstract public Integer eliminar(Integer id);
	abstract public Integer modificar(Object obj);
	abstract public Object buscar(Integer id);
}
