
package entities;

import architecture.AppContent;
import com.artemis.ComponentType;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import components.AIMonsterComponent;
import components.AIPetComponent;
import components.AbstractTextureRect;
import components.AnimatedTextureRect;
import components.Collector;
import components.CollideWithMap;
import components.DebugName;
import components.FixedTextureRect;
import components.HitBox;
import components.MultipleAnimations;
import components.Orientation;
import static components.Orientation.Direction.DOWN;
import components.Player;
import components.SpriteAnimation;
import components.TextureComponent;
import components.Transformation;
import components.Velocity;
import content.Animations;
import java.util.ArrayList;
import java.util.List;
import org.jsfml.graphics.ConstTexture;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.IntRect;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

/**
 *
 */
public class EntityFactory {

    public static Entity createPlayer(AppContent appContent, World world, float x, float y) {
        Entity player = world.createEntity();
        player.addComponent(new DebugName("Player"));
        player.addComponent(new Transformation(x, y));
        player.addComponent(new Velocity());
        
        player.addComponent(new TextureComponent(getTexture(appContent, "joueur1.png")));

        MultipleAnimations ma = new MultipleAnimations();
        ma.add(Animations.GO_LEFT, AnimatedTextureRect.createLinearAnimation(new IntRect(0, 32, 32, 32), 4, 500, true));
        ma.add(Animations.GO_UP, AnimatedTextureRect.createLinearAnimation(new IntRect(0, 96, 32, 32), 4, 500, true));
        ma.add(Animations.GO_RIGHT, AnimatedTextureRect.createLinearAnimation(new IntRect(0, 64, 32, 32), 4, 500, true));
        ma.add(Animations.GO_DOWN, AnimatedTextureRect.createLinearAnimation(new IntRect(0, 0, 32, 32), 4, 500, true));
        ma.setAnimation(Animations.GO_DOWN);
        player.addComponent(ma);
        player.addComponent(ma, ComponentType.getTypeFor(AbstractTextureRect.class));

        player.addComponent(new SpriteAnimation(3, 500, true));
        player.addComponent(new HitBox(new FloatRect(6, 16, 20, 16)));
        player.addComponent(new CollideWithMap());
        player.addComponent(new Player());
        player.addComponent(new Orientation(DOWN));
        player.addComponent(new Collector());
        player.addToWorld();
        world.getManager(TagManager.class).register("PLAYER", player);
        return player;
    }

    public static Entity createNoixCoco(AppContent appContent, World world, float x, float y) {
        Entity noixCoco = world.createEntity();
        noixCoco.addComponent(new DebugName("A noix de coco"));
        noixCoco.addComponent(new Transformation(x, y));
        ConstTexture tex = getTexture(appContent, "coco.png");
        noixCoco.addComponent(new TextureComponent(tex));
        Vector2i texSize = tex.getSize();
        noixCoco.addComponent(new FixedTextureRect(new IntRect(0, 0, texSize.x, texSize.y)), ComponentType.getTypeFor(AbstractTextureRect.class));
        noixCoco.addComponent(new HitBox(new FloatRect(32, 32, 16, 16)));
        noixCoco.addToWorld();
        world.getManager(GroupManager.class).add(noixCoco, "COLLECTABLE");
        return noixCoco;
    }


    public static Entity createCoin(AppContent appContent, World world, int x, int y) {
        Entity coin = world.createEntity();
        coin.addComponent(new DebugName("A coin"));
        Transformation t = new Transformation(x, y);
        t.getTransformable().scale(.5f, .5f);
        coin.addComponent(t);

        coin.addComponent(new TextureComponent(getTexture(appContent, "coin.png")));

        AnimatedTextureRect animatedRect
                = AnimatedTextureRect.createSquareAnimation(new IntRect(0, 0, 64, 64), 8, 8, 1000);
        animatedRect.setLoop(true);

        coin.addComponent(animatedRect, ComponentType.getTypeFor(AbstractTextureRect.class));
        // to allow specific accesses
        coin.addComponent(animatedRect, ComponentType.getTypeFor(AnimatedTextureRect.class));

        coin.addComponent(new HitBox(new FloatRect(0, 0, 32, 32)));
        coin.addToWorld();
        world.getManager(GroupManager.class).add(coin, "COLLECTABLE");
        return coin;
    }

    public static void createPet(AppContent appContent, World world, float x, float y) {
        Entity pet = world.createEntity();
        pet.addComponent(new DebugName("A pet"));
        pet.addComponent(new Transformation(x, y));
        pet.addComponent(new Velocity());
        
        pet.addComponent(new TextureComponent(getTexture(appContent, "pet.png")));

        MultipleAnimations ma = new MultipleAnimations();
        ma.add(Animations.GO_LEFT, AnimatedTextureRect.createLinearAnimation(new IntRect(0, 0, 24, 21), 4, 500, true));
        ma.add(Animations.GO_UP, AnimatedTextureRect.createLinearAnimation(new IntRect(0, 48, 21, 21), 4, 500, true));
        ma.add(Animations.GO_RIGHT, AnimatedTextureRect.createLinearAnimation(new IntRect(0, 24, 24, 21), 4, 500, true));
        ma.add(Animations.GO_DOWN, AnimatedTextureRect.createLinearAnimation(new IntRect(0, 72, 32, 32), 4, 500, true));
        ma.setAnimation(Animations.GO_LEFT);
        pet.addComponent(ma);
        pet.addComponent(ma, ComponentType.getTypeFor(AbstractTextureRect.class));

        pet.addComponent(new HitBox(new FloatRect(0, 5, 24, 16)));
        pet.addComponent(new AIPetComponent());
        pet.addComponent(new Orientation());
        pet.addToWorld();
    }

    public static void createMonster(AppContent appContent, World world, float x, float y) {
        Entity monster = world.createEntity();
        monster.addComponent(new DebugName("A monster"));
        monster.addComponent(new Transformation(x, y));
        monster.addComponent(new Velocity());
        //monster.addComponent(new RenderableSprite("monster.png", new IntRect(0, 0, 24, 36)));
        //monster.addComponent(new SpriteAnimation(4, 500, true));
        monster.addComponent(new HitBox(new FloatRect(0, 5, 24, 31)));
        monster.addComponent(new Orientation());

        monster.addComponent(new TextureComponent(getTexture(appContent, "monster.png")));

        MultipleAnimations ma = new MultipleAnimations();
        ma.add(Animations.GO_UP, AnimatedTextureRect.createLinearAnimation(new IntRect(0, 0, 24, 36), 4, 500, true));
        ma.add(Animations.GO_RIGHT, AnimatedTextureRect.createLinearAnimation(new IntRect(0, 36, 24, 36), 4, 500, true));
        ma.add(Animations.GO_DOWN, AnimatedTextureRect.createLinearAnimation(new IntRect(0, 36 * 2, 24, 36), 4, 500, true));
        ma.add(Animations.GO_LEFT, AnimatedTextureRect.createLinearAnimation(new IntRect(0, 36 * 3, 24, 36), 4, 500, true));
        ma.setAnimation(Animations.GO_LEFT);
        monster.addComponent(ma);
        monster.addComponent(ma, ComponentType.getTypeFor(AbstractTextureRect.class));

        List<Vector2f> path = new ArrayList<>();
        path.add(new Vector2f(x, y));
        path.add(new Vector2f(x + 160, y));
        path.add(new Vector2f(x + 161, y + 32));
        monster.addComponent(new AIMonsterComponent(path));
        monster.addToWorld();
    }

    private static ConstTexture getTexture(AppContent appContent, String textureName) {
        return appContent.getGraphicEngine().getTexture(textureName);
    }

}
