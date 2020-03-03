
package VIEW;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import jxl.Cell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import javax.swing.JOptionPane;public class LeituraArquivosXls {
    
    public static void main(String[] args) throws IOException, BiffException {
        
        
        String csvArquivo = "C:\\Users\\André Felipe\\Desktop\\Planilhas\\base\\CLIENTES.CSV";
        BufferedReader conteudoCSV = null;
        String linha = "";
        String csvSeparadorDeCampo = ";";
        
        try {
            conteudoCSV = new BufferedReader(new FileReader(csvArquivo));
            
            while((linha = conteudoCSV.readLine()) != null){
                String[] moeda = linha.split(csvSeparadorDeCampo);
                
                System.out.println("Região: " + moeda[0]
                + " Número: " + moeda[1]
                + " CNPJ / CPF: " + moeda[2]
                + " Nome Fantasia: " + moeda[3]);
            };
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado" + e.getMessage());
        }
        
        }
    }
    
    

