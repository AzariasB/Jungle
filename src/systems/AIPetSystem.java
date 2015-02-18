
package systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.managers.TagManager;
import com.artemis.systems.EntityProcessingSystem;
import components.AIPetComponent;
import components.HitBox;
import components.RenderableSprite;
import components.Transformation;
import components.Velocity;
import java.util.List;
import map.Map;
import org.jsfml.graphics.FloatRect;
import org.jsfml.system.Vector2f;
import systems.helpers.DistanceHelper;

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

    @Mapper
    ComponentMapper<HitBox> hm;

    @Mapper
    ComponentMapper<RenderableSprite> rsm;

    private Vector2f playerPos;
    private final Map mMap;
    private FloatRect playerBox;


    @SuppressWarnings("unchecked")
    public AIPetSystem(Map map) {
        super(Aspect.getAspectForAll(
                AIPetComponent.class,
                Velocity.class,
                Transformation.class,
                HitBox.class,
                RenderableSprite.class
        ));
        mMap = map;
    }

    @Override
    protected void begin() {
        Entity player = world.getManager(TagManager.class).getEntity("PLAYER");
        if (player != null) {
            playerPos = tm.getSafe(player).getTransformable().getPosition();
            playerBox = hm.getSafe(player).getHitBox();
        } else {
            playerPos = new Vector2f(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
            playerBox = FloatRect.EMPTY;
        }
    }

    @Override
    protected void process(Entity entity) {
        AIPetComponent petCmpt = petm.get(entity);
        Velocity vel = vm.get(entity);
        Transformation t = tm.get(entity);
        Vector2f pos = t.getTransformable().getPosition();
        FloatRect hitbox = hm.get(entity).getHitBox();
        RenderableSprite rs = rsm.get(entity);
                
        int currentState = petCmpt.getState();

        //System.out.println(currentState);

        switch (currentState) {
            case 0: // Compute path to player
                List<Vector2f> path = mMap.computePath(
                        pos.x,
                        pos.y,
                        playerPos.x,
                        playerPos.y,
                        hitbox.width,
                        hitbox.height);
                petCmpt.setOldPlayerPos(playerPos);
                petCmpt.setPath(path);
                petCmpt.setState(1);
                break;


            case 1: // Move to next tile
                if (petCmpt.getPathIterator().hasNext()) {
                    Vector2f next = petCmpt.getPathIterator().next();
                    petCmpt.setGoal(next);
                    Vector2f diff = Vector2f.sub(next, pos);
                    float fact = 50 / ((float) Math.sqrt(diff.x * diff.x + diff.y * diff.y));

                    if (diff.x > diff.y && diff.x > -diff.y) {// right
                        rs.setRectTop(19);
                    } else if (-diff.x > diff.y && -diff.x > -diff.y) {// left
                        rs.setRectTop(0);
                    } else if (diff.y > 0) {// down
                        rs.setRectTop(60);
                    } else {// up
                        rs.setRectTop(40);
                    }

                    vel.setVelocity(Vector2f.mul(diff, fact));

                    petCmpt.setState(2);
                } else {
                    vel.setVelocity(Vector2f.ZERO);
                    petCmpt.setState(0);
                }
                break;

            case 2: // Test if goal was reached
                if (DistanceHelper.distance(pos, petCmpt.getGoal()) < 1) {
                    petCmpt.setState(1);
                }
                // if player moved a lot
                Vector2f playerDiff = Vector2f.sub(playerPos, petCmpt.getOldPlayerPos());
                if (Math.abs(playerDiff.x) + Math.abs(playerDiff.y) > 32) {
                    petCmpt.setState(0);
                }
                break;

        }

    }

}
