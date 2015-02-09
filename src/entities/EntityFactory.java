
package entities;

import com.artemis.Entity;
import com.artemis.World;
import components.Collectable;
import components.HitBox;
import components.RenderableSprite;
import components.Transformation;
import org.jsfml.graphics.FloatRect;

/**
 *
 */
public class EntityFactory {

    public static Entity createNoixCoco(World world, float x, float y) {
        Entity noixCoco = world.createEntity();
        noixCoco.addComponent(new Transformation(x, y));
        noixCoco.addComponent(new RenderableSprite("coco.png"));
        noixCoco.addComponent(new HitBox(new FloatRect(0, 0, 16, 16)));
        noixCoco.addComponent(new Collectable());
        noixCoco.addToWorld();
        return noixCoco;
    }

}
