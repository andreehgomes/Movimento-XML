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
import OperacaoBanco.OperacaoBancoClientes;
import OperacaoBanco.OperacaoBancoMovimento;
import OperacaoBanco.OperacaoBancoProdutos;
import OperacaoBanco.OperacaoBancoProdutos;
import OperacaoBanco.OperacaoBancoXml;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
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

public class TelaMovimento extends javax.swing.JFrame {

    final static int GERAR_ARQUIVO = 61;
    final static int EMITIR_RELATORIO = 62;

    JLabel transferirdados;
    int codigousuario;
    //JButton produtos;

    public TelaMovimento() throws IOException, BiffException {
        initComponents();
        preencherTabela("SELECT * FROM produtos");
    }

    public TelaMovimento(JLabel transferirdados, int codigousuario) throws IOException, BiffException {
        initComponents();
        this.transferirdados = transferirdados;
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
        jlAvisoPesquisa.setText("");
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
                    obc.rs.getInt("cpcodpro"),
                    obc.rs.getString("descricao"),
                    obc.rs.getString("categoria"),
                    obc.rs.getString("marca"),
                    obc.rs.getString("unidade"),
                    obc.rs.getInt("qtdun"),
                    obc.rs.getInt("Estoque")
                });
            } while (obc.rs.next());
        } catch (SQLException ex) {
            jlAvisoPesquisa.setText("NENHUM PRODUTO ECONTRADO COM OS FILTROS APRESENTADOS");
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
        jtProdutos.getColumnModel().getColumn(6).setPreferredWidth(80);
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
                    obc.rs.getInt("cpcodpro"),
                    obc.rs.getString("descricao"),
                    obc.rs.getString("categoria"),
                    obc.rs.getString("marca"),
                    obc.rs.getString("unidade"),
                    obc.rs.getInt("qtdun"),
                    obc.rs.getInt("Estoque")
                });
            } while (obc.rs.next());
        } catch (SQLException ex) {
            //System.out.println("Nenhum produto cadastrado no momento");
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
        jtProdutos.getColumnModel().getColumn(6).setPreferredWidth(80);
        jtProdutos.getColumnModel().getColumn(6).setCellRenderer(direita);
        jtProdutos.getColumnModel().getColumn(6).setResizable(true);

        jtProdutos.getTableHeader().setReorderingAllowed(true);
        jtProdutos.setAutoResizeMode(jtProdutos.AUTO_RESIZE_OFF);
        jtProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        obc.fechaConexao();
    }

    public void preencherTabelaMovimento(String sql) throws IOException, BiffException {
        OperacaoBancoXml obc = new OperacaoBancoXml();
        OperacaoBancoClientes opbc = new OperacaoBancoClientes();
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"CODIGO", "DESCRIÇÃO", "UN", "QUANTIDADE",
            "MARCA", "CLIENTE", "CFOP", "TIPO MOVIMENTO", "ESTOQUE", "DATA", "XML"};
        obc.obterConexao();
        obc.executaSql(sql);

        try {
            obc.rs.first();
            do {
                dados.add(new Object[]{
                    obc.rs.getLong("cpcodpro"),
                    obc.rs.getString("descricao"),
                    obc.rs.getString("unproduto"),
                    obc.rs.getFloat("qtduntotal"),
                    obc.rs.getString("categoria"),
                    obc.rs.getInt("cecodcli"),
                    obc.rs.getInt("cfop"),
                    obc.rs.getString("tipomovimento"),
                    obc.rs.getInt("estoquenoregistro"),
                    obc.rs.getDate("dtmovimento"),
                    obc.rs.getInt("cecodigoxml")

                });
            } while (obc.rs.next());
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(rootPane, "Nenhum XML cadastrado no momento");
        }

        ModelTabela modelo = new ModelTabela(dados, colunas);
        jtMovimento.setModel((TableModel) modelo);
        cormovimento();

        jtMovimento.getColumnModel().getColumn(0).setPreferredWidth(90);
        jtMovimento.getColumnModel().getColumn(0).setResizable(true);
        jtMovimento.getColumnModel().getColumn(1).setPreferredWidth(280);
        jtMovimento.getColumnModel().getColumn(1).setResizable(true);
        jtMovimento.getColumnModel().getColumn(2).setPreferredWidth(70);
        jtMovimento.getColumnModel().getColumn(2).setResizable(true);
        jtMovimento.getColumnModel().getColumn(3).setPreferredWidth(90);
        jtMovimento.getColumnModel().getColumn(3).setResizable(true);
        jtMovimento.getColumnModel().getColumn(4).setPreferredWidth(120);
        jtMovimento.getColumnModel().getColumn(4).setResizable(true);
        jtMovimento.getColumnModel().getColumn(5).setPreferredWidth(120);
        jtMovimento.getColumnModel().getColumn(5).setResizable(true);
        jtMovimento.getColumnModel().getColumn(6).setPreferredWidth(120);
        jtMovimento.getColumnModel().getColumn(6).setResizable(true);
        jtMovimento.getColumnModel().getColumn(7).setPreferredWidth(90);
        jtMovimento.getColumnModel().getColumn(7).setResizable(true);
        jtMovimento.getColumnModel().getColumn(8).setPreferredWidth(120);
        jtMovimento.getColumnModel().getColumn(8).setResizable(true);
        jtMovimento.getColumnModel().getColumn(9).setPreferredWidth(90);
        jtMovimento.getColumnModel().getColumn(9).setResizable(true);
        jtMovimento.getColumnModel().getColumn(10).setPreferredWidth(90);
        jtMovimento.getColumnModel().getColumn(10).setResizable(true);

        jtMovimento.getTableHeader().setReorderingAllowed(false);
        jtMovimento.setAutoResizeMode(jtMovimento.AUTO_RESIZE_OFF);
        //jtMovimento.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        obc.fechaConexao();

    }

    public void cormovimento() {

        jtMovimento.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                Object CLASS;
                CLASS = jtMovimento.getValueAt(row, 7);

                if (CLASS != null && CLASS.equals("ENTRADA")) {
                    jtMovimento.setForeground(Color.BLUE);
                    //jtMovimento.setBackground(Color.GREEN);

                } else if (CLASS != null && CLASS.equals("SAÍDA")) {
                    jtMovimento.setForeground(Color.RED);
                    //jtMovimento.setBackground(Color.CYAN);

                } else {
                    jtMovimento.setForeground(Color.BLACK);
                }

                return this;
            }
        }
        );
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
        jtfPesquisaDesc = new javax.swing.JTextField();
        jtfPesquisaCod = new javax.swing.JTextField();
        jtfPesquisaCategoria = new javax.swing.JTextField();
        jtfPesquisaMarca = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jlAvisoPesquisa = new javax.swing.JLabel();
        jlDe = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jbLimparFiltro = new javax.swing.JButton();
        jbFiltrar = new javax.swing.JButton();
        jtfAte = new javax.swing.JFormattedTextField();
        jtfDe = new javax.swing.JFormattedTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        jtMovimento = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jlFundo = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jmRelatorio = new javax.swing.JMenu();
        jmRelPorData = new javax.swing.JMenuItem();
        jmRelProdData = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jmOpcoes = new javax.swing.JMenu();
        jmExportarArqAndroid = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Movimento - Movimento XML - Versão 1.0");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane2.setAutoscrolls(true);
        jScrollPane2.setOpaque(false);

        jtProdutos.setForeground(new java.awt.Color(51, 51, 51));
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
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtProdutosKeyTyped(evt);
            }
        });
        jScrollPane2.setViewportView(jtProdutos);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 1070, 180));

        jtfCodigo.setEnabled(false);
        jtfCodigo.setFocusable(false);
        jtfCodigo.setOpaque(false);
        getContentPane().add(jtfCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 30, 105, -1));

        jtfDescricao.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtfDescricao.setEnabled(false);
        jtfDescricao.setFocusable(false);
        jtfDescricao.setOpaque(false);
        getContentPane().add(jtfDescricao, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 30, 207, -1));

        jtfCategoria.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfCategoria.setEnabled(false);
        jtfCategoria.setFocusable(false);
        jtfCategoria.setOpaque(false);
        getContentPane().add(jtfCategoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 30, 155, -1));

        jtfMarca.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfMarca.setEnabled(false);
        jtfMarca.setFocusable(false);
        jtfMarca.setOpaque(false);
        getContentPane().add(jtfMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 30, 164, -1));

        jtfQtdemb.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfQtdemb.setEnabled(false);
        jtfQtdemb.setFocusable(false);
        jtfQtdemb.setOpaque(false);
        getContentPane().add(jtfQtdemb, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 30, 53, -1));

        jtfEstoque.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfEstoque.setEnabled(false);
        jtfEstoque.setFocusable(false);
        jtfEstoque.setOpaque(false);
        getContentPane().add(jtfEstoque, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 30, 80, -1));

        jLabel1.setText("Código");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, -1, -1));

        jLabel2.setText("Descrição");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, -1, -1));

        jLabel3.setText("Categoria");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 10, -1, -1));

        jLabel4.setText("Marca");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 10, -1, -1));

        jtfUn.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfUn.setEnabled(false);
        jtfUn.setFocusable(false);
        jtfUn.setOpaque(false);
        getContentPane().add(jtfUn, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 30, 53, -1));

        jLabel5.setText("Qtd Emb");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 10, -1, -1));

        jLabel6.setText("Un");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 10, -1, -1));

        jLabel7.setText("Estoque");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 10, -1, -1));

        jlLogomarca.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlLogomarca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENS/logomarca II.png"))); // NOI18N
        getContentPane().add(jlLogomarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 230, 90));

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
        getContentPane().add(jtfPesquisaDesc, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 60, 207, -1));

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
        getContentPane().add(jtfPesquisaCod, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 60, 105, -1));

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
        getContentPane().add(jtfPesquisaCategoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 60, 155, -1));

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
        getContentPane().add(jtfPesquisaMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 60, 164, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel8.setText("Digite nos campos ao lado para Filtrar a tabela");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 60, 200, 20));

        jlAvisoPesquisa.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jlAvisoPesquisa.setForeground(new java.awt.Color(204, 51, 0));
        getContentPane().add(jlAvisoPesquisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 95, 650, 10));

        jlDe.setText("De:");
        getContentPane().add(jlDe, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 300, 30, 20));

        jLabel10.setText("Até:");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 300, 30, 20));

        jbLimparFiltro.setText("Limpar Filtro");
        jbLimparFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbLimparFiltroActionPerformed(evt);
            }
        });
        getContentPane().add(jbLimparFiltro, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 300, 120, -1));

        jbFiltrar.setText("Filtrar");
        jbFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbFiltrarActionPerformed(evt);
            }
        });
        getContentPane().add(jbFiltrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 300, 110, -1));

        jtfAte.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));
        jtfAte.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfAte.setToolTipText("Data Fim (--/--/----)");
        getContentPane().add(jtfAte, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 300, 120, 20));

        jtfDe.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));
        jtfDe.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfDe.setToolTipText("Data início (--/--/----)");
        getContentPane().add(jtfDe, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 300, 120, 20));

        jScrollPane4.setBackground(new java.awt.Color(204, 204, 204));
        jScrollPane4.setOpaque(false);

        jtMovimento.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jtMovimento.setGridColor(new java.awt.Color(204, 204, 204));
        jtMovimento.setInheritsPopupMenu(true);
        jtMovimento.setOpaque(false);
        jtMovimento.setRowHeight(20);
        jtMovimento.setSelectionBackground(new java.awt.Color(255, 102, 0));
        jScrollPane4.setViewportView(jtMovimento);

        getContentPane().add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, 1070, 240));

        jLabel9.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel9.setText("Ctrl+E: Exportar arquivo de importação Android");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 589, 370, 30));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENS/legenda.png"))); // NOI18N
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 580, 130, 70));

        jlFundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENS/fundo padrão III.png"))); // NOI18N
        getContentPane().add(jlFundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -240, 1130, 910));

        jmRelatorio.setText("Relatórios");

        jmRelPorData.setText("611 - Movimento por Data");
        jmRelPorData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmRelPorDataActionPerformed(evt);
            }
        });
        jmRelatorio.add(jmRelPorData);

        jmRelProdData.setText("612 - Movimento por Produto e Data");
        jmRelProdData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmRelProdDataActionPerformed(evt);
            }
        });
        jmRelatorio.add(jmRelProdData);

        jMenuItem3.setText("613 - Relatório de Estoque Geral");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jmRelatorio.add(jMenuItem3);

        jMenuBar1.add(jmRelatorio);

        jmOpcoes.setText("Opções");

        jmExportarArqAndroid.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        jmExportarArqAndroid.setText("Gerar arquivo de importação Android");
        jmExportarArqAndroid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmExportarArqAndroidActionPerformed(evt);
            }
        });
        jmOpcoes.add(jmExportarArqAndroid);

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        jMenuItem1.setText("Sair");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jmOpcoes.add(jMenuItem1);

        jMenuBar1.add(jmOpcoes);

        setJMenuBar(jMenuBar1);

        setSize(new java.awt.Dimension(1135, 709));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    private void jtProdutosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtProdutosMouseClicked
        jtfCodigo.setText(jtProdutos.getValueAt(jtProdutos.getSelectedRow(), 0).toString());
        jtfDescricao.setText(jtProdutos.getValueAt(jtProdutos.getSelectedRow(), 1).toString());
        jtfCategoria.setText(jtProdutos.getValueAt(jtProdutos.getSelectedRow(), 2).toString());
        jtfMarca.setText(jtProdutos.getValueAt(jtProdutos.getSelectedRow(), 3).toString());
        jtfUn.setText(jtProdutos.getValueAt(jtProdutos.getSelectedRow(), 4).toString());
        jtfQtdemb.setText(jtProdutos.getValueAt(jtProdutos.getSelectedRow(), 5).toString());
        jtfEstoque.setText(jtProdutos.getValueAt(jtProdutos.getSelectedRow(), 6).toString());

        int codigoproduto = (int) jtProdutos.getValueAt(jtProdutos.getSelectedRow(), 0);
        try {
            preencherTabelaMovimento("SELECT * FROM produtos, movimento WHERE cpcodpro = "
                    + codigoproduto + ""
                    + " AND (cecodpro = codigofabrica"
                    + " or cecodpro = codigofabrica2"
                    + " or cecodpro = codigofabrica3"
                    + " or cecodpro = codigofabrica4"
                    + " or cecodpro = codigofabrica5 ) ORDER BY movimento.cpregistro;");
            System.out.println("TESTE CODIGO CONSULTA: " + codigoproduto);

        } catch (IOException ex) {
            Logger.getLogger(TelaMovimento.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (BiffException ex) {
            Logger.getLogger(TelaMovimento.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

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

    private void jbFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbFiltrarActionPerformed
        Date de = null;
        Date ate = null;

        try {
            de = new SimpleDateFormat("dd/MM/yyyy").parse(jtfDe.getText());
            ate = new SimpleDateFormat("dd/MM/yyyy").parse(jtfAte.getText());

        } catch (ParseException ex) {
            Logger.getLogger(TelaMovimento.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        Date sqlde = new java.sql.Date(de.getTime());
        Date sqlate = new java.sql.Date(ate.getTime());

        int codigoproduto = (int) jtProdutos.getValueAt(jtProdutos.getSelectedRow(), 0);
        try {
            preencherTabelaMovimento("SELECT * FROM produtos, movimento WHERE cpcodpro = "
                    + codigoproduto + ""
                    + " AND cecodpro = codigofabrica"
                    + " AND dtmovimento BETWEEN '" + sqlde + "' AND '" + sqlate + "'");
            System.out.println("TESTE CODIGO CONSULTA: " + codigoproduto);

        } catch (IOException ex) {
            Logger.getLogger(TelaMovimento.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (BiffException ex) {
            Logger.getLogger(TelaMovimento.class
                    .getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_jbFiltrarActionPerformed

    private void jbLimparFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbLimparFiltroActionPerformed
        jtfDe.setText("");
        jtfAte.setText("");

        int codigoproduto = (int) jtProdutos.getValueAt(jtProdutos.getSelectedRow(), 0);
        try {
            preencherTabelaMovimento("SELECT * FROM produtos, movimento WHERE cpcodpro = "
                    + codigoproduto + ""
                    + " AND (cecodpro = codigofabrica"
                    + " or cecodpro = codigofabrica2"
                    + " or cecodpro = codigofabrica3"
                    + " or cecodpro = codigofabrica4"
                    + " or cecodpro = codigofabrica5 ) ORDER BY movimento.cpregistro;");
            System.out.println("TESTE CODIGO CONSULTA: " + codigoproduto);

        } catch (IOException ex) {
            Logger.getLogger(TelaMovimento.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (BiffException ex) {
            Logger.getLogger(TelaMovimento.class
                    .getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_jbLimparFiltroActionPerformed

    private void jmRelPorDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmRelPorDataActionPerformed

        try {
            AUXILIAR.AuxiliarControleAcesso aux = new AuxiliarControleAcesso();
            if (transferirdados.getText().equals("ADM") || aux.controle(this.codigousuario, EMITIR_RELATORIO) == true) {
                TelaParamMovData tela = new TelaParamMovData(this.transferirdados);
                tela.setVisible(true);

            }
        } catch (IOException ex) {
            Logger.getLogger(TelaMovimento.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (BiffException ex) {
            Logger.getLogger(TelaMovimento.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (SQLException ex) {
            Logger.getLogger(TelaMovimento.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jmRelPorDataActionPerformed

    private void jmRelProdDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmRelProdDataActionPerformed

        try {
            AUXILIAR.AuxiliarControleAcesso aux = new AuxiliarControleAcesso();
            if (transferirdados.getText().equals("ADM") || aux.controle(this.codigousuario, EMITIR_RELATORIO) == true) {
                TelaParamMovProdData tela = new TelaParamMovProdData(this.transferirdados);
                tela.setVisible(true);

            }
        } catch (IOException ex) {
            Logger.getLogger(TelaMovimento.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (BiffException ex) {
            Logger.getLogger(TelaMovimento.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (SQLException ex) {
            Logger.getLogger(TelaMovimento.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jmRelProdDataActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        try {
            AUXILIAR.AuxiliarControleAcesso aux = new AuxiliarControleAcesso();
            if (transferirdados.getText().equals("ADM") || aux.controle(this.codigousuario, EMITIR_RELATORIO) == true) {

                OperacaoBancoProdutos obp = new OperacaoBancoProdutos();
                obp.obterConexao();

                String src = "Jasper/EstoqueGeralProdutos.jasper";
                JasperPrint jasperPrint = null;

                jasperPrint = JasperFillManager.fillReport(src, null, obp.obterConexao());

                JasperViewer view = new JasperViewer(jasperPrint, false);
                view.setVisible(true);
                obp.fechaConexao();

                AUXILIAR.AuxiliarLog log = new AuxiliarLog();
                log.log(transferirdados.getText(), "MOVIMENTO", "RELATÓRIO", Long.parseLong("613"));

            }

        } catch (IOException ex) {
            Logger.getLogger(TelaMovimento.class
                    .getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "ERRO: " + ex.getMessage());

        } catch (BiffException ex) {
            Logger.getLogger(TelaMovimento.class
                    .getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "ERRO: " + ex.getMessage());
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "ERRO: " + ex.getMessage());

        } catch (SQLException ex) {
            Logger.getLogger(TelaMovimento.class
                    .getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "ERRO: " + ex.getMessage());
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jmExportarArqAndroidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmExportarArqAndroidActionPerformed

        try {

            AUXILIAR.AuxiliarControleAcesso aux = new AuxiliarControleAcesso();
            if (transferirdados.getText().equals("ADM") || aux.controle(this.codigousuario, GERAR_ARQUIVO) == true) {

                OperacaoBancoProdutos opb = new OperacaoBancoProdutos();
                ModelProdutos produtos = new ModelProdutos();

                FileWriter log;
                File arquivo = new File("c:\\Anve\\ArquivoImportacao\\ArqImport.txt");

                if (arquivo.exists()) {
                    arquivo.delete();
                    log = new FileWriter(new File("c:\\Anve\\ArquivoImportacao\\ArqImport.txt"), true);
                } else {
                    log = new FileWriter(new File("c:\\Anve\\ArquivoImportacao\\ArqImport.txt"), true);
                }

                PrintWriter gravarLog = new PrintWriter(log);

                opb.obterConexao();
                opb.executaSql("SELECT * FROM produtos");
                while (opb.rs.next()) {

                    gravarLog.printf(
                            String.valueOf(opb.rs.getLong("cpcodpro")) + ";"
                            + String.valueOf(opb.rs.getString("descricao")) + ";"
                            + String.valueOf(opb.rs.getInt("estoque")) + "\n");
                }
                log.close();
                opb.fechaConexao();
                JOptionPane.showMessageDialog(null, "Arquivo gerado com sucesso!");

                AUXILIAR.AuxiliarLog auxlog = new AuxiliarLog();
                auxlog.log(transferirdados.getText(), "MOVIMENTO", "EXPORTAR", Long.parseLong("999999"));

            }

        } catch (IOException ex) {
            Logger.getLogger(TelaMovimento.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (BiffException ex) {
            Logger.getLogger(TelaMovimento.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (SQLException ex) {
            Logger.getLogger(TelaMovimento.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jmExportarArqAndroidActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jtfPesquisaCodKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfPesquisaCodKeyReleased
               String pesquisar = jtfPesquisaCod.getText().toUpperCase();
        String campoBanco = "cpcodpro";
        try {
            preecherTabelaPesquisa(pesquisar, campoBanco);

        } catch (IOException ex) {
            Logger.getLogger(TelaMovimento.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (BiffException ex) {
            Logger.getLogger(TelaMovimento.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jtfPesquisaCodKeyReleased

    private void jtfPesquisaDescKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfPesquisaDescKeyReleased
                String pesquisar = jtfPesquisaDesc.getText().toUpperCase();
        jtfPesquisaDesc.setText(pesquisar);
        String campoBanco = "descricao";
        try {
            preecherTabelaPesquisa(pesquisar, campoBanco);

        } catch (IOException ex) {
            Logger.getLogger(TelaMovimento.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (BiffException ex) {
            Logger.getLogger(TelaMovimento.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jtfPesquisaDescKeyReleased

    private void jtfPesquisaCategoriaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfPesquisaCategoriaKeyReleased
                String pesquisar = jtfPesquisaCategoria.getText().toUpperCase();
        jtfPesquisaCategoria.setText(pesquisar);
        String campoBanco = "categoria";
        try {
            preecherTabelaPesquisa(pesquisar, campoBanco);

        } catch (IOException ex) {
            Logger.getLogger(TelaMovimento.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (BiffException ex) {
            Logger.getLogger(TelaMovimento.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jtfPesquisaCategoriaKeyReleased

    private void jtfPesquisaMarcaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfPesquisaMarcaKeyReleased
                String pesquisar = jtfPesquisaMarca.getText().toUpperCase();
        jtfPesquisaMarca.setText(pesquisar);
        String campoBanco = "marca";
        try {
            preecherTabelaPesquisa(pesquisar, campoBanco);

        } catch (IOException ex) {
            Logger.getLogger(TelaMovimento.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (BiffException ex) {
            Logger.getLogger(TelaMovimento.class
                    .getName()).log(Level.SEVERE, null, ex);
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
            java.util.logging.Logger.getLogger(TelaMovimento.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaMovimento.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaMovimento.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaMovimento.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new TelaMovimento().setVisible(true);

                } catch (IOException ex) {
                    Logger.getLogger(TelaMovimento.class
                            .getName()).log(Level.SEVERE, null, ex);

                } catch (BiffException ex) {
                    Logger.getLogger(TelaMovimento.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JButton jbFiltrar;
    private javax.swing.JButton jbLimparFiltro;
    private javax.swing.JLabel jlAvisoPesquisa;
    private javax.swing.JLabel jlDe;
    private javax.swing.JLabel jlFundo;
    private javax.swing.JLabel jlLogomarca;
    private javax.swing.JMenuItem jmExportarArqAndroid;
    private javax.swing.JMenu jmOpcoes;
    private javax.swing.JMenuItem jmRelPorData;
    private javax.swing.JMenuItem jmRelProdData;
    private javax.swing.JMenu jmRelatorio;
    private javax.swing.JTable jtMovimento;
    private javax.swing.JTable jtProdutos;
    private javax.swing.JFormattedTextField jtfAte;
    private javax.swing.JTextField jtfCategoria;
    private javax.swing.JTextField jtfCodigo;
    private javax.swing.JFormattedTextField jtfDe;
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
