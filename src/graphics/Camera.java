/*

Games camera

 */
package graphics;

import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;

public class Camera {
    public Camera(Vector2f startPosition,Vector2f size){
        mView = new View();
        mTarget = new Vector2f(0, 0);
        mView.setCenter(startPosition);
        mView.setSize(size);
    }
    
    public void setTarget(Vector2f newTarget){
        this.mTarget = newTarget;
    }
    
    public void updateCamera(){
        Vector2f origin =  mView.getCenter();
        mView.setCenter(new Vector2f((origin.x + mTarget.x )/2, ( origin.y + mTarget.y )/2));
    }
    
    public Vector2f getTopLeft(){
        return new Vector2f(mView.getCenter().x - mView.getSize().x/2, mView.getCenter().y - mView.getSize().y/2);
    }
    
    public View getView(){
        return this.mView;
    }
    
    
    private Vector2f mTarget;
    private final View mView;
}
