
package model;

import factory.ConexaoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Date;


public class PerfilDAO {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    String sql;
    
    
    public ArrayList<Perfil> getLista()throws SQLException{
        ArrayList<Perfil> perfis = 
                new ArrayList<>();
        sql = "SELECT idPerfil, nome, dataCadastro, status " +
               "FROM perfil";
        con = ConexaoFactory.conectar();
        ps  = con.prepareStatement(sql);
        rs = ps.executeQuery();
        
        while(rs.next()){
            Perfil p  = new Perfil();
            p.setIdPerfil(rs.getInt("idPerfil"));
            p.setNome(rs.getString("nome"));
            p.setDataCadastro(rs.getDate("dataCadastro"));
            p.setStatus(rs.getInt("status"));
            
            perfis.add(p);
            
        }
        
           ConexaoFactory.close(con);
      
       
        
        
        return perfis;
    }
    
    public boolean gravar(Perfil p)throws SQLException{
        con = ConexaoFactory.conectar();
        
        if(p.getIdPerfil() == 0){
            sql = "INSERT INTO perfil (nome, dataCadastro, status) " +
                  "VALUES (?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getNome());
            ps.setDate(2, new Date(p.getDataCadastro().getTime()));
            ps.setInt(3, p.getStatus());
            
        }else{
            sql = 
               "UPDATE perfil set nome = ?, dataCadastro = ?, status = ? " +
               "WHERE idPerfil = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getNome());
            ps.setDate(2, new Date(p.getDataCadastro().getTime()));
            ps.setInt(3, p.getStatus());
            ps.setInt(4, p.getIdPerfil());
                  
            
        }
        
        ps.executeUpdate();
        ConexaoFactory.close(con);
               
        return true;
    }
    
    public Perfil getCarregarPorId(int idPerfil)
        throws SQLException{
        Perfil p = new Perfil();
        sql = "SELECT idPerfil, nome, dataCadastro, status " +
              "FROM perfil WHERE idPerfil = ?";
        
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        ps.setInt(1, idPerfil);
        rs = ps.executeQuery();
        if(rs.next()){
            p.setIdPerfil(rs.getInt("idPerfil"));
            p.setNome(rs.getString("nome"));
            p.setDataCadastro(rs.getDate("dataCadastro"));
            p.setStatus(rs.getInt("status"));
            
        }
        
        ConexaoFactory.close(con);
        
        
        return p;
        
    }
    
    public boolean desativar(Perfil p)throws SQLException{
        sql = "UPDATE perfil set status = 0 " +
              "WHERE idPerfil = ?";
        
        con = ConexaoFactory.conectar();
        
        ps = con.prepareStatement(sql);
        ps.setInt(1, p.getIdPerfil());
        ps.executeUpdate();
        ConexaoFactory.close(con);
        
        return true;
    }
    
    public boolean ativar(Perfil p)throws SQLException{
        sql = "UPDATE PERFIL set status = 1 " +
              "WHERE idPerfil = ?";
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        ps.setInt(1, p.getIdPerfil());
        ps.executeUpdate();
        ConexaoFactory.close(con);
        
        
        return true;
    }
    
 
    
    
    
}
