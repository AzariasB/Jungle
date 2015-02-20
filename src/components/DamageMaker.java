
package components;

import com.artemis.Component;

/**
 *
 */
public class DamageMaker extends Component {
    private final int mMask;

    public DamageMaker(int mask) {
        mMask = mask;
    }

    public int getMask() {
        return mMask;
    }

}
