/*
  A Layer is a part of the map
    Each layers have is utility in the map
    A Layer contains multiple chunks
    Thus no need to load all the map anymore, just some chunks
 */
package map;

import graphics.GraphicEngine;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.Vertex;
import org.jsfml.system.Vector2f;

public class Layer implements TileTest {

    public Layer(int[][] blockArray, Map.LayerType myFilter) {
        MAP_HEIGHT = blockArray.length;
        MAP_WIDTH = blockArray[0].length;
        mChunks = new ArrayList<>();
        mFilter = myFilter;
        setChunks(blockArray);
    }

    /**
     * This function calculate in terms of the position, the width and the height,
     * what chunck should be displayed, and then, draw them in the rendertarget
     * 
     * @param displayer The target to draw in
     * @param states The renderState to draw the verticies
     * @param position The top-bottom position of the view
     * @param width The width of the view
     * @param height The height of the view
     */
    public void drawYourSelf(GraphicEngine displayer, RenderStates states, Vector2f position, int width, int height) {
        position = new Vector2f( ((int)position.x) >> MAGIC_CHUNK, ((int)position.y) >> MAGIC_CHUNK);
        for (int yChunckIndex = (int) position.y; yChunckIndex < (int) position.y + (height >> MAGIC_CHUNK ); yChunckIndex += CHUNK_SIZE) {
            for (int xChunkIndex = (int) position.x; xChunkIndex < (int) position.x + (width >> MAGIC_CHUNK ); xChunkIndex += CHUNK_SIZE) {
                int chunckIndex = (xChunkIndex >> MAGIC_CHUNK) + ((yChunckIndex >> MAGIC_CHUNK) * ((MAP_WIDTH >> MAGIC_CHUNK ) + 1));
                if (chunckIndex < mChunks.size() && chunckIndex >= 0 ) {
                    mChunks.get(chunckIndex).drawYourVerticies(displayer, states);
                }
            }
        }
    }

    @Override
    public boolean tileExists(int xIndex, int yIndex) {
        // TODO : remove when we are sure our player is
        // always into the map.
        int chunck = (xIndex >> MAGIC_CHUNK ) + ((yIndex >> MAGIC_CHUNK) * ( (MAP_WIDTH >> MAGIC_CHUNK ) + 1 ));
        
        int xPos = xIndex & MASK_CHUNK;
        int yPos = yIndex & MASK_CHUNK;

        if (chunck < mChunks.size() && chunck >= 0 && xPos >= 0 && yPos >= 0) {
            return mChunks.get(chunck).getTile(xPos, yPos) != 0;
        }else{
            return false;
        }
    }

    private void setChunks(int[][] map) {
        createChunks(MAP_WIDTH, MAP_HEIGHT);
        int nbChunksWidth = (MAP_WIDTH >> MAGIC_CHUNK) + 1;
        for (int yTileIndex = 0; yTileIndex < MAP_HEIGHT; yTileIndex++) {
            for (int xChunkIndex = 0; xChunkIndex <= MAP_WIDTH >> MAGIC_CHUNK; xChunkIndex++) {
                int indexChunk = (xChunkIndex + ((yTileIndex >> MAGIC_CHUNK) * nbChunksWidth));
                int[] nwLine = Arrays.copyOfRange(map[yTileIndex], (xChunkIndex << MAGIC_CHUNK), (xChunkIndex + 1) << MAGIC_CHUNK);
                try {
                    mChunks.get(indexChunk).addLineToMap(nwLine, yTileIndex % 16);
                } catch (IndexOutOfBoundsException ex) {
                    System.err.println("Trop de tiles => " + ex.getMessage());
                }
            }
        }
        updateChunks();
    }

    private void updateChunks() {
        for (int i = 0; i < mChunks.size(); i++) {
            int xpos = i % ((MAP_WIDTH >> MAGIC_CHUNK) + 1);
            int ypos = (i - xpos) / ((MAP_WIDTH >> MAGIC_CHUNK) + 1);
            List<Vertex> chVertex = Map.loadVertex(mChunks.get(i).getMap(), xpos << MAGIC_CHUNK, ypos << MAGIC_CHUNK);
            mChunks.get(i).update(chVertex);
        }
    }

    private void createChunks(int width, int height) {
        for (int y = 0; y <= height >> MAGIC_CHUNK; y++) {
            for (int x = 0; x <= width >> MAGIC_CHUNK; x++) {
                mChunks.add(new Chunk(CHUNK_SIZE, CHUNK_SIZE));
            }
        }
    }
    private final List<Chunk> mChunks;
    private final Map.LayerType mFilter;
    public final int MAP_WIDTH;
    public final int MAP_HEIGHT;

    private static final int CHUNK_SIZE = 16;
    private static final int MASK_CHUNK = 0xF;
    private static final int MAGIC_CHUNK = 4;

    Map.LayerType getFilter() {
        return mFilter;
    }

}
