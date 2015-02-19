
package components;

import com.artemis.Component;
import org.jsfml.graphics.ConstTexture;

/**
 *
 */
public class TextureComponent extends Component {

    private final ConstTexture texture;

    public TextureComponent(ConstTexture texture) {
        this.texture = texture;
    }

    public ConstTexture getTexture() {
        return texture;
    }

}
