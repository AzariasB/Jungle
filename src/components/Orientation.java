
package components;

import com.artemis.Component;
import static components.Orientation.Direction.DOWN;

/**
 *
 */
public class Orientation extends Component {

    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    private Direction mDirection;

    public Orientation() {
        mDirection = DOWN;
    }

    public Orientation(Direction direction) {
        mDirection = direction;
    }

    public Direction getDirection() {
        return mDirection;
    }

    public void setDirection(Direction direction) {
        this.mDirection = direction;
    }

}
