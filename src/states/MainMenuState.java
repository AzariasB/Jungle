
package states;

import architecture.AbstractApplicationState;
import architecture.AppStateEnum;
import louveteau.Main;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.system.Time;
import org.jsfml.window.event.Event;

/**
 *
 */
public class MainMenuState extends AbstractApplicationState {

    @Override
    public AppStateEnum getStateId() {
        return Main.MyStates.MAINMENUSTATE;
    }
    

    @Override
    public void handleEvent(Event event) {

        if (event.type == Event.Type.KEY_PRESSED) {
            getAppContent().goToState(Main.MyStates.GAMESTATE);
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
