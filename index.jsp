<%@ page import="livraria.*" %>
<%@ page import="java.util.List" %>
<!doctype html>

<html lang="pt">
<head>
    <%= Livraria.getHead() %>
</head>
<%
    List<Livro> livros = Livro.findAll();
%>
<body>
    <div class="container header">
        <h1>Livraria Online</h1>
    </div>
    <div class="container loja">
        <% for(int i = 1; i <= livros.size(); i++) { %>
            <% Livro livro =  livros.get(i-1); %>
            <!-- Formula 3 por linha -->
            <% if(i % 3 == 0) {%><div class="row"><% }; %>
                <div class="col-md-4">
                    <div class="panel panel-default book">
                        <div class="panel-heading"><%= livro.getTitulo() %></div>
                        <div class="panel-body row">
                            <div class="col-xs-6">
                                <img alt="Imagem do livro <%= livro.getTitulo() %>"
                                    src="<%= livro.getFoto() %>"
                                >
                            </div>
                            <ul class="col-xs-6 list-group">
                                <li class="list-group-item autor">Autor: <%= livro.getAutor() %></li>
                                <li class="list-group-item ano">Ano: <%= livro.getAno() %></li>
                                <li class="list-group-item editora">Editora: <%= livro.getEditora().getNome() %></li>
                                <li class="list-group-item preco">Valor: R$ <%= String.valueOf(livro.getPreco()).replace(".", ",") %></li>
                            </ul>
                        </div>
                    </div>
                </div>
            <% if(i % 3 == 0) {%></div><% }; %>
        <% } %>
    </div>
</body>
</html>
