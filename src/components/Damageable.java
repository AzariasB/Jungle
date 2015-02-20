
package components;

import com.artemis.Component;

/**
 *
 */
public class Damageable extends Component {

    private final int mask;

    public Damageable(int mask) {
        this.mask = mask;
    }

    public int getMask() {
        return mask;
    }

}
