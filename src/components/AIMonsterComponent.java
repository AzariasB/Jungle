
package components;

import com.artemis.Component;
import components.interfaces.AIPathFollower;
import java.util.Iterator;
import java.util.List;
import org.jsfml.system.Vector2f;

/**
 *
 */
public class AIMonsterComponent extends Component implements AIPathFollower {

    private int mState;
    private List<Vector2f> mPath;
    private Iterator<Vector2f> mPit;
    private Vector2f mGoal;
    private Vector2f mOldPlayerPos;
    private final Iterable<Vector2f> mIdlePath;
    private Iterator<Vector2f> mIdlePathIterator;

    /**
     *
     * @param idlePath Path to follow when the monster is in idle state.
     */
    public AIMonsterComponent(Iterable<Vector2f> idlePath) {
        mIdlePath = idlePath;
        mState = 0;
        resetIdlePathIterator();
    }

    public final void resetIdlePathIterator() {
        mIdlePathIterator = mIdlePath.iterator();
    }

    public Iterator<Vector2f> getIdlePathIterator() {
        return mIdlePathIterator;
    }


    public int getState() {
        return mState;
    }

    public void setState(int state) {
        mState = state;
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
