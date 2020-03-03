/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OperacaoBanco;

;



import MODEL.ModelProdutos;
import java.io.IOException;
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
public class OperacaoBancoProdutos {
    OperacaoLeituraArquivoBase arquivo = new OperacaoLeituraArquivoBase();
    //String caminho = "127.0.0.1";
    String caminho = arquivo.lerArquivoBase();
    
    Connection con=null;
    
    public Statement stm; //Responsavel por preparar e realizar pesquisas.
    public ResultSet rs; //Responsavel por guardar as informações pesquisadas
    public Connection conection=null;
    
    public OperacaoBancoProdutos()throws IOException, BiffException {
    this.carregarDriver();
}
    
    public void carregarDriver(){
        try{
            //carrega o driver
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver Carregado");
        }catch (ClassNotFoundException ex){
            //se nao carregar
            System.out.println("O driver MYSQL nao "+"pode ser encontrado; erro: "+ ex);
        }
    }
    public Connection obterConexao(){
        try{
            //verifica se conexao fechada ou inexsistente
            //abre a conexao
            if((con==null)||(con.isClosed())){
                con = DriverManager.getConnection("jdbc:mysql://"+caminho+"/anve?allowMultiQueries=true", "root", "");
                System.out.println("Conexao obtida com sucesso");
            }
            
        }catch (SQLException ex){
            System.out.println("SQLException: "+ ex);
        }catch (Exception ex){
            System.out.println("Exception: "+ ex);
        }
        return con;//retorna uma conexao onde serao feitas as conexoes
        //operaçoes com o BD
    }
    
    public void fechaConexao(){
        try{
            if((con!=null) && (!con.isClosed())){
                con.close();
                System.out.println("Conexao fechada");
            }
        } catch(SQLException ex){
            System.out.println("SQLException: "+ ex);
        }catch(Exception ex){
            System.out.println("Exception: "+ ex);
        }
    }
   
    public void incluirProdutos(ModelProdutos produtos){
        obterConexao();
        Connection conexao = this.obterConexao();
        PreparedStatement pre = null;
        try{
            String sql = "START TRANSACTION;"
                    +"INSERT INTO produtos("
                    + "cpcodpro, "
                    + "descricao, "
                    + "categoria, "
                    + "marca, "
                    + "unidade,  "
                    + "qtdun, "
                    + "codigofabrica, "
                    + "codigofabrica2, "
                    + "codigofabrica3, "
                    + "codigofabrica4,"
                    + "codigofabrica5) values (?,?,?,?,?,?,?,?,?,?,?); COMMIT; ";
            pre = conexao.prepareStatement(sql);
            pre.setLong(1, produtos.getCpcodpro());
            pre.setString(2, produtos.getDescricao());
            pre.setString(3, produtos.getCategoria());
            pre.setString(4, produtos.getMarca());
            pre.setString(5, produtos.getUnidade());
            pre.setInt(6, produtos.getQtdun());
            pre.setInt(7, produtos.getCodigofabrica());
            pre.setInt(8, produtos.getCodigofabrica2());
            pre.setInt(9, produtos.getCodigofabrica3());
            pre.setInt(10, produtos.getCodigofabrica4());
            pre.setInt(11, produtos.getCodigofabrica5());
            
            
            pre.executeUpdate();            
        }catch (SQLException d){
            alterarProdutos(produtos);
        }catch (Exception e){
            System.out.println("Erro ao incluir: "+e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao salvar: " + e.getMessage());
        }
        
        fechaConexao();
    }
    
    public void alterarProdutos(ModelProdutos produtos) {
        obterConexao();
        Connection conexao = this.obterConexao();
        PreparedStatement pre = null;
        try {
            String sql = "START TRANSACTION;"
                    + "update produtos set descricao=?, "
                    + "categoria=?, "
                    + "marca=?, "
                    + "unidade=?, "
                    + "qtdun=?, "
                    + "codigofabrica=?, "
                    + "codigofabrica2=?, "
                    + "codigofabrica3=?, "
                    + "codigofabrica4=?, "
                    + "codigofabrica5=? "
                    + "where cpcodpro=?;COMMIT;";
            
            pre = conexao.prepareStatement(sql);
            pre.setString(1, produtos.getDescricao());
            pre.setString(2, produtos.getCategoria());
            pre.setString(3, produtos.getMarca());
            pre.setString(4, produtos.getUnidade());
            pre.setInt(5, produtos.getQtdun());
            pre.setInt(6, produtos.getCodigofabrica());
            pre.setInt(7, produtos.getCodigofabrica2());
            pre.setInt(8, produtos.getCodigofabrica3());
            pre.setInt(9, produtos.getCodigofabrica4());
            pre.setInt(10, produtos.getCodigofabrica5());
            pre.setLong(11, produtos.getCpcodpro());
            

            pre.executeUpdate();
            System.out.println("Inclusao Realizada");
        } catch (SQLException d) {
            System.out.println("Erro ao incluir: " + d.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao salvar: " + d.getMessage());
        }
        fechaConexao();
    }
    
    public void alterarEstoqueProdutos(ModelProdutos produtos) {
        obterConexao();
        Connection conexao = this.obterConexao();
        PreparedStatement pre = null;
        try {
            String sql = "START TRANSACTION;"
                    + "UPDATE produtos set estoque=? where (codigofabrica=? or codigofabrica2=? or "
                    + "codigofabrica3=? or codigofabrica4=? or codigofabrica5=?);COMMIT;";            
            
            pre = conexao.prepareStatement(sql);
            pre.setInt(1, produtos.getEstoque());                       
            pre.setInt(2, produtos.getCodigofabrica());
            pre.setInt(3, produtos.getCodigofabrica());
            pre.setInt(4, produtos.getCodigofabrica());
            pre.setInt(5, produtos.getCodigofabrica());
            pre.setInt(6, produtos.getCodigofabrica());
            
            pre.executeUpdate();
            System.out.println("Estoque Atualizado");
        } catch (SQLException d) {
            System.out.println("*OperacaoBancoProduto* Erro ao incluir: " + d.getMessage());
            JOptionPane.showMessageDialog(null, "*OperacaoBancoProduto* Erro ao salvar: " + d.getMessage());
        }
        fechaConexao();
    }
    
    
    /*public void excluir(ModelDeputadoEstadual produtos) {
        obterConexao();
        Connection conexao = this.obterConexao();
        PreparedStatement pre = null;
        try {
            String sql = "START TRANSACTION; delete from produtosestadual where codigo=?; COMMIT;";
             pre = conexao.prepareStatement(sql);
             pre.setInt(1, produtos.getCodigo());
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
