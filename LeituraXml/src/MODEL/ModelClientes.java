
package MODEL;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ModelClientes {
   private String cpfcnpj;
   private int cpcodcli;
   private String razaosocial;
   private String nomefantasia;
   private String cidade;
   private String bairro;
   private String logradouro;
   private int cep;
   private String uf;

    

    public int getCpcodcli() {
        return cpcodcli;
    }

    public void setCpcodcli(int cpcodcli) {
        this.cpcodcli = cpcodcli;
    }

    public String getRazaosocial() {
        return razaosocial;
    }

    public void setRazaosocial(String razaosocial) {
        this.razaosocial = razaosocial;
    }

    public String getNomefantasia() {
        return nomefantasia;
    }

    public void setNomefantasia(String nomefantasia) {
        this.nomefantasia = nomefantasia;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public int getCep() {
        return cep;
    }

    public void setCep(int cep) {
        this.cep = cep;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCpfcnpj() {
        return cpfcnpj;
    }

    public void setCpfcnpj(String cpfcnpj) {
        this.cpfcnpj = cpfcnpj;
    }
    
   public void setarValores(String a, int b){
       setNomefantasia(a);
   }
    
    public void exibirCliente(){
        System.out.println("NOME: " + getNomefantasia());
        JOptionPane.showMessageDialog(null, "NOME: " + getNomefantasia());
    }

    public void inclurClientes(JTextField nome, 
            JTextField apelido, 
            JTextField cpf, 
            JTextField rg, 
            JTextField telefone, 
            JTextField email, 
            JTextField cep, 
            JTextField cidade, 
            JTextField uf, 
            JTextField endereco, 
            JTextField bairro, 
            JTextField numero){
        
    }
    
}
