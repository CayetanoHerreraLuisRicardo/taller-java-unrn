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
						<h2>Modificación de categoría</h2>
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
						<form id="formAlta" action="CategoriaController" method="post">
							<fieldset id="categoria">
								<legend class="campoTit">Datos de la categoría</legend>
								<div id="nom">
									<label class="infoCampo">Nombre de la Categoria:</label>
									<input type="hidden" name="accion" value="modificar">
									<input class="campo" name="v_nombre" type="text" required="required" maxlength="25" />
								</div>
								<div id="nom">
									<label class="infoCampo">Nombre modificado:</label>
									<input class="campo" name="v_nombre_m" type="text" required="required" maxlength="25" />
								</div>
							</fieldset>
							<div id="envio">
								<input type="submit" value="Enviar"/>
							</div>
						</form>
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