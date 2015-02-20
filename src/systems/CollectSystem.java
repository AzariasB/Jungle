package systems;

import architecture.AppContent;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.managers.GroupManager;
import com.artemis.systems.IntervalEntityProcessingSystem;
import com.artemis.utils.ImmutableBag;
import components.Collector;
import components.HitBox;
import components.Transformation;
import content.Groups;
import systems.helpers.CollisionHelper;

/**
 *
 */
public class CollectSystem extends IntervalEntityProcessingSystem {

    @Mapper
    ComponentMapper<Transformation> tm;

    @Mapper
    ComponentMapper<HitBox> hm;

    private final AppContent mAppContent;

    @SuppressWarnings("unchecked")
    public CollectSystem(AppContent application) {
        super(Aspect.getAspectForAll(
                Collector.class,
                Transformation.class,
                HitBox.class
        ), 0.01f);
        mAppContent = application;
    }

    @Override
    protected void process(Entity collector) {
        GroupManager gm = world.getManager(GroupManager.class);

        ImmutableBag<Entity> collectables = gm.getEntities(Groups.COLLECTABLES);

        for (int i = 0, s = collectables.size(); i < s; ++i) {
            Entity collectable = collectables.get(i);
            if (tm.has(collectable) && hm.has(collector)) {

                if (CollisionHelper.isHitting(tm.get(collector),
                        hm.get(collector),
                        tm.get(collectable),
                        hm.get(collectable))) {

                    collectable.deleteFromWorld();
                    mAppContent.getMusicEngine().getSound("coin.wav").play();
                }

            }
        }
    }


}
