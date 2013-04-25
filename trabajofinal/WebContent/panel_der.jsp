<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>PS3 Argento</title>
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
		<div class="panel"> 
		    <h3>Panel Deslizablel</h3> 
		    <p>Aqui esta nuestro panel deslizable hecho con JQuery y algunas funciones en CSS3 que nos daran esquinas redondeadas</p> 
		    <p>vendria muy bien para nuestro carrito de compras.</p>
		</div>
		<!-- Boton del carrito -->
		<a class="trigger" href="#">Carrito</a>
	</body>
</html>