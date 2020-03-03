/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OperacaoBanco;

;



import MODEL.ModelMovimento;
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


public class OperacaoBancoMovimento {

    OperacaoLeituraArquivoBase arquivo = new OperacaoLeituraArquivoBase();
    //String caminho = "127.0.0.1";
    String caminho = arquivo.lerArquivoBase();

    Connection con = null;

    public Statement stm; //Responsavel por preparar e realizar pesquisas.
    public ResultSet rs; //Responsavel por guardar as informações pesquisadas
    public Connection conection = null;

    public OperacaoBancoMovimento() throws IOException, BiffException {
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

    public Connection obterConexao() {
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

    public void incluirMovimento(ModelMovimento movimento) throws SQLException {
        obterConexao();
        Connection conexao = this.obterConexao();
        PreparedStatement pre = null;

        try {
            executaSql("SELECT * FROM produtos where (codigofabrica = " + movimento.getModelprodutos().getCodigofabrica()
                    + " or codigofabrica2 = " + movimento.getModelprodutos().getCodigofabrica2()
                    + " or codigofabrica3 = " + movimento.getModelprodutos().getCodigofabrica3()
                    + " or codigofabrica4 = " + movimento.getModelprodutos().getCodigofabrica4()
                    + " or codigofabrica5 = " + movimento.getModelprodutos().getCodigofabrica5() + ");");
            rs.first();
            try {
                int codigo = rs.getInt("cpcodpro");
            } catch (Exception e) {
                System.out.println("*OperacaoBancoMovimento* Erro consulta: " + e.getMessage());
            }

            String sql = "START TRANSACTION;"
                    + "INSERT INTO movimento("
                    + "cecodigoxml, "
                    + "cecodcli, "
                    + "cecodpro, "
                    + "qtduntotal, "
                    + "valorun, "
                    + "valortotal, "
                    + "cfop, "
                    + "tipomovimento, "
                    + "estoquenoregistro, "
                    + "unproduto,"
                    + "dtmovimento"
                    + ") values (?,?,?,?,?,?,?,?,?,?,?); COMMIT; ";
            pre = conexao.prepareStatement(sql);
            pre.setInt(1, movimento.getModelxml().getNumeronfxml());
            pre.setInt(2, movimento.getModelclientes().getCpcodcli());
            pre.setInt(3, movimento.getModelprodutos().getCodigofabrica());
            pre.setInt(4, movimento.getQtduntotal());
            pre.setFloat(5, movimento.getValorun());
            pre.setFloat(6, movimento.getValortotal());
            pre.setInt(7, movimento.getCfop());
            pre.setString(8, movimento.getTipomovimento());
            pre.setInt(9, movimento.getEstoquenoregistro());
            pre.setString(10, movimento.getUnproduto());
            pre.setDate(11, (Date) movimento.getDtemissao());

            pre.executeUpdate();
        } catch (SQLException d) {
            System.out.println("*OperacaoBancoMovimento* Erro ao incluir1: " + d.getMessage());
            JOptionPane.showMessageDialog(null, "*OperacaoBancoMovimento* Erro ao salvar 1: " + d.getMessage());
        } catch (IndexOutOfBoundsException e) {
            System.out.println("*OperacaoBancoMovimento* Erro ao incluir1: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "*OperacaoBancoMovimento* Erro ao salvar 2: " + e.getMessage());
        } catch (Exception f) {
            System.out.println("*OperacaoBancoMovimento* Erro ao incluir1: " + f.getMessage());
            JOptionPane.showMessageDialog(null, "*OperacaoBancoMovimento* Erro ao salvar 3: " + f.getMessage());
        }

        fechaConexao();
    }

    /*public void alterarMovimento(ModelMovimento movimento) {
        obterConexao();
        Connection conexao = this.obterConexao();
        PreparedStatement pre = null;
        try {
            String sql = "START TRANSACTION;"
                    + "update movimento set naturezaop=?, tipomovimento=? where cpmovimento=?; COMMIT;";
            
            pre = conexao.prepareStatement(sql);            
            pre.setString(1, movimento.getNaturezadaop());
            pre.setString(2, movimento.getTipomovimento());
            pre.setInt(3, movimento.getCpmovimento());

            pre.executeUpdate();
            System.out.println("Inclusao Realizada");
        } catch (SQLException d) {
            System.out.println("Erro ao incluir: " + d.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao salvar: " + d.getMessage());
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
            System.out.println("ERRO: " + ex.getMessage());
        }

    }
}
