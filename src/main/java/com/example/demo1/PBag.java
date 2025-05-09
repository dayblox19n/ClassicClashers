package com.example.demo1;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class PBag extends Fighter{
    boolean res = false;
    charState cState = charState.Neutral;
    PBag(Pane root){
        super("Player 2",400,0,0,200,400, 0, root,"right");
        this.setFill(Color.BLACK);
    }
    public void update(){
        if (this.isNotBusy()) {
            if (this.getTranslateX() > 700 && this.getOrient().equals("right"))
                backStep();
            else if (this.getTranslateX() < 700 && this.getOrient().equals("right"))
                walk();
            else if (this.getTranslateX() > 700 && this.getOrient().equals("left"))
                walk();
            else if (this.getTranslateX() < 700 && this.getOrient().equals("left"))
                backStep();
        }
        stand();
        //cState = charState.Backing;
    }
    @Override
    public void neutralAttack(String ori) {

    }

    @Override
    public void forwardAttack(String ori) {

    }

    @Override
    public void backAttack(String ori) {

    }

    @Override
    public void jumpAttack(String ori) {

    }

    @Override
    public void downAttack(String ori) {

    }

    @Override
    public void neutralSpecial(String ori) {

    }

    @Override
    public void forwardSpecial(String ori) {

    }

    @Override
    public void backSpecial(String ori) {

    }

    @Override
    public void jumpSpecial(String ori) {

    }

    @Override
    public void downSpecial(String ori) {

    }
}
