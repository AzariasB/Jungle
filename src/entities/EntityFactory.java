
package entities;

import com.artemis.Entity;
import com.artemis.World;
import components.Collectable;
import components.HitBox;
import components.RenderableSprite;
import components.SpriteAnimationComplex;
import components.Transformation;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.IntRect;

/**
 *
 */
public class EntityFactory {

    public static Entity createNoixCoco(World world, float x, float y) {
        Entity noixCoco = world.createEntity();
        noixCoco.addComponent(new Transformation(x, y));
        noixCoco.addComponent(new RenderableSprite("coco.png"));
        noixCoco.addComponent(new HitBox(new FloatRect(0, 0, 16, 16)));
        noixCoco.addComponent(new Collectable());
        noixCoco.addToWorld();
        return noixCoco;
    }


    public static void createCoin(World world, int x, int y) {
        Entity coin = world.createEntity();
        Transformation t = new Transformation(x, y);
        t.getTransformable().scale(.5f, .5f);
        coin.addComponent(t);
        RenderableSprite coinRs = new RenderableSprite("coin.png");
        coinRs.setRect(new IntRect(0, 0, 64, 64));
        coin.addComponent(coinRs);
        coin.addComponent(new SpriteAnimationComplex(8, 8, 1000, true, 0, 0));
        coin.addComponent(new HitBox(new FloatRect(0, 0, 32, 32)));
        coin.addComponent(new Collectable());
        coin.addToWorld();
    }

}
