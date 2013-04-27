<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.Enumeration"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="css/styleListaUsers.css"/>
	<link rel="shortcut icon" href="img/favicon.png">
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
			<h2 id="titProd">Buscador</h2>
			<!-- Sesi�n iniciada -->
			<c:choose>
				<c:when test="${sessionScope.usuario.rol.id eq 1}">
					<form id="formAlta" action="UsuarioController" method="post">
						<fieldset id="categoria">
							<legend class="campoTit">Buscar Usuario</legend>
							<div id="nom">
								<label class="infoCampo">Ingrese la palabra o frase:</label>
								<input type="hidden" name="accion" value="buscar">
								<input class="campo" name="v_buscar" type="text" required="required" maxlength="25" />
							</div>
						</fieldset>
						<div id="envio">
							<input type="submit" value="Enviar"/>
						</div>
					</form>									
				</c:when>
				<b>*Tenga en cuenta que puede buscar por nombre, apellido, mail o nombre de usuario.</b><br></br>
				<b>*No es necesario ingresar la palabra entera</b>
				<c:when test="${sessionScope.usuario.rol.id eq 2 || sessionScope.usuario eq null}">
					<%	String redirectURL="HomeController";
						response.sendRedirect(redirectURL);%>
				</c:when>
			</c:choose>
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