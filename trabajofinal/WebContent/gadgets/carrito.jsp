<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="css/styleCarrito.css"/>
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
		<div class="gadgetCarrito" style="display:none"> 
		    <h3>Panel Deslizable</h3> 
		    <p>Aqui esta nuestro panel deslizable hecho con JQuery y algunas funciones en CSS3 que nos daran esquinas redondeadas</p> 
		    <p>vendria muy bien para nuestro carrito de compras.</p>
		</div>
		<!-- Boton del carrito -->
		<a class="trigger" href="#">Carrito</a>
	</body>
</html>