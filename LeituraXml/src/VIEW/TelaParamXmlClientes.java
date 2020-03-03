/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VIEW;

import AUXILIAR.AuxiliarLog;
import MODEL.ModelClientes;
import MODEL.ModelTabela;
import OperacaoBanco.OperacaoBancoClientes;
import OperacaoBanco.OperacaoBancoProdutos;
import OperacaoBanco.OperacaoBancoXml;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
public class TelaParamXmlClientes extends javax.swing.JFrame {

    JLabel transferedados;

    
    public TelaParamXmlClientes() throws IOException, BiffException {
        initComponents();
        preencherTabela("SELECT * FROM clientes");
    }

    public TelaParamXmlClientes(JLabel transferedados) throws IOException, BiffException {
        initComponents();
        this.transferedados = transferedados;
        preencherTabela("SELECT * FROM clientes");
        
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

    public void preencherTabela(String sql) throws IOException, BiffException {
        OperacaoBancoClientes obc = new OperacaoBancoClientes();
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"COD", "CPF/CNPJ", "RAZÃO SOCIAL", "NOME FANTASIA",
            "CIDADE", "BAIRRO", "LOGRADOURO", "CEP", "UF"};
        obc.obterConexao();
        obc.executaSql(sql);

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
            JOptionPane.showMessageDialog(rootPane, "Nenhum cliente cadastrado no momento");
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

    public void preencherTabelaPesquisa(String campo, String pesquisa) throws IOException, BiffException {
        OperacaoBancoClientes obc = new OperacaoBancoClientes();
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"COD", "CPF/CNPJ", "RAZÃO SOCIAL", "NOME FANTASIA",
            "CIDADE", "BAIRRO", "LOGRADOURO", "CEP", "UF"};
        obc.obterConexao();
        obc.executaSql("SELECT * FROM clientes WHERE " + campo + " like '%" + pesquisa + "%'");

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
        jlNomeTela = new javax.swing.JLabel();
        jbFiltrar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Filtro");
        setResizable(false);
        getContentPane().setLayout(null);

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
        jScrollPane2.setViewportView(jtClientes);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(30, 120, 858, 420);

        jtfCodigo.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtfCodigo.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jtfCodigo.setOpaque(false);
        jtfCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfCodigoKeyReleased(evt);
            }
        });
        getContentPane().add(jtfCodigo);
        jtfCodigo.setBounds(30, 80, 105, 24);

        jlCodigo.setText("Código");
        getContentPane().add(jlCodigo);
        jlCodigo.setBounds(30, 60, 39, 16);

        jtfCpfcnpj.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtfCpfcnpj.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jtfCpfcnpj.setOpaque(false);
        jtfCpfcnpj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfCpfcnpjKeyReleased(evt);
            }
        });
        getContentPane().add(jtfCpfcnpj);
        jtfCpfcnpj.setBounds(140, 80, 129, 24);

        jlCpfcnpj.setText("CPF / CNPJ");
        getContentPane().add(jlCpfcnpj);
        jlCpfcnpj.setBounds(140, 60, 62, 16);

        jtfRazao.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtfRazao.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jtfRazao.setOpaque(false);
        jtfRazao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfRazaoKeyReleased(evt);
            }
        });
        getContentPane().add(jtfRazao);
        jtfRazao.setBounds(280, 80, 260, 24);

        jlRazao.setText("Razão Social");
        getContentPane().add(jlRazao);
        jlRazao.setBounds(280, 60, 73, 16);

        jtFantasia.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtFantasia.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jtFantasia.setOpaque(false);
        jtFantasia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtFantasiaKeyReleased(evt);
            }
        });
        getContentPane().add(jtFantasia);
        jtFantasia.setBounds(550, 80, 334, 24);

        jlFantasia.setText("Nome Fantasia");
        getContentPane().add(jlFantasia);
        jlFantasia.setBounds(550, 60, 84, 16);

        jlNomeTela.setFont(new java.awt.Font("Franklin Gothic Heavy", 0, 36)); // NOI18N
        jlNomeTela.setForeground(new java.awt.Color(153, 51, 0));
        jlNomeTela.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlNomeTela.setText("Relatório de XML por Clientes");
        getContentPane().add(jlNomeTela);
        jlNomeTela.setBounds(10, 10, 490, 50);

        jbFiltrar.setText("Filtrar");
        jbFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbFiltrarActionPerformed(evt);
            }
        });
        getContentPane().add(jbFiltrar);
        jbFiltrar.setBounds(777, 540, 110, 32);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENS/TELA CLIENTE II.png"))); // NOI18N
        jLabel1.setText("jLabel1");
        jLabel1.setMaximumSize(new java.awt.Dimension(1746, 900));
        jLabel1.setMinimumSize(new java.awt.Dimension(1746, 900));
        jLabel1.setPreferredSize(new java.awt.Dimension(1746, 900));
        getContentPane().add(jLabel1);
        jLabel1.setBounds(-10, -10, 960, 620);

        jMenu1.setText("Relatórios");

        jMenuItem1.setText("Lista de Clientes");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Relatório de Clientes por cidade");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("Relatório de Clientes por cidade e bairro");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Opções");

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ENTER, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setText("Selecionar Item");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        jMenuItem5.setText("Sair");
        jMenu2.add(jMenuItem5);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        setSize(new java.awt.Dimension(928, 646));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    private void jtClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtClientesMouseClicked


    }//GEN-LAST:event_jtClientesMouseClicked

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        try {
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

        } catch (IOException ex) {
            Logger.getLogger(TelaMovimento.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(TelaMovimento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        try {
            TelaParamClientesCidade tela = new TelaParamClientesCidade();
            tela.setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(TelaParamXmlClientes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(TelaParamXmlClientes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        try {
            TelaParamClientesCidadeBairro tela = new TelaParamClientesCidadeBairro();
            tela.setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(TelaParamXmlClientes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(TelaParamXmlClientes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jtfCodigoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfCodigoKeyReleased
        try {
            preencherTabelaPesquisa("cpcodcli", jtfCodigo.getText());
        } catch (IOException ex) {
            Logger.getLogger(TelaParamXmlClientes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(TelaParamXmlClientes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jtfCodigoKeyReleased

    private void jtfCpfcnpjKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfCpfcnpjKeyReleased
        try {
            preencherTabelaPesquisa("cpfcnpj", jtfCpfcnpj.getText());
        } catch (IOException ex) {
            Logger.getLogger(TelaParamXmlClientes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(TelaParamXmlClientes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jtfCpfcnpjKeyReleased

    private void jtfRazaoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfRazaoKeyReleased
        try {
            preencherTabelaPesquisa("razaosocial", jtfRazao.getText().toUpperCase());
        } catch (IOException ex) {
            Logger.getLogger(TelaParamXmlClientes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(TelaParamXmlClientes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jtfRazaoKeyReleased

    private void jtFantasiaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtFantasiaKeyReleased
        try {
            preencherTabelaPesquisa("nomefantasia", jtFantasia.getText().toUpperCase());
        } catch (IOException ex) {
            Logger.getLogger(TelaParamXmlClientes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(TelaParamXmlClientes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jtFantasiaKeyReleased

    public void selecionarItem() {
        try {
            HashMap parametro = new HashMap<>();
            OperacaoBanco.OperacaoBancoXml ob = null;
            ob = new OperacaoBancoXml();
            parametro.put("codigocliente", jtClientes.getValueAt(jtClientes.getSelectedRow(), 0).toString());
            ob.obterConexao();

            String src = "Jasper/relatorioListaXmlCliente.jasper";
            JasperPrint jasperPrint = null;
            jasperPrint = JasperFillManager.fillReport(src, parametro, ob.obterConexao());
            JasperViewer view = new JasperViewer(jasperPrint, false);
            view.setVisible(true);

            ob.fechaConexao();

            AUXILIAR.AuxiliarLog log = new AuxiliarLog();
            log.log(transferedados.getText(), "XML", "RELATÓRIOS", Long.parseLong("522"));

        } catch (IOException ex) {
            Logger.getLogger(TelaMovimento.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(TelaMovimento.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }
    }

    private void jbFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbFiltrarActionPerformed
        selecionarItem();
        
    }//GEN-LAST:event_jbFiltrarActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        selecionarItem();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

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
            java.util.logging.Logger.getLogger(TelaParamXmlClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaParamXmlClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaParamXmlClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaParamXmlClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new TelaParamXmlClientes().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(TelaParamXmlClientes.class.getName()).log(Level.SEVERE, null, ex);
                } catch (BiffException ex) {
                    Logger.getLogger(TelaParamXmlClientes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbFiltrar;
    private javax.swing.JLabel jlCodigo;
    private javax.swing.JLabel jlCpfcnpj;
    private javax.swing.JLabel jlFantasia;
    private javax.swing.JLabel jlNomeTela;
    private javax.swing.JLabel jlRazao;
    private javax.swing.JTable jtClientes;
    private javax.swing.JTextField jtFantasia;
    private javax.swing.JTextField jtfCodigo;
    private javax.swing.JTextField jtfCpfcnpj;
    private javax.swing.JTextField jtfRazao;
    // End of variables declaration//GEN-END:variables
}
