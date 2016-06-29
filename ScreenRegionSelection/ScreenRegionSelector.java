package ScreenRegionSelection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.awt.MouseInfo.getPointerInfo;

/**
 * Created by Indi on 6/28/2016.
 */
public class ScreenRegionSelector {
    private int drawingRectangleOffset = 13;
    private int startPositionX;
    private int startPositionY;
    private int endPositionX;
    private int endPositionY;
    private int selectionWidth;
    private int selectionHeight;
    private int selectionPixelSize;
    private int selectionPixelDiffrence;
    private Color[] selectionPixels;
    private Color[] selectionRefreshPixels;
    private Rectangle rectangleScreenSelection;
    private Canvas canvas;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private double Screen_width = screenSize.getWidth();
    private double Screen_height = screenSize.getHeight();
    private GraphicsContext gc;
    private Stage stage;
    private boolean scannerIsLive = false;
    Boolean isReady;
    Robot robot;
    ScreenRegionSelector SRS;

    public void createScreenMonitor() throws AWTException {

        ScreenRegionMonitor SRM = new ScreenRegionMonitor(SRS);
        SRM.createMonitor();

    }

    
    public ScreenRegionSelector() {
        this.SRS = this;
        isReady = false;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

        createSelectionCanvas();


    }
    
    public void createSelectionCanvas(){
        stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        canvas = new Canvas(Screen_width, Screen_height);
        canvas.setStyle("-fx-background-color: rgba(255, 255, 255, 0.1);" +
                "-fx-effect: dropshadow(gaussian, white, 50, 0, 0, 0);" +
                "-fx-background-insets: 0;"
        );

        StackPane stackPane = new StackPane();
        stackPane.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.1);" +
                        "-fx-effect: dropshadow(gaussian, white, 50, 0, 0, 0);" +
                        "-fx-background-insets: 0;"
        );

        gc = canvas.getGraphicsContext2D();
        canvas.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                startPositionX = (int) getPointerInfo().getLocation().getX();
                startPositionY = (int) getPointerInfo().getLocation().getY();
            }
        });
        canvas.setOnMouseReleased(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                endPositionX = (int) getPointerInfo().getLocation().getX();
                endPositionY = (int) getPointerInfo().getLocation().getY();
                selectionWidth = getEndPositionX() - getStartPositionX();
                selectionHeight = getEndPositionY() - getStartPositionY();
                selectionPixelSize = selectionWidth * selectionHeight;
                rectangleScreenSelection = new Rectangle(
                        startPositionX, startPositionY,
                        selectionWidth,
                        selectionHeight);
                //  gc.fillRect(event.getSceneX(),event.getScreenY()+10,4,4);
                javafx.scene.paint.Color c = new javafx.scene.paint.Color(0, 0, 1.0, 0.3);

                gc.setFill(c);
                gc.fillRect(
                        getStartPositionX(),
                        getStartPositionY() + drawingRectangleOffset, // 13 needed for offset for some reason
                        getSelectionWidth(),
                        getSelectionHeight());

                confirmationPopUp popup = new confirmationPopUp(stage);
                popup.createWindow();
            }
        });


        stackPane.getChildren().add(canvas);
        Scene scene = new Scene(stackPane, Screen_width, Screen_height);
        scene.setFill(null);
        stage.setScene(scene);
        stage.show();

    }

    public int getStartPositionX() {
        return startPositionX;
    }

    public int getStartPositionY() {
        return startPositionY;
    }

    public int getEndPositionX() {
        return endPositionX;
    }

    public int getEndPositionY() {
        return endPositionY;
    }

    public int getSelectionWidth() {
        return selectionWidth;
    }

    public int getSelectionHeight() {
        return selectionHeight;
    }

    public int getSelectionPixelSize() {
        return selectionPixelSize;
    }

    public Rectangle getRectangleScreenSelection() {
        return rectangleScreenSelection;
    }

    public int getSelectionPixelDiffrence() {
        if(scannerIsLive){
            return selectionPixelDiffrence;
        }
        System.out.println("WARNING: Active Monitor is not running, try running - createScreenMonitor() "+"\n\r"+"Before trying to calculate pixel diffrences, you need to check for new pixels!");
        return selectionPixelDiffrence;
    }

    public void setSelectionPixelDiffrence(int selectionPixelDiffrence) {
        this.selectionPixelDiffrence = selectionPixelDiffrence;
    }

    public void scanInitialPixelData() {

        BufferedImage BI = robot.createScreenCapture(getRectangleScreenSelection());
        setSelectionPixels(new Color[getSelectionPixelSize()]);

        int i = 0;
        for (int x = 0; x < BI.getWidth(); x++) {
            for (int y = 0; y < BI.getHeight(); y++) {
                getSelectionPixels()[i] = new Color(BI.getRGB(x, y));
                i++;

            }
        }
    }

    public Color[] getSelectionPixels() {
        return selectionPixels;
    }

    public void setSelectionPixels(Color[] selectionPixels) {
        this.selectionPixels = selectionPixels;
    }

    public Color[] getSelectionRefreshPixels() {
        return selectionRefreshPixels;
    }

    public void setSelectionRefreshPixels(Color[] selectionRefreshPixels) {
        this.selectionRefreshPixels = selectionRefreshPixels;
    }

    public boolean isScannerIsLive() {
        return scannerIsLive;
    }

    public void setScannerIsLive(boolean scannerIsLive) {
        this.scannerIsLive = scannerIsLive;
    }

    /*
      Private class for confirmation popup
    */
    private class confirmationPopUp {
        private javafx.scene.control.Button but_agree;
        private javafx.scene.control.Button but_disagree;
        Stage canvasStage;

        public confirmationPopUp(Stage stage) {
            this.canvasStage = stage;
        }

        public void createWindow(){
            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            Pane pane = new Pane();
            pane.setStyle(
                    "-fx-background-color: rgba(201, 159, 145, 0.6);" +
                            "-fx-effect: dropshadow(gaussian, white, 50, 0, 0, 0);" +
                            "-fx-background-insets: 0;"
            );


            but_disagree = new javafx.scene.control.Button();
            but_disagree.setLayoutX(25);
            but_disagree.setLayoutY(10);
            but_disagree.setText("Retry");
            but_disagree.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event)  {
                    stage.hide();
                    canvasStage.hide();
                    ScreenRegionSelector reset = new ScreenRegionSelector();
                }
            });
            but_agree = new javafx.scene.control.Button();
            but_agree.setLayoutX(75);
            but_agree.setLayoutY(10);
            but_agree.setText("Accept");
            but_agree.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event)  {
                    stage.hide();
                    canvasStage.hide();
                    scanInitialPixelData();

                }
            });

            pane.getChildren().add(but_disagree);
            pane.getChildren().add(but_agree);

            Scene scene = new Scene(pane, 150,50 );
            scene.setFill(null);
            stage.setScene(scene);
            stage.show();
        }
    }
    // ^ end innerclass v end screenRegionSelector class
}