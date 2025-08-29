package estrategias;

import robocode.JuniorRobot;

public abstract class Strategy {
    protected JuniorRobot robot;

    public Strategy(JuniorRobot robot) {
        this.robot = robot;
    }

    public abstract void onScannedRobot();
    public abstract void onHitByBullet();
    public abstract void onHitWall();
    public abstract void run();  // opcional: lógica principal de movimiento
}