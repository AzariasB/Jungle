
package states;

import architecture.AbstractApplicationState;
import louveteau.Main;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.system.Time;
import org.jsfml.window.event.Event;

/**
 *
 */
public class MainMenuState extends AbstractApplicationState {

    public MainMenuState(int id) {
        super(id);
    }

    @Override
    public void handleEvent(Event event) {

        if (event.type == Event.Type.KEY_PRESSED) {
            getAppContent().goToState(Main.GAMESTATE);
        }
    }

    @Override
    public void update(Time time) {
        // TODO
    }

    @Override
    public void render() {
        final RenderTarget target = getGraphicEngine().getRenderTarget();
        target.clear(Color.BLUE);
    }

}
