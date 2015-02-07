/*
    Put the utility of the class here
*/

package map;

import graphics.GraphicEngine;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jsfml.graphics.Vertex;
import org.jsfml.graphics.VertexArray;
import org.jsfml.system.Vector2f;

public class Map {

    public Map(String map_source){
        mLayers = new ArrayList<>();
        Loader fileLoad = new Loader(map_source);
        mLayers = fileLoad.getLayers();
        mVertexs = new VertexArray();
        loadVertex();
    }
    
    public List<Layer> getLayers(){
        return mLayers;
    }
    
    public void displayMap(){
        for(Filters fil:Filters.values()){
            System.out.println(fil.Name() + "==============");
            if(mLayers.get(fil.Index()) != null){
                System.out.println(Arrays.deepToString(mLayers.get(fil.Index()).getArray()));
            }
            
        }
    }
    
    public static Filters getFilter(String filterName){
       for(Filters fil:Filters.values()){
           if(fil.Name().equals(filterName)){
               return fil;
           }
       }
       return null;
    }
    
    private void loadVertex(){
        int x_arr = 0;
        int y_arr = 0;
        for(Layer lay:mLayers){
            for(int[] y:lay.getArray()){
                for(int x:y){
                    int XTextPos = x%65 -1;
                    int yTextPos = XTextPos + x%64;
                    Vertex leftUpVertex = new Vertex(new Vector2f(x_arr, y_arr), new Vector2f(XTextPos, yTextPos));
                    Vertex leftDownVertex = new Vertex(new Vector2f(x_arr, y_arr+TILE_SIZE), new Vector2f(XTextPos, yTextPos + TILE_SIZE));
                    Vertex rightUpVertex = new Vertex(new Vector2f(x_arr+ TILE_SIZE, y_arr), new Vector2f(XTextPos + TILE_SIZE, yTextPos));
                    Vertex rightDownVertex = new Vertex(new Vector2f(x_arr + TILE_SIZE, y_arr + TILE_SIZE), new Vector2f(XTextPos + TILE_SIZE, yTextPos + TILE_SIZE));
//                    mVertexs.add(leftUpVertex);
                    mVertexs.add(leftDownVertex);
                    mVertexs.add(rightUpVertex);
                    mVertexs.add(rightDownVertex);
                    x_arr++;
                }
            }
            y_arr++;
        }
    }
    
    public void render(GraphicEngine drawInIt){
        drawInIt.getWindow().draw(mVertexs);
    }
    
    
    
    private List<Layer> mLayers;
    private VertexArray mVertexs;
    
    
    public static final int NB_FILTERS =  7;
    public static final int TILE_SIZE = 16;
    
    public enum Filters {
        GROUND("Ground",0),
        DECORATION("Decoration",1),
        COLLISION("Collision",2),
        DECO_COL("Deco_s_collision",3),
        FOREGROUND("Foreground",4),
        DECO_FG("Deco_s_fg",5),
        CLOUD("Cloud",6);
        
        private Filters(String name,int index){
           mName = name;
           mIndex = index;
        }
        
        public String Name(){
            return mName;
        }
        
        public int Index(){
            return mIndex;
        }
        
        private final String mName;
        private final int mIndex;
        private VertexArray mVertexs;
        
    }
    
}
