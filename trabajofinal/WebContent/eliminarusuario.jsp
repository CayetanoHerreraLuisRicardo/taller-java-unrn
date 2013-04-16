<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>PS3 Argento</title>
		<meta http-equiv="Content-Language" content="English" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" type="text/css" href="style.css" media="screen" />
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
							<h2><u>Usuarios</u></h2><br />
						    	<table border="1">
									<tr>
										<td><strong>Id</strong></td>
										<td><strong>Nombre</strong></td>
										<td><strong>Apellido</strong></td>
										<td><strong>Mail</strong></td>
										<td><strong>Nombre Usuario</strong></td>
										<td><strong>Rol</strong></td>
									</tr>
									<c:forEach var="usu" items="${listausuario}" >
										<tr>
											<td><c:out value="${usu.id}"/></td>
											<td><c:out value="${usu.nombre}"/></td>
											<td><c:out value="${usu.apellido}"/></td>
											<td><c:out value="${usu.mail}"/> </td>
											<td><c:out value="${usu.nombUsuario}"/> </td>
											<td><c:out value="${usu.rol}"/> </td>
											<td><a href="UsuarioController?id=<c:out value="${usu.id}"/>&accion=eliminarusuario" >Eliminar</a></td>
											
										</tr>
									</c:forEach>
								</table>
								<br></br>
								
								<input class="miboton" type="button" name="btnBorrar" value="Volver a Buscar" onclick="location='buscador.jsp'"/>
								<input class="miboton" type="button" name="btnBorrar" value="Cancelar" onclick="location='home.jsp'"/>
								
								<c:if test="${!empty sessionScope.error}">
									<font color="red"><%=session.getAttribute("error").toString()%></font>
									<%session.setAttribute("error", null);%>
								</c:if>
								<br />
								
							<c:if test="${sessionScope.mensaje != null}">
								<br>
								<strong><font color="green"><%=session.getAttribute("mensaje").toString() %></font></strong>
								<%session.setAttribute("mensaje", null); %>
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