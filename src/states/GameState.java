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
import entities.EntityFactory;
import java.util.List;
import map.Loader;
import map.Map;
import org.jsfml.audio.Music;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;
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
        MusicEngine mesMusiques = getAppContent().getMusicEngine();
        gameMusic = mesMusiques.getMusic("happy.ogg");
        gameMusic.play();
    }

    @Override
    public void notifyExiting() {
        gameMusic.pause();
    }

    @Override
    public void initialize() {
        getAppContent().getOptions().setIfUnset("map.filepath", "./assets/Maps/maps.tmx");

        /*
        New Loading system : with loader class
        */
        Loader ld = new Loader("maps.tmx", getGraphicEngine());         
        myMap = ld.getMap();

        /*
         World entities creation
         */
        world = new World();
        world.setSystem(mPlayerControlSystem = new PlayerControlSystem());
        world.setSystem(new AnimateSystem());
        world.setSystem(new MovemementSystem());
        world.setSystem(new MovemementCollideMapSystem(getAppContent(), myMap));
        world.setSystem(mRenderingSystem = new RenderingSystem(getGraphicEngine()), true);
        world.setSystem(mDebugRenderingSystem = new DebugRenderingSystem(getGraphicEngine()), true);
        world.setSystem(new CollectSystem(getAppContent()));
        world.initialize();
        
        Entity player = world.createEntity();
        player.addComponent(new Transformation(myMap.getSpawnPoint().x,myMap.getSpawnPoint().y));
        player.addComponent(new Velocity());
        RenderableSprite playerRs = new RenderableSprite("joueur1.png");
        playerRs.setRect(new IntRect(0, 0, 32, 32));
        player.addComponent(playerRs);
        player.addComponent(new SpriteAnimation(3, 500, true));
        player.addComponent(new HitBox(new FloatRect(6, 16, 20, 16)));
        player.addComponent(new CollideWithMap());
        player.addComponent(new Player());
        player.addToWorld();

        EntityFactory.createNoixCoco(world, 150, 350);
        
        List<Vector2f> coins = myMap.getCoins();
        for(int coin = 0; coin < coins.size();coin++){
            EntityFactory.createCoin(world,(int)coins.get(coin).x , (int)coins.get(coin).y);
        }


        GroupManager gm;
        world.setManager(gm = new GroupManager());
        gm.add(player, "COLLECTORS");

        TagManager tm;
        world.setManager(tm = new TagManager());
        tm.register("PLAYER", player);

        world.initialize(); 
    }


    @Override
    public void handleEvent(Event e) {
        // TODO
        if (e.type == Event.Type.KEY_PRESSED) {

            switch (e.asKeyEvent().key) {
                case ESCAPE:
                    getAppContent().exit();
                    break;
                case R:
                    initialize();
                    break;
            }
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
        myMap.renderFg(getGraphicEngine());
        // TODO : draw HUD && text if any

        mDebugRenderingSystem.process();
        
    }

    private Music gameMusic;

    private World world;
    private RenderingSystem mRenderingSystem;
    private DebugRenderingSystem mDebugRenderingSystem;
    private Map myMap;

    


}
