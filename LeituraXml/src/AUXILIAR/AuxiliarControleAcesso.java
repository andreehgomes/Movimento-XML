package AUXILIAR;

import OperacaoBanco.OperacaoBancoControleAcesso;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import jxl.read.biff.BiffException;

public class AuxiliarControleAcesso {

    public boolean controle(int codusu, int mod) throws IOException, BiffException, SQLException {

        OperacaoBanco.OperacaoBancoControleAcesso oba = new OperacaoBancoControleAcesso();
        oba.obterConexao();
        oba.executaSql("SELECT * FROM controleacesso WHERE ceusuario = " + codusu + " AND modulo = " + mod);
        oba.rs.first();

        try {

            if (oba.rs.getInt("situacao") == 0) {
                JOptionPane.showMessageDialog(null, "Usuário sem permissão para acessar essa "
                        + "função ou módulo, consulte o responsável.");

                oba.fechaConexao();
                return false;
            } else {
                oba.fechaConexao();
                return true;
            }

        } catch (Exception e) {
            return false;
        }

    }

}
