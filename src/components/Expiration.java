
package components;

import com.artemis.Component;
import org.apache.commons.lang3.Validate;

/**
 *
 */
public class Expiration extends Component {

    private int mLife;

    public Expiration() {
        mLife = 1;
    }

    public Expiration(int life) {
        Validate.isTrue(life > 0);
        mLife = life;
    }

    public int getLife() {
        return mLife;
    }

    /**
     * Decrement the life of one unit.
     *
     * @return The new life value.
     */
    public int decrementLife() {
        return --mLife;
    }

}
