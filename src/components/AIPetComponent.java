
package components;

import components.interfaces.AIPathFollower;
import com.artemis.Component;
import java.util.Iterator;
import java.util.List;
import org.jsfml.system.Vector2f;

/**
 *
 */
public class AIPetComponent extends Component implements AIPathFollower {

    private int mState;
    private List<Vector2f> mPath;
    private Iterator<Vector2f> mPit;
    private Vector2f mGoal;
    private Vector2f mOldPlayerPos;

    @Override
    public int getState() {
        return mState;
    }

    @Override
    public void setState(int state) {
        this.mState = state;
    }

    @Override
    public void setPath(List<Vector2f> path) {
        mPath = path;
        mPit = path.iterator();
    }

    @Override
    public Iterator<Vector2f> getPathIterator() {
        return mPit;
    }

    @Override
    public void setGoal(Vector2f goal) {
        mGoal = goal;
    }

    @Override
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
