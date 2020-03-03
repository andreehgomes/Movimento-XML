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

public class ProjetoLeituraXml {

    public static void main(String[] args) {

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document doc = builder.parse("D:\\Aplicativos\\Arquivos\\Projetos\\LeitorXML\\41181001829764000157550010009320091001700760.xml");

            
            ////INICIO LISTAGEM TAG "INFNFE"
            
            NodeList listaDeInfNfe = doc.getElementsByTagName("infNFe"); //" infNfe " nome do nó com os produtos do xml 4.0

            int tamanhoLista = listaDeInfNfe.getLength();

            for (int i = 0; i < tamanhoLista; i++) {
                Node noInfNfe = listaDeInfNfe.item(i); //varrer XML

                if (noInfNfe.getNodeType() == Node.ELEMENT_NODE) {
                    Element elementoInfNfe = (Element) noInfNfe;
                    String versao = elementoInfNfe.getAttribute("versao");
                    String id = elementoInfNfe.getAttribute("Id");
                    System.out.println("Versão: " + versao);
                    System.out.println("ID: " + id);

                    NodeList listDeFilhosInfNfe = elementoInfNfe.getChildNodes(); //pega todos os Nós filhos do elemnto " InfNFe "
                    int tamanhoListaFilho = listDeFilhosInfNfe.getLength();
                    System.out.println("TAMANHO: " + tamanhoListaFilho);
                    for (int j = 0; j < tamanhoListaFilho; j++) {
                        Node noFilhoInfNfe = listDeFilhosInfNfe.item(j);

                        if (noFilhoInfNfe.getNodeType() == Node.ELEMENT_NODE) {
                            Element elementoFilho = (Element) noFilhoInfNfe;

                        }

                    }

                }

            }
            
            //TERMINO LISTAGEM TAG "INFNFE"

            //INICIO LISTAGEM TAG "IDE"
            
            NodeList listaDeIde = doc.getElementsByTagName("ide");
            int tamanhoListaIde = listaDeIde.getLength();
            for (int k = 0; k < tamanhoListaIde; k++) {
                Node noIde = listaDeIde.item(k); //varrer XML

                if (noIde.getNodeType() == Node.ELEMENT_NODE) {
                    Element elementoIde = (Element) noIde;

                    NodeList listaFilhosIde = elementoIde.getChildNodes();
                    int tamanhoListaFilhosIde = listaFilhosIde.getLength();

                    for (int l = 0; l < tamanhoListaFilhosIde; l++) {
                        Node noFilhoIde = listaFilhosIde.item(l);

                        if (noFilhoIde.getNodeType() == Node.ELEMENT_NODE) {
                            Element elementoFilhoIde = (Element) noFilhoIde;

                            switch (elementoFilhoIde.getTagName()) {
                                case "mod":
                                    System.out.println("MODELO NF: " + elementoFilhoIde.getTextContent());
                                    break;

                                case "nNF":
                                    System.out.println("NUM. NF: " + elementoFilhoIde.getTextContent());
                                    break;
                                 
                                case "natOp":
                                    System.out.println("NATUREZA DA OPERAÇÃO: " + elementoFilhoIde.getTextContent());
                                    break;
                            }
                        }

                    }

                }
            } //TERMINO LISTAGEM TAG "IDE"
            
            
            //INICIO LISTAGEM TAG "DEST"
            
            NodeList listaDeDest = doc.getElementsByTagName("dest");
            int tamanhoListaDest = listaDeDest.getLength();
            for (int m = 0; m < tamanhoListaDest; m++) {
                Node noDest = listaDeDest.item(m); //varrer XML

                if (noDest.getNodeType() == Node.ELEMENT_NODE) {
                    Element elementoDest = (Element) noDest;

                    NodeList listaFilhosDest = elementoDest.getChildNodes();
                    int tamanhoListaFilhosDest = listaFilhosDest.getLength();

                    for (int n = 0; n < tamanhoListaFilhosDest; n++) {
                        Node noFilhoDest = listaFilhosDest.item(n);

                        if (noFilhoDest.getNodeType() == Node.ELEMENT_NODE) {
                            Element elementoFilhoIde = (Element) noFilhoDest;

                            switch (elementoFilhoIde.getTagName()) {
                                case "CNPJ":
                                    System.out.println("CNPJ: " + elementoFilhoIde.getTextContent());
                                    break;

                                case "xNome":
                                    System.out.println("NOME DEST: " + elementoFilhoIde.getTextContent());
                                    break;                                
                                
                                    
                                                                
                            }
                        }

                    }

                }
            }
            
            //TERMINO LISTAGEM TAG "DEST"
            
            //INICIO LISTAGEM TAG "PROD"
            
            NodeList listaDeDet = doc.getElementsByTagName("prod"); //" det " nome do nó com os produtos do xml 4.0
            
            int tamanhoListaDet = listaDeDet.getLength();
            
            for (int i = 0; i < tamanhoListaDet; i++) {
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
                                 
                                case "CFOP":
                                        System.out.println("CFOP: " + elementoFilho.getTextContent());
                                        break;
                                        
                                case "uCom":
                                        System.out.println("UNIDADE DO PRODUTO: " + elementoFilho.getTextContent());
                                        break;
                                 
                                case "qCom":
                                        System.out.println("QUANTIDADE: " + elementoFilho.getTextContent());
                                        break;
                                
                            }
                        }
                        
                    }
                    
                }
                
            }
            
            
            
            

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(ProjetoLeituraXml.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(ProjetoLeituraXml.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProjetoLeituraXml.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
