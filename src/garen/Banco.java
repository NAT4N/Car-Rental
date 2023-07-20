/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package garen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/* INTEGRANTES DO GRUPO
 * Natan Santos Bastos - 125111373706
 * Pedro Henrique Silva Torres Souza - 125111357179
 * Pedro Henrique Gil Antunes - 125111378102
 */

public class Banco {
    
    
    String DB_URL = "JDBC:MYSQL://LOCALHOST";
    String DB_USER = "root";
    String DB_PASSWD = "";

    
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    
    public void connection()
    {
        try
        {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWD);
            statement = connection.createStatement();
            
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "FALHA NA CONEXÃO COM O BANCO DE DADOS", "ERROR", JOptionPane.WARNING_MESSAGE);
        }
    }
    public void closeConnection()
    {
        try 
        {
            connection.close();
            statement.close();
        } catch (Exception e) {}
    }
    public void executeSQL(String query)
    {
        try 
        {
            statement.execute(query); 
           
        } catch (SQLException e) 
        {
           JOptionPane.showMessageDialog(null, e);
        }
    }
    public boolean login(String email, String senha)
    {
        try
        {
            String SQLlogin = "SELECT idUsuario,email,senha FROM GAREN.USUARIO where email='" + email + "' AND senha='" + senha + "'" ;
            resultSet = statement.executeQuery(SQLlogin);
            resultSet.next();
            LoggedUser.id = Integer.parseInt(resultSet.getString(1));
            int nroLinhas = resultSet.getRow();
            return nroLinhas == 1;

        }catch(SQLException e)
        {
            return false;

        }
    }
    public ArrayList getData(String query)
    {
        try 
        {
           ArrayList<String> dados = new ArrayList<>();        
           resultSet = statement.executeQuery(query); 
           int nrColunas = resultSet.getMetaData().getColumnCount();
           while(resultSet.next()){
               for(int i = 1; i <= nrColunas; i++)
               {
                   dados.add(resultSet.getString(i));
               }
           }
           
           return dados;

                     
        } catch (SQLException e) 
        {
            return null;
        }
    }
    
    public boolean checkCadastro(String query)
    {
        try
        {
            resultSet = statement.executeQuery(query);
            resultSet.next();
            int nroLinhas = resultSet.getRow();
            return nroLinhas == 1;

        }catch(SQLException e)
        {
            return false;

        }
    }
}

