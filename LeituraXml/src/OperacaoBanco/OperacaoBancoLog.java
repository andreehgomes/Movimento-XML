/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OperacaoBanco;

;



import MODEL.ModelCfop;
import MODEL.ModelClientes;
import MODEL.ModelLog;
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
import java.time.Instant;
import jxl.read.biff.BiffException;

/**
 *
 * @author Aluno
 */


public class OperacaoBancoLog {    
    OperacaoLeituraArquivoBase arquivo = new OperacaoLeituraArquivoBase();
    //String caminho = "127.0.0.1";
    String caminho = arquivo.lerArquivoBase();
    Connection con = null;

    public Statement stm; //Responsavel por preparar e realizar pesquisas.
    public ResultSet rs; //Responsavel por guardar as informações pesquisadas
    public Connection conection = null;

    public OperacaoBancoLog() throws IOException, BiffException {
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

    public void incluirLog(ModelLog log) throws IOException {
        obterConexao();
        Connection conexao = this.obterConexao();
        PreparedStatement pre = null;
        try {
            String sql = "START TRANSACTION;"
                    + "INSERT INTO log("
                    + "usuario, "
                    + "modulo, "
                    + "acao, "
                    + "codigoobjeto, "
                    + "dtlog"
                    + ") values (?,?,?,?,?); COMMIT; ";
            pre = conexao.prepareStatement(sql);
            pre.setString(1, log.getUsuario());
            pre.setString(2, log.getModulo());
            pre.setString(3, log.getAcao());
            pre.setLong(4, log.getCodigoobjeto());
            pre.setTimestamp(5, log.getDtlog());
            

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

    /*public void alterarCfop(ModelCfop cfop) throws IOException {
        obterConexao();
        Connection conexao = this.obterConexao();
        PreparedStatement pre = null;
        try {
            String sql = "START TRANSACTION;"
                    + "update cfop set naturezaop=?, tipomovimento=? where cpcfop=?; COMMIT;";

            pre = conexao.prepareStatement(sql);
            pre.setString(1, cfop.getNaturezadaop());
            pre.setString(2, cfop.getTipomovimento());
            pre.setInt(3, cfop.getCpcfop());

            pre.executeUpdate();
            System.out.println("Inclusao Realizada");
        } catch (SQLException d) {
            System.out.println("Erro ao incluir: " + d.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao salvar: " + d.getMessage());
        }
        fechaConexao();
    }*/

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
