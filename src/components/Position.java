
package components;

import com.artemis.Component;
import org.jsfml.system.Vector2f;

/**
 *
 */
public class Position extends Component {

    private Vector2f mPosition;

    public Position(float x, float y) {
        mPosition = new Vector2f(x, y);
    }

    public Vector2f getPosition() {
        return mPosition;
    }

    public void setPosition(Vector2f position) {
        this.mPosition = position;
    }

}
