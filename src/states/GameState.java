package states;

import architecture.AbstractApplicationState;
import architecture.AppStateEnum;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import com.artemis.managers.TeamManager;
import entities.EntityFactory;
import java.util.List;
import louveteau.Main;
import map.Loader;
import map.Map;
import org.jsfml.audio.Music;
import org.jsfml.graphics.Color;
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

    @Override
    public AppStateEnum getStateId() {
        return Main.MyStates.GAMESTATE;
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
        Loader ld = new Loader(getAppContent().getOptions().get("map.filepath"), getGraphicEngine());
        myMap = ld.getMap();

        /*
         World entities creation
         */
        world = new World();
        GroupManager gm = new GroupManager();
        world.setManager(gm);
        TagManager tm = new TagManager();
        world.setManager(tm);
        TeamManager tem = new TeamManager();
        world.setManager(tem);
        world.setSystem(mPlayerControlSystem = new PlayerControlSystem());
        world.setSystem(new AnimateSystem());
        world.setSystem(new MovemementSystem());
        world.setSystem(new MovemementCollideMapSystem(getAppContent(), myMap));
        world.setSystem(mRenderingSystem = new RenderingSystem(getGraphicEngine()), true);
        world.setSystem(mDebugRenderingSystem = new DebugRenderingSystem(getGraphicEngine()), true);
        world.setSystem(new CollectSystem(getAppContent()));

        EntityFactory.createPlayer(world, myMap.getSpawnPoint().x, myMap.getSpawnPoint().y);

        EntityFactory.createNoixCoco(world, 150, 350);
        EntityFactory.createCoin(world, 300, 400);
        EntityFactory.createCoin(world, 350, 400);
        EntityFactory.createCoin(world, 250, 400);

        List<Vector2f> coins = myMap.getCoins();
        for(int coin = 0; coin < coins.size();coin++){
            EntityFactory.createCoin(world,(int)coins.get(coin).x , (int)coins.get(coin).y);
        }

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
                case UP:
                    mPlayerControlSystem.goUp();
                    break;
                case LEFT:
                    mPlayerControlSystem.goLeft();
                    break;
                case DOWN:
                    mPlayerControlSystem.goDown();
                    break;
                case RIGHT:
                    mPlayerControlSystem.goRight();
                    break;
            }
        } else if (e.type == Event.Type.KEY_RELEASED) {
            switch (e.asKeyEvent().key) {
                case UP:
                    mPlayerControlSystem.stopUp();
                    break;
                case LEFT:
                    mPlayerControlSystem.stopLeft();
                    break;
                case DOWN:
                    mPlayerControlSystem.stopDown();
                    break;
                case RIGHT:
                    mPlayerControlSystem.stopRight();
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
