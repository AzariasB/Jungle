package states;

import architecture.AbstractApplicationState;
import architecture.AppStateEnum;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import com.artemis.managers.TeamManager;
import com.sun.org.apache.xalan.internal.xsltc.cmdline.Transform;
import components.Transformation;
import entities.EntityFactory;
import graphics.Camera;
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
import systems.AIMonsterSystem;
import systems.AIPetSystem;
import systems.AnimateTextRectSystem;
import systems.CollectSystem;
import systems.DebugRenderingSystem;
import systems.MovemementCollideMapSystem;
import systems.MovemementSystem;
import systems.MultipleAnimationSystem;
import systems.PlayerControlSystem;
import systems.RenderSpriteSystem;

/**
 *
 */
public class GameState extends AbstractApplicationState {

    private boolean mDebugGraphics = true;

    private Music gameMusic;
    private Map myMap;

    private World world;
    private PlayerControlSystem mPlayerControlSystem;
    private RenderSpriteSystem mRenderingSystem;
    private DebugRenderingSystem mDebugRenderingSystem;
    private Entity mEntityPlayer;

    @Override
    public AppStateEnum getStateId() {
        return Main.MyStates.GAMESTATE;
    }

    @Override
    public void notifyEntering() {
        MusicEngine mesMusiques = getAppContent().getMusicEngine();
        gameMusic = mesMusiques.getMusic("happy.ogg");
        //gameMusic.play();
    }

    @Override
    public void notifyExiting() {
        gameMusic.pause();
    }

    @Override
    public void initialize() {
        getAppContent().getOptions().setIfUnset("maps.filepath", "./assets/Maps/maps.tmx");

        /*
         New Loading system : with loader class
         */
        Loader ld = new Loader(getAppContent().getOptions().get("maps.filepath"), getGraphicEngine());
        myMap = ld.getMap();

        /*
         World entities creation
         */
        world = new World();
        world.setManager(new GroupManager());
        world.setManager(new TagManager());
        world.setManager(new TeamManager());

        world.setSystem(mPlayerControlSystem = new PlayerControlSystem());
        world.setSystem(new MovemementSystem());
        world.setSystem(new MovemementCollideMapSystem(getAppContent(), myMap));
        world.setSystem(mDebugRenderingSystem = new DebugRenderingSystem(getGraphicEngine()), true);
        world.setSystem(new CollectSystem(getAppContent()));
        world.setSystem(new AIPetSystem(myMap));
        world.setSystem(new AIMonsterSystem(myMap));
        world.setSystem(mRenderingSystem = new RenderSpriteSystem(getGraphicEngine()), true);
        world.setSystem(new AnimateTextRectSystem());
        world.setSystem(new MultipleAnimationSystem());

        mEntityPlayer = EntityFactory.createPlayer(getAppContent(), world, myMap.getSpawnPoint().x, myMap.getSpawnPoint().y);

        /* Collectables */
        EntityFactory.createNoixCoco(getAppContent(), world, 150, 350);
        EntityFactory.createCoin(getAppContent(), world, 300, 400);
        EntityFactory.createCoin(getAppContent(), world, 350, 400);
        EntityFactory.createCoin(getAppContent(), world, 250, 400);
        List<Vector2f> coins = myMap.getCoins();
        for (Vector2f coin : coins) {
            EntityFactory.createCoin(getAppContent(), world, (int) coin.x, (int) coin.y);
        }

//        EntityFactory.createPet(world, 50, 50);
        EntityFactory.createPet(getAppContent(), world, 550, 550);
//        EntityFactory.createPet(world, 550, 50);
//        EntityFactory.createPet(world, 50, 550);

        EntityFactory.createMonster(getAppContent(), world, 288 + 288, 96);

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
                case R: // reset
                    initialize();
                    break;
                case D: // toggle graphic debug
                    mDebugGraphics = !mDebugGraphics;
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

        Camera cam = getGraphicEngine().getCamera();
        cam.setTarget(mEntityPlayer.getComponent(Transformation.class).getTransformable().getPosition());
        
       // Vector2f camPos = new Vector2f(cam.getTopLeft().x/16 + 1,cam.getTopLeft().y/16 + 1);
       // System.out.println(cam.getView().getSize().x);
        myMap.render(getGraphicEngine(), cam.getTopLeft() , (int)(cam.getView().getSize().x*1.5), (int)(cam.getView().getSize().y*1.5 ));

        mRenderingSystem.process();
        myMap.renderFg(getGraphicEngine(), cam.getTopLeft(),(int)(cam.getView().getSize().x*1.5) , (int)(cam.getView().getSize().y*1.5) ) ;

        // TODO : draw HUD && text if any
        if (mDebugGraphics) {
            mDebugRenderingSystem.process();
        }

    }

}
