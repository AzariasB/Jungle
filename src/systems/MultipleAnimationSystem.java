package systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.IntervalEntityProcessingSystem;
import components.AnimatedTextureRect;
import components.MultipleAnimations;
import org.jsfml.system.Clock;

/**
 *
 */
public class MultipleAnimationSystem extends IntervalEntityProcessingSystem {

    @Mapper
    ComponentMapper<MultipleAnimations> mam;

    private final Clock mClock;
    private long mElapsedTime;

    @SuppressWarnings("unchecked")
    public MultipleAnimationSystem() {
        super(Aspect.getAspectForAll(
                MultipleAnimations.class
        ), 1.f / 60.f);

        mClock = new Clock();
    }

    @Override
    protected void initialize() {
        mClock.restart();
    }

    @Override
    protected void begin() {
        mElapsedTime = mClock.restart().asMilliseconds();
    }

    @Override
    protected void process(Entity entity) {
        MultipleAnimations ma = mam.get(entity);

        AnimatedTextureRect animation = ma.getCurrentAnimation();
        AnimateTextRectSystem.updateAnimation(animation, mElapsedTime);
    }

}
