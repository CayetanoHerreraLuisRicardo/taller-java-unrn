<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.Enumeration"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="css/styleLista.css"/>
	<link rel="shortcut icon" href="img/favicon.png">
	<script type="text/javascript" src="js/javascript.js"></script>
	<title>PS3 Argento</title>
</head>
<body>
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
			<h2 id="titProd">Lista de productos</h2>
			<!-- Sesión iniciada -->
			<c:choose>
				<c:when test="${sessionScope.usuario.rol.id eq 1}">
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
						<c:forEach var="prods" items="${requestScope.listaproductos}">
						<div class="block_prod" id="prod${prods.id}">
							<h3 class="tit_prod">${prods.nombre}<label class="precio_prod">Precio: $ ${prods.precio}</label></h3><br><br>
							<img alt="Imágen no disponible" src="${prods.imgURL}" height="100%" width="25%"/>
							<ul class="desc_prod">${prods.descripcion}</ul>
							<ul class="edit">
								<li><a href="ProductoController?accion=eliminar&cat=<%=request.getParameter("cat")%>&prodID=${prods.id}">Borrar</a></li>
								<li><a href="ProductoModif?cat=<%=request.getParameter("cat")%>&id=${prods.id}">Editar</a></li>
							</ul>
						</div>
						</c:forEach>
					</div>
				</c:when>
				<c:when test="${sessionScope.usuario.rol.id eq 2 || sessionScope.usuario eq null}">
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
						<c:forEach var="prods" items="${requestScope.listaproductos}">
						<div class="block_prod">
							<h3 class="tit_prod">${prods.nombre}<label class="precio_prod">Precio: $ ${prods.precio}</label></h3><br><br>
							<img alt="Imágen no disponible" src="${prods.imgURL}" height="100%" width="25%"/>
							<ul class="desc_prod">${prods.descripcion}</ul>
							<ul class="edit">
								<li><a href="CarritoController?accion=agregar&cat=<%=request.getParameter("cat")%>&prodID=${prods.id}">Agregar</a></li>
								<li><a href="CarritoController?accion=quitar&cat=<%=request.getParameter("cat")%>&prodID=${prods.id}">Quitar</a></li>
							</ul>
						</div>
						</c:forEach>
					</div>
				</c:when>
			</c:choose>
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