package RMI;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by Admin on 20.11.2016.
 */
public class Client {

    public static void main(String[] argv) throws Exception{
        try {
            BufferedImage sourceImage = ImageIO.read(new File(argv[0]));
            int width = sourceImage.getWidth(), height = sourceImage.getHeight();
            int[] size = new int[width * height];
            int[] pixelsSet = sourceImage.getRGB(0, 0, width, height, size, 0, width);
            Registry registry = LocateRegistry.getRegistry("localhost", 2099);
            ImageProcessingService service = (ImageProcessingService) registry.lookup("Lab2");
            int[] response = service.smooth(pixelsSet, width, height);
            BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            resultImage.setRGB(0, 0, width, height, response, 0, width);
            ImageIO.write(resultImage, "png", new File(argv[1]));
            System.out.println("Mission completed!");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
