/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VIEW;

import AUXILIAR.AuxiliarControleAcesso;
import AUXILIAR.AuxiliarLog;
import MODEL.ModelCfop;
import MODEL.ModelTabela;
import OperacaoBanco.OperacaoBancoCfop;
import OperacaoBanco.OperacaoBancoCfop;
import OperacaoBanco.OperacaoBancoUsuarios;
import static VIEW.TelaUsuarios.NOVO_USUARIO;
import com.lowagie.text.Row;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import jxl.read.biff.BiffException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class TelaCfop extends javax.swing.JFrame {

    MODEL.ModelCfop cfop = new ModelCfop();
    OperacaoBanco.OperacaoBancoCfop obc = new OperacaoBancoCfop();

    final static int NOVO_CFOP = 21;
    final static int EDITAR_CFOP = 22;
    final static int EMITIR_RELATORIOS = 23;

    int flag = 0;
    JLabel transferedados;
    int codigousuario;

    public TelaCfop() throws IOException, BiffException {
        initComponents();
        preencherTabela("SELECT * FROM cfop");

        ImageIcon imagem = new ImageIcon("C:\\Anve\\Icons\\logomarca II.png");
        //jlLogo.setIcon(new ImageIcon(imagem.getImage().getScaledInstance(120, 60, Image.SCALE_DEFAULT)));
    }

    public TelaCfop(JLabel transferedados, int codigousuario) throws IOException, BiffException {
        initComponents();
        this.transferedados = transferedados;
        preencherTabela("SELECT * FROM cfop");
        this.codigousuario = codigousuario;

        ImageIcon imagem = new ImageIcon("C:\\Anve\\Icons\\logomarca II.png");
        //jlLogo.setIcon(new ImageIcon(imagem.getImage().getScaledInstance(120, 60, Image.SCALE_DEFAULT)));
    }

    public void salvar() throws IOException {
        if (flag == 1) {
            cfop.setCpcfop(Integer.parseInt(jtfCfop.getText()));
            cfop.setNaturezadaop(jtfNaturezaDaOperacao.getText());
            cfop.setTipomovimento(jcbMovimento.getSelectedItem().toString());
            obc.incluirCfop(cfop);
            //flag = 0;
            jtfCfop.setEnabled(false);
            jtfNaturezaDaOperacao.setEnabled(false);
            jcbMovimento.setEnabled(false);
            jbBotaoNovo.setEnabled(true);
            jbBotaoSalvar.setEnabled(false);
        } else if (flag == 2) {
            cfop.setCpcfop(Integer.parseInt(jtfCfop.getText()));
            cfop.setNaturezadaop(jtfNaturezaDaOperacao.getText());
            cfop.setTipomovimento(jcbMovimento.getSelectedItem().toString());
            obc.alterarCfop(cfop);
            //flag = 0;
            jtfCfop.setEnabled(false);
            jtfNaturezaDaOperacao.setEnabled(false);
            jcbMovimento.setEnabled(false);
            jbBotaoNovo.setEnabled(true);
            jbBotaoSalvar.setEnabled(false);
        }
        flag = 0;
    }

    public void preencherTabela(String sql) throws IOException, BiffException {
        OperacaoBancoCfop obc = new OperacaoBancoCfop();
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"CFOP", "NATUREZA DA OPERAÇÃO", "TIPO DE MOVIMENTO"};
        obc.obterConexao();
        obc.executaSql(sql);

        try {
            obc.rs.first();
            do {
                dados.add(new Object[]{
                    obc.rs.getInt("cpcfop"),
                    obc.rs.getString("naturezaop"),
                    obc.rs.getString("tipomovimento"),});
            } while (obc.rs.next());
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(rootPane, "Nenhum cliente cadastrado no momento");
        }

        ModelTabela modelo = new ModelTabela(dados, colunas);        
        /*DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        DefaultTableCellRenderer direita = new DefaultTableCellRenderer();*/
        //direita.setHorizontalAlignment(SwingConstants.RIGHT);
        //esquerda.setHorizontalAlignment(SwingConstants.LEFT);
        //centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        
        cormovimento();        

        jtCfop.setModel((TableModel) modelo);
        jtCfop.getColumnModel().getColumn(0).setPreferredWidth(90);
        //jtCfop.getColumnModel().getColumn(0).setCellRenderer(centralizado);
        jtCfop.getColumnModel().getColumn(0).setResizable(true);
        jtCfop.getColumnModel().getColumn(1).setPreferredWidth(170);
        //jtCfop.getColumnModel().getColumn(1).setCellRenderer(esquerda);
        jtCfop.getColumnModel().getColumn(1).setResizable(true);
        jtCfop.getColumnModel().getColumn(2).setPreferredWidth(160);
        jtCfop.getColumnModel().getColumn(2).setResizable(true);

        jtCfop.getTableHeader().setReorderingAllowed(true);
        jtCfop.setAutoResizeMode(jtCfop.AUTO_RESIZE_OFF);
        jtCfop.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        obc.fechaConexao();
    }

    public void cormovimento() {

        jtCfop.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                Object CLASS;                              

                    CLASS = jtCfop.getValueAt(row, 2);
                    String teste = jtCfop.getValueAt(row, 2).toString();

                        if (CLASS != null && teste.equals("ENTRADA")) {
                            jtCfop.setForeground(Color.BLUE);                           
                            

                        } else if (CLASS != null && teste.equals("SAÍDA")) {
                            jtCfop.setForeground(Color.RED);                            
                            

                        } else if (CLASS != null && teste.equals("NÃO MOVIMENTA")) {
                            jtCfop.setForeground(Color.BLACK);
                            
                        }                  
                

                return this;
            }
        }
        );
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jtfCfop = new javax.swing.JTextField();
        jtfNaturezaDaOperacao = new javax.swing.JTextField();
        jcbMovimento = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jlLogo = new javax.swing.JLabel();
        jlTitulo = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtCfop = new javax.swing.JTable();
        jbBotaoCancelar = new javax.swing.JButton();
        jbBotaoEditar = new javax.swing.JButton();
        jbBotaoSalvar = new javax.swing.JButton();
        jbBotaoNovo = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jlFundo = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jmNovo = new javax.swing.JMenuItem();
        jmSalvar = new javax.swing.JMenuItem();
        jmEditar = new javax.swing.JMenuItem();
        jmCancelar = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("CFOP - Movimento XML - Versão 1.0");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtfCfop.setEnabled(false);
        getContentPane().add(jtfCfop, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, 84, -1));

        jtfNaturezaDaOperacao.setEnabled(false);
        jtfNaturezaDaOperacao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfNaturezaDaOperacaoKeyPressed(evt);
            }
        });
        getContentPane().add(jtfNaturezaDaOperacao, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 170, 216, -1));

        jcbMovimento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NÃO MOVIMENTA", "ENTRADA", "SAÍDA" }));
        jcbMovimento.setEnabled(false);
        getContentPane().add(jcbMovimento, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 170, 110, -1));

        jLabel1.setText("CFOP");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, -1, -1));

        jLabel2.setText("Natureza da Operação");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 150, 216, -1));

        jLabel3.setText("Tipo de Movimento");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 150, -1, -1));

        jlLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENS/logomarca II.png"))); // NOI18N
        getContentPane().add(jlLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 230, 90));

        jlTitulo.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 36)); // NOI18N
        jlTitulo.setForeground(new java.awt.Color(153, 0, 0));
        jlTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlTitulo.setText("CFOP");
        getContentPane().add(jlTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 0, 160, 80));

        jScrollPane2.setBackground(new java.awt.Color(204, 204, 204));
        jScrollPane2.setOpaque(false);

        jtCfop.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jtCfop.setGridColor(new java.awt.Color(204, 204, 204));
        jtCfop.setInheritsPopupMenu(true);
        jtCfop.setOpaque(false);
        jtCfop.setRowHeight(20);
        jtCfop.setSelectionBackground(new java.awt.Color(255, 102, 0));
        jtCfop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtCfopMouseClicked(evt);
            }
        });
        jtCfop.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtCfopKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtCfopKeyTyped(evt);
            }
        });
        jScrollPane2.setViewportView(jtCfop);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, 440, 280));

        jbBotaoCancelar.setText("CANCELAR");
        jbBotaoCancelar.setFocusable(false);
        jbBotaoCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbBotaoCancelarActionPerformed(evt);
            }
        });
        getContentPane().add(jbBotaoCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 100, 100, 40));

        jbBotaoEditar.setText("EDITAR");
        jbBotaoEditar.setEnabled(false);
        jbBotaoEditar.setFocusable(false);
        jbBotaoEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbBotaoEditarActionPerformed(evt);
            }
        });
        getContentPane().add(jbBotaoEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 100, 90, 40));

        jbBotaoSalvar.setText("SALVAR");
        jbBotaoSalvar.setEnabled(false);
        jbBotaoSalvar.setFocusable(false);
        jbBotaoSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbBotaoSalvarActionPerformed(evt);
            }
        });
        getContentPane().add(jbBotaoSalvar, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 100, 90, 40));

        jbBotaoNovo.setText("NOVO");
        jbBotaoNovo.setFocusable(false);
        jbBotaoNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbBotaoNovoActionPerformed(evt);
            }
        });
        getContentPane().add(jbBotaoNovo, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, 90, 40));

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel4.setText("Ctrl+E: Editar | Ctrl+Z: Cancelar");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 520, 210, 20));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENS/legenda.png"))); // NOI18N
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 500, 130, 60));

        jLabel6.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel6.setText("Ctrl+N: Novo | Ctrl+S: Salvar");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 500, 190, 20));

        jlFundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENS/fundo padrão II.png"))); // NOI18N
        getContentPane().add(jlFundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -30, 530, 610));

        jMenu2.setText("Opções");

        jmNovo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jmNovo.setText("Novo");
        jmNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmNovoActionPerformed(evt);
            }
        });
        jMenu2.add(jmNovo);

        jmSalvar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jmSalvar.setText("Salvar");
        jmSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmSalvarActionPerformed(evt);
            }
        });
        jMenu2.add(jmSalvar);

        jmEditar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        jmEditar.setText("Editar");
        jmEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmEditarActionPerformed(evt);
            }
        });
        jMenu2.add(jmEditar);

        jmCancelar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        jmCancelar.setText("Cancelar");
        jmCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmCancelarActionPerformed(evt);
            }
        });
        jMenu2.add(jmCancelar);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        jMenuItem2.setText("Sair");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuBar1.add(jMenu2);

        jMenu1.setText("Relatórios");
        jMenu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu1MouseClicked(evt);
            }
        });

        jMenuItem1.setText("Lista de CFOP");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        setSize(new java.awt.Dimension(527, 632));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jtfNaturezaDaOperacaoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfNaturezaDaOperacaoKeyPressed
        jtfNaturezaDaOperacao.setText(jtfNaturezaDaOperacao.getText().toUpperCase());
    }//GEN-LAST:event_jtfNaturezaDaOperacaoKeyPressed

    private void jtCfopMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtCfopMouseClicked
        jtfCfop.setText(jtCfop.getValueAt(jtCfop.getSelectedRow(), 0).toString());
        jtfNaturezaDaOperacao.setText(jtCfop.getValueAt(jtCfop.getSelectedRow(), 1).toString());
        jcbMovimento.setSelectedItem(jtCfop.getValueAt(jtCfop.getSelectedRow(), 2));
        jbBotaoEditar.setEnabled(true);
    }//GEN-LAST:event_jtCfopMouseClicked

    private void jtCfopKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtCfopKeyTyped

    }//GEN-LAST:event_jtCfopKeyTyped

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        AUXILIAR.AuxiliarControleAcesso aux = new AuxiliarControleAcesso();
        try {
            if (transferedados.getText().equals("ADM") || aux.controle(this.codigousuario, EMITIR_RELATORIOS) == true) {

                OperacaoBancoCfop ob = new OperacaoBancoCfop();
                ob.obterConexao();

                String src = "Jasper/relatorioListaCfop.jasper";
                JasperPrint jasperPrint = null;
                try {
                    jasperPrint = JasperFillManager.fillReport(src, null, ob.obterConexao());
                } catch (JRException ex) {
                    System.out.println("ERRO: " + ex.getMessage());
                }

                JasperViewer view = new JasperViewer(jasperPrint, false);
                view.setVisible(true);
                ob.fechaConexao();

            }
        } catch (IOException ex) {
            Logger.getLogger(TelaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(TelaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TelaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu1MouseClicked

    }//GEN-LAST:event_jMenu1MouseClicked

    private void jmSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmSalvarActionPerformed
        if (jbBotaoSalvar.isEnabled()) {
            System.out.println("LIB");
            try {
                salvar();
            } catch (IOException ex) {
                Logger.getLogger(TelaCfop.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                preencherTabela("SELECT * FROM cfop");
                AUXILIAR.AuxiliarLog aux = new AuxiliarLog();
                if (flag == 1) {
                    aux.log(transferedados.getText(), "CFOP", "INCLUIR", Long.parseLong(jtfCfop.getText()));
                } else if (flag == 2) {
                    aux.log(transferedados.getText(), "CFOP", "ALTERAR", Long.parseLong(jtfCfop.getText()));
                }

            } catch (IOException ex) {
                Logger.getLogger(TelaCfop.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BiffException ex) {
                Logger.getLogger(TelaCfop.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("BLOQ");
        }
    }//GEN-LAST:event_jmSalvarActionPerformed

    private void jmEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmEditarActionPerformed
        editar();
    }//GEN-LAST:event_jmEditarActionPerformed

    private void jmCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmCancelarActionPerformed
        cancelar();
    }//GEN-LAST:event_jmCancelarActionPerformed

    private void jbBotaoNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbBotaoNovoActionPerformed
        novo();
    }//GEN-LAST:event_jbBotaoNovoActionPerformed

    private void jbBotaoSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbBotaoSalvarActionPerformed
        if (jbBotaoSalvar.isEnabled()) {
            System.out.println("LIB");
            try {
                salvar();
            } catch (IOException ex) {
                Logger.getLogger(TelaCfop.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                preencherTabela("SELECT * FROM cfop");
                AUXILIAR.AuxiliarLog aux = new AuxiliarLog();
                if (flag == 1) {
                    aux.log(transferedados.getText(), "CFOP", "INCLUIR", Long.parseLong(jtfCfop.getText()));
                } else if (flag == 2) {
                    aux.log(transferedados.getText(), "CFOP", "ALTERAR", Long.parseLong(jtfCfop.getText()));
                }

            } catch (IOException ex) {
                Logger.getLogger(TelaCfop.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BiffException ex) {
                Logger.getLogger(TelaCfop.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("BLOQ");
        }
    }//GEN-LAST:event_jbBotaoSalvarActionPerformed

    private void jbBotaoEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbBotaoEditarActionPerformed
        editar();
    }//GEN-LAST:event_jbBotaoEditarActionPerformed

    private void jbBotaoCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbBotaoCancelarActionPerformed
        cancelar();
    }//GEN-LAST:event_jbBotaoCancelarActionPerformed

    private void jtCfopKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtCfopKeyReleased
        jtfCfop.setText(jtCfop.getValueAt(jtCfop.getSelectedRow(), 0).toString());
        jtfNaturezaDaOperacao.setText(jtCfop.getValueAt(jtCfop.getSelectedRow(), 1).toString());
        jcbMovimento.setSelectedItem(jtCfop.getValueAt(jtCfop.getSelectedRow(), 2));
        jbBotaoEditar.setEnabled(true);
    }//GEN-LAST:event_jtCfopKeyReleased

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        if (flag == 0) {
            this.dispose();
        } else if (flag == 1) {

            int resp = JOptionPane.showConfirmDialog(null, "Os dados preenchidos serão perdidos, deseja sair?", null, 0);
            if (resp == 0) {
                this.dispose();
            }
        } else if (flag == 2) {
            int resp = JOptionPane.showConfirmDialog(null, "A alteração do cadastro não foi salva, deseja sair??", null, 0);
            if (resp == 0) {
                this.dispose();
            }
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jmNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmNovoActionPerformed
        novo();
    }//GEN-LAST:event_jmNovoActionPerformed

    public void novo() {
        AUXILIAR.AuxiliarControleAcesso aux = new AuxiliarControleAcesso();
        try {
            if (transferedados.getText().equals("ADM") || aux.controle(this.codigousuario, NOVO_CFOP) == true) {

                if (jbBotaoNovo.isEnabled()) {
                    flag = 1;
                    jbBotaoSalvar.setEnabled(true);
                    jtfCfop.setEnabled(true);
                    jtfNaturezaDaOperacao.setEnabled(true);
                    jcbMovimento.setEnabled(true);
                    jbBotaoNovo.setEnabled(false);
                    jbBotaoEditar.setEnabled(false);
                    jtfCfop.setText("");
                    jtfNaturezaDaOperacao.setText("");
                    jcbMovimento.setSelectedIndex(0);
                }

            }
        } catch (IOException ex) {
            Logger.getLogger(TelaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(TelaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TelaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editar() {
        AUXILIAR.AuxiliarControleAcesso aux = new AuxiliarControleAcesso();
        try {
            if (transferedados.getText().equals("ADM") || aux.controle(this.codigousuario, EDITAR_CFOP) == true) {

                if (jtfCfop.getText().isEmpty()) {

                } else {
                    flag = 2;
                    jbBotaoSalvar.setEnabled(true);
                    jtfCfop.setEnabled(false);
                    jtfNaturezaDaOperacao.setEnabled(true);
                    jcbMovimento.setEnabled(true);
                    jbBotaoNovo.setEnabled(false);
                }

            }
        } catch (IOException ex) {
            Logger.getLogger(TelaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(TelaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TelaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cancelar() {
        jbBotaoNovo.setEnabled(true);
        jbBotaoSalvar.setEnabled(false);
        jbBotaoEditar.setEnabled(false);
        jtfCfop.setEnabled(false);
        jtfNaturezaDaOperacao.setEnabled(false);
        jcbMovimento.setEnabled(false);
        flag = 0;
    }

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
            java.util.logging.Logger.getLogger(TelaCfop.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaCfop.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaCfop.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaCfop.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new TelaCfop().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(TelaCfop.class.getName()).log(Level.SEVERE, null, ex);
                } catch (BiffException ex) {
                    Logger.getLogger(TelaCfop.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbBotaoCancelar;
    private javax.swing.JButton jbBotaoEditar;
    private javax.swing.JButton jbBotaoNovo;
    private javax.swing.JButton jbBotaoSalvar;
    private javax.swing.JComboBox<String> jcbMovimento;
    private javax.swing.JLabel jlFundo;
    private javax.swing.JLabel jlLogo;
    private javax.swing.JLabel jlTitulo;
    private javax.swing.JMenuItem jmCancelar;
    private javax.swing.JMenuItem jmEditar;
    private javax.swing.JMenuItem jmNovo;
    private javax.swing.JMenuItem jmSalvar;
    private javax.swing.JTable jtCfop;
    private javax.swing.JTextField jtfCfop;
    private javax.swing.JTextField jtfNaturezaDaOperacao;
    // End of variables declaration//GEN-END:variables
}
