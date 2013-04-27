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
					%><h3>Carrito vacío</h3><%
				}else{%>
				<h3>Carrito</h3>
				<table id="prods">
					<tr class="fila">
						<th class="producto">Producto</th>
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
							<tr class="fila">
								<td class="producto"><%=prod.getNombre() %></td>
								<td><%=prod.getPrecio() %></td>
								<td><%=cantidad %></td>
								<td><%=prod.getPrecio()*cantidad %></td>
								<td><a href="ProductoController?accion=carritoDel&cat=<%=prod.getCategoria().getId()%>&prodID=<%=prod.getId() %>">Sacar</a></td>
							</tr>
							<%
						}%>
						<tr class="fila">
							<td class="producto"></td>
							<td></td>
							<td style="text-align:right;">Total:</td>
							<td><%=total%></td>
							<td><a href="ProductoController?accion=carritoSupr">Vaciar</a></td>
						</tr>
				</table>
				<c:if test="${sessionScope.usuario eq null}">
					<label>Inicie sesión para poder finalizar la compra</label>
				</c:if>
				<c:if test="${sessionScope.usuario.id eq 1 || sessionScope.usuario.id eq 2}">
					<a id="finCompra" href="pedidoFacturar.jsp?compra=facturar">Facturar</a>
				</c:if>
				<%}%>
			</div>
			<!-- Boton del carrito -->
			<a class="trigger" href="#">Carrito</a>
		</div>
	</body>
</html>