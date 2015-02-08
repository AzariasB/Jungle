/*
 Put the utility of the class here
 */
package map;

import graphics.GraphicEngine;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jsfml.graphics.ConstTexture;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.Vertex;
import org.jsfml.graphics.VertexArray;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

public class Map {

    public Map(String map_source, GraphicEngine g_eng) {
        mLayers = new ArrayList<>();
        Loader fileLoad = new Loader(map_source);
        mLayers = fileLoad.getLayers();
        mTexture = g_eng.getTexture("tiles.png");
        displayMap();
        loadVertex();
    }

    public List<Layer> getLayers() {
        return mLayers;
    }

    public void displayMap() {
        for (Filter fil : Filter.values()) {
            System.out.println(fil.Name() + "==============");
            if (mLayers.get(fil.Index()) != null) {
                System.out.println(Arrays.deepToString(mLayers.get(fil.Index()).getArray()));
            }

        }
    }

    public static Filter getFilter(String filterName) {
        for (Filter fil : Filter.values()) {
            if (fil.Name().equals(filterName)) {
                return fil;
            }
        }
        return null;
    }

    public boolean isHittingBlock(float x, float y, float width, float height) {

        Vector2i positArr = getPositInArray((int) x, (int) y);

        int mWidth = ((int) (width) + TILE_SIZE - 1) / TILE_SIZE;
        int mHeight = ((int) (height) + TILE_SIZE - 1) / TILE_SIZE;

        for (int _y = 0; _y <= mHeight; _y++) {
            for (int _x = 0; _x <= mWidth; _x++) {

                Layer lay = mLayers.get(Filter.COLLISION.Index());
                if (lay.blockExists(positArr.x + _x, positArr.y + _y)) {
                    return true;
                }
            }
        }
        return false;
    }
    

    private Vector2i getPositInArray(int x_pos, int y_pos) {
        int xArrPos = x_pos / TILE_SIZE;
        int yArrPos = y_pos / TILE_SIZE;

        return new Vector2i(xArrPos, yArrPos);
    }

    public void render(GraphicEngine drawInIt) {
        //System.out.println("Taille : " + mVertexs.size());
        RenderStates render = new RenderStates(mTexture);

        for (Layer lay : mLayers) {
            lay.drawYourSelf(drawInIt, render);
        }
    }

    private void loadVertex() {
        ArrayList<Vertex> verticies = new ArrayList<>();
        for (Layer lay : mLayers) {
            int[][] myArray = lay.getArray();
            for (int y_arr = 0; y_arr < myArray.length; y_arr++) {
                for (int x_arr = 0; x_arr < myArray[y_arr].length; x_arr++) {
                    int indexSp = myArray[y_arr][x_arr];
                    if (indexSp > 0) {
                        indexSp--;
                        int XTextPos = (indexSp % 64);
                        int yTextPos = ((indexSp - XTextPos) / 64) * TILE_SIZE;
                        XTextPos *= TILE_SIZE;

                        Vertex leftUpVertex = new Vertex(new Vector2f(x_arr * TILE_SIZE, y_arr * TILE_SIZE),
                                new Vector2f(XTextPos, yTextPos));

                        Vertex leftDownVertex = new Vertex(new Vector2f(x_arr * TILE_SIZE, (y_arr + 1) * TILE_SIZE),
                                new Vector2f(XTextPos, yTextPos + TILE_SIZE));

                        Vertex rightUpVertex = new Vertex(new Vector2f((x_arr + 1) * TILE_SIZE, y_arr * TILE_SIZE),
                                new Vector2f(XTextPos + TILE_SIZE, yTextPos));

                        Vertex rightDownVertex = new Vertex(new Vector2f((x_arr + 1) * TILE_SIZE, (y_arr + 1) * TILE_SIZE),
                                new Vector2f(XTextPos + TILE_SIZE, yTextPos + TILE_SIZE));

                        verticies.add(leftUpVertex);
                        verticies.add(rightUpVertex);
                        verticies.add(rightDownVertex);
                        verticies.add(leftDownVertex);

                    }
                }
            }
            lay.setVerticies(verticies);
            verticies.clear();
        }

    }

    private List<Layer> mLayers;
    private final ConstTexture mTexture;

    public static final int NB_FILTERS = 7;
    public static final int TILE_SIZE = 16;

    public enum Filter {

        GROUND("Ground", 0),
        DECORATION("Decoration", 1),
        COLLISION("Collision", 2),
        DECO_COL("Deco_s_collision", 3),
        FOREGROUND("Foreground", 4),
        DECO_FG("Deco_s_fg", 5),
        CLOUD("Cloud", 6);

        private Filter(String name, int index) {
            mName = name;
            mIndex = index;
        }

        public String Name() {
            return mName;
        }

        public int Index() {
            return mIndex;
        }

        private final String mName;
        private final int mIndex;
        private VertexArray mVertexs;

    }

}
