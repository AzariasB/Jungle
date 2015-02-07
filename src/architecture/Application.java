/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package architecture;

import graphics.GraphicEngine;
import org.apache.commons.lang3.Validate;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Clock;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2i;
import org.jsfml.window.event.Event;
import sounds.MusicEngine;

/**
 *
 */
public final class Application {

    public Application(String name) {
        mWindowName = name;
        mWindowSize = new Vector2i(800, 600);
        mStates = new StateManager(this);
    }

    public void setDisplayMode(int width, int height, boolean fullscreen) {
        mWindowSize = new Vector2i(width, height);
        mWindowFullscreen = fullscreen;
    }

    public void addState(AbstractApplicationState state) {
        mStates.addState(state);
    }

    public void setStartingState(int id) {
        mStates.setStartingState(id);
    }

    public void goToState(int stateId) {
        mStates.goToState(stateId);
    }

    public void exit() {
        mRunning = false;
    }

    public GraphicEngine getGraphicEngine() {
        Validate.notNull(mGraphicEngine, "Application has to run before calling this method.");
        return mGraphicEngine;
    }

    public MusicEngine getMusicEngine() {
        Validate.notNull(mMusicEngine, "Application has to run before calling this method.");
        return mMusicEngine;
    }

    public void run() {
        /* Init application ressources */
        mGraphicEngine = new GraphicEngine(mWindowSize, mWindowName, mWindowFullscreen);
        mMusicEngine = new MusicEngine();
        mStates.initAll();

        /* Main loop */
        Clock clock = new Clock();
        Time timeSinceLastUpdate = Time.ZERO;
        final RenderWindow window = getGraphicEngine().getWindow();

        mRunning = true;
        while (mRunning) {
            final Time dt = clock.restart();
            timeSinceLastUpdate = Time.add(timeSinceLastUpdate, dt);

            final AbstractApplicationState currentState = mStates.getCurrentState();

            while (timeSinceLastUpdate.compareTo(TIME_PER_FRAME) > 0) {
                timeSinceLastUpdate = Time.sub(timeSinceLastUpdate, TIME_PER_FRAME);

                /* Process Input devices (keyboard, mouse, joystick, etc) */
                handleEvents(currentState, window);
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


    private void handleEvents(AbstractApplicationState state, RenderWindow window) {
        for (Event event : window.pollEvents()) {
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

    private Vector2i mWindowSize;
    private final String mWindowName;
    private boolean mWindowFullscreen;
    private GraphicEngine mGraphicEngine;
    private MusicEngine mMusicEngine;

    

}
