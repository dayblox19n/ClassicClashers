package com.example.demo1;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

enum charState{
    Neutral,
    Jumping,
    Recoiling,
    Crouching,
    Walking,
    Backing,
    Attacking,
    Hit,
    Stun,
}
public abstract class Fighter extends Sprite{
    private SimpleIntegerProperty fighterHealth = new SimpleIntegerProperty(0),
    fighterMHealth = new SimpleIntegerProperty(0);

    String name, orient;
    GUI gui = new GUI();
    double gravity = 1;
    Pane root;
    private int jumpHeight;
    private Attack atk;
    private double fighterWidth, fighterHeight, fighterX, fighterY,fighterSpeed, fighterMetGain, baseFS;
    public double xVel, yVel, groundHeight = 0, airV;
    private charState cState;
    private boolean isTouchingOpponent = false;
    Fighter(){
        super(0,0,0,0,"Blank");
    }
    Fighter(String name, int fighterHeight, int fighterSpeed, int fighterMetGain, int fighterWidth, int fighterHealth, int jumpHeight,Pane root, String ori) {
        super(0,0,fighterWidth,fighterHeight,"Fighter");
        this.cState = charState.Neutral;
        this.name = name;
        this.fighterHeight = fighterHeight;
        this.fighterSpeed = fighterSpeed;
        this.baseFS = fighterSpeed;
        this.fighterMetGain = fighterMetGain;
        this.fighterWidth = fighterWidth;
        this.fighterHealth.set(fighterHealth);
        this.fighterMHealth.set(fighterHealth);
        this.jumpHeight = jumpHeight;
        this.root = root;
        groundHeight = gui.getHeight()-100-fighterHeight;
        this.orient = ori;
    }
    public void setFighterX(double fighterX) {setTranslateX(fighterX);}
    public void setFighterY(double fighterY) {
        setTranslateY(fighterY);
    }
    public void setFighterSpeed(double fighterSpeed) {
        this.fighterSpeed = fighterSpeed;
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
    public void getFighterHealth() {
        fighterHealth.get();
    }
    public SimpleIntegerProperty getFighterHealthProperty(){ return fighterHealth;}
    public int getFighterMHealth(){ return fighterMHealth.get();}
    public Pane getRoot() {
        return root;
    }
    public Attack getCurAtk(){
        return atk;
    }
    public void setCurAtk(Attack a) {
        this.atk = a;
    }
    public charState checkState(){return this.cState;}
    public void setState(charState state){
        if (this.cState != charState.Jumping)
            this.cState = state;
    }
    public String getName(){return this.name;}
    public String getOrient(){return this.orient;}//Getters and Setters

    public boolean isNotBusy(){
        return this.cState != charState.Jumping && this.cState != charState.Attacking && this.cState != charState.Hit && this.cState != charState.Recoiling;
    }
    public int isNotTouchingWall(){
            if (this.getFighterX() <= 0)
                return -1;
            if (this.getFighterX() >= gui.getWidth() - this.getFighterWidth())
                return 1;
            return 0;
    }
    public void checkTouchingOpponent(boolean check){
        isTouchingOpponent = check;
    }
    public boolean blockLow() {
        return cState == charState.Crouching;
    }
    public int direction(){
        return -1; //if facing left
    }
    public void gravity(){
       if (charState.Jumping == cState || charState.Hit == cState || charState.Recoiling == cState)
           gravity+=0.1;
       else if (charState.Neutral == cState)
           gravity = 0;
       setTranslateY(getTranslateY()+gravity);
    }


    public void update(){
        if (this.orient.equals("right"))
            setFighterSpeed(this.baseFS);
        if (this.orient.equals("left"))
            setFighterSpeed(-this.baseFS);
        stand();
        walk();
        backStep();
        if (charState.Hit == cState)
            this.setFill(Color.RED);
    }
    public void updateOri(String ori){
        this.orient = ori;
    }
    public void stand(){
            if (getTranslateY() > getGroundHeight() && (cState == charState.Jumping || cState == charState.Hit || cState == charState.Recoiling)) {
                this.setTranslateY(getGroundHeight());
                cState = charState.Neutral;
                airV = 0;
                this.setFill(Color.BLUE);
            }
    }
    public void walk() {
        if (cState == charState.Walking && this.isNotTouchingWall() != 1 && !this.isTouchingOpponent)
            setTranslateX(getTranslateX()+this.fighterSpeed);
    }
    public void backStep() {
        if (cState == charState.Backing && this.isNotTouchingWall() != -1)
            setTranslateX(getTranslateX()-this.fighterSpeed/3);
    }
    public void jump() {
        if (cState == charState.Walking)
            airV = this.fighterSpeed;
        else if (cState == charState.Backing)
            airV = -this.fighterSpeed/2;
        if (cState != charState.Jumping) {
            cState = charState.Jumping;
        } else {
            if (this.isTouchingOpponent && this.getOrient().equals("right"))
                airV = -0.2;
            else if (this.isTouchingOpponent && this.getOrient().equals("left"))
                airV = 0.2;
            setTranslateY(getTranslateY() - 7);
            if (this.isNotTouchingWall() == 0)
                setTranslateX(getTranslateX()+airV);

        }

    }
    public void jump(double airV,double airY) {
        if (cState != charState.Recoiling) {
            cState = charState.Recoiling;
        } else {
            if (this.isTouchingOpponent && this.getOrient().equals("right"))
                airV = -0.2;
            else if (this.isTouchingOpponent && this.getOrient().equals("left"))
                airV = 0.2;
            setTranslateY(getTranslateY() - airY);
            if (this.isNotTouchingWall() == 0)
                setTranslateX(getTranslateX()+airV);

        }

    }
    public void dash(){this.fighterSpeed*=2;}
    public void attack(){
        if (cState != charState.Attacking) {
            switch (cState) {
                case Jumping:
                    jumpAttack(orient);
                    break;
                case Backing:
                    backAttack(orient);
                    break;
                case Walking:
                    forwardAttack(orient);
                    break;
                case Crouching:
                    downAttack(orient);
                    break;
                default:
                    neutralAttack(orient);
                    break;
            }
        }
    }
    public void special(){
        if (cState != charState.Attacking) {
            switch (cState) {
                case Jumping:
                    jumpSpecial(orient);
                    break;
                case Backing:
                    backSpecial(orient);
                    break;
                case Walking:
                    forwardSpecial(orient);
                    break;
                case Crouching:
                    downSpecial(orient);
                    break;
                default:
                    neutralSpecial(orient);
                    break;
            }
        }
    }
    public void knockBack(int kb){
        airV = kb;
        if (cState != charState.Hit) {
            cState = charState.Hit;
        } else {
            this.setFill(Color.RED);
            setTranslateY(getTranslateY() - 4);
            if (this.isNotTouchingWall() == 0)
                setTranslateX(getTranslateX()+airV);
        }
    }
    public void damage(Attack atk,GUI g){
        if (cState != charState.Hit) {
            this.fighterHealth.set(fighterHealth.get() - atk.getDamage());
            g.damage(this, atk.getDamage()/3);
        }
    }
    public void block(Attack atk,GUI g){
        this.fighterHealth.set(fighterHealth.get() - atk.getDamage()/3);
        setTranslateX(getTranslateX()+0.2);
        g.damage(this, atk.getDamage()/3);
    }



    public abstract void neutralAttack(String orient);
    public abstract void forwardAttack(String orient);
    public abstract void backAttack(String orient);
    public abstract void jumpAttack(String orient);
    public abstract void downAttack(String orient);
    public abstract void neutralSpecial(String orient);
    public abstract void forwardSpecial(String orient);
    public abstract void backSpecial(String orient);
    public abstract void jumpSpecial(String orient);
    public abstract void downSpecial(String orient);
}
