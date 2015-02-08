
package systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import components.Player;
import components.Transformation;
import org.jsfml.graphics.Transformable;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;

/**
 *
 */
public class PlayerControlSystem extends EntityProcessingSystem {

    @Mapper
    ComponentMapper<Transformation> pm;

    @SuppressWarnings("unchecked")
    public PlayerControlSystem() {
        super(Aspect.getAspectForAll(Player.class, Transformation.class));
    }

    @Override
    protected void process(Entity entity) {

        Transformable transformable = pm.get(entity).getTransformable();
        Vector2f pos = transformable.getPosition();
        float x = pos.x, y = pos.y;

        if (Keyboard.isKeyPressed(Keyboard.Key.UP)) {
            y -= 100 * world.delta;
        } else if (Keyboard.isKeyPressed(Keyboard.Key.RIGHT)) {
            x += 100 * world.delta;
        } else if (Keyboard.isKeyPressed(Keyboard.Key.DOWN)) {
            y += 100 * world.delta;
        } else if (Keyboard.isKeyPressed(Keyboard.Key.LEFT)) {
            x -= 100 * world.delta;
        }

        transformable.setPosition(new Vector2f(x, y));
    }

}
