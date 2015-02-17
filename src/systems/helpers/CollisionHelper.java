/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.helpers;

import components.HitBox;
import components.Transformation;
import org.jsfml.graphics.FloatRect;
import org.jsfml.system.Vector2f;

/**
 *
 */
public class CollisionHelper {

    /**
     * Test if two hitbox are colliding.
     *
     * @param transA Position of entity A
     * @param hitA Hitbox of entity A
     * @param transB Position of entity B
     * @param hitB Hitbox of entity B
     * @return true if the hitboxes overload
     */
    public static boolean isHitting(Transformation transA,
            HitBox hitA,
            Transformation transB,
            HitBox hitB) {

        Vector2f posA = transA.getTransformable().getPosition();
        FloatRect rectA = hitA.moveCopy(posA);

        Vector2f posB = transB.getTransformable().getPosition();
        FloatRect rectB = hitB.moveCopy(posB);

        return rectA.intersection(rectB) != null;
    }

}
