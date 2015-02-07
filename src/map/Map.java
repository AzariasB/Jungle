/*
    Put the utility of the class here
*/

package map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Map {

    public Map(String map_source){
        mLayers = new ArrayList<>();
        Loader fileLoad = new Loader(map_source);
        mLayers = fileLoad.getLayers();
        displayMap();
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
           System.out.println("Attendu : " + fil.Name() + " recu  : " + filterName);
           if(fil.Name().equals(filterName)){
               return fil;
           }
       }
       return null;
    }
    
    private List<Layer> mLayers;
    public static final int NB_FILTERS =  7;
    
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
        
    }
    
}
