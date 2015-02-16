/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package architecture;

import graphics.GraphicEngine;
import org.apache.commons.lang3.Validate;
import org.jsfml.system.Clock;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2i;
import org.jsfml.window.event.Event;
import sounds.MusicEngine;

/**
 *
 */
public final class Application implements AppContent {
    private final ApplicationOptions mOptions;


    public Application(String name, String[] args) {
        mStates = new StateManager(this);
        mOptions = new ApplicationOptions(args);
        mOptions.set("window.name", name);

    }

    public void setDisplayMode(int width, int height, boolean fullscreen) {
        mOptions.setIfUnset("window.size.width", width);
        mOptions.setIfUnset("window.size.height", height);
    }

    public void addState(AbstractApplicationState state) {
        mStates.addState(state);
    }

    public void setStartingState(AppStateEnum id) {
        mStates.setStartingState(id.name());
    }

    @Override
    public void goToState(AppStateEnum stateId) {
        mStates.goToState(stateId.name());
    }

    @Override
    public void exit() {
        mRunning = false;
    }

    @Override
    public ApplicationOptions getOptions() {
        return mOptions;
    }

    @Override
    public GraphicEngine getGraphicEngine() {
        Validate.notNull(mGraphicEngine, "Application has to run before calling this method.");
        return mGraphicEngine;
    }

    @Override
    public MusicEngine getMusicEngine() {
        Validate.notNull(mMusicEngine, "Application has to run before calling this method.");
        return mMusicEngine;
    }

    public void run() {
        /* Init application ressources */
        mGraphicEngine = new GraphicEngine(
                new Vector2i(
                        mOptions.get("window.size.width", 800),
                        mOptions.get("window.size.heigth", 600)),
                mOptions.get("window.name"),
                mOptions.get("window.fullscreen", false));
        
        mMusicEngine = new MusicEngine();
        mStates.initAll();

        /* Main loop */
        Clock clock = new Clock();
        Time timeSinceLastUpdate = Time.ZERO;

        mRunning = true;
        while (mRunning) {
            final Time dt = clock.restart();
            timeSinceLastUpdate = Time.add(timeSinceLastUpdate, dt);

            final AbstractApplicationState currentState = mStates.getCurrentState();

            while (timeSinceLastUpdate.compareTo(TIME_PER_FRAME) > 0) {
                timeSinceLastUpdate = Time.sub(timeSinceLastUpdate, TIME_PER_FRAME);

                /* Process Input devices (keyboard, mouse, joystick, etc) */
                handleEvents(currentState);
                /* Process Game Logic (update position, velocity, etc. of
                 each moving game object and perform collision
                 detection and AI functions) */
                update(currentState, TIME_PER_FRAME);
            }

            /* Erase the screen and draw each game object */
            render(currentState);
        }

        /* Closing application ressources */
        mGraphicEngine.close();
    }


    private void handleEvents(AbstractApplicationState state) {
        for (Event event : getGraphicEngine().getWindowEvents()) {
            if (event.type == Event.Type.CLOSED) {
                mRunning = false;
                return;
            }

            state.handleEvent(event);
        }
    }

    private void update(AbstractApplicationState state, final Time time) {
        state.update(time);
    }

    private void render(AbstractApplicationState state) {
        mGraphicEngine.beginRender();
        state.render();
        mGraphicEngine.endRender();
    }

    private static final Time TIME_PER_FRAME = Time.getSeconds(1.f / 60.f);

    private boolean mRunning;
    private final StateManager mStates;
    private GraphicEngine mGraphicEngine;
    private MusicEngine mMusicEngine;

    


}
