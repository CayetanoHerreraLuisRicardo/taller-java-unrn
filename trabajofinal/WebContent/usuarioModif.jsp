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
			<c:if test="${sessionScope.usuario.rol.id eq 2 || sessionScope.usuario.rol.id eq 1}">
				<div id="alta">
					<h2>Información personal</h2>
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
					<form id="formAlta" action="UsuarioController" method="post">
						<input type="hidden" name="accion" value="modificar">
						<fieldset id="user">
							<legend class="campoTit">Datos del usuario</legend>
							<div id="user">
								<label class="infoCampo">Usuario:</label>
								<input class="campo" readonly="readonly" name="v_user" type="text" onkeypress="return validarNick(event)" required="required" maxlength="12" value="${sessionScope.usuario.user}" />
							</div>
							<div id="pass0">
								<label class="infoCampo">Contraseña:</label>
								<input id="passContent0" class="campo" name="v_pass" type="password" onkeypress="return validarPass(event)" required="required" maxlength="11" />
							</div>
							<div id="pass1">
								<label class="infoCampo">Nueva contraseña:<label id="errorPass1" style="display:none"> La contraseña mínima es de 6 caracteres.</label></label>
								<input id="passContent1"  class="campo" name="v_pass1" type="password" onkeypress="return validarPass(event)" onblur="return validarPass1(this,event)" required="required" maxlength="11" />
							</div>
							<div id="pass2">
								<label class="infoCampo">Confirmar contraseña nueva:<label id="errorPass2" style="display:none"> No coinciden las contraseñas.</label></label>
								<input id="passContent2" class="campo" name="v_pass2" type="password" onkeypress="return validarPass(event)" onblur="return validarPass2(this,event)" required="required" maxlength="11" />
							</div>
						</fieldset>
						<fieldset id="personales">
							<legend class="campoTit">Datos personales</legend>
							<div id="nom">
								<label class="infoCampo">Nombre:</label>
								<input class="campo" name="v_nombre_m" type="text" required="required" maxlength="45" value="${sessionScope.usuario.nombre}" />
							</div>
							<div id="ape">
								<label class="infoCampo">Apellido:</label>
								<input class="campo" name="v_apellido_m" type="text" required="required" maxlength="45" value="${sessionScope.usuario.apellido}" />
							</div>
							<div id="mail">
								<label class="infoCampo">Mail:</label>
								<input class="campo" name="v_mail_m" type="text" required="required" maxlength="45" value="${sessionScope.usuario.mail}" />
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