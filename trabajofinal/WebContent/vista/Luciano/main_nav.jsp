<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<ul>
			<li id="menuInit" class="lvl1">
				<a href="home.jsp">Inicio</a>
			</li>
			<c:if test="${sessionScope.usuario.rol.id eq 1}">
				<li id="catgs" class="lvl1"><a href="#">Categor�as</a>
					<ul>
						<li class="lvl2"><a href="categoriaAlta.jsp">Crear</a></li>
						<li class="lvl2"><a href="categoriaModif.jsp">Modificar</a></li>
						<li class="lvl2"><a href="categoriaEliminar.jsp">Borrar</a></li>
					</ul>
				</li>
				<li id="prods" class="lvl1"><a href="#">Productos</a>
					<ul>
						<li class="lvl2"><a href="productoAlta.jsp">Crear</a></li>
					</ul>
				</li>
				<li class="lvl1"><a href="#">Administrar Cuentas</a></li>
				<li class="lvl1"><a href="HomeController">Cerrar Sesi�n</a></li>
			</c:if>
			<c:if test="${sessionScope.usuario.rol.id eq 2}">
				<li class="lvl1"><a href="usuarioModif.jsp">Cuenta</a>
					<ul>
						<li class="lvl2"><a href="PedidoController?accion=lista">Historial de compras</a></li>
					</ul>
				</li>
				<li class="lvl1"><a href="#">Carrito</a><jsp:include page="listaProductos.jsp" /></li>
				<li class="lvl1"><a href="finalizarCompra.jsp&tema=compra">Finalizar Compra</a></li>
				<li class="lvl1"><a href="HomeController">Cerrar Sesi�n</a></li>
			</c:if>
			<c:if test="${sessionScope.usuario eq null}">
				<li class="lvl1"><a href="usuarioAlta.jsp">Crear una cuenta</a></li>
				<li class="lvl1"><a href="micuenta.jsp">Mi Cuenta</a></li>
				<li class="lvl1"><a href="http://www.3djuegos.com/">Ir a 3dJuegos</a></li>
			</c:if>
		</ul>