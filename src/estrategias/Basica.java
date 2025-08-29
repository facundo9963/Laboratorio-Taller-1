package estrategias;

public class Basica extends Strategy {

    public Basica(robocode.JuniorRobot robot) {
        super(robot);
    }

    @Override
    public void run() {
        robot.setColors(robot.orange, robot.blue, robot.white, robot.yellow, robot.black);
        while (true) {
            robot.ahead(100);
            robot.turnGunRight(360);
            robot.back(100);
            robot.turnGunRight(360);
        }
    }

    @Override
    public void onScannedRobot() {
        if (robot.gunReady) {
            robot.fire(1);
        }
    }

    @Override
    public void onHitByBullet() {
        robot.back(10);
    }

    @Override
    public void onHitWall() {
        robot.back(20);
        robot.turnRight(90);
    }
}