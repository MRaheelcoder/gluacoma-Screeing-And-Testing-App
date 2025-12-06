package app;
// ImageLoader.java
import javax.swing.*;
import java.awt.*;
import java.io.InputStream;

public class ImageLoader {

    public static ImageIcon loadImage(String imagePath, int width, int height) {
        try {
            // Try to load from file system first
            ImageIcon originalIcon = new ImageIcon(imagePath);
            if (originalIcon.getIconWidth() > 0) {
                Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImage);
            }
        } catch (Exception e) {
            System.err.println("Failed to load image from file system: " + imagePath);
        }

        try {
            // Try to load from classpath/resources
            InputStream inputStream = ImageLoader.class.getClassLoader().getResourceAsStream(imagePath);
            if (inputStream != null) {
                ImageIcon originalIcon = new ImageIcon(inputStream.readAllBytes());
                Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImage);
            }
        } catch (Exception e) {
            System.err.println("Failed to load image from classpath: " + imagePath);
        }

        // Return null if both methods fail
        return null;
    }
}