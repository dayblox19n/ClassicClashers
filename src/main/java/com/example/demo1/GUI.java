package com.example.demo1;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
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
    Pane GUI = new Pane();
    Rectangle p1HealthMax = new Rectangle(), p2HealthMax = new Rectangle(), p1HealthCur = new Rectangle(), p2HealthCur = new Rectangle();
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
    public void setGameScreen(Pane root){

        GUI.setPrefSize(cw, ch);
        p1HealthMax.setFill(Color.RED);
        p2HealthMax.setFill(Color.RED);
        p1HealthMax.setX(35*scalar);
        p1HealthMax.setY(65*scalar);
        p1HealthMax.setWidth(400*scalar);
        p1HealthMax.setHeight(25*scalar);
        p2HealthMax.setX(565*scalar);
        p2HealthMax.setY(65*scalar);
        p2HealthMax.setWidth(400*scalar);
        p2HealthMax.setHeight(25*scalar);

        p1HealthCur.setFill(Color.GREEN);
        p2HealthCur.setFill(Color.GREEN);
        p1HealthCur.setX(35*scalar);
        p1HealthCur.setY(65*scalar);
        p1HealthCur.setWidth(400*scalar);
        p1HealthCur.setHeight(25*scalar);
        p2HealthCur.setX(565*scalar);
        p2HealthCur.setY(65*scalar);
        p2HealthCur.setWidth(400*scalar);
        p2HealthCur.setHeight(25*scalar);

        GUI.getChildren().addAll(p1HealthMax,p2HealthMax,p1HealthCur,p2HealthCur);
        root.getChildren().add(GUI);
    }
    public void updateGameScreen(Pane root){
        p2HealthCur.setWidth(p2HealthCur.getWidth()*scalar);
    }
    public void damage(Fighter f, int dmg){
        int barDamage = dmg*(int)((double)f.getFighterMHealth()/400*scalar);
        if (f.getName().equals("Player 1")) {
            ScaleTransition st = new ScaleTransition(Duration.millis(1000));
            st.setNode(p1HealthCur);
            st.setFromX(p1HealthCur.getWidth()*scalar);
            st.setByX(barDamage);
            st.playFromStart();
        } else {
            ScaleTransition st = new ScaleTransition(Duration.millis(1000));
            st.setNode(p2HealthCur);
           //at this point maybe just have health bars
            // in the middle getting smaller
            st.setFromX(1);
            st.setFromY(1.5);
            st.setByX(-(double)barDamage /100);
            st.setByY(1);
            st.setToX(1-(double)barDamage /100);
            st.setToY(1);
            p2HealthCur.setWidth(p2HealthCur.getWidth()*scalar-barDamage);
            st.playFromStart();


        }
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
