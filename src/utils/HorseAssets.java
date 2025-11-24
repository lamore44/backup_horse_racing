package utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class HorseAssets {
    
    private static BufferedImage horseBaseImage = null;
    
    // Load GIF image (will use first frame if animated)
    private static BufferedImage loadHorseImage() {
        if (horseBaseImage != null) {
            return horseBaseImage;
        }
        
        try {
            // Try to load from assets folder
            File gifFile = new File("assets/horse.gif");
            if (gifFile.exists()) {
                horseBaseImage = ImageIO.read(gifFile);
                System.out.println("Horse GIF loaded successfully from assets/horse.gif");
            } else {
                System.out.println("Warning: assets/horse.gif not found, using fallback");
                // Fallback: create simple horse image
                horseBaseImage = createFallbackHorseImage();
            }
        } catch (IOException e) {
            System.err.println("Error loading horse image: " + e.getMessage());
            horseBaseImage = createFallbackHorseImage();
        }
        
        return horseBaseImage;
    }
    
    // Create horse image with color tinting
    public static BufferedImage createHorseImage(Color color, int width, int height) {
        BufferedImage baseImage = loadHorseImage();
        
        // Scale image to desired size
        BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = scaledImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw scaled base image
        g2d.drawImage(baseImage, 0, 0, width, height, null);
        g2d.dispose();
        
        // Apply color tint
        BufferedImage tintedImage = applyColorTint(scaledImage, color);
        
        return tintedImage;
    }
    
    // Apply color tint to image
    private static BufferedImage applyColorTint(BufferedImage image, Color tintColor) {
        BufferedImage tinted = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int pixel = image.getRGB(x, y);
                
                // Get alpha channel
                int alpha = (pixel >> 24) & 0xff;
                
                // Skip transparent pixels
                if (alpha == 0) {
                    tinted.setRGB(x, y, pixel);
                    continue;
                }
                
                // Get RGB values
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = pixel & 0xff;
                
                // Calculate grayscale (for better tinting)
                int gray = (red + green + blue) / 3;
                
                // Apply tint color
                int newRed = (gray * tintColor.getRed()) / 255;
                int newGreen = (gray * tintColor.getGreen()) / 255;
                int newBlue = (gray * tintColor.getBlue()) / 255;
                
                // Combine with original for better detail
                newRed = (newRed + red) / 2;
                newGreen = (newGreen + green) / 2;
                newBlue = (newBlue + blue) / 2;
                
                // Clamp values
                newRed = Math.min(255, Math.max(0, newRed));
                newGreen = Math.min(255, Math.max(0, newGreen));
                newBlue = Math.min(255, Math.max(0, newBlue));
                
                // Set new pixel
                int newPixel = (alpha << 24) | (newRed << 16) | (newGreen << 8) | newBlue;
                tinted.setRGB(x, y, newPixel);
            }
        }
        
        return tinted;
    }
    
    // Fallback: create simple horse image if GIF not found
    private static BufferedImage createFallbackHorseImage() {
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw simple horse silhouette in black (will be tinted later)
        g2d.setColor(Color.BLACK);
        
        // Body
        g2d.fillOval(20, 30, 60, 40);
        
        // Head
        g2d.fillOval(65, 35, 30, 30);
        
        // Neck
        int[] neckX = {50, 65, 65, 45};
        int[] neckY = {35, 35, 45, 45};
        g2d.fillPolygon(neckX, neckY, 4);
        
        // Legs
        g2d.setStroke(new BasicStroke(8));
        g2d.drawLine(30, 70, 30, 95);  // Front left leg
        g2d.drawLine(45, 70, 45, 95);  // Front right leg
        g2d.drawLine(55, 70, 55, 95);  // Back left leg
        g2d.drawLine(70, 70, 70, 95);  // Back right leg
        
        // Tail
        g2d.setStroke(new BasicStroke(6));
        int[] tailX = {25, 15, 10};
        int[] tailY = {55, 65, 75};
        g2d.drawPolyline(tailX, tailY, 3);
        
        // Mane
        g2d.setStroke(new BasicStroke(4));
        g2d.drawArc(55, 25, 20, 25, 30, 120);
        
        // Ear
        int[] earX = {75, 80, 85};
        int[] earY = {35, 25, 35};
        g2d.fillPolygon(earX, earY, 3);
        
        g2d.dispose();
        return image;
    }
    
    public static BufferedImage createFinishLineImage(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        
        int squareSize = 20;
        for (int y = 0; y < height; y += squareSize) {
            for (int x = 0; x < width; x += squareSize) {
                if ((x / squareSize + y / squareSize) % 2 == 0) {
                    g2d.setColor(Color.BLACK);
                } else {
                    g2d.setColor(Color.WHITE);
                }
                g2d.fillRect(x, y, squareSize, squareSize);
            }
        }
        
        g2d.dispose();
        return image;
    }
    
    public static BufferedImage createTrophyImage(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Trophy cup
        g2d.setColor(new Color(255, 215, 0));
        int[] xPoints = {width/4, width*3/4, width*2/3, width/3};
        int[] yPoints = {height/3, height/3, height*2/3, height*2/3};
        g2d.fillPolygon(xPoints, yPoints, 4);
        
        // Trophy base
        g2d.fillRect(width/3, height*2/3, width/3, height/6);
        g2d.fillRect(width/4, height*5/6, width/2, height/12);
        
        // Trophy handles
        g2d.setStroke(new BasicStroke(3));
        g2d.drawArc(5, height/3, width/5, height/4, 90, 180);
        g2d.drawArc(width*3/4, height/3, width/5, height/4, -90, 180);
        
        g2d.dispose();
        return image;
    }
}
