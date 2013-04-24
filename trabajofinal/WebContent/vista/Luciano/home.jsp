<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" import="java.util.Enumeration"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="css/styleIndex.css"/>
	<link rel="shortcut icon" href="img/favicon.png">
	<script type="text/javascript" src="js/javascript.js"></script>
	<title>Compras</title>
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
		<!---Panel de navegaci�n, ubicado a la derecha----------->
		<!------------------------------------------------------->
		<div id="panelDer">
		<jsp:include page="panel_der.jsp" />
		</div>
		<!------------------------------------------------------->
		<!---Estructura de la p�gina en cuesti�n----------------->
		<!------------------------------------------------------->
		<div id="content">
			<!-- Inicio de sesi�n vac�o -->
			<div id="inicio">
			<c:if test="${sessionScope.usuario.user eq null}">
				<h2>Inicio de sesi�n</h2>
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
				<form id="formInic" action="InicioSesion" method="post">
					<fieldset id="datos">
						<legend class="campoUsu">Datos</legend>
						<div id="nom">
							<label class="infoCampo">Usuario:</label>
							<input autofocus="autofocus" class="campo" name="v_nombre" type="text" required="required" maxlength="12" />
						</div>
						<br>
						<div id="pass">
							<label class="infoCampo">Contrase�a:</label>
							<input class="campo" name="v_pass" maxlength="11" required="required" type="password" />
						</div>
					</fieldset>
					<br>
					<div id="envio">
						<input type="submit" value="Iniciar sesi&oacute;n"/>
					</div>
				</form>
			<!-- Sesi�n iniciada -->
			</c:if>
			<c:if test="${sessionScope.usuario.user ne null}">
				<h2>Bienvenido, <c:out value="${sessionScope.usuario.nombre}" /> <c:out value="${sessionScope.usuario.apellido}" />.</h2>
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
				<c:if test="${sessionScope.usuario.rol.id eq 1}">
				</c:if>
			</c:if>
			</div>
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