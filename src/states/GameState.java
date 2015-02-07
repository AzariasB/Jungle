package states;

import architecture.AbstractApplicationState;
import com.artemis.Entity;
import com.artemis.World;
import components.Position;
import org.jsfml.audio.Music;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.system.Time;
import org.jsfml.window.event.Event;
import sounds.MusicEngine;
import systems.RenderingSystem;

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
    public void init() {
        world = new World();
        world.setSystem(mRenderingSystem = new RenderingSystem(getGraphicEngine()), true);
        world.initialize();
        

        Entity e = world.createEntity();
        e.addComponent(new Position(20, 40));
        e.addToWorld();
        e.changedInWorld();
        
    }


    @Override
    public void handleEvent(Event e) {
        // TODO
        if (e.type == Event.Type.KEY_PRESSED) {
            //getApplication().goToState(Main.MAINMENUSTATE);
            getApplication().exit();
        }
    }

    @Override
    public void update(Time time) {
        world.setDelta(time.asSeconds());
        world.process();
    }

    @Override
    public void render() {
        final RenderTarget target = getGraphicEngine().getRenderTarget();
        target.clear(Color.RED);
        // TODO : draw map
        mRenderingSystem.process();
        // TODO : draw HUD

    }

    private Music gameMusic;
    
    private World world;
    private RenderingSystem mRenderingSystem;

}
