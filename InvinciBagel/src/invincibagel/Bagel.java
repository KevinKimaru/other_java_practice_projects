/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invincibagel;

import static invincibagel.InvinciBagel.HEIGHT;
import javafx.scene.image.Image;
import static invincibagel.InvinciBagel.WIDTH;
import java.util.Random;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;

/**
 *
 * @author Kevin Kimaru Chege
 */
public class Bagel extends Hero {

    protected static final double SPRITE_PIXELS_X = 81;
    protected static final double SPRITE_PIXELS_Y = 81;
    protected static final double rightBoundary = WIDTH - SPRITE_PIXELS_X;
    protected static final double leftBoundary = 0;
    protected static final double bottomBoundary = HEIGHT - SPRITE_PIXELS_Y;
    protected static final double topBoundary = 0;
    protected InvinciBagel invinciBagel;
    boolean animator = false;
    int frameCounter = 0;
    int runningSpeed = 7;
    Random randomNum = new Random();

    public Bagel(InvinciBagel iBagel, String SVGdata, double xLocation, double yLocation, Image... spriteCels) {
        super(SVGdata, xLocation, yLocation, spriteCels);
        invinciBagel = iBagel;
    }

    @Override
    public void update() {
        setXYLocation();
        setBOundaries();
        setImageState();
        moveInvinciBagel(iX, iY);
//        playAudioClip();
        checkCollision();
        restorePB0();
        restorePH0();
        restorePV0();
        restoreTR0();
        restoreTR1();
        restorePR0();
    }

    private void setXYLocation() {
        if (invinciBagel.isRight()) {
            iX += vX;
        }
        if (invinciBagel.isLeft()) {
            iX -= vX;
        }
        if (invinciBagel.isDown()) {
            iY += vY;
        }
        if (invinciBagel.isUp()) {
            iY -= vY;
        }
    }

    private void setBOundaries() {
        if (iX >= rightBoundary) {
            iX = rightBoundary;
        }
        if (iX <= leftBoundary) {
            iX = leftBoundary;
        }
        if (iY >= bottomBoundary) {
            iY = bottomBoundary;
        }
        if (iY <= topBoundary) {
            iY = topBoundary;
        }
    }

    private void setImageState() {
        if (!invinciBagel.isRight()
                && !invinciBagel.isLeft()
                && !invinciBagel.isDown()
                && !invinciBagel.isUp()) {
            spriteFrame.setImage(imageStates.get(0));
            animator = false;
            frameCounter = 0;
        }
        if (invinciBagel.isRight()) {
            spriteFrame.setScaleX(1);
            this.setIsFlipH(false);
            if (!animator && (!invinciBagel.isDown() && !invinciBagel.isUp())) {
                spriteFrame.setImage(imageStates.get(1));
                if (frameCounter >= runningSpeed) {
                    animator = true;
                    frameCounter = 0;
                } else {
                    frameCounter += 1;
                }
            } else if (animator) {
                spriteFrame.setImage(imageStates.get(2));
                if (frameCounter >= runningSpeed) {
                    animator = false;
                    frameCounter = 0;
                } else {
                    frameCounter += 1;
                }
            }
        }
        if (invinciBagel.isLeft()) {
            spriteFrame.setScaleX(-1);
            this.setIsFlipH(true);
            if (!animator && (!invinciBagel.isDown() && !invinciBagel.isUp())) {
                spriteFrame.setImage(imageStates.get(1));
                if (frameCounter >= runningSpeed) {
                    animator = true;
                    frameCounter = 0;
                } else {
                    frameCounter += 1;
                }
            } else if (animator) {
                spriteFrame.setImage(imageStates.get(2));
                if (frameCounter >= runningSpeed) {
                    animator = false;
                    frameCounter = 0;
                } else {
                    frameCounter += 1;
                }
            }
        }
        if (invinciBagel.isDown()) {
            spriteFrame.setImage(imageStates.get(6));
        }
        if (invinciBagel.isUp()) {
            spriteFrame.setImage(imageStates.get(4));
        }
        if (invinciBagel.iswKey()) {
            spriteFrame.setImage(imageStates.get(5));
        }
        if (invinciBagel.issKey()) {
            spriteFrame.setImage(imageStates.get(8));
        }
    }

    private void moveInvinciBagel(double x, double y) {
        spriteFrame.setTranslateX(x);
        spriteFrame.setTranslateY(y);
    }

    private void playAudioClip() {
        if (invinciBagel.isLeft()) {
            invinciBagel.playiSound0();
        }
        if (invinciBagel.isRight()) {
            invinciBagel.playiSound1();
        }
        if (invinciBagel.isUp()) {
            invinciBagel.playiSound2();
        }
        if (invinciBagel.isDown()) {
            invinciBagel.playiSound3();
        }
        if (invinciBagel.iswKey()) {
            invinciBagel.playiSound4();
        }
        if (invinciBagel.issKey()) {
            invinciBagel.playiSound5();
        }
    }

    private void checkCollision() {
        for (int i = 0; i < invinciBagel.castDirector.getCurrentCast().size(); i++) {
            Actor object = invinciBagel.castDirector.getCurrentCast().get(i);
            if (collide(object)) {
                invinciBagel.castDirector.addToRemovedActors(object);
                invinciBagel.root.getChildren().remove(object.getSpriteFrame());
                invinciBagel.castDirector.resetRemovedActors();
                scoringEngine(object);
            }
        }
    }

    private void scoringEngine(Actor object) {
        if (object instanceof Prop) {
            invinciBagel.gameScore -= 1;
            invinciBagel.playiSound0();
        } else if (object instanceof PropV) {
            invinciBagel.gameScore -= 2;
            invinciBagel.playiSound1();
        } else if (object instanceof PropH) {
            invinciBagel.gameScore -= 1;
            invinciBagel.playiSound2();
        } else if (object instanceof PropB) {
            invinciBagel.gameScore -= 2;
            invinciBagel.playiSound3();
        } else if (object instanceof Treasure) {
            invinciBagel.gameScore += 5;
            invinciBagel.playiSound4();
        } else if (object.equals(invinciBagel.iBullet)) {
            invinciBagel.gameScore -= 5;
            invinciBagel.playiSound5();
        } else if (object.equals(invinciBagel.iCheese)) {
            invinciBagel.gameScore += 5;
            invinciBagel.playiSound0();
        } else if (object.equals(invinciBagel.iBeagle)) {
            invinciBagel.gameScore += 10;
            invinciBagel.playiSound0();
        }
        invinciBagel.scoreText.setText(String.valueOf(invinciBagel.gameScore));
    }

    @Override
    public boolean collide(Actor object) {
        if (invinciBagel.iBagel.spriteFrame.getBoundsInParent().intersects(
                object.getSpriteFrame().getBoundsInParent())) {
            Shape intersection = SVGPath.intersect(invinciBagel.iBagel.getSpriteBound(), object.getSpriteBound());
            if (intersection.getBoundsInLocal().getWidth() != -1) {
                return true;
            }
        }
        return false;
    }
    
    /*
    iPR0 = new Prop("M0,0 L0,32 72,32 72,0 Z", 30, 48, iP0);
        iPH0 = new PropH("M0,0 L0,32 72,32 72,0 Z", 172, 248, iP0);
        iPV0 = new PropV("M0,0 L0,32 72,32 72,0 Z", 396, 116, iP0);
        iPB0 = new PropB("M0,0 L0,32 72,32 72,0 Z", 512, 316, iP0);
        iTR0 = new Treasure("M0 0 L0 64 64 64 64 0 Z", 50, 105, iT0);
        iTR1 = new Treasure("M0 0 L0 64 64 64 64 0 Z", 533, 206, iT1);
    */
       
    private void restorePR0() {
        for (int i = 0; i < invinciBagel.castDirector.getCurrentCast().size(); i++) {
            Actor object = invinciBagel.castDirector.getCurrentCast().get(i);
            if (object.equals(invinciBagel.iPR0)) {
                return;
            }
        }
        invinciBagel.castDirector.addCurrentCast(invinciBagel.iPR0);
        invinciBagel.iPR0.spriteFrame.setTranslateY(20+randomNum.nextInt(340));
        invinciBagel.root.getChildren().add(invinciBagel.iPR0.spriteFrame);
    }
    private void restorePH0() {
        for (int i = 0; i < invinciBagel.castDirector.getCurrentCast().size(); i++) {
            Actor object = invinciBagel.castDirector.getCurrentCast().get(i);
            if (object.equals(invinciBagel.iPH0)) {
                return;
            }
        }
        invinciBagel.castDirector.addCurrentCast(invinciBagel.iPH0);
        invinciBagel.iPH0.spriteFrame.setTranslateY(20+randomNum.nextInt(340));
        invinciBagel.root.getChildren().add(invinciBagel.iPH0.spriteFrame);
    }
    private void restorePV0() {
        for (int i = 0; i < invinciBagel.castDirector.getCurrentCast().size(); i++) {
            Actor object = invinciBagel.castDirector.getCurrentCast().get(i);
            if (object.equals(invinciBagel.iPV0)) {
                return;
            }
        }
        invinciBagel.castDirector.addCurrentCast(invinciBagel.iPV0);
        invinciBagel.iPV0.spriteFrame.setTranslateY(20+randomNum.nextInt(340));
        invinciBagel.root.getChildren().add(invinciBagel.iPV0.spriteFrame);
    }
    private void restorePB0() {
        for (int i = 0; i < invinciBagel.castDirector.getCurrentCast().size(); i++) {
            Actor object = invinciBagel.castDirector.getCurrentCast().get(i);
            if (object.equals(invinciBagel.iPB0)) {
                return;
            }
        }
        invinciBagel.castDirector.addCurrentCast(invinciBagel.iPB0);
       invinciBagel.iPB0.spriteFrame.setTranslateY(20+randomNum.nextInt(340));
        invinciBagel.root.getChildren().add(invinciBagel.iPB0.spriteFrame);
    }
    private void restoreTR0() {
        for (int i = 0; i < invinciBagel.castDirector.getCurrentCast().size(); i++) {
            Actor object = invinciBagel.castDirector.getCurrentCast().get(i);
            if (object.equals(invinciBagel.iTR0)) {
                return;
            }
        }
        invinciBagel.castDirector.addCurrentCast(invinciBagel.iTR0);
        invinciBagel.iTR0.spriteFrame.setTranslateY(20+randomNum.nextInt(340));
        invinciBagel.root.getChildren().add(invinciBagel.iTR0.spriteFrame);
    }
    private void restoreTR1() {
        for (int i = 0; i < invinciBagel.castDirector.getCurrentCast().size(); i++) {
            Actor object = invinciBagel.castDirector.getCurrentCast().get(i);
            if (object.equals(invinciBagel.iTR1)) {
                return;
            }
        }
        invinciBagel.castDirector.addCurrentCast(invinciBagel.iTR1);
        invinciBagel.iTR1.spriteFrame.setTranslateY(20+randomNum.nextInt(340));
        invinciBagel.root.getChildren().add(invinciBagel.iTR1.spriteFrame);
    }
}
