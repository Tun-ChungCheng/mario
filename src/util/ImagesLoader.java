package util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import static manager.MapManager.MAP_HEIGHT;
import static manager.MapManager.MAP_WIDTH;


public class ImagesLoader {
    private final static String IMAGE_DIR = "Images/";

    private HashMap<String, ArrayList<BufferedImage>> imagesMap;
    

    public ImagesLoader(String imagesInfo) {
        initLoader();
        loadImagesFile(imagesInfo);
    }

    private void initLoader() {
        imagesMap = new HashMap();
    }

    private void loadImagesFile(String imagesInfo) {
        /* Formats:
            o <fnm>                     // a single image
            n <fnm*.ext> <number>       // a numbered sequence of images
            s <fnm> <number>            // an images strip
            g <name> <fnm> [ <fnm> ]*   // a group of images

            and blank lines and comment lines.
        */
        String imagesInfoPath = IMAGE_DIR + imagesInfo;
        System.out.println("Reading file: " + imagesInfoPath);
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(imagesInfoPath))) {
            String line;
            char ch;
            while((line = bufferedReader.readLine()) != null) {
                if (line.length() == 0) continue;
                if (line.startsWith("//")) continue;

                ch = Character.toLowerCase(line.charAt(0));
                if (ch == 'o') checkArgumentNumber(line);
                else if (ch == 'n') getNumberedImages(line);
                else if (ch == 's') getStripImages(line);
                else if (ch == 'g') getGroupImages(line);
                else System.out.println("Do not recognize line: " + line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    // --------- load a single image -------------------------------

    private void checkArgumentNumber(String line) {
        StringTokenizer tokens = new StringTokenizer(line);

        if (tokens.countTokens() != 2) System.out.println("Wrong no. of arguments for " + line);
        else {
            tokens.nextToken();
            System.out.print("o Line: ");
            loadSingleImage(tokens.nextToken());
        }
    }

    private boolean loadSingleImage(String filename) {
        String name = getPrefix(filename);

        if (imagesMap.containsKey(name)) {
            System.out.println( "Error: " + name + " already used");
            return false;
        }

        BufferedImage bufferedImage = loadImage(filename);
        if (bufferedImage != null) {
            var imagesList = new ArrayList<BufferedImage>();
            imagesList.add(bufferedImage);
            imagesMap.put(name, imagesList);
            System.out.println("  Stored " + name + "/" + filename);
            return true;
        } else return false;
    }

    private String getPrefix(String filename) {
        int dotPosition;
        if ((dotPosition = filename.lastIndexOf('.')) == -1){
            System.out.println("No prefix found for filename: " + filename);
            return filename;
        } else return filename.substring(0, dotPosition);
    }

    // --------- load numbered images -------------------------------

    private void getNumberedImages(String line) {}

    // --------- load image strip -------------------------------

    private void getStripImages(String line) {
        StringTokenizer tokens = new StringTokenizer(line);

        if (tokens.countTokens() != 3) System.out.println("Wrong no. of arguments for " + line);
        else {
            tokens.nextToken();
            System.out.print("s Line: ");

            String filename = tokens.nextToken();
            int number = Integer.parseInt(tokens.nextToken());
            loadStripImages(filename, number);
        }
    }

    private int loadStripImages(String filename, int number) {
        String name = getPrefix(filename);

        if (imagesMap.containsKey(name)) {
            System.out.println( "Error: " + name + " already used");
            return 0;
        }

        BufferedImage[] strip = loadStripImageArray(filename, number);
        if (strip == null) return 0;

        ArrayList imagesList = new ArrayList();
        int loadCount = 0;
        System.out.println("  Adding " + name + "/" + filename + "... ");
        for (int i = 0; i < strip.length; i++) {
        	imagesList.add(strip[i]);
        	loadCount++;
        }
        
        if (loadCount == 0) System.out.println("No images loaded for " + name);
        else imagesMap.put(name, imagesList);
        	
        return loadCount;
    }


    

	// ------ grouped filename seq. of images ---------

    private void getGroupImages(String line) {}



    // ------------------- Image Input ------------------

    public static BufferedImage loadImage(String filename) {
        String pathname = IMAGE_DIR + filename;

        try {
            BufferedImage bufferedImage = ImageIO.read(new File(pathname));
            return bufferedImage;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    private BufferedImage[] loadStripImageArray(String filename, int number) {
		if (number <= 0) {
			System.out.println("number <= 0; returning null");
			return null;
		}

		BufferedImage stripImage;
		if ((stripImage = loadImage(filename)) == null) {
			System.out.println("Returning null");
		    return null;
		}
		
		int subImageWidth = stripImage.getWidth() / ((number * 2) - 1); // Include space interval
		int subImageHeight = stripImage.getHeight();
		var strip = new BufferedImage[number];
		for (int i = 0, index = 0; index < number; i += 2, index++) { // Skip space interval
			strip[index] = stripImage.getSubimage(i * subImageWidth, 0,
												  subImageWidth, subImageHeight);
		}
		return strip;
    }


    // ------------------ access methods -------------------

    public BufferedImage getImage(String imageName) {
    	ArrayList imagesList = (ArrayList) imagesMap.get(imageName);
        if (imagesList == null) {
            System.out.println("No image(s) stored under " + imageName);
            return null;
        }

        return (BufferedImage) imagesList.get(0);
    }

    public BufferedImage getImage(String imageName, int position) {
    	ArrayList imagesList = imagesMap.get(imageName);
        if (imagesList == null) {
            System.out.println("No image(s) stored under " + imageName);
            return null;
        }

        int imagesListSize = imagesList.size();
        if (position < 0) return (BufferedImage) imagesList.get(0);
        else if (position >= imagesListSize) {
            int newPosition = position % imagesListSize;
            return (BufferedImage) imagesList.get(newPosition);
        }

        return (BufferedImage) imagesList.get(position);
    }

    public int numImages(String imageName) {
    	ArrayList imagesList = (ArrayList) imagesMap.get(imageName);
        if (imagesList == null) {
            System.out.println("No image(s) stored under " + imageName);
            return 0;
        }

        return imagesList.size();
    }

    public boolean isLoaded(String imageName) {
        ArrayList imagesList = imagesMap.get(imageName);
        if (imagesList == null) return false;
        else return true;
    }

    public int[][] getMapRedPixelValue(String imageName) {
        BufferedImage image = imagesMap.get(imageName).get(0);
        if (image == null) {
            System.out.println("No image(s) stored under " + imageName);
            return null;
        }

        int[][] map = new int[MAP_HEIGHT][MAP_WIDTH];
        for (int i = 0; i < MAP_HEIGHT; i++) {
            for (int j = 0; j < MAP_WIDTH; j++) {
                Color color = new Color(image.getRGB(j, i));
                map[i][j] = color.getRed();
            }
        }

        return map;
    }
}
