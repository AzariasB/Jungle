
package systems;

import architecture.Application;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.managers.GroupManager;
import com.artemis.systems.VoidEntitySystem;
import com.artemis.utils.ImmutableBag;
import components.Position;
import org.jsfml.system.Vector2f;
import sounds.MusicEngine;

/**
 *
 */
public class CollectSystem extends VoidEntitySystem {

    @Mapper
    ComponentMapper<Position> pm;
    private final Application mApplication;

    public CollectSystem(Application application) {
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
                Vector2f collectorPos = pm.get(collector).getPosition();

                for (int j = 0, t = collectables.size(); j < t; ++j) {
                    Entity collectable = collectables.get(j);
                    if (pm.has(collectable)) {

                        Vector2f collectablePos = pm.get(collectable).getPosition();

                        Vector2f dv = Vector2f.sub(collectorPos, collectablePos);

                        if (dv.x * dv.x + dv.y * dv.y < 500) {
                            collectable.deleteFromWorld();
                            //mApplication.getMusicEngine().get
                            MusicEngine mesMusiques = new MusicEngine();
                            mesMusiques.getMusic("happy.ogg").play();

                        }
                    }
                }
            }
        }
    }

}
