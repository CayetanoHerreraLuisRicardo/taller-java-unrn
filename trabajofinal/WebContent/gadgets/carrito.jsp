<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"
	import="java.util.*, modelo.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="css/styleCarrito.css"/>
		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3/jquery.min.js"></script>
		<script type="text/javascript">
			$(document).ready(function(){ 
				$(".trigger").click(function(){ 
					$(".panel").toggle("fast"); 
					$(this).toggleClass("active"); 
					return false; 
				}); 
			});
		</script>
	</head>
	<body>
		<!-- Contenido del panel -->
		<div id="gadgetCarrito">
			<div class="panel">
				<%	@SuppressWarnings("unchecked")
					Hashtable<Producto,Integer>carrito=(Hashtable<Producto,Integer>)session.getAttribute("carrito");if(carrito.size()==0){
					%><h3>Cargue un producto para ver </h3><%
				}else{%>
				<h3>Carrito</h3>
				<table id="prods">
					<tr>
						<th>Producto</th>
						<th>Precio</th>
						<th>Cantidad</th>
						<th>Subtotal</th>
					</tr>
					<%	Enumeration<Producto>prods=carrito.keys();
						Double total=0.00;
						while(prods.hasMoreElements()){
							Producto prod=prods.nextElement();
							Integer cantidad=carrito.get(prod);
							total=total+prod.getPrecio()*cantidad;%>
							<tr>
								<td><%=prod.getNombre() %></td>
								<td><%=prod.getPrecio() %></td>
								<td><%=cantidad %></td>
								<td><%=prod.getPrecio()*cantidad %></td>
								<td><a href="ProductoController?accion=carritoDel&cat=<%=request.getParameter("cat")%>&prodID=<%=prod.getId() %>">Sacar</a></td>
							</tr>
							<%
						}%>
						<tr>
							<td></td>
							<td></td>
							<td>Total:</td>
							<td><%=total%></td>
						</tr>
				</table>
				<%}%>
			</div>
			<!-- Boton del carrito -->
			<a class="trigger" href="#">Carrito</a>
		</div>
	</body>
</html>