/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VIEW;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author André Felipe
 */
public class TelaCarregando extends javax.swing.JFrame {

    /**
     * Creates new form TelaCarregando
     */
    public TelaCarregando() {
        initComponents();
        iniciaCronometro();
        
    }
    Timer timer = null; 
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar1 = new javax.swing.JProgressBar();
        jlTempo = new javax.swing.JLabel();
        jlAguarde = new javax.swing.JLabel();
        jlTempo2 = new javax.swing.JLabel();
        jlCarrengado = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Carregando");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jProgressBar1.setIndeterminate(true);
        getContentPane().add(jProgressBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, 290, -1));

        jlTempo.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        jlTempo.setForeground(new java.awt.Color(204, 51, 0));
        jlTempo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(jlTempo, new org.netbeans.lib.awtextra.AbsoluteConstraints(203, 40, 140, 23));

        jlAguarde.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        jlAguarde.setForeground(new java.awt.Color(204, 51, 0));
        jlAguarde.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlAguarde.setText("AGUARDE...");
        getContentPane().add(jlAguarde, new org.netbeans.lib.awtextra.AbsoluteConstraints(203, 11, 140, 23));

        jlTempo2.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        jlTempo2.setForeground(new java.awt.Color(204, 51, 0));
        jlTempo2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(jlTempo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(203, 60, 140, 23));

        jlCarrengado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENS/tela carregando.png"))); // NOI18N
        getContentPane().add(jlCarrengado, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 390, 140));

        setSize(new java.awt.Dimension(396, 163));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        timer.cancel();
    }//GEN-LAST:event_formWindowClosed

    public void iniciaCronometro(){
    	      
        final SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        jlTempo.setText("Início: "+format.format(new Date().getTime()));
        if (timer == null)   
         {      
             timer = new Timer();  
            TimerTask tarefa = new TimerTask() {     
                 public void run()   
                 {      
                     try {      
                         System.out.println("Hora: "+format.format(new Date().getTime()));
                         jlTempo2.setText("Término: "+format.format(new Date().getTime()));
                   } catch (Exception e) {      
                         e.printStackTrace();      
                     }      
                }   
             };      
            timer.scheduleAtFixedRate(tarefa, 0, 1000);      
        }    
    }
    
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
            java.util.logging.Logger.getLogger(TelaCarregando.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaCarregando.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaCarregando.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaCarregando.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaCarregando().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JLabel jlAguarde;
    private javax.swing.JLabel jlCarrengado;
    private javax.swing.JLabel jlTempo;
    private javax.swing.JLabel jlTempo2;
    // End of variables declaration//GEN-END:variables
}
