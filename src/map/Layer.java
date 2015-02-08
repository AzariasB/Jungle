/*
 Put the utility of the class here
 */
package map;

import graphics.GraphicEngine;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import org.jsfml.graphics.PrimitiveType;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.Vertex;

public class Layer {

    public Layer(int l_width, int l_height,Map.Filter myFilter) {
        mLayer = new int[l_height][l_width];
        mFilter = myFilter;
        mVerticies = new ArrayList<>();
    }

    public void readArray(Scanner fToRead) {
        fToRead.useDelimiter(Pattern.compile(",|\\s"));
        for (int i = 0; i < mLayer.length; i++) {
            for (int j = 0; j < mLayer[i].length; j++) {
                //System.out.println("i = " + i + " j = " + j);
                mLayer[i][j] = fToRead.nextInt();
            }
            if (i < mLayer.length - 1 ) {
                //System.out.println("Avant");
                fToRead.nextLine();
                //System.out.println("Apres");
            }
            //System.out.println(i);
        }
        fToRead.useDelimiter(" ");
    }

    public int[][] getArray() {
        return mLayer;
    }
    
    public void setVicies(ArrayList<Vertex> nwVirticies){
        Vertex[] original = nwVirticies.toArray(new Vertex[nwVirticies.size()]);
        int nbBuffer = original.length/BUFFER_SIZE;
        if(original.length%BUFFER_SIZE != 0){
            nbBuffer++;
        }
        
        int posCurseur = 0;
        for(int i = 0; i < nbBuffer;i++){
            int bufferLength = 0;
            int reste = original.length - posCurseur;
            
            if(reste >= BUFFER_SIZE){
                bufferLength = BUFFER_SIZE;
            }else{
                bufferLength = reste;
            }
            
            Vertex[] buffer  = new Vertex[bufferLength];
            //Copying elements
            System.arraycopy(original, posCurseur, buffer, 0, bufferLength);
            mVerticies.add(buffer);
            
            posCurseur+= bufferLength;
        }
    }
    
    public void drawYourSelf(GraphicEngine displayer,RenderStates states){
        for(Vertex[] toDraw:mVerticies ){
            displayer.getRenderTarget().draw(toDraw,PrimitiveType.QUADS,states);
        }
    }
    
    private final int mLayer[][];
    private List<Vertex[]> mVerticies;
    private final Map.Filter mFilter;
    private final int BUFFER_SIZE = 256;

    Map.Filter getFilter() {
        return mFilter;
    }

}
