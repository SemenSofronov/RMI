package RMI;

import java.awt.image.BufferedImage;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Admin on 20.11.2016.
 */
public class Server implements ImageProcessingService {
    @Override
    public int[] smooth(int[] pixelsSet, int width, int height) throws RemoteException {

        double[][] mask = new double[][]{{1./16.,1./8.,1./16.},
                                         {1./8.,1./4.,1./8.},
                                         {1./16.,1./8.,1./16.}
        };

        BufferedImage sourceImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        sourceImage.setRGB(0,0,width,height,pixelsSet,0,width);
        BufferedImage resultImage = sourceImage;
        int[] pixNew = new int[3], pixOld = new int[3];
        int[] ii = new int[]{0,0,0};
        for (int m1 = 3; m1 < sourceImage.getWidth(); m1++) {
            for (int m2 = 3; m2 < sourceImage.getHeight(); m2++) {
                for (int n1 = 0; n1 < 3; n1++) {
                    for (int n2 = 0; n2 < 3; n2++) {
                        pixOld[0] = sourceImage.getRaster().getPixel(m1 - n1, m2 - n2, ii)[0];
                        pixOld[1] = sourceImage.getRaster().getPixel(m1 - n1, m2 - n2, ii)[1];
                        pixOld[2] = sourceImage.getRaster().getPixel(m1 - n1, m2 - n2, ii)[2];
                        pixNew[0] += mask[n1][n2] * pixOld[0];
                        pixNew[1] += mask[n1][n2] * pixOld[1];
                        pixNew[2] += mask[n1][n2] * pixOld[2];
                    }
                }
                resultImage.getRaster().setPixel(m1, m2, pixNew);
                pixNew[0]=0;
                pixNew[1]=0;
                pixNew[2]=0;
            }
        }

        return resultImage.getRGB(0,0,width,height,new int[width*height],0,width);
    }

    public static void main(String[] argv) throws Exception {
        final Registry registry = LocateRegistry.createRegistry(2099);
        final ImageProcessingService service = new Server();
        Remote stub = UnicastRemoteObject.exportObject(service, 0);

        System.out.println("Server is ready");
        registry.bind("Lab2",stub);
        while (true) {

        }

    }
}
