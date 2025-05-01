package com.example.demo1;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.io.FileInputStream;

public class Map {
    Boolean Hazards = false;
    String name;
    double mapWidth;
    FileInputStream file;
    public Map(String name, double mapWidth, FileInputStream fis) {
        this.mapWidth = mapWidth;
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public FileInputStream getFile() {
        return file;
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
    public Parent genMap(double cW, double cH){
        Pane root = new Pane();
        Rectangle background = new Rectangle(cW*2, 100, 0, cH-200);
        Image image = new javafx.scene.image.Image(this.getFile());
        ImagePattern image_pattern = new ImagePattern(image);
        background.setFill(image_pattern);

        return root;
    }
    public void mapSetup(String name, double width, Rectangle mapIcon, FileInputStream iconInput, FileInputStream imageInput) {
        this.name = name;
        this.file = imageInput;
        this.mapWidth = width;
        Image image = new javafx.scene.image.Image(iconInput);
        ImagePattern image_pattern = new ImagePattern(image);
        mapIcon.setFill(image_pattern);
    }
}
