package livraria;

import java.util.List;

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
