<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.Enumeration"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="css/styleAlta.css"/>
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
			<!-- Sesión iniciada -->
				<c:if test="${sessionScope.usuario.rol.id eq 1}">
					<div id="alta">
						<h2>Eliminar Categoría</h2>
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
						<fieldset id="categoria">
							<legend class="campoTit">Datos de la categoría</legend>
							<input type="hidden" name="accion" value="eliminar">
							<table>
								<tr><th>Categoría</th><th>¿Eliminar?</th></tr>
								<c:forEach var="cats" items="${sessionScope.listacategorias}">
									<tr>
										<td class="delDesc" id="${cats.id}">${cats.nombre}</td>
										<td class="del">
											<!-- <input type="checkbox" name="cat" value="${cats.id}"> -->
											<a class="borrar" href="CategoriaController?accion=eliminar&cat=${cats.id}">Borrar</a>
										</td>
									</tr>
								</c:forEach>
							</table>
						</fieldset>
					</div>
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