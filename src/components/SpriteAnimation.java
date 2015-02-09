package components;

import com.artemis.Component;
import org.jsfml.graphics.IntRect;

/**
 *
 */
public class SpriteAnimation extends Component {

    private int mStartX;
    private int mNbFrames;
    private long mAnimDuration;
    private long mFrameDuration;
    private boolean mLoop;
    private long mElapsedTime;
    private int mIndex;
    private boolean mPlaying;
    private int mStartY;
    private final int mNbFrameRows;

    /**
     *
     * @param nbFrames
     * @param animDuration Animation duration in milliseconds
     * @param loop
     * @param startX First frame position into the texture
     * @param nbFrameRows
     * @param startY
     */
    public SpriteAnimation(int nbFrames, long animDuration, boolean loop, int startX,
            int startY, int nbFrameRows) {
        mNbFrames = nbFrames;
        mAnimDuration = animDuration;
        mFrameDuration = animDuration / nbFrames;
        mLoop = loop;
        mStartX = startX;
        mElapsedTime = 0;
        mIndex = nbFrames + 1;
        mPlaying = true;
        mStartY = startY;
        mNbFrameRows = nbFrameRows;
    }

    public SpriteAnimation(int nbFrames, long animDuration, boolean loop) {
        this(nbFrames, animDuration, loop, 0, 0, 1);
    }

    public int getStartX() {
        return mStartX;
    }

    public void setStartX(int startX) {
        this.mStartX = startX;
    }

    public int getNbFrames() {
        return mNbFrames;
    }

    public void setNbFrames(int nbFrames) {
        this.mNbFrames = nbFrames;
    }

    public long getAnimationDuration() {
        return mAnimDuration;
    }

    public void setAnimDuration(long duration) {
        mAnimDuration = duration;
        mFrameDuration = duration / mNbFrames;
    }

    public long getFrameDuration() {
        return mFrameDuration;
    }

    public boolean isLoop() {
        return mLoop;
    }

    public void setLoop(boolean loop) {
        this.mLoop = loop;
    }

    public long getElapsedTime() {
        return mElapsedTime;
    }

    public void addElapsedTime(long dt) {
        mElapsedTime += dt;
    }

    public void resetElapsedTime() {
        mElapsedTime = 0;
    }

    protected void incrementIndexFrame() {
        mIndex++;
    }

    public int getIndexFrame() {
        return mIndex;
    }

    public void setIndexFrame(int index) {
        mIndex = index;
    }

    public boolean isPlaying() {
        return mPlaying;
    }

    public void setPlaying(boolean val) {
        mPlaying = val;
    }

    public void setStartY(int val) {
        mStartY = val;
    }

    public IntRect getNextFrameMovement(IntRect currentFrame) {
        int left = currentFrame.left;
        int top = currentFrame.top;

        if (getIndexFrame() < getNbFrames() - 1) {
            left += currentFrame.width;
        } else if (isLoop()) {
            left = getStartX();
            setIndexFrame(0);
        } else {
            setPlaying(false);
        }
        incrementIndexFrame();

        return new IntRect(left, top, currentFrame.width, currentFrame.height);
    }

}
