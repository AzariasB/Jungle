
package components;

import com.artemis.Component;
import org.jsfml.graphics.ConstTexture;
import org.jsfml.graphics.IntRect;
import org.jsfml.system.Vector2i;

/**
 *
 */
public class RenderableSprite extends Component {

    private ConstTexture mTexture;
    private IntRect mRect;
    private final String mTexName;

    public RenderableSprite(String textureName, IntRect rect) {
        mTexName = textureName;
        mRect = rect;
    }

    public RenderableSprite(String textureName) {
        this(textureName, null);
    }

    

    public void setTexture(ConstTexture tex) {
        mTexture = tex;
    }

    public ConstTexture getTexture() {
        return mTexture;
    }

    public IntRect getRect() {
        return mRect;
    }

    public void setRectSize(Vector2i size) {
        mRect = new IntRect(Vector2i.ZERO, size);
    }

    public void setRectTop(int top) {
        mRect = new IntRect(mRect.left, top, mRect.width, mRect.height);
    }

    public void setRect(IntRect rect) {
        mRect = rect;
    }

    public String getTextureName() {
        return mTexName;
    }


}
