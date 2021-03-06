/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VIEW;

import AUXILIAR.AuxiliarLog;
import MODEL.ModelTabela;
import OperacaoBanco.OperacaoBancoCfop;
import OperacaoBanco.OperacaoBancoMovimento;
import OperacaoBanco.OperacaoBancoProdutos;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import jxl.read.biff.BiffException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author André Felipe
 */
public class TelaParamProdutosMarcaCategoria extends javax.swing.JFrame {

    JLabel transferedados;

    public TelaParamProdutosMarcaCategoria() throws IOException, BiffException {
        initComponents();
        preencherTabelaMarca("SELECT marca FROM produtos GROUP BY marca");
    }

    public TelaParamProdutosMarcaCategoria(JLabel transferedados) throws IOException, BiffException {
        initComponents();
        this.transferedados = transferedados;
        preencherTabelaMarca("SELECT marca FROM produtos GROUP BY marca");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jtfPesquisa = new javax.swing.JFormattedTextField();
        jbFiltrar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtMarca = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtCategoria = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Filtro");
        setResizable(false);
        getContentPane().setLayout(null);

        jtfPesquisa.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));
        jtfPesquisa.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfPesquisaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfPesquisaKeyReleased(evt);
            }
        });
        getContentPane().add(jtfPesquisa);
        jtfPesquisa.setBounds(20, 70, 220, 20);

        jbFiltrar.setText("Filtrar");
        jbFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbFiltrarActionPerformed(evt);
            }
        });
        getContentPane().add(jbFiltrar);
        jbFiltrar.setBounds(380, 60, 100, 32);

        jScrollPane2.setBackground(new java.awt.Color(204, 204, 204));
        jScrollPane2.setOpaque(false);

        jtMarca.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jtMarca.setGridColor(new java.awt.Color(204, 204, 204));
        jtMarca.setInheritsPopupMenu(true);
        jtMarca.setOpaque(false);
        jtMarca.setRowHeight(20);
        jtMarca.setSelectionBackground(new java.awt.Color(255, 102, 0));
        jtMarca.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtMarcaMouseClicked(evt);
            }
        });
        jtMarca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtMarcaKeyTyped(evt);
            }
        });
        jScrollPane2.setViewportView(jtMarca);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(20, 100, 220, 180);

        jLabel3.setFont(new java.awt.Font("Franklin Gothic Medium", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 0, 0));
        jLabel3.setText("Relatório de Produtos por Marca e Categoria");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(20, 0, 360, 30);

        jScrollPane3.setBackground(new java.awt.Color(204, 204, 204));
        jScrollPane3.setOpaque(false);

        jtCategoria.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jtCategoria.setGridColor(new java.awt.Color(204, 204, 204));
        jtCategoria.setInheritsPopupMenu(true);
        jtCategoria.setOpaque(false);
        jtCategoria.setRowHeight(20);
        jtCategoria.setSelectionBackground(new java.awt.Color(255, 102, 0));
        jtCategoria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtCategoriaMouseClicked(evt);
            }
        });
        jtCategoria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtCategoriaKeyTyped(evt);
            }
        });
        jScrollPane3.setViewportView(jtCategoria);

        getContentPane().add(jScrollPane3);
        jScrollPane3.setBounds(260, 100, 220, 180);

        jLabel5.setText("Digite para filtrar a marca");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(20, 50, 150, 16);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENS/fundo padrão II.png"))); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 570, 340);

        setSize(new java.awt.Dimension(513, 338));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbFiltrarActionPerformed

        HashMap parametro = new HashMap<>();

        OperacaoBanco.OperacaoBancoProdutos ob = null;
        try {
            ob = new OperacaoBancoProdutos();
            parametro.put("marcaProduto", jtMarca.getValueAt(jtMarca.getSelectedRow(), 0).toString());
            parametro.put("categoriaProduto", jtCategoria.getValueAt(jtCategoria.getSelectedRow(), 0).toString());

        } catch (IOException ex) {
            Logger.getLogger(TelaMovimento.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(TelaMovimento.class.getName()).log(Level.SEVERE, null, ex);
        }
        ob.obterConexao();

        String src = "Jasper/relatorioListaProdutosMarcaCategoria.jasper";
        JasperPrint jasperPrint = null;
        try {
            jasperPrint = JasperFillManager.fillReport(src, parametro, ob.obterConexao());
        } catch (JRException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }

        JasperViewer view = new JasperViewer(jasperPrint, false);
        view.setVisible(true);
        ob.fechaConexao();

        AUXILIAR.AuxiliarLog log = new AuxiliarLog();
        try {
            log.log(transferedados.getText(), "PRODUTOS", "RELATÓRIO", Long.parseLong("324"));
        } catch (IOException ex) {
            Logger.getLogger(TelaClientes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(TelaClientes.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jbFiltrarActionPerformed

    private void jtMarcaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtMarcaMouseClicked
        try {
            preencherTabelaCategoria(jtMarca.getValueAt(jtMarca.getSelectedRow(), 0).toString());
        } catch (IOException ex) {
            Logger.getLogger(TelaParamProdutosMarcaCategoria.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(TelaParamProdutosMarcaCategoria.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jtMarcaMouseClicked

    public void preencherTabelaMarca(String sql) throws IOException, BiffException {
        OperacaoBancoProdutos obc = new OperacaoBancoProdutos();
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"MARCA"};
        obc.obterConexao();
        obc.executaSql(sql);

        try {
            obc.rs.first();

            do {
                dados.add(new Object[]{
                    obc.rs.getString("marca")

                });
            } while (obc.rs.next());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Nenhum cadastrado no momento");
        }

        ModelTabela modelo = new ModelTabela(dados, colunas);
        DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        DefaultTableCellRenderer direita = new DefaultTableCellRenderer();
        direita.setHorizontalAlignment(SwingConstants.RIGHT);
        esquerda.setHorizontalAlignment(SwingConstants.LEFT);
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);

        jtMarca.setModel((TableModel) modelo);
        jtMarca.getColumnModel().getColumn(0).setPreferredWidth(200);
        jtMarca.getColumnModel().getColumn(0).setCellRenderer(esquerda);
        jtMarca.getColumnModel().getColumn(0).setResizable(true);

        jtMarca.getTableHeader().setReorderingAllowed(true);
        jtMarca.setAutoResizeMode(jtMarca.AUTO_RESIZE_OFF);
        jtMarca.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        obc.fechaConexao();
    }

    public void preencherTabelaPesquisaMarca(String pesquisa) throws IOException, BiffException {
        OperacaoBancoProdutos obc = new OperacaoBancoProdutos();
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"MARCA"};
        obc.obterConexao();
        obc.executaSql("SELECT marca FROM produtos WHERE marca like '%" + pesquisa + "%' GROUP BY marca");

        try {
            obc.rs.first();

            do {
                dados.add(new Object[]{
                    obc.rs.getString("marca")

                });
            } while (obc.rs.next());
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(rootPane, "Nenhum cadastrado no momento");
        }

        ModelTabela modelo = new ModelTabela(dados, colunas);
        DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        DefaultTableCellRenderer direita = new DefaultTableCellRenderer();
        direita.setHorizontalAlignment(SwingConstants.RIGHT);
        esquerda.setHorizontalAlignment(SwingConstants.LEFT);
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);

        jtMarca.setModel((TableModel) modelo);
        jtMarca.getColumnModel().getColumn(0).setPreferredWidth(200);
        jtMarca.getColumnModel().getColumn(0).setCellRenderer(esquerda);
        jtMarca.getColumnModel().getColumn(0).setResizable(true);

        jtMarca.getTableHeader().setReorderingAllowed(true);
        jtMarca.setAutoResizeMode(jtMarca.AUTO_RESIZE_OFF);
        jtMarca.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        obc.fechaConexao();
    }

    public void preencherTabelaCategoria(String pesquisa) throws IOException, BiffException {
        OperacaoBancoProdutos obc = new OperacaoBancoProdutos();
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"CATEGORIA"};
        obc.obterConexao();
        obc.executaSql("SELECT categoria FROM produtos WHERE marca like '%" + pesquisa + "%' GROUP BY categoria");

        try {
            obc.rs.first();

            do {
                dados.add(new Object[]{
                    obc.rs.getString("categoria")

                });
            } while (obc.rs.next());
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(rootPane, "Nenhum cadastrado no momento");
        }

        ModelTabela modelo = new ModelTabela(dados, colunas);
        DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        DefaultTableCellRenderer direita = new DefaultTableCellRenderer();
        direita.setHorizontalAlignment(SwingConstants.RIGHT);
        esquerda.setHorizontalAlignment(SwingConstants.LEFT);
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);

        jtCategoria.setModel((TableModel) modelo);
        jtCategoria.getColumnModel().getColumn(0).setPreferredWidth(200);
        jtCategoria.getColumnModel().getColumn(0).setCellRenderer(esquerda);
        jtCategoria.getColumnModel().getColumn(0).setResizable(true);

        jtCategoria.getTableHeader().setReorderingAllowed(true);
        jtCategoria.setAutoResizeMode(jtCategoria.AUTO_RESIZE_OFF);
        jtCategoria.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        obc.fechaConexao();
    }


    private void jtMarcaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtMarcaKeyTyped

    }//GEN-LAST:event_jtMarcaKeyTyped

    private void jtfPesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfPesquisaKeyReleased

    }//GEN-LAST:event_jtfPesquisaKeyReleased

    private void jtfPesquisaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfPesquisaKeyPressed
        try {
            preencherTabelaPesquisaMarca(jtfPesquisa.getText().toUpperCase());
        } catch (IOException ex) {
            Logger.getLogger(TelaParamProdutosMarcaCategoria.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(TelaParamProdutosMarcaCategoria.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jtfPesquisaKeyPressed

    private void jtCategoriaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtCategoriaMouseClicked

    }//GEN-LAST:event_jtCategoriaMouseClicked

    private void jtCategoriaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtCategoriaKeyTyped

    }//GEN-LAST:event_jtCategoriaKeyTyped

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaParamProdutosMarcaCategoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaParamProdutosMarcaCategoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaParamProdutosMarcaCategoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaParamProdutosMarcaCategoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new TelaParamProdutosMarcaCategoria().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(TelaParamProdutosMarcaCategoria.class.getName()).log(Level.SEVERE, null, ex);
                } catch (BiffException ex) {
                    Logger.getLogger(TelaParamProdutosMarcaCategoria.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton jbFiltrar;
    private javax.swing.JTable jtCategoria;
    private javax.swing.JTable jtMarca;
    private javax.swing.JFormattedTextField jtfPesquisa;
    // End of variables declaration//GEN-END:variables
}
