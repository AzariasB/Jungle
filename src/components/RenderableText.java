/*
    All the texts we can display above the map (dialogs, HUV,tutorila, ...)
*/

package components;

import com.artemis.Component;
import java.io.IOException;
import java.nio.file.FileSystems;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.Text;
import org.jsfml.system.Vector2f;


public class RenderableText extends Component {

    /**
     * 
     * @param fontOrigin origin of the font
     * @param textContent what do I display
     */
    public RenderableText(String fontOrigin,String textContent){
        try {
            mFont.loadFromFile(FileSystems.getDefault().getPath("assets", "fonts",fontOrigin));
            mText.setFont(mFont);
        } catch (IOException ex) {
            System.err.println("Erreur lors du chargement de la police : " + fontOrigin + "\n" + ex);
            System.exit(1);
        }
    }
    
    public Vector2f getPosition(){
        return mPosition;
    }
    
    public void setPosition(Vector2f newPosition){
        mPosition = newPosition;
    }
    
    private Font mFont;
    private Text mText;
    private Vector2f mPosition;
}
