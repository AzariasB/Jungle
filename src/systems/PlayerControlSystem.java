
package systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import components.Player;
import static components.Player.Direction.DOWN;
import static components.Player.Direction.LEFT;
import static components.Player.Direction.RIGHT;
import static components.Player.Direction.UP;
import components.RenderableSprite;
import components.SpriteAnimation;
import components.Velocity;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;


/**
 *
 */
public class PlayerControlSystem extends EntityProcessingSystem {

    @Mapper
    ComponentMapper<Player> pm;

    @Mapper
    ComponentMapper<Velocity> vm;

    @Mapper
    ComponentMapper<RenderableSprite> rsm;

    @Mapper
    ComponentMapper<SpriteAnimation> sam;

    @SuppressWarnings("unchecked")
    public PlayerControlSystem() {
        super(Aspect.getAspectForAll(
                Player.class,
                Velocity.class,
                RenderableSprite.class,
                SpriteAnimation.class));
    }

    private final float VELOCITY = 100;

    @Override
    protected void process(Entity entity) {

        Player p = pm.get(entity);
        RenderableSprite rs = rsm.get(entity);
        SpriteAnimation sa = sam.get(entity);

        float x = 0, y = 0;

        sa.setPlaying(true);

        if (Keyboard.isKeyPressed(Keyboard.Key.UP)) {
            y -= VELOCITY;
            p.setDirection(UP);
            rs.setRectTop(3 * 32);

        } else if (Keyboard.isKeyPressed(Keyboard.Key.RIGHT)) {
            x += VELOCITY;
            p.setDirection(RIGHT);
            rs.setRectTop(2 * 32);

        } else if (Keyboard.isKeyPressed(Keyboard.Key.DOWN)) {
            y += VELOCITY;
            rs.setRectTop(0);
            p.setDirection(DOWN);

        } else if (Keyboard.isKeyPressed(Keyboard.Key.LEFT)) {
            x -= VELOCITY;
            rs.setRectTop(32);
            p.setDirection(LEFT);

        } else {
            sa.setPlaying(false);
        }

        vm.get(entity).setVelocity(new Vector2f(x, y));
    }

}
