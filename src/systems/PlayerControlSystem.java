
package systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import components.Player;
import components.Velocity;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;

/**
 *
 */
public class PlayerControlSystem extends EntityProcessingSystem {

    @Mapper
    ComponentMapper<Velocity> vm;

    @SuppressWarnings("unchecked")
    public PlayerControlSystem() {
        super(Aspect.getAspectForAll(Player.class,
                Velocity.class));
    }

    private final float VELOCITY = 100;

    @Override
    protected void process(Entity entity) {

        float x = 0, y = 0;

        if (Keyboard.isKeyPressed(Keyboard.Key.UP)) {
            y -= VELOCITY;
        } else if (Keyboard.isKeyPressed(Keyboard.Key.RIGHT)) {
            x += VELOCITY;
        } else if (Keyboard.isKeyPressed(Keyboard.Key.DOWN)) {
            y += VELOCITY;
        } else if (Keyboard.isKeyPressed(Keyboard.Key.LEFT)) {
            x -= VELOCITY;
        }

        vm.get(entity).setVelocity(new Vector2f(x, y));
    }

}
