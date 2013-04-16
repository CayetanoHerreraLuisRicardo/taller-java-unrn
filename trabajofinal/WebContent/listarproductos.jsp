<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Pagina de compra de insumos mas grande del pais</title>
		<meta http-equiv="Content-Language" content="English" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" type="text/css" href="style.css" media="screen" />
	</head>
	<body>

		<div id="wrap">
		
			<div id="header">
				<h1><a  id="cabeza "href="home.jsp" >J u e g o t e c a</a></h1>
			<img ></img>
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
					<div class="algo">
						<h2><u>Productos</u></h2>
				    	<table border=""  width="100%" background="/images/flight_simulator_x-1682316.jpg">
							<tr>
								<td>Foto</td>
								<td>Id</td>
								<td>Nombre</td>
								<td>Descripcion</td>
								<td>Precio</td>
								<td>Carrito</td>
							</tr>
							<c:forEach var="prod" items="${listaproductos}" >
								<tr>
									<td><c:out value="${prod.id}"/></td>
									<td><c:out value="${prod.nombre}"/></td>
									<td><c:out value="${prod.descripcion}"/></td>
									<td><c:out value="${prod.precio}"/> </td>
									<td><a href="ProductoController?id=<c:out value="${prod.id}"/>&accion=insertarcarrito&catid=<c:out value="${prod.categoria.id}"/>" >Añadir</a></td>
									
								</tr>
							</c:forEach>
						</table>
						<br>
					</div> 
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
					<ul>		    
			    		<c:forEach var="p" items="${sessionScope.carrito.productos}" >
							<li><c:out value="${p.id}"/></li>							
						</c:forEach>
			      	</ul>
				</div>

			</div>

		</div>

		<div id="footer">
			Diseñado por <a href="https://es-la.facebook.com/manolo.sanhueza">Emanuel Sanhueza</a>, Gracias a <a href="http://www.openwebdesign.org/">Mauro y Nico</a>
		</div>

	</body>
</html>