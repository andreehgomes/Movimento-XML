/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MODEL;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;

public class ModelLog {
    private int codigoregistro;
    private String usuario;
    private String modulo;
    private String acao;
    private Long codigoobjeto;
    private Timestamp dtlog;

    public int getCodigoregistro() {
        return codigoregistro;
    }

    public void setCodigoregistro(int codigoregistro) {
        this.codigoregistro = codigoregistro;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public Long getCodigoobjeto() {
        return codigoobjeto;
    }

    public void setCodigoobjeto(Long codigoobjeto) {
        this.codigoobjeto = codigoobjeto;
    }

    public Timestamp getDtlog() {
        return dtlog;
    }

    public void setDtlog(Timestamp dtlog) {
        this.dtlog = dtlog;
    }
}
