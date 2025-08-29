package estrategias;

import robocode.JuniorRobot;

public class Agresive extends Strategy {

    public Agresive(JuniorRobot robot) {
        super(robot);
    }

    @Override
    public void run() {
        robot.setColors(robot.red, robot.black, robot.black, robot.red, robot.orange);

        // Movimiento inicial: patrullaje agresivo
        while (true) {
            robot.ahead(150);
            robot.turnGunRight(360); // búsqueda constante de enemigos
            robot.turnRight(45);     // movimiento circular para no ser blanco fácil
        }
    }

    @Override
    public void onScannedRobot() {
        // Calcular potencia según distancia (más cerca = más daño)
        double power = 3.0; // máximo para sumar muchos puntos
        if (robot.scannedDistance > 400) {
            power = 2.0; // media distancia
        } else if (robot.scannedDistance > 200) {
            power = 2.5;
        }

        // Apuntar el cañón hacia el robot detectado
        robot.turnGunTo(robot.scannedAngle);

        // Disparar solo si el arma está lista
        if (robot.gunReady) {
            robot.fire(power);
        }

        // Avanzar hacia el enemigo (presión constante)
        if (robot.scannedDistance > 150) {
            robot.ahead(50);
        }
    }

    @Override
    public void onHitByBullet() {
        // Movimiento evasivo: esquivar hacia los costados
        robot.turnRight(90);
        robot.ahead(100);
    }

    @Override
    public void onHitWall() {
        // Rebotar agresivamente contra la pared
        robot.back(50);
        robot.turnRight(90);
    }
}