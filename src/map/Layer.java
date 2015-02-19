/*
 Put the utility of the class here
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
        //   mLayer = blockArray;
        MAP_HEIGHT = blockArray.length;
        MAP_WIDTH = blockArray[0].length;
        mChunks = new ArrayList<>();
        mFilter = myFilter;
        setChunks(blockArray);
        //    mVerticies = new ArrayList<>();
    }

//    public void setVerticies(ArrayList<Vertex> nwVirticies){
//        }
//    }
    public void drawYourSelf(GraphicEngine displayer, RenderStates states, Vector2f position, int width, int height) {
        for (int yChunckIndex = (int) position.y; yChunckIndex < (int) position.y + height; yChunckIndex += 16) {
            for (int xChunkIndex = (int) position.x; xChunkIndex < (int) position.x + width; xChunkIndex += 16) {
                int chunckIndex = (xChunkIndex / CHUNK_SIZE) + ((yChunckIndex / CHUNK_SIZE) * (MAP_WIDTH / CHUNK_SIZE + 1));
                if (chunckIndex < mChunks.size()) {
                    mChunks.get(chunckIndex).drawYourVerticies(displayer, states);
                }
            }
        }
    }

    @Override
    public boolean tileExists(int xIndex, int yIndex) {
        // TODO : remove when we are sure our player is
        // always into the map.
        int chunck = xIndex / CHUNK_SIZE + ((yIndex / CHUNK_SIZE) * (MAP_WIDTH / CHUNK_SIZE + 1));

        int xPos = xIndex % CHUNK_SIZE;
        int yPos = yIndex % CHUNK_SIZE;

        if (chunck < mChunks.size() && xPos > 0 && yPos > 0 && xPos < CHUNK_SIZE && yPos < CHUNK_SIZE ) {
            return mChunks.get(chunck).getTile(xPos, yPos) != 0;
        }else{
            return false;
        }
    }

    private void setChunks(int[][] map) {
        createChunks(MAP_WIDTH, MAP_HEIGHT);
        int nbChunksWidth = (MAP_WIDTH / CHUNK_SIZE) + 1;
        for (int yTileIndex = 0; yTileIndex < MAP_HEIGHT; yTileIndex++) {
            for (int xChunkIndex = 0; xChunkIndex <= MAP_WIDTH / CHUNK_SIZE; xChunkIndex++) {
                int indexChunk = (xChunkIndex + ((yTileIndex / CHUNK_SIZE) * nbChunksWidth));
                int[] nwLine = Arrays.copyOfRange(map[yTileIndex], (xChunkIndex * CHUNK_SIZE), (xChunkIndex + 1) * CHUNK_SIZE);
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
            int xpos = i % ((MAP_WIDTH / CHUNK_SIZE) + 1);
            int ypos = (i - xpos) / ((MAP_WIDTH / CHUNK_SIZE) + 1);
            List<Vertex> chVertex = Map.loadVertex(mChunks.get(i).getMap(), xpos * CHUNK_SIZE, ypos * CHUNK_SIZE);
            mChunks.get(i).update(chVertex);
        }
    }

    private void createChunks(int width, int height) {
        for (int y = 0; y <= height / CHUNK_SIZE; y++) {
            for (int x = 0; x <= width / CHUNK_SIZE; x++) {
                mChunks.add(new Chunk(CHUNK_SIZE, CHUNK_SIZE));
            }
        }
    }
    private final List<Chunk> mChunks;
    private final Map.LayerType mFilter;
    private final int MAP_WIDTH;
    private final int MAP_HEIGHT;

    private static final int CHUNK_SIZE = 16;

    Map.LayerType getFilter() {
        return mFilter;
    }

}
