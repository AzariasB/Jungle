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
                    if (indexSp > 0) {
                        indexSp--;
                    }
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
            lay.setVerticies(verticies);
            verticies.clear();
        }

    }

    private List<Layer> mLayers;
    private List<MapObject> mObjects;
    private final ConstTexture mTexture;

    public static final int NB_FILTERS = 7;
    public static final int TILE_SIZE = 16;

    public enum LayerType {

        GROUND("Ground", 0),
        DECORATION("Decoration", 1),
        COLLISION("Collision", 2),
        DECO_COL("Deco_s_collision", 3),
        FOREGROUND("Foreground", 4),
        DECO_FG("Deco_s_fg", 5),
        CLOUD("Cloud", 6);

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
