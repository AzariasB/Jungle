
package states;

import architecture.AbstractApplicationState;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.system.Time;
import org.jsfml.window.event.Event;

/**
 *
 */
public class GameState extends AbstractApplicationState {

    public GameState(int id) {
        super(id);
    }

    @Override
    public void handleEvent(Event e) {
        // TODO
        if (e.type == Event.Type.KEY_PRESSED) {
            getApplication().exit();
        }
    }

    @Override
    public void update(Time time) {
        // TODO
    }

    @Override
    public void render() {
        // TODO
        final RenderTarget target = getGraphicEngine().getRenderTarget();
        target.clear(Color.RED);
    }

}
