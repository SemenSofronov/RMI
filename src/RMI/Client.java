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
        BufferedImage sourceImage = ImageIO.read(new File(argv[0]));
        int w = sourceImage.getWidth(), h = sourceImage.getHeight();
        int[] ii = new int[w*h];
        int[] pixelsSet = sourceImage.getRGB(0,0,w,h,ii,0,w);
        Registry registry = LocateRegistry.getRegistry("localhost", 2099);
        ImageProcessingService service = (ImageProcessingService) registry.lookup("Lab2");
        int[] response = service.smooth(pixelsSet,w,h);
        BufferedImage resultImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        resultImage.setRGB(0,0,w,h,response,0,w);
        ImageIO.write(resultImage, "JPG", new File(argv[1]));
    }
}
