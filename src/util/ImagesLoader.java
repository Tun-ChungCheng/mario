package util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.StringTokenizer;



public class ImagesLoader {
    private static final String IMAGE_DIR = "/images/";

    private HashMap<String, ArrayList<BufferedImage>> imagesMap;
    

    public ImagesLoader(String imagesInfo) {
        initLoader();
        loadImagesFile(imagesInfo);
    }

    public ImagesLoader() {
        initLoader();
    }

    private void initLoader() {
        imagesMap = new HashMap<>();
    }

    private void loadImagesFile(String imagesInfo) {
        String pathFromContentRoot = IMAGE_DIR + imagesInfo;
        System.out.println("Reading file: " + pathFromContentRoot);
        try {
            InputStream inputStream = this.getClass().getResourceAsStream(pathFromContentRoot);
            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(Objects.requireNonNull(inputStream)));

            String line;
            char ch;
            while((line = bufferedReader.readLine()) != null) {
                if (line.length() == 0) continue;
                if (line.startsWith("//")) continue;

                ch = Character.toLowerCase(line.charAt(0));
                if (ch == 'o') checkIfTwoArguments(line);
                else if (ch == 's') checkIfThreeArguments(line);
                else System.out.println("Do not recognize line: " + line);
            }

            inputStream.close();
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    // --------- load a single image -------------------------------

    private void checkIfTwoArguments(String line) {
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

    // --------- load image strip -------------------------------

    private void checkIfThreeArguments(String line) {
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

        ArrayList<BufferedImage> imagesList = new ArrayList<>();
        int loadCount = 0;
        System.out.println("  Adding " + name + "/" + filename + "... ");
        for (BufferedImage bufferedImage : strip) {
            imagesList.add(bufferedImage);
            loadCount++;
        }
        
        if (loadCount == 0) System.out.println("No images loaded for " + name);
        else imagesMap.put(name, imagesList);
        	
        return loadCount;
    }

    // ------------------- Image Input ------------------

    public BufferedImage loadImage(String filename) {
        String pathFromContentRoot = IMAGE_DIR + filename;
        try {
            return ImageIO.read(Objects.requireNonNull(getClass().getResource(pathFromContentRoot)));
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
    	ArrayList<BufferedImage> imagesList = imagesMap.get(imageName);
        if (imagesList == null) {
            System.out.println("No image(s) stored under " + imageName);
            return null;
        }

        return imagesList.get(0);
    }

    public BufferedImage getImage(String imageName, int position) {
    	ArrayList<BufferedImage> imagesList = imagesMap.get(imageName);
        if (imagesList == null) {
            System.out.println("No image(s) stored under " + imageName);
            return null;
        }

        int imagesListSize = imagesList.size();
        if (position < 0) return imagesList.get(0);
        else if (position >= imagesListSize) {
            int newPosition = position % imagesListSize;
            return imagesList.get(newPosition);
        }

        return imagesList.get(position);
    }

    public int numImages(String imageName) {
    	ArrayList<BufferedImage> imagesList = imagesMap.get(imageName);
        if (imagesList == null) {
            System.out.println("No image(s) stored under " + imageName);
            return 0;
        }

        return imagesList.size();
    }
}
