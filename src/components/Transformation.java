
package components;

import com.artemis.Component;
import org.jsfml.graphics.BasicTransformable;
import org.jsfml.graphics.Transformable;
import org.jsfml.system.Vector2f;

/**
 *
 */
public class Transformation extends Component {

    private Transformable mTransformatable;

    /**
     * Default constructor
     *
     * @see BasicTransformable BasicTransformable to know defaults values
     */
    public Transformation() {
        mTransformatable = new BasicTransformable();
    }

    /**
     * Constructor with an initial position.
     *
     * @param x Position x
     * @param y Position y
     */
    public Transformation(float x, float y) {
        this();
        mTransformatable.setPosition(x, y);
    }

    /**
     * Constructor with an initial position.
     *
     * @param position Position
     */
    public Transformation(Vector2f position) {
        this(position.x, position.y);
    }

    public Transformable getTransformable() {
        return mTransformatable;
    }

    public Vector2f getPosition() {
        return getTransformable().getPosition();
    }
}
