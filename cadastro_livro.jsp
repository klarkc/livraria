<%@page pageEncoding="UTF-8" %>
<jsp:useBean id="livraria" class="livraria.Livraria" scope="request"></jsp:useBean>
<jsp:useBean id="livro" class="livraria.Livro" scope="page"></jsp:useBean>
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
        <h1>Livraria Online: Cadastro de livros</h1>
    </div>
    <div class="container adicionar_livro">
        <jsp:getProperty name="livraria" property="messages"></jsp:getProperty>
        <form action="cadastro_livro.jsp" method="post">
<%
   if(request.getParameter("id") != null ) {
        try {
            livro.loadBy("id", Integer.parseInt(request.getParameter("id")));
            out.println("\n<input type='hidden' value='" + livro.getId() + "' name='id'>");
        } catch (Exception e) {
            e.printStackTrace();
            livraria.addError("Erro ao carregar livro");
            livraria.redirect("index.jsp");
        }
   }
%>
            <div class="form-group">
                <label for="titulo">Título do Livro</label>
                <input type="text" class="form-control" name="titulo" placeholder="Título do Livro" value="${livro.titulo}">
            </div>
            <div class="form-group">
                <label for="autor">Nome do Autor</label>
                <input type="text" class="form-control" name="autor" placeholder="Nome do Autor" value="${livro.autor}">
            </div>
            <div class="form-group">
                <select class="form-control" name="editora">
                    <jsp:getProperty name="livraria" property="editoras"></jsp:getProperty>
                </select>
            </div>
            <div class="form-group">
                <label for="ano">Ano de Publicação</label>
                <input type="number" class="form-control" name="ano" value="2016" min="1800" max="2020" value="${livro.ano}">
            </div>
            <div class="form-group">
                <label for="preco">Preço (em Reais)</label>
                <div class="input-group">
                    <div class="input-group-addon">R$</div>
                    <input type="text" class="form-control" name="preco" placeholder="Valor" value="${livro.preco}">
                </div>
            </div>
            <div class="form-group">
                <label for="foto">URL para Foto</label>
                <input type="text" class="form-control" name="foto" value="${livro.foto}">
            </div>
            <button type="submit" class="btn btn-default">Salvar</button>
        </form>
    </div>
</body>
</html>
