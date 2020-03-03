/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leituraxml;

import MODEL.ModelClientes;
import MODEL.ModelMovimento;
import MODEL.ModelProdutos;
import MODEL.ModelXml;
import OperacaoBanco.OperacaoBancoCfop;
import OperacaoBanco.OperacaoBancoClientes;
import OperacaoBanco.OperacaoBancoMovimento;
import OperacaoBanco.OperacaoBancoProdutos;
import OperacaoBanco.OperacaoBancoXml;
import VIEW.TelaCarregando;
import VIEW.TelaLeituraXml;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import jxl.read.biff.BiffException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class LerXml {

    public void lerArquivo(String caminho) throws IOException, SQLException, BiffException, ParseException {

        ModelProdutos produtos = new ModelProdutos();
        ModelClientes clientes = new ModelClientes();
        MODEL.ModelMovimento movimento = new ModelMovimento();
        MODEL.ModelXml xml = new ModelXml();
        OperacaoBanco.OperacaoBancoXml opx = new OperacaoBancoXml();
        OperacaoBanco.OperacaoBancoMovimento opm = new OperacaoBancoMovimento();

        FileWriter log = new FileWriter(new File("c:\\Anve\\log.txt"), true);
        PrintWriter gravarLog = new PrintWriter(log); // criação do        
        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document doc = builder.parse(caminho);

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
                    gravarLog.printf("=========================================================%n");
                    gravarLog.printf("Versão %s\n%n", versao);
                    gravarLog.printf("ID: %s\n%n", id);

                    NodeList listDeFilhosInfNfe = elementoInfNfe.getChildNodes(); //pega todos os Nós filhos do elemnto " InfNFe "
                    int tamanhoListaFilho = listDeFilhosInfNfe.getLength();
                    //System.out.println("TAMANHO: " + tamanhoListaFilho);
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
                                    String mod = elementoFilhoIde.getTextContent();
                                    gravarLog.printf("MODELO NF: %s%n", mod);
                                    xml.setModelo(Float.parseFloat(elementoFilhoIde.getTextContent()));
                                    break;

                                case "nNF":
                                    System.out.println("NUM. NF: " + elementoFilhoIde.getTextContent());
                                    String numnf = elementoFilhoIde.getTextContent();
                                    gravarLog.printf("NUM NF: %s%n", numnf);
                                    xml.setNumeronfxml(Integer.parseInt(elementoFilhoIde.getTextContent()));
                                    movimento.setModelxml(xml);
                                    break;

                                case "dhEmi":
                                    Date dtmovimento = null;
                                    System.out.println("DATA DE EMISSÃO");
                                    String dtemissao = elementoFilhoIde.getTextContent().toString().substring(0, 10);
                                    dtmovimento = new SimpleDateFormat("yyyy-MM-dd").parse(dtemissao);

                                    movimento.setDtemissao(new java.sql.Date(dtmovimento.getTime()));

                                    xml.setDtemissao(dtemissao);
                                    break;

                                case "natOp":
                                    System.out.println("NATUREZA DA OPERAÇÃO: " + elementoFilhoIde.getTextContent());
                                    System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");
                                    String ndo = elementoFilhoIde.getTextContent();
                                    gravarLog.printf("NATUREZA DA OPERAÇÃO: %s%n", ndo);
                                    gravarLog.printf("++++++++++++++++++++++++++++++++++++++++++++++++");
                                    xml.setNaturezaop(ndo);
                                    break;

                            }
                        }

                    }

                }
            } //TERMINO LISTAGEM TAG "IDE"

            //INICIO LISTAGEM TAG "DEST"
            NodeList listaDeDest = doc.getElementsByTagName("dest");
            int tamanhoListaDest = listaDeDest.getLength();
            if (tamanhoListaDest < 1) {
                clientes.setCpcodcli(10011);
                movimento.setModelclientes(clientes);
            }

            for (int m = 0; m < tamanhoListaDest; m++) {
                Node noDest = listaDeDest.item(m); //varrer XML

                if (noDest.getNodeType() == Node.ELEMENT_NODE) {
                    Element elementoDest = (Element) noDest;

                    NodeList listaFilhosDest = elementoDest.getChildNodes();
                    int tamanhoListaFilhosDest = listaFilhosDest.getLength();

                    for (int n = 0; n < tamanhoListaFilhosDest; n++) {
                        Node noFilhoDest = listaFilhosDest.item(n);

                        if (noFilhoDest.getNodeType() == Node.ELEMENT_NODE) {
                            Element elementoFilhoDest = (Element) noFilhoDest;

                            switch (elementoFilhoDest.getTagName()) {

                                case "CNPJ":
                                    System.out.println("CNPJ: " + elementoFilhoDest.getTextContent());
                                    String cnpjcpf = elementoFilhoDest.getTextContent();
                                    gravarLog.printf("CNPJ: %s%n", cnpjcpf);

                                    OperacaoBanco.OperacaoBancoClientes opc = new OperacaoBancoClientes();

                                    opc.obterConexao();
                                    opc.executaSql("SELECT * FROM clientes where cpfcnpj like '%" + cnpjcpf + "%'");
                                    try {
                                        opc.rs.first();
                                        clientes.setCpcodcli(Integer.parseInt(opc.rs.getString("cpcodcli")));
                                        movimento.setModelclientes(clientes);
                                    } catch (SQLException e) {
                                        clientes.setCpcodcli(1);
                                        movimento.setModelclientes(clientes);
                                        System.out.println("ERRO NA CONSULTA DO CLIENTE CNPJ: " + clientes.getRazaosocial() + " " + e.getMessage());
                                        //JOptionPane.showMessageDialog(null, "ERRO NA CONSULTA DO CLIENTE CNPJ: " + clientes.getRazaosocial() + " " + e.getMessage());

                                    } catch (Exception f) {
                                        JOptionPane.showMessageDialog(null, "ERRO NA CONSULTA DO CLIENTE CNPJ: " + clientes.getRazaosocial() + " " + f.getMessage());
                                    }

                                    opc.fechaConexao();

                                    break;

                                case "CPF":
                                    System.out.println("CPF: " + elementoFilhoDest.getTextContent());
                                    String cpf = elementoFilhoDest.getTextContent();
                                    gravarLog.printf("CPF: %s%n", cpf);

                                    OperacaoBanco.OperacaoBancoClientes opbc = new OperacaoBancoClientes();

                                    opbc.obterConexao();
                                    opbc.executaSql("SELECT * FROM clientes where cpfcnpj like '%" + cpf + "%'");
                                    try {
                                        opbc.rs.first();
                                        clientes.setCpcodcli(Integer.parseInt(opbc.rs.getString("cpcodcli")));
                                        movimento.setModelclientes(clientes);
                                    } catch (SQLException e) {
                                        clientes.setCpcodcli(1);
                                        movimento.setModelclientes(clientes);
                                        System.out.println("ERRO NA CONSULTA DO CLIENTE CPF: " + clientes.getRazaosocial() + " " + e.getMessage());
                                    }

                                    opbc.fechaConexao();

                                    break;

                                case "xNome":
                                    System.out.println("NOME DEST: " + elementoFilhoDest.getTextContent());
                                    String nomedest = elementoFilhoDest.getTextContent();
                                    gravarLog.printf("NOME DEST: %s%n", nomedest);
                                    System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");
                                    gravarLog.printf("------------------------------------------------------%n");
                                    clientes.setRazaosocial(nomedest);
                                    break;

                            }
                        }

                    }

                }
            }

            //TERMINO LISTAGEM TAG "DEST"
            //INICIO LISTAGEM TAG "DEST"
            NodeList listaDeInfProt = doc.getElementsByTagName("infProt");
            int tamanhoListaInfProt = listaDeInfProt.getLength();
            for (int m = 0; m < tamanhoListaInfProt; m++) {
                Node noProt = listaDeInfProt.item(m); //varrer XML

                if (noProt.getNodeType() == Node.ELEMENT_NODE) {
                    Element elementoProt = (Element) noProt;

                    NodeList listaFilhosProt = elementoProt.getChildNodes();
                    int tamanhoListaFilhosDest = listaFilhosProt.getLength();

                    for (int n = 0; n < tamanhoListaFilhosDest; n++) {
                        Node noFilhoProt = listaFilhosProt.item(n);

                        if (noFilhoProt.getNodeType() == Node.ELEMENT_NODE) {
                            Element elementoFilhoProt = (Element) noFilhoProt;

                            switch (elementoFilhoProt.getTagName()) {
                                case "chNFe":
                                    System.out.println("CHAVE DA NFE: " + elementoFilhoProt.getTextContent());
                                    String chnfe = elementoFilhoProt.getTextContent();
                                    gravarLog.printf("CHAVE DA NFE: %s%n", chnfe);
                                    xml.setChavenfxml(chnfe);
                                    break;

                                case "xMotivo":
                                    System.out.println("MOTIVO: " + elementoFilhoProt.getTextContent());
                                    String motivo = elementoFilhoProt.getTextContent();
                                    gravarLog.printf("MOTIVO: %s%n", motivo);
                                    xml.setMotivo(motivo);
                                    break;

                                case "dhRecbto":
                                    System.out.println("DATA E HORA DA AUTORIZAÇÃO: " + elementoFilhoProt.getTextContent());
                                    String dtauto = elementoFilhoProt.getTextContent();
                                    gravarLog.printf("DATA E HORA DA AUTORIZAÇÃO: %s%n", dtauto);
                                    xml.setDtrecebimento(dtauto);
                                    System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");
                                    gravarLog.printf("------------------------------------------------------%n");
                                    break;

                            }
                        }

                    }

                }
            }

            NodeList listaDeDetEvento = doc.getElementsByTagName("detEvento");
            int tamanhoListaDetEvento = listaDeDetEvento.getLength();
            for (int m = 0; m < tamanhoListaDetEvento; m++) {
                Node noDetEvento = listaDeDetEvento.item(m); //varrer XML

                if (noDetEvento.getNodeType() == Node.ELEMENT_NODE) {
                    Element elementoDetEvento = (Element) noDetEvento;

                    NodeList listaFilhosDetEvento = elementoDetEvento.getChildNodes();
                    int tamanhoListaFilhosDest = listaFilhosDetEvento.getLength();

                    for (int n = 0; n < tamanhoListaFilhosDest; n++) {
                        Node noFilhoDetEvento = listaFilhosDetEvento.item(n);

                        if (noFilhoDetEvento.getNodeType() == Node.ELEMENT_NODE) {
                            Element elementoFilhoDetEvento = (Element) noFilhoDetEvento;

                            switch (elementoFilhoDetEvento.getTagName()) {
                                case "descEvento":
                                    System.out.println("NATUREZA DA OP: " + elementoFilhoDetEvento.getTextContent());
                                    String naturezaop = elementoFilhoDetEvento.getTextContent();
                                    gravarLog.printf("NATEREZA DA OP: %s%n", naturezaop);
                                    xml.setNaturezaop(naturezaop);
                                    xml.setMotivo(naturezaop);
                                    break;

                            }
                        }

                    }

                }
            }

            NodeList listaDeinfEvento = doc.getElementsByTagName("infEvento");
            int tamanhoListainfEvento = listaDeinfEvento.getLength();
            for (int m = 0; m < tamanhoListainfEvento; m++) {
                Node noinfEvento = listaDeinfEvento.item(m); //varrer XML

                if (noinfEvento.getNodeType() == Node.ELEMENT_NODE) {
                    Element elementoinfEvento = (Element) noinfEvento;

                    NodeList listaFilhosinfEvento = elementoinfEvento.getChildNodes();
                    int tamanhoListaFilhosDest = listaFilhosinfEvento.getLength();

                    for (int n = 0; n < tamanhoListaFilhosDest; n++) {
                        Node noFilhoinfEvento = listaFilhosinfEvento.item(n);

                        if (noFilhoinfEvento.getNodeType() == Node.ELEMENT_NODE) {
                            Element elementoFilhoinfEvento = (Element) noFilhoinfEvento;

                            switch (elementoFilhoinfEvento.getTagName()) {
                                case "chNFe":
                                    System.out.println("CHAVE DA NOTA FISCAL: " + elementoFilhoinfEvento.getTextContent());
                                    String chavenf = elementoFilhoinfEvento.getTextContent();
                                    gravarLog.printf("CHAVE DA NOTA FISCAL: %s%n", chavenf);
                                    xml.setChavenfxml(chavenf);

                                    System.out.println("NOTA FISCAL: " + chavenf.substring(25, 34));
                                    gravarLog.printf("NÚMERO DA NOTA: %s%n", chavenf.substring(25, 34));
                                    xml.setNumeronfxml(Integer.parseInt(chavenf.substring(25, 34)));

                                    System.out.println("MODELO NF: " + chavenf.substring(20, 22));
                                    gravarLog.printf("MODELO: %s%n", chavenf.substring(20, 22));
                                    xml.setModelo(Integer.parseInt(chavenf.substring(20, 22)));
                                    break;

                                case "dhEvento":
                                    System.out.println("DATA DE EMISSÃO: " + elementoFilhoinfEvento.getTextContent());
                                    String dtemissao = elementoFilhoinfEvento.getTextContent();
                                    gravarLog.printf("DTA DE EMISSÃO: %s%n", dtemissao);
                                    xml.setDtemissao(dtemissao);
                                    break;

                            }
                        }

                    }

                }
            }

            opx.incluirXml(xml);

            //INICIO LISTAGEM TAG "PROD"
            NodeList listaDeDet = doc.getElementsByTagName("prod"); //" det " nome do nó com os produtos do xml 4.0

            int tamanhoListaDet = listaDeDet.getLength();

            for (int i = 0; i < tamanhoListaDet; i++) {
                Node noDet = listaDeDet.item(i); //varrer XML

                if (noDet.getNodeType() == Node.ELEMENT_NODE) {
                    Element elementoDet = (Element) noDet;
                    /*String nItem = elementoDet.getAttribute("nItem");
                    System.out.println("nItem : " + nItem);*/

                    NodeList listaDeFilhosDet = elementoDet.getChildNodes(); //pega todos os Nós filhos do elemnto " DET "
                    int tamanhoListaFilho = listaDeFilhosDet.getLength();
                    for (int j = 0; j < tamanhoListaFilho; j++) {

                        Node noFilho = listaDeFilhosDet.item(j);

                        if (noFilho.getNodeType() == Node.ELEMENT_NODE) {
                            Element elementoFilho = (Element) noFilho;

                            switch (elementoFilho.getTagName()) {
                                case "cProd":

                                    System.out.println("CODIGO: " + elementoFilho.getTextContent());
                                    String cod = elementoFilho.getTextContent();

                                    gravarLog.printf("CODIGO: %s%n", testeNumero(cod.replaceAll("\\D", "")));
                                    produtos.setCodigofabrica(Integer.parseInt(cod.replaceAll("\\D", "")));
                                    produtos.setCodigofabrica2(Integer.parseInt(cod.replaceAll("\\D", "")));
                                    produtos.setCodigofabrica3(Integer.parseInt(cod.replaceAll("\\D", "")));
                                    produtos.setCodigofabrica4(Integer.parseInt(cod.replaceAll("\\D", "")));
                                    produtos.setCodigofabrica5(Integer.parseInt(cod.replaceAll("\\D", "")));

                                    movimento.setModelprodutos(produtos);

                                    break;

                                case "xProd":

                                    System.out.println("DESCRIÇÃO: " + elementoFilho.getTextContent());
                                    String desc = elementoFilho.getTextContent();
                                    gravarLog.printf("DESCRIÇÃO: %s%n", desc);
                                    //Long codigo = Long.parseLong(elementoFilho.getTextContent().substring(0, 10));
                                    //gravarLog.printf("CODIGO PRODUTO: %s%n", codigo);
                                    //produtos.setCpcodpro(codigo);

                                    break;

                                case "CFOP":
                                    System.out.println("CFOP: " + elementoFilho.getTextContent());
                                    String cfop = elementoFilho.getTextContent();
                                    gravarLog.printf("CFOP: %s%n", cfop);
                                    movimento.setCfop(Integer.parseInt(cfop));

                                    OperacaoBancoCfop opcfop = new OperacaoBancoCfop();
                                    opcfop.obterConexao();
                                    opcfop.executaSql("SELECT * FROM cfop where cpcfop = " + Integer.parseInt(cfop));

                                    try {
                                        opcfop.rs.first();
                                        movimento.setTipomovimento(opcfop.rs.getString("tipomovimento"));
                                    } catch (SQLException e) {
                                        movimento.setCfop(1);
                                        movimento.setTipomovimento("NÃO MOVIMENTA");
                                        System.out.println("ERRO CONSULTA CFOP: " + e.getMessage());
                                    }

                                    opcfop.fechaConexao();

                                    break;

                                case "uCom":
                                    System.out.println("UNIDADE DO PRODUTO: " + elementoFilho.getTextContent());
                                    String udp = elementoFilho.getTextContent();
                                    gravarLog.printf("UNIDADE DO PRODUTO: %s%n", udp);
                                    movimento.setUnproduto(udp);
                                    break;

                                case "qCom":
                                    System.out.println("QUANTIDADE: " + elementoFilho.getTextContent());
                                    System.out.println("------------------------------------------------------");
                                    String qtd = elementoFilho.getTextContent();
                                    gravarLog.printf("QUANTIDADE: %s%n", qtd);
                                    gravarLog.printf("------------------------------------------------------%n");
                                    movimento.setQtduntotal((int) Float.parseFloat(qtd));
                                    break;

                                case "vUnCom":
                                    System.out.println("VALOR UNITÁRIO: " + elementoFilho.getTextContent());
                                    System.out.println("------------------------------------------------------");
                                    String vlrunitario = elementoFilho.getTextContent();
                                    gravarLog.printf("VALOR UNITÁRIO: %s%n", vlrunitario);
                                    gravarLog.printf("------------------------------------------------------%n");
                                    movimento.setValorun(Float.parseFloat(vlrunitario));
                                    break;

                                case "vProd":
                                    System.out.println("VALOR TOTAL: " + elementoFilho.getTextContent());
                                    System.out.println("------------------------------------------------------");
                                    String vlrtotal = elementoFilho.getTextContent();
                                    gravarLog.printf("VALOR TOTAL: %s%n", vlrtotal);
                                    gravarLog.printf("------------------------------------------------------%n");
                                    movimento.setValortotal(Float.parseFloat(vlrtotal));
                                    break;
                            }
                        }

                    }

                }

                //VALIDA SE O XML ESTÁ VALIDO OU NÃO
                if (xml.getMotivo().equals("Autorizado o uso da NF-e")) {

                    int estoqueregistro = gravarEstoque(movimento.getModelprodutos().getCodigofabrica(),
                            movimento.getTipomovimento(),
                            movimento.getUnproduto(),
                            movimento.getQtduntotal(),
                            xml.getMotivo());

                    movimento.setEstoquenoregistro(estoqueregistro);
                    opm.incluirMovimento(movimento);
                } else {
                    movimento.setTipomovimento("NÃO MOVIMENTA");
                    opm.incluirMovimento(movimento);
                }

            }

            log.close();

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(ProjetoLeituraXml.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(ProjetoLeituraXml.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProjetoLeituraXml.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static boolean testeNumero(String elemento) {
        return elemento.matches("[0-9]+");
    }

    public int gravarEstoque(int codigoproduto, String tipomovimento, String unprodvenda, int qtdtotal, String motivo) {
        int estoquecad = 0;

        try {
            ModelProdutos produto = new ModelProdutos();
            OperacaoBancoProdutos obp = new OperacaoBancoProdutos();
            OperacaoBancoXml obx = new OperacaoBancoXml();
            int codprod = codigoproduto;
            produto.setCodigofabrica(codprod);
            obp.obterConexao();
            obp.executaSql("SELECT * FROM produtos where (codigofabrica = " + codprod
                    + " or codigofabrica2 = " + codprod
                    + " or codigofabrica3 = " + codprod
                    + " or codigofabrica4 = " + codprod
                    + " or codigofabrica5 = " + codprod + ")");

            obp.rs.first();

            String produncad = obp.rs.getString("unidade");
            String produnmov = unprodvenda;
            int unprodcad = obp.rs.getInt("qtdun");
            int totalunvenda = qtdtotal;
            estoquecad = obp.rs.getInt("estoque");
            int totalentradas = 0;
            int totalsaidas = 0;

            /* if (produncad.startsWith(produnmov)) {

                if (tipomovimento.equals("ENTRADA")) {
                    totalentradas = totalunvenda * unprodcad;
                    System.out.println("TOTAL ENTRADAS: " + totalentradas);
                } else if (tipomovimento.equals("SAÍDA")) {
                    totalsaidas = totalunvenda * unprodcad;
                    System.out.println("TOTAL SAÍDAS: " + totalsaidas);
                }
                estoquecad = estoquecad + totalentradas - totalsaidas;
                produto.setEstoque(estoquecad);
                System.out.println("ESTOQUE: " + estoquecad);

            } else {
                if (tipomovimento.equals("ENTRADA")) {
                    totalentradas = totalunvenda;
                    System.out.println("TOTAL ENTRADAS: " + totalentradas);
                } else if (tipomovimento.equals("SAÍDA")) {
                    totalsaidas = totalunvenda;
                    System.out.println("TOTAL SAÍDAS: " + totalsaidas);
                }
                estoquecad = estoquecad + totalentradas - totalsaidas;
                produto.setEstoque(estoquecad);
                System.out.println("ESTOQUE: " + estoquecad);
            }*/
            
            
            if (produnmov.startsWith("UN")) {

                if (tipomovimento.equals("ENTRADA")) {
                    totalentradas = totalunvenda;
                    System.out.println("TOTAL ENTRADAS: " + totalentradas);
                } else if (tipomovimento.equals("SAÍDA")) {
                    totalsaidas = totalunvenda;
                    System.out.println("TOTAL SAÍDAS: " + totalsaidas);
                }
                estoquecad = estoquecad + totalentradas - totalsaidas;
                produto.setEstoque(estoquecad);
                System.out.println("ESTOQUE: " + estoquecad); 

            } else { //QUALQUER OUTRO RESULTADO QUE OBTIVER IRÁ MULTIPLICAR A QUANTIDADE DE ITENS DO XML PELA QUANTIDADE DE ITENS P/ CX DO CADASTRO DOS PRODUTOS.
                if (tipomovimento.equals("ENTRADA")) {
                    totalentradas = totalunvenda * unprodcad;
                    System.out.println("TOTAL ENTRADAS: " + totalentradas);
                } else if (tipomovimento.equals("SAÍDA")) {
                    totalsaidas = totalunvenda * unprodcad;
                    System.out.println("TOTAL SAÍDAS: " + totalsaidas);
                }
                estoquecad = estoquecad + totalentradas - totalsaidas;
                produto.setEstoque(estoquecad);
                System.out.println("ESTOQUE: " + estoquecad);
            }

            obp.alterarEstoqueProdutos(produto);
            obp.fechaConexao();

        } catch (IOException ex) {
            Logger.getLogger(TelaLeituraXml.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (BiffException ex) {
            Logger.getLogger(TelaLeituraXml.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (SQLException ex) {
            Logger.getLogger(TelaLeituraXml.class
                    .getName()).log(Level.SEVERE, null, ex);

        }
        return estoquecad;
    }

}
