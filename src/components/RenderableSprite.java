
package components;

import com.artemis.Component;
import org.jsfml.graphics.ConstTexture;
import org.jsfml.graphics.IntRect;
import org.jsfml.system.Vector2i;

/**
 *
 */
public class RenderableSprite extends Component {

    private int spriteId;
    private ConstTexture mTexture;
    private IntRect mRect;

    public RenderableSprite(int spriteId) {
        this.spriteId = spriteId;
    }

    public void setTexture(ConstTexture tex) {
        mTexture = tex;
    }

    public ConstTexture getTexture() {
        return mTexture;
    }

    public void setRect(Vector2i size) {
        mRect = new IntRect(Vector2i.ZERO, size);
    }

    /**
     * @return the mRect
     */
    public IntRect getRect() {
        return mRect;
    }

    /**
     * @return the spriteId
     */
    public int getSpriteId() {
        return spriteId;
    }

    /**
     * @param spriteId the spriteId to set
     */
    public void setSpriteId(int spriteId) {
        this.spriteId = spriteId;
    }


}
