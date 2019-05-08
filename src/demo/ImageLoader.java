package demo;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;


public class ImageLoader {

    public static ImageLoader active = new ImageLoader(null);
    private Map<String, BufferedImage> cachedImages;
    private final int MAX_IMAGE_CACHE_SIZE = 100;
    private Canvas canvas;

    public ImageLoader(Canvas canvas) {
        this.canvas = canvas;
        this.cachedImages = new HashMap<>();
        active = this;
    }

    public BufferedImage fetchImage(String imagePath) {

        if (cachedImages.get(imagePath) != null) {
            return cachedImages.get(imagePath);
        }

        try {

            BufferedImage image = ImageIO.read(Paths.get(imagePath).toUri().toURL());
            image = makeColorTransparent(image, Color.WHITE);
            GraphicsConfiguration graphConfig = this.canvas.getGraphicsConfiguration();
            BufferedImage formatted = graphConfig.createCompatibleImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

            formatted.getGraphics().drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);

            if(cachedImages.size() > MAX_IMAGE_CACHE_SIZE) {
                cachedImages.clear();
            }
            cachedImages.put(imagePath, formatted);

            return formatted;

        } catch (Exception e) {
            System.err.println("Error while loading image");
            e.printStackTrace();
            return null;
        }
    }

    public static BufferedImage makeColorTransparent(BufferedImage im, final Color color) {
        ImageFilter filter = new RGBImageFilter() {
            public int markerRGB = color.getRGB() | 0xFF000000;
            public final int filterRGB(int x, int y, int rgb) {
                if ((rgb | 0xFF000000) == markerRGB) {
                    return 0x00FFFFFF & rgb;
                } else {
                    return rgb;
                }
            }
        };
        ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
        Image resultImage = Toolkit.getDefaultToolkit().createImage(ip);
        BufferedImage bufferedImage = new BufferedImage(im.getWidth(null), im.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bufferedImage.createGraphics();
        g2.drawImage(resultImage, 0, 0, null);
        g2.dispose();
        return bufferedImage;
    }

    public void flipImageHorizontally(BufferedImage image) {
        for (int i = 0; i < image.getWidth() / 2; i++)
            for (int j = 0; j < image.getHeight(); j++) {
                int tmp = image.getRGB(i, j);
                image.setRGB(i, j, image.getRGB(image.getWidth() - i - 1, j));
                image.setRGB(image.getWidth() - i - 1, j, tmp);
            }
    }

}
