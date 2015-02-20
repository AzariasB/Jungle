package systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.managers.GroupManager;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.ImmutableBag;
import components.DamageMaker;
import components.Damageable;
import components.HitBox;
import components.Transformation;
import content.Groups;
import org.jsfml.graphics.FloatRect;
import systems.helpers.CollisionHelper;

/**
 *
 */
public class DamageSystem extends EntityProcessingSystem {

    @Mapper
    ComponentMapper<Transformation> tm;

    @Mapper
    ComponentMapper<HitBox> hm;

    @Mapper
    ComponentMapper<Damageable> dm;

    @Mapper
    ComponentMapper<DamageMaker> dmakm;

    private ImmutableBag<Entity> groupEntities;
    private FloatRect hitbox;

    @SuppressWarnings("unchecked")
    public DamageSystem() {
        super(Aspect.getAspectForAll(
                Transformation.class,
                HitBox.class,
                Damageable.class
        ));

    }

    @Override
    protected void begin() {
        groupEntities = world.getManager(GroupManager.class).getEntities(Groups.DAMAGEABLES);
    }

    @Override
    protected void process(Entity entity) {

        Damageable d = dm.get(entity);

        for (int i = 0; i < groupEntities.size(); i++) {
            Entity dmakEntity = groupEntities.get(i);

            DamageMaker dmak = dmakm.get(dmakEntity);

            int and = d.getMask() & dmak.getMask();

            if (and != 0) {
                Transformation t = tm.get(entity);
                HitBox h = hm.get(entity);
                if (CollisionHelper.isHitting(t, h,
                        tm.get(dmakEntity),
                        hm.get(dmakEntity))) {
                    entity.deleteFromWorld();
                }
            }
        }

    }

}
