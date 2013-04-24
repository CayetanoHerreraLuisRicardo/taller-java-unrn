<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.Enumeration"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="css/styleAlta.css"/>
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
		<!---Estructura de la página en cuestión----------------->
		<!------------------------------------------------------->
		<div id="content">
			<!-- Sesión iniciada -->
			<c:if test="${sessionScope.usuario.rol.id ne 2}">
				<div id="alta">
					<h2>Alta de usuario</h2>
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
						<input type="hidden" name="accion" value="guardar">
						<fieldset id="user">
							<legend class="campoTit">Datos del usuario</legend>
							<div id="user">
								<label class="infoCampo">Usuario:</label>
								<input class="campo" name="v_user" type="text" onkeypress="return validarNick(event)" required="required" maxlength="12" />
							</div>
							<div id="pass1">
								<label class="infoCampo">Contraseña:<label id="errorPass1" style="display:none;"> La contraseña mínima es de 6 caracteres.</label></label>
								<input id="passContent1" class="campo" name="v_pass" type="password" onkeypress="return validarPass(event)" onblur="return validarPass1(this,event)" required="required" maxlength="11" />
							</div>
							<div id="pass2">
								<label class="infoCampo">Confirmar contraseña:<label id="errorPass2" style="display:none"> No coinciden las contraseñas.</label></label>
								<input id="passContent2"  class="campo" name="v_pass2" type="password" onkeypress="return validarPass(event)" onblur="return validarPass2(this,event)" required="required" maxlength="11" />
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
	<!---Pie de Página--------------------------------------->
	<!------------------------------------------------------->
	<div id="footer">
	<jsp:include page="footer.jsp" />
	</div>
</div>
</body>
</html>