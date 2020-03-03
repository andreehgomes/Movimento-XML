/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OperacaoBanco;

;



import MODEL.ModelUsuarios;
import MODEL.ModelClientes;
import MODEL.ModelOperacaoBanco;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.System.in;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Driver;
import jxl.read.biff.BiffException;

/**
 *
 * @author Aluno
 */


public class OperacaoBancoUsuarios {    
    OperacaoLeituraArquivoBase arquivo = new OperacaoLeituraArquivoBase();
    //String caminho = "127.0.0.1";
    String caminho = arquivo.lerArquivoBase();
    Connection con = null;

    public Statement stm; //Responsavel por preparar e realizar pesquisas.
    public ResultSet rs; //Responsavel por guardar as informações pesquisadas
    public Connection conection = null;

    public OperacaoBancoUsuarios() throws IOException, BiffException {
        this.carregarDriver();        
    }
    public void carregarDriver() {        

        try {
            //carrega o driver
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver Carregado");
        } catch (ClassNotFoundException ex) {
            //se nao carregar
            System.out.println("O driver MYSQL nao " + "pode ser encontrado; erro: " + ex);
        }
    }

    public Connection obterConexao() throws IOException {        
        try {
            //verifica se conexao fechada ou inexsistente
            //abre a conexao
            if ((con == null) || (con.isClosed())) {
                con = DriverManager.getConnection("jdbc:mysql://" + caminho + "/anve?allowMultiQueries=true", "root", "");
                System.out.println("Conexao obtida com sucesso");
            }

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
        }
        return con;//retorna uma conexao onde serao feitas as conexoes
        //operaçoes com o BD
    }

    public void fechaConexao() {
        try {
            if ((con != null) && (!con.isClosed())) {
                con.close();
                System.out.println("Conexao fechada");
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
        }
    }

    public void incluirUsuarios(ModelUsuarios usuarios) throws IOException {
        obterConexao();
        Connection conexao = this.obterConexao();
        PreparedStatement pre = null;
        try {
            String sql = "START TRANSACTION;"
                    + "INSERT INTO usuarios("
                    + "nome, "
                    + "login, "
                    + "senha"
                    + ") values (?,?,?); COMMIT; ";
            pre = conexao.prepareStatement(sql);
            pre.setString(1, usuarios.getNome());
            pre.setString(2, usuarios.getLogin());
            pre.setString(3, usuarios.getSenha());

            pre.executeUpdate();
        } catch (SQLException d) {
            System.out.println("Erro ao incluir: " + d.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao salvar: " + d.getMessage());
        } catch (Exception e) {
            System.out.println("Erro ao incluir: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao salvar: " + e.getMessage());
        }

        fechaConexao();
    }

    public void alterarUsuarios(ModelUsuarios usuarios) throws IOException {
        obterConexao();
        Connection conexao = this.obterConexao();
        PreparedStatement pre = null;
        try {
            String sql = "START TRANSACTION;"
                    + "update usuarios set senha=? where codusu=?; COMMIT;";

            pre = conexao.prepareStatement(sql);
            pre.setString(1, usuarios.getSenha());
            pre.setInt(2, usuarios.getCodusu());

            pre.executeUpdate();
            System.out.println("Alteração Realizada");
        } catch (SQLException d) {
            System.out.println("Erro ao incluir: " + d.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao salvar: " + d.getMessage() + " Cadastro do USUARIO"
                    + " não foi realizado!!!! ");
        }
        fechaConexao();
    }

    /*public void alterarDeputadoSemFoto(ModelDeputadoEstadual deputado) {
        obterConexao();
        Connection conexao = this.obterConexao();
        PreparedStatement pre = null;
        try {
            String sql = "START TRANSACTION;"
                    + "update deputadoestadual set numero=?, nome=?, siglapartido=?, totalvotos=? where codigo=?; COMMIT;";
            System.out.println("teste1");
            
            pre = conexao.prepareStatement(sql);
            pre.setInt(1, deputado.getNumero());
            pre.setString(2, deputado.getNome());
            pre.setString(3, deputado.getSiglapartido());            
            pre.setInt(4, deputado.getTotalvotos());            
            pre.setInt(5, deputado.getCodigo());                     
            System.out.println("teste2");
            
            pre.executeUpdate();
            System.out.println("Inclusao Realizada");
        } catch (SQLException d) {
            System.out.println("Erro ao incluir: " + d.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao salvar: " + d.getMessage());
        }
        fechaConexao();
    }*/
 /*public void excluir(ModelDeputadoEstadual deputado) {
        obterConexao();
        Connection conexao = this.obterConexao();
        PreparedStatement pre = null;
        try {
            String sql = "START TRANSACTION; delete from deputadoestadual where codigo=?; COMMIT;";
             pre = conexao.prepareStatement(sql);
             pre.setInt(1, deputado.getCodigo());
             pre.executeUpdate();
             System.out.println("Item excluído!");
             JOptionPane.showMessageDialog(null, "Item excluido");
        } catch (SQLException e) {
            System.out.println("Erro ao excluir: " + e.getMessage());
        }
    }*/
    public void executaSql(String sql) {
        try {
            stm = con.createStatement(rs.TYPE_SCROLL_INSENSITIVE, rs.CONCUR_READ_ONLY);
            rs = stm.executeQuery(sql);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ExecutaSQL:\n" + ex.getMessage());
        }

    }
}
