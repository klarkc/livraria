<%@page pageEncoding="UTF-8" %>
<jsp:useBean id="livraria" class="livraria.Livraria"></jsp:useBean>
<jsp:setProperty name="livraria" property="response" value="${pageContext.response}"></jsp:setProperty>
<jsp:setProperty name="livraria" property="request" value="${pageContext.request}"></jsp:setProperty>
