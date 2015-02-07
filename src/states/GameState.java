package states;

import architecture.AbstractApplicationState;
import louveteau.Main;
import org.jsfml.audio.Music;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.system.Time;
import org.jsfml.window.event.Event;
import sounds.MusicEngine;

/**
 *
 */
public class GameState extends AbstractApplicationState {

    public GameState(int id) {
        super(id);
        MusicEngine mesMusiques = new MusicEngine();
        gameMusic = mesMusiques.getMusic("happy.ogg");
    }

    @Override
    public void notifyEntering() {
        gameMusic.play();
    }

    @Override
    public void notifyExiting() {
        gameMusic.pause();
    }

    @Override
    public void handleEvent(Event e) {
        // TODO
        if (e.type == Event.Type.KEY_PRESSED) {
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
        target.clear(Color.RED);
    }

    private Music gameMusic;
    
}
