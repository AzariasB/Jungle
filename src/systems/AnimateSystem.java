
package systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.IntervalEntityProcessingSystem;
import components.RenderableSprite;
import components.SpriteAnimation;
import components.SpriteAnimationComplex;
import org.jsfml.graphics.IntRect;
import org.jsfml.system.Clock;

/**
 *
 */
public class AnimateSystem extends IntervalEntityProcessingSystem {

    @Mapper
    ComponentMapper<RenderableSprite> rsm;

    @Mapper
    ComponentMapper<SpriteAnimation> sam;

    @Mapper
    ComponentMapper<SpriteAnimationComplex> sacm;

    private final Clock mClock;
    private long mElapsedTime;

    @SuppressWarnings("unchecked")
    public AnimateSystem() {
        super(Aspect.getAspectForAll(
                RenderableSprite.class
                
        ).one(SpriteAnimation.class, SpriteAnimationComplex.class), 1.f / 60.f);

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
        RenderableSprite rs = rsm.get(entity);
        SpriteAnimation ras;
        if (sam.has(entity))
            ras = sam.get(entity);
        else
            ras = sacm.get(entity);

        if (ras.isPlaying()) {
            ras.addElapsedTime(mElapsedTime);

            while (ras.getElapsedTime() > ras.getFrameDuration()) {
                ras.addElapsedTime(-ras.getFrameDuration());
                // move frame
                IntRect newRect = ras.getNextFrame(rs.getRect());
                rs.setRect(newRect);
            }
        }
    }

}
