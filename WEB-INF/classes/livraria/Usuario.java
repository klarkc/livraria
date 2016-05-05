package livraria;

import java.util.List;

public class Usuario extends Model {
    private int id;
    private String nome;
    private String senha;

    public Usuario(){}

    public Usuario(String nome, String senha)
    {
        this.nome = nome;
        this.senha = senha;
    }

    public Usuario(int id, String nome, String senha)
    {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
    }

    public int getId()
    {
        return this.id;
    }

    public String getNome()
    {
        return this.nome;
    }

    public String getSenha()
    {
        return this.senha;
    }

    @Override
    protected void fill(List values)
    {
        for(int i = 0; i< values.size(); i++) {
            if(i==0) this.id = (int) values.get(i);
            if(i==1) this.nome = (String) values.get(i);
            if(i==2) this.senha = (String) values.get(i);
       }
    }
}
