/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components.interfaces;

/**
 *
 */
public interface AIWaitable extends AIStateBased {

    void startTimer();

    long getElapsedTime();

}
