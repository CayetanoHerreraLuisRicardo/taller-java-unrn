<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" import="java.util.Enumeration"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="css/styleIndex.css"/>
	<link rel="shortcut icon" href="img/favicon.png">
	<script type="text/javascript" src="js/javascript.js"></script>
	<title>Compras</title>
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
			<!-- Inicio de sesión vacío -->
			<div id="inicio">
			<c:if test="${sessionScope.usuario.user eq null}">
				<h2>Efectos positivos de los Video Juegos</h2>
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
				<p>
					Durante años muchas personas han argumentado que los Video Juegos no tienen ningún efecto positivo a causa de su uso. 
					Se ha dicho que atrofia el cerebro, que hace a las personas mas agresivas, e incluso que hace perder la capacidad de concentración en los niños.
					Sin embargo en la realidad puede ayudar en el desarrollo de los niños, mejorar la coordinación ojo mano, he incluso hasta contribuir en problemas para combatir el sobrepeso, 
					entre otros beneficios.
				</p>
				<br>
				<img src="img/juegos.jpg" alt="Example pic" style="border: 3px solid #ccc;" /><br>
				<br>
				<p><strong>"Los Video Juegos te hacen mas lento"</strong> ¿Has escuchado esa frase? Con el paso del tiempo la industria de los Video Juegos 
					han luchado para traer al mercado juegos con contenido o educacionales, que rompan estos estereotipos. 
					Cada una de las consolas disponibles en la actualidad tiene disponible en su catalogo de juegos alternativas muy variadas. 
					Juegos como Brain Age, Jumpstart, y Oregon Trail todos ellos ayudan a niños y adultos en el aprendizaje de las matemáticas, la lectura, historia, desarrollo de mejores reflejos y mucho mas. 
					Otros juegos que se pueden considerar educativos podrían ser los de simulación o estrategia pues en estos se simulan ambientes ficticios tales como SimCity, Civilization, y Nintendogs.
					Estos enseñan a las personas como sus decisiones pueden afectar positiva o negativamente sus vidas. 
					Este tipo de juegos desarrollan un pensamiento de toma de decisiones rápidas pero bien pensadas a trabes del pensamiento analítico.
				</p>
				<h2>Un buen debate</h2>
				<p>Debatir acerca de los efectos positivos de los Video Juegos o no en el ser humano podría llevarnos hasta el fin de los tiempos. 
					Personas alrededor del mundo han experimentado buenas y malas cosas con los Video Juegos, y estoy seguro que al final serán mas cosas positivas que negativas las que terminen por inclinar la balanza. 
					Ahora te digo termina de leer esto, levántate de la silla y disfruta de alguna de las cosas positivas que los Video Juegos traen a tu vida.
				</p>
			<!-- Sesión iniciada -->
			</c:if>
			<c:if test="${sessionScope.usuario.user ne null}">
				<h2>Bienvenido, <c:out value="${sessionScope.usuario.nombre}" /> <c:out value="${sessionScope.usuario.apellido}" />.</h2>
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
				<c:if test="${sessionScope.usuario.rol.id eq 1}">
				</c:if>
			</c:if>
			</div>
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