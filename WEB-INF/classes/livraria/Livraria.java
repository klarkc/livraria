package livraria;


import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import javax.servlet.http.*;

public class Livraria {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private SessionMessages messages;
    private String curUrl;
    private boolean toBeRedirected = false;
    private boolean admin = false;

    public Livraria() {}

    public Livraria(HttpServletRequest request) {
        this.request = request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
        this.session = request.getSession();
        this.messages = new SessionMessages(session);
        admin = (session.getAttribute("usuario") != null);
        curUrl = request.getServletPath().substring(1);
        System.out.println("URL REQUISITADA: " + curUrl + " " + request.getMethod());

        if(curUrl.equals("login.jsp") && request.getMethod().equals("POST")) {
            this.login();
        }

        if(curUrl.equals("logoff.jsp")) {
            this.logoff();
        }

        if(curUrl.equals("adicionar_usuario.jsp") && !admin) {
            messages.addError("Acesso Negado!");
            redirect("index.jsp");
        }

        if(curUrl.equals("adicionar_usuario.jsp") && request.getMethod().equals("POST")) {

        }
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public String getHead() {
        HtmlBuilder head = new HtmlBuilder();

        head.appendLine("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />");
        head.appendLine("<title>Livraria Online</title>");
        head.appendLine("<meta name=\"description\" content=\"Livraria Online\">");
        head.appendLine("<meta name=\"author\" content=\"Walker Gusmão Leite\">");
        
        //Jquery
        head.appendLine("<script src=\"https://code.jquery.com/jquery-2.2.3.min.js\"></script>");
  
        //Bootstrap Assets
        head.appendLine("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css\" integrity=\"sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7\" crossorigin=\"anonymous\">");
        head.appendLine("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css\" integrity=\"sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r\" crossorigin=\"anonymous\">");
        head.appendLine("<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js\" integrity=\"sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS\" crossorigin=\"anonymous\"></script>");
  
        // Custom CSS
        head.appendLine("<link rel=\"stylesheet\" href=\"css/styles.css?v=1.0\">");

        head.appendLine("<!--[if lt IE 9]><script src=\"http://html5shiv.googlecode.com/svn/trunk/html5.js\"></script>  <![endif]-->");

        return head.toString();
    }

    public String getNavBar() {
        HtmlBuilder nav = new HtmlBuilder();
        Map<String, String> links = new HashMap<String, String>();

        links.put("Home", "index.jsp");
        if(admin) {
            links.put("Adicionar Livro", "adicionar_livro.jsp");
            links.put("Adicionar Editora", "adicionar_editora.jsp");
            links.put("Adicionar Usuário", "adicionar_usuario.jsp");
            links.put("Sair", "logoff.jsp");
        }

        nav.appendLine("<nav class=\"navbar navbar-default\">");
        nav.appendLine("<div class=\"container\">");

        nav.appendLine("<div class=\"navbar-header\">");

        // Brand e Botão Responsivo
        nav.appendLine("<button type=\"button\" class=\"navbar-toggle collapsed\" data-toggle=\"collapse\" data-target=\"#menu\" aria-expanded=\"false\">");
        nav.appendLine("<span class=\"sr-only\">Mostrar/Esconder Navegação</span>");
        nav.appendLine("<span class=\"icon-bar\"></span>");
        nav.appendLine("<span class=\"icon-bar\"></span>");
        nav.appendLine("<span class=\"icon-bar\"></span>");
        nav.appendLine("</button>");
        nav.appendLine("<a class=\"navbar-brand\" href=\"/livraria\">Livraria Online</a>");

        nav.appendLine("</div>"); // /.navbar-header

        // Menu
        nav.appendLine("<div class=\"collapse navbar-collapse\" id=\"menu\">");
        nav.appendLine("<ul class=\"nav navbar-nav\">");
        List<String> itens = new ArrayList(links.keySet());
        Collections.reverse(itens);
        for (String item: itens) {
            nav.appendLine("<li");
            if(links.get(item).equals(curUrl)) {
                nav.appendLine(" class=\"active\"");
            }
            nav.appendLine("><a href=\"" + links.get(item) + "\">" + item + "</a></li>");
        }

        nav.appendLine("</ul>");

        // Login Form
        if(!admin) {
            nav.appendLine("<form class=\"navbar-form navbar-left\" action=\"login.jsp\" method=\"post\">");
            nav.appendLine("<div class=\"form-group\">");
            nav.appendLine("<input class=\"form-control\" type=\"text\" name=\"nome\" placeholder=\"Login\">");
            nav.appendLine("<input class=\"form-control\" type=\"password\" name=\"senha\" placeholder=\"Senha\">");
            nav.appendLine("</div>"); // /.form-group
            nav.appendLine("<button type=\"submit\" class=\"btn btn-default\">Login</button>");
            nav.appendLine("</form>");
        }

        // Busca
        nav.appendLine("<form class=\"navbar-form navbar-right\" action=\"index.jsp\" method=\"post\" role=\"search\">");
        nav.appendLine("<div class=\"form-group\">");
        nav.appendLine("<input class=\"form-control\" type=\"text\" name=\"texto\" placeholder=\"Digite um termo\">");
        nav.appendLine("</div>"); // /.form-group
        nav.appendLine("<button type=\"submit\" class=\"btn btn-default\">Buscar</button>");
        nav.appendLine("</form>");

        nav.appendLine("</div>"); // /.navbar-collapse
        
        nav.appendLine("</div>"); // /.container-fluid
        nav.appendLine("</nav>");
        
        return nav.toString();
    }
    
    public String getIndex() {
        HtmlBuilder index = new HtmlBuilder();
        Iterator<Livro> livros = (new ArrayList<Livro>()).iterator();
        int cols = 3;
        String colClass = "col-md-4";

        if(request.getMethod() == "GET") {
            livros = Livro.findAll().iterator();
        } else {
            //String texto = request.getAttribute("texto");
            //livros = Livro.search(texto);
        }

        while(livros.hasNext()) {
            index.appendLine("<div class=\"row\">");
            for(int i = 0; i < cols; i++) {
                Livro livro = livros.next();

                index.appendLine("<div class=\"" + colClass + "\">");
                index.appendLine("<div class=\"panel panel-default book\">");

                // Panel Head
                index.appendLine("<div class=\"panel-heading\">");
                index.append(livro.getTitulo());
                index.append("</div>");

                // Panel Body
                index.appendLine("<div class=\"panel-body row\">");

                index.appendLine("<div class=\"col-xs-6\">");
                index.append("<img alt=\"Imagem do livro ");
                index.append(livro.getTitulo());
                index.append("\" src=\"");
                index.append(livro.getFoto());
                index.append("\">");
                index.append("</div>"); // .col-xs-6

                index.appendLine("<ul class=\"col-xs-6 list-group\">");
                index.appendLine("<li class=\"list-group-item autor\">Autor: ");
                index.append(livro.getAutor());
                index.append("</li>");
                index.appendLine("<li class=\"list-group-item ano\">Ano: ");
                index.append(livro.getAno());
                index.append("</li>");
                index.appendLine("<li class=\"list-group-item editora\">Editora: ");
                index.append(livro.getEditora().getNome());
                index.append("</li>");
                index.appendLine("<li class=\"list-group-item preco\">Valor: ");
                index.append(String.valueOf(livro.getPreco()).replace(".", ","));
                index.append("</li>");
                index.appendLine("</ul>");

                index.appendLine("</div>"); // .panel-body

                index.appendLine("</div>"); // .panel
                index.appendLine("</div>"); // .col-md-4
            }
            index.appendLine("</div>"); // .row
        }

        return index.toString();
    }

    public String getMessages() {
        if(toBeRedirected) return new String();
        return messages.getAndFlush();
    }

    public void login() {
        Usuario usuario = new Usuario();
        if(usuario.loadBy("nome", request.getParameter("nome"))) {
            if(usuario.getSenha().equals(request.getParameter("senha"))) {
                session.setAttribute("usuario", usuario);
                System.out.println("USUÁRIO LOGADO: " + usuario.getNome());
            } else {
                messages.addError("Senha incorreta, por favor tente novamente");
            }
        } else {
            messages.addError("Usuário não encontrado, por favor tente novamente");
        }

        redirect("index.jsp");
    }

    public void logoff() {
        session.removeAttribute("usuario");
        redirect("index.jsp");
    }

    /*
    public boolean inserirLivro(Livro livro) {
        try {
            int id = gerarId();
            ps = con.prepareStatement("INSERT INTO livro VALUES (?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, id);
            ps.setString(2, livro.getTitulo());
            ps.setString(3, livro.getAutor());
            ps.setInt(4, livro.getAno());
            ps.setDouble(5, livro.getPreco());
            ps.setString(6, livro.getFoto());
            ps.setInt(7, livro.getEditora().getId());
            ps.executeUpdate();
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public int gerarId() {
        String novoId;
        try {
            ps = con.prepareStatement("SELECT MAX(id) as novoid FROM livro");
            rs = ps.executeQuery();
            rs.next();
            novoId = rs.getString("novoid");
            if (novoId == null) {
                return 1;
            } else {
                return Integer.parseInt(novoId) + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public boolean atualizarLivro(Livro livro) {
        try {
            ps = con.prepareStatement(
                "UPDATE livro " +
                "SET titulo = ?, " +
		    	"autor = ?, " +
                "ano = ?, " +
                "preco = ?, " +
                "foto = ? " +
                "idEditora = ? " +
                "WHERE id = ?"
                );
            ps.setInt(1, livro.getId());
            ps.setString(2, livro.getTitulo());
            ps.setString(3, livro.getAutor());
            ps.setInt(4, livro.getAno());
            ps.setDouble(5, livro.getPreco());
            ps.setString(6, livro.getFoto());
            ps.setInt(7, livro.getEditora().getId());
            ps.executeUpdate();
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean excluirLivro(Livro livro) {
        try {
            ps = con.prepareStatement("DELETE FROM livro WHERE id = ?");
            ps.setInt(1, livro.getId());
            ps.executeUpdate();
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public ArrayList<Livro> listarLivros() {
        ArrayList<Livro> livros = new ArrayList<Livro>();
        try {
            ps = con.prepareStatement("SELECT * FROM livro");
            rs = ps.executeQuery();
            
            while(rs.next()) {
                Editora editora = new Editora(
                    
                Livro livro = new Livro(
                    rs.getInt("id"),
                    rs.getString("autor"),
                    rs.getInt("ano"),
                    rs.getDouble("preco"),
                    rs.getString("foto")
                    );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    */

    private void redirect(String url) {
        toBeRedirected = true;
        try{
            response.sendRedirect(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
