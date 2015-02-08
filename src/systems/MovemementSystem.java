
package systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import components.CollideWithMap;
import components.Transformation;
import components.Velocity;
import org.jsfml.graphics.Transformable;
import org.jsfml.system.Vector2f;

/**
 *
 */
public class MovemementSystem extends EntityProcessingSystem {

    @Mapper
    ComponentMapper<Velocity> vm;

    @Mapper
    ComponentMapper<Transformation> tm;

    @SuppressWarnings("unchecked")
    public MovemementSystem() {
        super(Aspect.getAspectForAll(Transformation.class, Velocity.class)
                .exclude(CollideWithMap.class));
    }

    @Override
    protected void process(Entity entity) {
        Transformable transm = tm.get(entity).getTransformable();
        Velocity velm = vm.get(entity);

        Vector2f pos = transm.getPosition();
        Vector2f vel = velm.getVelocity();

        vel = Vector2f.mul(vel, world.delta);
        transm.setPosition(Vector2f.add(pos, vel));
    }

}
