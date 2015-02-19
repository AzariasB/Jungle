package systems;

import architecture.AppContent;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import components.CollideWithMap;
import components.HitBox;
import components.Transformation;
import components.Velocity;
import map.Map;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Transformable;
import org.jsfml.system.Vector2f;

/**
 *
 */
public class MovemementCollideMapSystem extends EntityProcessingSystem {

    @Mapper
    ComponentMapper<Velocity> vm;

    @Mapper
    ComponentMapper<Transformation> tm;

    @Mapper
    ComponentMapper<HitBox> hbm;

    private final AppContent mApp;
    private Map mMap;

    @SuppressWarnings("unchecked")
    public MovemementCollideMapSystem(AppContent app, Map map) {
        super(Aspect.getAspectForAll(
                Transformation.class,
                Velocity.class,
                CollideWithMap.class,
                HitBox.class
        ));
        mApp = app;
        mMap = map;
    }

    @Override
    protected void process(Entity entity) {
        Transformable transm = tm.get(entity).getTransformable();
        Velocity velm = vm.get(entity);
        HitBox hitboxm = hbm.get(entity);

        Vector2f pos = transm.getPosition();
        Vector2f vel = velm.getVelocity();

        if (vel.x != 0 || vel.y != 0) {

            vel = Vector2f.mul(vel, world.delta);
            Vector2f newPos = Vector2f.add(pos, vel);

            FloatRect hitbox = hitboxm.getHitBox();

            if (!mMap.isHittingBlock(
                    newPos.x + hitbox.left,
                    newPos.y + hitbox.top,
                    hitbox.width,
                    hitbox.height)) {

                transm.setPosition(newPos);
            }

        }

    }

}
