
package systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import components.Position;
import graphics.GraphicEngine;
import org.jsfml.graphics.CircleShape;
import org.jsfml.graphics.Color;

/**
 *
 */
public class RenderingSystem extends EntityProcessingSystem {

    @Mapper
    ComponentMapper<Position> pm;

    private final GraphicEngine mGraphicEngine;

    @SuppressWarnings("unchecked")
    public RenderingSystem(GraphicEngine graphicEngine) {
        super(Aspect.getAspectForAll(Position.class));

        mGraphicEngine = graphicEngine;
    }


    @Override
    protected boolean checkProcessing() {
        return true;
    }

    @Override
    protected void process(Entity entity) {
        Position position = pm.get(entity);

        CircleShape c = new CircleShape(50);
        c.setFillColor(Color.YELLOW);
        c.setPosition(position.getPosition());
        mGraphicEngine.getRenderTarget().draw(c);
    }

}
