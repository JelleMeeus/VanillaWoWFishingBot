package model;

import java.util.ArrayList;

import model.Tools.ReadXMLFile;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

/**
 * Created by jelle on 23-6-2017.
 */
public class LureFactory {
    private LureFactory(){};
    private static LureFactory uniqueInstance;
    private static ArrayList<Lure> lures;
    public static LureFactory getInstance(){
        if (uniqueInstance == null){
            uniqueInstance = new LureFactory();
        }
        return uniqueInstance;
    }

    public static Lure createLure(String name, Long duration)
    {
        return new Lure(name, duration);
    }

    public static ArrayList<Lure> createLureArrayList()
    {
        lures = new ArrayList<Lure>();

        String name = null;
        Long duration = null;

        //ReadFile
        ReadXMLFile reader = new ReadXMLFile();
        Document doc = reader.getDocument();
        doc.getDocumentElement().normalize();
        //navigate
        NodeList nList = doc.getElementsByTagName("lure");
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                name = eElement.getElementsByTagName("itemname")
                        .item(0).getTextContent().trim();
                duration = Long.parseLong(eElement.getElementsByTagName("duration")
                        .item(0).getTextContent().trim());

            }
            lures.add( createLure(name,duration));
        }
        return lures;
    }

}
