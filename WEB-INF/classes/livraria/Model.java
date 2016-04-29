package livraria;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public abstract class Model {
    protected static final Database db = new Database();
    
    protected abstract void fill(List values);
    
    /**
    * Atualiza Bean com dados recuperados do banco de dados, apenas o primeiro
    * resultado encontrado será considerado
    * @param field Coluna ou Propriedade do Bean
    * @param value Valor para comparar
    * @return Retorna true se encontrado, false em caso de erros ou nao encontrado
    */
    public <T> boolean loadBy(String field, T value)
    {                   
        try {
            String table = this.getClass().getSimpleName().toLowerCase();
            
            PreparedStatement ps = sanitizeColumns("SELECT * FROM " + table, table, Collections.singletonList(field));
            ps.setObject(1, value);


            ResultSet rs = executeQuery(ps);
            ResultSetMetaData rsmd = rs.getMetaData();

            if(rs.next()) {
                List columns = new ArrayList();           
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    columns.add(rs.getObject(i));
                }
                this.fill(columns);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
        
    public static PreparedStatement sanitizeColumns(String query, String table, List<String> fields)
    throws SQLException
    {       
        if(fields.size() < 1) {
            return db.getConnection().prepareStatement(query);
        }
        
        StringBuilder finalQuery = new StringBuilder();
        finalQuery.append(query);
        
        PreparedStatement ps = db.getConnection().prepareStatement("SHOW COLUMNS FROM " + table);
        
        ResultSet rs = executeQuery(ps);
        List<String> db_columns = new ArrayList<String>();
        
        while(rs.next()) {
            db_columns.add(rs.getString("Field"));
        }
        
        fields.retainAll(db_columns); // Now fields are filtered and checked
        
        for(int i = 0; i< fields.size(); i++) {
            if (i==0) {
                finalQuery.append(" WHERE " + fields.get(i) + " = ?");
            } else {
                finalQuery.append(" AND " + fields.get(i) + " = ?");
            }
        }
        
        return db.getConnection().prepareStatement(finalQuery.toString());
    }
    
    public static ResultSet executeQuery(PreparedStatement ps)
    throws SQLException
    {
        System.out.println("QUERY ENVIADA: " + ps);
        ResultSet rs = ps.executeQuery();
        System.out.println("QUERY EXECUTADA: " + rs.getStatement().toString());
        return rs;
    }
}
