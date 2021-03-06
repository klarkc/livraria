<%@page pageEncoding="UTF-8" %>
<jsp:useBean id="livraria" class="livraria.Livraria" scope="request"></jsp:useBean>
<jsp:setProperty name="livraria" property="response" value="${pageContext.response}"></jsp:setProperty>
<jsp:setProperty name="livraria" property="request" value="${pageContext.request}"></jsp:setProperty>
<!doctype html>

<html lang="pt">
<head>
    <jsp:getProperty name="livraria" property="head"></jsp:getProperty>
</head>
<body>
    <jsp:getProperty name="livraria" property="navBar"></jsp:getProperty>
    <div class="container header">
        <h1>Livraria Online</h1>
    </div>
    <div class="container loja">
        <jsp:getProperty name="livraria" property="messages"></jsp:getProperty>
        <jsp:getProperty name="livraria" property="index"></jsp:getProperty>
    </div>
</body>
</html>
