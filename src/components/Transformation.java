
package components;

import com.artemis.Component;
import org.jsfml.graphics.BasicTransformable;
import org.jsfml.graphics.Transformable;

/**
 *
 */
public class Transformation extends Component {

    private Transformable mTransformatable;

    public Transformation() {
        mTransformatable = new BasicTransformable();
    }

    public Transformation(float x, float y) {
        this();
        mTransformatable.setPosition(x, y);
    }

    public Transformable getTransformable() {
        return mTransformatable;
    }
}
