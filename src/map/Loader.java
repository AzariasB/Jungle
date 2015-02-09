/*
 Put the utility of the class here
 */
package map;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.jsfml.system.Vector2i;

public class Loader {

    public Loader(String map_source) {
        mSource = map_source;
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
                while (scan.hasNextLine() && !scan.nextLine().contains("layer")){}

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
            Layer newLayer = new Layer(l_size.x, l_size.y,filtre);
            scan.nextLine();
            newLayer.readArray(scan);
            return newLayer;
        } else {
            return null;
        }
    }

    private final String mSource;
}
