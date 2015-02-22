/*
 All the objects that you can put on a map :
 - Spawnpoint
 - (Coins && bonuses)
 - ...
 */
package map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        mPath = new ArrayList<>();
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
        mPath = new ArrayList<>();
        mProprieties = proprieties;
    }

    /**
     *
     * @param mWidth width of the object
     * @param mHeight height of the object
     * @param mType type of the object (may not exist)
     * @param mName name of the object
     * @param mRotation rotation (may not exist)
     * @param mId id of the oject
     * @param mPosition postion in the map of the object
     * @param mPoints if the object is a path, or a polygon, it has got multiple
     * points
     * @param mProprieties the personnal propreties of the object
     */
    public MapObject(int mWidth, int mHeight, String mType, String mName, int mRotation, int mId, Vector2f mPosition, List<Vector2f> mPoints, Map<String, Object> mProprieties) {
        this.mId = mId;
        this.mType = mType;
        this.mName = mName;
        this.mWidth = mWidth;
        this.mPath = mPoints;
        this.mHeight = mHeight;
        this.mPosition = mPosition;
        this.mRotation = mRotation;
        this.mProprieties = mProprieties;
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

    public void setPath(List<Vector2f> path) {
        this.mPath = new ArrayList<>();
        for(Vector2f oneP : path){
            Vector2f exactPosition = new Vector2f(oneP.x + mPosition.x ,oneP.y + mPosition.y);
            mPath.add(exactPosition);
        }
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

    public List<Vector2f> getPath() {
        return mPath;
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
    private OBJECT_TYPE mShape;
    private List<Vector2f> mPath;
    private java.util.Map<String, Object> mProprieties;

    public enum OBJECT_TYPE {

        SQUARE, CIRCLE, POLYGON, PATH
    }
}
