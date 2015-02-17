package architecture;

import graphics.GraphicEngine;
import org.apache.commons.lang3.Validate;
import org.jsfml.system.Time;
import org.jsfml.window.event.Event;

/**
 *
 */
public abstract class AbstractApplicationState {

    public AbstractApplicationState() {
    }

    public abstract AppStateEnum getStateId();

    public void initialize() {
    }

    public abstract void handleEvent(Event event);

    public abstract void update(Time time);

    public abstract void render();

    public void notifyExiting() {
    }

    public void notifyEntering() {
    }

    protected final void setApplication(Application application) {
        mApplication = application;
    }

    protected final AppContent getAppContent() {
        Validate.notNull(mApplication);
        return mApplication;
    }

    protected final GraphicEngine getGraphicEngine() {
        return getAppContent().getGraphicEngine();

    }

    private Application mApplication;

}
