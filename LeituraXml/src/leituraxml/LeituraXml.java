/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leituraxml;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class LeituraXml {

    public static void main(String[] args) {
        
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            Document doc = builder.parse("D:\\41181001829764000157650020000010691201800013.xml");
            
            NodeList listaDeDet = doc.getElementsByTagName("prod"); //" det " nome do nó com os produtos do xml 4.0
            
            int tamanhoLista = listaDeDet.getLength();
            
            for (int i = 0; i < tamanhoLista; i++) {
                Node noDet = listaDeDet.item(i); //varrer XML
                
                if(noDet.getNodeType() == Node.ELEMENT_NODE){
                    Element elementoDet = (Element) noDet;
                    /*String nItem = elementoDet.getAttribute("nItem");
                    System.out.println("nItem : " + nItem);*/
                    
                    NodeList listaDeFilhosDet = elementoDet.getChildNodes(); //pega todos os Nós filhos do elemnto " DET "
                    int tamanhoListaFilho = listaDeFilhosDet.getLength();
                    for (int j = 0; j < tamanhoListaFilho; j++) {
                        Node noFilho = listaDeFilhosDet.item(j);
                        
                        if(noFilho.getNodeType() == Node.ELEMENT_NODE){
                            Element elementoFilho = (Element) noFilho;
                            
                            switch(elementoFilho.getTagName()){
                                case "cProd":
                                        System.out.println("CODIGO: " + elementoFilho.getTextContent());
                                        break;
                                case "xProd":
                                        System.out.println("DESCRIÇÃO: " + elementoFilho.getTextContent());
                                        break;
                                
                            }
                        }
                        
                    }
                    
                }
                
            }
            
            
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(LeituraXml.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(LeituraXml.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LeituraXml.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
