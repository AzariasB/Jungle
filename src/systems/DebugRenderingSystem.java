
package systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.ImmutableBag;
import components.Transformation;
import graphics.GraphicEngine;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.ConstFont;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.Transform;
import org.jsfml.system.Vector2f;

/**
 *
 */
public class DebugRenderingSystem extends EntityProcessingSystem {

    @Mapper
    ComponentMapper<Transformation> transm;

    private final GraphicEngine mGraphicEngine;
    private Text mTmpText;
    private RectangleShape mTmpRectShape;
    private GroupManager mGroupManager;
    private TagManager mTagManager;

    @SuppressWarnings("unchecked")
    public DebugRenderingSystem(GraphicEngine graphicEngine) {
        super(Aspect.getAspectForAll(Transformation.class));

        mGraphicEngine = graphicEngine;
    }

    @Override
    protected void initialize() {
        ConstFont font = mGraphicEngine.getFont("dbg-font.otf");

        mTmpText = new Text();
        mTmpText.setFont(font);
        mTmpText.setCharacterSize(24);
        
        mTmpRectShape = new RectangleShape();
        mTmpRectShape.setFillColor(Color.BLACK);

        mGroupManager = world.getManager(GroupManager.class);
        mTagManager = world.getManager(TagManager.class);
    }

    @Override
    protected boolean checkProcessing() {
        return true;
    }

    @Override
    protected void process(Entity entity) {
        StringBuffer debug = new StringBuffer();

        if (mGroupManager.isInAnyGroup(entity)) {
            ImmutableBag<String> bag = mGroupManager.getGroups(entity);
            debug.append("Groups [");
            int i;
            for (i = 0; i < bag.size() - 1; i++) {
                debug.append(bag.get(i));
                debug.append(", ");
            }
            debug.append(bag.get(i));
            debug.append("] ");
        }

        StringBuffer tags = new StringBuffer();
        for (String tag : mTagManager.getRegisteredTags()) {
            if (entity == mTagManager.getEntity(tag)) {
                tags.append(tag);
                tags.append(", ");
            }
        }
        if (tags.length() > 0) {
            debug.append("Tags [");
            debug.append(tags);
            debug.append("]");
        }

        if (debug.length() > 0) {
            mTmpText.setString(debug.toString());
            mTmpRectShape.setSize(new Vector2f(mTmpText.getGlobalBounds().width,
                    28));

            Transform transform = transm.get(entity).getTransformable().getTransform();
            RenderStates renderStates = new RenderStates(transform);

            mGraphicEngine.getRenderTarget().draw(mTmpRectShape, renderStates);
            mGraphicEngine.getRenderTarget().draw(mTmpText, renderStates);
        }

        
    }

}
