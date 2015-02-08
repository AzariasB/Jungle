package states;

import architecture.AbstractApplicationState;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import components.HitBox;
import components.Player;
import components.RenderableSprite;
import components.Transformation;
import map.Map;
import org.jsfml.audio.Music;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.system.Time;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.Event;
import sounds.MusicEngine;
import systems.CollectSystem;
import systems.DebugRenderingSystem;
import systems.PlayerControlSystem;
import systems.RenderingSystem;

/**
 *
 */
public class GameState extends AbstractApplicationState {

    private PlayerControlSystem mPlayerControlSystem;

    public GameState(int id) {
        super(id);
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
        world.setSystem(mDebugRenderingSystem = new DebugRenderingSystem(getGraphicEngine()), true);
        world.setSystem(new CollectSystem(getApplication()));
        world.initialize();
        myMap = new Map("map.txt", getGraphicEngine());

        Entity player = world.createEntity();
        player.addComponent(new Transformation(20, 40));
        player.addComponent(new RenderableSprite(1));
        player.addComponent(new HitBox(new FloatRect(0, 0, 111, 111)));
        player.addComponent(new Player());
        player.addToWorld();

        Entity noixCoco = world.createEntity();
        noixCoco.addComponent(new Transformation(150, 150));
        noixCoco.addComponent(new RenderableSprite(2));
        noixCoco.addToWorld();

        GroupManager gm;
        world.setManager(gm = new GroupManager());
        gm.add(player, "COLLECTORS");
        gm.add(noixCoco, "COLLECTABLES");


        TagManager tm;
        world.setManager(tm = new TagManager());
        tm.register("PLAYER", player);

        world.initialize();
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
        final RenderTarget target = getGraphicEngine().getRenderTarget();

        target.clear(Color.RED);
        // Drawing map
        myMap.render(getGraphicEngine());

        mRenderingSystem.process();
        // TODO : draw HUD

        mDebugRenderingSystem.process();
    }

    private Music gameMusic;

    private World world;
    private RenderingSystem mRenderingSystem;
    private DebugRenderingSystem mDebugRenderingSystem;
    private Map myMap;

    


}
