
package entities;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import components.AIMonsterComponent;
import components.AIPetComponent;
import components.Collector;
import components.CollideWithMap;
import components.HitBox;
import components.Player;
import components.RenderableSprite;
import components.SpriteAnimation;
import components.SpriteAnimationComplex;
import components.Transformation;
import components.Velocity;
import java.util.ArrayList;
import java.util.List;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.IntRect;
import org.jsfml.system.Vector2f;

/**
 *
 */
public class EntityFactory {

    public static Entity createPlayer(World world, float x, float y) {
        Entity player = world.createEntity();
        player.addComponent(new Transformation(x, y));
        player.addComponent(new Velocity());
        RenderableSprite playerRs = new RenderableSprite("joueur1.png");
        playerRs.setRect(new IntRect(0, 0, 32, 32));
        player.addComponent(playerRs);
        player.addComponent(new SpriteAnimation(3, 500, true));
        player.addComponent(new HitBox(new FloatRect(6, 16, 20, 16)));
        player.addComponent(new CollideWithMap());
        player.addComponent(new Player());
        player.addComponent(new Collector());
        player.addToWorld();
        world.getManager(TagManager.class).register("PLAYER", player);
        return player;
    }

    public static Entity createNoixCoco(World world, float x, float y) {
        Entity noixCoco = world.createEntity();
        noixCoco.addComponent(new Transformation(x, y));
        noixCoco.addComponent(new RenderableSprite("coco.png"));
        noixCoco.addComponent(new HitBox(new FloatRect(0, 0, 16, 16)));
        noixCoco.addToWorld();
        world.getManager(GroupManager.class).add(noixCoco, "COLLECTABLE");
        return noixCoco;
    }


    public static Entity createCoin(World world, int x, int y) {
        Entity coin = world.createEntity();
        Transformation t = new Transformation(x, y);
        t.getTransformable().scale(.5f, .5f);
        coin.addComponent(t);
        coin.addComponent(new RenderableSprite("coin.png", new IntRect(0, 0, 64, 64)));
        coin.addComponent(new SpriteAnimationComplex(8, 8, 1000, true, 0, 0));
        coin.addComponent(new HitBox(new FloatRect(0, 0, 32, 32)));
        coin.addToWorld();
        world.getManager(GroupManager.class).add(coin, "COLLECTABLE");
        return coin;
    }

    public static void createPet(World world, float x, float y) {
        Entity pet = world.createEntity();
        pet.addComponent(new Transformation(x, y));
        pet.addComponent(new Velocity());
        pet.addComponent(new RenderableSprite("pet.png", new IntRect(0, 0, 24, 21)));
        pet.addComponent(new SpriteAnimation(4, 500, true));
        pet.addComponent(new HitBox(new FloatRect(0, 5, 24, 16)));
        pet.addComponent(new AIPetComponent());
        pet.addToWorld();
    }

    public static void createMonster(World world, float x, float y) {
        Entity monster = world.createEntity();
        monster.addComponent(new Transformation(x, y));
        monster.addComponent(new Velocity());
        monster.addComponent(new RenderableSprite("monster.png", new IntRect(0, 0, 24, 36)));
        monster.addComponent(new SpriteAnimation(4, 500, true));
        monster.addComponent(new HitBox(new FloatRect(0, 5, 24, 31)));
        //pet.addComponent(new CollideWithMap());
        List<Vector2f> path = new ArrayList<>();
        path.add(new Vector2f(x, y));
        path.add(new Vector2f(x + 160, y));
        path.add(new Vector2f(x + 161, y + 32));
        monster.addComponent(new AIMonsterComponent(path));
        monster.addToWorld();
    }

}
