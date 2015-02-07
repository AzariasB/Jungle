/*

Graphic engine of the game

 */
package graphics;

import java.util.Collection;
import java.util.HashSet;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2i;
import org.jsfml.window.VideoMode;
import org.jsfml.window.WindowStyle;


public class GraphicEngine {
    
    public GraphicEngine(int screen_widht,int screen_height,String titre_fen,boolean fullscreen){
        /*
        Element initialisation
        */
        mDrawables = new HashSet<>();
        mWindow = new RenderWindow();
        
        /*
        Element setup
        */
        int style = WindowStyle.CLOSE;
        if (fullscreen) {
            style = WindowStyle.FULLSCREEN;
        }
        mWindow.create(new VideoMode(screen_widht, screen_height), titre_fen, style);
    }

    public GraphicEngine(Vector2i windowSize, String windowName, boolean fullscreenMode) {
        this(windowSize.x, windowSize.y, windowName, fullscreenMode);
    }
    
    public void addDrawable(Drawable d_toad){
        mDrawables.add(d_toad);
    }
    
    public void remove(Drawable d_toremove){
        mDrawables.remove(d_toremove);
    }
    
    public void render() {
        mWindow.clear();
        /*
        Draw all the Drawable in the HashSet
        */
        for (Drawable todraw : mDrawables) {
            mWindow.draw(todraw);
        }
        mWindow.display();
    }
    
    public RenderWindow getWindow(){
        return mWindow;
    }
    
    public void setCamera(Camera newCam){
        mCamera = newCam;
    }
    
    public void close() {
        mWindow.close();
    }

    private Camera mCamera;
    private Collection<Drawable> mDrawables;
    private RenderWindow mWindow;

    public RenderTarget getRenderTarget() {
        return mWindow;
    }

    
}
