package estrategias;

import laboratorio.LaboRobot;
import robocode.JuniorRobot;

public abstract class Strategy {
    protected LaboRobot robot;

    public Strategy(LaboRobot robot) {
        this.robot = robot;
    }

    public abstract void onScannedRobot();
    public abstract void onHitByBullet();
    public abstract void onHitWall();
    public abstract void run();  // opcional: lógica principal de movimiento
}