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
     * @param id : objects id
     * @param width must be a multiple of 16 (to hold in a square)
     * @param height must be a multiple of 16 (to hold in a square)
     * @param position : the position of the object in the map. Normally, it is
     * two integers, by security and if it changes, it is better to take two
     * float
     */
    public MapObject(int id, int width, int height, Vector2f position) {
        mId = id;
        mWidth = width;
        mHeight = height;
        mPosition = position;
        mProprieties = new HashMap<>();
    }

    /**
     *
     * @param id : objects id (unic)
     * @param name : objects name
     * @param type : some object can have a type ... for the moment, it's
     * useless
     * @param width : objects width
     * @param height : objects height
     * @param position : objects position
     * @param proprieties : proprieties of the object : a standard is define for
     * the proprieties's possible values
     */
    public MapObject(String name, String type, int id, int width, int height, Vector2f position, java.util.Map<String, Object> proprieties) {
        mId = id;
        mName = name;
        mType = type;
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

    public void setProprieties(java.util.Map<String, Object> proprieties) {
        this.mProprieties = proprieties;
    }

    public void setWidth(int width) {
        this.mWidth = width;
    }

    public void setHeight(int height) {
        this.mHeight = height;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public void setmPosition(Vector2f mPosition) {
        this.mPosition = mPosition;
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

    public java.util.Map<String, Object> getProprieties() {
        return mProprieties;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public int getId() {
        return mId;
    }

    public int getmRotation() {
        return mRotation;
    }

    public String getType() {
        return mType;
    }

    /*
     Atributes :
     */
    private int mWidth;
    private int mHeight;
    private String mType;
    private String mName;
    private int mRotation;
    private final int mId;
    private Vector2f mPosition;
    private java.util.Map<String, Object> mProprieties;
}
