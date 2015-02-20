package systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.ImmutableBag;
import components.AIMonsterComponent;
import components.AIPetComponent;
import components.DamageMaker;
import components.HitBox;
import components.Transformation;
import graphics.GraphicEngine;
import org.jsfml.graphics.CircleShape;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.ConstFont;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.Transform;
import org.jsfml.graphics.Transformable;
import org.jsfml.system.Vector2f;

/**
 *
 */
public class DebugRenderingSystem extends EntityProcessingSystem {

    @Mapper
    ComponentMapper<Transformation> transm;
    @Mapper
    ComponentMapper<HitBox> hitboxm;
    @Mapper
    ComponentMapper<AIPetComponent> aipetm;
    @Mapper
    ComponentMapper<AIMonsterComponent> monsterm;
    @Mapper
    ComponentMapper<DamageMaker> damagem;

    private final GraphicEngine mGraphicEngine;
    private Text mTmpText;
    private RectangleShape mTmpRectShape;
    private GroupManager mGroupManager;
    private TagManager mTagManager;
    private CircleShape mCircleShape;

    @SuppressWarnings("unchecked")
    public DebugRenderingSystem(GraphicEngine graphicEngine) {
        super(Aspect.getAspectForAll(Transformation.class)
                .one(Transformation.class, HitBox.class,
                        AIPetComponent.class));

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

        mCircleShape = new CircleShape();

        mGroupManager = world.getManager(GroupManager.class);
        mTagManager = world.getManager(TagManager.class);
    }

    @Override
    protected boolean checkProcessing() {
        return true;
    }

    @Override
    protected void process(Entity entity) {

        /*
         Groups and Tags
         */
        StringBuilder debug = new StringBuilder();

        /* Groups */
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

        /* Tags */
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

        /* Display Group and Tags */
        if (false && debug.length() > 0) {
            mTmpText.setString(debug.toString());
            mTmpRectShape.setPosition(Vector2f.ZERO);
            mTmpRectShape.setSize(new Vector2f(mTmpText.getGlobalBounds().width,
                    28));
            mTmpRectShape.setFillColor(Color.BLACK);
            mTmpRectShape.setOutlineColor(Color.BLACK);

            Transform transform = transm.get(entity).getTransformable().getTransform();
            RenderStates renderStates = new RenderStates(transform);

            mGraphicEngine.getRenderTarget().draw(mTmpRectShape, renderStates);
            mGraphicEngine.getRenderTarget().draw(mTmpText, renderStates);
        }

        /*
         HitBox
         */
        HitBox hitbox = hitboxm.getSafe(entity);
        if (hitbox != null) {
            if (transm.has(entity)) {
                Transformable transformable = transm.get(entity).getTransformable();
                FloatRect rhb = hitbox.moveCopy(transformable.getPosition());

                mTmpRectShape.setPosition(new Vector2f(rhb.left, rhb.top));
                mTmpRectShape.setSize(new Vector2f(rhb.width, rhb.height));
                if (damagem.has(entity)) {
                    mTmpRectShape.setFillColor(new Color(255, 0, 0, 96));
                } else {
                    mTmpRectShape.setFillColor(new Color(0, 0, 0, 0));
                }
                mTmpRectShape.setOutlineColor(Color.RED);
                mTmpRectShape.setOutlineThickness(1.f);
                mGraphicEngine.getRenderTarget().draw(mTmpRectShape);
            }
        }

        /*
         AI Pet pathfinding
         */
        if (aipetm.has(entity)) {
            AIPetComponent aipet = aipetm.get(entity);
            /* Goal */
            if (aipet.getPath() != null) {
                for (Vector2f c : aipet.getPath()) {
                    drawCircle(Color.YELLOW, c, 2);
                }
            }
            if (aipet.getGoal() != null) {
                drawCircle(Color.YELLOW, aipet.getGoal());
            }
            if (aipet.getOldPlayerPos() != null) {
                drawCircle(Color.BLUE, aipet.getOldPlayerPos());
            }

        }

        /*
         AI Monster pathfinding
         */
        if (monsterm.has(entity)) {
            AIMonsterComponent monster = monsterm.get(entity);
            /* Goal */
            if (monster.getPath() != null) {
                for (Vector2f c : monster.getPath()) {
                    drawCircle(Color.RED, c, 2);
                }
            }
            if (monster.getGoal() != null) {
                drawCircle(Color.RED, monster.getGoal());
            }
            if (monster.getOldPlayerPos() != null) {
                drawCircle(Color.BLUE, monster.getOldPlayerPos());
            }

        }
    }

    private void drawCircle(Color color, Vector2f position) {
        drawCircle(color, position, 4.f);
    }

    private void drawCircle(Color color, Vector2f position, float radius) {
        mCircleShape.setRadius(radius);
        mCircleShape.setFillColor(color);
        mCircleShape.setPosition(position);
        mCircleShape.setOrigin(mCircleShape.getLocalBounds().width / 2,
                mCircleShape.getLocalBounds().height / 2);
        mGraphicEngine.getRenderTarget().draw(mCircleShape);
    }

}
