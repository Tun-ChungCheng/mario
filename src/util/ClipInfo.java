package util;

import javax.sound.sampled.*;
import java.io.*;
import java.net.URL;

public class ClipInfo implements LineListener {
    private static final String SOUND_DIR = "/sounds/";

    private boolean isLooping = false;
    private String name;
    private Clip clip = null;


    public ClipInfo(String name, String filename) {
        this.name = name;
        String pathFromContentRoot = SOUND_DIR + filename;

        loadClip(pathFromContentRoot);
    }

    private void loadClip(String pathFromContentRoot) {
        try  {
            URL url = this.getClass().getResource(pathFromContentRoot);
            AudioInputStream audioInputStream = null;
            if (url != null) audioInputStream = AudioSystem.getAudioInputStream(url);

            AudioFormat audioFormat = null;
            if (audioInputStream != null) audioFormat = audioInputStream.getFormat();

            DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);
            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Unsupported Clip File: " + pathFromContentRoot);
                return;
            }

            clip = (Clip) AudioSystem.getLine(info);
            clip.addLineListener(this);
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException e) {
            System.out.println("Unsupported audio file: " + pathFromContentRoot);
        } catch (IOException e) {
            System.out.println("Could not read: " + pathFromContentRoot);
        } catch (LineUnavailableException e) {
            System.out.println("No audio line available for : " + pathFromContentRoot);
        } catch (Exception e) {
            System.out.println("Problem with " + pathFromContentRoot);
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
