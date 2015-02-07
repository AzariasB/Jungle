
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
public class SplashScreenState extends AbstractApplicationState {

    public SplashScreenState(int id) {
        super(id);
    }

    @Override
    public void handleEvent(Event event) {
        // TODO
        if (event.type == Event.Type.KEY_PRESSED) {
            getApplication().goToState(Main.MAINMENUSTATE);
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
        target.clear(Color.BLACK);

    }

}
