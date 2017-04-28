package com.SER216.BattleShip;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.Stack;

/**
 * Created by zawata on 4/12/2017.
 */
public class Util {
    //width of the board
    public static final int boardWidth = 10;

    // resource location
    public static final String resources = "src/resources/";

    // Returns the value of the game coordinates given pixels of the click
    public static int getGrid(int x) {
        // integer division in java is, by implementation, to the floor.
        int retval = x / 45;
        if(retval > 0 && retval <= 10) {
            return retval;
        }
        else {
            return 0;
        }
    }

    public static Stack reverse(Stack stack) {
        Stack temp = (Stack)stack.clone();
        Collections.reverse(temp);
        return temp;
    }

    public static Image rotate(Image img, double angle)
    {
        double sin = Math.abs(Math.sin(Math.toRadians(angle))),
                cos = Math.abs(Math.cos(Math.toRadians(angle)));

        int w = img.getWidth(null), h = img.getHeight(null);

        int neww = (int) Math.floor(w*cos + h*sin),
                newh = (int) Math.floor(h*cos + w*sin);

        BufferedImage bimg = toBufferedImage(getEmptyImage(neww, newh));
        Graphics2D g = bimg.createGraphics();

        g.translate((neww-w)/2, (newh-h)/2);
        g.rotate(Math.toRadians(angle), w/2, h/2);
        g.drawRenderedImage(toBufferedImage(img), null);
        g.dispose();

        return toImage(bimg);
    }

    private static BufferedImage toBufferedImage(Image img)
    {
        if (img instanceof BufferedImage)
        {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }

    private static Image getEmptyImage(int width, int height){
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        return toImage(img);
    }

    private static Image toImage(BufferedImage bimage){
        // Casting is enough to convert from BufferedImage to Image
        Image img = (Image) bimage;
        return img;
    }
}
