
package systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.managers.TagManager;
import com.artemis.systems.EntityProcessingSystem;
import components.AIPetComponent;
import components.Transformation;
import components.Velocity;
import org.jsfml.system.Vector2f;

/**
 *
 */
public class AIPetSystem extends EntityProcessingSystem {

    @Mapper
    ComponentMapper<AIPetComponent> petm;

    @Mapper
    ComponentMapper<Velocity> vm;

    @Mapper
    ComponentMapper<Transformation> tm;

    private Vector2f playerPos;

    @SuppressWarnings("unchecked")
    public AIPetSystem() {
        super(Aspect.getAspectForAll(
                AIPetComponent.class,
                Velocity.class,
                Transformation.class));
    }

    @Override
    protected void begin() {
        Entity player = world.getManager(TagManager.class).getEntity("PLAYER");
        if (player != null) {
            playerPos = tm.getSafe(player).getTransformable().getPosition();
        } else {
            playerPos = new Vector2f(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
        }
    }

    @Override
    protected void process(Entity entity) {
        AIPetComponent petCmpt = petm.get(entity);
        Velocity vel = vm.get(entity);
        Transformation t = tm.get(entity);
        Vector2f pos = t.getTransformable().getPosition();

        int currentState = petCmpt.getState();

        switch (currentState) {
            case 0: // Search Player
                int x = (int) (pos.x - playerPos.x);
                int y = (int) (pos.y - playerPos.y);

                if (x * x + y * y < 400) {
                    petCmpt.setState(1);
                }
                break;

            case 1: // Follow player
                x = (int) (pos.x - playerPos.x);
                y = (int) (pos.y - playerPos.y);

                if (x > 0) {
                    x = 1;
                } else if (x < 0) {
                    x = -1;
                }

                if (y > 0) {
                    y = 1;
                } else if (y < 0) {
                    y = -1;
                }

                vel.setVelocity(new Vector2f(-x * 20, -y * 20));

                if (x * x + y * y > 400) {
                    vel.setVelocity(new Vector2f(0, 0));
                    petCmpt.setState(0);
                }

                break;

        }

    }

}
