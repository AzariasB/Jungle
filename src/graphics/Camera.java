/*

Camera du jeu

 */
package graphics;

import org.jsfml.graphics.View;

public class Camera {
    
    public void setTarget(TargetAble newTarget){
        this.mView.setCenter(newTarget.getPosition());
    }
    
    
    private View mView;
}
