package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoFactory {
    private static final String URL = "jdbc:mysql://localhost:3306/projetoa1?useTimezone=true&serverTimezone=UTC&useSSL=false";
    private static final String USUARIO = "root";
    private static final String SENHA = "";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    
    public static Connection conectar()throws SQLException{
        Connection conexao = null;
        try {
            Class.forName(DRIVER);
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (ClassNotFoundException e) {
            System.out.println("Falha ao registrar o Driver: " + e.getMessage());
        }
        
        return conexao;
    }
    
    public static void close(Connection conexao)throws SQLException{
        if(conexao != null){
            conexao.close();
        }
    }
}
