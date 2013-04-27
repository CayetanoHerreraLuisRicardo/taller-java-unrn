<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*, modelo.Producto"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="css/styleLista.css"/>
	<link rel="shortcut icon" href="img/favicon.png">
	<script type="text/javascript" src="js/javascript.js"></script>
	<title>PS3 Argento</title>
</head>
<%
	//Controla la facturación del carrito
	//Falta poner errores
	String compra=request.getParameter("compra");
	if(compra==null || compra.isEmpty()){
		Boolean exito=false;
		request.setAttribute("exito", exito);
		String error="El sistema no reconoce esta Acción.";
		request.setAttribute("error", error);
		getServletContext().getRequestDispatcher("/HomeController").forward(request, response);
	}
	@SuppressWarnings("unchecked")
	Hashtable<Producto,Integer>carrito=(Hashtable<Producto,Integer>) session.getAttribute("carrito");
	if(compra.equals("facturar")){
		if(carrito==null || carrito.size()==0){
			Boolean exito=false;
			request.setAttribute("exito", exito);
			String error="No se puede facturar una compra vacía.";
			request.setAttribute("error", error);
			String url="HomeController";
			getServletContext().getRequestDispatcher(url).forward(request, response);
			return;
		}
	}else{
		Boolean exito=false;
		request.setAttribute("exito", exito);
		String error="El sistema no reconoce esta Acción.";
		request.setAttribute("error", error);
		getServletContext().getRequestDispatcher("/HomeController").forward(request, response);
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
		<!---Panel de navegación, ubicado a la derecha----------->
		<!------------------------------------------------------->
		<div id="panelDer">
			<jsp:include page="panel_der.jsp" />
		</div>
		<!------------------------------------------------------->
		<!---Estructura de la página en cuestión----------------->
		<!------------------------------------------------------->
		<div id="content">
			<h2 id="titProd">Factura</h2>
			<h3>${sessionScope.usuario.apellido}, ${sessionScope.usuario.nombre}.</h3>
			<!-- Sesión iniciada -->
			<c:if test="${sessionScope.usuario.rol.id eq 2 || sessionScope.usuario.rol.id eq 1}">
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
			</c:if>
			<c:if test="${sessionScope.usuario.rol.id ne 1 || sessionScope.usuario.rol.id ne 2}">
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