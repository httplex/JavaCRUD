
package model;

import factory.ConexaoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Date;


public class ClienteDAO {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    String sql;
    
    
    public ArrayList<Cliente> getLista()throws SQLException{
        ArrayList<Cliente> clientes = 
                new ArrayList<>();
        sql = "SELECT idCliente, nome, contato, dataCadastro, status " +
               "FROM cliente";
        con = ConexaoFactory.conectar();
        ps  = con.prepareStatement(sql);
        rs = ps.executeQuery();
        
        while(rs.next()){
            Cliente c  = new Cliente();
            c.setIdCliente(rs.getInt("idCliente"));
            c.setNome(rs.getString("nome"));
            c.setContato(rs.getString("contato"));
            c.setDataCadastro(rs.getDate("dataCadastro"));
            c.setStatus(rs.getInt("status"));
            
            clientes.add(c);
            
        }
        
           ConexaoFactory.close(con);
      
       
        
        
        return clientes;
    }
    
    public boolean gravar(Cliente c)throws SQLException{
        con = ConexaoFactory.conectar();
        
        if(c.getIdCliente() == 0){
            sql = "INSERT INTO cliente (nome, contato, dataCadastro, status) " +
                  "VALUES (?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, c.getNome());
            ps.setString(2, c.getContato());
            ps.setDate(3, new Date(c.getDataCadastro().getTime()));
            ps.setInt(4, c.getStatus());
            
        }else{
            sql = 
               "UPDATE cliente set nome = ?, contato = ?, dataCadastro = ?, status = ? " +
               "WHERE idCliente = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, c.getNome());
            ps.setString(2, c.getContato());
            ps.setDate(3, new Date(c.getDataCadastro().getTime()));
            ps.setInt(4, c.getStatus());
            ps.setInt(5, c.getIdCliente());
                  
            
        }
        
        ps.executeUpdate();
        ConexaoFactory.close(con);
               
        return true;
    }
    
    public Cliente getCarregarPorId(int idCliente)
        throws SQLException{
        Cliente c = new Cliente();
        sql = "SELECT idCliente, nome, contato, dataCadastro, status " +
              "FROM cliente WHERE idCliente = ?";
        
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        ps.setInt(1, idCliente);
        rs = ps.executeQuery();
        if(rs.next()){
            c.setIdCliente(rs.getInt("idCliente"));
            c.setNome(rs.getString("nome"));
            c.setContato(rs.getString("contato"));
            c.setDataCadastro(rs.getDate("dataCadastro"));
            c.setStatus(rs.getInt("status"));
            
        }
        
        ConexaoFactory.close(con);
        
        
        return c;
        
    }
    
    public boolean desativar(Cliente c)throws SQLException{
        sql = "UPDATE cliente set status = 0 " +
              "WHERE idCliente = ?";
        
        con = ConexaoFactory.conectar();
        
        ps = con.prepareStatement(sql);
        ps.setInt(1, c.getIdCliente());
        ps.executeUpdate();
        ConexaoFactory.close(con);
        
        return true;
    }
    
    public boolean ativar(Cliente c)throws SQLException{
        sql = "UPDATE CLIENTE set status = 1 " +
              "WHERE idCliente = ?";
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        ps.setInt(1, c.getIdCliente());
        ps.executeUpdate();
        ConexaoFactory.close(con);
        
        
        return true;
    }
    
 
    
    
    
}
