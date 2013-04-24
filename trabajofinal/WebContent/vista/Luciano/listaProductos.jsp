<%@ page language="java" contentType="text/html; charset=ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
				<ul>
					<c:forEach var="prods" items="${sessionScope.carrito}">
						<li class="lvl2"><a href="#">${prods.nombre}</a></li>
					</c:forEach>
				</ul>