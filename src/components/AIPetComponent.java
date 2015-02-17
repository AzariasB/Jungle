
package components;

import com.artemis.Component;
import java.util.Iterator;
import java.util.List;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

/**
 *
 */
public class AIPetComponent extends Component {

    private int mState;
    private List<Vector2i> mPath;
    private Iterator<Vector2i> mPit;
    private Vector2f mGoal;

    public int getState() {
        return mState;
    }

    public void setState(int state) {
        this.mState = state;
    }

    public void setPath(List<Vector2i> path) {
        mPath = path;
        mPit = path.iterator();
    }

    public Iterator<Vector2i> getPathIterator() {
        return mPit;
    }

    public void setGoal(Vector2f goal) {
        mGoal = goal;
    }

    public Vector2f getGoal() {
        return mGoal;
    }

}
