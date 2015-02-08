
package components;

import com.artemis.Component;
import org.jsfml.system.Vector2f;

/**
 *
 */
public class Velocity extends Component {

    private Vector2f mVelocity;

    public Velocity() {
        mVelocity = Vector2f.ZERO;
    }

    public Velocity(Vector2f vel) {
        setVelocity(vel);
    }

    public Vector2f getVelocity() {
        return mVelocity;
    }

    public void setVelocity(Vector2f velocity) {
        this.mVelocity = velocity;
    }
}
