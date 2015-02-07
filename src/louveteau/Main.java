/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package louveteau;

import architecture.Application;
import states.GameState;
import states.MainMenuState;
import states.SplashScreenState;

/**
 *
 */
public class Main {

    public static final int SPLASHSCREENSTATE = 0;
    public static final int MAINMENUSTATE = 1;
    public static final int GAMESTATE = 2;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application app = new Application("Louveteau");
        app.setDisplayMode(800, 600, false);

        app.addState(new SplashScreenState(SPLASHSCREENSTATE));
        app.addState(new MainMenuState(MAINMENUSTATE));
        app.addState(new GameState(GAMESTATE));
        app.goToState(GAMESTATE);

        app.run();
    }

}
