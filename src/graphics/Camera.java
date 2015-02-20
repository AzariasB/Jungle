/*

Games camera

 */
package graphics;

import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;

public class Camera {
    
    public Camera(Vector2f startPosition,Vector2f size){
        mView = new View();
        mView.setCenter(startPosition);
        mView.setSize(size);
        
    }
    
    public void setTarget(TargetAble newTarget){
        this.mTarget = newTarget.getPosition();
    }
    
    public void updateCamera(){
        Vector2f origin =  mView.getCenter();
        mView.setCenter(new Vector2f((origin.x + mTarget.x )/2, ( origin.y + mTarget.y )/2));
    }
    
    public View getView(){
        return this.mView;
    }
    
    
    private Vector2f mTarget;
    private final View mView;
}
