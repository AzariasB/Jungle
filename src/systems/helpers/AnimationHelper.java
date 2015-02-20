package systems.helpers;

import components.MultipleAnimations;
import components.Orientation;
import content.Animations;

/**
 *
 */
public class AnimationHelper {

    public static void setAnimationByOrientation(MultipleAnimations ma, Orientation orientation) {
        switch (orientation.getDirection()) {
            case RIGHT:
                ma.setAnimation(Animations.GO_RIGHT);
                break;
            case LEFT:
                ma.setAnimation(Animations.GO_LEFT);
                break;
            case DOWN:
                ma.setAnimation(Animations.GO_DOWN);
                break;
            case UP:
                ma.setAnimation(Animations.GO_UP);
                break;
        }
    }

}
