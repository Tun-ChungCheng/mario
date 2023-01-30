package util;

import javax.sound.sampled.Clip;
import java.io.*;
import java.util.HashMap;
import java.util.StringTokenizer;


public class ClipsLoader {
    private final static String SOUND_DIR = "Sounds/";

    private HashMap clipsMap;

    public ClipsLoader(String soundsInfo) {
        clipsMap = new HashMap<>();
        loadSoundsFile(soundsInfo);
    }

    private void loadSoundsFile(String soundsInfo) {
        String soundsInfoPath = SOUND_DIR + soundsInfo;
        System.out.println("Reading file: " + soundsInfoPath);
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(soundsInfoPath))) {
            StringTokenizer tokens;
            String line, name, filename;
            while((line = bufferedReader.readLine()) != null) {
                if (line.length() == 0) continue;
                if (line.startsWith("//")) continue;

                tokens = new StringTokenizer(line);
                if (tokens.countTokens() != 2) System.out.println("Wrong no. of arguments for " + line);
                else {
                    name = tokens.nextToken();
                    filename = tokens.nextToken();
                    load(name, filename);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private void load(String name, String filename) {
        if (clipsMap.containsKey(name)) System.out.println( "Error: " + name + "already stored");
        else {
            clipsMap.put(name, new ClipInfo(name, filename));
            System.out.println("-- " + name + "/" + filename);
        }
    }

    public void play(String name) {
        ClipInfo clipInfo = (ClipInfo) clipsMap.get(name);
        if (clipInfo == null) System.out.println( "Error: " + name + "not stored");
        else clipInfo.play();
    }
}
