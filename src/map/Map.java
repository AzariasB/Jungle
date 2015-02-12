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

public class Map {

    public Map(GraphicEngine g_eng) {
        mLayers = new ArrayList<>();
        mObjects = new ArrayList<>();
        mTexture = g_eng.getTexture("tiles.png");
    }

    public List<Layer> getLayers() {
        return mLayers;
    }

    public List<MapObject> getObject() {
        return mObjects;
    }

    public void setObjects(List<MapObject> myNewObjects) {
        mObjects = myNewObjects;
    }

    public void setLayers(List<Layer> myNewLayers) {
        mLayers = myNewLayers;
        loadVertex();
    }

    public void displayMap() {
        for (LayerType fil : LayerType.values()) {
            System.out.println(fil.Name() + "==============");
            if (mLayers.get(fil.Index()) != null) {
                System.out.println(Arrays.deepToString(mLayers.get(fil.Index()).getArray()));
            }

        }
    }

    public boolean isHittingBlock(float x, float y, float width, float height) {

        int _x = (int) x;
        int _y = (int) y;
        int _w = (int) width;
        int _h = (int) height;

        int _sx = _x / TILE_SIZE;
        int _sy = _y / TILE_SIZE;

        int _ex = (_x + _w - 1) / TILE_SIZE;
        int _ey = (_y + _h - 1) / TILE_SIZE;

        for (int _iy = _sy; _iy <= _ey; _iy++) {
            for (int _ix = _sx; _ix <= _ex; _ix++) {
                if (mLayers.size() >= LayerType.COLLISION.Index()) {
                    Layer lay = mLayers.get(LayerType.COLLISION.Index());
                    if (lay.blockExists(_ix, _iy)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void render(GraphicEngine drawInIt) {
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
                    int XTextPos;
                    int yTextPos;

                    Vertex leftUpVertex;
                    Vertex leftDownVertex;
                    Vertex rightUpVertex;
                    Vertex rightDownVertex;

                    int luXincr = 0;
                    int luYincr = 0;
                    int ldXincr = 0;
                    int ldYincr = 0;
                    int rdXincr = 0;
                    int rdYincr = 0;
                    int ruXincr = 0;
                    int ruYincr = 0;

                    if (indexSp < 0) { // With Horizontal case
                        indexSp = indexSp << 24;
                        indexSp = Integer.reverseBytes(indexSp);

                        luXincr = 1;
                        ldXincr = 1;
                        ldYincr = 1;
                        rdYincr = 1;

                    } else if (indexSp > LAST_TILESET) { // With vertical rotation case
                        indexSp = indexSp << 24;
                        indexSp = Integer.reverseBytes(indexSp);

                        luYincr = 1;
                        ruXincr = 1;
                        ruYincr = 1;
                        rdXincr = 1;

                    } else { // Normal case
                        ruXincr = 1;
                        rdXincr = 1;
                        rdYincr = 1;
                        ldYincr = 1;

                    }
                    indexSp--;

                    XTextPos = (indexSp % 64);
                    yTextPos = ((indexSp - XTextPos) / 64) * TILE_SIZE;
                    XTextPos *= TILE_SIZE;

                    leftUpVertex = new Vertex(new Vector2f(x_arr * TILE_SIZE, y_arr * TILE_SIZE),
                            new Vector2f(XTextPos + luXincr * TILE_SIZE, yTextPos + luYincr * TILE_SIZE));

                    leftDownVertex = new Vertex(new Vector2f(x_arr * TILE_SIZE, (y_arr + 1) * TILE_SIZE),
                            new Vector2f(XTextPos + ldXincr * TILE_SIZE, yTextPos + ldYincr * TILE_SIZE));

                    rightUpVertex = new Vertex(new Vector2f((x_arr + 1) * TILE_SIZE, y_arr * TILE_SIZE),
                            new Vector2f(XTextPos + ruXincr * TILE_SIZE, yTextPos + ruYincr * TILE_SIZE));

                    rightDownVertex = new Vertex(new Vector2f((x_arr + 1) * TILE_SIZE, (y_arr + 1) * TILE_SIZE),
                            new Vector2f(XTextPos + rdXincr * TILE_SIZE, yTextPos + rdYincr * TILE_SIZE));

                    verticies.add(leftUpVertex);
                    verticies.add(rightUpVertex);
                    verticies.add(rightDownVertex);
                    verticies.add(leftDownVertex);

                }
            }
            lay.setVerticies(verticies);
            verticies.clear();
        }

    }

    private List<Layer> mLayers;
    private List<MapObject> mObjects;
    private final ConstTexture mTexture;

    public static final int NB_FILTERS = 7;
    public static final int TILE_SIZE = 16;
    public static final int LAST_TILESET = 8064;

    public enum LayerType {

        GROUND("Ground", 0),
        DECORATION("Decoration", 1),
        QUITUE("QuiTue", 2),
        COLLISION("Collision", 3),
        DECO_COL("Deco_s_collision", 4),
        FOREGROUND("Foreground", 5),
        DECO_FG("Deco_s_fg", 6),
        CLOUD("Cloud", 7);

        private LayerType(String name, int index) {
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
