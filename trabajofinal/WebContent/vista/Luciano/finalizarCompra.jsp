<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.Enumeration, java.util.List, modelo.Producto"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="css/styleLista.css"/>
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
		}

		//Controla la facturación del carrito
		
		//Falta poner errores
		String tema=request.getParameter("tema");
		if(tema==null || tema.isEmpty()){
			getServletContext().getRequestDispatcher(request.getParameter("url")).forward(request, response);
			return;
		}
		if(tema.equals("compra")){
			@SuppressWarnings("unchecked")
			List<Producto>carrito=(List<Producto>) session.getAttribute("carrito");
			if(carrito==null || carrito.size()==0){
				Boolean exito=false;
				request.setAttribute("exito", exito);
				String error="No se puede facturar una compra vacía.";
				request.setAttribute("error", error);
				String url="/home.jsp";
				getServletContext().getRequestDispatcher(url).forward(request, response);
				return;
			}else{
				getServletContext().getRequestDispatcher(request.getParameter("url")).forward(request, response);
				return;
			}
		}
		%>
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
			<h2 id="titProd">Factura</h2>
			<h3>${sessionScope.usuario.apellido}, ${sessionScope.usuario.nombre}.</h3>
			<!-- Sesión iniciada -->
			<c:if test="${sessionScope.usuario.rol.id eq 2}">
				<div id="error">
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
				</div>
				<div id="listado">
					<c:forEach var="prods" items="${sessionScope.carrito}">
					<div class="block_prod">
						<h3 class="tit_prod">${prods.nombre}<label class="precio_prod">Precio: $${prods.precio}</label></h3>
					</div>
					</c:forEach>
					<div class="block_prod">
						<h3 id="total" class="tit_prod"><a href="PedidoController?accion=guardar">Pagar</a><label class="precio_prod">Total: $${sessionScope.total}</label></h3>
					</div>
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