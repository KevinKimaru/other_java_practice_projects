/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invincibagel;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;

/**
 *
 * @author Kevin Kimaru Chege
 */
public class GamePlayPloop extends AnimationTimer {

    Pos location;
    protected InvinciBagel invinciBagel;

    public GamePlayPloop(InvinciBagel iBagel) {
        this.invinciBagel = iBagel;
    }

    @Override
    public void handle(long now) {
        invinciBagel.iBagel.update();
        invinciBagel.iBeagle.update();
    }

    @Override
    public void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void stop() {
        super.stop(); //To change body of generated methods, choose Tools | Templates.
    }

}
