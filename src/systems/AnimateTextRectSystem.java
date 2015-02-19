
package systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.IntervalEntityProcessingSystem;
import components.AnimatedTextureRect;
import org.jsfml.system.Clock;

/**
 *
 */
public class AnimateTextRectSystem extends IntervalEntityProcessingSystem {

    @Mapper
    ComponentMapper<AnimatedTextureRect> atrm;

    private final Clock mClock;
    private long mElapsedTime;

    @SuppressWarnings("unchecked")
    public AnimateTextRectSystem() {
        super(Aspect.getAspectForAll(AnimatedTextureRect.class
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
        AnimatedTextureRect atr = atrm.get(entity);

        updateAnimation(atr, mElapsedTime);
    }

    public static void updateAnimation(AnimatedTextureRect atr, long elapsedTime) {
        if (atr.isPlaying()) {
            atr.addElapsedTime(elapsedTime);

            while (atr.getElapsedTime() > atr.getFrameDuration()) {
                atr.addElapsedTime(-atr.getFrameDuration());

                if (atr.hasNextFrame()) {
                    atr.selectNextFrame();
                } else if (atr.isLoop()) {
                    atr.restart();
                } else {
                    atr.setPlaying(false);
                }
            }
        }
    }

}
