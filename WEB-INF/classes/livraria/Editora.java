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
        return findAll(fields, values, "AND", false);
    }

    public static List<Editora> findAll(List<String> fields, List values, String operator, boolean like)
    {
        try {
            PreparedStatement ps;

            if(like) {
                ps = sanitizeColumns("SELECT * FROM editora", "editora", fields, operator, "LIKE");
            } else {
                ps = sanitizeColumns("SELECT * FROM editora", "editora", fields, operator, "=");
            }

            for(int i = 0; i < values.size(); i++) {
                if(like) {
                    ps.setString(i+1, "%" + values.get(i) + "%");
                } else {
                    ps.setObject(i+1, values.get(i));
                }
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
    
    public static List<Editora> search(String text) {
        List<String> db_columns = new ArrayList<String>();
        List<String> values = new ArrayList<String>();
        try {
            db_columns = Editora.getColumns("editora");

            // Remove ID columns
            db_columns.remove(0);

            for(int i = 0; i < db_columns.size(); i++) {
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
            if(i==1) this.nome = (String) values.get(i);
            if(i==2) this.cidade = (String) values.get(i);
       }
    }
}
