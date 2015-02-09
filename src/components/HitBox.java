
package components;

import com.artemis.Component;
import org.jsfml.graphics.FloatRect;
import org.jsfml.system.Vector2f;

/**
 *
 */
public class HitBox extends Component {

    private FloatRect mHitBox;

    public HitBox(FloatRect hitbox) {
        mHitBox = hitbox;
    }

    public FloatRect getHitBox() {
        return mHitBox;
    }

    public void setHitBox(FloatRect mHitBox) {
        this.mHitBox = mHitBox;
    }

    public FloatRect moveCopy(Vector2f offset) {
        return new FloatRect(offset.x + mHitBox.left,
                offset.y + mHitBox.top,
                mHitBox.width,
                mHitBox.height);

    }

}
