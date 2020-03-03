
package MODEL;

import java.util.Date;

public class ModelMovimento {
    private int cpregistro;
    private int cecodigoxml;
    private ModelXml modelxml;
    private int cecodcli;
    private ModelClientes modelclientes;
    private int cecodpro;
    private ModelProdutos modelprodutos;
    private int qtdunprod;
    private int qtduntotal;
    private float valorun;
    private float valortotal;
    private int cfop;
    private String tipomovimento;
    private String cpfcnpj;
    private String unproduto;
    private int estoquenoregistro;
    private Date dtemissao;
    

    public int getCpregistro() {
        return cpregistro;
    }

    public void setCpregistro(int cpregistro) {
        this.cpregistro = cpregistro;
    }
    public int getCecodpro() {
        return cecodpro;
    }

    public void setCecodpro(int cecodpro) {
        this.cecodpro = cecodpro;
    }

    public int getQtdunprod() {
        return qtdunprod;
    }

    public void setQtdunprod(int qtdunprod) {
        this.qtdunprod = qtdunprod;
    }

    public int getQtduntotal() {
        return qtduntotal;
    }

    public void setQtduntotal(int qtduntotal) {
        this.qtduntotal = qtduntotal;
    }

    public float getValorun() {
        return valorun;
    }

    public void setValorun(float valorun) {
        this.valorun = valorun;
    }

    public float getValortotal() {
        return valortotal;
    }

    public void setValortotal(float valortotal) {
        this.valortotal = valortotal;
    }

    public int getCfop() {
        return cfop;
    }

    public void setCfop(int cfop) {
        this.cfop = cfop;
    }

    public String getTipomovimento() {
        return tipomovimento;
    }

    public void setTipomovimento(String tipomovimento) {
        this.tipomovimento = tipomovimento;
    }

    public void getCecodcli(String textContent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getCpfcnpj() {
        return cpfcnpj;
    }

    public void setCpfcnpj(String cpfcnpj) {
        this.cpfcnpj = cpfcnpj;
    }

    public String getUnproduto() {
        return unproduto;
    }

    public void setUnproduto(String unproduto) {
        this.unproduto = unproduto;
    }

    public ModelXml getModelxml() {
        return modelxml;
    }

    public void setModelxml(ModelXml modelxml) {
        this.modelxml = modelxml;
    }

    public ModelClientes getModelclientes() {
        return modelclientes;
    }

    public void setModelclientes(ModelClientes modelclientes) {
        this.modelclientes = modelclientes;
    }

    public ModelProdutos getModelprodutos() {
        return modelprodutos;
    }

    public void setModelprodutos(ModelProdutos modelprodutos) {
        this.modelprodutos = modelprodutos;
    }

    public int getCecodigoxml() {
        return cecodigoxml;
    }

    public void setCecodigoxml(int cecodigoxml) {
        this.cecodigoxml = cecodigoxml;
    }

    public int getCecodcli() {
        return cecodcli;
    }

    public void setCecodcli(int cecodcli) {
        this.cecodcli = cecodcli;
    }

    public int getEstoquenoregistro() {
        return estoquenoregistro;
    }

    public void setEstoquenoregistro(int estoquenoregistro) {
        this.estoquenoregistro = estoquenoregistro;
    }

    public Date getDtemissao() {
        return dtemissao;
    }

    public void setDtemissao(Date dtemissao) {
        this.dtemissao = dtemissao;
    }
}
