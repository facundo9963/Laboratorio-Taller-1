package estrategias;

import laboratorio.R2D2Robot;

public class Basica extends Strategy {

    public Basica(R2D2Robot robot) {
        super(robot);
    }

    @Override
    public void run() {
        robot.setColors(robot.red, robot.black, robot.black, robot.red, robot.orange);

        // Movimiento inicial: patrullaje agresivo
        while (true) {
            robot.ahead((robot.fieldHeight + robot.fieldWidth)/8);
            robot.turnRight(45);
            robot.turnGunRight(360);// búsqueda constante de enemigos
        }
    }

    @Override
    public void onScannedRobot() {
        if (robot.scannedDistance < 400){
            double power = 3.0 * (1 - (double) robot.scannedDistance / 400);
            robot.turnGunTo(robot.scannedAngle);
            robot.fireIfReady(power);
        }
    }

    @Override
    public void onHitByBullet() {
        robot.turnRight(robot.hitByBulletBearing + 90);
        robot.ahead((robot.fieldHeight + robot.fieldWidth)/32);
        robot.turnGunTo(robot.hitByBulletAngle);
    }

    @Override
    public void onHitWall() {
        this.robot.alejarDeLaPared();
    }
}