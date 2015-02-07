package states;

import architecture.AbstractApplicationState;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import components.Player;
import components.Transformation;
import components.RenderableSprite;
import map.Map;
import org.jsfml.audio.Music;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.system.Time;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.Event;
import sounds.MusicEngine;
import systems.CollectSystem;
import systems.PlayerControlSystem;
import systems.RenderingSystem;

/**
 *
 */
public class GameState extends AbstractApplicationState {
    private PlayerControlSystem mPlayerControlSystem;

    public GameState(int id) {
        super(id);
        myMap = new Map("map.txt");
    }


    @Override
    public void notifyEntering() {
        MusicEngine mesMusiques = getApplication().getMusicEngine();
        gameMusic = mesMusiques.getMusic("happy.ogg");
        //gameMusic.play();
    }

    @Override
    public void notifyExiting() {
        gameMusic.pause();
    }

    @Override
    public void init() {
        world = new World();
        world.setSystem(mPlayerControlSystem = new PlayerControlSystem());
        world.setSystem(mRenderingSystem = new RenderingSystem(getGraphicEngine()), true);
        world.setSystem(new CollectSystem(getApplication()));
        world.initialize();
        

        Entity e = world.createEntity();
        e.addComponent(new Transformation(20, 40));
        e.addComponent(new RenderableSprite(1));
        e.addComponent(new Player());
        e.addToWorld();

        Entity noixCoco = world.createEntity();
        noixCoco.addComponent(new Transformation(150, 150));
        noixCoco.addComponent(new RenderableSprite(2));
        noixCoco.addToWorld();

        GroupManager gm;
        world.setManager(gm = new GroupManager());
        gm.add(e, "COLLECTORS");
        gm.add(noixCoco, "COLLECTABLES");
        
    }


    @Override
    public void handleEvent(Event e) {
        // TODO
        if (e.type == Event.Type.KEY_PRESSED
                && e.asKeyEvent().key == Keyboard.Key.ESCAPE) {
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
        myMap.render(getGraphicEngine());
        final RenderTarget target = getGraphicEngine().getRenderTarget();
        target.clear(Color.RED);
        // TODO : draw map
        mRenderingSystem.process();
        // TODO : draw HUD

    }

    private Music gameMusic;
    
    private World world;
    private RenderingSystem mRenderingSystem;
    private Map myMap;

}
