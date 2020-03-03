/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VIEW;

import AUXILIAR.AuxiliarControleAcesso;
import AUXILIAR.AuxiliarLog;
import MODEL.ModelContagens;
import MODEL.ModelItensContagens;
import MODEL.ModelProdutos;
import MODEL.ModelTabela;
import OperacaoBanco.OperacaoBancoContagens;
import OperacaoBanco.OperacaoBancoItensContagens;
import OperacaoBanco.OperacaoBancoProdutos;
import OperacaoBanco.OperacaoBancoProdutos;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import jxl.read.biff.BiffException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import org.jfree.ui.DateCellRenderer;

public class TelaContagens extends javax.swing.JFrame {

    final static int IMPORTAR_ARQUIVO = 71;
    final static int EDITAR_CONTAGEM = 72;
    final static int EMITIR_RELATORIO = 73;

    JLabel transferedados;
    int codigousuario;
    int flag = 0;

    public TelaContagens() throws IOException, BiffException {
        initComponents();
        preencherTabela("SELECT * FROM contagens");
    }

    public TelaContagens(JLabel transferedados, int codigousuario) throws IOException, BiffException {
        initComponents();
        this.transferedados = transferedados;
        this.codigousuario = codigousuario;
        preencherTabela("SELECT * FROM contagens");

    }

    String caminhoContagens = "";
    String[] moeda = null;
    String[] moeda2 = null;

    public void lerArquivo() throws IOException, BiffException {
        ModelContagens contagens = new ModelContagens();
        OperacaoBanco.OperacaoBancoContagens obc = new OperacaoBancoContagens();
        ModelItensContagens itenscontagens = new ModelItensContagens();
        OperacaoBancoItensContagens obic = new OperacaoBancoItensContagens();

        String arquivo = "C:\\Anve\\ArquivoExportacao\\ArqExport.txt";
        BufferedReader conteudo = null;
        String linha = "";
        String csvSeparadorDeCampo = ";";

        conteudo = new BufferedReader(new FileReader(arquivo));
        linha = conteudo.readLine();
        if (linha == null) {
            JOptionPane.showMessageDialog(rootPane, "ARQUIVO VAZIO!");
        }

        //inclusão do codigo da contagem no banco        
        String[] moeda = linha.split(csvSeparadorDeCampo);
        try {
            contagens.setCodigoContagem(Long.parseLong(moeda[0]));
            obc.incluirContagens(contagens);
            System.out.println("PASSOU");

        } catch (Exception erro) {
            System.out.println("ERRO: " + erro);
            JOptionPane.showMessageDialog(null, "ERRO INCLUSÃO DO CODIGO DA CONTAGEM: " + erro);
        }

        //FIM da inclusão do codigo da contagem no banco
        //inclusão dos itens da contagem no banco
        conteudo = new BufferedReader(new FileReader(arquivo));
        while ((linha = conteudo.readLine()) != null) {
            moeda = linha.split(csvSeparadorDeCampo);
            try {

                itenscontagens.setContagens(contagens);
                itenscontagens.setCodigoproduto(Integer.parseInt(moeda[1]));
                itenscontagens.setDescricao(String.valueOf(moeda[2]));
                itenscontagens.setEstoque(Integer.parseInt(moeda[3]));
                itenscontagens.setContagem(Integer.parseInt(moeda[4]));

                obic.incluirItensContagens(itenscontagens);
                System.out.println("PASSOU");

            } catch (Exception erro) {
                System.out.println("ERRO: " + erro);
                JOptionPane.showMessageDialog(null, "ERRO INCLUSÃO DOS ITENS DA CONTAGENS: " + erro);
            }
        }

        AUXILIAR.AuxiliarLog aux = new AuxiliarLog();
        aux.log(transferedados.getText(), "CONTAGENS", "IMPORTAR", contagens.getCodigoContagem());

    }

    public void preencherTabelaItens(String sql) throws IOException, BiffException {
        OperacaoBancoItensContagens obc = new OperacaoBancoItensContagens();
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"COD DO PRODUTO", "DESCRIÇÃO", "ESTOQUE", "CONTAGEM"};

        obc.obterConexao();
        obc.executaSql(sql);

        try {
            obc.rs.first();
            do {
                dados.add(new Object[]{
                    obc.rs.getInt("codigoproduto"),
                    obc.rs.getString("descricaoproduto"),
                    obc.rs.getInt("estoque"),
                    obc.rs.getInt("contagem"),
                    obc.rs.getLong("fkcodigocontagem")
                });
            } while (obc.rs.next());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Contagem não encontrada: " + ex);
        }

        ModelTabela modelo = new ModelTabela(dados, colunas);
        DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        DefaultTableCellRenderer direita = new DefaultTableCellRenderer();
        direita.setHorizontalAlignment(SwingConstants.RIGHT);
        esquerda.setHorizontalAlignment(SwingConstants.LEFT);
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        DefaultTableCellRenderer editar = new DateCellRenderer();

        jtItensContagens.setModel((TableModel) modelo);
        jtItensContagens.getColumnModel().getColumn(0).setPreferredWidth(120);
        jtItensContagens.getColumnModel().getColumn(0).setCellRenderer(direita);
        jtItensContagens.getColumnModel().getColumn(0).setResizable(true);
        jtItensContagens.getColumnModel().getColumn(1).setPreferredWidth(300);
        jtItensContagens.getColumnModel().getColumn(1).setCellRenderer(esquerda);
        jtItensContagens.getColumnModel().getColumn(1).setResizable(true);
        jtItensContagens.getColumnModel().getColumn(2).setPreferredWidth(114);
        jtItensContagens.getColumnModel().getColumn(2).setCellRenderer(direita);
        jtItensContagens.getColumnModel().getColumn(2).setResizable(true);
        jtItensContagens.getColumnModel().getColumn(3).setPreferredWidth(114);
        jtItensContagens.getColumnModel().getColumn(3).setCellRenderer(direita);
        jtItensContagens.getColumnModel().getColumn(3).setResizable(true);

        jtItensContagens.getTableHeader().setReorderingAllowed(true);
        jtItensContagens.setAutoResizeMode(jtContagens.AUTO_RESIZE_OFF);
        jtItensContagens.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        obc.fechaConexao();
    }

    public void preencherTabela(String sql) throws IOException, BiffException {
        OperacaoBancoContagens obc = new OperacaoBancoContagens();
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"CODIGO CONTAGEM"};
        obc.obterConexao();
        obc.executaSql(sql);

        try {
            obc.rs.first();

            do {
                dados.add(new Object[]{
                    obc.rs.getLong("codigoContagem")

                });
            } while (obc.rs.next());
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(rootPane, "Nenhuma contagem cadastrada no momento");
        }

        ModelTabela modelo = new ModelTabela(dados, colunas);
        DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        DefaultTableCellRenderer direita = new DefaultTableCellRenderer();
        direita.setHorizontalAlignment(SwingConstants.RIGHT);
        esquerda.setHorizontalAlignment(SwingConstants.LEFT);
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);

        jtContagens.setModel((TableModel) modelo);
        jtContagens.getColumnModel().getColumn(0).setPreferredWidth(178);
        jtContagens.getColumnModel().getColumn(0).setCellRenderer(centralizado);
        jtContagens.getColumnModel().getColumn(0).setResizable(true);

        jtContagens.getTableHeader().setReorderingAllowed(true);
        jtContagens.setAutoResizeMode(jtContagens.AUTO_RESIZE_OFF);
        jtContagens.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        obc.fechaConexao();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jtContagens = new javax.swing.JTable();
        jlLogomarca = new javax.swing.JLabel();
        jtNomeTabela2 = new javax.swing.JLabel();
        jtNomeTabela1 = new javax.swing.JLabel();
        jlNomeTela = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtItensContagens = new javax.swing.JTable();
        jtImportar = new javax.swing.JButton();
        jtfCodContagem = new javax.swing.JTextField();
        jtfCodProduto = new javax.swing.JTextField();
        jtfDescProduto = new javax.swing.JTextField();
        jtfContagem = new javax.swing.JTextField();
        jtfEstoque = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jbCancelar = new javax.swing.JButton();
        jbEditar = new javax.swing.JButton();
        jbSalvar = new javax.swing.JButton();
        jbImprimir = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jlFundo = new javax.swing.JLabel();
        jMenuBar3 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Contagens - Movimento XML - Versão 1.0");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane2.setAutoscrolls(true);
        jScrollPane2.setOpaque(false);

        jtContagens.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jtContagens.setGridColor(new java.awt.Color(204, 204, 204));
        jtContagens.setInheritsPopupMenu(true);
        jtContagens.setOpaque(false);
        jtContagens.setRowHeight(20);
        jtContagens.setSelectionBackground(new java.awt.Color(255, 102, 0));
        jtContagens.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtContagensMouseClicked(evt);
            }
        });
        jtContagens.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtContagensKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtContagensKeyTyped(evt);
            }
        });
        jScrollPane2.setViewportView(jtContagens);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, 180, 310));

        jlLogomarca.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlLogomarca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENS/logomarca II.png"))); // NOI18N
        getContentPane().add(jlLogomarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 240, 90));

        jtNomeTabela2.setFont(new java.awt.Font("Franklin Gothic Heavy", 0, 14)); // NOI18N
        jtNomeTabela2.setForeground(new java.awt.Color(0, 0, 0));
        jtNomeTabela2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jtNomeTabela2.setText("Itens contagens");
        getContentPane().add(jtNomeTabela2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 160, 110, 30));

        jtNomeTabela1.setFont(new java.awt.Font("Franklin Gothic Heavy", 0, 14)); // NOI18N
        jtNomeTabela1.setForeground(new java.awt.Color(0, 0, 0));
        jtNomeTabela1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jtNomeTabela1.setText("Contagens");
        getContentPane().add(jtNomeTabela1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, -1, 30));

        jlNomeTela.setFont(new java.awt.Font("Franklin Gothic Heavy", 0, 36)); // NOI18N
        jlNomeTela.setForeground(new java.awt.Color(153, 51, 0));
        jlNomeTela.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlNomeTela.setText("Contagens");
        getContentPane().add(jlNomeTela, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 10, -1, 70));

        jScrollPane3.setAutoscrolls(true);
        jScrollPane3.setOpaque(false);

        jtItensContagens.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jtItensContagens.setGridColor(new java.awt.Color(204, 204, 204));
        jtItensContagens.setInheritsPopupMenu(true);
        jtItensContagens.setOpaque(false);
        jtItensContagens.setRowHeight(20);
        jtItensContagens.setSelectionBackground(new java.awt.Color(255, 102, 0));
        jtItensContagens.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtItensContagensMouseClicked(evt);
            }
        });
        jtItensContagens.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtItensContagensKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtItensContagensKeyTyped(evt);
            }
        });
        jScrollPane3.setViewportView(jtItensContagens);

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 190, 650, 310));

        jtImportar.setText("Importar");
        jtImportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtImportarActionPerformed(evt);
            }
        });
        getContentPane().add(jtImportar, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 30, 110, 40));

        jtfCodContagem.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfCodContagem.setEnabled(false);
        getContentPane().add(jtfCodContagem, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 120, 180, -1));

        jtfCodProduto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfCodProduto.setEnabled(false);
        getContentPane().add(jtfCodProduto, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 120, 110, -1));

        jtfDescProduto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfDescProduto.setEnabled(false);
        getContentPane().add(jtfDescProduto, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 120, 330, -1));

        jtfContagem.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfContagem.setEnabled(false);
        getContentPane().add(jtfContagem, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 120, 100, -1));

        jtfEstoque.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfEstoque.setEnabled(false);
        getContentPane().add(jtfEstoque, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 120, 110, -1));

        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Cod. Contagem");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, -1, -1));

        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Cod. Produto");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 100, -1, -1));

        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Descrição do Produto");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 100, -1, -1));

        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Estoque");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 100, -1, -1));

        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Contagem");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 100, -1, -1));

        jbCancelar.setText("CANCELAR");
        jbCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCancelarActionPerformed(evt);
            }
        });
        getContentPane().add(jbCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 30, 110, 40));

        jbEditar.setText("EDITAR");
        jbEditar.setEnabled(false);
        jbEditar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbEditarMouseClicked(evt);
            }
        });
        jbEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbEditarActionPerformed(evt);
            }
        });
        getContentPane().add(jbEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 30, 110, 40));

        jbSalvar.setText("SALVAR");
        jbSalvar.setEnabled(false);
        jbSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSalvarActionPerformed(evt);
            }
        });
        getContentPane().add(jbSalvar, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 30, 110, 40));

        jbImprimir.setText("Imprimir");
        jbImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbImprimirActionPerformed(evt);
            }
        });
        getContentPane().add(jbImprimir, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 160, -1, -1));

        jLabel6.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel6.setText("Ctrl + I: Importar | Ctrl + S: Salvar | Ctrl + E: Editar | Ctrl + Z: Cancelar | Ctrl + P: Imprimir");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 510, 620, -1));

        jlFundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENS/fundo padrão II.png"))); // NOI18N
        getContentPane().add(jlFundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1130, 550));

        jMenu3.setText("Opções");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Importar");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Salvar");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem2);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setText("Editar");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem3);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setText("Cancelar");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem5.setText("Imprimir Contagem");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem5);

        jMenuItem6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        jMenuItem6.setText("Sair");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem6);

        jMenuBar3.add(jMenu3);

        setJMenuBar(jMenuBar3);

        setSize(new java.awt.Dimension(978, 596));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jtContagensMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtContagensMouseClicked
        selecionaContagem();
    }//GEN-LAST:event_jtContagensMouseClicked

    public void selecionaContagem() {
        carregarTabelaItens();
        jtfCodContagem.setText(jtContagens.getValueAt(jtContagens.getSelectedRow(), 0).toString());
    }

    public void carregarTabelaItens() {
        Long codigocotagem = Long.parseLong(jtContagens.getValueAt(jtContagens.getSelectedRow(), 0).toString());
        try {
            preencherTabelaItens("SELECT * FROM itenscontagens WHERE fkcodigocontagem = " + codigocotagem);

        } catch (IOException ex) {
            Logger.getLogger(TelaContagens.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(TelaContagens.class.getName()).log(Level.SEVERE, null, ex);
        }

        jtfCodContagem.setText("");
        jtfCodProduto.setText("");
        jtfContagem.setText("");
        jtfDescProduto.setText("");
        jtfEstoque.setText("");
        jtfCodContagem.setEnabled(false);
        jtfCodProduto.setEnabled(false);
        jtfContagem.setEnabled(false);
        jtfDescProduto.setEnabled(false);
        jtfEstoque.setEnabled(false);
    }

    private void jtContagensKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtContagensKeyTyped

    }//GEN-LAST:event_jtContagensKeyTyped

    private void jtItensContagensMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtItensContagensMouseClicked
        selecionaItemContagem();
    }//GEN-LAST:event_jtItensContagensMouseClicked

    public void selecionaItemContagem(){
        jtfCodContagem.setText(jtContagens.getValueAt(jtContagens.getSelectedRow(), 0).toString());
        jtfCodProduto.setText(jtItensContagens.getValueAt(jtItensContagens.getSelectedRow(), 0).toString());
        jtfDescProduto.setText(jtItensContagens.getValueAt(jtItensContagens.getSelectedRow(), 1).toString());
        jtfEstoque.setText(jtItensContagens.getValueAt(jtItensContagens.getSelectedRow(), 2).toString());
        jtfContagem.setText(jtItensContagens.getValueAt(jtItensContagens.getSelectedRow(), 3).toString());

        jbEditar.setEnabled(true);
    }
    
    private void jtItensContagensKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtItensContagensKeyTyped

    }//GEN-LAST:event_jtItensContagensKeyTyped

    private void jtImportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtImportarActionPerformed
        importar();
    }//GEN-LAST:event_jtImportarActionPerformed

    public void importar() {
        try {

            AUXILIAR.AuxiliarControleAcesso aux = new AuxiliarControleAcesso();
            if (transferedados.getText().equals("ADM") || aux.controle(this.codigousuario, EMITIR_RELATORIO) == true) {
                lerArquivo();
                preencherTabela("SELECT * FROM contagens");
            }

        } catch (IOException ex) {
            Logger.getLogger(TelaContagens.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(TelaContagens.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TelaContagens.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void jbEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEditarActionPerformed
        editar();
    }//GEN-LAST:event_jbEditarActionPerformed

    public void editar() {
        try {
            flag = 2;
            AUXILIAR.AuxiliarControleAcesso aux = new AuxiliarControleAcesso();
            if (transferedados.getText().equals("ADM") || aux.controle(this.codigousuario, EMITIR_RELATORIO) == true) {

                jtContagens.setEnabled(false);
                jtItensContagens.setEnabled(false);
                jtfContagem.setEnabled(true);
                jbSalvar.setEnabled(true);
                jbEditar.setEnabled(false);

            }
        } catch (IOException ex) {
            Logger.getLogger(TelaContagens.class.getName()).log(Level.SEVERE, null, ex);
            flag = 0;
        } catch (BiffException ex) {
            Logger.getLogger(TelaContagens.class.getName()).log(Level.SEVERE, null, ex);
            flag = 0;
        } catch (SQLException ex) {
            Logger.getLogger(TelaContagens.class.getName()).log(Level.SEVERE, null, ex);
            flag = 0;
        }
    }

    private void jbSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSalvarActionPerformed
        salvar();
    }//GEN-LAST:event_jbSalvarActionPerformed

    public void salvar() {
        try {
            ModelItensContagens itens = new ModelItensContagens();
            ModelContagens contagens = new ModelContagens();
            OperacaoBancoItensContagens obic = new OperacaoBancoItensContagens();

            contagens.setCodigoContagem(Long.parseLong(jtfCodContagem.getText()));
            itens.setContagens(contagens);
            itens.setCodigoproduto(Integer.parseInt(jtfCodProduto.getText()));
            itens.setContagem(Integer.parseInt(jtfContagem.getText()));

            obic.alterarContagem(itens);
            carregarTabelaItens();
            jtContagens.setEnabled(true);
            jtItensContagens.setEnabled(true);
            jbSalvar.setEnabled(false);
            jbEditar.setEnabled(false);
            flag = 0;

            AUXILIAR.AuxiliarLog aux = new AuxiliarLog();
            aux.log(transferedados.getText(), "CONTAGENS", "ALTERAR", contagens.getCodigoContagem());

        } catch (IOException ex) {
            Logger.getLogger(TelaContagens.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(TelaContagens.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void jbCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCancelarActionPerformed

        cancelar();
    }//GEN-LAST:event_jbCancelarActionPerformed

    public void cancelar() {
        jtContagens.setEnabled(true);
        jtItensContagens.setEnabled(true);

        jtfCodContagem.setText("");
        jtfCodProduto.setText("");
        jtfContagem.setText("");
        jtfDescProduto.setText("");
        jtfEstoque.setText("");
        jtfCodContagem.setEnabled(false);
        jtfCodProduto.setEnabled(false);
        jtfContagem.setEnabled(false);
        jtfDescProduto.setEnabled(false);
        jtfEstoque.setEnabled(false);

        jbSalvar.setEnabled(false);
        jbEditar.setEnabled(false);
        flag = 0;
    }

    private void jbEditarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbEditarMouseClicked

    }//GEN-LAST:event_jbEditarMouseClicked

    private void jbImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbImprimirActionPerformed
        imprimirContagem();
    }//GEN-LAST:event_jbImprimirActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        importar();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        salvar();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        editar();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        cancelar();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        imprimirContagem();
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jtContagensKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtContagensKeyPressed
        selecionaContagem();
    }//GEN-LAST:event_jtContagensKeyPressed

    private void jtItensContagensKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtItensContagensKeyPressed
        selecionaItemContagem();
    }//GEN-LAST:event_jtItensContagensKeyPressed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
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
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    public void imprimirContagem() {
        try {
            AUXILIAR.AuxiliarControleAcesso aux = new AuxiliarControleAcesso();
            if (transferedados.getText().equals("ADM") || aux.controle(this.codigousuario, EMITIR_RELATORIO) == true) {

                if (jtfCodContagem.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "É necessário selecionar uma contagem para a impressão.");
                } else {
                    HashMap parametro = new HashMap<>();
                    Long codigo = Long.parseLong(jtfCodContagem.getText());

                    parametro.put("codigoContagem", codigo);
                    System.out.println(codigo);

                    OperacaoBancoItensContagens obic = new OperacaoBancoItensContagens();
                    obic.obterConexao();

                    String src = "Jasper/relatorioContagens.jasper";
                    JasperPrint jasperPrint = null;
                    jasperPrint = JasperFillManager.fillReport(src, parametro, obic.obterConexao());

                    JasperViewer view = new JasperViewer(jasperPrint, false);
                    view.setVisible(true);
                    obic.fechaConexao();

                    AUXILIAR.AuxiliarLog log = new AuxiliarLog();
                    log.log(transferedados.getText(), "CONTAGENS", "RELATÓRIO", Long.parseLong("731"));

                }
            }
        } catch (IOException ex) {
            Logger.getLogger(TelaContagens.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(TelaContagens.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(TelaContagens.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TelaContagens.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            java.util.logging.Logger.getLogger(TelaContagens.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaContagens.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaContagens.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaContagens.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new TelaContagens().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(TelaContagens.class.getName()).log(Level.SEVERE, null, ex);
                } catch (BiffException ex) {
                    Logger.getLogger(TelaContagens.class.getName()).log(Level.SEVERE, null, ex);
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
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar3;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JButton jbEditar;
    private javax.swing.JButton jbImprimir;
    private javax.swing.JButton jbSalvar;
    private javax.swing.JLabel jlFundo;
    private javax.swing.JLabel jlLogomarca;
    private javax.swing.JLabel jlNomeTela;
    private javax.swing.JTable jtContagens;
    private javax.swing.JButton jtImportar;
    private javax.swing.JTable jtItensContagens;
    private javax.swing.JLabel jtNomeTabela1;
    private javax.swing.JLabel jtNomeTabela2;
    private javax.swing.JTextField jtfCodContagem;
    private javax.swing.JTextField jtfCodProduto;
    private javax.swing.JTextField jtfContagem;
    private javax.swing.JTextField jtfDescProduto;
    private javax.swing.JTextField jtfEstoque;
    // End of variables declaration//GEN-END:variables
}
