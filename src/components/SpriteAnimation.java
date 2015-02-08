
package components;

import com.artemis.Component;

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
        mIndex = nbFrames + 1;
        mPlaying = true;
    }

    public SpriteAnimation(int nbFrames, long animDuration, boolean loop) {
        this(nbFrames, animDuration, loop, 0);
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

    public void incrementIndexFrame() {
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

}
