
package components;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.Validate;
import org.jsfml.graphics.IntRect;

/**
 *
 */
public class MultipleAnimations extends AbstractTextureRect {

    /**
     * Special type to identify animations
     */
    public interface MultipleAnimationsEnum {}

    private AnimatedTextureRect mCurrentAnimation;
    private MultipleAnimationsEnum mCurrentId;
    private final Map<MultipleAnimationsEnum, AnimatedTextureRect> mAnimations;

    private static final AnimatedTextureRect sEmptyAnim = AnimatedTextureRect.createLinearAnimation(IntRect.EMPTY, 1, Long.MAX_VALUE);

    public MultipleAnimations() {
        mCurrentAnimation = sEmptyAnim;
        mCurrentId = null;
        mAnimations = new HashMap<>();
    }

    public void add(MultipleAnimationsEnum id, AnimatedTextureRect animation) {
        Validate.notNull(animation);
        mAnimations.put(id, animation);
    }

    public void setAnimation(MultipleAnimationsEnum id) {
        if (id != mCurrentId) {
            if (mAnimations.containsKey(id)) {
                mCurrentAnimation = mAnimations.get(id);
                mCurrentAnimation.restart();
                mCurrentId = id;
            }
        }
    }

    public AnimatedTextureRect getCurrentAnimation() {
        return mCurrentAnimation;
    }

    @Override
    public IntRect getRect() {
        return mCurrentAnimation.getRect();
    }

}
