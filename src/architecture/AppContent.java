/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package architecture;

import graphics.GraphicEngine;
import sounds.MusicEngine;

/**
 *
 */
public interface AppContent {

    public void goToState(AppStateEnum stateId);

    public void exit();

    public ApplicationOptions getOptions();

    public GraphicEngine getGraphicEngine();

    public MusicEngine getMusicEngine();

}
