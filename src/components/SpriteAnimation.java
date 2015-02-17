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

    /**
     *
     * @param nbFrames
     * @param animDuration Animation duration in milliseconds
     * @param loop
     * @param startX First frame position into the texture
     */
    public SpriteAnimation(int nbFrames, long animDuration, boolean loop, int startX) {
        mNbFrames = nbFrames;
        mAnimDuration = animDuration;
        mFrameDuration = animDuration / nbFrames;
        mLoop = loop;
        mStartX = startX;
        mElapsedTime = 0;
        mIndex = 1;
        mPlaying = true;
    }

    public SpriteAnimation(int nbFrames, long animDuration, boolean loop) {
        this(nbFrames, animDuration, loop, 0);
    }

    public int getNbFrames() {
        return mNbFrames;
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

    public boolean isPlaying() {
        return mPlaying;
    }

    public void setPlaying(boolean val) {
        mPlaying = val;
    }

    protected int getStartX() {
        return mStartX;
    }

    protected void incrementIndexFrame() {
        mIndex++;
    }

    protected int getIndexFrame() {
        return mIndex;
    }

    protected void setIndexFrame(int index) {
        mIndex = index;
    }



    public IntRect getNextFrame(IntRect currentFrame) {
        int left = currentFrame.left;

        if (getIndexFrame() < getNbFrames()) {
            left += currentFrame.width;
        } else if (isLoop()) {
            left = getStartX();
            setIndexFrame(0);
        } else {
            setPlaying(false);
        }
        incrementIndexFrame();

        return new IntRect(left, currentFrame.top, currentFrame.width, currentFrame.height);
    }

}
