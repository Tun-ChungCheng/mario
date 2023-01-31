package manager;

import javax.sound.sampled.*;
import java.io.*;


public class SoundManager {
    private final static String SOUND_DIR = "Sounds/";

    private Clip background;

    public SoundManager() {
        background = setClip(loadAudio("background"));
    }

    private AudioInputStream loadAudio(String filename) {
        String pathname = SOUND_DIR + filename + ".wav";
        try {
             return AudioSystem.getAudioInputStream(new File(pathname));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Clip setClip(AudioInputStream audioSystem) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(audioSystem);
            return clip;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void startBackgroundMusic() {
        background.start();
    }

}