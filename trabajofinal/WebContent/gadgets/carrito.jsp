<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="css/styleCarrito.css"/>
		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3/jquery.min.js"></script>
		<script type="text/javascript">
			$(document).ready(function(){ 
				$(".trigger").click(function(){ 
					$(".panel").toggle("fast"); 
					$(this).toggleClass("active"); 
					return false; 
			    }); 
			});
		</script>
	</head>
	<body>
		<!-- Contenido del panel -->
		<div id="gadgetCarrito">
			<div class="panel">
				<h3>Carrito</h3>
				<c:forEach var="prods" items="${sessionScope.carrito.productos}">
					
				</c:forEach>
			</div>
			<!-- Boton del carrito -->
			<a class="trigger" href="#">Carrito</a>
		</div>
	</body>
</html>