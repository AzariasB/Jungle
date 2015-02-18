/*
    All the texts we can display above the map (dialogs, HUV,tutorila, ...)
*/

package components;

import com.artemis.Component;
import org.jsfml.graphics.ConstFont;
import org.jsfml.graphics.Text;


public class RenderableText extends Component {

    /**
     * 
     * @param font
     * @param textContent String to display
     */
    public RenderableText(ConstFont font, String textContent) {
        mText = new Text(textContent, font);
    }

    public Text getText() {
        return mText;
    }

    public void setTextContent(String textContent) {
        mText.setString(textContent);
    }
    
    private Text mText;
}
