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
        <h1>Livraria Online: Cadastro de Usuários</h1>
    </div>
    <div class="container adicionar_usuario">
        <jsp:getProperty name="livraria" property="messages"></jsp:getProperty>
        <form action="adicionar_usuario.jsp" method="post">
            <div class="form-group">
                <label for="nome">Nome do Usuário</label>
                <input type="text" class="form-control" name="nome" placeholder="Nome do Usuário">
            </div>
            <div class="form-group">
                <label for="senha">Senha</label>
                <input type="password" class="form-control" name="senha" placeholder="Senha">
            </div>
            <button type="submit" class="btn btn-default">Adicionar</button>
        </form>
    </div>
</body>
</html>
