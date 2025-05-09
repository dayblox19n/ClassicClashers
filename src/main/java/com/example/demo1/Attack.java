package com.example.demo1;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.util.Duration;

enum frameState {
    startUp,
    active,
    endLag,
        }
public class Attack extends Sprite{
    int damage, knockback, counter,hitStun;
    int startUp, active, endLag;
    Fighter fighter;
    frameState aState;
    Attack(int x, int y, int w, int h, int damage, int knockback,int hitStun, int startUp, int active, int endLag, Fighter fighter) {
        super(x,y,w,h,"Hit_Inactive");
        this.damage = damage;
        this.knockback = knockback;
        this.counter = 0;
        this.fighter = fighter;
        this.startUp = startUp;
        this.active = active;
        this.endLag = endLag;
        this.hitStun = hitStun;
    }
    public int getDamage() {
        return this.damage;
    }
    public int getKnockback() {
        return this.knockback;
    }
    public Fighter getFighter() {
        return this.fighter;
    }
    public boolean isFinished(){
        this.counter++;
        if (this.counter >= this.startUp+this.active+this.endLag){
            this.counter = 0;
            return true;
        }
        return false;
    }
    public void checkState(){
        if (this.counter <= this.startUp){
            this.aState = frameState.startUp;
            this.setType("Hit_Inactive");
            this.setFill(Color.GREEN);
        }
        else if (this.counter <= this.startUp+this.active){
            this.aState = frameState.active;
            this.setType("Hit_Active");
            this.setFill(Color.RED);
        }
        else if (this.counter <= this.startUp+this.active+this.endLag){
            this.aState = frameState.endLag;
            this.setType("Hit_Inactive");
            this.setFill(Color.GREEN);
        }
    }
    public boolean isActive(){
        this.counter++;
        return this.aState == frameState.active;
    }
}
