package components;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jsfml.graphics.IntRect;

/**
 *
 */
public class AnimatedTextureRect extends AbstractTextureRect {

    public static AnimatedTextureRect createLinearAnimation(IntRect startingFrame, int nbFrames, long animDuration, boolean loop) {
        final List<IntRect> frames = new ArrayList<>(nbFrames);
        IntRect currentFrame = startingFrame;
        for (int i = 0; i < nbFrames; ++i) {
            frames.add(currentFrame);
            currentFrame = new IntRect(currentFrame.left + startingFrame.width,
                    startingFrame.top,
                    startingFrame.width,
                    startingFrame.height);
        }
        AnimatedTextureRect animation = new AnimatedTextureRect(frames, animDuration / (long) nbFrames);
        animation.setLoop(loop);
        return animation;
    }

    public static AnimatedTextureRect createLinearAnimation(IntRect startingFrame, int nbFrames, long animDuration) {
        return createLinearAnimation(startingFrame, nbFrames, animDuration, false);
    }

    public static AnimatedTextureRect createSquareAnimation(IntRect startingFrame, int nbRowFrames, int nbColFrames, long animDuration) {
        final int nbFrames = nbRowFrames * nbColFrames;
        final List<IntRect> frames = new ArrayList<>(nbFrames);
        int currentLeft = startingFrame.left;
        int currentTop = startingFrame.top;
        for (int y = 0; y < nbColFrames; ++y) {
            for (int x = 0; x < nbRowFrames; ++x) {
                IntRect currentFrame = new IntRect(currentLeft,
                        currentTop,
                        startingFrame.width,
                        startingFrame.height);
                frames.add(currentFrame);
                currentLeft += startingFrame.width;
            }
            currentLeft = startingFrame.left;
            currentTop += startingFrame.height;
        }
        return new AnimatedTextureRect(frames, animDuration / (long) nbFrames);
    }

    /*
     *****************************************
     */

    private IntRect mCurrentRect;
    private boolean mPlaying;
    private boolean mLoop;
    private long mElapsedTime;
    private final long mFrameDuration;
    private final Iterable<IntRect> mFrames;
    private Iterator<IntRect> mFrameIterator;

    public AnimatedTextureRect(Iterable<IntRect> frames, long frameDuration) {
        mPlaying = true;
        mElapsedTime = 0;
        mFrameDuration = frameDuration;
        mFrames = frames;
        mLoop = false;
        mCurrentRect = IntRect.EMPTY;
        resetFrameIterator();
    }

    private void resetFrameIterator() {
        mFrameIterator = mFrames.iterator();
        if (hasNextFrame()) {
            selectNextFrame();
        }
    }

    public void restart() {
        resetFrameIterator();
        setPlaying(true);
    }

    @Override
    public IntRect getRect() {
        return mCurrentRect;
    }

    public boolean isPlaying() {
        return mPlaying;
    }

    public void setPlaying(boolean playing) {
        mPlaying = playing;
    }

    public boolean isLoop() {
        return mLoop;
    }

    public void setLoop(boolean loop) {
        mLoop = loop;
    }

    public void addElapsedTime(long elapsedTime) {
        mElapsedTime += elapsedTime;
    }

    public long getElapsedTime() {
        return mElapsedTime;
    }

    public long getFrameDuration() {
        return mFrameDuration;
    }

    public final boolean hasNextFrame() {
        return mFrameIterator.hasNext();
    }

    public final void selectNextFrame() {
        mCurrentRect = mFrameIterator.next();
    }

}
