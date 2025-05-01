package com.example.demo1;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
enum charState{
    Neutral,
    Jumping,
    Crouching,
    Walking,
    Backing,
    Attacking
}
public abstract class Fighter extends Sprite{
    private SimpleIntegerProperty fighterHealth = new SimpleIntegerProperty(0);
    String name;
    GUI gui = new GUI();
    double gravity = 1;
    Pane root;
    private int jumpHeight;
    private double fighterWidth, fighterHeight, fighterX, fighterY,fighterSpeed, fighterMetGain;
    public double xVel, yVel, groundHeight = 0, airV;
    private charState cState;
    Fighter(){
        super(0,0,0,0,"Blank");
    }
    Fighter(String name, int fighterHeight, int fighterSpeed, int fighterMetGain, int fighterWidth, int fighterHealth, int jumpHeight,Pane root) {
        super(0,0,fighterWidth,fighterHeight,"Fighter");
        this.cState = charState.Neutral;
        this.name = name;
        this.fighterHeight = fighterHeight;
        this.fighterSpeed = fighterSpeed;
        this.fighterMetGain = fighterMetGain;
        this.fighterWidth = fighterWidth;
        this.fighterHealth.set(fighterHealth);
        this.jumpHeight = jumpHeight;
        this.root = root;
        groundHeight = gui.getHeight()-100-fighterHeight;
    }

    public void setStats(String name, int fighterHeight, int fighterSpeed, int fighterMetGain, int fighterWidth, int fighterHealth) {
        this.name = name;
        this.fighterHeight = fighterHeight;
        this.fighterSpeed = fighterSpeed;
        this.fighterMetGain = fighterMetGain;
        this.fighterWidth = fighterWidth;
        this.fighterHealth.set(fighterHealth);
        groundHeight = gui.getHeight()-200-this.getFighterHeight();
    }
    public void setFighterX(double fighterX) {setTranslateX(fighterX);}
    public void setFighterY(double fighterY) {
        setTranslateY(fighterY);
    }
    public double getGroundHeight() {
        return groundHeight;
    }
    public double getFighterHeight() {
        return fighterHeight;
    }
    public double getFighterWidth() {
        return fighterWidth;
    }
    public double getFighterX() {
        return getTranslateX();
    }
    public double getFighterY() {
        return getTranslateY();
    }
    public double getFighterSpeed() {
        return fighterSpeed;
    }
    public double getFighterMetGain() {
        return fighterMetGain;
    }
    public SimpleIntegerProperty getFighterHealth() {
        return fighterHealth;
    }
    public Pane getRoot() {
        return root;
    }
    public charState checkState(){return cState;}
    public void setState(charState state){cState = state;}//Getters and Setters

    public boolean isBusy(){
        return false;
    }
    public boolean blockLow() {
        return cState == charState.Crouching;
    }
    public void gravity(){
       if (charState.Jumping == cState)
           gravity+=0.1;
       else if (charState.Neutral == cState)
           gravity = 0;
       setTranslateY(getTranslateY()+gravity);
    }

    public void move(){
        stand();
        walk();
        backStep();
    }
    public void stand(){
            if (getTranslateY() > getGroundHeight() && cState == charState.Jumping) {
                this.setTranslateY(getGroundHeight());
                cState = charState.Neutral;
                airV = 0;
            }
    }
    public void walk() {
        if (cState == charState.Walking)
            setTranslateX(getTranslateX()+this.fighterSpeed);
    }
    public void backStep() {
        if (cState == charState.Backing)
            setTranslateX(getTranslateX()-this.fighterSpeed/3);
    }
    public void jump() {
        if (cState == charState.Walking) {
            airV = this.fighterSpeed;
        }
        if (cState == charState.Backing) {
            airV = -this.fighterSpeed/2;
        }
        if (cState != charState.Jumping) {
            cState = charState.Jumping;
        } else {
            setTranslateY(getTranslateY() - 10);
            setTranslateX(getTranslateX()+airV);
        }

    }
    public void dash(){this.fighterSpeed*=2;}
    public void attack(){
        switch (cState) {
            case Neutral:
                neutralAttack();
                break;
            case Jumping:
                jumpAttack();
                break;
            case Backing:
                backAttack();
                break;
            case Walking:
                forwardAttack();
                break;
            case Crouching:
                downAttack();
                break;
            default:
                neutralAttack();
                break;
        }
    }
    public void special(){
        switch (cState) {
            case Neutral:
                neutralSpecial();
                break;
            case Jumping:
                jumpSpecial();
                break;
            case Backing:
                backSpecial();
                break;
            case Walking:
                forwardSpecial();
                break;
            case Crouching:
                downSpecial();
                break;
            default:
                neutralSpecial();
                break;
        }
    }
    public void knockBack(){}

    public abstract void neutralAttack();
    public abstract void forwardAttack();
    public abstract void backAttack();
    public abstract void jumpAttack();
    public abstract void downAttack();
    public abstract void neutralSpecial();
    public abstract void forwardSpecial();
    public abstract void backSpecial();
    public abstract void jumpSpecial();
    public abstract void downSpecial();
}
