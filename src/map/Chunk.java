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

public class Chunk {

    public Chunk(int width, int height) {
        mMap = new int[height][width];
        mVerticies = new ArrayList<>();
    }

    public void addLineToMap(int[] nwLine, int position) {
        if (position < mMap.length) {
            mMap[position] = nwLine;
        }else{
            System.out.println("Trop grand");
        }
    }

    public void update(List<Vertex> nwVirticies ) {
        Vertex[] original = nwVirticies.toArray(new Vertex[nwVirticies.size()]);
        int nbBuffer = original.length / BUFFER_SIZE;
        if (original.length % BUFFER_SIZE != 0) {
            nbBuffer++;
        }

        int posCurseur = 0;
        for (int i = 0; i < nbBuffer; i++) {
            int bufferLength = 0;
            int reste = original.length - posCurseur;

            if (reste >= BUFFER_SIZE) {
                bufferLength = BUFFER_SIZE;
            } else {
                bufferLength = reste;
            }

            Vertex[] buffer = new Vertex[bufferLength];
            //Copying elements
            System.arraycopy(original, posCurseur, buffer, 0, bufferLength);

            mVerticies.add(buffer);

            posCurseur += bufferLength;
        }
    }

    public int getTile(int xPos, int yPos) {
        if (yPos < mMap.length && xPos < mMap[0].length) {
            return mMap[yPos][xPos];
        } else {
            return 0;
        }
    }
    
    public int[][] getMap(){
        return mMap;
    }
    
    /**
     * This function draw all the Verticies that the chunck contains
     * 
     * @param target The RenderTarget to draw the verticies
     * @param renderState The RenderState to draw the verticies correctly
     */
    public void drawYourVerticies(GraphicEngine target, RenderStates renderState) {
        for (Vertex[] toDraw : mVerticies) {
            target.getRenderTarget().draw(toDraw, PrimitiveType.QUADS, renderState);
        }

    }

    private final int[][] mMap;
    private final List<Vertex[]> mVerticies;
    
    private final int BUFFER_SIZE = 256;
}
