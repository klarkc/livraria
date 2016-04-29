package livraria;

import java.sql.*;

public class Livraria {
    public static String getHead() {
        String head = "  <meta charset=\"utf-8\">";
        head += "<title>Livraria Online</title>";
        head += "<meta name=\"description\" content=\"Livraria Online\">";
        head += "<meta name=\"author\" content=\"Walker Gusmão Leite\">";
        
        //Jquery
        head +="<script src=\"https://code.jquery.com/jquery-2.2.3.min.js\"></script>";
  
        //Bootstrap Assets
        head +="<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css\" integrity=\"sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7\" crossorigin=\"anonymous\">";
        head += "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css\" integrity=\"sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r\" crossorigin=\"anonymous\">";
        head += "<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js\" integrity=\"sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS\" crossorigin=\"anonymous\"></script>";
  
        // Custom CSS
        head += "<link rel=\"stylesheet\" href=\"css/styles.css?v=1.0\">";
        
        head +="<!--[if lt IE 9]><script src=\"http://html5shiv.googlecode.com/svn/trunk/html5.js\"></script>  <![endif]-->";
        
        return head;
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
