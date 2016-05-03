package livraria;

import java.lang.StringBuilder;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.*;

public class Livraria {
    public static String getHead() {
        StringBuilder head = new StringBuilder();

        head.append("<meta charset=\"utf-8\">");
        head.append("<title>Livraria Online</title>");
        head.append("<meta name=\"description\" content=\"Livraria Online\">");
        head.append("<meta name=\"author\" content=\"Walker Gusmão Leite\">");
        
        //Jquery
        head.append("<script src=\"https://code.jquery.com/jquery-2.2.3.min.js\"></script>");
  
        //Bootstrap Assets
        head.append("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css\" integrity=\"sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7\" crossorigin=\"anonymous\">");
        head.append("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css\" integrity=\"sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r\" crossorigin=\"anonymous\">");
        head.append("<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js\" integrity=\"sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS\" crossorigin=\"anonymous\"></script>");
  
        // Custom CSS
        head.append("<link rel=\"stylesheet\" href=\"css/styles.css?v=1.0\">");

        head.append("<!--[if lt IE 9]><script src=\"http://html5shiv.googlecode.com/svn/trunk/html5.js\"></script>  <![endif]-->");

        return head.toString();
    }

    public static String getNavBar(HttpServletRequest request) {
        StringBuilder nav = new StringBuilder();
        HttpSession session = request.getSession();
        String curUrl = request.getServletPath().substring(1);
        Map<String, String> links = new HashMap<String, String>();
        boolean admin = (session.getAttribute("usuario") != null);

        links.put("Home", "index.jsp");
        if(admin) {
            links.put("Adicionar Livro", "adicionar_livro.jsp");
            links.put("Adicionar Editora", "adicionar_editora.jsp");
            links.put("Adicionar Usuário", "adicionar_usuario.jsp");
            links.put("Sair", "logoff.jsp");
        }

        nav.append("<nav class=\"navbar navbar-default\">");
        nav.append("<div class=\"container\">");

        nav.append("<div class=\"navbar-header\">");

        // Brand e Botão Responsivo
        nav.append("<button type=\"button\" class=\"navbar-toggle collapsed\" data-toggle=\"collapse\" data-target=\"#menu\" aria-expanded=\"false\">");
        nav.append("<span class=\"sr-only\">Mostrar/Esconder Navegação</span>");
        nav.append("<span class=\"icon-bar\"></span>");
        nav.append("<span class=\"icon-bar\"></span>");
        nav.append("<span class=\"icon-bar\"></span>");
        nav.append("</button>");
        nav.append("<a class=\"navbar-brand\" href=\"/livraria\">Livraria Online</a>");

        nav.append("</div>"); // /.navbar-header

        // Menu
        nav.append("<div class=\"collapse navbar-collapse\" id=\"menu\">");
        nav.append("<ul class=\"nav navbar-nav\">");
        for (String item: links.keySet()) {
            nav.append("<li");
            if(links.get(item).equals(curUrl)) {
                nav.append(" class=\"active\"");
            }
            nav.append("><a href=\"" + links.get(item) + "\">" + item + "</a></li>");
        }

        nav.append("</ul>");

        // Login Form
        if(!admin) {
            nav.append("<form class=\"navbar-form navbar-left\" action=\"login.jsp\" method=\"post\">");
            nav.append("<div class=\"form-group\">");
            nav.append("\n<input class=\"form-control\" type=\"text\" name=\"nome\" placeholder=\"Login\">");
            nav.append("\n<input class=\"form-control\" type=\"password\" name=\"senha\" placeholder=\"Senha\">");
            nav.append("</div>"); // /.form-group
            nav.append("\n<button type=\"submit\" class=\"btn btn-default\">Login</button>");
            nav.append("</form>");
        }

        // Busca
        nav.append("<form class=\"navbar-form navbar-right\" action=\"busca.jsp\" method=\"post\" role=\"search\">");
        nav.append("<div class=\"form-group\">");
        nav.append("\n<input class=\"form-control\" type=\"text\" name=\"texto\" placeholder=\"Digite um termo\">");
        nav.append("</div>"); // /.form-group
        nav.append("\n<button type=\"submit\" class=\"btn btn-default\">Buscar</button>");
        nav.append("</form>");

        nav.append("</div>"); // /.navbar-collapse
        
        nav.append("</div>"); // /.container-fluid
        nav.append("</nav>");
        
        return nav.toString();
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
}
