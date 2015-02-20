package systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import components.Expiration;

/**
 *
 */
public class ExpirationSystem extends EntityProcessingSystem {

    @Mapper
    ComponentMapper<Expiration> em;

    @SuppressWarnings("unchecked")
    public ExpirationSystem() {
        super(Aspect.getAspectForAll(Expiration.class));
    }

    @Override
    protected void process(Entity entity) {
        Expiration ex = em.get(entity);

        if (ex.decrementLife() == 0) {
            entity.deleteFromWorld();
        }

    }

}
