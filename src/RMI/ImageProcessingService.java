package RMI;

import java.awt.image.BufferedImage;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Admin on 20.11.2016.
 */
public interface ImageProcessingService extends Remote{

    int[] smooth(int[] pixelsSet, int w, int h) throws RemoteException;
}
