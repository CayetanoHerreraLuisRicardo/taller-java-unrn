package modelo;

import java.util.*;

public class Pedido {
	private Integer id;
	private String fechaCompra;
	private Usuario usuario;
	private Hashtable<Producto,Integer> productos=new Hashtable<Producto,Integer>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFechaCompra() {
		return fechaCompra;
	}

	public void setFechaCompra(String fechaCompra) {
		this.fechaCompra = fechaCompra;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Hashtable<Producto,Integer> getProductos() {
		return productos;
	}

	public void setProductos(Hashtable<Producto,Integer> productos) {
		this.productos = productos;
	}
	
	public Pedido(){}

}