package estrategias;

import laboratorio.R2D2Robot;

public class Defensive extends Strategy {

    public Defensive(R2D2Robot robot) {
        super(robot);
    }

    @Override
    public void run() {

        while (true) {
            robot.ahead((robot.fieldHeight + robot.fieldWidth) / 16);
            robot.turnRight(45);
            robot.turnGunRight(360);
            robot.back((robot.fieldHeight + robot.fieldWidth) / 32);
            robot.turnLeft(45);
        }
    }

    @Override
    public void onScannedRobot() {
        if (robot.scannedDistance < 250) {
            double power = 2.0 * (1 - (double) robot.scannedDistance / 250);
            robot.turnGunTo(robot.scannedAngle);
            robot.fireIfReady(power);
        } else {
            robot.turnTo(robot.scannedAngle + 90);
            robot.ahead((robot.fieldHeight + robot.fieldWidth) / 64);
        }
    }

    @Override
    public void onHitByBullet() {
        robot.turnRight(robot.hitByBulletBearing + 135);
        robot.ahead((robot.fieldHeight + robot.fieldWidth)/32);
    }

    @Override
    public void onHitWall() {
        robot.alejarDeLaPared();
    }
}
