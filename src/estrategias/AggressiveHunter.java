package estrategias;

import laboratorio.LaboRobot;

public class AggressiveHunter extends Strategy {

    public AggressiveHunter(LaboRobot robot) {
        super(robot);
    }

    @Override
    public void run() {
        robot.setColors(robot.red, robot.black, robot.black, robot.red, robot.orange);

        while (true) {
            // Giro constante del radar para no perder enemigos
            robot.turnGunRight(45);
            robot.ahead(80);
        }
    }

    @Override
    public void onScannedRobot() {
        // Ajuste de potencia según la distancia
        double power;
        if (robot.scannedDistance > 400) {
            power = 2.0;
        } else if (robot.scannedDistance > 200) {
            power = 2.5;
        } else {
            power = 3.0;
        }

        // Apuntar directamente al enemigo
        robot.turnGunTo(robot.scannedAngle);
        robot.fireIfReady(power);

        // Si el enemigo está cerca, perseguirlo
        if (robot.scannedDistance < 300) {
            robot.turnTo(robot.scannedAngle);
            robot.ahead(100);
        } else {
            // Si está lejos, acercarse un poco pero con cautela
            robot.turnTo(robot.scannedAngle);
            robot.ahead(50);
        }
    }

    @Override
    public void onHitByBullet() {
        // Movimiento evasivo pero sin dejar de atacar
        robot.turnRight(45);
        robot.ahead(120);
        robot.turnGunRight(360); // Buscar inmediatamente al atacante
    }

    @Override
    public void onHitWall() {
        // Usar tu helper para alejarse rápido de la pared
        robot.alejarDeLaPared();
    }
}