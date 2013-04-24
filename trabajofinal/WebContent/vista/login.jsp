<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
	function validar(form) {
		if (form.nombusuario.value == "" && form.passusuario.value == "") {
			alert("Debe llenar todos los campos para poder continuar.");
			return (false);
		}
	}
</script>
<div class="login">
<h2><u>Iniciar Sesion :</u></h2>
<form action="UsuarioController" onsubmit="return validar(this)" method="post">
	<table>
		<tr>
			<td>Usuario:</td>
		</tr>
		<tr>
			<td><input type="text" name="nombusuario" id="campo" /></td>
			<td><input type="hidden" name="accion" value="loginUsuario"/></td>
		</tr>
		<tr>
			<td>Contraseña:</td>
		</tr>
		<tr>
			<td><input type="password" name="passusuario" id="campo" /></td>
		</tr>
	</table>
	<br>
		<center><input class="miboton" type="submit" name="iniciar" value="Iniciar Sesion" /></center>
	<table>
		<tr>
			<td><b>No tienes cuenta?</b></td>
		</tr>
		<tr>
			<td><a href="registrarusuario.jsp">Registrate Aqui!!</a></td>
		</tr>
		<c:if test="${!empty sessionScope.error}">
			<tr><td><font color="red"><%=session.getAttribute("error").toString() %></font></td></tr>
			<%session.setAttribute("error", null); %>
		</c:if>
	</table>
</form>
</div>