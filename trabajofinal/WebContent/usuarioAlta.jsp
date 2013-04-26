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
			<!-- Sesi�n iniciada -->
			<c:if test="${sessionScope.usuario.rol.id ne 2}">
				<div id="alta">
					<h2>Alta de usuario</h2>
					<!------------------------------------------------------->
					<!---Alerta---------------------------------------------->
					<!------------------------------------------------------->
					<div id="alerta">
						<jsp:include page="gadgets/alerta.jsp" />
					</div>
					<form id="formAlta" action="UsuarioController" method="post">
						<input type="hidden" name="accion" value="guardar">
						<fieldset id="user">
							<legend class="campoTit">Datos del usuario</legend>
							<div id="user">
								<label class="infoCampo">Usuario: 
									<label class="warning">El usuario solo acepta letras y n�meros.</label> 
									<label id="errorNick" style="display:none;"><br>El nombre de usuario m�nimo es de 6 caracteres.</label>
								</label>
								<input class="campo" name="v_user" type="text" onblur="return validarUser(this,event)" required="required" maxlength="12" />
							</div>
							<div id="pass1">
								<label class="infoCampo">Contrase�a:<label id="errorPass1" style="display:none;"> La contrase�a m�nima es de 6 caracteres.</label></label>
								<input id="passContent1" class="campo" name="v_pass" type="password" onblur="return validarPass1(this,event)" required="required" maxlength="11" />
							</div>
							<div id="pass2">
								<label class="infoCampo">Confirmar contrase�a:<label id="errorPass2" style="display:none"> No coinciden las contrase�as.</label></label>
								<input id="passContent2"  class="campo" name="v_pass2" type="password" onblur="return validarPass2(this,event)" required="required" maxlength="11" />
							</div>
						</fieldset>
						<fieldset id="personales">
							<legend class="campoTit">Datos personales</legend>
							<div id="nom">
								<label class="infoCampo">Nombre:</label>
								<input class="campo" name="v_nombre" type="text" required="required" maxlength="45" />
							</div>
							<div id="ape">
								<label class="infoCampo">Apellido:</label>
								<input class="campo" name="v_apellido" type="text" required="required" maxlength="45" />
							</div>
							<div id="mail">
								<label class="infoCampo">Mail:</label>
								<input class="campo" name="v_mail" type="text" required="required" maxlength="45" />
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
	<!---Pie de P�gina--------------------------------------->
	<!------------------------------------------------------->
	<div id="footer">
	<jsp:include page="footer.jsp" />
	</div>
</div>
</body>
</html>