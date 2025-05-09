package com.example.demo1;

import javafx.animation.TranslateTransition;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

public class Map {
    Boolean Hazards = false;
    GUI gui = new GUI();
    String name;
    double mapWidth;
    FileInputStream file;
    public Map(String name, double mapWidth, FileInputStream fis) {
        this.mapWidth = mapWidth;
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
    public FileInputStream getFile() {
        return this.file;
    }
    public void setFile(FileInputStream fis) {
        this.file = fis;
    }
    public void setWidth(double width) {
        this.mapWidth = width;
    }
    public double getWidth() {
        return mapWidth;
    }
    public void genMap(Rectangle background) {
        Image image = new javafx.scene.image.Image(this.file);
        ImagePattern image_pattern = new ImagePattern(image);
        background.setFill(image_pattern);

    }
    public void mapSetup(String name, double width, Rectangle mapIcon, FileInputStream iconInput, FileInputStream imageInput) {
        this.name = name;
        this.file = imageInput;
        this.mapWidth = width;
        Image image = new javafx.scene.image.Image(iconInput);
        ImagePattern image_pattern = new ImagePattern(image);
        mapIcon.setFill(image_pattern);
    }
    public void update(Rectangle background, List<Fighter> f) {
        if (background.getTranslateX() < this.getWidth()/4-50) {
            f.forEach(fighter -> {
                f.forEach(fighter1 -> {
                    if (fighter.getFighterX() < 50 && fighter1.getFighterX() < gui.getWidth()/2){
                        background.setTranslateX(background.getTranslateX() + 10);

                    }
                });
            });
        }
        if (background.getTranslateX() > -this.getWidth()/4+50) {
            f.forEach(fighter -> {
                f.forEach(fighter1 -> {
                    if (fighter.getFighterX()+fighter.getWidth() > gui.getWidth()-50 && fighter1.getFighterX()+fighter1.getWidth() > gui.getWidth()/2){
                        background.setTranslateX(background.getTranslateX() - 10);

                    }
                });
            });
        }
    }
}
