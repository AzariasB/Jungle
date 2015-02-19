package map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.jsfml.system.Vector2i;

/**
 *
 */
public class PathFinding {

    private static final Vector2i[] DIRECTIONS
            = {new Vector2i(1, 0), new Vector2i(0, 1), new Vector2i(-1, 0),
                new Vector2i(0, -1)};

    /**
     *
     * @param lay
     * @param sx Start x index
     * @param sy
     * @param tx Destination x index
     * @param ty
     * @return if destination is not reachable, return an empty path
     */
    static List<Vector2i> compute(TileTest collisionLayer, int sx, int sy, int tx, int ty,
            int w, int h) {

        if (isColliding(collisionLayer, new Vector2i(tx, ty), w, h)) {
            return new ArrayList<>(0);
        }

        Vector2i source = new Vector2i(sx, sy);
        Vector2i destination = new Vector2i(tx, ty);

        /*
         * 1) Add the starting square (or node) to the open list.
         *
         * 2) Repeat the following:
         *
         * a) Look for the lowest cost square on the open list. We refer to this
         * as the current square.
         *
         * b) Switch it to the closed list.
         *
         * c) For each of the 4 squares adjacent to this current square …
         *
         * If it is not walkable or if it is on the closed list, ignore it.
         * Otherwise do the following.
         *
         * If it isn’t on the open list, add it to the open list. Make the
         * current square the parent of this square. Record the cost of the
         * square.
         *
         * If it is on the open list already, check to see if this path to that
         * square is better, using cost as the measure. A lower cost means that
         * this is a better path. If so, change the parent of the square to the
         * current square, and recalculate the score of the square.
         *
         * d) Stop when we:
         *
         * Add the target square to the closed list, in which case the path has
         * been found (see note below), or Fail to find the target square, and
         * the open list is empty. In this case, there is no path.
         *
         * 3) Save the path. Working backwards from the target square, go from
         * each square to its parent square until you reach the starting square.
         * That is the path.
         */
        Set<Vector2i> openSet = new HashSet<>();
        Set<Vector2i> closedSet = new HashSet<>();
        // permit to only associate data to visited tile
        // and so, avoid to initialize all tiles.
        java.util.Map<Vector2i, Vector2i> metaData = new HashMap<>();

        metaData.put(source, null);

        openSet.add(source);

        while (true) {

            // Fail to find the target square, and the open list is empty.
            // In this case, there is no path.
            if (openSet.isEmpty()) {
                break;
            }

            // Look for the lowest F cost square on the open list.
            // We refer to this as the current square.
            // Use Euclidean distance as our heuristic.
            // init current tile
            Vector2i current = null;
            int currentDistance = Integer.MAX_VALUE;

            for (Vector2i it : openSet) {
                int itDistance = computeFastDistance(it, destination);

                if (itDistance < currentDistance) {
                    current = it;
                    currentDistance = itDistance;
                }
            }

            // Switch it to the closed list.
            openSet.remove(current);
            closedSet.add(current);

            if (current.equals(destination)) {
                break;
            }

            /*
             * For each of the 4 squares adjacent to this current square …
             *
             * If it is not walkable or if it is on the closed list, ignore it.
             * Otherwise do the following.
             *
             * If it isn’t on the open list, add it to the open list. Make the
             * current square the parent of this square. Record the cost of the
             * square.
             *
             * If it is on the open list already, check to see if this path to
             * that square is better, using cost as the measure. A lower cost
             * means that this is a better path. If so, change the parent of the
             * square to the current square, and recalculate the score of the
             * square.
             */
            for (Vector2i d : DIRECTIONS) {
                Vector2i adjacent = Vector2i.add(current, d);

                if (closedSet.contains(adjacent)) {
                    continue;
                }

                /* Test collisions */
                if (isColliding(collisionLayer, adjacent, w, h)) {
                    closedSet.add(adjacent);
                    continue;
                }

                openSet.add(adjacent);
                metaData.put(adjacent, current);
            }
        }

        List<Vector2i> path = null;

        // If destination was reached
        if (closedSet.contains(destination)) {
            path = new LinkedList<>();
            // Save the path. Working backwards from the target square,
            // go from each square to its parent square until you reach the
            // starting square.
            Vector2i it = destination;
            while (it != null) {
                path.add(0, it);
                it = metaData.get(it);
            }

            // Remove the source tile because we are already onto
            path.remove(source);
        }

        return path;
    }

    private static boolean isColliding(TileTest collisionLayer, Vector2i adjacent, int w, int h) {
        boolean ok = true;
        int _ey = adjacent.y + h;
        int _ex = adjacent.x + w;
        for (int _iy = adjacent.y; _iy <= _ey; _iy++) {
            for (int _ix = adjacent.x; _ix <= _ex; _ix++) {
                if (collisionLayer.tileExists(_ix, _iy)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static int computeFastDistance(Vector2i a, Vector2i b) {
        int x = a.x - b.x;
        int y = a.y - b.y;

        if (x < 0) {
            x = -x;
        }
        if (y < 0) {
            y = -y;
        }

        return x + y;// x * x + y * y;
    }

}
