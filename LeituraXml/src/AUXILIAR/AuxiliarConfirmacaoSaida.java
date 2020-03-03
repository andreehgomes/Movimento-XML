/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AUXILIAR;

import static groovy.ui.text.FindReplaceUtility.dispose;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class AuxiliarConfirmacaoSaida {

public void saida(int flag, String mensagem1, String mensagem2, JFrame frame){
    if (flag == 0) {
            dispose();
        } else if (flag == 1) {

            int resp = JOptionPane.showConfirmDialog(null, mensagem1, null, 0);
            if (resp == 0) {
                dispose();
            }
        } else if (flag == 2) {
            int resp = JOptionPane.showConfirmDialog(null, mensagem2, null, 0);
            if (resp == 0) {
                dispose();
            }
        }
}    

}
