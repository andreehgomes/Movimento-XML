/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VIEW;

import AUXILIAR.AuxiliarControleAcesso;
import AUXILIAR.AuxiliarLog;
import MODEL.ModelClientes;
import MODEL.ModelTabela;
import OperacaoBanco.OperacaoBancoClientes;
import OperacaoBanco.OperacaoBancoProdutos;
import static VIEW.TelaProdutos.EMITIR_RELATORIOS;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimerTask;
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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author André Felipe
 */
public class TelaClientes extends javax.swing.JFrame {

    final static int IMPORTAR_CLIENTES = 41;
    final static int EMITIR_RELATORIOS = 42;

    JLabel transferedados;
    int codigousuario;
    int flag = 0;

    public TelaClientes() throws IOException, BiffException {
        initComponents();
        preencherTabela();
    }

    public TelaClientes(JLabel transferedados, int codigousuario) throws IOException, BiffException {
        initComponents();
        this.transferedados = transferedados;
        this.codigousuario = codigousuario;
        preencherTabela();
    }

    String caminhoClientes = "";
    String[] moeda = null;
    int contaregistros = 0;

    public void lerArquivo(String caminho) throws IOException, BiffException {
        MODEL.ModelClientes clientes = new ModelClientes();
        OperacaoBanco.OperacaoBancoClientes obc = new OperacaoBancoClientes();

        String csvArquivo = caminho;
        BufferedReader conteudoCSV = null;
        String linha = "";
        String csvSeparadorDeCampo = ";";

        try {
            conteudoCSV = new BufferedReader(new FileReader(csvArquivo));

            while ((linha = conteudoCSV.readLine()) != null) {

                while ((linha = conteudoCSV.readLine()) != ""
                        + "Regiao;Numero;CNPJ/CPF;Sigla;Razao Social;Vend(1);Superv(1);Gerente(1);Vend(2);Superv(2);Gerente(2);Vend(3)"
                        + ";Superv(3);Gerente(3);Vend(4);Superv(4);Gerente(4);Vend(5);Superv(5);Gerente(5);Canal;Sub Canal;Codigo "
                        + "Companhia;RG;SSP;Inscr.Estadual;Tp Cobr;Cond Pagto;Tabela Preco;Transport;Contabil;Pasta(1);Seq(1);Pasta(2);"
                        + "Seq(2);Pasta(3);Seq(3);Pasta(4);Seq(4);Pasta(5);Seq(5);Status;Dt Inclusao;Dt Bloqueio;Dt Alteracao;||"
                        + "FAT-Tipo;Prep;Patente;Logradouro;N�;Compl;Municipio;Bairro;UF;CEP;Cx.Postal;||COB-Tipo;Prep;Patente;"
                        + "Logradouro;N�;Compl;Municipio;Bairro;UF;CEP;Cx.Postal;||ENT-Tipo;Prep;Patente;Logradouro;N�;Compl;"
                        + "Municipio;Bairro;UF;CEP;Cx.Postal;||Referencia;Fone(1);Fone(2);Fax;Cel(1);Cel(2);Contato;Obs;Aliq."
                        + "ICMS Ret;Aliq.ICMS Frete;Contrib;Contrib.Arbitrado;ABC;Form.Op;Perc.Desc.Boleto;Perc.Desc.Financ;Perc."
                        + "Taxa Financ;Etiq;MotBlq;CPF Cont;Dt.Nascto;Dt.Ult.Compra;Codigo-BW;"
                        + "Coorden-X;Coorden-Y;Regiao+Seq;Email-NF-e;") {

                    if (linha == null) {
                        break;
                    }

                    //System.out.println("teste" + linha);
                    String[] moeda = linha.split(csvSeparadorDeCampo);

                    try {
                        clientes.setCpcodcli(Integer.parseInt(moeda[103]));
                        clientes.setCpfcnpj(moeda[2].replace(".", "").replace("/", "").replace("-", ""));
                        clientes.setRazaosocial(moeda[4]);
                        clientes.setNomefantasia(moeda[3]);
                        clientes.setCidade(moeda[51]);
                        clientes.setBairro(moeda[52]);
                        clientes.setLogradouro(moeda[48]);
                        clientes.setCep(Integer.parseInt(moeda[54]));
                        clientes.setUf(moeda[53]);
                        obc.incluirClientes(clientes);
                        //System.out.println("PASSOU");

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

    public void preencherTabela() throws IOException, BiffException {
        OperacaoBancoClientes obc = new OperacaoBancoClientes();
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"COD", "CPF/CNPJ", "RAZÃO SOCIAL", "NOME FANTASIA",
            "CIDADE", "BAIRRO", "LOGRADOURO", "CEP", "UF"};
        obc.obterConexao();
        obc.executaSql("SELECT * FROM clientes");

        try {
            obc.rs.first();

            do {
                dados.add(new Object[]{
                    obc.rs.getInt("cpcodcli"),
                    obc.rs.getString("cpfcnpj"),
                    obc.rs.getString("razaosocial"),
                    obc.rs.getString("nomefantasia"),
                    obc.rs.getString("cidade"),
                    obc.rs.getString("bairro"),
                    obc.rs.getString("logradouro"),
                    obc.rs.getInt("cep"),
                    obc.rs.getString("uf")
                });
            } while (obc.rs.next());
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(rootPane, "Nenhum cliente cadastrado no momento");
        }

        ModelTabela modelo = new ModelTabela(dados, colunas);
        DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        esquerda.setHorizontalAlignment(SwingConstants.LEFT);
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        jtClientes.setModel((TableModel) modelo);
        jtClientes.getColumnModel().getColumn(0).setPreferredWidth(100);
        jtClientes.getColumnModel().getColumn(0).setCellRenderer(centralizado);
        jtClientes.getColumnModel().getColumn(0).setResizable(true);
        jtClientes.getColumnModel().getColumn(1).setPreferredWidth(130);
        jtClientes.getColumnModel().getColumn(1).setCellRenderer(esquerda);
        jtClientes.getColumnModel().getColumn(1).setResizable(true);
        jtClientes.getColumnModel().getColumn(2).setPreferredWidth(300);
        jtClientes.getColumnModel().getColumn(2).setResizable(true);
        jtClientes.getColumnModel().getColumn(3).setPreferredWidth(300);
        jtClientes.getColumnModel().getColumn(3).setResizable(true);
        jtClientes.getColumnModel().getColumn(4).setPreferredWidth(200);
        jtClientes.getColumnModel().getColumn(4).setResizable(true);
        jtClientes.getColumnModel().getColumn(5).setPreferredWidth(250);
        jtClientes.getColumnModel().getColumn(5).setResizable(true);
        jtClientes.getColumnModel().getColumn(6).setPreferredWidth(250);
        jtClientes.getColumnModel().getColumn(6).setResizable(true);
        jtClientes.getColumnModel().getColumn(7).setPreferredWidth(100);
        jtClientes.getColumnModel().getColumn(7).setResizable(true);
        jtClientes.getColumnModel().getColumn(8).setPreferredWidth(50);
        jtClientes.getColumnModel().getColumn(8).setResizable(true);

        jtClientes.getTableHeader().setReorderingAllowed(true);
        jtClientes.setAutoResizeMode(jtClientes.AUTO_RESIZE_OFF);
        jtClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        obc.fechaConexao();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jtfCaminhoClientes = new javax.swing.JTextField();
        jbSelecionarClientes = new javax.swing.JButton();
        jbImportar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtClientes = new javax.swing.JTable();
        jtfCodigo = new javax.swing.JTextField();
        jlCodigo = new javax.swing.JLabel();
        jtfCpfcnpj = new javax.swing.JTextField();
        jlCpfcnpj = new javax.swing.JLabel();
        jtfRazao = new javax.swing.JTextField();
        jlRazao = new javax.swing.JLabel();
        jtFantasia = new javax.swing.JTextField();
        jlFantasia = new javax.swing.JLabel();
        jtfCep = new javax.swing.JTextField();
        jlCep = new javax.swing.JLabel();
        jtfBairro = new javax.swing.JTextField();
        jlCidade = new javax.swing.JLabel();
        jtfLogradouro = new javax.swing.JTextField();
        jlLogradouro = new javax.swing.JLabel();
        jtfCidade = new javax.swing.JTextField();
        jlBairro = new javax.swing.JLabel();
        jtfUf = new javax.swing.JTextField();
        jlUf = new javax.swing.JLabel();
        jlNomeTela = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Clientes - Movimento XML - Versão 1.0");
        setResizable(false);
        getContentPane().setLayout(null);

        jtfCaminhoClientes.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jtfCaminhoClientes.setEnabled(false);
        jtfCaminhoClientes.setOpaque(false);
        getContentPane().add(jtfCaminhoClientes);
        jtfCaminhoClientes.setBounds(280, 70, 610, 24);

        jbSelecionarClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENS/botão selecionar.png"))); // NOI18N
        jbSelecionarClientes.setFocusable(false);
        jbSelecionarClientes.setOpaque(false);
        jbSelecionarClientes.setRequestFocusEnabled(false);
        jbSelecionarClientes.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENS/botão selecionar II.png"))); // NOI18N
        jbSelecionarClientes.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENS/botão selecionar II.png"))); // NOI18N
        jbSelecionarClientes.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENS/botão selecionar II.png"))); // NOI18N
        jbSelecionarClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSelecionarClientesActionPerformed(evt);
            }
        });
        getContentPane().add(jbSelecionarClientes);
        jbSelecionarClientes.setBounds(280, 20, 110, 30);

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
        getContentPane().add(jbImportar);
        jbImportar.setBounds(400, 20, 110, 30);

        jtClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jtClientes.setGridColor(new java.awt.Color(204, 204, 204));
        jtClientes.setInheritsPopupMenu(true);
        jtClientes.setOpaque(false);
        jtClientes.setRowHeight(20);
        jtClientes.setSelectionBackground(new java.awt.Color(255, 102, 0));
        jtClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtClientesMouseClicked(evt);
            }
        });
        jtClientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtClientesKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(jtClientes);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(31, 212, 858, 360);

        jtfCodigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfCodigo.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jtfCodigo.setEnabled(false);
        jtfCodigo.setOpaque(false);
        getContentPane().add(jtfCodigo);
        jtfCodigo.setBounds(31, 133, 105, 24);

        jlCodigo.setText("Código");
        getContentPane().add(jlCodigo);
        jlCodigo.setBounds(31, 117, 39, 16);

        jtfCpfcnpj.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfCpfcnpj.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jtfCpfcnpj.setEnabled(false);
        jtfCpfcnpj.setOpaque(false);
        getContentPane().add(jtfCpfcnpj);
        jtfCpfcnpj.setBounds(146, 133, 129, 24);

        jlCpfcnpj.setText("CPF / CNPJ");
        getContentPane().add(jlCpfcnpj);
        jlCpfcnpj.setBounds(146, 117, 62, 16);

        jtfRazao.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfRazao.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jtfRazao.setEnabled(false);
        jtfRazao.setOpaque(false);
        getContentPane().add(jtfRazao);
        jtfRazao.setBounds(285, 133, 260, 24);

        jlRazao.setText("Razão Social");
        getContentPane().add(jlRazao);
        jlRazao.setBounds(285, 117, 73, 16);

        jtFantasia.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtFantasia.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jtFantasia.setEnabled(false);
        jtFantasia.setOpaque(false);
        getContentPane().add(jtFantasia);
        jtFantasia.setBounds(555, 133, 334, 24);

        jlFantasia.setText("Nome Fantasia");
        getContentPane().add(jlFantasia);
        jlFantasia.setBounds(555, 117, 84, 16);

        jtfCep.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfCep.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jtfCep.setEnabled(false);
        jtfCep.setOpaque(false);
        getContentPane().add(jtfCep);
        jtfCep.setBounds(31, 181, 105, 24);

        jlCep.setText("CEP");
        getContentPane().add(jlCep);
        jlCep.setBounds(31, 164, 23, 16);

        jtfBairro.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfBairro.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jtfBairro.setEnabled(false);
        jtfBairro.setOpaque(false);
        getContentPane().add(jtfBairro);
        jtfBairro.setBounds(555, 181, 226, 24);

        jlCidade.setText("Cidade");
        getContentPane().add(jlCidade);
        jlCidade.setBounds(146, 164, 39, 16);

        jtfLogradouro.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfLogradouro.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jtfLogradouro.setEnabled(false);
        jtfLogradouro.setOpaque(false);
        getContentPane().add(jtfLogradouro);
        jtfLogradouro.setBounds(285, 181, 260, 24);

        jlLogradouro.setText("Logradouro");
        getContentPane().add(jlLogradouro);
        jlLogradouro.setBounds(285, 164, 66, 16);

        jtfCidade.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfCidade.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jtfCidade.setEnabled(false);
        jtfCidade.setOpaque(false);
        getContentPane().add(jtfCidade);
        jtfCidade.setBounds(146, 181, 129, 24);

        jlBairro.setText("Bairro");
        getContentPane().add(jlBairro);
        jlBairro.setBounds(555, 164, 35, 16);

        jtfUf.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfUf.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jtfUf.setEnabled(false);
        jtfUf.setOpaque(false);
        getContentPane().add(jtfUf);
        jtfUf.setBounds(787, 181, 102, 24);

        jlUf.setText("UF");
        getContentPane().add(jlUf);
        jlUf.setBounds(787, 164, 40, 16);

        jlNomeTela.setFont(new java.awt.Font("Franklin Gothic Heavy", 0, 36)); // NOI18N
        jlNomeTela.setForeground(new java.awt.Color(153, 51, 0));
        jlNomeTela.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlNomeTela.setText("Clientes");
        getContentPane().add(jlNomeTela);
        jlNomeTela.setBounds(550, 10, 330, 50);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENS/logomarca II.png"))); // NOI18N
        getContentPane().add(jLabel2);
        jLabel2.setBounds(10, 10, 260, 100);

        jLabel9.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel9.setText("Ctrl+O: Selecionar | Ctrl+I: Importar");
        getContentPane().add(jLabel9);
        jLabel9.setBounds(30, 580, 330, 19);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENS/TELA CLIENTE II.png"))); // NOI18N
        jLabel1.setText("jLabel1");
        jLabel1.setMaximumSize(new java.awt.Dimension(1746, 900));
        jLabel1.setMinimumSize(new java.awt.Dimension(1746, 900));
        jLabel1.setPreferredSize(new java.awt.Dimension(1746, 900));
        getContentPane().add(jLabel1);
        jLabel1.setBounds(-10, -10, 960, 630);

        jMenu2.setText("Opções");

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setText("Selecionar");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem5.setText("Importar");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem5);

        jMenuItem6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        jMenuItem6.setText("Sair");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem6);

        jMenuBar1.add(jMenu2);

        jMenu1.setText("Relatórios");

        jMenuItem1.setText("421 - Lista de Clientes");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("422 - Relatório de Clientes por cidade");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("423 - Relatório de Clientes por cidade e bairro");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        setSize(new java.awt.Dimension(928, 664));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    private void jbSelecionarClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSelecionarClientesActionPerformed
        selecionar();
    }//GEN-LAST:event_jbSelecionarClientesActionPerformed

    public void selecionar() {
        JFileChooser buscaArquivo = new JFileChooser();
        //buscaArquivo.setFileFilter(new FileNameExtensionFilter("CSV"));
        buscaArquivo.setAcceptAllFileFilterUsed(false);
        buscaArquivo.setDialogTitle("Buscar Arquivo");
        buscaArquivo.showOpenDialog(this);

        caminhoClientes = "" + buscaArquivo.getSelectedFile().getAbsolutePath();
        jtfCaminhoClientes.setText(caminhoClientes);

    }

    private void jbImportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbImportarActionPerformed
        importar();
    }//GEN-LAST:event_jbImportarActionPerformed

    public void importar() {
        AUXILIAR.AuxiliarControleAcesso aux = new AuxiliarControleAcesso();
        try {
            if (transferedados.getText().equals("ADM") || aux.controle(this.codigousuario, IMPORTAR_CLIENTES) == true) {
                Date data = new Date();
                final SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                String inicio = format.format(new Date().getTime());
                String fim;

                if (jtfCaminhoClientes.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Selecione o arquivo para importação!!!");
                } else {

                    TelaCarregando tela = new TelaCarregando();
                    tela.setVisible(true);

                    Thread t = new Thread() {
                        @Override
                        public void run() {

                            try {
                                lerArquivo(caminhoClientes);
                            } catch (IOException ex) {
                                Logger.getLogger(TelaClientes.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (BiffException ex) {
                                Logger.getLogger(TelaClientes.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            tela.dispose();
                            JOptionPane.showMessageDialog(null, "IMPORTAÇÃO FINALIZADA\n Registros realizados: " + contaregistros
                                    + "\nInício: " + inicio + "\nTermino: " + format.format(new Date().getTime()));
                            try {
                                preencherTabela();
                            } catch (IOException ex) {
                                Logger.getLogger(TelaClientes.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (BiffException ex) {
                                Logger.getLogger(TelaClientes.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            jtfCaminhoClientes.setText("");
                            contaregistros = 0;

                        }
                    };

                    t.start();
                }

                AUXILIAR.AuxiliarLog log = new AuxiliarLog();
                try {
                    log.log(transferedados.getText(), "CLIENTES", "IMPORTAR", Long.parseLong("888888"));
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
    }

    private void jtClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtClientesMouseClicked
        jtfCodigo.setText(jtClientes.getValueAt(jtClientes.getSelectedRow(), 0).toString());
        jtfCpfcnpj.setText(jtClientes.getValueAt(jtClientes.getSelectedRow(), 1).toString());
        jtfRazao.setText(jtClientes.getValueAt(jtClientes.getSelectedRow(), 2).toString());
        jtFantasia.setText(jtClientes.getValueAt(jtClientes.getSelectedRow(), 3).toString());
        jtfCep.setText(jtClientes.getValueAt(jtClientes.getSelectedRow(), 7).toString());
        jtfCidade.setText(jtClientes.getValueAt(jtClientes.getSelectedRow(), 4).toString());
        jtfLogradouro.setText(jtClientes.getValueAt(jtClientes.getSelectedRow(), 6).toString());
        jtfBairro.setText(jtClientes.getValueAt(jtClientes.getSelectedRow(), 5).toString());
        jtfUf.setText(jtClientes.getValueAt(jtClientes.getSelectedRow(), 8).toString());
    }//GEN-LAST:event_jtClientesMouseClicked

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed

        AUXILIAR.AuxiliarControleAcesso aux = new AuxiliarControleAcesso();
        try {
            if (transferedados.getText().equals("ADM") || aux.controle(this.codigousuario, EMITIR_RELATORIOS) == true) {
                OperacaoBancoClientes ob = new OperacaoBancoClientes();
                ob.obterConexao();

                String src = "Jasper/relatorioListaClientes.jasper";
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
                    log.log(transferedados.getText(), "CLIENTES", "RELATÓRIO", Long.parseLong("421"));
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

        try {
            AUXILIAR.AuxiliarControleAcesso aux = new AuxiliarControleAcesso();
            if (transferedados.getText().equals("ADM") || aux.controle(this.codigousuario, EMITIR_RELATORIOS) == true) {
                TelaParamClientesCidade tela = new TelaParamClientesCidade(this.transferedados);
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

        AUXILIAR.AuxiliarControleAcesso aux = new AuxiliarControleAcesso();
        try {
            if (transferedados.getText().equals("ADM") || aux.controle(this.codigousuario, EMITIR_RELATORIOS) == true) {
                TelaParamClientesCidadeBairro tela = new TelaParamClientesCidadeBairro(this.transferedados);
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
        selecionar();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        importar();
    }//GEN-LAST:event_jMenuItem5ActionPerformed

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

    private void jtClientesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtClientesKeyReleased
        jtfCodigo.setText(jtClientes.getValueAt(jtClientes.getSelectedRow(), 0).toString());
        jtfCpfcnpj.setText(jtClientes.getValueAt(jtClientes.getSelectedRow(), 1).toString());
        jtfRazao.setText(jtClientes.getValueAt(jtClientes.getSelectedRow(), 2).toString());
        jtFantasia.setText(jtClientes.getValueAt(jtClientes.getSelectedRow(), 3).toString());
        jtfCep.setText(jtClientes.getValueAt(jtClientes.getSelectedRow(), 7).toString());
        jtfCidade.setText(jtClientes.getValueAt(jtClientes.getSelectedRow(), 4).toString());
        jtfLogradouro.setText(jtClientes.getValueAt(jtClientes.getSelectedRow(), 6).toString());
        jtfBairro.setText(jtClientes.getValueAt(jtClientes.getSelectedRow(), 5).toString());
        jtfUf.setText(jtClientes.getValueAt(jtClientes.getSelectedRow(), 8).toString());
    }//GEN-LAST:event_jtClientesKeyReleased

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
            java.util.logging.Logger.getLogger(TelaClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new TelaClientes().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(TelaClientes.class.getName()).log(Level.SEVERE, null, ex);
                } catch (BiffException ex) {
                    Logger.getLogger(TelaClientes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbImportar;
    private javax.swing.JButton jbSelecionarClientes;
    private javax.swing.JLabel jlBairro;
    private javax.swing.JLabel jlCep;
    private javax.swing.JLabel jlCidade;
    private javax.swing.JLabel jlCodigo;
    private javax.swing.JLabel jlCpfcnpj;
    private javax.swing.JLabel jlFantasia;
    private javax.swing.JLabel jlLogradouro;
    private javax.swing.JLabel jlNomeTela;
    private javax.swing.JLabel jlRazao;
    private javax.swing.JLabel jlUf;
    private javax.swing.JTable jtClientes;
    private javax.swing.JTextField jtFantasia;
    private javax.swing.JTextField jtfBairro;
    private javax.swing.JTextField jtfCaminhoClientes;
    private javax.swing.JTextField jtfCep;
    private javax.swing.JTextField jtfCidade;
    private javax.swing.JTextField jtfCodigo;
    private javax.swing.JTextField jtfCpfcnpj;
    private javax.swing.JTextField jtfLogradouro;
    private javax.swing.JTextField jtfRazao;
    private javax.swing.JTextField jtfUf;
    // End of variables declaration//GEN-END:variables
}
