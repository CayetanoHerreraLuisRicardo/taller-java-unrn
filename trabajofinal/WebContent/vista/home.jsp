<%@page import="modelo.Usuario"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>PS3 Argento</title>
		<meta http-equiv="Content-Language" content="English" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" type="text/css" href="css/style.css" media="screen" />
	</head>
	<body>

		<div id="wrap">
		
			<div id="header">
			<img src="img/ac3.jpg"></img>
		</div>

			<div id="menu">
				<c:if test="${sessionScope.usulog.rol == null}">
					<jsp:include page="menusimple.jsp" />
				</c:if>
				<c:if test="${sessionScope.usulog.rol eq 'simple'}">
					<jsp:include page="menusimple.jsp" />
				</c:if>
				<c:if test="${sessionScope.usulog.rol eq 'admin'}">
					<jsp:include page="menuadmin.jsp" />
				</c:if>
			</div>

			<div id="content">
				<div class="right"> 

					<h2>Efectos positivos de los Video Juegos</h2>
					Durante años muchas personas han argumentado que los Video Juegos no tienen ningún efecto positivo a causa de su uso. 
					Se ha dicho que atrofia el cerebro, que hace a las personas mas agresivas, e incluso que hace perder la capacidad de concentración en los niños.
					Sin embargo en la realidad puede ayudar en el desarrollo de los niños, mejorar la coordinación ojo mano, he incluso hasta contribuir en problemas para combatir el sobrepeso, 
					entre otros beneficios.	 
					<br /><br />
					<img src="img/juegos.jpg" alt="Example pic" style="border: 3px solid #ccc;" />
					<br /><br />
					<strong>"Los Video Juegos te hacen mas lento"</strong> has escuchado esa frase? Con el paso del tiempo la industria de los Video Juegos 
					han luchado para traer al mercado juegos con contenido o educacionales, que rompan estos estereotipos. 
					Cada una de las consolas disponibles en la actualidad tiene disponible en su catalogo de juegos alternativas muy variadas. 
					Juegos como Brain Age, Jumpstart, y Oregon Trail todos ellos ayudan a niños y adultos en el aprendizaje de las matemáticas, la lectura, historia, desarrollo de mejores reflejos y mucho mas. 
					Otros juegos que se pueden considerar educativos podrían ser los de simulación o estrategia pues en estos se simulan ambientes ficticios tales como SimCity, Civilization, y Nintendogs.
					Estos enseñan a las personas como sus decisiones pueden afectar positiva o negativamente sus vidas. 
					Este tipo de juegos desarrollan un pensamiento de toma de decisiones rápidas pero bien pensadas a trabes del pensamiento analítico.
					
					<h2>Un buen debate</h2>
					Debatir acerca de los efectos positivos de los Video Juegos o no en el ser humano podría llevarnos hasta el fin de los tiempos. 
					Personas alrededor del mundo han experimentado buenas y malas cosas con los Video Juegos, y estoy seguro que al final serán mas cosas positivas que negativas las que terminen por inclinar la balanza. 
					Ahora te digo termina de leer esto, levántate de la silla y disfruta de alguna de las cosas positivas que los Video Juegos traen a tu vida.
				</div>

				<div class="left"> 
					<div class="categoriamenu">
						<h2>Categorias :</h2>
						<ul>
					    	<c:forEach var="cate" items="${sessionScope.listacategorias}" >
								<li><a href="ProductoController?id=<c:out value="${cate.id}"/>&accion=listadoPorCategoria" ><c:out value="${cate.nombre}"/></a></li>
							</c:forEach>
					    </ul>
					    		
					</div>
					<c:if test="${sessionScope.usulog == null}">
					    <jsp:include page="login.jsp"/>
					</c:if>
					<c:if test="${sessionScope.usulog != null}">
						<div class="login">
						<h2><font color="blue"><center>Bienvenido Usuario ${sessionScope.usulog.nombUsuario}!!!</center></font></h2><br></br>
						<center><input class="miboton" type="button" value="Cerrar Sesion" onclick = "location='UsuarioController?accion=cerrarSesion'" /></center>
						</div>
					</c:if>	
				</div>

				<div style="clear: both;">
					
				</div>

			</div>

		</div>

		<div id="footer">
			Diseñado por <a href="https://es-la.facebook.com/manolo.sanhueza">Emanuel Sanhueza</a>, Gracias a <a href="http://www.openwebdesign.org/">Mauro y Nico</a>		
		</div>

	</body>
</html>