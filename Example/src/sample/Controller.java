package sample;

import ScreenRegionSelection.ScreenRegionSelector;
import javafx.event.ActionEvent;

import java.awt.*;

public class Controller {
    ScreenRegionSelector SRS;

    public void openScreenRegionSelection(ActionEvent actionEvent) {
        SRS = new ScreenRegionSelector();
    }

    public void openMonitor(ActionEvent actionEvent) throws AWTException {
        SRS.createScreenMonitor();
    }

    public void otherStuff(){
     // SRS.setScannerIsLive(); // do not use
        SRS.getStartPositionX(); // start X position of the selection
        SRS.getStartPositionY(); // start Y position of the selection
        SRS.getEndPositionX(); // end X position of the selection
        SRS.getEndPositionY(); // end Y postion of the selection
        SRS.getSelectionPixelSize(); // returns the number of pixels in the selected area
        SRS.getSelectionPixels(); // returns a pixel array of the selected area
        SRS.getSelectionWidth(); // returns the width of the selected area
        SRS.getSelectionHeight(); // returns the height of the selected area
        SRS.getRectangleScreenSelection(); // returns a rectangle of the selected area
        // If a monitor is live, you can check the ammount of pixels that differ from the original position and call :
        SRS.getSelectionRefreshPixels(); // get the refreshed pixel array
        SRS.getSelectionPixelDiffrence(); // returns the number of diffrentiating pixels



    }
}
