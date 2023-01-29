package util;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

public class ClipInfo implements LineListener {
    private final static String SOUND_DIR = "Sounds/";

    private boolean isLooping = false;
    private String name, pathname;
    private DecimalFormat decimalFormat;
    private Clip clip = null;
    private SoundWatcher soundWatcher = null;


    public ClipInfo(String name, String filename) {
        this.name = name;
        pathname = SOUND_DIR + filename;
        decimalFormat = new DecimalFormat("0.#");

        loadClip(pathname);
    }

    private void loadClip(String pathname) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(pathname));
            AudioFormat audioFormat = audioInputStream.getFormat();
            if ((audioFormat.getEncoding() == AudioFormat.Encoding.ULAW) ||
                (audioFormat.getEncoding() == AudioFormat.Encoding.ALAW)) {
                AudioFormat newAudioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                                                             audioFormat.getSampleRate(),
                                                             audioFormat.getSampleSizeInBits() * 2,
                                                             audioFormat.getChannels(),
                                                             audioFormat.getFrameSize() * 2,
                                                             audioFormat.getFrameRate(), true);
                audioInputStream = AudioSystem.getAudioInputStream(newAudioFormat, audioInputStream);
                System.out.println("Converted Audio format: " + newAudioFormat);
                audioFormat = newAudioFormat;
            }

            DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);

            if (!AudioSystem.isLineSupported(info)) System.out.println("Unsupported Clip File: " + pathname);

            try {
                clip = (Clip)AudioSystem.getLine(info);
            } catch (LineUnavailableException e) {
                throw new RuntimeException(e);
            }

            clip.addLineListener(this);

            try {
                clip.open(audioInputStream);
            } catch (LineUnavailableException e) {
                throw new RuntimeException(e);
            }

            audioInputStream.close();

            checkDuration();
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkDuration() {
        double duration = clip.getMicrosecondLength() / 1000000.0;
        if (duration <= 1.0) {
            System.out.println("WARNING. Duration <= 1 sec : " + decimalFormat.format(duration) + " secs");
            System.out.println("         The clip in " + pathname + " may not play in J2SE 1.5 -- make it longer");
        }
        else System.out.println(pathname + ": Duration: " + decimalFormat.format(duration) + " secs");
    }

    @Override
    public void update(LineEvent event) {
        if (event.getType() == LineEvent.Type.STOP) {
            clip.stop();
            clip.setFramePosition(0);
            if (!isLooping) {
                if (soundWatcher != null) soundWatcher.atSequenceEnd(name, SoundWatcher.STOPPED);
            } else {
                clip.start();
                if (soundWatcher != null) soundWatcher.atSequenceEnd(name, SoundWatcher.REPLAYED);
            }
        }
    }
}
