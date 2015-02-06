/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package louveteau;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Clock;
import org.jsfml.system.Time;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.Event;
import physic.PhysicEngine;
import physic.PhysicEngine.Direction;

/**
 *
 */
class Game {

    public Game() {
        mGraphicEngine = new GraphicEngine();
        mWindow = mGraphicEngine.getWindow();

        mPhysicEngine = new PhysicEngine();
    }

    public void run() {
        Clock clock = new Clock();
        Time timeSinceLastUpdate = Time.ZERO;

        while (mWindow.isOpen()) {
            Time dt = clock.restart();
            timeSinceLastUpdate = Time.add(timeSinceLastUpdate, dt);

            while (timeSinceLastUpdate.compareTo(TIME_PER_FRAME) > 0) {
                timeSinceLastUpdate = Time.sub(timeSinceLastUpdate, TIME_PER_FRAME);

                handleEvents();
                update(TIME_PER_FRAME);
            }

            render();
        }
    }

    protected void handleEvents() {
        for (Event event : mWindow.pollEvents()) {
            if (event.type == Event.Type.CLOSED) {
                mWindow.close();
            }
            if (event.type == Event.Type.KEY_PRESSED) {
                if (event.asKeyEvent().key == Keyboard.Key.UP) {
                    mPhysicEngine.movePlayer(Direction.UP);
                } else if (event.asKeyEvent().key == Keyboard.Key.LEFT) {
                    mPhysicEngine.movePlayer(Direction.LEFT);
                } else if (event.asKeyEvent().key == Keyboard.Key.DOWN) {
                    mPhysicEngine.movePlayer(Direction.DOWN);
                } else if (event.asKeyEvent().key == Keyboard.Key.RIGHT) {
                    mPhysicEngine.movePlayer(Direction.RIGHT);
                }
            }
        }
    }

    protected void update(Time time) {
        mPhysicEngine.update(time);
    }

    protected void render() {
        mGraphicEngine.render();
    }

    private static final Time TIME_PER_FRAME = Time.getSeconds(1.f / 60.f);
    private GraphicEngine mGraphicEngine;
    private RenderWindow mWindow;
    private PhysicEngine mPhysicEngine;
    

}
