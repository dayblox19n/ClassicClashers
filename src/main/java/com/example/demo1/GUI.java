package com.example.demo1;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.awt.*;
import java.io.FileInputStream;

import static com.example.demo1.ClashersMain.isMax;


public class GUI {
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final double width = screenSize.getWidth();
    private final double height = screenSize.getHeight();
    private double cw, ch, scalar;
    private final FadeTransition iconFade = new FadeTransition(Duration.millis(1000));
    private boolean FlashComplete = false;
    private final TranslateTransition iconSlide = new TranslateTransition(Duration.millis(1000));

    public GUI(){
            cw = 1000;
            ch = 800;
            scalar = 1;
    }
    public Region setBaseScreen(Pane root){
        if (isMax){
            cw = width;
            ch = height;
            scalar = width/1000;
        }
        Region background = new Region();
        root.setPrefSize(cw, ch);
        background.setPrefSize(cw, ch);
        background.setStyle("-fx-background-color: rgb(0,0,0)");
        return background;
    }
    public double getScalar() {
        return scalar;
    }
    public double getWidth() {
        return cw;
    }
    public double getHeight() {
        return ch;
    }
    public void flashSetUp(Pane root, javafx.scene.shape.Rectangle iconFlash){

        iconFade.setNode(iconFlash);
        iconFade.setFromValue(1);
        iconFade.setToValue(0);
        iconFade.setCycleCount(1);
        iconFade.setOnFinished(actionEvent -> {
            root.getChildren().remove(iconFlash);
        });
        iconSlide.setNode(iconFlash);
        iconSlide.setFromX(10*scalar);
        iconSlide.setToX(100*scalar);
        iconSlide.setCycleCount(1);
    }
    public void flashStop(){
        iconFade.stop();
        iconSlide.stop();
    }
    public void flashStart(){
        iconFade.playFromStart();
        iconSlide.playFromStart();
    }
    public boolean flashIsFinished() {
        iconFade.setOnFinished(actionEvent -> {
            this.FlashComplete = true;
        });
        return this.FlashComplete;
    }
    public void iconSetUp(Pane root, javafx.scene.shape.Rectangle iconFlash, Rectangle icon, FileInputStream input){
        Image image = new javafx.scene.image.Image(input);
        ImagePattern image_pattern = new ImagePattern(image);
        root.getChildren().remove(icon);
        if (!root.getChildren().contains(iconFlash)) root.getChildren().add(iconFlash);
        root.getChildren().add(icon);
        iconFlash.setFill(image_pattern);
        icon.setFill(image_pattern);
    }
}
