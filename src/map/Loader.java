/*
 Put the utility of the class here
 */
package map;

import graphics.GraphicEngine;
import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.Inflater;
import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.jsfml.system.Vector2f;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Loader {

    public Loader(String map_source, GraphicEngine drawTarget) {
        mMap = new Map(drawTarget);
        mSource = map_source;
        readTMX();
    }

    public Map getMap() {
        return mMap;
    }

    private void parseDocument(Document dom) {
        // System.out.println(dom.getLocalName());
        Element docEle = dom.getDocumentElement();

        NodeList nl = docEle.getElementsByTagName("objectgroup");
        if (nl != null && nl.getLength() > 0) {

            Element el = (Element) nl.item(1);
            List<MapObject> mapObjts =  readObjects(el);
            mMap.setObjects(mapObjts);
        }

        NodeList nlLayer = docEle.getElementsByTagName("layer");
        if (nlLayer != null && nlLayer.getLength() > 0) {
            ArrayList<Layer> mapLayers = new ArrayList<>();
            for (int i = 0; i < nlLayer.getLength(); i++) {
                mapLayers.add(readLayer(nlLayer.item(i)));

            }
            mMap.setLayers(mapLayers);
        }
    }

    private List<MapObject> readObjects(Element objgroup) {
        ArrayList<MapObject> theObjects = new ArrayList<>();
        NodeList mListe = objgroup.getElementsByTagName("object");

        for (int i = 0; i < mListe.getLength(); i++) {
            if (mListe.item(i).getChildNodes().getLength() <= 1) {
                theObjects.add(readWithoutProprieties(mListe.item(i)));
            } else {
                theObjects.add(readWithProprieties(mListe.item(i)));
            }
        }

        return new ArrayList<>();
    }

    private Layer readLayer(Node layers) {

        NamedNodeMap lAttributes = layers.getAttributes();

        /*
         Get the layer's name
         */
        String name = lAttributes.getNamedItem("name").getTextContent().toUpperCase();
        Map.LayerType lType = Map.LayerType.valueOf(name);

        /*
         Gather some informations about the layer
         */
        int width = Integer.parseInt(lAttributes.getNamedItem("width").getTextContent());
        int height = Integer.parseInt(lAttributes.getNamedItem("height").getTextContent());

        Element ts = (Element) layers;
        String toDecompress = ts.getElementsByTagName("data").item(0).getTextContent().trim();

        try {
            //Decode the base64 String
            byte[] decode = DatatypeConverter.parseBase64Binary(toDecompress);

            // Decompress the bytes
            Inflater decompresser = new Inflater();
            decompresser.setInput(decode, 0, decode.length);
            byte[] result = new byte[width*height*4];
            int resultLength = decompresser.inflate(result);
            decompresser.end();

            // Little endian ordering
            ByteBuffer bb = ByteBuffer.wrap(result);
            bb.order(ByteOrder.BIG_ENDIAN);
            
            //Turn into int buffer
            IntBuffer res = bb.asIntBuffer();
            
            // Annnnd finally, we have our array
            return new Layer(IntbufferToInarray(width, height, res), lType);

        } catch (java.util.zip.DataFormatException ex) {
            System.err.println("Erreur lors de la décompression : " + ex);
        }

        return null;
    }

    private MapObject readWithProprieties(Node objecWithProp) {
        MapObject obj = readWithoutProprieties(objecWithProp);
        obj.setProprieties(getProprieties((Element) objecWithProp));

        return obj;
    }

    private java.util.Map<String, Object> getProprieties(Element properNode) {
        java.util.Map<String, Object> myPropreties = new HashMap<>();
        NodeList nodes = properNode.getElementsByTagName("property");
        for (int i = 0; i < nodes.getLength(); i++) {
            NamedNodeMap attri = nodes.item(i).getAttributes();
            myPropreties.put(attri.getNamedItem("name").getTextContent(), nodes.item(i).getAttributes().getNamedItem("value"));
        }

        return myPropreties;
    }

    private int[][] IntbufferToInarray(int width, int height, IntBuffer toTransform) {
        int[][] toR = new int[height][width];
        try {
            for (int i = 0; i <= height; i++) {
                for (int j = 0; j < width && toTransform.capacity() > (i * width + j); j++) {
                    toR[i][j] = Integer.reverseBytes(toTransform.get(i*width + j));
                }
            }
        } catch (BufferOverflowException ex) {
            System.out.println(" Erreur lors de la transformation de la map : " + ex);
        }

        return toR;
    }

    private MapObject readWithoutProprieties(Node objcLess) {
        MapObject obj;
        // Basic attributes
        NamedNodeMap attr = objcLess.getAttributes();
        int id = Integer.parseInt(attr.getNamedItem("id").getTextContent());
        int width = Integer.parseInt(attr.getNamedItem("width").getTextContent());
        int height = Integer.parseInt(attr.getNamedItem("height").getTextContent());

        float x = Float.parseFloat(attr.getNamedItem("x").getTextContent());
        float y = Float.parseFloat(attr.getNamedItem("y").getTextContent());

        if (objcLess.getAttributes().getLength() == 5) {
            //Only the 5 necessary attributes

            obj = new MapObject(id, width, height, new Vector2f(x, y));
        } else {
            //More than 5 attributes ...
            int rotation = 0;
            String name = "";
            String type = "";
            if (attr.getNamedItem("rotation") != null) {
                rotation = Integer.parseInt(attr.getNamedItem("rotation").getTextContent());
            }
            if (attr.getNamedItem("name") != null) {
                name = attr.getNamedItem("name").getTextContent();
            }
            if (attr.getNamedItem("type") != null) {
                type = attr.getNamedItem("type").getTextContent();
            }
            obj = new MapObject(name, type, id, width, height, new Vector2f(x, y), null);

        }
        return obj;
    }

    private void readTMX() {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            try {
                DocumentBuilder db = dbf.newDocumentBuilder();

                Document dom = db.parse("assets/Maps/" + mSource);
                parseDocument(dom);

            } catch (ParserConfigurationException | SAXException | IOException ex) {
                System.err.println("Exception lors de l'ouverture de la carte : " + ex);
            }

        } catch (Exception ex) {
            System.err.println("Exception lors de l'ouverture du fichier : " + mSource + "Exception : " + ex);
        }

    }

    private final String mSource;
    private Map mMap;
}
