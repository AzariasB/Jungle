package systems.helpers;

import components.Orientation;
import components.interfaces.AIPathFollower;
import components.interfaces.AIWaitable;
import java.util.Iterator;
import java.util.List;
import map.Map;
import org.jsfml.graphics.FloatRect;
import org.jsfml.system.Vector2f;

/**
 *
 */
public class AIHelper {

    public static void goTo(AIPathFollower pathFollower,
            Map mMap,
            Vector2f currentPosition,
            FloatRect hitbox,
            Vector2f destination,
            int stateFollowPath) {

        List<Vector2f> path = mMap.computePath(
                currentPosition.x,
                currentPosition.y,
                destination.x,
                destination.y,
                hitbox.width + hitbox.left,
                hitbox.height + hitbox.top);

        pathFollower.setPath(path);
        pathFollower.setState(stateFollowPath);
    }

    public static Vector2f followPath(AIPathFollower pathFollower,
            Vector2f currentPosition,
            int stateToTestWhenArrived,
            int stateWhenPathFinished,
            Orientation outOrientation) {

        Iterator<Vector2f> pathIt = pathFollower.getPathIterator();

        if (pathIt.hasNext()) {
            Vector2f next = pathIt.next();
            pathFollower.setGoal(next);
            Vector2f diff = Vector2f.sub(next, currentPosition);
            float fact = 50 / ((float) Math.sqrt(diff.x * diff.x + diff.y * diff.y));

            if (diff.x > diff.y && diff.x > -diff.y) {// right
                outOrientation.setDirection(Orientation.Direction.RIGHT);
            } else if (-diff.x > diff.y && -diff.x > -diff.y) {// left
                outOrientation.setDirection(Orientation.Direction.LEFT);
            } else if (diff.y > 0) {// down
                outOrientation.setDirection(Orientation.Direction.DOWN);
            } else {// up
                outOrientation.setDirection(Orientation.Direction.UP);
            }

            pathFollower.setState(stateToTestWhenArrived);

            return Vector2f.mul(diff, fact);

        } else {
            pathFollower.setState(stateWhenPathFinished);
            return Vector2f.ZERO;
        }
    }

    public static void testPathProgress(AIPathFollower pathFollower, Vector2f currentPosition, int stateToContinueFollowingPath) {
        if (DistanceHelper.fastDistance(currentPosition, pathFollower.getGoal()) < 2) {
            pathFollower.setState(stateToContinueFollowingPath);
        }
    }

    public static void startWaiting(AIWaitable waiter, int stateWait) {
        waiter.startTimer();
        waiter.setState(stateWait);
    }

    public static void updateWaiting(AIWaitable waiter, int timeToWait, int stateWhenWaitingFinish) {
        if (waiter.getElapsedTime() >= timeToWait) {
            waiter.setState(stateWhenWaitingFinish);
        }
    }

}
