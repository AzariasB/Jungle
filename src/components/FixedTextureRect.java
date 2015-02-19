
package components;

import org.jsfml.graphics.IntRect;

/**
 *
 */
public class FixedTextureRect extends AbstractTextureRect {
    private IntRect mRect;

    public FixedTextureRect(IntRect rect) {
        mRect = rect;
    }

    public FixedTextureRect() {
        this(IntRect.EMPTY);
    }

    @Override
    public IntRect getRect() {
        return mRect;
    }

}
