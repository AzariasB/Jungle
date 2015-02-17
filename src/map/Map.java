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
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

public class Map {

    public Map(GraphicEngine g_eng) {
        mLayers = new ArrayList<>();
        mObjects = new ArrayList<>();
        mTexture = g_eng.getTexture("tiles.png");
        mEmptyLayer = (int xi, int yi) -> false;
    }

    public List<Layer> getLayers() {
        return mLayers;
    }

    public Vector2f getSpawnPoint() {
        int i;
        for (i = 0; i < mObjects.size() && !"spawnpoint".equals(mObjects.get(i).getName().toLowerCase()); i++) {

        }
        if (i < mObjects.size() && mObjects.get(i).getName().toLowerCase().equals("spawnpoint")) {
            return mObjects.get(i).getPosition();
        } else {
            return Vector2f.ZERO;
        }
    }

    public List<Vector2f> getCoins() {
        ArrayList<Vector2f> myCoins = new ArrayList<>();
        for (int i = 0; i < mObjects.size(); i++) {
            if (mObjects.get(i).getName().toLowerCase().equals("coin")) {
                myCoins.add(mObjects.get(i).getPosition());
            }
        }
        return myCoins;
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
            System.out.println(fil.getName() + "==============");
            if (mLayers.get(fil.getIndex()) != null) {
                System.out.println(Arrays.deepToString(mLayers.get(fil.getIndex()).getArray()));
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

        TileTest lay = getCollisionLayer();

        for (int _iy = _sy; _iy <= _ey; _iy++) {
            for (int _ix = _sx; _ix <= _ex; _ix++) {
                if (lay.tileExists(_ix, _iy)) {
                    return true;
                }
            }
        }

        return false;
    }

    private TileTest getCollisionLayer() {
        if (LayerType.COLLISION.getIndex() < mLayers.size()) {
            return mLayers.get(LayerType.COLLISION.getIndex());
        }
        return mEmptyLayer;
    }

    public List<Vector2f> computePath(float startX, float startY, float toX, float toY,
            float width, float height) {
        int sx = ((int) startX) / TILE_SIZE;
        int sy = ((int) startY) / TILE_SIZE;
        int tx = ((int) toX) / TILE_SIZE;
        int ty = ((int) toY) / TILE_SIZE;
        int w = ((int) width + TILE_SIZE - 1) / TILE_SIZE;
        int h = ((int) height + TILE_SIZE - 1) / TILE_SIZE;

        List<Vector2i> indexPath = PathFinding.compute(getCollisionLayer(), sx, sy, tx, ty, w, h);
        List<Vector2f> path = new ArrayList<>(indexPath.size());

        for (Vector2i ic : indexPath) {
            path.add(new Vector2f(ic.x * TILE_SIZE, ic.y * TILE_SIZE));
        }

        return path;
    }

    public void render(GraphicEngine drawInIt) {
        RenderStates render = new RenderStates(mTexture);
        for (int i = 0 ; i < LayerType.FOREGROUND.Index();i++) {
            mLayers.get(i).drawYourSelf(drawInIt, render);
        }
    }
    
    public void renderFg(GraphicEngine drawInThat){
        RenderStates render = new RenderStates(mTexture);
        for(int i = LayerType.FOREGROUND.Index(); i < mLayers.size();i++){
            mLayers.get(i).drawYourSelf(drawInThat, render);
        }
    }

    /**
     * Function that takes the id of the grid and turn it into a vertex
     *
     * It takes care of the rotation of the tiles (horizontal && vertical)
     *
     */
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

    private TileTest mEmptyLayer;
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

        public String getName() {
            return mName;
        }

        public int getIndex() {
            return mIndex;
        }

        private final String mName;
        private final int mIndex;
    }

}
