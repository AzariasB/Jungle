
package systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import components.RenderableAnimatedSprite;
import components.RenderableSprite;
import components.Transformation;
import graphics.GraphicEngine;
import org.jsfml.graphics.ConstTexture;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Transform;

/**
 *
 */
public class RenderingSystem extends EntityProcessingSystem {

    @Mapper
    ComponentMapper<Transformation> transm;
    @Mapper
    ComponentMapper<RenderableSprite> rsm;

    private final GraphicEngine mGraphicEngine;
    private final Sprite mTmpSprite;

    @SuppressWarnings("unchecked")
    public RenderingSystem(GraphicEngine graphicEngine) {
        super(Aspect.getAspectForAll(
                Transformation.class, RenderableSprite.class)
                .exclude(RenderableAnimatedSprite.class));

        mGraphicEngine = graphicEngine;
        mTmpSprite = new Sprite();
    }

    @Override
    protected boolean checkProcessing() {
        return true;
    }

    @Override
    protected void inserted(Entity e) {
        RenderableSprite rs = rsm.get(e);

        String textureName = "";
        switch (rs.getSpriteId()) {
            case 1:
                textureName = "ball.png";
                break;
            case 2:
                textureName = "coco.png";
                break;
        }

        ConstTexture tex = mGraphicEngine.getTexture(textureName);
        rs.setTexture(tex);
        rs.setRect(tex.getSize());
    }

    @Override
    protected void process(Entity entity) {
        RenderableSprite rs = rsm.get(entity);
        Transform transform = transm.get(entity).getTransformable().getTransform();

        mTmpSprite.setTexture(rs.getTexture());
        mTmpSprite.setTextureRect(rs.getRect());

        RenderStates renderStates = new RenderStates(transform);
        mGraphicEngine.getRenderTarget().draw(mTmpSprite, renderStates);
    }

}
