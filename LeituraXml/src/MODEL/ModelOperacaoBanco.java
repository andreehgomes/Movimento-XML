
package MODEL;


public class ModelOperacaoBanco {
    private String caminho;

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }
}


/*
        ModelOperacaoBanco mob = new ModelOperacaoBanco();
        
        String csvArquivo = "C:\\Anve\\base.csv";
        BufferedReader conteudoCSV = null;
        String linha = "";
        String csvSeparadorDeCampo = ",";        

        try {
            conteudoCSV = new BufferedReader(new FileReader(csvArquivo));
            while ((linha = conteudoCSV.readLine()) != null) {
                if (linha == null) {
                    break;
                }

                System.out.println("teste" + linha);
                String[] moeda = linha.split(csvSeparadorDeCampo);

                try {                    
                    mob.setCaminho(moeda[1]);

                } catch (Exception erro) {
                    System.out.println("ERRO: " + erro);
                    break;
                }
                break;
            };

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado" + e.getMessage());
        } 
*/
