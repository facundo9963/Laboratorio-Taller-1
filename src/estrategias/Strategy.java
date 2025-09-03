package estrategias;

import laboratorio.R2D2Robot;

public abstract class Strategy {
    protected R2D2Robot robot;

    public Strategy(R2D2Robot robot) {
        this.robot = robot;
    }

    public abstract void onScannedRobot();
    public abstract void onHitByBullet();
    public abstract void onHitWall();
    public abstract void run();  // opcional: lógica principal de movimiento
}