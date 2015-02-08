
package components;

import com.artemis.Component;
import static components.Player.Direction.UP;

/**
 *
 */
public class Player extends Component {

    public enum Direction {

        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    private Direction direction;

    public Player() {
        this.direction = UP;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }


}
