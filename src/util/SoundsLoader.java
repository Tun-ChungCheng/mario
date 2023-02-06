package util;

import javax.sound.sampled.*;
import java.io.*;


public class SoundsLoader {
    private final static String SOUND_DIR = "sounds/";

    private Clip background, jumpClip, dieClip;

    public SoundsLoader() {
        background = setClip(loadAudio("background"));
        background.start();
    }

    private AudioInputStream loadAudio(String filename) {
        String pathname = SOUND_DIR + filename + ".wav";

        try {
            return AudioSystem.getAudioInputStream(new File(pathname));
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    private Clip setClip(AudioInputStream audioSystem)  {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(audioSystem);
            return clip;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public void playJumpSound() {
        jumpClip = setClip(loadAudio("jump"));
        jumpClip.start();
    }

    public void playDieSound() {
        dieClip = setClip(loadAudio("die"));
        dieClip.start();
    }
}