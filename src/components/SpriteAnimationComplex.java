package components;

import org.jsfml.graphics.IntRect;

/**
 *
 */
public class SpriteAnimationComplex extends SpriteAnimation {

    private final int mStartY;
    private final int mNbRows;
    private int mIndexRow;

    /**
     *
     * @param nbFramesOnRow
     * @param nbRows
     * @param animDuration
     * @param loop
     * @param startX
     * @param startY
     */
    public SpriteAnimationComplex(int nbFramesOnRow,
            int nbRows,
            long animDuration,
            boolean loop,
            int startX,
            int startY) {
        super(nbFramesOnRow, animDuration / nbRows, loop);

        mNbRows = nbRows;
        mStartY = startY;
        
        mIndexRow = 0;
    }

    @Override
    public IntRect getNextFrame(IntRect currentFrame) {
        int left = currentFrame.left;
        int top = currentFrame.top;

        if (getIndexFrame() < getNbFrames()) {
            left += currentFrame.width;
        } else {
            left = getStartX();
            setIndexFrame(0);

            if (mIndexRow < mNbRows - 1) {
                top += currentFrame.height;
                mIndexRow++;
            } else if (isLoop()) {
                top = mStartY;
                mIndexRow = 0;
            } else {
                setPlaying(false);
            }
            
        }
        incrementIndexFrame();

        return new IntRect(left, top, currentFrame.width, currentFrame.height);
    }

}
