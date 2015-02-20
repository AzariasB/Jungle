package systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import components.AnimatedTextureRect;
import components.MultipleAnimations;
import components.Orientation;
import components.Orientation.Direction;
import static components.Orientation.Direction.DOWN;
import static components.Orientation.Direction.LEFT;
import static components.Orientation.Direction.RIGHT;
import static components.Orientation.Direction.UP;
import components.Player;
import components.Transformation;
import components.Velocity;
import entities.EntityFactory;
import java.util.ArrayDeque;
import java.util.Deque;
import org.jsfml.system.Vector2f;
import systems.helpers.AnimationHelper;

/**
 *
 */
public class PlayerControlSystem extends EntityProcessingSystem {

    @Mapper
    ComponentMapper<Player> pm;

    @Mapper
    ComponentMapper<Velocity> vm;

    @Mapper
    ComponentMapper<MultipleAnimations> mam;

    @Mapper
    ComponentMapper<Orientation> om;

    @Mapper
    ComponentMapper<Transformation> tm;

    Deque<Direction> mForces;
    private boolean isAttacking;

    @SuppressWarnings("unchecked")
    public PlayerControlSystem() {
        super(Aspect.getAspectForAll(
                Player.class,
                Transformation.class,
                Velocity.class,
                MultipleAnimations.class,
                Orientation.class));

        mForces = new ArrayDeque<>(4);
    }

    private final float VELOCITY = 250;

    @Override
    protected void process(Entity entity) {
        MultipleAnimations ma = mam.get(entity);
        AnimatedTextureRect anim = ma.getCurrentAnimation();

        if (mForces.isEmpty()) {
            anim.setPlaying(false);
            vm.get(entity).setVelocity(Vector2f.ZERO);

        } else {
            anim.setPlaying(true);

            Orientation or = om.get(entity);
            Direction last = mForces.getLast();
            or.setDirection(last);
            AnimationHelper.setAnimationByOrientation(ma, or);

            vm.get(entity).setVelocity(or.getVector2f(VELOCITY));
        }

        if (isAttacking) {
            isAttacking = false;

            Transformation t = tm.get(entity);
            Vector2f pos = t.getTransformable().getPosition();

            Orientation o = om.get(entity);
            o.getVector2f();

            EntityFactory.createDamageFromPlayer(world, pos, o);
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

    public void attack() {
        isAttacking = true;
    }

}
