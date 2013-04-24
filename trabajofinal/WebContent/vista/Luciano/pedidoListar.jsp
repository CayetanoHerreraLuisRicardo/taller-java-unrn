<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.Enumeration"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="css/styleLista.css"/>
	<link rel="shortcut icon" href="img/favicon.png">
	<script type="text/javascript" src="js/javascript.js"></script>
	<title>Compras</title>
</head>
<!-- Verifica Existencia del usuario -->
<%if(session.getAttribute("usuario") == null){
	response.sendRedirect("HomeController");
	return;
}
		@SuppressWarnings("unchecked")
		Enumeration<String> verif=session.getAttributeNames();
		Boolean contiene=false;
		while(verif.hasMoreElements()){
			String elem=verif.nextElement();
			if(elem.contains("listacategorias")){
				contiene=true;
			}
		}if(contiene == false){
			response.sendRedirect("HomeController");
			return;
		}%>
<body>
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
			<h2 id="titProd">Pedidos</h2>
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
					<c:forEach var="peds" items="${requestScope.pedidos}">
					<div class="block_ped">
						<h3 class="tit_ped">Pedido N�: ${peds.id}<label class="fecha_ped">Fecha: ${peds.fechaPedido}</label></h3>
						<c:forEach var="prods" items="${peds.productos}">
						<div class="block_prod">
							<h3 class="tit_prod">${prods.nombre}<label class="precio_prod">Precio: $${prods.precio}</label></h3>
						</div>
						</c:forEach>
					</div>
					</c:forEach>
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