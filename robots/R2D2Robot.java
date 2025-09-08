package sanchezNievas;

import sanchezNievas.estrategias.Strategy;
import robocode.*;

/**
 * LaboRobot - Un humilde tanquecito.
 * <p>
 * Es un tanquecito agresivo
 *
 */
public class R2D2Robot extends JuniorRobot {
    private Strategy estrategia;

    @Override
    public void run() {
        estrategia = new AggressiveHunter(this); // le paso el robot en el constructor
        estrategia.run();              // delego el comportamiento principal
    }

    @Override
    public void onScannedRobot() {
        estrategia.onScannedRobot();
    }

    @Override
    public void onHitByBullet() {
        estrategia.onHitByBullet();
    }

    @Override
    public void onHitWall() {
        estrategia.onHitWall();
    }

    public void fireIfReady(double power) {
        if (gunReady) {
            fire(power);
        }
    }

    public void alejarDeLaPared() {
        System.out.println(hitWallBearing);
        turnTo(hitWallAngle + 180);
        ahead((fieldHeight + fieldWidth)/8);
    }
}
