<%@ page import="livraria.*" %>
<%@ page import="java.util.List" %>
<!doctype html>

<html lang="pt">
<head>
    <%= Livraria.getHead() %>
</head>
<%
    // List<Livro> livros = Livro.findAll();
%>
<body>
    <div class="container header">
        <h1>Livraria Online: Login</h1>
    </div>
    <div class="container login">
        <div class="row">
            <div class="panel panel-default">
                <div class="panel-heading">Login da Loja</div>
                <div class="panel-body">
                    <form action="login" method="post" class="form-horizontal">
                        <div class="form-group">
                            <label for="nome" class="col-sm-2 control-label">Nome</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" name="nome" placeholder="Nome">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="senha" class="col-sm-2 control-label">Senha</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" name="senha" placeholder="Senha">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-10 col-sm-offset-2">
                                <button type="submit" class="btn btn-primary">Entrar</button>
                                <!--<a class="btn btn-default" href="registrar.jsp">Registrar-se</a>-->
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
