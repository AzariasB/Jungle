
package systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.managers.TagManager;
import com.artemis.systems.EntityProcessingSystem;
import components.AIPetComponent;
import components.HitBox;
import components.MultipleAnimations;
import components.Orientation;
import components.Transformation;
import components.Velocity;
import map.Map;
import org.jsfml.graphics.FloatRect;
import org.jsfml.system.Vector2f;
import systems.helpers.AIHelper;
import systems.helpers.AnimationHelper;
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
    ComponentMapper<MultipleAnimations> mam;

    @Mapper
    ComponentMapper<Orientation> om;

    private Vector2f playerPos;
    private final Map mMap;
    private FloatRect playerBox;
    private Entity mPlayer;


    @SuppressWarnings("unchecked")
    public AIPetSystem(Map map) {
        super(Aspect.getAspectForAll(
                AIPetComponent.class,
                Velocity.class,
                Transformation.class,
                HitBox.class,
                MultipleAnimations.class,
                Orientation.class
        ));
        mMap = map;
    }

    @Override
    protected void begin() {
        mPlayer = world.getManager(TagManager.class).getEntity("PLAYER");
        if (mPlayer != null) {
            playerPos = tm.getSafe(mPlayer).getTransformable().getPosition();
            playerBox = hm.getSafe(mPlayer).getHitBox();
        }
    }

    @Override
    protected void process(Entity entity) {
        AIPetComponent petCmpt = petm.get(entity);
        Velocity vel = vm.get(entity);
        Transformation t = tm.get(entity);
        Vector2f pos = t.getTransformable().getPosition();
        FloatRect hitbox = hm.get(entity).getHitBox();
        MultipleAnimations ma = mam.get(entity);
        Orientation orientation = om.get(entity);
                
        int currentState = petCmpt.getState();

        //System.out.println(currentState);

        switch (currentState) {
            case 0: // Compute path to player
                vel.setVelocity(Vector2f.ZERO);
                if (mPlayer != null) {
                    petCmpt.setOldPlayerPos(playerPos);
                    AIHelper.goTo(petCmpt, mMap, pos, hitbox, playerPos, 1);
                }
                break;


            case 1: // Move to next tile
                vel.setVelocity(AIHelper.followPath(petCmpt, pos, 2, 0, orientation));
                AnimationHelper.setAnimationByOrientation(ma, orientation);
                break;

            case 2: // Test if goal was reached
                AIHelper.testPathProgress(petCmpt, pos, 1);
               
                // if player moved a lot
                if (DistanceHelper.fastDistance(playerPos, petCmpt.getOldPlayerPos()) > 32) {
                    petCmpt.setState(0);
                }
                break;

        }

    }

}
