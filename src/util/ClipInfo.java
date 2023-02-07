package util;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class ClipInfo implements LineListener {
    private static final String SOUND_DIR = "sounds/";

    private boolean isLooping = false;
    private String name, relativePath;
    private Clip clip = null;


    public ClipInfo(String name, String filename) {
        this.name = name;
        relativePath = SOUND_DIR + filename;

        loadClip(relativePath);
    }

    private void loadClip(String relativePath) {
        try (AudioInputStream audioInputStream =
                     AudioSystem.getAudioInputStream(new File(relativePath))) {
            AudioFormat audioFormat = audioInputStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);

            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Unsupported Clip File: " + relativePath);
                return;
            }

            clip = (Clip) AudioSystem.getLine(info);
            clip.addLineListener(this);
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException e) {
            System.out.println("Unsupported audio file: " + relativePath);
        } catch (IOException e) {
            System.out.println("Could not read: " + relativePath);
        } catch (LineUnavailableException e) {
            System.out.println("No audio line available for : " + relativePath);
        } catch (Exception e) {
            System.out.println("Problem with " + relativePath);
        }
    }

    @Override
    public void update(LineEvent event) {
        if (event.getType() == LineEvent.Type.STOP) {
            clip.stop();
            clip.setFramePosition(0);
            if (isLooping) clip.start();
        }
    }

    public void play(boolean isLoop) {
        if (clip != null) {
            isLooping = isLoop;
            clip.start();
        }
    }

    public void stop() {
        if (clip != null) {
            isLooping = false;
            clip.stop();
        }
    }
}
