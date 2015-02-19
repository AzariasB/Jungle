
package systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.managers.TagManager;
import com.artemis.systems.EntityProcessingSystem;
import components.AIMonsterComponent;
import components.HitBox;
import components.MultipleAnimations;
import components.Transformation;
import components.Velocity;
import content.Animations;
import java.util.List;
import map.Map;
import org.jsfml.graphics.FloatRect;
import org.jsfml.system.Vector2f;
import systems.helpers.DistanceHelper;

/**
 *
 */
public class AIMonsterSystem extends EntityProcessingSystem {

    @Mapper
    ComponentMapper<AIMonsterComponent> monsterm;

    @Mapper
    ComponentMapper<Velocity> vm;

    @Mapper
    ComponentMapper<Transformation> tm;

    @Mapper
    ComponentMapper<HitBox> hm;

    @Mapper
    ComponentMapper<MultipleAnimations> mam;

    private Vector2f playerPos;
    private final Map mMap;
    private FloatRect playerBox;


    @SuppressWarnings("unchecked")
    public AIMonsterSystem(Map map) {
        super(Aspect.getAspectForAll(
                AIMonsterComponent.class,
                Velocity.class,
                Transformation.class,
                HitBox.class,
                MultipleAnimations.class
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
        AIMonsterComponent monster = monsterm.get(entity);
        Velocity vel = vm.get(entity);
        Transformation t = tm.get(entity);
        Vector2f pos = t.getTransformable().getPosition();
        FloatRect hitbox = hm.get(entity).getHitBox();
        MultipleAnimations ma = mam.get(entity);
                
        int currentState = monster.getState();

        //System.out.println(currentState);

        switch (currentState) {
            case 0: // Idle state. Follow its idle path
                if (monster.getIdlePathIterator().hasNext()) {
                    Vector2f next = monster.getIdlePathIterator().next();
                    List<Vector2f> path = mMap.computePath(
                            pos.x,
                            pos.y,
                            next.x,
                            next.y,
                            hitbox.width + hitbox.left,
                            hitbox.height + hitbox.top);
                    monster.setPath(path);
                    monster.setState(1);
                } else {
                    monster.resetIdlePathIterator();
                }
                break;


            case 1: // Move to next tile
                if (monster.getPathIterator().hasNext()) {
                    Vector2f next = monster.getPathIterator().next();
                    monster.setGoal(next);
                    Vector2f diff = Vector2f.sub(next, pos);
                    float fact = 50 / ((float) Math.sqrt(diff.x * diff.x + diff.y * diff.y));

                    if (diff.x > diff.y && diff.x > -diff.y) {// right
                        ma.setAnimation(Animations.GO_RIGHT);
                    } else if (-diff.x > diff.y && -diff.x > -diff.y) {// left
                        ma.setAnimation(Animations.GO_LEFT);
                    } else if (diff.y > 0) {// down
                        ma.setAnimation(Animations.GO_DOWN);
                    } else {// up
                        ma.setAnimation(Animations.GO_UP);
                    }

                    vel.setVelocity(Vector2f.mul(diff, fact));

                    monster.setState(2);
                } else {
                    vel.setVelocity(Vector2f.ZERO);
                    monster.setState(0);
                }
                break;

            case 2: // Test if goal was reached
                if (DistanceHelper.distance(pos, monster.getGoal()) < 1) {
                    monster.setState(1);
                }
                // if player is near
                if (DistanceHelper.distance(pos, playerPos) < 256) {
                    monster.setState(3);
                }
                break;

            case 3: // chase player
                List<Vector2f> path = mMap.computePath(
                        pos.x,
                        pos.y,
                        playerPos.x,
                        playerPos.y,
                        hitbox.width + hitbox.left,
                        hitbox.height + hitbox.top);
                monster.setPath(path);
                monster.setState(4);
                break;

        }

    }

}
