/*
 Put the utility of the class here
 */
package map;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import graphics.GraphicEngine;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.jsfml.system.Vector2i;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Loader {

    public Loader(String map_source, GraphicEngine drawTarget) {
        mMap = new Map(drawTarget);
        //Debut tests XML

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document dom = db.parse("assets/Maps/" + map_source);
            parseDocument(dom);

        } catch (ParserConfigurationException | SAXException | IOException ex) {
            System.err.println("Exception lors de l'ouverture de la carte : " + ex);
        }

        //Fin tests XML
        mSource = map_source;
    }

    public Map readMap() {

        return null;
    }

    private void parseDocument(Document dom) {
        // System.out.println(dom.getLocalName());
        Element docEle = dom.getDocumentElement();

        NodeList nl = docEle.getElementsByTagName("objectgroup");
        if (nl != null && nl.getLength() > 0) {

                //get the employee element
                Element el = (Element) nl.item(1);

                System.out.println(el.getAttribute("name"));
                readObjects(el);
        }
    }

    private List<MapObject> readObjects(Element objgroup) {
        ArrayList<MapObject> theObjects = new ArrayList<>();
        NodeList mListe = objgroup.getElementsByTagName("object");
        for(int i = 0; i < mListe.getLength(); i++ ){
           if(mListe.item(i).getChildNodes() == null){
               
           }else{
               theObjects.add(readWithProprieties(mListe.item(i)));
           }
        }
        
        System.out.println(objgroup.getElementsByTagName("object").getLength());

        return null;
    }

    private Layer readLayer(NodeList layer) {

        return null;
    }
    
    private MapObject readWithProprieties(Node objecWithProp){
        
        
        return null;
    }

    public List<Layer> getLayers() {
        try {
            ArrayList<Layer> l_Layer = new ArrayList<>(Map.NB_FILTERS);
            Scanner scan = new Scanner(new File(mSource));
            /*
             Scan the header
             */
            Vector2i layers_size = readHeader(scan);

            /*
             Scan all the layers
             */
            Layer newLayer = null;
            do {
                while (scan.hasNextLine() && !scan.nextLine().contains("layer")) {
                }

                if (scan.hasNext()) {
                    newLayer = readLayer(scan, layers_size);
                    /*
                     Add new Layers to the array
                     */
                    if (newLayer != null) {
                        l_Layer.add(newLayer);
                    }
                }

            } while (newLayer != null && scan.hasNextLine());
            return l_Layer;
        } catch (Exception ex) {
            System.err.println("Exception lors de l'ouverture du fichier : " + mSource + "Exception : " + ex);
        }

        return null;
    }

    private Vector2i readHeader(Scanner fileScan) {
        /*
         Read the 'header' tag
         */
        fileScan.nextLine();

        /*
         Read the informations we want : width and height
         First we've got the width, and then the height
         */
        fileScan.findInLine("width=");
        int width = fileScan.nextInt();
        fileScan.nextLine();
        fileScan.findInLine("height=");
        int height = fileScan.nextInt();

        Vector2i dimensions = new Vector2i(width, height);

        return dimensions;
    }

    private Layer readLayer(Scanner scan, Vector2i l_size) {

        //scan.nextLine();
        scan.findInLine("type=");
        Map.Filter filtre = Map.getFilter(scan.nextLine());
        if (filtre != null) {
            Layer newLayer = new Layer(l_size.x, l_size.y, filtre);
            scan.nextLine();
            newLayer.readArray(scan);
            return newLayer;
        } else {
            return null;
        }
    }

    private final String mSource;
    private Map mMap;
}
