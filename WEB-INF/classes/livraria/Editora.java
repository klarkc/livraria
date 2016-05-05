package livraria;

import java.util.List;
import java.util.ArrayList;
import java.sql.*;

public class Editora extends Model {   
    private int id;
    private String nome;
    private String cidade;
    private List<Livro> livros;
    
    public Editora(){}
    
    public Editora(String nome, String cidade)
    {
        this.nome = nome;
        this.cidade = cidade;
    }
    
    public Editora(int id, String nome, String cidade)
    {
        this.id = id;
        this.nome = nome;
        this.cidade = cidade;
    }
          
    public int getId()
    {
        return this.id;
    }   
    
    public void setId(int id)
    {
        this.id = id;
    }

    public String getNome()
    {
        return this.nome;
    }
    
    public String getCidade()
    {
        return this.cidade;
    }
    
    public List<Livro> getLivros()
    {
        return this.livros;
    }

    public static List<Editora> findAll()
    {
        return findAll(new ArrayList(), new ArrayList());
    }    

    public static List<Editora> findAll(List<String> fields, List values)
    {
        try {
            PreparedStatement ps = sanitizeColumns("SELECT * FROM editora", "editora", fields);

            for(int i = 0; i < values.size(); i++) {
                ps.setObject(i, values.get(i));
            }

            ResultSet rs = executeQuery(ps);
            List<Editora> editoras = new ArrayList<Editora>();

            while(rs.next()) {
                editoras.add(new Editora(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("cidade")
                ));
            }
            return editoras;
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
            if(i==1) this.nome = (String) values.get(i);
            if(i==2) this.cidade = (String) values.get(i);
       }
    }
}
