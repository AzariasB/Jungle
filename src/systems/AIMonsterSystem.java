
package systems;

import architecture.AppContent;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.managers.TagManager;
import com.artemis.systems.EntityProcessingSystem;
import components.AIMonsterComponent;
import components.HitBox;
import components.MultipleAnimations;
import components.Orientation;
import components.Transformation;
import components.Velocity;
import entities.EntityFactory;
import map.Map;
import org.jsfml.graphics.FloatRect;
import org.jsfml.system.Vector2f;
import systems.helpers.AIHelper;
import systems.helpers.AnimationHelper;
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

    @Mapper
    ComponentMapper<Orientation> om;

    private Vector2f playerPos;
    private final Map mMap;
    private FloatRect playerBox;
    private final AppContent mAppContent;


    @SuppressWarnings("unchecked")
    public AIMonsterSystem(Map map, AppContent appContent) {
        super(Aspect.getAspectForAll(
                AIMonsterComponent.class,
                Velocity.class,
                Transformation.class,
                HitBox.class,
                MultipleAnimations.class,
                Orientation.class
        ));
        mMap = map;
        mAppContent = appContent;
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
        Orientation orientation = om.get(entity);
                
        int currentState = monster.getState();

        //System.out.println(currentState);

        switch (currentState) {
            case 0: // Idle state. Follow its idle path
                if (monster.getIdlePathIterator().hasNext()) {
                    Vector2f next = monster.getIdlePathIterator().next();

                    AIHelper.goTo(monster, mMap, pos, hitbox, next, 1);

                } else {
                    monster.resetIdlePathIterator();
                }
                break;


            case 1: // Move to next tile
                vel.setVelocity(AIHelper.followPath(monster, pos, 2, 0, orientation));
                AnimationHelper.setAnimationByOrientation(ma, orientation);
                break;

            case 2: // Test if goal was reached
                AIHelper.testPathProgress(monster, pos, 1);
                // if player is near
                
                if (DistanceHelper.distance(pos, playerPos) < 50000) {
                    monster.setState(3);
                }
                break;

            case 3: // chase player
                monster.setOldPlayerPos(playerPos);
                AIHelper.goTo(monster, mMap, pos, hitbox, playerPos, 4);
                break;

            case 4:
                vel.setVelocity(AIHelper.followPath(monster, pos, 5, 6, orientation));
                AnimationHelper.setAnimationByOrientation(ma, orientation);
                break;

            case 5:
                AIHelper.testPathProgress(monster, pos, 4);
                // if player is near
                if (DistanceHelper.fastDistance(pos, playerPos) < 120) {
                    monster.setState(6);
                } else if (DistanceHelper.fastDistance(pos, playerPos) > 300) {
                    monster.setState(0);
                }
                // if player moved a lot
                if (DistanceHelper.fastDistance(playerPos, monster.getOldPlayerPos()) > 32) {
                    monster.setState(3);
                }
                break;

            case 6: // attack player
                System.out.println("ATTACK!");
                EntityFactory.createFireBall(mAppContent, world, pos, orientation);
                AIHelper.startWaiting(monster, 7);
                break;
            case 7:
                AIHelper.updateWaiting(monster, 1000, 3);
                break;

        }

    }

}
