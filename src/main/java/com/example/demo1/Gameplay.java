package com.example.demo1;

import javafx.scene.layout.Pane;

import java.util.List;




public class Gameplay {
    private int counter = 0;
    GUI gui = new GUI();
    public void update(List<Sprite> sprites, List<Fighter> fighters,List<Attack> attacks,Pane p, GUI gui) {
        fighters.forEach(fighter -> {
            try {
                if (fighter.checkState().equals(charState.Jumping))
                    fighter.jump();
                if (fighter.checkState().equals(charState.Recoiling))
                    fighter.jump(3,2);
                if (fighter.checkState().equals(charState.Hit))
                    fighter.knockBack(attacks.get(0).getKnockback());
            } catch (Exception e) {
            }
            fighter.update();
            fighter.gravity();
            fighters.forEach(fighter1 -> {
                if (fighter.getFighterX() < fighter1.getFighterX()) {
                    fighter.updateOri("right");
                    fighter1.updateOri("left");
                } else {
                    fighter.updateOri("left");
                    fighter1.updateOri("right");
                }
                fighter.checkTouchingOpponent(fighter.getBoundsInParent().intersects(fighter1.getBoundsInParent()));
            });

            attacks.forEach(attack -> {
                attack.checkState();
                if (attack.getBoundsInParent().intersects(fighter.getBoundsInParent())  && !attack.getFighter().equals(fighter) && attack.getType().equals("Hit_Active")) {
                    //gui.updateGameScreen(p);
                    if (fighter.checkState().equals(charState.Backing))
                        fighter.block(attack,gui);
                    else {
                        fighter.damage(attack,gui);
                        fighter.knockBack(attack.getKnockback());
                    }
                }
                if (attack.isFinished()){
                    eraseAttacks(attack.getFighter(), p);
                }
            });
        });
    }

    public void renderAttacks(Fighter fighter, Pane p) {
        p.getChildren().add(fighter.getCurAtk());
    }
    public void eraseAttacks(Fighter fighter, Pane p) {
        p.getChildren().remove(fighter.getCurAtk());
        if (fighter.checkState().equals(charState.Attacking))
            fighter.setState(charState.Neutral);
        else if (fighter.checkState().equals(charState.Recoiling)) {
            fighter.setState(charState.Recoiling);

        }
    }
}
