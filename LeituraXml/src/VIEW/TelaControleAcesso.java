/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VIEW;

import MODEL.ModelControleAcesso;
import MODEL.ModelUsuarios;
import OperacaoBanco.OperacaoBancoControleAcesso;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import jxl.read.biff.BiffException;

public class TelaControleAcesso extends javax.swing.JFrame {

    String usuario;
    int codusu;

    final int MOD1 = 1;
    final int MOD11 = 11;
    final int MOD12 = 12;
    final int MOD13 = 13;
    final int MOD14 = 14;
    final int MOD2 = 2;
    final int MOD21 = 21;
    final int MOD22 = 22;
    final int MOD23 = 23;
    final int MOD3 = 3;
    final int MOD31 = 31;
    final int MOD32 = 32;
    final int MOD4 = 4;
    final int MOD41 = 41;
    final int MOD42 = 42;
    final int MOD5 = 5;
    final int MOD51 = 51;
    final int MOD52 = 52;
    final int MOD6 = 6;
    final int MOD61 = 61;
    final int MOD62 = 62;
    final int MOD7 = 7;
    final int MOD71 = 71;
    final int MOD72 = 72;
    final int MOD73 = 73;
    final int MOD8 = 8;
    final int SIM = 1;
    final int NAO = 0;

    public TelaControleAcesso() throws IOException, BiffException {
        initComponents();
    }

    public TelaControleAcesso(String usuario, int codusu) throws IOException, BiffException, SQLException {
        initComponents();
        this.usuario = usuario;
        this.codusu = codusu;
        jlCodUsu.setText("" + this.codusu);
        jlUsuario.setText(this.usuario);

        buscarControleAcessoDoUsuario(this.codusu);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        gb1 = new javax.swing.ButtonGroup();
        gb11 = new javax.swing.ButtonGroup();
        gb12 = new javax.swing.ButtonGroup();
        gb13 = new javax.swing.ButtonGroup();
        gb14 = new javax.swing.ButtonGroup();
        gb2 = new javax.swing.ButtonGroup();
        gb21 = new javax.swing.ButtonGroup();
        gb22 = new javax.swing.ButtonGroup();
        gb23 = new javax.swing.ButtonGroup();
        gb3 = new javax.swing.ButtonGroup();
        gb31 = new javax.swing.ButtonGroup();
        gb32 = new javax.swing.ButtonGroup();
        gb4 = new javax.swing.ButtonGroup();
        gb41 = new javax.swing.ButtonGroup();
        gb42 = new javax.swing.ButtonGroup();
        gb5 = new javax.swing.ButtonGroup();
        gb51 = new javax.swing.ButtonGroup();
        gb52 = new javax.swing.ButtonGroup();
        gb6 = new javax.swing.ButtonGroup();
        gb61 = new javax.swing.ButtonGroup();
        gb62 = new javax.swing.ButtonGroup();
        gb7 = new javax.swing.ButtonGroup();
        gb71 = new javax.swing.ButtonGroup();
        gb72 = new javax.swing.ButtonGroup();
        gb73 = new javax.swing.ButtonGroup();
        gb8 = new javax.swing.ButtonGroup();
        jlTitulo = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jrSim1 = new javax.swing.JRadioButton();
        jrNao1 = new javax.swing.JRadioButton();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jrSim11 = new javax.swing.JRadioButton();
        jrNao11 = new javax.swing.JRadioButton();
        jrNao12 = new javax.swing.JRadioButton();
        jrSim12 = new javax.swing.JRadioButton();
        jrNao13 = new javax.swing.JRadioButton();
        jrSim13 = new javax.swing.JRadioButton();
        jrNao14 = new javax.swing.JRadioButton();
        jrSim14 = new javax.swing.JRadioButton();
        jrSim2 = new javax.swing.JRadioButton();
        jrNao2 = new javax.swing.JRadioButton();
        jrNao21 = new javax.swing.JRadioButton();
        jrSim21 = new javax.swing.JRadioButton();
        jrNao22 = new javax.swing.JRadioButton();
        jrSim22 = new javax.swing.JRadioButton();
        jrNao23 = new javax.swing.JRadioButton();
        jrSim23 = new javax.swing.JRadioButton();
        jrNao3 = new javax.swing.JRadioButton();
        jrSim3 = new javax.swing.JRadioButton();
        jrNao31 = new javax.swing.JRadioButton();
        jrSim31 = new javax.swing.JRadioButton();
        jrNao32 = new javax.swing.JRadioButton();
        jrSim32 = new javax.swing.JRadioButton();
        jrNao4 = new javax.swing.JRadioButton();
        jrSim4 = new javax.swing.JRadioButton();
        jrNao41 = new javax.swing.JRadioButton();
        jrSim41 = new javax.swing.JRadioButton();
        jrNao42 = new javax.swing.JRadioButton();
        jrSim42 = new javax.swing.JRadioButton();
        jrNao5 = new javax.swing.JRadioButton();
        jrSim5 = new javax.swing.JRadioButton();
        jrNao51 = new javax.swing.JRadioButton();
        jrSim51 = new javax.swing.JRadioButton();
        jrNao52 = new javax.swing.JRadioButton();
        jrSim52 = new javax.swing.JRadioButton();
        jrNao6 = new javax.swing.JRadioButton();
        jrSim6 = new javax.swing.JRadioButton();
        jrNao61 = new javax.swing.JRadioButton();
        jrSim61 = new javax.swing.JRadioButton();
        jrNao62 = new javax.swing.JRadioButton();
        jrSim62 = new javax.swing.JRadioButton();
        jrNao7 = new javax.swing.JRadioButton();
        jrSim7 = new javax.swing.JRadioButton();
        jrNao71 = new javax.swing.JRadioButton();
        jrSim71 = new javax.swing.JRadioButton();
        jrNao72 = new javax.swing.JRadioButton();
        jrSim72 = new javax.swing.JRadioButton();
        jrNao73 = new javax.swing.JRadioButton();
        jrSim73 = new javax.swing.JRadioButton();
        jrNao8 = new javax.swing.JRadioButton();
        jrSim8 = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();
        jbSalvar = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jlUsuario = new javax.swing.JLabel();
        jlCodUsu = new javax.swing.JLabel();
        jlFundo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Controle de Acesso - Movimento XML - Versão 1.0");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlTitulo.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        jlTitulo.setForeground(new java.awt.Color(153, 0, 0));
        jlTitulo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlTitulo.setText("Controle de acesso de usuários");
        getContentPane().add(jlTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 0, 230, 30));

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("NÃO");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 80, 30, -1));

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel2.setText("1 - Usuários ..................................................");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 280, -1));

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel3.setText("8 - Auditoria ..................................................");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 330, 280, 20));

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel4.setText("11 - Novo usuário ..........................................");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 280, -1));

        jLabel5.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel5.setText("12 - Editar usuário ..................................................");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 280, -1));

        jLabel6.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel6.setText("13 - Emitir relatórios ..................................................");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 280, -1));

        jLabel7.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel7.setText("14 - Controle de acesso ..................................................");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 280, -1));

        jLabel8.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel8.setText("2 - CFOP .........................................................");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 280, -1));

        jLabel9.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel9.setText("21 - Novo CFOP ..................................................");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 280, -1));

        jLabel10.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel10.setText("22 - Editar CFOP ..................................................");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 280, -1));

        jLabel11.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel11.setText("23 - Emitir relatórios ..................................................");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 280, -1));

        jLabel12.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel12.setText("3 - Produtos ..................................................");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 280, -1));

        jLabel13.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel13.setText("31 - Importar produtos ..................................................");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 280, -1));

        jLabel14.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel14.setText("32 - Emitir relatórios ..................................................");
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 280, -1));

        jLabel15.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel15.setText("4 - Clientes ..................................................");
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 280, -1));

        jLabel16.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel16.setText("41 - Importar clientes ..................................................");
        getContentPane().add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, 280, -1));

        jLabel17.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel17.setText("42 - Emitir relatórios ..................................................");
        getContentPane().add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 280, -1));

        jLabel18.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel18.setText("5 - XML ....................................................................................................");
        getContentPane().add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 100, 280, -1));

        jLabel19.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel19.setText("51 - Importar XML ..................................................");
        getContentPane().add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 120, 280, -1));

        jLabel20.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel20.setText("52 - Emitir relatórios ..................................................");
        getContentPane().add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 140, 280, -1));

        jLabel21.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel21.setText("6 - Movimento ....................................................................................................");
        getContentPane().add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 170, 280, -1));

        jLabel22.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel22.setText("61 - Emitir relatórios ..................................................");
        getContentPane().add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 190, 280, -1));

        jLabel23.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel23.setText("62 - Gerar arquivo de importação Android ..................................................");
        getContentPane().add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 210, 280, 20));

        jLabel24.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel24.setText("7 - Contagens ..................................................");
        getContentPane().add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 240, 280, 20));

        jLabel25.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel25.setText("71 - Importar ..................................................");
        getContentPane().add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 260, 280, 20));

        jLabel26.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel26.setText("72 - Editar contagem ..................................................");
        getContentPane().add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 280, 280, 20));

        jLabel27.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel27.setText("73 - Emitir relatórios ..................................................");
        getContentPane().add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 300, 280, 20));

        gb1.add(jrSim1);
        jrSim1.setSelected(true);
        jrSim1.setOpaque(false);
        getContentPane().add(jrSim1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 100, 30, 20));

        gb1.add(jrNao1);
        jrNao1.setOpaque(false);
        getContentPane().add(jrNao1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 100, -1, 20));

        jLabel28.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel28.setText("Módulo");
        getContentPane().add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 280, -1));

        jLabel29.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel29.setText("SIM");
        getContentPane().add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 80, 40, -1));

        gb11.add(jrSim11);
        jrSim11.setSelected(true);
        jrSim11.setOpaque(false);
        getContentPane().add(jrSim11, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 120, 30, 20));

        gb11.add(jrNao11);
        jrNao11.setOpaque(false);
        getContentPane().add(jrNao11, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 120, -1, 20));

        gb12.add(jrNao12);
        jrNao12.setOpaque(false);
        getContentPane().add(jrNao12, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 140, -1, 20));

        gb12.add(jrSim12);
        jrSim12.setSelected(true);
        jrSim12.setOpaque(false);
        getContentPane().add(jrSim12, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 140, 30, 20));

        gb13.add(jrNao13);
        jrNao13.setOpaque(false);
        getContentPane().add(jrNao13, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 160, -1, 20));

        gb13.add(jrSim13);
        jrSim13.setSelected(true);
        jrSim13.setOpaque(false);
        getContentPane().add(jrSim13, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 160, 30, 20));

        gb14.add(jrNao14);
        jrNao14.setToolTipText("");
        jrNao14.setOpaque(false);
        getContentPane().add(jrNao14, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 180, -1, 20));

        gb14.add(jrSim14);
        jrSim14.setSelected(true);
        jrSim14.setToolTipText("");
        jrSim14.setOpaque(false);
        getContentPane().add(jrSim14, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 180, 30, 20));

        gb2.add(jrSim2);
        jrSim2.setSelected(true);
        jrSim2.setOpaque(false);
        getContentPane().add(jrSim2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 210, 30, 20));

        gb2.add(jrNao2);
        jrNao2.setOpaque(false);
        getContentPane().add(jrNao2, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 210, -1, 20));

        gb21.add(jrNao21);
        jrNao21.setOpaque(false);
        getContentPane().add(jrNao21, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 230, -1, 20));

        gb21.add(jrSim21);
        jrSim21.setSelected(true);
        jrSim21.setOpaque(false);
        getContentPane().add(jrSim21, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 230, 30, 20));

        gb22.add(jrNao22);
        jrNao22.setOpaque(false);
        getContentPane().add(jrNao22, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 250, -1, 20));

        gb22.add(jrSim22);
        jrSim22.setSelected(true);
        jrSim22.setOpaque(false);
        getContentPane().add(jrSim22, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 250, 30, 20));

        gb23.add(jrNao23);
        jrNao23.setToolTipText("");
        jrNao23.setOpaque(false);
        getContentPane().add(jrNao23, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 270, -1, 20));

        gb23.add(jrSim23);
        jrSim23.setSelected(true);
        jrSim23.setToolTipText("");
        jrSim23.setOpaque(false);
        getContentPane().add(jrSim23, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 270, 30, 20));

        gb3.add(jrNao3);
        jrNao3.setOpaque(false);
        getContentPane().add(jrNao3, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 300, -1, 20));

        gb3.add(jrSim3);
        jrSim3.setSelected(true);
        jrSim3.setOpaque(false);
        getContentPane().add(jrSim3, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 300, 30, 20));

        gb31.add(jrNao31);
        jrNao31.setOpaque(false);
        getContentPane().add(jrNao31, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 320, -1, 20));

        gb31.add(jrSim31);
        jrSim31.setSelected(true);
        jrSim31.setOpaque(false);
        getContentPane().add(jrSim31, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 320, 30, 20));

        gb32.add(jrNao32);
        jrNao32.setToolTipText("");
        jrNao32.setOpaque(false);
        getContentPane().add(jrNao32, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 340, -1, 20));

        gb32.add(jrSim32);
        jrSim32.setSelected(true);
        jrSim32.setToolTipText("");
        jrSim32.setOpaque(false);
        getContentPane().add(jrSim32, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 340, 30, 20));

        gb4.add(jrNao4);
        jrNao4.setOpaque(false);
        getContentPane().add(jrNao4, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 370, -1, 20));

        gb4.add(jrSim4);
        jrSim4.setSelected(true);
        jrSim4.setOpaque(false);
        getContentPane().add(jrSim4, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 370, 30, 20));

        gb41.add(jrNao41);
        jrNao41.setOpaque(false);
        getContentPane().add(jrNao41, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 390, -1, 20));

        gb41.add(jrSim41);
        jrSim41.setSelected(true);
        jrSim41.setOpaque(false);
        getContentPane().add(jrSim41, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 390, 30, 20));

        gb42.add(jrNao42);
        jrNao42.setToolTipText("");
        jrNao42.setOpaque(false);
        getContentPane().add(jrNao42, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 410, -1, 20));

        gb42.add(jrSim42);
        jrSim42.setSelected(true);
        jrSim42.setToolTipText("");
        jrSim42.setOpaque(false);
        getContentPane().add(jrSim42, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 410, 30, 20));

        gb5.add(jrNao5);
        jrNao5.setOpaque(false);
        getContentPane().add(jrNao5, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 100, -1, 20));

        gb5.add(jrSim5);
        jrSim5.setSelected(true);
        jrSim5.setOpaque(false);
        getContentPane().add(jrSim5, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 100, 30, 20));

        gb51.add(jrNao51);
        jrNao51.setOpaque(false);
        getContentPane().add(jrNao51, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 120, -1, 20));

        gb51.add(jrSim51);
        jrSim51.setSelected(true);
        jrSim51.setOpaque(false);
        getContentPane().add(jrSim51, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 120, 30, 20));

        gb52.add(jrNao52);
        jrNao52.setToolTipText("");
        jrNao52.setOpaque(false);
        getContentPane().add(jrNao52, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 140, -1, 20));

        gb52.add(jrSim52);
        jrSim52.setSelected(true);
        jrSim52.setToolTipText("");
        jrSim52.setOpaque(false);
        getContentPane().add(jrSim52, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 140, 30, 20));

        gb6.add(jrNao6);
        jrNao6.setOpaque(false);
        getContentPane().add(jrNao6, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 170, -1, 20));

        gb6.add(jrSim6);
        jrSim6.setSelected(true);
        jrSim6.setOpaque(false);
        getContentPane().add(jrSim6, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 170, 30, 20));

        gb61.add(jrNao61);
        jrNao61.setOpaque(false);
        getContentPane().add(jrNao61, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 190, -1, 20));

        gb61.add(jrSim61);
        jrSim61.setSelected(true);
        jrSim61.setOpaque(false);
        getContentPane().add(jrSim61, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 190, 30, 20));

        gb62.add(jrNao62);
        jrNao62.setToolTipText("");
        jrNao62.setOpaque(false);
        getContentPane().add(jrNao62, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 210, -1, 20));

        gb62.add(jrSim62);
        jrSim62.setSelected(true);
        jrSim62.setToolTipText("");
        jrSim62.setOpaque(false);
        getContentPane().add(jrSim62, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 210, 30, 20));

        gb7.add(jrNao7);
        jrNao7.setOpaque(false);
        getContentPane().add(jrNao7, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 240, -1, 20));

        gb7.add(jrSim7);
        jrSim7.setSelected(true);
        jrSim7.setOpaque(false);
        getContentPane().add(jrSim7, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 240, 30, 20));

        gb71.add(jrNao71);
        jrNao71.setOpaque(false);
        getContentPane().add(jrNao71, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 260, -1, 20));

        gb71.add(jrSim71);
        jrSim71.setSelected(true);
        jrSim71.setOpaque(false);
        getContentPane().add(jrSim71, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 260, 30, 20));

        gb72.add(jrNao72);
        jrNao72.setToolTipText("");
        jrNao72.setOpaque(false);
        getContentPane().add(jrNao72, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 280, -1, 20));

        gb72.add(jrSim72);
        jrSim72.setSelected(true);
        jrSim72.setToolTipText("");
        jrSim72.setOpaque(false);
        getContentPane().add(jrSim72, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 280, 30, 20));

        gb73.add(jrNao73);
        jrNao73.setToolTipText("");
        jrNao73.setOpaque(false);
        getContentPane().add(jrNao73, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 300, -1, 20));

        gb73.add(jrSim73);
        jrSim73.setSelected(true);
        jrSim73.setToolTipText("");
        jrSim73.setOpaque(false);
        getContentPane().add(jrSim73, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 300, 30, 20));

        gb8.add(jrNao8);
        jrNao8.setToolTipText("");
        jrNao8.setOpaque(false);
        getContentPane().add(jrNao8, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 330, -1, 20));

        gb8.add(jrSim8);
        jrSim8.setSelected(true);
        jrSim8.setToolTipText("");
        jrSim8.setOpaque(false);
        getContentPane().add(jrSim8, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 330, 30, 20));

        jButton1.setText("Cancelar");
        jButton1.setFocusPainted(false);
        jButton1.setFocusable(false);
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 390, -1, 40));

        jbSalvar.setText("Salvar");
        jbSalvar.setFocusPainted(false);
        jbSalvar.setFocusable(false);
        jbSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSalvarActionPerformed(evt);
            }
        });
        getContentPane().add(jbSalvar, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 390, 80, 40));

        jLabel30.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel30.setText("NÃO");
        getContentPane().add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 80, 30, -1));

        jLabel31.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel31.setText("Módulo");
        getContentPane().add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 80, 280, -1));

        jLabel32.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel32.setText("SIM");
        getContentPane().add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 80, 40, -1));

        jLabel33.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel33.setText("Usuário");
        getContentPane().add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, 250, -1));

        jLabel34.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel34.setText("Codigo");
        getContentPane().add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 90, -1));

        jlUsuario.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jlUsuario.setText("Usuário padrão");
        getContentPane().add(jlUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, 250, -1));

        jlCodUsu.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jlCodUsu.setText("000000");
        getContentPane().add(jlCodUsu, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 90, -1));

        jlFundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENS/fundo padrão II.png"))); // NOI18N
        getContentPane().add(jlFundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 0, 820, 510));

        setSize(new java.awt.Dimension(815, 483));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public void buscarControleAcessoDoUsuario(int codusuario) throws IOException, BiffException, SQLException {

        OperacaoBancoControleAcesso oba = new OperacaoBancoControleAcesso();
        oba.obterConexao();
        oba.executaSql("SELECT * FROM controleacesso WHERE ceusuario = " + codusuario);

        try {

            oba.rs.first();
            do {

                int modulo = oba.rs.getInt("modulo");
                int situacao = oba.rs.getInt("situacao");
                switch (modulo) {
                    case 1:
                        if (situacao == 1) {
                            jrSim1.setSelected(true);
                            jrNao1.setSelected(false);
                        } else {
                            jrSim1.setSelected(false);
                            jrNao1.setSelected(true);
                        }
                        
                        case 11:
                        if (situacao == 1) {
                            jrSim11.setSelected(true);
                            jrNao11.setSelected(false);
                        } else {
                            jrSim11.setSelected(false);
                            jrNao11.setSelected(true);
                        }
                        
                        case 12:
                        if (situacao == 1) {
                            jrSim12.setSelected(true);
                            jrNao12.setSelected(false);
                        } else {
                            jrSim12.setSelected(false);
                            jrNao12.setSelected(true);
                        }
                        
                        case 13:
                        if (situacao == 1) {
                            jrSim13.setSelected(true);
                            jrNao13.setSelected(false);
                        } else {
                            jrSim13.setSelected(false);
                            jrNao13.setSelected(true);
                        }
                        
                        case 14:
                        if (situacao == 1) {
                            jrSim14.setSelected(true);
                            jrNao14.setSelected(false);
                        } else {
                            jrSim14.setSelected(false);
                            jrNao14.setSelected(true);
                        }
                        
                        case 2:
                        if (situacao == 1) {
                            jrSim2.setSelected(true);
                            jrNao2.setSelected(false);
                        } else {
                            jrSim2.setSelected(false);
                            jrNao2.setSelected(true);
                        }
                        
                        case 21:
                        if (situacao == 1) {
                            jrSim21.setSelected(true);
                            jrNao21.setSelected(false);
                        } else {
                            jrSim21.setSelected(false);
                            jrNao21.setSelected(true);
                        }
                        
                        case 22:
                        if (situacao == 1) {
                            jrSim22.setSelected(true);
                            jrNao22.setSelected(false);
                        } else {
                            jrSim22.setSelected(false);
                            jrNao22.setSelected(true);
                        }
                        
                        case 23:
                        if (situacao == 1) {
                            jrSim23.setSelected(true);
                            jrNao23.setSelected(false);
                        } else {
                            jrSim23.setSelected(false);
                            jrNao23.setSelected(true);
                        }
                        
                        case 3:
                        if (situacao == 1) {
                            jrSim3.setSelected(true);
                            jrNao3.setSelected(false);
                        } else {
                            jrSim3.setSelected(false);
                            jrNao3.setSelected(true);
                        }
                        
                        case 31:
                        if (situacao == 1) {
                            jrSim31.setSelected(true);
                            jrNao31.setSelected(false);
                        } else {
                            jrSim31.setSelected(false);
                            jrNao31.setSelected(true);
                        }
                        
                        case 32:
                        if (situacao == 1) {
                            jrSim32.setSelected(true);
                            jrNao32.setSelected(false);
                        } else {
                            jrSim32.setSelected(false);
                            jrNao32.setSelected(true);
                        }
                        
                        case 4:
                        if (situacao == 1) {
                            jrSim4.setSelected(true);
                            jrNao4.setSelected(false);
                        } else {
                            jrSim4.setSelected(false);
                            jrNao4.setSelected(true);
                        }
                        
                        case 41:
                        if (situacao == 1) {
                            jrSim41.setSelected(true);
                            jrNao41.setSelected(false);
                        } else {
                            jrSim41.setSelected(false);
                            jrNao41.setSelected(true);
                        }

                        case 42:
                        if (situacao == 1) {
                            jrSim42.setSelected(true);
                            jrNao42.setSelected(false);
                        } else {
                            jrSim42.setSelected(false);
                            jrNao42.setSelected(true);
                        }
                        
                        case 5:
                        if (situacao == 1) {
                            jrSim5.setSelected(true);
                            jrNao5.setSelected(false);
                        } else {
                            jrSim5.setSelected(false);
                            jrNao5.setSelected(true);
                        }
                        
                        case 51:
                        if (situacao == 1) {
                            jrSim51.setSelected(true);
                            jrNao51.setSelected(false);
                        } else {
                            jrSim51.setSelected(false);
                            jrNao51.setSelected(true);
                        }
                        
                        case 52:
                        if (situacao == 1) {
                            jrSim52.setSelected(true);
                            jrNao52.setSelected(false);
                        } else {
                            jrSim52.setSelected(false);
                            jrNao52.setSelected(true);
                        }
                        
                        case 6:
                        if (situacao == 1) {
                            jrSim6.setSelected(true);
                            jrNao6.setSelected(false);
                        } else {
                            jrSim6.setSelected(false);
                            jrNao6.setSelected(true);
                        }

                        case 61:
                        if (situacao == 1) {
                            jrSim61.setSelected(true);
                            jrNao61.setSelected(false);
                        } else {
                            jrSim61.setSelected(false);
                            jrNao61.setSelected(true);
                        }
                        
                        case 62:
                        if (situacao == 1) {
                            jrSim62.setSelected(true);
                            jrNao62.setSelected(false);
                        } else {
                            jrSim62.setSelected(false);
                            jrNao62.setSelected(true);
                        }
                        
                        case 7:
                        if (situacao == 1) {
                            jrSim7.setSelected(true);
                            jrNao7.setSelected(false);
                        } else {
                            jrSim7.setSelected(false);
                            jrNao7.setSelected(true);
                        }
                        
                        case 71:
                        if (situacao == 1) {
                            jrSim71.setSelected(true);
                            jrNao71.setSelected(false);
                        } else {
                            jrSim71.setSelected(false);
                            jrNao71.setSelected(true);
                        }
                        
                        case 72:
                        if (situacao == 1) {
                            jrSim72.setSelected(true);
                            jrNao72.setSelected(false);
                        } else {
                            jrSim72.setSelected(false);
                            jrNao72.setSelected(true);
                        }
                        
                        case 73:
                        if (situacao == 1) {
                            jrSim73.setSelected(true);
                            jrNao73.setSelected(false);
                        } else {
                            jrSim73.setSelected(false);
                            jrNao73.setSelected(true);
                        }
                        
                        case 8:
                        if (situacao == 1) {
                            jrSim8.setSelected(true);
                            jrNao8.setSelected(false);
                        } else {
                            jrSim8.setSelected(false);
                            jrNao8.setSelected(true);
                        }
                }

            } while (oba.rs.next());

        } catch (SQLException a) {

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO BUSCA CONTROLE: " + e.getMessage() + e.getStackTrace());
        }

        oba.fechaConexao();
    }

    private void jbSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSalvarActionPerformed

        try {

            if (jrNao1.isSelected()) {
                salvarControleAcesso(this.codusu, MOD1, NAO);
            } else if (jrSim1.isSelected()) {
                salvarControleAcesso(this.codusu, MOD1, SIM);
            }

            if (jrNao11.isSelected()) {
                salvarControleAcesso(this.codusu, MOD11, NAO);
            } else if (jrSim11.isSelected()) {
                salvarControleAcesso(this.codusu, MOD11, SIM);
            }

            if (jrNao12.isSelected()) {
                salvarControleAcesso(this.codusu, MOD12, NAO);
            } else if (jrSim12.isSelected()) {
                salvarControleAcesso(this.codusu, MOD12, SIM);
            }

            if (jrNao13.isSelected()) {
                salvarControleAcesso(this.codusu, MOD13, NAO);
            } else if (jrSim13.isSelected()) {
                salvarControleAcesso(this.codusu, MOD13, SIM);
            }

            if (jrNao14.isSelected()) {
                salvarControleAcesso(this.codusu, MOD14, NAO);
            } else if (jrSim14.isSelected()) {
                salvarControleAcesso(this.codusu, MOD14, SIM);
            }

            if (jrNao2.isSelected()) {
                salvarControleAcesso(this.codusu, MOD2, NAO);
            } else if (jrSim2.isSelected()) {
                salvarControleAcesso(this.codusu, MOD2, SIM);
            }

            if (jrNao21.isSelected()) {
                salvarControleAcesso(this.codusu, MOD21, NAO);
            } else if (jrSim21.isSelected()) {
                salvarControleAcesso(this.codusu, MOD21, SIM);
            }

            if (jrNao22.isSelected()) {
                salvarControleAcesso(this.codusu, MOD22, NAO);
            } else if (jrSim22.isSelected()) {
                salvarControleAcesso(this.codusu, MOD22, SIM);
            }

            if (jrNao23.isSelected()) {
                salvarControleAcesso(this.codusu, MOD23, NAO);
            } else if (jrSim23.isSelected()) {
                salvarControleAcesso(this.codusu, MOD23, SIM);
            }

            if (jrNao3.isSelected()) {
                salvarControleAcesso(this.codusu, MOD3, NAO);
            } else if (jrSim3.isSelected()) {
                salvarControleAcesso(this.codusu, MOD3, SIM);
            }

            if (jrNao31.isSelected()) {
                salvarControleAcesso(this.codusu, MOD31, NAO);
            } else if (jrSim31.isSelected()) {
                salvarControleAcesso(this.codusu, MOD31, SIM);
            }

            if (jrNao32.isSelected()) {
                salvarControleAcesso(this.codusu, MOD32, NAO);
            } else if (jrSim32.isSelected()) {
                salvarControleAcesso(this.codusu, MOD32, SIM);
            }

            if (jrNao4.isSelected()) {
                salvarControleAcesso(this.codusu, MOD4, NAO);
            } else if (jrSim4.isSelected()) {
                salvarControleAcesso(this.codusu, MOD4, SIM);
            }

            if (jrNao41.isSelected()) {
                salvarControleAcesso(this.codusu, MOD41, NAO);
            } else if (jrSim41.isSelected()) {
                salvarControleAcesso(this.codusu, MOD41, SIM);
            }

            if (jrNao42.isSelected()) {
                salvarControleAcesso(this.codusu, MOD42, NAO);
            } else if (jrSim42.isSelected()) {
                salvarControleAcesso(this.codusu, MOD42, SIM);
            }

            if (jrNao5.isSelected()) {
                salvarControleAcesso(this.codusu, MOD5, NAO);
            } else if (jrSim5.isSelected()) {
                salvarControleAcesso(this.codusu, MOD5, SIM);
            }

            if (jrNao51.isSelected()) {
                salvarControleAcesso(this.codusu, MOD51, NAO);
            } else if (jrSim51.isSelected()) {
                salvarControleAcesso(this.codusu, MOD51, SIM);
            }
            
            if (jrNao52.isSelected()) {
                salvarControleAcesso(this.codusu, MOD52, NAO);
            } else if (jrSim52.isSelected()) {
                salvarControleAcesso(this.codusu, MOD52, SIM);
            }

            if (jrNao6.isSelected()) {
                salvarControleAcesso(this.codusu, MOD6, NAO);
            } else if (jrSim6.isSelected()) {
                salvarControleAcesso(this.codusu, MOD6, SIM);
            }

            if (jrNao61.isSelected()) {
                salvarControleAcesso(this.codusu, MOD61, NAO);
            } else if (jrSim61.isSelected()) {
                salvarControleAcesso(this.codusu, MOD61, SIM);
            }

            if (jrNao62.isSelected()) {
                salvarControleAcesso(this.codusu, MOD62, NAO);
            } else if (jrSim62.isSelected()) {
                salvarControleAcesso(this.codusu, MOD62, SIM);
            }

            if (jrNao7.isSelected()) {
                salvarControleAcesso(this.codusu, MOD7, NAO);
            } else if (jrSim7.isSelected()) {
                salvarControleAcesso(this.codusu, MOD7, SIM);
            }

            if (jrNao71.isSelected()) {
                salvarControleAcesso(this.codusu, MOD71, NAO);
            } else if (jrSim71.isSelected()) {
                salvarControleAcesso(this.codusu, MOD71, SIM);
            }

            if (jrNao72.isSelected()) {
                salvarControleAcesso(this.codusu, MOD72, NAO);
            } else if (jrSim72.isSelected()) {
                salvarControleAcesso(this.codusu, MOD72, SIM);
            }

            if (jrNao73.isSelected()) {
                salvarControleAcesso(this.codusu, MOD73, NAO);
            } else if (jrSim73.isSelected()) {
                salvarControleAcesso(this.codusu, MOD73, SIM);
            }

            if (jrNao8.isSelected()) {
                salvarControleAcesso(this.codusu, MOD8, NAO);
            } else if (jrSim8.isSelected()) {
                salvarControleAcesso(this.codusu, MOD8, SIM);
            }

            JOptionPane.showMessageDialog(null, "Salvo com sucesso ");

        } catch (IOException ex) {
            Logger.getLogger(TelaControleAcesso.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(TelaControleAcesso.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TelaControleAcesso.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_jbSalvarActionPerformed

    public void salvarControleAcesso(int codigo, int modulo, int situacao) throws IOException, BiffException, SQLException, SQLException {
        OperacaoBanco.OperacaoBancoControleAcesso oba = new OperacaoBancoControleAcesso();
        MODEL.ModelControleAcesso controle = new ModelControleAcesso();
        MODEL.ModelUsuarios usuario = new ModelUsuarios();

        usuario.setCodusu(codigo);
        controle.setUsuarios(usuario);
        controle.setModulo(modulo);
        controle.setSituacao(situacao);

        oba.salvarControle(controle);
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
            java.util.logging.Logger.getLogger(TelaControleAcesso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaControleAcesso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaControleAcesso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaControleAcesso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new TelaControleAcesso().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(TelaControleAcesso.class.getName()).log(Level.SEVERE, null, ex);
                } catch (BiffException ex) {
                    Logger.getLogger(TelaControleAcesso.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup gb1;
    private javax.swing.ButtonGroup gb11;
    private javax.swing.ButtonGroup gb12;
    private javax.swing.ButtonGroup gb13;
    private javax.swing.ButtonGroup gb14;
    private javax.swing.ButtonGroup gb2;
    private javax.swing.ButtonGroup gb21;
    private javax.swing.ButtonGroup gb22;
    private javax.swing.ButtonGroup gb23;
    private javax.swing.ButtonGroup gb3;
    private javax.swing.ButtonGroup gb31;
    private javax.swing.ButtonGroup gb32;
    private javax.swing.ButtonGroup gb4;
    private javax.swing.ButtonGroup gb41;
    private javax.swing.ButtonGroup gb42;
    private javax.swing.ButtonGroup gb5;
    private javax.swing.ButtonGroup gb51;
    private javax.swing.ButtonGroup gb52;
    private javax.swing.ButtonGroup gb6;
    private javax.swing.ButtonGroup gb61;
    private javax.swing.ButtonGroup gb62;
    private javax.swing.ButtonGroup gb7;
    private javax.swing.ButtonGroup gb71;
    private javax.swing.ButtonGroup gb72;
    private javax.swing.ButtonGroup gb73;
    private javax.swing.ButtonGroup gb8;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JButton jbSalvar;
    private javax.swing.JLabel jlCodUsu;
    private javax.swing.JLabel jlFundo;
    private javax.swing.JLabel jlTitulo;
    private javax.swing.JLabel jlUsuario;
    private javax.swing.JRadioButton jrNao1;
    private javax.swing.JRadioButton jrNao11;
    private javax.swing.JRadioButton jrNao12;
    private javax.swing.JRadioButton jrNao13;
    private javax.swing.JRadioButton jrNao14;
    private javax.swing.JRadioButton jrNao2;
    private javax.swing.JRadioButton jrNao21;
    private javax.swing.JRadioButton jrNao22;
    private javax.swing.JRadioButton jrNao23;
    private javax.swing.JRadioButton jrNao3;
    private javax.swing.JRadioButton jrNao31;
    private javax.swing.JRadioButton jrNao32;
    private javax.swing.JRadioButton jrNao4;
    private javax.swing.JRadioButton jrNao41;
    private javax.swing.JRadioButton jrNao42;
    private javax.swing.JRadioButton jrNao5;
    private javax.swing.JRadioButton jrNao51;
    private javax.swing.JRadioButton jrNao52;
    private javax.swing.JRadioButton jrNao6;
    private javax.swing.JRadioButton jrNao61;
    private javax.swing.JRadioButton jrNao62;
    private javax.swing.JRadioButton jrNao7;
    private javax.swing.JRadioButton jrNao71;
    private javax.swing.JRadioButton jrNao72;
    private javax.swing.JRadioButton jrNao73;
    private javax.swing.JRadioButton jrNao8;
    private javax.swing.JRadioButton jrSim1;
    private javax.swing.JRadioButton jrSim11;
    private javax.swing.JRadioButton jrSim12;
    private javax.swing.JRadioButton jrSim13;
    private javax.swing.JRadioButton jrSim14;
    private javax.swing.JRadioButton jrSim2;
    private javax.swing.JRadioButton jrSim21;
    private javax.swing.JRadioButton jrSim22;
    private javax.swing.JRadioButton jrSim23;
    private javax.swing.JRadioButton jrSim3;
    private javax.swing.JRadioButton jrSim31;
    private javax.swing.JRadioButton jrSim32;
    private javax.swing.JRadioButton jrSim4;
    private javax.swing.JRadioButton jrSim41;
    private javax.swing.JRadioButton jrSim42;
    private javax.swing.JRadioButton jrSim5;
    private javax.swing.JRadioButton jrSim51;
    private javax.swing.JRadioButton jrSim52;
    private javax.swing.JRadioButton jrSim6;
    private javax.swing.JRadioButton jrSim61;
    private javax.swing.JRadioButton jrSim62;
    private javax.swing.JRadioButton jrSim7;
    private javax.swing.JRadioButton jrSim71;
    private javax.swing.JRadioButton jrSim72;
    private javax.swing.JRadioButton jrSim73;
    private javax.swing.JRadioButton jrSim8;
    // End of variables declaration//GEN-END:variables
}
