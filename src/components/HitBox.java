
package components;

import com.artemis.Component;
import org.jsfml.graphics.FloatRect;

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


}
