package model.Tools;

/**
 * Created by jelle on 23-6-2017.
 */
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

public class ReadXMLStream implements XMLReadI {
    private Document doc;
    public ReadXMLStream()
    {
        File file = new File("C:\\Users\\jelle\\Documents\\IntelliJ\\VanillaWoWFishingBot\\src\\resources.xml");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        try {
            Document document = db.parse(file);
            doc = document;
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Document getDocument()
    {
        return doc;
    }
}