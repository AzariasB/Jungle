/*
 All the sounds and the music of the games in here
 */
package sounds;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsfml.audio.Music;
import org.jsfml.audio.Sound;
import org.jsfml.audio.SoundBuffer;

public class MusicEngine {

    public MusicEngine(){
        mSounds = new HashMap<>();
        mMusics = new HashMap<>();
    }
    
    public Sound getSound(String soundName) {
        if (mSounds.keySet().contains(soundName)) {
            return mSounds.get(soundName);
        } else {
            try {
                Path myPath = FileSystems.getDefault().getPath("./assets/Sounds", soundName);
                SoundBuffer sBuffer = new SoundBuffer();
                sBuffer.loadFromFile(myPath);
                Sound nSound = new Sound(sBuffer);
                mSounds.put(soundName, nSound);
                
            } catch (IOException ex) {
                Logger.getLogger(MusicEngine.class.getName()).log(Level.SEVERE, null, ex);
            }
            return mSounds.get(soundName);
        }
    }

    public Music getMusic(String musicName) {
        if (mMusics.keySet().contains(musicName)) {
            return mMusics.get(musicName);
        } else {
            Path mPath = FileSystems.getDefault().getPath("./assets/Musics", musicName);
            Music mMus = new Music();
            try {
                mMus.openFromFile(mPath);
            } catch (IOException ex) {
                System.err.println(ex.toString());
                System.exit(1);
            }
            mMusics.put(musicName, mMus);
            return mMusics.get(musicName);
        }
    }

    private final Map<String, Sound> mSounds;
    private final Map<String, Music> mMusics;
}
