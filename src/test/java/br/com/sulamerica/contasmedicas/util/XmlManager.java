package br.com.sulamerica.contasmedicas.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.com.sulamerica.contasmedicas.constants.PathConstants;

public class XmlManager {
    
    /*
     * Exemplo de update
     * 
     * Map<String, String> mod = new HashMap<String, String>();
     *   mod.put("indRetificacao", "certo");
     *  mod.put("tpAmb", "certo");
     *   mod.put("aplicEmi", "certo");
     *   updateXml(mod, "cadastro_intermediario.xml");
     * 
     * 
     */
      public static void updateXml(Map<String, String> valoresParaModificarXml, String nomeXml) throws Exception {
        InputStream inputStream = new FileInputStream(FileManager.getRecursiveFiles(PathConstants.FIXTURES_PATH + File.separator + "xml", nomeXml));
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder domBuilder = domFactory.newDocumentBuilder();
        Document document = domBuilder.parse(inputStream);

        for (Map.Entry<String, String> entry : valoresParaModificarXml.entrySet()) {
           NodeList rowNodes = document.getElementsByTagName(entry.getKey());
            for (int i = 0; i < rowNodes.getLength(); i++) {
                Node rowNode = rowNodes.item(i);

                // Remove existing content (if any)
                while (rowNode.getFirstChild() != null)
                    rowNode.removeChild(rowNode.getFirstChild());

                // Add text content
                rowNode.appendChild(document.createTextNode(entry.getValue()));
            }
        }
            
        // Save XML to file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.transform(new DOMSource(document),
                new StreamResult(FileManager.getRecursiveFiles(PathConstants.FIXTURES_PATH + File.separator + "xml", nomeXml)));

    }

}
