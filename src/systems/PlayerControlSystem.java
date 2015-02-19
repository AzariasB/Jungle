package systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import components.Player;
import components.Player.Direction;
import static components.Player.Direction.DOWN;
import static components.Player.Direction.LEFT;
import static components.Player.Direction.RIGHT;
import static components.Player.Direction.UP;
import components.RenderableSprite;
import components.SpriteAnimation;
import components.Velocity;
import java.util.ArrayDeque;
import java.util.Deque;
import org.jsfml.system.Vector2f;

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

    Deque<Direction> mForces;

    @SuppressWarnings("unchecked")
    public PlayerControlSystem() {
        super(Aspect.getAspectForAll(
                Player.class,
                Velocity.class,
                RenderableSprite.class,
                SpriteAnimation.class));

        mForces = new ArrayDeque<>(4);
    }

    private final float VELOCITY = 250;

    @Override
    protected void process(Entity entity) {

        SpriteAnimation sa = sam.get(entity);

        if (mForces.isEmpty()) {
            sa.setPlaying(false);
            vm.get(entity).setVelocity(Vector2f.ZERO);

        } else {
            sa.setPlaying(true);

            Player p = pm.get(entity);
            RenderableSprite rs = rsm.get(entity);

            Direction last = mForces.getLast();
            p.setDirection(last);

            float x = 0, y = 0;

            switch (last) {
                case UP:
                    y -= VELOCITY;
                    rs.setRectTop(3 * 32);
                    break;
                case LEFT:
                    x -= VELOCITY;
                    rs.setRectTop(32);
                    break;
                case DOWN:
                    y += VELOCITY;
                    rs.setRectTop(0);
                    break;
                case RIGHT:
                    x += VELOCITY;
                    rs.setRectTop(2 * 32);
                    break;
            }

            vm.get(entity).setVelocity(new Vector2f(x, y));
        }

    }

    public void goUp() {
        mForces.addLast(UP);
    }

    public void goLeft() {
        mForces.addLast(LEFT);
    }

    public void goDown() {
        mForces.addLast(DOWN);
    }

    public void goRight() {
        mForces.addLast(RIGHT);
    }

    @SuppressWarnings("empty-statement")
    public void stopUp() {
        while (mForces.removeFirstOccurrence(UP));
    }

    @SuppressWarnings("empty-statement")
    public void stopLeft() {
        while (mForces.removeFirstOccurrence(LEFT));
    }

    @SuppressWarnings("empty-statement")
    public void stopDown() {
        while (mForces.removeFirstOccurrence(DOWN));
    }

    @SuppressWarnings("empty-statement")
    public void stopRight() {
        while (mForces.removeFirstOccurrence(RIGHT));
    }

}
