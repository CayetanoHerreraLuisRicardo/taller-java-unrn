<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" type="text/css" href="css/styleLogin.css"/>
			<c:if test="${sessionScope.usuario.user eq null}">
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
				<form id="formInic" action="UsuarioController?accion=logIn" method="post">
					<fieldset id="datos">
						<legend class="campoUsu">Iniciar Sesión</legend>
						<div id="nom">
							<label class="infoCampo">Usuario:</label>
							<input autofocus="autofocus" class="campo" name="v_user" type="text" required="required" maxlength="12" />
						</div>
						<br>
						<div id="pass">
							<label class="infoCampo">Contraseña:</label>
							<input class="campo" name="v_pass" maxlength="11" required="required" type="password" />
						</div>
					</fieldset>
					<div id="envio">
						<input type="submit" value="Iniciar sesi&oacute;n"/>
					</div>
				</form>
			</c:if>
			<!-- Sesión iniciada -->
			<c:if test="${sessionScope.usuario.user ne null}">
				<h3>Información del usuario</h3>
				<p>Hola, <c:out value="${sessionScope.usuario.user}" /></p>
				<a href="usuarioModif.jsp">Editar Perfil</a> <a href="UsuarioController?accion=logOut">Salir</a>
			</c:if>
			<c:if test="${sessionScope.usuario.rol.id eq 1}">
			</c:if>