/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VIEW;

import AUXILIAR.AuxiliarLog;
import MODEL.ModelLog;
import MODEL.ModelTabela;
import MODEL.ModelUsuarios;
import OperacaoBanco.OperacaoBancoCfop;
import OperacaoBanco.OperacaoBancoLog;
import OperacaoBanco.OperacaoBancoProdutos;
import OperacaoBanco.OperacaoBancoUsuarios;
import java.awt.Dimension;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
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
 * @author ANDREFELIPEFELICIOGO
 */
public class TelaLog extends javax.swing.JFrame {

    /**
     * Creates new form TelaUsuarios
     */
    public TelaLog() throws IOException, BiffException {
        initComponents();
        preencherTabela("SELECT * FROM log");
    }

    int flag = 0;

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jtLog = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jtfPesquisa = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jlFundo = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Log - Movimento XML - Versão 1.0");
        setBackground(new java.awt.Color(204, 204, 204));
        setForeground(new java.awt.Color(204, 204, 204));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane2.setBackground(new java.awt.Color(204, 204, 204));
        jScrollPane2.setOpaque(false);

        jtLog.setBackground(new java.awt.Color(255, 255, 255));
        jtLog.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jtLog.setForeground(new java.awt.Color(51, 51, 51));
        jtLog.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jtLog.setAlignmentY(3.0F);
        jtLog.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jtLog.setGridColor(new java.awt.Color(204, 204, 204));
        jtLog.setInheritsPopupMenu(true);
        jtLog.setRowHeight(30);
        jtLog.setRowMargin(3);
        jtLog.setSelectionBackground(new java.awt.Color(255, 102, 0));
        jtLog.setSelectionForeground(new java.awt.Color(255, 255, 255));
        jtLog.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtLogMouseClicked(evt);
            }
        });
        jtLog.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtLogKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtLogKeyTyped(evt);
            }
        });
        jScrollPane2.setViewportView(jtLog);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 990, 390));

        jLabel9.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 0, 0));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Auditoria");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 0, 270, 90));

        jLabel10.setFont(new java.awt.Font("Times New Roman", 0, 36)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENS/logomarca II.png"))); // NOI18N
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 220, 90));

        jtfPesquisa.setToolTipText("");
        jtfPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfPesquisaKeyReleased(evt);
            }
        });
        getContentPane().add(jtfPesquisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 600, 30));

        jLabel1.setText("Pesquise por USUÁRIO, MODULO ou AÇÃO");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 90, 280, 30));

        jlFundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENS/TELA CLIENTE II.png"))); // NOI18N
        getContentPane().add(jlFundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1040, 560));

        jMenu1.setText("Opções");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        jMenuItem1.setText("Sair");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        setSize(new java.awt.Dimension(1043, 602));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public void preencherTabela(String sql) throws IOException, BiffException {
        OperacaoBancoLog obc = new OperacaoBancoLog();
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"USUÁRIO", "MÓDULO", "AÇÃO", "COD OBJETO", "DATA"};
        obc.obterConexao();
        obc.executaSql(sql);

        try {
            obc.rs.first();

            do {
                dados.add(new Object[]{
                    obc.rs.getString("usuario"),
                    obc.rs.getString("modulo"),
                    obc.rs.getString("acao"),
                    obc.rs.getLong("codigoobjeto"),
                    obc.rs.getTimestamp("dtlog")});
            } while (obc.rs.next());
        } catch (SQLException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }

        ModelTabela modelo = new ModelTabela(dados, colunas);
        DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        DefaultTableCellRenderer direita = new DefaultTableCellRenderer();
        DefaultTableCellRenderer padding = new DefaultTableCellRenderer();
        direita.setHorizontalAlignment(SwingConstants.RIGHT);
        esquerda.setHorizontalAlignment(SwingConstants.LEFT);
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);

        jtLog.setIntercellSpacing(new Dimension(5, 5));
        jtLog.setModel((TableModel) modelo);
        jtLog.getColumnModel().getColumn(0).setPreferredWidth(180);
        jtLog.getColumnModel().getColumn(0).setCellRenderer(esquerda);
        jtLog.getColumnModel().getColumn(0).setResizable(true);
        jtLog.getColumnModel().getColumn(1).setPreferredWidth(180);
        jtLog.getColumnModel().getColumn(1).setCellRenderer(esquerda);
        jtLog.getColumnModel().getColumn(1).setResizable(true);
        jtLog.getColumnModel().getColumn(2).setPreferredWidth(230);
        jtLog.getColumnModel().getColumn(2).setResizable(true);
        jtLog.getColumnModel().getColumn(2).setCellRenderer(esquerda);
        jtLog.getColumnModel().getColumn(3).setPreferredWidth(150);
        jtLog.getColumnModel().getColumn(3).setResizable(true);
        jtLog.getColumnModel().getColumn(3).setCellRenderer(direita);
        jtLog.getColumnModel().getColumn(4).setPreferredWidth(228);
        jtLog.getColumnModel().getColumn(4).setResizable(true);
        jtLog.getColumnModel().getColumn(4).setCellRenderer(centralizado);

        jtLog.getTableHeader().setReorderingAllowed(true);
        jtLog.setAutoResizeMode(jtLog.AUTO_RESIZE_OFF);
        jtLog.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        obc.fechaConexao();
    }

    public void preencherTabelaPesquisa(String pesquisa) throws IOException, BiffException, ParseException {
        OperacaoBancoLog obc = new OperacaoBancoLog();
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"USUÁRIO", "MÓDULO", "AÇÃO", "COD OBJETO", "DATA"};
        obc.obterConexao();
        obc.executaSql("SELECT * FROM log WHERE "
                + "usuario like '%" + pesquisa + "%' OR "
                + "modulo like '%" + pesquisa + "%' OR "
                + "acao like '%" + pesquisa + "%'"
                
               );

        try {
            obc.rs.first();

            do {
                dados.add(new Object[]{
                    obc.rs.getString("usuario"),
                    obc.rs.getString("modulo"),
                    obc.rs.getString("acao"),
                    obc.rs.getLong("codigoobjeto"),
                    obc.rs.getTimestamp("dtlog")});
            } while (obc.rs.next());
        } catch (SQLException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }

        ModelTabela modelo = new ModelTabela(dados, colunas);
        DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        DefaultTableCellRenderer direita = new DefaultTableCellRenderer();
        DefaultTableCellRenderer padding = new DefaultTableCellRenderer();
        direita.setHorizontalAlignment(SwingConstants.RIGHT);
        esquerda.setHorizontalAlignment(SwingConstants.LEFT);
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);

        jtLog.setIntercellSpacing(new Dimension(5, 5));
        jtLog.setModel((TableModel) modelo);
        jtLog.getColumnModel().getColumn(0).setPreferredWidth(180);
        jtLog.getColumnModel().getColumn(0).setCellRenderer(esquerda);
        jtLog.getColumnModel().getColumn(0).setResizable(true);
        jtLog.getColumnModel().getColumn(1).setPreferredWidth(180);
        jtLog.getColumnModel().getColumn(1).setCellRenderer(esquerda);
        jtLog.getColumnModel().getColumn(1).setResizable(true);
        jtLog.getColumnModel().getColumn(2).setPreferredWidth(230);
        jtLog.getColumnModel().getColumn(2).setResizable(true);
        jtLog.getColumnModel().getColumn(2).setCellRenderer(esquerda);
        jtLog.getColumnModel().getColumn(3).setPreferredWidth(150);
        jtLog.getColumnModel().getColumn(3).setResizable(true);
        jtLog.getColumnModel().getColumn(3).setCellRenderer(direita);
        jtLog.getColumnModel().getColumn(4).setPreferredWidth(228);
        jtLog.getColumnModel().getColumn(4).setResizable(true);
        jtLog.getColumnModel().getColumn(4).setCellRenderer(centralizado);

        jtLog.getTableHeader().setReorderingAllowed(true);
        jtLog.setAutoResizeMode(jtLog.AUTO_RESIZE_OFF);
        jtLog.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        obc.fechaConexao();
    }


    private void jtLogMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtLogMouseClicked

    }//GEN-LAST:event_jtLogMouseClicked

    private void jtLogKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtLogKeyTyped

    }//GEN-LAST:event_jtLogKeyTyped

    private void jtLogKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtLogKeyPressed

    }//GEN-LAST:event_jtLogKeyPressed

    private void jtfPesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfPesquisaKeyReleased
        try {
            jtfPesquisa.setText(jtfPesquisa.getText().toUpperCase());
            preencherTabelaPesquisa(jtfPesquisa.getText().toUpperCase());
        } catch (IOException ex) {
            Logger.getLogger(TelaLog.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(TelaLog.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(TelaLog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jtfPesquisaKeyReleased

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

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
            java.util.logging.Logger.getLogger(TelaLog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaLog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaLog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaLog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new TelaLog().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(TelaLog.class.getName()).log(Level.SEVERE, null, ex);
                } catch (BiffException ex) {
                    Logger.getLogger(TelaLog.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel jlFundo;
    private javax.swing.JTable jtLog;
    private javax.swing.JTextField jtfPesquisa;
    // End of variables declaration//GEN-END:variables
}
