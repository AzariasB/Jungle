
package systems;

import architecture.AppContent;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.managers.GroupManager;
import com.artemis.systems.VoidEntitySystem;
import com.artemis.utils.ImmutableBag;
import components.Transformation;
import org.jsfml.system.Vector2f;

/**
 *
 */
public class CollectSystem extends VoidEntitySystem {

    @Mapper
    ComponentMapper<Transformation> pm;

    private final AppContent mApplication;

    public CollectSystem(AppContent application) {
        super();
        mApplication = application;
    }

    @Override
    protected void processSystem() {
        GroupManager gm = world.getManager(GroupManager.class);
        

        ImmutableBag<Entity> collectables = gm.getEntities("COLLECTABLES");
        ImmutableBag<Entity> collectors = gm.getEntities("COLLECTORS");

        for (int i = 0, s = collectors.size(); i < s; ++i) {
            Entity collector = collectors.get(i);
            if (pm.has(collector)) {
                Vector2f collectorPos = pm.get(collector).getTransformable().getPosition();

                for (int j = 0, t = collectables.size(); j < t; ++j) {
                    Entity collectable = collectables.get(j);
                    if (pm.has(collectable)) {

                        Vector2f collectablePos = pm.get(collectable).getTransformable().getPosition();

                        Vector2f dv = Vector2f.sub(collectorPos, collectablePos);

                        if (dv.x * dv.x + dv.y * dv.y < 500) {
                            collectable.deleteFromWorld();
                            mApplication.getMusicEngine().getMusic("happy.ogg").play();
                        }
                    }
                }

            }
        }
    }

}
