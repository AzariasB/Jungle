/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components.interfaces;

import java.util.Iterator;
import java.util.List;
import org.jsfml.system.Vector2f;

/**
 *
 */
public interface AIPathFollower extends AIStateBased {

    void setPath(List<Vector2f> path);

    Iterator<Vector2f> getPathIterator();

    void setGoal(Vector2f goal);

    Vector2f getGoal();

}
