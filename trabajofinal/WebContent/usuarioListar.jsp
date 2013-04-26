<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.Enumeration"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="css/styleListaUsers.css"/>
	<link rel="shortcut icon" href="img/favicon.png">
	<script type="text/javascript" src="js/javascript.js"></script>
	<title>PS3 Argento</title>
</head>
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
			<h2 id="titProd">Lista de usuarios</h2>
			<!-- Sesión iniciada -->
			<c:choose>
				<c:when test="${sessionScope.usuario.rol.id eq 1}">
					<div id="listado">
						<c:forEach var="users" items="${requestScope.listausuarios}">
						<div class="block_user" id="user${users.id}">
							<h3 class="saludo">${users.apellido}, ${users.nombre}</h3>
							<ul class="edit">
								<li><a href="PedidoController?accion=lista&userID=${users.id}">Historial de compras</a></li>
								<li><a href="UsuarioController?accion=eliminar&userID=${users.id}">Borrar</a></li>
								<li><a href="UsuarioController?accion=adminEdit&userID=${users.id}">Editar Perfil</a></li>
							</ul>
						</div>
						</c:forEach>
					</div>
				</c:when>
				<c:when test="${sessionScope.usuario.rol.id eq 2 || sessionScope.usuario eq null}">
					<%	String redirectURL="HomeController";
						response.sendRedirect(redirectURL);%>
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