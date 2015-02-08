package states;

import architecture.AbstractApplicationState;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import components.CollideWithMap;
import components.HitBox;
import components.Player;
import components.RenderableSprite;
import components.SpriteAnimation;
import components.Transformation;
import components.Velocity;
import map.Map;
import org.jsfml.audio.Music;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.system.Time;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.Event;
import sounds.MusicEngine;
import systems.AnimateSystem;
import systems.CollectSystem;
import systems.DebugRenderingSystem;
import systems.MovemementCollideMapSystem;
import systems.MovemementSystem;
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

        /*
         Environnement creation
         */
        myMap = new Map("map.txt", getGraphicEngine());

        /*
         World entities creation
         */
        world = new World();
        world.setSystem(mPlayerControlSystem = new PlayerControlSystem());
        world.setSystem(new AnimateSystem());
        world.setSystem(new MovemementSystem());
        world.setSystem(new MovemementCollideMapSystem(getApplication(), myMap));
        world.setSystem(mRenderingSystem = new RenderingSystem(getGraphicEngine()), true);
        world.setSystem(mDebugRenderingSystem = new DebugRenderingSystem(getGraphicEngine()), true);
        world.setSystem(new CollectSystem(getApplication()));
        world.initialize();
        
        Entity player = world.createEntity();
        player.addComponent(new Transformation(200, 400));
        player.addComponent(new Velocity());
        RenderableSprite playerRs = new RenderableSprite("joueur1.png");
        playerRs.setRect(new IntRect(0, 0, 32, 32));
        player.addComponent(playerRs);
        player.addComponent(new SpriteAnimation(4, 500, true));
        player.addComponent(new HitBox(new FloatRect(6, 16, 20, 16)));
        player.addComponent(new CollideWithMap());
        player.addComponent(new Player());
        player.addToWorld();

        Entity noixCoco = world.createEntity();
        noixCoco.addComponent(new Transformation(150, 150));
        noixCoco.addComponent(new RenderableSprite("coco.png"));
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

        target.clear(new Color(64, 64, 64));
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
