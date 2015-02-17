
package systems.helpers;

import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

/**
 *
 */
public class DistanceHelper {

    public static float distance(Vector2f a, Vector2f b) {
        return (a.x - b.x) * (a.x - b.x)
                + (a.y - b.y) * (a.y - b.y);
    }

    public static int distance(Vector2i a, Vector2i b) {
        return (a.x - b.x) * (a.x - b.x)
                + (a.y - b.y) * (a.y - b.y);
    }

}
