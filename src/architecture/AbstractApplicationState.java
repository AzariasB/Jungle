
package architecture;

import graphics.GraphicEngine;
import org.apache.commons.lang3.Validate;
import org.jsfml.system.Time;
import org.jsfml.window.event.Event;

/**
 *
 */
public abstract class AbstractApplicationState {
    private final int mId;

    public AbstractApplicationState(int id) {
        mId = id;
    }

    public int getId() {
        return mId;
    }

    public abstract void handleEvent(Event event);

    public abstract void update(Time time);

    public abstract void render();

    protected void setApplication(Application application) {
        mApplication = application;
    }

    protected Application getApplication() {
        Validate.notNull(mApplication);
        return mApplication;
    }

    protected GraphicEngine getGraphicEngine() {
        return getApplication().getGraphicEngine();
    }

    private Application mApplication;

    

    


}
