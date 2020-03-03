/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VIEW;

import MODEL.ModelClientes;
import MODEL.ModelMovimento;
import MODEL.ModelOperacaoBanco;
import OperacaoBanco.OperacaoBancoClientes;
import OperacaoBanco.OperacaoLeituraArquivoBase;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import jxl.read.biff.BiffException;

/**
 *
 * @author André Felipe
 */
public class LeituraArquivos extends javax.swing.JFrame {

    /**
     * Creates new form LeituraArquivos
     */
    public LeituraArquivos() {
        initComponents();
    }
    String caminhoClientes = "";
    String[] moeda = null;

    public void lerArquivo(String caminho) throws IOException, BiffException {
        MODEL.ModelClientes clientes = new ModelClientes();
        OperacaoBanco.OperacaoBancoClientes obc = new OperacaoBancoClientes();

        String csvArquivo = caminho;
        BufferedReader conteudoCSV = null;
        String linha = "";
        String csvSeparadorDeCampo = ";";
        int teste = 0;
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
                        + "Taxa Financ;Etiq;MotBlq;CPF Cont;Dt.Nascto;Dt.Ult.Compra;Codigo-BW;Coorden-X;Coorden-Y;Regiao+Seq;Email-NF-e;") {

                    if (linha == null) {
                        break;
                    }

                    System.out.println("teste" + linha);
                    String[] moeda = linha.split(csvSeparadorDeCampo);

                    /*+ " CNPJ / CPF: " + moeda[2]
                + " Razão Social: " + moeda[4]
                + " Nome Fantasia: " + moeda[3]
                + " Cidade: " + moeda[51]        
                + " Bairro: " + moeda[52]
                + " Logradouro: " + moeda[48]
                + " CEP: " + moeda[54]
                + " UF: " + moeda[53]);*/
                    clientes.setCpcodcli(Integer.parseInt(moeda[103]));
                    clientes.setCpfcnpj(moeda[2]);
                    clientes.setRazaosocial(moeda[4]);
                    clientes.setNomefantasia(moeda[3]);
                    clientes.setCidade(moeda[51]);
                    clientes.setBairro(moeda[52]);
                    clientes.setLogradouro(moeda[48]);
                    clientes.setCep(Integer.parseInt(moeda[54]));
                    clientes.setUf(moeda[53]);
                    obc.incluirClientes(clientes);
                    System.out.println("PASSOU");

                }
            };
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado" + e.getMessage());
        }

        /*int tamanho = moeda.length;
            for(int i = 1; i <= tamanho; i++ ){
                System.out.println("CONTROLE: " + i + " TAMANHO: " + tamanho);
                clientes.setCpcodcli(Integer.parseInt(moeda[103]));
                clientes.setCpfcnpj(moeda[2]);
                clientes.setRazaosocial(moeda[4]);
                clientes.setNomefantasia(moeda[3]);
                clientes.setCidade(moeda[51]);
                clientes.setBairro(moeda[52]);
                clientes.setLogradouro(moeda[48]);
                clientes.setCep(Integer.parseInt(moeda[54]));
                clientes.setUf(moeda[53]);                
                obc.incluirClientes(clientes);
                System.out.println("PASSOU");
            }
            System.out.println("Inclusao Realizada");  */
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jtfCaminhoClientes = new javax.swing.JTextField();
        jbSelecionarClientes = new javax.swing.JButton();
        jbImportar = new javax.swing.JButton();
        jlLog = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtpLog = new javax.swing.JTextPane();
        jtfLog = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jbSelecionarClientes.setText("SELECIONAR");
        jbSelecionarClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSelecionarClientesActionPerformed(evt);
            }
        });

        jbImportar.setText("IMPORTAR");
        jbImportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbImportarActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(jtpLog);

        jtfLog.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jlLog, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jtfCaminhoClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jbImportar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbSelecionarClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jScrollPane1)
                    .addComponent(jtfLog))
                .addContainerGap(84, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfCaminhoClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbSelecionarClientes))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbImportar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlLog, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jtfLog, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(567, 351));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    private void jbSelecionarClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSelecionarClientesActionPerformed
        /*JFileChooser buscaArquivo = new JFileChooser();
        //buscaArquivo.setFileFilter(new FileNameExtensionFilter("CSV"));
        buscaArquivo.setAcceptAllFileFilterUsed(false);
        buscaArquivo.setDialogTitle("Buscar Arquivo");
        buscaArquivo.showOpenDialog(this);

        caminhoClientes = "" + buscaArquivo.getSelectedFile().getAbsolutePath();
        jtfCaminhoClientes.setText(caminhoClientes);*/

        
        
        // TESTANDO LEITURA DA BASE
        
        OperacaoBanco.OperacaoLeituraArquivoBase opa = new OperacaoLeituraArquivoBase();
        MODEL.ModelOperacaoBanco op = new ModelOperacaoBanco();
        try {
            String caminho = opa.lerArquivoBase();
            System.out.println("TESTE: " + caminho);
            
        } catch (IOException ex) {
            Logger.getLogger(LeituraArquivos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        

    }//GEN-LAST:event_jbSelecionarClientesActionPerformed

    private void jbImportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbImportarActionPerformed
        TelaCarregando tela = new TelaCarregando();
        tela.setVisible(true);

        Thread t = new Thread() {
            @Override
            public void run() {

                try {
                    lerArquivo(caminhoClientes);
                } catch (IOException ex) {
                    Logger.getLogger(LeituraArquivos.class.getName()).log(Level.SEVERE, null, ex);
                } catch (BiffException ex) {
                    Logger.getLogger(LeituraArquivos.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                tela.dispose();
            }
        };
        
        t.start();
    }//GEN-LAST:event_jbImportarActionPerformed

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
            java.util.logging.Logger.getLogger(LeituraArquivos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LeituraArquivos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LeituraArquivos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LeituraArquivos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LeituraArquivos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbImportar;
    private javax.swing.JButton jbSelecionarClientes;
    private javax.swing.JLabel jlLog;
    private javax.swing.JTextField jtfCaminhoClientes;
    private javax.swing.JTextField jtfLog;
    private javax.swing.JTextPane jtpLog;
    // End of variables declaration//GEN-END:variables
}
