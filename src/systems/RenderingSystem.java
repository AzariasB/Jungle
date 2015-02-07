
package systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import components.Position;
import components.RenderableSprite;
import graphics.GraphicEngine;
import org.jsfml.graphics.CircleShape;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.Sprite;

/**
 *
 */
public class RenderingSystem extends EntityProcessingSystem {

    @Mapper
    ComponentMapper<Position> pm;
    @Mapper
    ComponentMapper<RenderableSprite> rsm;

    private final GraphicEngine mGraphicEngine;

    @SuppressWarnings("unchecked")
    public RenderingSystem(GraphicEngine graphicEngine) {
        super(Aspect.getAspectForAll(Position.class, RenderableSprite.class));

        mGraphicEngine = graphicEngine;
    }


    @Override
    protected boolean checkProcessing() {
        return true;
    }

    @Override
    protected void process(Entity entity) {
        Position position = pm.get(entity);
        RenderableSprite rs = rsm.get(entity);

        Drawable toDraw = null;
        switch (rs.spriteId) {
            case 0:
                CircleShape c = new CircleShape(50);
                c.setFillColor(Color.YELLOW);
                c.setPosition(position.getPosition());
                toDraw = c;
                break;
            case 1:
                Sprite s = new Sprite(mGraphicEngine.getTexture("ball.png"));
                s.setPosition(position.getPosition());
                toDraw = s;
                break;
            case 2:
                s = new Sprite(mGraphicEngine.getTexture("coco.png"));
                s.setPosition(position.getPosition());
                toDraw = s;
                break;

        }

        if (toDraw != null) {
            mGraphicEngine.getRenderTarget().draw(toDraw);
        }
    }

}
