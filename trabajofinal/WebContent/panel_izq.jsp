<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="dao.CategoriaDao, java.util.List, modelo.Categoria" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	//Listar las Categorías
	CategoriaDao daoCat=new CategoriaDao();
	List<Categoria> listaCategoria=daoCat.listar();
	session.setAttribute("listacategorias", listaCategoria);
%>
			<ul id="subMenuTit" style="">
				<h3>Categorías</h3>
				<c:forEach var="cats" items="${sessionScope.listacategorias}">
					<li class="titulo" id="${cats.id}"><a href="WebController?url=/ProductoController?accion=lista&cat=${cats.id}">${cats.nombre}</a><li>
				</c:forEach>
			</ul>