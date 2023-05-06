
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Perfil;
import model.PerfilDAO;


@WebServlet(name = "GerenciarPerfil", urlPatterns = {"/gerenciarPerfil"})
public class GerenciarPerfil extends HttpServlet {

    
 
 @Override
    protected void doGet(HttpServletRequest request, 
        HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String acao = request.getParameter("acao");
        String idPerfil = request.getParameter("idPerfil");
        String mensagem = "";
        
        Perfil p = new Perfil();
        PerfilDAO pdao = new PerfilDAO();
        
        try {
            if(acao.equals("listar")){
                ArrayList<Perfil> perfis = new ArrayList<>();
                perfis = pdao.getLista();
                RequestDispatcher dispatcher =
                        getServletContext().
                                getRequestDispatcher("/listarPerfis.jsp");
                request.setAttribute("perfis", perfis);
                dispatcher.forward(request, response);
                        
            
            }else if(acao.equals("alterar")){
                p = pdao.getCarregarPorId(
                        Integer.parseInt(idPerfil));
                if(p.getIdPerfil() > 0){
                    RequestDispatcher dispatcher =
                            getServletContext().
                                getRequestDispatcher("/cadastrarPerfil.jsp");
                    request.setAttribute("perfil", p);
                    dispatcher.forward(request, response);
                    
                }else{
                    mensagem = "Perfil não encontrado na base de dados!";
                }
            
            }else if(acao.equals("ativar")){
                p.setIdPerfil(Integer.parseInt(idPerfil));
                if(pdao.ativar(p)){
                    mensagem = "Perfil ativado com sucesso!";
                }else{
                    mensagem = "Perfil desativado com sucesso!";
                }

            }else if(acao.equals("desativar")){
                p.setIdPerfil(Integer.parseInt(idPerfil));
                if(pdao.desativar(p)){
                    mensagem = "Perfil desativado com sucesso!";
                }else{
                    mensagem = "Falha ao desativar o Perfil!";
                }
            }else{
                response.sendRedirect("/index.jsp");
            }
            
        } catch (SQLException e) {
            mensagem = "Erro: " + e.getMessage();
            e.printStackTrace();
        }
        
        out.println(
                "<script type='text/javascript'>" +
                "alert('" + mensagem + "');" +
                "location.href='gerenciarPerfil?acao=listar';" +
                "</script>"
        );
        
      
        
    }
  
    @Override
    protected void doPost(HttpServletRequest request, 
        HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String idPerfil = request.getParameter("idPerfil");
        String nome = request.getParameter("nome");
        String dataCadastro = request.getParameter("dataCadastro");
        String status = request.getParameter("status");
        String mensagem = "";
        
        
        Perfil p = new Perfil();
        PerfilDAO pdao = new PerfilDAO();
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            
            if(!idPerfil.isEmpty()){
                p.setIdPerfil(Integer.parseInt(idPerfil));
            }
            
            if(nome.isEmpty() || nome.equals("")){
                request.setAttribute("msg", "Informe o nome do Perfil!");
                getServletContext().
                        getRequestDispatcher("/cadastrarPerfil.jsp").
                        forward(request, response);
                
               
            }else{
                p.setNome(nome);
            }
            
            if(dataCadastro.isEmpty() || dataCadastro.equals("")){
                request.setAttribute("msg", "Informe a data do cadastro!");
                getServletContext().
                        getRequestDispatcher("/cadastrarPerfil.jsp").
                        forward(request, response);
                
            }else{
                p.setDataCadastro(df.parse(dataCadastro));
            }
            
            if(status.isEmpty() || status.equals("")){
                request.setAttribute("msg", "Informe o status do Perfil!");
                getServletContext().
                        getRequestDispatcher("/cadastrarPerfil.jsp").
                            forward(request, response);
            }else{
                p.setStatus(Integer.parseInt(status));
            }
            
            if(pdao.gravar(p)){
                mensagem = "Perfil salvo na base de dados";
               
            }else{
                mensagem = "falha ao gravar o perfil na base de dados";
            }
        } catch (ParseException pe) {
            mensagem = "Erro: " + pe.getMessage();
        } catch (SQLException e){
            mensagem = "Erro: " + e.getMessage();
        }
        
        out.println(
                "<script type='text/javascript'>" +
                "alert('" + mensagem + "');" +
                "location.href='gerenciarPerfil?acao=listar';" +
                "</script>"
        
        );
    }
    
  



}
