<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*, modelo.Producto"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="css/styleFactura.css"/>
	<link rel="shortcut icon" href="img/favicon.png">
	<script type="text/javascript" src="js/javascript.js"></script>
	<title>PS3 Argento</title>
</head>
<%
	//Controla la facturación del carrito
	//Falta poner errores
	String compra=request.getParameter("compra");
	if(compra==null || compra.isEmpty()){
		String redirectURL="HomeController";
		response.sendRedirect(redirectURL);
	}
	@SuppressWarnings("unchecked")
	Hashtable<Producto,Integer>carrito=(Hashtable<Producto,Integer>) session.getAttribute("carrito");
	if(compra.equals("facturar")){
		if(carrito==null || carrito.size()==0){
			String redirectURL="HomeController";
			response.sendRedirect(redirectURL);
		}
	}else{
		String redirectURL="HomeController";
		response.sendRedirect(redirectURL);
	}

%>
<body>
<!------------------------------------------------------->
<!---Alerta---------------------------------------------->
<!------------------------------------------------------->
<div id="alerta">
	<jsp:include page="gadgets/alerta.jsp" />
</div>
<div id="wrapper">
	<!------------------------------------------------------->
	<!---Cabecera, ubicada al tope del documento------------->
	<!------------------------------------------------------->
	<div id="header">
		<jsp:include page="header.jsp" />
	</div>
	<!------------------------------------------------------->
	<!---Menú principal, ubicado debajo del header----------->
	<!------------------------------------------------------->
	<div id="main_nav">
		<jsp:include page="main_nav.jsp" />
	</div>
	<!------------------------------------------------------->
	<!---Contenido de la Página------------------------------>
	<!------------------------------------------------------->
	<div id="page">
		<!------------------------------------------------------->
		<!---Panel de navegación, ubicado a la izquierda--------->
		<!------------------------------------------------------->
		<div id="panelIzq">
		<jsp:include page="panel_izq.jsp" />
		</div>
		<!------------------------------------------------------->
		<!---Estructura de la página en cuestión----------------->
		<!------------------------------------------------------->
		<div id="content">
			<h2 id="titulo">Factura</h2>
			<!-- Sesión iniciada -->
			<c:if test="${sessionScope.usuario.rol.id eq 2 || sessionScope.usuario.rol.id eq 1}">
			<div id="factura">
				<h3>${sessionScope.usuario.apellido}, ${sessionScope.usuario.nombre}.</h3>
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
							</tr>
							<%
						}%>
						<tr class="fila">
							<td class="producto"></td>
							<td></td>
							<td style="text-align:right;">Total:</td>
							<td><%=total%></td>
						</tr>
				</table>
				<form action="PedidoController" method="post">
					<input type="hidden" name="accion" value="guardar">
					<input type="submit" value="Finalizar compra">
				</form>
			</div>
			</c:if>
			<c:if test="${sessionScope.usuario.rol.id ne 1 && sessionScope.usuario.rol.id ne 2}">
				<%	String redirectURL="HomeController";
					response.sendRedirect(redirectURL);%>
			</c:if>
		</div>
	</div>
	<!------------------------------------------------------->
	<!---Pie de Página--------------------------------------->
	<!------------------------------------------------------->
	<div id="footer">
	<jsp:include page="footer.jsp" />
	</div>
</div>
</body>
</html>