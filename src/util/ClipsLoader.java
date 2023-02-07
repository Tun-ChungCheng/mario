package util;

import java.io.*;
import java.util.HashMap;
import java.util.StringTokenizer;


public class ClipsLoader {
    private static final String SOUND_DIR = "sounds/";

    private HashMap<String, ClipInfo> clipsMap;

    public ClipsLoader(String soundsInfo) {
        clipsMap = new HashMap<>();
        loadSoundsFile(soundsInfo);
    }

    private void loadSoundsFile(String soundsInfo) {
        String soundsInfoPath = SOUND_DIR + soundsInfo;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(soundsInfoPath))) {
            StringTokenizer tokens;
            String line, name, filename;
            while ((line = bufferedReader.readLine()) != null) {
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
            System.out.println("Error reading file: " + soundsInfoPath);
            System.exit(1);
        }
    }

    private void load(String name, String filename) {
        if (clipsMap.containsKey(name)) System.out.println( "Error: " + name + "already stored");
        else {
            clipsMap.put(name, new ClipInfo(name, filename));
            System.out.println("-- " + name + "/" + filename);
        }
    }

    public void play(String name, boolean isLoop) {
        ClipInfo clipInfo = clipsMap.get(name);
        if (clipInfo == null) System.out.println("Error: " + name + "not stored");
        else clipInfo.play(isLoop);
    }

    public void stop(String name) {
        ClipInfo clipInfo = clipsMap.get(name);
        if (clipInfo == null) System.out.println("Error: " + name + "not stored");
        else clipInfo.stop();

    }
}