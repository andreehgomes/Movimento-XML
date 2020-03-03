/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AUXILIAR;

import MODEL.ModelLog;
import OperacaoBanco.OperacaoBancoLog;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import jxl.read.biff.BiffException;

public class AuxiliarLog {

    public void log(String usuario, String modulo, String acao, Long codigoobjeto) throws IOException, BiffException {
        Date date = new Date();
        Timestamp data3 = new Timestamp(date.getTime());

        OperacaoBanco.OperacaoBancoLog obl = new OperacaoBancoLog();
        MODEL.ModelLog log = new ModelLog();
        log.setUsuario(usuario);
        log.setModulo(modulo);
        log.setAcao(acao);
        log.setCodigoobjeto(codigoobjeto);
        log.setDtlog(data3);

        obl.incluirLog(log);
    }

}
