
package components;

import com.artemis.Component;
import java.util.Iterator;
import java.util.List;
import org.jsfml.system.Vector2f;

/**
 *
 */
public class AIPetComponent extends Component {

    private int mState;
    private List<Vector2f> mPath;
    private Iterator<Vector2f> mPit;
    private Vector2f mGoal;
    private Vector2f mOldPlayerPos;

    public int getState() {
        return mState;
    }

    public void setState(int state) {
        this.mState = state;
    }

    public void setPath(List<Vector2f> path) {
        mPath = path;
        mPit = path.iterator();
    }

    public Iterator<Vector2f> getPathIterator() {
        return mPit;
    }

    public void setGoal(Vector2f goal) {
        mGoal = goal;
    }

    public Vector2f getGoal() {
        return mGoal;
    }

    public void setOldPlayerPos(Vector2f playerPos) {
        mOldPlayerPos = playerPos;
    }

    public Vector2f getOldPlayerPos() {
        return mOldPlayerPos;
    }

    public Iterable<Vector2f> getPath() {
        return mPath;
    }

}
