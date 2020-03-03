/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VIEW;

import AUXILIAR.AuxiliarControleAcesso;
import AUXILIAR.AuxiliarLog;
import MODEL.ModelProdutos;
import MODEL.ModelTabela;
import OperacaoBanco.OperacaoBancoCfop;
import OperacaoBanco.OperacaoBancoProdutos;
import OperacaoBanco.OperacaoBancoProdutos;
import static VIEW.TelaUsuarios.NOVO_USUARIO;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
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
public class TelaProdutos extends javax.swing.JFrame {

    final static int IMPORTAR_PRODUTOS = 31;
    final static int EMITIR_RELATORIOS = 32;

    JLabel transferedados;
    int codigousuario;
    int flag = 0;

    public TelaProdutos() throws IOException, BiffException {
        initComponents();
        preencherTabela("SELECT * FROM produtos");
    }

    public TelaProdutos(JLabel transferedados, int codigousuario) throws IOException, BiffException {
        initComponents();
        this.transferedados = transferedados;
        this.codigousuario = codigousuario;
        preencherTabela("SELECT * FROM produtos");
    }

    String caminhoProdutos = "";
    String[] moeda = null;
    int contaregistros = 0;

    public void lerArquivo(String caminho) throws IOException, BiffException {
        MODEL.ModelProdutos produtos = new ModelProdutos();
        OperacaoBanco.OperacaoBancoProdutos obc = new OperacaoBancoProdutos();

        String csvArquivo = caminho;
        BufferedReader conteudoCSV = null;
        String linha = "";
        String csvSeparadorDeCampo = ";";

        try {
            conteudoCSV = new BufferedReader(new FileReader(csvArquivo));

            while ((linha = conteudoCSV.readLine()) != null) {

                while ((linha = conteudoCSV.readLine()) != "Familia;Produtos;Reduzido;Codigo Companhia;Descricao;Grupo;Categoria;"
                        + "Marca;Sigla;Sabor;Un;Qtd/Un;Peso Un;Peso Emb;Origem;Mercosul;IPI;ICMS;Red ICMS;Potencial;Modelo 3;"
                        + "Grupo Fiscal;Grupo Gerencial;Classf.Fiscal;Classe 10;Manifesto;Permite CEV;IPI Pautado;Venda UN;"
                        + "Ativo;Local Estoque;Exclui Carreg.;Tipo Produtos;Merchandising;Cod Barras;Cod Bar Frac;||DIPI-1;"
                        + "DIPI-2;Cod.CIA-2;Cod.CIA-3;Cod.CIA-4;Cod.CIA-5;Fat.Conv.Distrib;Fat.Conv1;Fat.Conv2;Aliq.PVV;Preco;"
                        + "Sld estoq CX;Sld estoq UN;Preco PVV;Vlr.ICMS;Vlr.IPI;Vlr.ICMR;Embalagem;Tp.Ativ;Permite-bonif;") {

                    if (linha == null) {
                        break;
                    }

                    System.out.println("teste" + linha);
                    String[] moeda = linha.split(csvSeparadorDeCampo);

                    try {
                        produtos.setCpcodpro(Long.parseLong(moeda[2]));
                        produtos.setDescricao(moeda[4]);
                        produtos.setCategoria(moeda[6]);
                        produtos.setMarca(moeda[7]);
                        produtos.setQtdun(Integer.parseInt(moeda[11]));
                        produtos.setUnidade(moeda[10]);
                        produtos.setCodigofabrica(Integer.parseInt(moeda[3]));
                        produtos.setCodigofabrica2(Integer.parseInt(moeda[38]));
                        produtos.setCodigofabrica3(Integer.parseInt(moeda[39]));
                        produtos.setCodigofabrica4(Integer.parseInt(moeda[40]));
                        produtos.setCodigofabrica5(Integer.parseInt(moeda[41]));

                        obc.incluirProdutos(produtos);
                        System.out.println("PASSOU");

                        contaregistros++;

                    } catch (Exception erro) {
                        System.out.println("ERRO: " + erro);
                        break;
                    }
                }
                break;
            };

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado" + e.getMessage());
        }
    }

    public void preecherTabelaPesquisa(String pesquisar, String campoBanco) throws IOException, BiffException {
        OperacaoBancoProdutos obc = new OperacaoBancoProdutos();
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"COD", "DESCRIÇÃO", "CATEGORIA", "MARCA",
            "UNIDADE", "QTD EMB", "ESTOQUE"};
        obc.obterConexao();
        obc.executaSql("SELECT * FROM produtos where " + campoBanco + "  like '%" + pesquisar + "%'");

        try {
            obc.rs.first();

            do {
                dados.add(new Object[]{
                    obc.rs.getLong("cpcodpro"),
                    obc.rs.getString("descricao"),
                    obc.rs.getString("categoria"),
                    obc.rs.getString("marca"),
                    obc.rs.getString("unidade"),
                    obc.rs.getInt("qtdun"),
                    obc.rs.getInt("Estoque")
                });
            } while (obc.rs.next());
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(rootPane, "Nenhum cliente cadastrado no momento");
        }

        ModelTabela modelo = new ModelTabela(dados, colunas);
        DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        DefaultTableCellRenderer direita = new DefaultTableCellRenderer();
        direita.setHorizontalAlignment(SwingConstants.RIGHT);
        esquerda.setHorizontalAlignment(SwingConstants.LEFT);
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);

        jtProdutos.setModel((TableModel) modelo);
        jtProdutos.getColumnModel().getColumn(0).setPreferredWidth(150);
        jtProdutos.getColumnModel().getColumn(0).setCellRenderer(centralizado);
        jtProdutos.getColumnModel().getColumn(0).setResizable(true);
        jtProdutos.getColumnModel().getColumn(1).setPreferredWidth(250);
        jtProdutos.getColumnModel().getColumn(1).setCellRenderer(esquerda);
        jtProdutos.getColumnModel().getColumn(1).setResizable(true);
        jtProdutos.getColumnModel().getColumn(2).setPreferredWidth(200);
        jtProdutos.getColumnModel().getColumn(2).setResizable(true);
        jtProdutos.getColumnModel().getColumn(3).setPreferredWidth(200);
        jtProdutos.getColumnModel().getColumn(3).setResizable(true);
        jtProdutos.getColumnModel().getColumn(4).setPreferredWidth(85);
        jtProdutos.getColumnModel().getColumn(4).setCellRenderer(centralizado);
        jtProdutos.getColumnModel().getColumn(4).setResizable(true);
        jtProdutos.getColumnModel().getColumn(5).setPreferredWidth(85);
        jtProdutos.getColumnModel().getColumn(5).setCellRenderer(direita);
        jtProdutos.getColumnModel().getColumn(5).setResizable(true);
        jtProdutos.getColumnModel().getColumn(6).setPreferredWidth(85);
        jtProdutos.getColumnModel().getColumn(6).setCellRenderer(direita);
        jtProdutos.getColumnModel().getColumn(6).setResizable(true);

        jtProdutos.getTableHeader().setReorderingAllowed(true);
        jtProdutos.setAutoResizeMode(jtProdutos.AUTO_RESIZE_OFF);
        jtProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        obc.fechaConexao();
    }

    public void preencherTabela(String sql) throws IOException, BiffException {
        OperacaoBancoProdutos obc = new OperacaoBancoProdutos();
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"COD", "DESCRIÇÃO", "CATEGORIA", "MARCA",
            "UNIDADE", "QTD EMB", "ESTOQUE"};
        obc.obterConexao();
        obc.executaSql(sql);

        try {
            obc.rs.first();

            do {
                dados.add(new Object[]{
                    obc.rs.getLong("cpcodpro"),
                    obc.rs.getString("descricao"),
                    obc.rs.getString("categoria"),
                    obc.rs.getString("marca"),
                    obc.rs.getString("unidade"),
                    obc.rs.getInt("qtdun"),
                    obc.rs.getInt("Estoque")
                });
            } while (obc.rs.next());
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(rootPane, "Nenhum cliente cadastrado no momento");
        }

        ModelTabela modelo = new ModelTabela(dados, colunas);
        DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        DefaultTableCellRenderer direita = new DefaultTableCellRenderer();
        direita.setHorizontalAlignment(SwingConstants.RIGHT);
        esquerda.setHorizontalAlignment(SwingConstants.LEFT);
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);

        jtProdutos.setModel((TableModel) modelo);
        jtProdutos.getColumnModel().getColumn(0).setPreferredWidth(150);
        jtProdutos.getColumnModel().getColumn(0).setCellRenderer(centralizado);
        jtProdutos.getColumnModel().getColumn(0).setResizable(true);
        jtProdutos.getColumnModel().getColumn(1).setPreferredWidth(250);
        jtProdutos.getColumnModel().getColumn(1).setCellRenderer(esquerda);
        jtProdutos.getColumnModel().getColumn(1).setResizable(true);
        jtProdutos.getColumnModel().getColumn(2).setPreferredWidth(200);
        jtProdutos.getColumnModel().getColumn(2).setResizable(true);
        jtProdutos.getColumnModel().getColumn(3).setPreferredWidth(200);
        jtProdutos.getColumnModel().getColumn(3).setResizable(true);
        jtProdutos.getColumnModel().getColumn(4).setPreferredWidth(85);
        jtProdutos.getColumnModel().getColumn(4).setCellRenderer(centralizado);
        jtProdutos.getColumnModel().getColumn(4).setResizable(true);
        jtProdutos.getColumnModel().getColumn(5).setPreferredWidth(85);
        jtProdutos.getColumnModel().getColumn(5).setCellRenderer(direita);
        jtProdutos.getColumnModel().getColumn(5).setResizable(true);
        jtProdutos.getColumnModel().getColumn(6).setPreferredWidth(85);
        jtProdutos.getColumnModel().getColumn(6).setCellRenderer(direita);
        jtProdutos.getColumnModel().getColumn(6).setResizable(true);

        jtProdutos.getTableHeader().setReorderingAllowed(true);
        jtProdutos.setAutoResizeMode(jtProdutos.AUTO_RESIZE_OFF);
        jtProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        obc.fechaConexao();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jbSelecionarProdutos = new javax.swing.JButton();
        jbImportar = new javax.swing.JButton();
        jtfCaminhoProdutos = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtProdutos = new javax.swing.JTable();
        jtfCodigo = new javax.swing.JTextField();
        jtfDescricao = new javax.swing.JTextField();
        jtfCategoria = new javax.swing.JTextField();
        jtfMarca = new javax.swing.JTextField();
        jtfQtdemb = new javax.swing.JTextField();
        jtfEstoque = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jtfUn = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jlLogomarca = new javax.swing.JLabel();
        jlNomeTela = new javax.swing.JLabel();
        jtfPesquisaDesc = new javax.swing.JTextField();
        jtfPesquisaCod = new javax.swing.JTextField();
        jtfPesquisaCategoria = new javax.swing.JTextField();
        jtfPesquisaMarca = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jlFundo = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Produtos - Movimento XML - Versão 1.0");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jbSelecionarProdutos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENS/botão selecionar.png"))); // NOI18N
        jbSelecionarProdutos.setFocusable(false);
        jbSelecionarProdutos.setOpaque(false);
        jbSelecionarProdutos.setRequestFocusEnabled(false);
        jbSelecionarProdutos.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENS/botão selecionar II.png"))); // NOI18N
        jbSelecionarProdutos.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENS/botão selecionar II.png"))); // NOI18N
        jbSelecionarProdutos.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENS/botão selecionar II.png"))); // NOI18N
        jbSelecionarProdutos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSelecionarProdutosActionPerformed(evt);
            }
        });
        getContentPane().add(jbSelecionarProdutos, new org.netbeans.lib.awtextra.AbsoluteConstraints(369, 11, 110, 30));

        jbImportar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENS/botão importar.png"))); // NOI18N
        jbImportar.setBorderPainted(false);
        jbImportar.setFocusable(false);
        jbImportar.setOpaque(false);
        jbImportar.setRequestFocusEnabled(false);
        jbImportar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENS/botão importar II.png"))); // NOI18N
        jbImportar.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENS/botão importar II.png"))); // NOI18N
        jbImportar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENS/botão importar II.png"))); // NOI18N
        jbImportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbImportarActionPerformed(evt);
            }
        });
        getContentPane().add(jbImportar, new org.netbeans.lib.awtextra.AbsoluteConstraints(497, 11, 110, 30));

        jtfCaminhoProdutos.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jtfCaminhoProdutos.setEnabled(false);
        jtfCaminhoProdutos.setOpaque(false);
        getContentPane().add(jtfCaminhoProdutos, new org.netbeans.lib.awtextra.AbsoluteConstraints(369, 59, 543, -1));

        jScrollPane2.setAutoscrolls(true);
        jScrollPane2.setOpaque(false);

        jtProdutos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jtProdutos.setGridColor(new java.awt.Color(204, 204, 204));
        jtProdutos.setInheritsPopupMenu(true);
        jtProdutos.setOpaque(false);
        jtProdutos.setRowHeight(20);
        jtProdutos.setSelectionBackground(new java.awt.Color(255, 102, 0));
        jtProdutos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtProdutosMouseClicked(evt);
            }
        });
        jtProdutos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtProdutosKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtProdutosKeyTyped(evt);
            }
        });
        jScrollPane2.setViewportView(jtProdutos);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 167, 858, 350));

        jtfCodigo.setEnabled(false);
        jtfCodigo.setFocusable(false);
        jtfCodigo.setOpaque(false);
        getContentPane().add(jtfCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 112, 105, -1));

        jtfDescricao.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtfDescricao.setEnabled(false);
        jtfDescricao.setFocusable(false);
        jtfDescricao.setOpaque(false);
        getContentPane().add(jtfDescricao, new org.netbeans.lib.awtextra.AbsoluteConstraints(165, 112, 207, -1));

        jtfCategoria.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfCategoria.setEnabled(false);
        jtfCategoria.setFocusable(false);
        jtfCategoria.setOpaque(false);
        getContentPane().add(jtfCategoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(378, 112, 155, -1));

        jtfMarca.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfMarca.setEnabled(false);
        jtfMarca.setFocusable(false);
        jtfMarca.setOpaque(false);
        getContentPane().add(jtfMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(539, 112, 164, -1));

        jtfQtdemb.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfQtdemb.setEnabled(false);
        jtfQtdemb.setFocusable(false);
        jtfQtdemb.setOpaque(false);
        getContentPane().add(jtfQtdemb, new org.netbeans.lib.awtextra.AbsoluteConstraints(709, 112, 53, -1));

        jtfEstoque.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfEstoque.setEnabled(false);
        jtfEstoque.setFocusable(false);
        jtfEstoque.setOpaque(false);
        getContentPane().add(jtfEstoque, new org.netbeans.lib.awtextra.AbsoluteConstraints(827, 112, 85, -1));

        jLabel1.setText("Código");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 92, -1, -1));

        jLabel2.setText("Descrição");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(165, 92, -1, -1));

        jLabel3.setText("Categoria");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(378, 92, -1, -1));

        jLabel4.setText("Marca");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(539, 92, -1, -1));

        jtfUn.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfUn.setEnabled(false);
        jtfUn.setFocusable(false);
        jtfUn.setOpaque(false);
        getContentPane().add(jtfUn, new org.netbeans.lib.awtextra.AbsoluteConstraints(768, 112, 53, -1));

        jLabel5.setText("Qtd Emb");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(709, 92, -1, -1));

        jLabel6.setText("Un");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(768, 92, -1, -1));

        jLabel7.setText("Estoque");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(827, 92, -1, -1));

        jlLogomarca.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlLogomarca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENS/logomarca II.png"))); // NOI18N
        getContentPane().add(jlLogomarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 240, 90));

        jlNomeTela.setFont(new java.awt.Font("Franklin Gothic Heavy", 0, 36)); // NOI18N
        jlNomeTela.setForeground(new java.awt.Color(153, 51, 0));
        jlNomeTela.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlNomeTela.setText("Produtos");
        getContentPane().add(jlNomeTela, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 10, -1, -1));

        jtfPesquisaDesc.setBackground(new java.awt.Color(255, 255, 255));
        jtfPesquisaDesc.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtfPesquisaDesc.setOpaque(false);
        jtfPesquisaDesc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfPesquisaDescKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfPesquisaDescKeyTyped(evt);
            }
        });
        getContentPane().add(jtfPesquisaDesc, new org.netbeans.lib.awtextra.AbsoluteConstraints(165, 140, 207, -1));

        jtfPesquisaCod.setBackground(new java.awt.Color(255, 255, 255));
        jtfPesquisaCod.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtfPesquisaCod.setOpaque(false);
        jtfPesquisaCod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfPesquisaCodKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfPesquisaCodKeyTyped(evt);
            }
        });
        getContentPane().add(jtfPesquisaCod, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 140, 105, -1));

        jtfPesquisaCategoria.setBackground(new java.awt.Color(255, 255, 255));
        jtfPesquisaCategoria.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtfPesquisaCategoria.setOpaque(false);
        jtfPesquisaCategoria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfPesquisaCategoriaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfPesquisaCategoriaKeyTyped(evt);
            }
        });
        getContentPane().add(jtfPesquisaCategoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(378, 140, 155, -1));

        jtfPesquisaMarca.setBackground(new java.awt.Color(255, 255, 255));
        jtfPesquisaMarca.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtfPesquisaMarca.setOpaque(false);
        jtfPesquisaMarca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfPesquisaMarcaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfPesquisaMarcaKeyTyped(evt);
            }
        });
        getContentPane().add(jtfPesquisaMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(539, 140, 164, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel8.setText("Digite nos campos ao lado para Filtrar a tabela");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 140, 200, 20));

        jLabel9.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel9.setText("Ctrl+O: Selecionar | Ctrl+I: Importar");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 519, 430, 30));

        jlFundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENS/fundo padrão II.png"))); // NOI18N
        getContentPane().add(jlFundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1130, 570));

        jMenu2.setText("Opções");

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem5.setText("Selecionar");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem5);

        jMenuItem6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem6.setText("Importar");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem6);

        jMenuItem7.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        jMenuItem7.setText("Sair");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem7);

        jMenuBar1.add(jMenu2);

        jMenu1.setText("Relatórios");

        jMenuItem1.setText("321 - Lista de Produtos");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("322 - Relatório de Produtos por Marca");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("323 - Relatório de Produtos por Categoria");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem4.setText("324 - Relatório de Produtos por Marca e Categoria");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        setSize(new java.awt.Dimension(978, 614));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbSelecionarProdutosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSelecionarProdutosActionPerformed
        selecionar();
    }//GEN-LAST:event_jbSelecionarProdutosActionPerformed

    public void selecionar() {
        JFileChooser buscaArquivo = new JFileChooser();
        //buscaArquivo.setFileFilter(new FileNameExtensionFilter("CSV"));
        buscaArquivo.setAcceptAllFileFilterUsed(false);
        buscaArquivo.setDialogTitle("Buscar Arquivo");
        buscaArquivo.showOpenDialog(this);

        caminhoProdutos = "" + buscaArquivo.getSelectedFile().getAbsolutePath();
        jtfCaminhoProdutos.setText(caminhoProdutos);
    }

    private void jbImportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbImportarActionPerformed
        importar();
    }//GEN-LAST:event_jbImportarActionPerformed

    public void importar() {
        AUXILIAR.AuxiliarControleAcesso aux = new AuxiliarControleAcesso();
        try {
            if (transferedados.getText().equals("ADM") || aux.controle(this.codigousuario, IMPORTAR_PRODUTOS) == true) {
                Date data = new Date();
                final SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                String inicio = format.format(new Date().getTime());
                String fim;

                if (jtfCaminhoProdutos.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Selecione o arquivo para importação!!!");
                } else {

                    TelaCarregando tela = new TelaCarregando();
                    tela.setVisible(true);

                    Thread t = new Thread() {
                        @Override
                        public void run() {

                            try {
                                lerArquivo(caminhoProdutos);
                            } catch (IOException ex) {
                                Logger.getLogger(TelaProdutos.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (BiffException ex) {
                                Logger.getLogger(TelaProdutos.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            tela.dispose();

                            try {
                                preencherTabela("SELECT * FROM produtos");
                            } catch (IOException ex) {
                                Logger.getLogger(TelaProdutos.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (BiffException ex) {
                                Logger.getLogger(TelaProdutos.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            JOptionPane.showMessageDialog(null, "IMPORTAÇÃO FINALIZADA\n Registros realizados: " + contaregistros
                                    + "\nInício: " + inicio + "\nTermino: " + format.format(new Date().getTime()));
                            /*preencherTabela("select * FROM produtos");*/
                            jtfCaminhoProdutos.setText("");
                            contaregistros = 0;
                        }
                    };

                    t.start();
                }

                AUXILIAR.AuxiliarLog log = new AuxiliarLog();
                try {
                    log.log(transferedados.getText(), "PRODUTOS", "IMPORTAR", Long.parseLong("777777"));
                } catch (IOException ex) {
                    Logger.getLogger(TelaProdutos.class.getName()).log(Level.SEVERE, null, ex);
                } catch (BiffException ex) {
                    Logger.getLogger(TelaProdutos.class.getName()).log(Level.SEVERE, null, ex);
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

    private void jtProdutosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtProdutosMouseClicked
        jtfCodigo.setText(jtProdutos.getValueAt(jtProdutos.getSelectedRow(), 0).toString());
        jtfDescricao.setText(jtProdutos.getValueAt(jtProdutos.getSelectedRow(), 1).toString());
        jtfCategoria.setText(jtProdutos.getValueAt(jtProdutos.getSelectedRow(), 2).toString());
        jtfMarca.setText(jtProdutos.getValueAt(jtProdutos.getSelectedRow(), 3).toString());
        jtfUn.setText(jtProdutos.getValueAt(jtProdutos.getSelectedRow(), 4).toString());
        jtfQtdemb.setText(jtProdutos.getValueAt(jtProdutos.getSelectedRow(), 5).toString());
        jtfEstoque.setText(jtProdutos.getValueAt(jtProdutos.getSelectedRow(), 6).toString());
    }//GEN-LAST:event_jtProdutosMouseClicked

    private void jtProdutosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtProdutosKeyTyped

    }//GEN-LAST:event_jtProdutosKeyTyped

    private void jtfPesquisaDescKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfPesquisaDescKeyTyped

    }//GEN-LAST:event_jtfPesquisaDescKeyTyped

    private void jtfPesquisaCodKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfPesquisaCodKeyTyped

    }//GEN-LAST:event_jtfPesquisaCodKeyTyped

    private void jtfPesquisaCategoriaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfPesquisaCategoriaKeyTyped

    }//GEN-LAST:event_jtfPesquisaCategoriaKeyTyped

    private void jtfPesquisaMarcaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfPesquisaMarcaKeyTyped

    }//GEN-LAST:event_jtfPesquisaMarcaKeyTyped

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed

        AUXILIAR.AuxiliarControleAcesso aux = new AuxiliarControleAcesso();
        try {
            if (transferedados.getText().equals("ADM") || aux.controle(this.codigousuario, EMITIR_RELATORIOS) == true) {
                OperacaoBancoProdutos ob = new OperacaoBancoProdutos();
                ob.obterConexao();

                String src = "Jasper/relatorioListaProdutos.jasper";
                JasperPrint jasperPrint = null;
                try {
                    jasperPrint = JasperFillManager.fillReport(src, null, ob.obterConexao());
                } catch (JRException ex) {
                    System.out.println("ERRO: " + ex.getMessage());
                }

                JasperViewer view = new JasperViewer(jasperPrint, false);
                view.setVisible(true);
                ob.fechaConexao();

                AUXILIAR.AuxiliarLog log = new AuxiliarLog();
                try {
                    log.log(transferedados.getText(), "PRODUTOS", "RELATÓRIO", Long.parseLong("321"));
                } catch (IOException ex) {
                    Logger.getLogger(TelaClientes.class.getName()).log(Level.SEVERE, null, ex);
                } catch (BiffException ex) {
                    Logger.getLogger(TelaClientes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(TelaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(TelaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TelaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed

        AUXILIAR.AuxiliarControleAcesso aux = new AuxiliarControleAcesso();
        try {
            if (transferedados.getText().equals("ADM") || aux.controle(this.codigousuario, EMITIR_RELATORIOS) == true) {
                TelaParamProdutosMarca tela = new TelaParamProdutosMarca(this.transferedados);
                tela.setVisible(true);
            }
        } catch (IOException ex) {
            Logger.getLogger(TelaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(TelaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TelaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        TelaParamProdutosCategoria tela;
        AUXILIAR.AuxiliarControleAcesso aux = new AuxiliarControleAcesso();
        try {
            if (transferedados.getText().equals("ADM") || aux.controle(this.codigousuario, EMITIR_RELATORIOS) == true) {
                tela = new TelaParamProdutosCategoria(this.transferedados);
                tela.setVisible(true);
            }
        } catch (IOException ex) {
            Logger.getLogger(TelaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(TelaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TelaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed

        AUXILIAR.AuxiliarControleAcesso aux = new AuxiliarControleAcesso();
        try {
            if (transferedados.getText().equals("ADM") || aux.controle(this.codigousuario, EMITIR_RELATORIOS) == true) {
                TelaParamProdutosMarcaCategoria tela = new TelaParamProdutosMarcaCategoria(this.transferedados);
                tela.setVisible(true);
            }
        } catch (IOException ex) {
            Logger.getLogger(TelaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(TelaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TelaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        selecionar();
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        importar();
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jtProdutosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtProdutosKeyReleased
        jtfCodigo.setText(jtProdutos.getValueAt(jtProdutos.getSelectedRow(), 0).toString());
        jtfDescricao.setText(jtProdutos.getValueAt(jtProdutos.getSelectedRow(), 1).toString());
        jtfCategoria.setText(jtProdutos.getValueAt(jtProdutos.getSelectedRow(), 2).toString());
        jtfMarca.setText(jtProdutos.getValueAt(jtProdutos.getSelectedRow(), 3).toString());
        jtfUn.setText(jtProdutos.getValueAt(jtProdutos.getSelectedRow(), 4).toString());
        jtfQtdemb.setText(jtProdutos.getValueAt(jtProdutos.getSelectedRow(), 5).toString());
        jtfEstoque.setText(jtProdutos.getValueAt(jtProdutos.getSelectedRow(), 6).toString());
    }//GEN-LAST:event_jtProdutosKeyReleased


    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
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
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jtfPesquisaCodKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfPesquisaCodKeyReleased
        String pesquisar = jtfPesquisaCod.getText().toUpperCase();
        String campoBanco = "cpcodpro";
        try {
            preecherTabelaPesquisa(pesquisar, campoBanco);
        } catch (IOException ex) {
            Logger.getLogger(TelaProdutos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(TelaProdutos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jtfPesquisaCodKeyReleased

    private void jtfPesquisaDescKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfPesquisaDescKeyReleased
                String pesquisar = jtfPesquisaDesc.getText().toUpperCase();
        jtfPesquisaDesc.setText(pesquisar);
        String campoBanco = "descricao";
        try {
            preecherTabelaPesquisa(pesquisar, campoBanco);
        } catch (IOException ex) {
            Logger.getLogger(TelaProdutos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(TelaProdutos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jtfPesquisaDescKeyReleased

    private void jtfPesquisaCategoriaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfPesquisaCategoriaKeyReleased
                String pesquisar = jtfPesquisaCategoria.getText().toUpperCase();
        jtfPesquisaCategoria.setText(pesquisar);
        String campoBanco = "categoria";
        try {
            preecherTabelaPesquisa(pesquisar, campoBanco);
        } catch (IOException ex) {
            Logger.getLogger(TelaProdutos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(TelaProdutos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jtfPesquisaCategoriaKeyReleased

    private void jtfPesquisaMarcaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfPesquisaMarcaKeyReleased
                String pesquisar = jtfPesquisaMarca.getText().toUpperCase();
        jtfPesquisaMarca.setText(pesquisar);
        String campoBanco = "marca";
        try {
            preecherTabelaPesquisa(pesquisar, campoBanco);
        } catch (IOException ex) {
            Logger.getLogger(TelaProdutos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(TelaProdutos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jtfPesquisaMarcaKeyReleased

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
            java.util.logging.Logger.getLogger(TelaProdutos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaProdutos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaProdutos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaProdutos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new TelaProdutos().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(TelaProdutos.class.getName()).log(Level.SEVERE, null, ex);
                } catch (BiffException ex) {
                    Logger.getLogger(TelaProdutos.class.getName()).log(Level.SEVERE, null, ex);
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
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbImportar;
    private javax.swing.JButton jbSelecionarProdutos;
    private javax.swing.JLabel jlFundo;
    private javax.swing.JLabel jlLogomarca;
    private javax.swing.JLabel jlNomeTela;
    private javax.swing.JTable jtProdutos;
    private javax.swing.JTextField jtfCaminhoProdutos;
    private javax.swing.JTextField jtfCategoria;
    private javax.swing.JTextField jtfCodigo;
    private javax.swing.JTextField jtfDescricao;
    private javax.swing.JTextField jtfEstoque;
    private javax.swing.JTextField jtfMarca;
    private javax.swing.JTextField jtfPesquisaCategoria;
    private javax.swing.JTextField jtfPesquisaCod;
    private javax.swing.JTextField jtfPesquisaDesc;
    private javax.swing.JTextField jtfPesquisaMarca;
    private javax.swing.JTextField jtfQtdemb;
    private javax.swing.JTextField jtfUn;
    // End of variables declaration//GEN-END:variables
}
