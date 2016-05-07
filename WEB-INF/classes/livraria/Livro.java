package livraria;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class Livro extends Model {   
    private int id;
    private String titulo;
    private String autor;
    private int ano;
    private double preco;
    private String foto;
    private Editora editora;
    private int idEditora;
    
    public Livro(){}
       
    public Livro(String titulo, String autor, int ano, Double preco, String foto, Editora editora)
    {
        this.titulo = titulo;
        this.autor = autor;
        this.ano = ano;
        this.preco = preco;
        this.foto = foto;
        this.editora = editora;
    }
    
    public Livro(String titulo, String autor, int ano, Double preco, String foto, int idEditora)
    {
        this.titulo = titulo;
        this.autor = autor;
        this.ano = ano;
        this.preco = preco;
        this.foto = foto;
        this.idEditora = idEditora;
    }

    public Livro(int id, String titulo, String autor, int ano, Double preco, String foto, Editora editora)
    {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.ano = ano;
        this.preco = preco;
        this.foto = foto;
        this.editora = editora;
    }
          
    public int getId()
    {
        return this.id;
    }   
    
    public void setId(int id)
    {
        this.id = id;
    }

    public int getIdEditora()
    {
        return this.idEditora;
    }

    public void setIdEditora(int id) {
        this.idEditora = id;
    }

    public String getTitulo()
    {
        return this.titulo;
    }
    
    public String getAutor()
    {
        return this.autor;
    }
    
    public int getAno()
    {
        return this.ano;
    }
    
    public double getPreco()
    {
        return this.preco;
    }
    
    public String getFoto()
    {
        return this.foto;
    }
    
    public Editora getEditora()
    {
        return this.editora;
    }
    
    public static List<Livro> findAll()
    {
        return findAll(new ArrayList(), new ArrayList());
    }    

    public static List<Livro> findAll(List<String> fields, List values)
    {
        return findAll(fields, values, "AND", false);
    }

    public static List<Livro> findAll(List<String> fields, List values, String operator, boolean like)
    {
        List<Livro> livros = new ArrayList<Livro>();
        try {
            PreparedStatement ps;
            if(like) {
                ps =  sanitizeColumns("SELECT * FROM livro", "livro", fields, operator, "LIKE");
            } else {
                ps =  sanitizeColumns("SELECT * FROM livro", "livro", fields, operator, "=");
            }
        
            for(int i = 0; i < values.size(); i++) {
                if(like) {
                    ps.setString(i+1, "%" + values.get(i) + "%");
                } else {
                    ps.setObject(i+1, values.get(i));
                }

            }        
            
            ResultSet rs = executeQuery(ps);
            
            while(rs.next()) {
                Editora editora = new Editora();
                if(!editora.loadBy("id", rs.getInt("idEditora"))) {
                    editora = null;
                }
                livros.add(new Livro(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    rs.getInt("ano"),
                    rs.getDouble("preco"),
                    rs.getString("foto"),
                    editora
                ));
            }    
            return livros;
        } catch (SQLException e) {
            e.printStackTrace();
            return livros;
        }
    }


    public static List<Livro> search(String text) {
        List<String> db_columns = new ArrayList<String>();
        List<String> values = new ArrayList<String>();
        try {
            db_columns = Livro.getColumns("livro");

            // Add text value for fields that are not "id"
            for(int i = 0; i < db_columns.size() - 2; i++) {
                values.add(text);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return findAll(db_columns, values, "OR", true);
    }
            
    @Override
    protected void fill(List values)
    {
        for(int i = 0; i< values.size(); i++) {
            if(i==0) this.id = (int) values.get(i);
            if(i==1) this.titulo = (String) values.get(i);
            if(i==2) this.autor = (String) values.get(i);
            if(i==3) this.ano = (int) values.get(i);
            if(i==4) this.preco = (double) values.get(i);
            if(i==5) this.foto = (String) values.get(i);
            if(i==6) {
                this.editora = new Editora();
                this.editora.loadBy("id", (int) values.get(i));
            }
       }
    }

    /*
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
    */
}
