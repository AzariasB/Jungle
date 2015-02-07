/*

 Graphic engine of the game

 */
package graphics;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsfml.graphics.ConstTexture;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2i;
import org.jsfml.window.VideoMode;
import org.jsfml.window.WindowStyle;

public class GraphicEngine {

    public GraphicEngine(int screenWidht, int screenHeight, String titre_fen, boolean fullscreen) {
        /*
         Element initialisation
         */
        mTextures = new HashMap<>();

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

    public ConstTexture getTexture(String textureName) {
        if (mTextures.containsKey(textureName)) {
            return mTextures.get(textureName);
        }
        Path path = FileSystems.getDefault().getPath(".", "assets", "textures", textureName);
        Texture tex = new Texture();
        try {
            tex.loadFromFile(path);
        } catch (IOException ex) {
            Logger.getLogger(GraphicEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
        mTextures.put(textureName, tex);
        return tex;
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
    private RenderWindow mWindow;

    private Map<String, Texture> mTextures;

}
