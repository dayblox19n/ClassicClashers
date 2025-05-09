package com.example.demo1;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Wyll extends Fighter{
    private double FlameGauge;
    Wyll(String name, int fighterHeight, int fighterSpeed, int fighterMetGain, int fighterWidth, int fighterHealth, Pane root, String ori){
        super(name,fighterHeight,fighterSpeed,fighterMetGain,fighterWidth,fighterHealth, 100,root, ori);
    }

    @Override
    public void neutralAttack(String ori) {
        this.setState(charState.Attacking);
        Attack s = null;
        if (ori.equals("right")){
            s = new Attack((int)(this.getFighterX()+this.getFighterWidth()),(int)(this.getFighterY()+150),100, 75,7,1,5,4,3,4,this);
        } else if (ori.equals("left")){
            s = new Attack((int)(this.getFighterX()-100),(int)(this.getFighterY()+150),100, 75,7,-1,5,4,3,4,this);
        }
        setCurAtk(s);
        root.getChildren().add(s);
    }


    @Override
    public void forwardAttack(String ori) {
        TranslateTransition tt = new TranslateTransition();
        this.setState(charState.Attacking);
        Attack s = null;
        if (ori.equals("right")){tt.setNode(this);
            tt.setByX(120);
            tt.setFromX(this.getFighterX());
            tt.setToX(this.getFighterX()+60);
            tt.play();
            s = new Attack((int)(this.getFighterX()+this.getFighterWidth()),(int)(this.getFighterY()+100),200,75,25,10,5,11,4,18,this);


        } else if (ori.equals("left")){
            tt.setNode(this);
            tt.setByX(60);
            tt.setFromX(this.getFighterX());
            tt.setToX(this.getFighterX()-60);
            tt.play();
            s = new Attack((int)(this.getFighterX()-200),(int)(this.getFighterY()+100),200,75,25,-10,5,11,4,18,this);

        }
        setCurAtk(s);
        root.getChildren().add(s);
    }

    @Override
    public void backAttack(String ori) {
        this.setState(charState.Attacking);
        Attack s = null;
        if (ori.equals("right")){
            s = new Attack((int)(this.getFighterX()+this.getFighterWidth()),(int)(this.getFighterY()+50),150,175,12,10,5,14,12,9,this);
            jump(-3,2);
        } else if (ori.equals("left")){
            s = new Attack((int)(this.getFighterX()-150),(int)(this.getFighterY()+50),150,175,12,-10,5,14,12,9,this);
            jump(3,2);
        }
        setCurAtk(s);
        root.getChildren().add(s);
    }

    @Override //Maybe make Left side attacks call the "Attack.getWidth"
    public void jumpAttack(String ori) {
        this.setState(charState.Attacking);
        Attack s = null;
        if (ori.equals("right")){
            s = new Attack((int)(this.getTranslateX()+this.getFighterWidth()),(int)(this.getTranslateY()+this.getFighterHeight()-125),175,125,4,10,5,7,5,16,this);
        } else if (ori.equals("left")){
            s = new Attack((int)(this.getTranslateX()-175),(int)(this.getTranslateY()+this.getFighterHeight()-125),175,125,4,-10,5,7,5,16,this);
        }
        setCurAtk(s);
        root.getChildren().add(s);
    }

    @Override
    public void downAttack(String ori) {
        this.setState(charState.Attacking);
        Attack s = null;
        if (ori.equals("right")){
            s = new Attack((int)(this.getFighterX()+this.getFighterWidth()),(int)(this.getFighterY()+this.getFighterHeight()-125),175,125,17,10,5,7,5,16,this);
        } else if (ori.equals("left")){
            s = new Attack((int)(this.getFighterX()-175),(int)(this.getFighterY()+this.getFighterHeight()-125),175,125,17,-10,5,7,5,16,this);
        }
        setCurAtk(s);
        root.getChildren().add(s);
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
