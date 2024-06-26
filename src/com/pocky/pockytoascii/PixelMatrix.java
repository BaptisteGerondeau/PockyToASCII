package com.pocky.pockytoascii;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PixelMatrix {
    private String imagePath;
    private int width;
    private int height;
    private File fimage;
    private BufferedImage image;

    private int[] rawPixels;

    private int[][][] rgbMatrix;

    public enum BRIGHTNESS_MODE {
        AVERAGE,
        LIGHTNESS,
        LUMINOSITY
    }

    public PixelMatrix(String imagePath, int dwidth, int dheight) {
        if (imagePath == null || imagePath.isEmpty()) {
            throw new IllegalArgumentException("Image path cannot be empty");
        }
        if (dwidth == 0 || dheight == 0) {
            throw new IllegalArgumentException("Dimensions cannot be zero");
        }

        this.imagePath = imagePath;
        this.fimage = new File(imagePath);
        this.image = null;
        try {
            image = ImageIO.read(fimage);
            System.out.print("Successfully loaded image!\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ImageIcon imageIcon = new ImageIcon(image);
        Image scaledImage = imageIcon.getImage().getScaledInstance(dwidth, dheight, Image.SCALE_DEFAULT);


        BufferedImage outputImage = new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null), BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(scaledImage, 0, 0, null);
        image = outputImage;

        width = image.getWidth();
        height = image.getHeight();

        rawPixels = image.getRGB(0,0, width, height, null, 0, width);

        rgbMatrix = null;
    }

    @Override
    public String toString() {
        return "com.pocky.pockytoascii.PixelMatrix imagePath=" + imagePath + ", width=" + width + ", height=" + height;
    }

    public int[][][] getRgbMatrix() {
        if (rgbMatrix == null) {
            this.populateMatrix();
        }
        return rgbMatrix;
    }

    public int[][] getANSIColourMatrix() {
        if (rgbMatrix == null) {
            this.populateMatrix();
        }

        int[][] ANSIMatrix = new int[height][width];
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                ANSIMatrix[x][y] = ConsoleColours.RGBtoANSI(rgbMatrix[x][y][0], rgbMatrix[x][y][1], rgbMatrix[x][y][2]).ordinal();
            }
        }
        return ANSIMatrix;
    }

    public int[][] getBrightnessMatrix(BRIGHTNESS_MODE mode) {
        if (rgbMatrix == null) {
            this.populateMatrix();
        }

        int[][] brightnessMatrix = new int[height][width];
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                switch (mode) {
                    case AVERAGE:
                        brightnessMatrix[x][y] = (rgbMatrix[x][y][0] + rgbMatrix[x][y][1] + rgbMatrix[x][y][2]) / 3;
                        break;
                    case LIGHTNESS:
                        int max = Math.max(rgbMatrix[x][y][0], Math.max(rgbMatrix[x][y][1], rgbMatrix[x][y][2]));
                        int min = Math.min(rgbMatrix[x][y][0], Math.min(rgbMatrix[x][y][1], rgbMatrix[x][y][2]));
                        brightnessMatrix[x][y] = (max + min) / 2;
                        break;
                    case LUMINOSITY:
                        brightnessMatrix[x][y] = (int) (0.21 * rgbMatrix[x][y][0] + 0.72 * rgbMatrix[x][y][1] + 0.07 * rgbMatrix[x][y][2]);
                        break;
                    default:
                        brightnessMatrix[x][y] = 0;
                }
            }
        }

        return brightnessMatrix;
    }

    private void populateMatrix() {
        rgbMatrix = new int[height][width][3];

        int i = 0;
        for (int x = 0; x < this.height; x++) {
            for (int y = 0; y < this.width; y++) {
                Color c = new Color(rawPixels[i++]);
                rgbMatrix[x][y][0] = c.getRed();
                rgbMatrix[x][y][1] = c.getGreen();
                rgbMatrix[x][y][2] = c.getBlue();
            }
        }
    }
}
