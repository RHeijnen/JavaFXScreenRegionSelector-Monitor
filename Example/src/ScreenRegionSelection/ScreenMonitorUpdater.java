package ScreenRegionSelection;

import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.TimerTask;

/**
 * Created by Indi on 6/29/2016.
 */
public class ScreenMonitorUpdater extends TimerTask {
    boolean runnable = true;
    ImageView IV;
    int selectionPixelSize;
    Rectangle rectangleScreenSelection;
    Color[] colors;
    Robot robot;
    BufferedImage bf;
    WritableImage wi;
    Color[] initialSnapShot;
    ScreenRegionSelector SRC;
    public  ScreenMonitorUpdater(ScreenRegionSelector SRC, Color[] initialSnapShot, ImageView IV, int selectionPixelSize, Rectangle rectangleScreenSelection){
        this.IV = IV; // makes sure we both know what imageview we talkin' bout
        this.selectionPixelSize = selectionPixelSize; // gets total pixel size
        this.rectangleScreenSelection = rectangleScreenSelection; // gets the selection info
        this.initialSnapShot = initialSnapShot; // get initial pixel data for comparison
        this.SRC = SRC;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        if(runnable){
            runnable = false; // guard
            colors = new Color[selectionPixelSize];
            bf = robot.createScreenCapture(rectangleScreenSelection);
            wi = new WritableImage(bf.getWidth(), bf.getHeight());
            PixelWriter pw = wi.getPixelWriter();
            int changecounter = 0;
            int i = 0;
            for (int x = 0; x < bf.getWidth(); x++) {
                for (int y = 0; y < bf.getHeight(); y++) {
                    pw.setArgb(x, y, bf.getRGB(x, y)); // write pixels to x y location with RGB value of the buf.img
                    colors[i] = new Color(bf.getRGB(x, y));
                    if(colors[i].equals(initialSnapShot[i])){
                        // do nothing
                    }else{
                        changecounter++;
                    }
                    i++;
                }
            }
            SRC.setSelectionRefreshPixels(colors);
            SRC.setSelectionPixelDiffrence(changecounter);
            //

            IV.setImage(wi);
            runnable = true; // guard
        }
    }
}

  /*
        No need to Save file at this time.
        File f = new File("C:\\Users\\**************\\Desktop\\MyFile.png");

        try {
            ImageIO.write(bf, "PNG", f);
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }
   */
