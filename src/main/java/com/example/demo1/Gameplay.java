package com.example.demo1;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.List;


enum Inputs {
    A, //Controller Buttons
    B,
    X,
    Y,
    DUp,
    DDown,
    DLeft,
    DRight,
    LEFT, //Controller Stick Positions
    LEFT_BOTTOM,
    LEFT_TOP,
    TOP,
    BOTTOM,
    RIGHT,
    RIGHT_BOTTOM,
    RIGHT_TOP,
}

public class Gameplay {
    private Inputs input;
    private final AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            //update();
            newInput();
        }
    };
    public Gameplay(int TPS, int FPS) {
    }
    public void update(List<Sprite> sprites, List<Fighter> fighters,Pane pane) {
        fighters.forEach(fighter -> {
            if (fighter.checkState().equals(charState.Jumping))
                fighter.jump();
            fighter.move();
            fighter.gravity();

            sprites.stream().filter(sprite1 -> sprite1.getType().equals("Hurt")).forEach(sprite1 -> {
                if (fighter.contains(sprite1.getX(), sprite1.getY())) {
                    fighter.knockBack();
                }
            });

        });



        }

//        for (Fighter fighter : fighters) {
//            if (fighter.checkState().equals(charState.Jumping))
//                fighter.jump();
//            fighter.move();
//            fighter.gravity();
//        }


    public void startGameTimer() {
        timer.start();
    }
    public void pauseGameTimer() throws InterruptedException {
        timer.wait();}
    public void stopGameTimer() {
        timer.stop();}
    public void tick() {}
    public void newInput(){



    }
}
