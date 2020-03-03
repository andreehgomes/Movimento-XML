/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VIEW;

import AUXILIAR.AuxiliarControleAcesso;
import AUXILIAR.AuxiliarLog;
import MODEL.ModelTabela;
import OperacaoBanco.OperacaoBancoClientes;
import OperacaoBanco.OperacaoBancoMovimento;
import OperacaoBanco.OperacaoBancoXml;
import OperacaoBanco.OperacaoBancoProdutos;
import static VIEW.TelaClientes.EMITIR_RELATORIOS;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import jxl.read.biff.BiffException;
import leituraxml.LerXml;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author André Felipe
 */
public class TelaLeituraXml extends javax.swing.JFrame {

    final static int IMPORTAR_XML = 51;
    final static int EMITIR_RELATORIO = 52;

    JLabel transferedados;
    int codigousuario;

    public TelaLeituraXml() throws IOException, BiffException {
        initComponents();
        jtfChave.setEditable(false);
        jtfDataAutorizacao.setEditable(false);
        jtfDataEmissao.setEditable(false);
        jtfModelo.setEditable(false);
        jtfCfop.setEditable(false);
        jtfRazaoSocial.setEditable(false);
        jtfStatus.setEditable(false);
        jtfNumeroNf.setEditable(false);
        jtfCodCliente.setEditable(false);
        jtfNaturezaOp.setEditable(false);
        jtfValorXml.setEditable(false);

        OperacaoBanco.OperacaoBancoProdutos obp = new OperacaoBancoProdutos();
        preencherTabela("SELECT * FROM xml");
    }

    public TelaLeituraXml(JLabel transferedados, int codigousuario) throws IOException, BiffException {
        initComponents();
        this.transferedados = transferedados;
        this.codigousuario = codigousuario;
        jtfChave.setEditable(false);
        jtfDataAutorizacao.setEditable(false);
        jtfDataEmissao.setEditable(false);
        jtfModelo.setEditable(false);
        jtfCfop.setEditable(false);
        jtfRazaoSocial.setEditable(false);
        jtfStatus.setEditable(false);
        jtfNumeroNf.setEditable(false);
        jtfCodCliente.setEditable(false);
        jtfNaturezaOp.setEditable(false);
        jtfValorXml.setEditable(false);

        OperacaoBanco.OperacaoBancoProdutos obp = new OperacaoBancoProdutos();
        preencherTabela("SELECT * FROM xml");
    }

    /*SELECT * FROM movimento m , produtos p , clientes c WHERE m.cecodigoxml  = 917116
    AND p
    .cpcodpro  = m.cecodpro
    AND c
    .cpcodcli  = m.cecodcli*/
    public void preencherTabelaProdutos(String sql) throws SQLException, IOException, BiffException {
        OperacaoBancoXml obc = new OperacaoBancoXml();
        OperacaoBancoClientes opbc = new OperacaoBancoClientes();
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"CODIGO", "DESCRIÇÃO", "UN", "QUANTIDADE",
            "VALOR UN", "VALOR TOTAL", "CATEGORIA", "MARCA", "QTD/UN", "CLIENTE", "CFOP"};
        obc.obterConexao();
        obc.executaSql(sql);

        try {
            obc.rs.first();
            do {
                dados.add(new Object[]{
                    obc.rs.getInt("cpcodpro"),
                    obc.rs.getString("descricao"),
                    obc.rs.getString("unproduto"),
                    obc.rs.getFloat("qtduntotal"),
                    obc.rs.getString("valorun"),
                    obc.rs.getString("valortotal"),
                    obc.rs.getString("categoria"),
                    obc.rs.getString("marca"),
                    obc.rs.getInt("qtdun"),
                    obc.rs.getInt("cecodcli"),
                    obc.rs.getInt("cfop")

                });
            } while (obc.rs.next());
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(rootPane, "Nenhum XML cadastrado no momento");
        }

        ModelTabela modelo = new ModelTabela(dados, colunas);
        DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        DefaultTableCellRenderer direita = new DefaultTableCellRenderer();
        direita.setHorizontalAlignment(SwingConstants.RIGHT);
        esquerda.setHorizontalAlignment(SwingConstants.LEFT);
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);

        jtProdutoXml.setModel((TableModel) modelo);
        jtProdutoXml.getColumnModel().getColumn(0).setPreferredWidth(90);
        jtProdutoXml.getColumnModel().getColumn(0).setCellRenderer(centralizado);
        jtProdutoXml.getColumnModel().getColumn(0).setResizable(true);
        jtProdutoXml.getColumnModel().getColumn(1).setPreferredWidth(280);
        jtProdutoXml.getColumnModel().getColumn(1).setCellRenderer(centralizado);
        jtProdutoXml.getColumnModel().getColumn(1).setResizable(true);
        jtProdutoXml.getColumnModel().getColumn(2).setPreferredWidth(70);
        jtProdutoXml.getColumnModel().getColumn(2).setResizable(true);
        jtProdutoXml.getColumnModel().getColumn(3).setPreferredWidth(90);
        jtProdutoXml.getColumnModel().getColumn(3).setCellRenderer(centralizado);
        jtProdutoXml.getColumnModel().getColumn(3).setResizable(true);
        jtProdutoXml.getColumnModel().getColumn(4).setPreferredWidth(90);
        jtProdutoXml.getColumnModel().getColumn(4).setCellRenderer(centralizado);
        jtProdutoXml.getColumnModel().getColumn(4).setResizable(true);
        jtProdutoXml.getColumnModel().getColumn(5).setPreferredWidth(120);
        jtProdutoXml.getColumnModel().getColumn(5).setCellRenderer(centralizado);
        jtProdutoXml.getColumnModel().getColumn(5).setResizable(true);
        jtProdutoXml.getColumnModel().getColumn(6).setPreferredWidth(120);
        jtProdutoXml.getColumnModel().getColumn(6).setCellRenderer(centralizado);
        jtProdutoXml.getColumnModel().getColumn(6).setResizable(true);
        jtProdutoXml.getColumnModel().getColumn(7).setPreferredWidth(120);
        jtProdutoXml.getColumnModel().getColumn(7).setCellRenderer(centralizado);
        jtProdutoXml.getColumnModel().getColumn(7).setResizable(true);
        jtProdutoXml.getColumnModel().getColumn(8).setPreferredWidth(90);
        jtProdutoXml.getColumnModel().getColumn(8).setCellRenderer(centralizado);
        jtProdutoXml.getColumnModel().getColumn(8).setResizable(true);

        jtProdutoXml.getTableHeader().setReorderingAllowed(true);
        jtProdutoXml.setAutoResizeMode(jtXml.AUTO_RESIZE_OFF);
        jtProdutoXml.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        float valortotal = 0;
        for (int i = 0; i < jtProdutoXml.getRowCount(); i++) {
            String valorAux = (String) jtProdutoXml.getValueAt(i, 5);
            //System.out.println("TESTE " + valorAux);
            valortotal = valortotal + Float.parseFloat(valorAux);
            //System.out.println("" + valortotal);
            jtfValorXml.setText(String.valueOf(valortotal));
        }

        obc.fechaConexao();
    }

    public void pegarInformacoesAdicionais() throws SQLException, IOException, BiffException {
        OperacaoBancoClientes opbc = new OperacaoBancoClientes();
        String razaosocial;
        String codigocliente = jtProdutoXml.getValueAt(0, 9).toString();
        String cfop = jtProdutoXml.getValueAt(0, 10).toString();
        jtfCodCliente.setText(codigocliente);
        jtfCfop.setText(cfop);
        opbc.obterConexao();
        opbc.executaSql("SELECT razaosocial FROM clientes where cpcodcli = " + Integer.parseInt(codigocliente));
        opbc.rs.first();
        try {
            razaosocial = opbc.rs.getString("razaosocial");
            jtfRazaoSocial.setText(razaosocial);
        } catch (SQLException e) {
            System.out.println("ERRO CONSULTA PREENCHER TABELA: " + e.getMessage());
        }
    }

    public void preencherTabela(String sql) throws IOException, BiffException {
        OperacaoBancoXml obc = new OperacaoBancoXml();
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"Nº NF", "CHAVE NF", "STATUS", "MODELO", "DATA DE EMISSÃO",
            "DATA DE AUTORIZAÇÃO", "NATUREZA DA OPERAÇÃO"};
        obc.obterConexao();
        obc.executaSql(sql);

        try {
            obc.rs.first();

            do {
                dados.add(new Object[]{
                    obc.rs.getInt("numeronfxml"),
                    obc.rs.getString("chavenfxml"),
                    obc.rs.getString("motivo"),
                    obc.rs.getFloat("modelo"),
                    obc.rs.getString("dtemissao"),
                    obc.rs.getString("dtrecebimento"),
                    obc.rs.getString("naturezaop")

                });
            } while (obc.rs.next());
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(rootPane, "Nenhum XML cadastrado no momento");
        }

        ModelTabela modelo = new ModelTabela(dados, colunas);
        DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        DefaultTableCellRenderer direita = new DefaultTableCellRenderer();
        direita.setHorizontalAlignment(SwingConstants.RIGHT);
        esquerda.setHorizontalAlignment(SwingConstants.LEFT);
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);

        jtXml.setModel((TableModel) modelo);
        jtXml.getColumnModel().getColumn(0).setPreferredWidth(90);
        jtXml.getColumnModel().getColumn(0).setCellRenderer(centralizado);
        jtXml.getColumnModel().getColumn(0).setResizable(true);
        jtXml.getColumnModel().getColumn(1).setPreferredWidth(350);
        jtXml.getColumnModel().getColumn(1).setCellRenderer(centralizado);
        jtXml.getColumnModel().getColumn(1).setResizable(true);
        jtXml.getColumnModel().getColumn(2).setPreferredWidth(200);
        jtXml.getColumnModel().getColumn(2).setResizable(true);
        jtXml.getColumnModel().getColumn(3).setPreferredWidth(90);
        jtXml.getColumnModel().getColumn(3).setCellRenderer(centralizado);
        jtXml.getColumnModel().getColumn(3).setResizable(true);
        jtXml.getColumnModel().getColumn(4).setPreferredWidth(250);
        jtXml.getColumnModel().getColumn(4).setCellRenderer(centralizado);
        jtXml.getColumnModel().getColumn(4).setResizable(true);
        jtXml.getColumnModel().getColumn(5).setPreferredWidth(250);
        jtXml.getColumnModel().getColumn(5).setCellRenderer(centralizado);
        jtXml.getColumnModel().getColumn(5).setResizable(true);
        jtXml.getColumnModel().getColumn(6).setPreferredWidth(300);
        jtXml.getColumnModel().getColumn(6).setCellRenderer(centralizado);
        jtXml.getColumnModel().getColumn(6).setResizable(true);

        jtXml.getTableHeader().setReorderingAllowed(true);
        jtXml.setAutoResizeMode(jtXml.AUTO_RESIZE_OFF);
        jtXml.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        obc.fechaConexao();
    }

    public void getXml() throws IOException {
        TelaCarregando tela = new TelaCarregando();
        LerXml ler = new LerXml();

        JFileChooser buscaXml = new JFileChooser();
        //buscaXml.setFileFilter(new FileNameExtensionFilter(""));
        buscaXml.setAcceptAllFileFilterUsed(true);
        buscaXml.setMultiSelectionEnabled(true);
        buscaXml.setDialogTitle("Buscar XML");
        buscaXml.showOpenDialog(this);
        File[] files = buscaXml.getSelectedFiles();
        int tamanho = files.length;
        //System.out.println("Quantidade de arquivos: " + tamanho);

        tela.setVisible(true);

        Thread t = new Thread() {

            @Override
            public void run() {

                for (int i = 0; i < tamanho; i++) {

                    //System.out.println("========================================================="
                    //+ "===================================================================");
                    //System.out.println("ORDEM Nº: " + (i + 1));
                    //System.out.println("NOME/CAMINHO" + files[i]);
                    try {
                        ler.lerArquivo(files[i].toString());
                        preencherTabela("SELECT * FROM xml");

                    } catch (IOException ex) {
                        Logger.getLogger(TelaLeituraXml.class
                                .getName()).log(Level.SEVERE, null, ex);

                    } catch (SQLException ex) {
                        Logger.getLogger(TelaLeituraXml.class
                                .getName()).log(Level.SEVERE, null, ex);
                    } catch (BiffException ex) {
                        Logger.getLogger(TelaLeituraXml.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ParseException ex) {
                        Logger.getLogger(TelaLeituraXml.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                tela.dispose();
            }
        };
        t.start();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jbBusca = new javax.swing.JToggleButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtXml = new javax.swing.JTable();
        jtfRazaoSocial = new javax.swing.JTextField();
        jtfChave = new javax.swing.JTextField();
        jtfStatus = new javax.swing.JTextField();
        jtfModelo = new javax.swing.JTextField();
        jtfDataEmissao = new javax.swing.JTextField();
        jtfDataAutorizacao = new javax.swing.JTextField();
        jtfCfop = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jlLogo = new javax.swing.JLabel();
        jlPesquisaCliente = new javax.swing.JLabel();
        jtfPesquisaCliente = new javax.swing.JTextField();
        jlPesquisa = new javax.swing.JLabel();
        jtfPesquisa = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jtfNumeroNf = new javax.swing.JTextField();
        jtfCodCliente = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jtfNaturezaOp = new javax.swing.JTextField();
        jtfValorXml = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtProdutoXml = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jbImprimir = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jlFundo = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("XML - Movimento XML - Versão 1.0");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jbBusca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENS/botão importar.png"))); // NOI18N
        jbBusca.setFocusable(false);
        jbBusca.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENS/botão importar II.png"))); // NOI18N
        jbBusca.setRequestFocusEnabled(false);
        jbBusca.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENS/botão importar II.png"))); // NOI18N
        jbBusca.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENS/botão importar II.png"))); // NOI18N
        jbBusca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbBuscaActionPerformed(evt);
            }
        });
        getContentPane().add(jbBusca, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 110, 30));

        jScrollPane2.setBackground(new java.awt.Color(204, 204, 204));
        jScrollPane2.setOpaque(false);

        jtXml.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jtXml.setGridColor(new java.awt.Color(204, 204, 204));
        jtXml.setInheritsPopupMenu(true);
        jtXml.setOpaque(false);
        jtXml.setRowHeight(20);
        jtXml.setSelectionBackground(new java.awt.Color(255, 102, 0));
        jtXml.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtXmlMouseClicked(evt);
            }
        });
        jtXml.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtXmlKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(jtXml);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 1070, 210));

        jtfRazaoSocial.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfRazaoSocial.setSelectionColor(new java.awt.Color(255, 102, 0));
        getContentPane().add(jtfRazaoSocial, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 97, 260, -1));

        jtfChave.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfChave.setSelectionColor(new java.awt.Color(255, 102, 0));
        getContentPane().add(jtfChave, new org.netbeans.lib.awtextra.AbsoluteConstraints(496, 97, 370, -1));

        jtfStatus.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfStatus.setSelectionColor(new java.awt.Color(255, 102, 0));
        getContentPane().add(jtfStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 97, 211, -1));

        jtfModelo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfModelo.setSelectionColor(new java.awt.Color(255, 102, 0));
        getContentPane().add(jtfModelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 143, 110, -1));

        jtfDataEmissao.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfDataEmissao.setSelectionColor(new java.awt.Color(255, 102, 0));
        getContentPane().add(jtfDataEmissao, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 143, 223, -1));

        jtfDataAutorizacao.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfDataAutorizacao.setSelectionColor(new java.awt.Color(255, 102, 0));
        getContentPane().add(jtfDataAutorizacao, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 143, 217, -1));

        jtfCfop.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfCfop.setSelectionColor(new java.awt.Color(255, 102, 0));
        getContentPane().add(jtfCfop, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 143, 130, -1));

        jLabel1.setText("Nº NF");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 110, -1));

        jLabel2.setText("Chave de acesso da Nota Fiscal");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 80, -1, -1));

        jLabel3.setText("Status");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 80, -1, -1));

        jLabel4.setText("Modelo da Nota Fiscal");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 123, -1, -1));

        jLabel5.setText("Data de Emissão");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 123, 100, -1));

        jLabel6.setText("Data de Autorização");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 123, -1, -1));

        jLabel7.setText("Natureza da Operação");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 123, -1, -1));

        jlLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENS/logomarca II.png"))); // NOI18N
        getContentPane().add(jlLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 0, 260, 80));

        jlPesquisaCliente.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jlPesquisaCliente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlPesquisaCliente.setText("Pesquisa (Cod Cliente):");
        getContentPane().add(jlPesquisaCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 10, -1, 20));

        jtfPesquisaCliente.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtfPesquisaCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfPesquisaClienteKeyReleased(evt);
            }
        });
        getContentPane().add(jtfPesquisaCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 40, 130, -1));

        jlPesquisa.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jlPesquisa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlPesquisa.setText("Pesquisa (Nº NF):");
        getContentPane().add(jlPesquisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 120, 20));

        jtfPesquisa.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtfPesquisa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtfPesquisaMouseClicked(evt);
            }
        });
        jtfPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfPesquisaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfPesquisaKeyTyped(evt);
            }
        });
        getContentPane().add(jtfPesquisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 40, 130, -1));

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 36)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("XML");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 0, 270, -1));

        jtfNumeroNf.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfNumeroNf.setSelectionColor(new java.awt.Color(255, 102, 0));
        getContentPane().add(jtfNumeroNf, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 97, 110, -1));

        jtfCodCliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfCodCliente.setSelectionColor(new java.awt.Color(255, 102, 0));
        getContentPane().add(jtfCodCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 97, 90, -1));

        jLabel10.setText("Razão Social");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 80, 140, -1));

        jLabel11.setText("Cód. Cliente");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 80, -1));

        jtfNaturezaOp.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfNaturezaOp.setSelectionColor(new java.awt.Color(255, 102, 0));
        getContentPane().add(jtfNaturezaOp, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 143, 211, -1));

        jtfValorXml.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfValorXml.setSelectionColor(new java.awt.Color(255, 102, 0));
        getContentPane().add(jtfValorXml, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 143, 130, -1));

        jLabel12.setText("CFOP");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 123, 50, -1));

        jLabel13.setText("Valor do XML");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 123, -1, -1));

        jScrollPane3.setBackground(new java.awt.Color(204, 204, 204));
        jScrollPane3.setOpaque(false);

        jtProdutoXml.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jtProdutoXml.setToolTipText("Itens do XML");
        jtProdutoXml.setGridColor(new java.awt.Color(204, 204, 204));
        jtProdutoXml.setInheritsPopupMenu(true);
        jtProdutoXml.setOpaque(false);
        jtProdutoXml.setRowHeight(20);
        jtProdutoXml.setSelectionBackground(new java.awt.Color(255, 102, 0));
        jtProdutoXml.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtProdutoXmlMouseClicked(evt);
            }
        });
        jtProdutoXml.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtProdutoXmlKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtProdutoXmlKeyTyped(evt);
            }
        });
        jScrollPane3.setViewportView(jtProdutoXml);

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, 1070, 170));

        jLabel14.setText("Itens do XML");
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, -1, 20));

        jLabel15.setText("Dados do XML");
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, -1, -1));

        jbImprimir.setText("Imprimir");
        jbImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbImprimirActionPerformed(evt);
            }
        });
        getContentPane().add(jbImprimir, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 170, -1, -1));

        jLabel8.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel8.setText("Ctrl+I: Importar | Ctrl+P: Imprirmir XML selecionado");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 600, 410, 20));

        jlFundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENS/fundo padrão III.png"))); // NOI18N
        getContentPane().add(jlFundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1110, 640));

        jMenu2.setText("Opções");

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Importar");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        jMenuItem4.setText("Sair");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);

        jMenuBar1.add(jMenu2);

        jMenu1.setText("Relatórios");

        jMenuItem1.setText("Relatorio de XML por Cliente");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setText("Imprimir XML");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        setSize(new java.awt.Dimension(1112, 691));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbBuscaActionPerformed
        importar();
    }//GEN-LAST:event_jbBuscaActionPerformed

    public void importar() {
        flag = 1;
        OperacaoBancoProdutos obp = null;
        try {

            AUXILIAR.AuxiliarControleAcesso aux = new AuxiliarControleAcesso();
            if (transferedados.getText().equals("ADM") || aux.controle(this.codigousuario, IMPORTAR_XML) == true) {
                obp = new OperacaoBancoProdutos();
                obp.carregarDriver();
                obp.obterConexao();
                getXml();

                AUXILIAR.AuxiliarLog log = new AuxiliarLog();
                log.log(transferedados.getText(), "XML", "IMPORTAR", Long.parseLong("999999"));
                flag = 0;
            }

        } catch (IOException ex) {
            Logger.getLogger(TelaLeituraXml.class.getName()).log(Level.SEVERE, null, ex);
            flag = 0;
        } catch (BiffException ex) {
            Logger.getLogger(TelaLeituraXml.class.getName()).log(Level.SEVERE, null, ex);
            flag = 0;
        } catch (SQLException ex) {
            Logger.getLogger(TelaLeituraXml.class.getName()).log(Level.SEVERE, null, ex);
            flag = 0;
        } catch (Exception ex) {
            Logger.getLogger(TelaLeituraXml.class.getName()).log(Level.SEVERE, null, ex);
            flag = 0;
        }
    }

    private void jtXmlMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtXmlMouseClicked
        selecionarXml();

    }//GEN-LAST:event_jtXmlMouseClicked

    public void selecionarXml() {
        try {
            jtfNumeroNf.setText(jtXml.getValueAt(jtXml.getSelectedRow(), 0).toString());
            jtfChave.setText(jtXml.getValueAt(jtXml.getSelectedRow(), 1).toString());
            jtfStatus.setText(jtXml.getValueAt(jtXml.getSelectedRow(), 2).toString());
            jtfModelo.setText(jtXml.getValueAt(jtXml.getSelectedRow(), 3).toString());
            jtfDataEmissao.setText(jtXml.getValueAt(jtXml.getSelectedRow(), 4).toString());
            jtfDataAutorizacao.setText(jtXml.getValueAt(jtXml.getSelectedRow(), 5).toString());
            jtfNaturezaOp.setText(jtXml.getValueAt(jtXml.getSelectedRow(), 6).toString());

            int numxml = (int) jtXml.getValueAt(jtXml.getSelectedRow(), 0);

            preencherTabelaProdutos("SELECT * FROM movimento m, produtos p, clientes c "
                    + "WHERE m.cecodigoxml = " + numxml
                    + " AND (p.codigofabrica = m.cecodpro"
                    + " or p.codigofabrica2 = m.cecodpro"
                    + " or p.codigofabrica3 = m.cecodpro"
                    + " or p.codigofabrica4 = m.cecodpro"
                    + " or p.codigofabrica5 = m.cecodpro)"
                    + " AND c.cpcodcli = m.cecodcli");

            pegarInformacoesAdicionais();

        } catch (NullPointerException ex) {
            Logger.getLogger(TelaLeituraXml.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TelaLeituraXml.class
                    .getName()).log(Level.SEVERE, null, ex + "ERRO AQUI OH SEU DOIDO");

        } catch (IOException ex) {
            Logger.getLogger(TelaLeituraXml.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (BiffException ex) {
            Logger.getLogger(TelaLeituraXml.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void jtfPesquisaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfPesquisaKeyTyped

    }//GEN-LAST:event_jtfPesquisaKeyTyped

    private void jtProdutoXmlMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtProdutoXmlMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jtProdutoXmlMouseClicked

    private void jtProdutoXmlKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtProdutoXmlKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtProdutoXmlKeyPressed

    private void jtProdutoXmlKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtProdutoXmlKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jtProdutoXmlKeyTyped

    private void jtfPesquisaClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfPesquisaClienteKeyReleased
        try {
            preencherTabela("SELECT * FROM movimento, xml where movimento.cecodcli like "
                    + "'%" + jtfPesquisaCliente.getText().toString()
                    + "%' AND movimento.cecodigoxml = xml.numeronfxml "
                    + "GROUP BY numeronfxml");

        } catch (IOException ex) {
            Logger.getLogger(TelaLeituraXml.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (BiffException ex) {
            Logger.getLogger(TelaLeituraXml.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jtfPesquisaClienteKeyReleased

    private void jtfPesquisaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtfPesquisaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfPesquisaMouseClicked

    private void jbImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbImprimirActionPerformed
        imprimirXml();
    }//GEN-LAST:event_jbImprimirActionPerformed

    public void imprimirXml() {
        try {

            AUXILIAR.AuxiliarControleAcesso aux = new AuxiliarControleAcesso();
            if (transferedados.getText().equals("ADM") || aux.controle(this.codigousuario, EMITIR_RELATORIO) == true) {

                HashMap parametro = new HashMap<>();
                OperacaoBanco.OperacaoBancoXml ob = null;
                ob = new OperacaoBancoXml();
                parametro.put("numeronfe", jtXml.getValueAt(jtXml.getSelectedRow(), 0).toString());
                parametro.put("valorxml", Float.parseFloat(jtfValorXml.getText()));

                ob.obterConexao();

                String src = "Jasper/relatorioXml.jasper";
                JasperPrint jasperPrint = null;
                jasperPrint = JasperFillManager.fillReport(src, parametro, ob.obterConexao());
                JasperViewer view = new JasperViewer(jasperPrint, false);
                view.setVisible(true);

                ob.fechaConexao();

                AUXILIAR.AuxiliarLog log = new AuxiliarLog();
                log.log(transferedados.getText(), "XML", "RELATÓRIOS", Long.parseLong("521"));
            }

        } catch (IOException ex) {
            Logger.getLogger(TelaMovimento.class
                    .getName()).log(Level.SEVERE, null, ex);
            System.out.println("ERRO: " + ex.getMessage());
        } catch (BiffException ex) {
            Logger.getLogger(TelaMovimento.class
                    .getName()).log(Level.SEVERE, null, ex);
            System.out.println("ERRO: " + ex.getMessage());
        } catch (JRException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(TelaLeituraXml.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ERRO: " + ex.getMessage());
        }
    }

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        try {

            AUXILIAR.AuxiliarControleAcesso aux = new AuxiliarControleAcesso();
            if (transferedados.getText().equals("ADM") || aux.controle(this.codigousuario, EMITIR_RELATORIO) == true) {

                TelaParamXmlClientes tela = new TelaParamXmlClientes(this.transferedados);
                tela.setVisible(true);

            }

        } catch (IOException ex) {
            Logger.getLogger(TelaLeituraXml.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (BiffException ex) {
            Logger.getLogger(TelaLeituraXml.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TelaLeituraXml.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        importar();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        imprimirXml();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jtXmlKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtXmlKeyReleased
        selecionarXml();
    }//GEN-LAST:event_jtXmlKeyReleased

    int flag = 0;

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        if (flag == 0) {
            this.dispose();
        } else if (flag == 1) {
            JOptionPane.showMessageDialog(null, "Aguarde o fim da importação!!!");

        } else if (flag == 2) {
            int resp = JOptionPane.showConfirmDialog(null, "A alteração do cadastro não foi salva, deseja sair??", null, 0);
            if (resp == 0) {
                this.dispose();
            }
        }
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jtfPesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfPesquisaKeyReleased
                try {
            preencherTabela("SELECT * FROM xml where numeronfxml like '%" + jtfPesquisa.getText().toString() + "%'");

        } catch (IOException ex) {
            Logger.getLogger(TelaLeituraXml.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (BiffException ex) {
            Logger.getLogger(TelaLeituraXml.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jtfPesquisaKeyReleased

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
            java.util.logging.Logger.getLogger(TelaLeituraXml.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaLeituraXml.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaLeituraXml.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaLeituraXml.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new TelaLeituraXml().setVisible(true);

                } catch (IOException ex) {
                    Logger.getLogger(TelaLeituraXml.class
                            .getName()).log(Level.SEVERE, null, ex);

                } catch (BiffException ex) {
                    Logger.getLogger(TelaLeituraXml.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JToggleButton jbBusca;
    private javax.swing.JButton jbImprimir;
    private javax.swing.JLabel jlFundo;
    private javax.swing.JLabel jlLogo;
    private javax.swing.JLabel jlPesquisa;
    private javax.swing.JLabel jlPesquisaCliente;
    private javax.swing.JTable jtProdutoXml;
    private javax.swing.JTable jtXml;
    private javax.swing.JTextField jtfCfop;
    private javax.swing.JTextField jtfChave;
    private javax.swing.JTextField jtfCodCliente;
    private javax.swing.JTextField jtfDataAutorizacao;
    private javax.swing.JTextField jtfDataEmissao;
    private javax.swing.JTextField jtfModelo;
    private javax.swing.JTextField jtfNaturezaOp;
    private javax.swing.JTextField jtfNumeroNf;
    private javax.swing.JTextField jtfPesquisa;
    private javax.swing.JTextField jtfPesquisaCliente;
    private javax.swing.JTextField jtfRazaoSocial;
    private javax.swing.JTextField jtfStatus;
    private javax.swing.JTextField jtfValorXml;
    // End of variables declaration//GEN-END:variables
}
