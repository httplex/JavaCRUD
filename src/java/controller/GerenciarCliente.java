
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
import model.Cliente;
import model.ClienteDAO;


@WebServlet(name = "GerenciarCliente", urlPatterns = {"/gerenciarCliente"})
public class GerenciarCliente extends HttpServlet {

    
 
 @Override
    protected void doGet(HttpServletRequest request, 
        HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String acao = request.getParameter("acao");
        String idCliente = request.getParameter("idCliente");
        String mensagem = "";
        
        Cliente c = new Cliente();
        ClienteDAO cdao = new ClienteDAO();
        
        try {
            if(acao.equals("listar")){
                ArrayList<Cliente> clientes = new ArrayList<>();
                clientes = cdao.getLista();
                RequestDispatcher dispatcher =
                        getServletContext().
                                getRequestDispatcher("/listarClientes.jsp");
                request.setAttribute("clientes", clientes);
                dispatcher.forward(request, response);
                        
            
            }else if(acao.equals("alterar")){
                c = cdao.getCarregarPorId(
                        Integer.parseInt(idCliente));
                if(c.getIdCliente() > 0){
                    RequestDispatcher dispatcher =
                            getServletContext().
                                getRequestDispatcher("/cadastrarCliente.jsp");
                    request.setAttribute("cliente", c);
                    dispatcher.forward(request, response);
                    
                }else{
                    mensagem = "Cliente não encontrado na base de dados!";
                }
            
            }else if(acao.equals("ativar")){
                c.setIdCliente(Integer.parseInt(idCliente));
                if(cdao.ativar(c)){
                    mensagem = "Cliente ativado com sucesso!";
                }else{
                        mensagem = "Cliente desativado com sucesso!";
                }

            }else if(acao.equals("desativar")){
                c.setIdCliente(Integer.parseInt(idCliente));
                if(cdao.desativar(c)){
                    mensagem = "Cliente desativado com sucesso!";
                }else{
                    mensagem = "Falha ao desativar o Cliente!";
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
                "location.href='gerenciarCliente?acao=listar';" +
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
        String idCliente = request.getParameter("idCliente");
        String nome = request.getParameter("nome");
        String contato = request.getParameter("contato");
        String dataCadastro = request.getParameter("dataCadastro");
        String status = request.getParameter("status");
        String mensagem = "";
        
        
        Cliente c = new Cliente();
        ClienteDAO cdao = new ClienteDAO();
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            
            if(!idCliente.isEmpty()){
                c.setIdCliente(Integer.parseInt(idCliente));
            }
            
            if(nome.isEmpty() || nome.equals("")){
                request.setAttribute("msg", "Informe o nome do Cliente!");
                getServletContext().
                        getRequestDispatcher("/cadastrarCliente.jsp").
                        forward(request, response);
                
               
            }else{
                c.setNome(nome);
            }
            
            if(contato.isEmpty() || contato.equals("")){
                request.setAttribute("msg", "Informe o contato do Cliente!");
                getServletContext().
                        getRequestDispatcher("/cadastrarCliente.jsp").
                        forward(request, response);
                
               
            }else{
                c.setContato(contato);
            }
            
            if(dataCadastro.isEmpty() || dataCadastro.equals("")){
                request.setAttribute("msg", "Informe a data do cadastro!");
                getServletContext().
                        getRequestDispatcher("/cadastrarCliente.jsp").
                        forward(request, response);
                
            }else{
                c.setDataCadastro(df.parse(dataCadastro));
            }
            
            if(status.isEmpty() || status.equals("")){
                request.setAttribute("msg", "Informe o status do Cliente!");
                getServletContext().
                        getRequestDispatcher("/cadastrarCliente.jsp").
                            forward(request, response);
            }else{
                c.setStatus(Integer.parseInt(status));
            }
            
            if(cdao.gravar(c)){
                mensagem = "Cliente salvo na base de dados";
               
            }else{
                mensagem = "falha ao gravar o cliente na base de dados";
            }
        } catch (ParseException pe) {
            mensagem = "Erro: " + pe.getMessage();
        } catch (SQLException e){
            mensagem = "Erro: " + e.getMessage();
        }
        
        out.println(
                "<script type='text/javascript'>" +
                "alert('" + mensagem + "');" +
                "location.href='gerenciarCliente?acao=listar';" +
                "</script>"
        
        );
    }
    
  



}
