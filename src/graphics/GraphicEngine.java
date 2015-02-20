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
import org.jsfml.graphics.ConstFont;
import org.jsfml.graphics.ConstTexture;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.VideoMode;
import org.jsfml.window.WindowStyle;
import org.jsfml.window.event.Event;

public class GraphicEngine {

    public GraphicEngine(int screenWidht, int screenHeight, String titre_fen, boolean fullscreen) {
        /*
         Element initialisation
         */
        mTextures = new HashMap<>();
        mFonts = new HashMap<>();
        mCamera = new Camera(Vector2f.ZERO, new Vector2f(screenWidht, screenHeight));
        mWindow = new RenderWindow();

        /*
         Element setup
         */
        int style = WindowStyle.CLOSE;
        if (fullscreen) {
            style = WindowStyle.FULLSCREEN;
        }
        mWindow.create(new VideoMode(screenWidht, screenHeight), titre_fen, style);
        mWindow.setView(mCamera.getView());
    }

    public GraphicEngine(Vector2i windowSize, String windowName, boolean fullscreenMode) {
        this(windowSize.x, windowSize.y, windowName, fullscreenMode);
    }

    public ConstTexture getTexture(String textureName) {
        if (mTextures.containsKey(textureName)) {
            return mTextures.get(textureName);
        }
        Path path = FileSystems.getDefault().getPath(".", "assets", "Textures", textureName);
        Texture tex = new Texture();
        try {
            tex.loadFromFile(path);
        } catch (IOException ex) {
            Logger.getLogger(GraphicEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
        mTextures.put(textureName, tex);
        return tex;
    }

    public ConstFont getFont(String fontName) {
        if (mTextures.containsKey(fontName)) {
            return mFonts.get(fontName);
        }
        Path path = FileSystems.getDefault().getPath(".", "assets", "Fonts", fontName);
        Font font = new Font();
        try {
            font.loadFromFile(path);
        } catch (IOException ex) {
            Logger.getLogger(GraphicEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
        mFonts.put(fontName, font);
        return font;
    }

    public void setCamera(Camera newCam) {
        mCamera = newCam;
    }
    
    public Camera getCamera(){
        return this.mCamera;
    }

    public Iterable<Event> getWindowEvents() {
        return mWindow.pollEvents();
    }

    public RenderTarget getRenderTarget() {
        return mWindow;
    }

    public void beginRender() {
        mWindow.clear();
        mCamera.updateCamera();
        mWindow.setView(mCamera.getView());
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
    private Map<String, Font> mFonts;

}
