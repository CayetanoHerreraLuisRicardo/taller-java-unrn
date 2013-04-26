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
			<!-- Sesión iniciada -->
			<c:if test="${sessionScope.usuario.rol.id eq 1}">
				<div id="alta">
					<h2>Alta de producto</h2>
					<form id="formAlta" action="ProductoController" method="post" enctype="multipart/form-data">
						<input type="hidden" name="accion" value="guardar">
						<fieldset id="categoria">
							<legend class="campoTit">Datos del producto</legend>
							<div id="nom">
								<label class="infoCampo">Nombre:</label>
								<input class="campo" name="v_nombre" type="text" required="required" maxlength="25" />
							</div>
							<div id="desc">
								<label class="infoCampo">Descripción: (encerrar a cada característica entre &lt;li&gt;&lt;/li&gt;)</label>
								<textarea class="campo" name="v_desc" required="required" onkeyup="return salir(this)"></textarea>
							</div>
							<div id="precio">
								<label class="infoCampo">Precio:</label>
								<input id="precioInput" class="campo" name="v_precio" onkeyup="return isNum(this,event)" type="text" required="required" maxlength="25" />
							</div>
							<!-- COMENTADO TU INGRESAR IMAGEN
							<div id="img">
								<label class="infoCampo">URL local de la imagen:</label>
								<input class="campo" name="v_img_url" type="text" required="required" maxlength="500" />
							</div>
							-->
							<!-- INGRESAR IMAGEN -->
							<div id="img">
								<label class="infoCampo">Cargar Imagen (opcional):</label>
								<input class="campo" type="file" name="v_img_url" maxlength="500"/>
							</div>
							<div id="cat">
								<label class="infoCampo">Categoría:</label>
								<select class="campo" name="v_cat_id">
								<c:forEach var="cats" items="${sessionScope.listacategorias}">
									<option value="${cats.id}">${cats.nombre}</option>
								</c:forEach>
								</select>
							</div>
						</fieldset>
						<div id="envio">
							<input type="submit" value="Enviar"/>
						</div>
					</form>
				</div>
			</c:if>
			<c:if test="${sessionScope.usuario.rol.id eq 2 || sessionScope.usuario.rol.id eq null}">
				<%	String redirectURL="HomeController";
					response.sendRedirect(redirectURL);%>
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