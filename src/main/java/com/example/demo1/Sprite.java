package com.example.demo1;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Sprite extends Rectangle {
    private boolean alive = true;
    private String type;

    Sprite(int x, int y, int w, int h, String type) {
        super(w, h);
        setTranslateX(x);
        setTranslateY(y);
        this.type = type;
        this.setFill(Color.BLUE);
    }
    public String getType(){
        return this.type;
    }
    public void setType(String type){
        this.type = type;
    }
}
