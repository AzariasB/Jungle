/*
 All the objects that you can put on a map :
 - Spawnpoint
 - (Coins && bonuses)
 - ...
 */
package map;

import java.util.HashMap;
import org.jsfml.system.Vector2f;

public class MapObject {

    /*
     Constructors
    Some object don't have any proprieties, so instead of passing 'null' argument, you have nothing to do :
     */
    /**
     *@param name : name of the object
    * @param width : the width of the object
    * @param height : the height of the object
    * @param position : the position of the object in the map. Normally, it is two integers, by security and if it changes, it is better to take two float
    */
    public MapObject(String name, int width, int height, Vector2f position) {
        mName = name;
        mWidth = width;
        mHeight = height;
        mPosition = position;
        mProprieties = new HashMap<>();
    }

    /**
     * 
     * @param name : objects name
     * @param width : objects width
     * @param height : objects height
     * @param position : objects position
     * @param proprieties  : proprieties of the object : a standard is define for the proprieties's possible values
     */
    public MapObject(String name, int width, int height, Vector2f position, java.util.Map<String, Integer> proprieties) {
        mName = name;
        mWidth = width;
        mHeight = height;
        mPosition = position;
        mProprieties = proprieties;
    }

    /*
     Setters
     */
    public void setName(String newName) {
        this.mName = newName;
    }

    public void setPosition(Vector2f newPosition) {
        this.mPosition = newPosition;
    }

    public void setProprieties(java.util.Map<String, Integer> proprieties) {
        this.mProprieties = proprieties;
    }

    public void setWidth(int width) {
        this.mWidth = width;
    }

    public void setHeight(int height) {
        this.mHeight = height;
    }

    /*
     Getters
     */
    public String getName() {
        return mName;
    }

    public Vector2f getPosition() {
        return mPosition;
    }

    public java.util.Map<String, Integer> getProprieties() {
        return mProprieties;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public int getGriddpos() {
        return griddpos;
    }

    /*
    Atributes :
    */
    private Vector2f mPosition;
    private int mWidth;
    private int mHeight;
    private int griddpos;
    private String mName;
    private java.util.Map<String, Integer> mProprieties;
}
