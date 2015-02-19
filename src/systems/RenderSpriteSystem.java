
package systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import components.AbstractTextureRect;
import components.TextureComponent;
import components.Transformation;
import graphics.GraphicEngine;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Transform;

/**
 *
 */
public class RenderSpriteSystem extends EntityProcessingSystem {

    @Mapper
    ComponentMapper<Transformation> transm;
    @Mapper
    ComponentMapper<TextureComponent> rsm;
    @Mapper
    ComponentMapper<AbstractTextureRect> trm;

    private final GraphicEngine mGraphicEngine;
    private final Sprite mTmpSprite;

    @SuppressWarnings("unchecked")
    public RenderSpriteSystem(GraphicEngine graphicEngine) {
        super(Aspect.getAspectForAll(Transformation.class,
                TextureComponent.class,
                AbstractTextureRect.class
        ));

        mGraphicEngine = graphicEngine;
        mTmpSprite = new Sprite();
    }

    @Override
    protected void process(Entity entity) {
        Transform transform = transm.get(entity).getTransformable().getTransform();
        TextureComponent rs = rsm.get(entity);
        AbstractTextureRect tr = trm.get(entity);
        
        mTmpSprite.setTexture(rs.getTexture());
        mTmpSprite.setTextureRect(tr.getRect());

        RenderStates renderStates = new RenderStates(transform);
        mGraphicEngine.getRenderTarget().draw(mTmpSprite, renderStates);
    }

}
