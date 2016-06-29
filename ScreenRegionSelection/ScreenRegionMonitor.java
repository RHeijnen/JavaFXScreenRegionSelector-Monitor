package ScreenRegionSelection;

import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.util.Timer;

/**
 * Created by Indi on 6/29/2016.
 */
public class ScreenRegionMonitor {
    Stage stage;
    Robot robot;
    ScreenRegionSelector SRC;
    ImageView IV;
    Timer timer;
    public ScreenRegionMonitor(ScreenRegionSelector SRC) {
        this.SRC = SRC;
        System.out.println();

    }


    public void createMonitor() throws AWTException {
        stage = new Stage();

        stage.initStyle(StageStyle.DECORATED);
        IV = new ImageView();
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(IV);
        stackPane.setStyle("-fx-background-color: #c0c9ff");
        Scene scene;
        // if selection is smaller than 150 make custom window
        if (SRC.getSelectionWidth() < 150) {
            scene = new Scene(stackPane, 150, 150);
        } else {
            scene = new Scene(stackPane, SRC.getSelectionWidth() + 25, SRC.getSelectionHeight() + 25);
        }
        IV.setFitHeight(SRC.getSelectionHeight());
        IV.setFitWidth(SRC.getSelectionWidth());

        scene.setFill(null);
        stage.setScene(scene);
        stage.show();
        Draw();
    }



    public void Draw() {
        attachShutDownHook();
        new Thread() {
            public void run() {
                timer = new Timer();
                timer.schedule(new ScreenMonitorUpdater(SRC,
                        SRC.getSelectionPixels(),
                        IV,
                        SRC.getSelectionPixelSize(),
                        SRC.getRectangleScreenSelection()), 0, 10);
            }
        }.start();
    }

    // shut down hook for the timer works on System.exit(0);
    public void attachShutDownHook() {
        SRC.setScannerIsLive(true);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                SRC.setScannerIsLive(false);
                timer.cancel();
            }
        });


    }
}


