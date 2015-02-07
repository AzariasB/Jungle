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

    public GraphicEngine(int screenWidht, int screenHeight, String titre_fen, boolean fullscreen) {
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
        mWindow.create(new VideoMode(screenWidht, screenHeight), titre_fen, style);
    }

    public GraphicEngine(Vector2i windowSize, String windowName, boolean fullscreenMode) {
        this(windowSize.x, windowSize.y, windowName, fullscreenMode);
    }

    public void setCamera(Camera newCam) {
        mCamera = newCam;
    }

    public RenderWindow getWindow() {
        return mWindow;
    }

    public RenderTarget getRenderTarget() {
        return mWindow;
    }

    public void beginRender() {
        mWindow.clear();
        // TODO
        //mWindow.setView(mCamera.getView());
    }

    public void endRender() {
        mWindow.display();
    }

    public void close() {
        mWindow.close();
    }

    private Camera mCamera;
    private Collection<Drawable> mDrawables;
    private RenderWindow mWindow;
}
