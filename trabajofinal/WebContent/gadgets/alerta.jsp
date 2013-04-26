<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
					<c:choose>
						<c:when test="${requestScope.exito eq true}">
							<a class="alerta" href="#alerta">${requestScope.error}</a>
						</c:when>
						<c:when test="${requestScope.exito eq false}">
							<a class="alerta" href="#alerta">${requestScope.error}</a>
						</c:when>
					</c:choose>