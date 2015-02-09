package systems;

import architecture.AppContent;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.managers.GroupManager;
import com.artemis.systems.IntervalEntityProcessingSystem;
import com.artemis.utils.ImmutableBag;
import components.Collectable;
import components.HitBox;
import components.Transformation;
import org.jsfml.graphics.FloatRect;
import org.jsfml.system.Vector2f;

/**
 *
 */
public class CollectSystem extends IntervalEntityProcessingSystem {

    @Mapper
    ComponentMapper<Transformation> pm;

    @Mapper
    ComponentMapper<HitBox> hm;

    private final AppContent mAppContent;

    @SuppressWarnings("unchecked")
    public CollectSystem(AppContent application) {
        super(Aspect.getAspectForAll(
                Collectable.class,
                Transformation.class,
                HitBox.class
        ), 0.01f);
        mAppContent = application;
    }

    @Override
    protected void process(Entity entity) {
        GroupManager gm = world.getManager(GroupManager.class);

        ImmutableBag<Entity> collectors = gm.getEntities("COLLECTORS");

        for (int i = 0, s = collectors.size(); i < s; ++i) {
            Entity collector = collectors.get(i);
            if (pm.has(collector) && hm.has(entity)) {

                Vector2f collectorPos = pm.get(collector).getTransformable().getPosition();
                FloatRect orHitbox = hm.get(collector).moveCopy(collectorPos);
                
                Entity collectable = entity;

                Vector2f collectablePos = pm.get(collectable).getTransformable().getPosition();
                FloatRect ableHitbox = hm.get(collectable).moveCopy(collectablePos);
                
                if (orHitbox.intersection(ableHitbox) != null) {
                    collectable.deleteFromWorld();
                    mAppContent.getMusicEngine().getSound("coin.wav").play();
                }

            }
        }
    }

}
