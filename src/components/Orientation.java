
package components;

import com.artemis.Component;
import static components.Orientation.Direction.DOWN;
import org.jsfml.system.Vector2f;

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

    public Vector2f getVector2f() {
        switch (mDirection) {
            case UP:
                return sVec2fUp;
            case LEFT:
                return sVec2fLeft;
            case DOWN:
                return sVec2fDown;
            case RIGHT:
                return sVec2fRight;
        }
        return null;
    }

    /**
     * Get the direction vector multiplied by a factor.
     *
     * @param factor Factor to multiply the vector.
     * @return
     */
    public Vector2f getVector2f(float factor) {
        return Vector2f.mul(getVector2f(), factor);
    }

    /**
     * Get the angle in deegres
     *
     * @return Degree angle of the direction
     */
    public float getAngle() {
        switch (mDirection) {
            case UP:
                return 0f;
            case LEFT:
                return -90f;
            case DOWN:
                return 180f;
            case RIGHT:
                return 90f;
        }
        return 0f;
    }

    private static final Vector2f sVec2fUp = new Vector2f(0.f, -1.f);
    private static final Vector2f sVec2fDown = new Vector2f(0.f, 1.f);
    private static final Vector2f sVec2fLeft = new Vector2f(-1.f, 0.f);
    private static final Vector2f sVec2fRight = new Vector2f(1.f, 0.f);

}
