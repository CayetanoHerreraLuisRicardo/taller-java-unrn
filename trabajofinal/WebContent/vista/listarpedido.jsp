<%@page import="modelo.Usuario"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>PS3 Argento</title>
		<meta http-equiv="Content-Language" content="English" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" type="text/css" href="style.css" media="screen" />
		<script>
			function a(){
				alert("esto anda pero no se como");
			}
		</script>
	</head>
	<body>

		<div id="wrap">
		
			<div id="header">
				
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
						<c:if test="${sessionScope.usulog != null}">
							<h2><u>Pedidos</u></h2>
							<c:if test="${!empty listapedido}">
									   	<table border="1" align="center">
											<tr>
												<td>Id</td>
												<td>Nombre</td>
												<td>Descripcion</td>
												<td>Precio</td>
												<td>Carrito</td>
											</tr>
											<c:forEach var="ped" items="${listapedido}" >
												<tr>
													<td><c:out value="${ped.id}"/></td>
													<td><c:out value="${ped.fecha_pedido}"/></td>
													<td><c:out value="${ped.estado}"/></td>
													<td><c:out value="${ped.fecha_entrega}"/></td>
													<td><c:out value="${ped.usuario_id}"/></td>
												</tr>
																	
											</c:forEach>
											
											</table>
								
							</c:if>
							<c:if test="${empty listapedido}">
								<h2>No hay pedidos</h2>
							</c:if>
						</c:if>
						<c:if test="${sessionScope.usulog == null }">
							<h2 id="nota"><font color="red"><strong><center>Debes INICIAR SESION para poder comprar insumos en PS3-ARGENTO!!</center></strong></font></h2>
						</c:if>
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
					
				</div>

			</div>

		</div>

		<div id="footer">
			Diseñado por <a href="https://es-la.facebook.com/manolo.sanhueza">Emanuel Sanhueza</a>, Gracias a <a href="http://www.openwebdesign.org/">Mauro y Nico</a>
		</div>

	</body>
</html>