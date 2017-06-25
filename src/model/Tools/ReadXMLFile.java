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
import java.io.InputStream;
import java.net.URL;

public class ReadXMLFile implements XMLReadI {
    private Document doc;
    public ReadXMLFile()
    {
        InputStream in = getClass().getResourceAsStream("/resources/resources.xml");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        try {
            Document document = db.parse(in);
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