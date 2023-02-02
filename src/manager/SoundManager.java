package manager;

import javax.sound.sampled.*;
import java.io.*;


public class SoundManager {
    private final static String SOUND_DIR = "sounds/";

    private Clip background;

    public SoundManager() {
        background = setClip(loadAudio("background"));
    }

    private AudioInputStream loadAudio(String filename) {
        String pathname = SOUND_DIR + filename + ".wav";

        try {
            return AudioSystem.getAudioInputStream(new File(pathname));
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Clip setClip(AudioInputStream audioSystem)  {
        Clip clip;
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }

        try {
            clip.open(audioSystem);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return clip;
    }

    public void startBackgroundMusic() {
        background.loop(Clip.LOOP_CONTINUOUSLY);
        background.start();
    }

    public void playSound(String filename) {
        Clip clip = setClip(loadAudio(filename));
        clip.start();
    }
}