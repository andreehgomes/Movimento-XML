package OperacaoBanco;

import MODEL.ModelOperacaoBanco;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.System.in;
import java.util.Scanner;

public class OperacaoLeituraArquivoBase {

    public String lerArquivoBase() throws FileNotFoundException, IOException{
    
    ModelOperacaoBanco mob = new ModelOperacaoBanco();

    String csvArquivo = "C:\\Anve\\base.csv";
    BufferedReader conteudoCSV = null;
    String linha = "";
    String csvSeparadorDeCampo = ",";
    String caminho;

    
        
            conteudoCSV = new BufferedReader(new FileReader(csvArquivo));
        while ((linha = conteudoCSV.readLine()) != null) {
            if (linha == null) {
                break;
            }

            String[] moeda = linha.split(csvSeparadorDeCampo);

            try {
                caminho = (moeda[0]);
                return caminho;
            } catch (Exception erro) {
                System.out.println("ERRO: " + erro);
                break;
            }
            
        };return null;



    }
    
}
