package livraria;

import java.sql.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.lang.reflect.Method;

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

    /**
    * Salva um Bean no banco de dados
    * @param isNew se definido como true executa um INSERT no banco de dados, ao invés de um UPDATE
    * @return true se salvo com sucesso
    */
    public boolean save(boolean isNew)
    {
        try {
            Class<?> c = this.getClass();
            String table = c.getSimpleName().toLowerCase();
            StringBuilder query = new StringBuilder();

            if(isNew) {
                query.append("INSERT INTO " + table + " VALUES (");

                // Generate ID Column from Bean
                Method setId = c.getMethod("setId", int.class);
                setId.invoke(this, generateId(table));
            } else {
                query.append("UPDATE " + table + " SET");
            }

            Iterator<String> db_columns = getColumns(table).iterator();
            List values = new ArrayList();

            if(!isNew) db_columns.next(); // Skip id column only in UPDATE
            int index = 0;
            while(db_columns.hasNext()) {
                String col = db_columns.next();
                Method m = c.getMethod(getMethodOfColumn(col));
                Object mValue = m.invoke(this);
                if(mValue != null) {
                    if(index++ > 0) query.append(",");
                    query.append(" ");
                    if(!isNew) {
                        query.append(col);
                        query.append("=");
                    }
                    query.append("?");
                    values.add(mValue);
                }
            }

            if(isNew) {
                query.append(")");
            } else {
                query.append(" WHERE id=?");
            }

            PreparedStatement ps = db.getConnection().prepareStatement(query.toString());

            if(!isNew) {
                // Set ID Column from Bean
                Method m = c.getMethod("getId");
                ps.setInt(values.size() +1, (int) m.invoke(this)); // Last ?, after WHERE statement
            }

            // Set column values
            for(int i = 0; i < values.size(); i++) {
                //System.out.println("SETTING: " + (i + 1) + " to " + values.get(i));
                ps.setObject(i+1, values.get(i));
            }

            if(executeUpdate(ps) > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
    * Salva um Bean no banco de dados
    * @return true se salvo com sucesso
    */
    public boolean save() {
        return this.save(false);
    }

    /**
    * Salva um novo Bean no banco de dados
    * @return true se salvo com sucesso
    */
    public boolean saveNew()
    {
        return this.save(true);
    }

    public static PreparedStatement sanitizeColumns(String query, String table, List<String> fields)
    throws SQLException
    {
        return sanitizeColumns(query, table, fields, "AND", "=");
    }

    public static PreparedStatement sanitizeColumns(String query, String table, List<String> fields, String multipleOp, String operator)
    throws SQLException
    {
        if(
            !multipleOp.equals("AND")
            && !multipleOp.equals("&&")
            && !multipleOp.equals("OR")
            && !multipleOp.equals("||")
            && !multipleOp.equals("XOR")
            && !operator.equals("=")
            && !operator.equals("LIKE")
        ) throw new SQLException();

        if(fields.size() < 1) {
            return db.getConnection().prepareStatement(query);
        }
        
        StringBuilder finalQuery = new StringBuilder();
        finalQuery.append(query);
        
        PreparedStatement ps = db.getConnection().prepareStatement("SHOW COLUMNS FROM " + table);
        
        ResultSet rs = executeQuery(ps);
        List<String> db_columns = getColumns(table);
        
        fields.retainAll(db_columns); // Now fields are filtered and checked
        
        for(int i = 0; i < fields.size(); i++) {
            // Terminar aqui
            if(i==0) {
                finalQuery.append(" WHERE ");
            } else {
                finalQuery.append(" ");
                finalQuery.append(multipleOp);
                finalQuery.append(" ");
            }

            if (fields.get(i).contains("id")){
                finalQuery.append(fields.get(i));
                finalQuery.append(" = ?");
            } else {
                finalQuery.append(fields.get(i));
                if(operator.equals("LIKE")) {
                    finalQuery.append(" LIKE ? ");
                } else {
                    finalQuery.append(" = ?");
                }
            }
        }

        //System.out.println(finalQuery.toString());
        return db.getConnection().prepareStatement(finalQuery.toString());
    }
    
    public static ResultSet executeQuery(PreparedStatement ps)
    throws SQLException
    {
        //System.out.println("QUERY ENVIADA: " + ps);
        ResultSet rs = ps.executeQuery();
        System.out.println("QUERY EXECUTADA: " + rs.getStatement().toString());
        return rs;
    }

    public static int executeUpdate(PreparedStatement ps)
    throws SQLException
    {
        System.out.println("QUERY ENVIADA: " + ps);
        int lines = ps.executeUpdate();
        System.out.println("LINHAS AFETADAS: " + lines);
        return lines;
    }

    public static List<String> getColumns(String table)
    throws SQLException
    {
        PreparedStatement ps = db.getConnection().prepareStatement("SHOW COLUMNS FROM " + table);

        ResultSet rs = executeQuery(ps);
        List<String> db_columns = new ArrayList<String>();

        while(rs.next()) {
            db_columns.add(rs.getString("Field"));
        }

        return db_columns;
    }

    public static int generateId(String table)
    throws SQLException
    {
        PreparedStatement ps = db.getConnection().prepareStatement("SELECT MAX(id) as novoid FROM " + table);
        ResultSet rs = executeQuery(ps);
        rs.next();
        String novoId = rs.getString("novoid");
        int id;
        if (novoId == null) {
            id = 1;
        } else {
            id = Integer.parseInt(novoId) + 1;
        }
        //System.out.println("GENERATED ID: " + id);
        return id;
    }

    public static String getMethodOfColumn(String column)
    {
        String field = new String(column);
        Pattern p = Pattern.compile("_(.)");
        Matcher m = p.matcher("get_" + column);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, m.group(1).toUpperCase());
        }
        m.appendTail(sb);
        return sb.toString();
    }
}
