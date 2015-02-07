/*
 All the sounds and the music of the games in here
 */
package sounds;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import org.jsfml.audio.Music;
import org.jsfml.audio.Sound;
import org.jsfml.audio.SoundBuffer;

public class MusicEngine {

    public MusicEngine(){
        mSounds = new HashMap<>();
        mMusics = new HashMap<>();
    }
    
    public Sound getSound(String sound_name) {
        if (mSounds.keySet().contains(sound_name)) {
            return mSounds.get(sound_name);
        } else {
            Path myPath = FileSystems.getDefault().getPath("../assets/Sounds", sound_name);
            SoundBuffer sBuffer = new SoundBuffer();
            Sound nSound = new Sound(sBuffer);
            mSounds.put(sound_name, nSound);
            return mSounds.get(sound_name);
        }
    }

    public Music getMusic(String music_name) {
        if (mMusics.keySet().contains(music_name)) {
            return mMusics.get(music_name);
        } else {
            Path mPath = FileSystems.getDefault().getPath("./assets/Musics", music_name);
            Music mMus = new Music();
            try {
                mMus.openFromFile(mPath);
            } catch (IOException ex) {
                System.err.println(ex.toString());
                System.exit(1);
            }
            mMusics.put(music_name, mMus);
            return mMusics.get(music_name);
        }
    }

    private final Map<String, Sound> mSounds;
    private final Map<String, Music> mMusics;
}
