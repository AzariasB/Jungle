/*
 Put the utility of the class here
 */
package map;

import graphics.GraphicEngine;
import java.util.ArrayList;
import java.util.List;
import org.jsfml.graphics.PrimitiveType;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.Vertex;

public class Layer {

    public Layer(int[][] blockArray,Map.LayerType myFilter) {
        mLayer = blockArray;
        mFilter = myFilter;
        mVerticies = new ArrayList<>();
    }


    public int[][] getArray() {
        return mLayer;
    }
    
    public void setVerticies(ArrayList<Vertex> nwVirticies){
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
    
    public boolean blockExists(int x_index, int y_index) {
        // TODO : remove when we are sure our player is
        // always into the map.
        if (0 <= y_index && y_index < mLayer.length
                && 0 <= x_index && x_index < mLayer[y_index].length) {
            return mLayer[y_index][x_index] != 0;
        }
        return false;
    }
    
    private final int mLayer[][];
    private List<Vertex[]> mVerticies;
    private final Map.LayerType mFilter;
    private final int BUFFER_SIZE = 256;

    Map.LayerType getFilter() {
        return mFilter;
    }

}
