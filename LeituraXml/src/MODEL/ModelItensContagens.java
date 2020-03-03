
package MODEL;

public class ModelItensContagens {
    
    private int codigo;
    //private long codigoContagem;
    private int codigoproduto;
    private String descricao;
    private int estoque;
    private int contagem;
    private ModelContagens contagens;

    public ModelContagens getContagens() {
        return contagens;
    }

    public void setContagens(ModelContagens contagens) {
        this.contagens = contagens;
    }
   

    /*public long getCodigoContagem() {
        return codigoContagem;
    }

    public void setCodigoContagem(long codigoContagem) {
        this.codigoContagem = codigoContagem;
    }*/

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public int getContagem() {
        return contagem;
    }

    public void setContagem(int contagem) {
        this.contagem = contagem;
    }

    public int getCodigoproduto() {
        return codigoproduto;
    }

    public void setCodigoproduto(int codigoproduto) {
        this.codigoproduto = codigoproduto;
    }

   
    
}
