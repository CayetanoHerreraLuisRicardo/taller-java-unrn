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
	//Controla la facturaci�n del carrito
	//Falta poner errores
	String compra=request.getParameter("compra");
	if(compra==null || compra.isEmpty()){
		Boolean exito=false;
		request.setAttribute("exito", exito);
		String error="El sistema no reconoce esta Acci�n.";
		request.setAttribute("error", error);
		getServletContext().getRequestDispatcher("/HomeController").forward(request, response);
	}
	if(compra.equals("facturar")){
		@SuppressWarnings("unchecked")
		Hashtable<Producto,Integer>carrito=(Hashtable<Producto,Integer>) session.getAttribute("carrito");
		if(carrito==null || carrito.size()==0){
			Boolean exito=false;
			request.setAttribute("exito", exito);
			String error="No se puede facturar una compra vac�a.";
			request.setAttribute("error", error);
			String url="/home.jsp";
			getServletContext().getRequestDispatcher(url).forward(request, response);
			return;
		}else{
			getServletContext().getRequestDispatcher(request.getParameter("url")).forward(request, response);
			return;
		}
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
	<!---Men� principal, ubicado debajo del header----------->
	<!------------------------------------------------------->
	<div id="main_nav">
		<jsp:include page="main_nav.jsp" />
	</div>
	<!------------------------------------------------------->
	<!---Contenido de la P�gina------------------------------>
	<!------------------------------------------------------->
	<div id="page">
		<!------------------------------------------------------->
		<!---Panel de navegaci�n, ubicado a la izquierda--------->
		<!------------------------------------------------------->
		<div id="panelIzq">
		<jsp:include page="panel_izq.jsp" />
		</div>
		<!------------------------------------------------------->
		<!---Estructura de la p�gina en cuesti�n----------------->
		<!------------------------------------------------------->
		<div id="content">
			<h2 id="titProd">Factura</h2>
			<h3>${sessionScope.usuario.apellido}, ${sessionScope.usuario.nombre}.</h3>
			<!-- Sesi�n iniciada -->
			<c:if test="${sessionScope.usuario.rol.id eq 2}">
				<div id="error">
					<c:choose>
						<c:when test="${requestScope.exito eq true}">
							<c:out value="${requestScope.error}" />
							<br>
						</c:when>
						<c:when test="${requestScope.exito eq false}">
							<c:out value="${requestScope.error}" />
							<br>
						</c:when>
					</c:choose>
				</div>
				<div id="listado">
					<c:forEach var="prods" items="${sessionScope.carrito}">
					<div class="block_prod">
						<h3 class="tit_prod">${prods.nombre}<label class="precio_prod">Precio: $${prods.precio}</label></h3>
					</div>
					</c:forEach>
					<div class="block_prod">
						<h3 id="total" class="tit_prod"><a href="PedidoController?accion=guardar">Pagar</a><label class="precio_prod">Total: $${sessionScope.total}</label></h3>
					</div>
				</div>
			</c:if>
		</div>
	</div>
	<!------------------------------------------------------->
	<!---Pie de P�gina--------------------------------------->
	<!------------------------------------------------------->
	<div id="footer">
	<jsp:include page="footer.jsp" />
	</div>
</div>
</body>
</html>