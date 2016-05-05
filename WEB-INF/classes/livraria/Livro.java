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
        try {
            PreparedStatement ps = sanitizeColumns("SELECT * FROM livro", "livro", fields);
        
            for(int i = 0; i < values.size(); i++) {
                ps.setObject(i, values.get(i));
            }        
            
            ResultSet rs = executeQuery(ps);        
            List<Livro> livros = new ArrayList<Livro>();
            
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
            return null;
        }
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
