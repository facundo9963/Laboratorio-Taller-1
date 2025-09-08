package sanchezNievas.estrategas;

import sanchezNievas.R2D2Robot;
import sanchezNievas.estrategias.Strategy;


public abstract class EstrategaBase {
    public abstract void evaluar(R2D2Robot robot);

    /** ================= Estrategias compartidas ================= */
    public static class Defensive extends Strategy {

        public Defensive(R2D2Robot robot) {
            super(robot);
        }

        @Override
        public void run() {

                robot.ahead((robot.fieldHeight + robot.fieldWidth) / 16);
                robot.turnRight(45);
                robot.turnGunRight(360);
                robot.back((robot.fieldHeight + robot.fieldWidth) / 32);
                robot.turnLeft(45);

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


    public static class AggressiveHunter extends Strategy {

        public AggressiveHunter(R2D2Robot robot) {
            super(robot);
        }

        @Override
        public void run() {


                // Movimiento constante para no ser un blanco fácil
                robot.turnAheadRight(100, 30);
                robot.turnGunRight(30); // barrido continuo del cañón
                robot.turnBackLeft(60, 20);

        }

        @Override
        public void onScannedRobot() {
            // --- Potencia del disparo ---
            double power;
            if (robot.scannedDistance > 400) {
                power = 1.5; // lejos → ahorrar energía
            } else if (robot.scannedDistance > 200) {
                power = 2.5;
            } else {
                power = Math.min(3.0, robot.energy / 8); // máximo daño sin suicidarse
            }

            // --- Predicción de disparo ---
            double bulletSpeed = 20 - 3 * power;
            double time = robot.scannedDistance / bulletSpeed;

            // Posición futura estimada del enemigo
            double enemyFutureX = robot.robotX + robot.scannedDistance * Math.cos(Math.toRadians(robot.scannedAngle))
                    + robot.scannedVelocity * time * Math.cos(Math.toRadians(robot.scannedHeading));
            double enemyFutureY = robot.robotY + robot.scannedDistance * Math.sin(Math.toRadians(robot.scannedAngle))
                    + robot.scannedVelocity * time * Math.sin(Math.toRadians(robot.scannedHeading));

            // Ángulo hacia la posición futura
            double dx = enemyFutureX - robot.robotX;
            double dy = enemyFutureY - robot.robotY;
            int predictedAngle = (int) Math.toDegrees(Math.atan2(dy, dx));

            robot.turnGunTo(predictedAngle);
            robot.fireIfReady(power);

            // --- Movimiento ofensivo ---
            if (robot.scannedDistance < 150) {
                // Muy cerca → orbitar y presionar
                robot.turnAheadRight(120, 40);
            } else if (robot.scannedDistance < 300) {
                // Medio alcance → rodeo agresivo
                robot.turnAheadLeft(150, 25);
            } else {
                // Lejos → acercarse rápido
                robot.turnTo(robot.scannedAngle);
                robot.ahead(180);
            }

            // Si el enemigo tiene poca energía, ir directo a rematar
            if (robot.scannedEnergy < 20) {
                robot.turnTo(robot.scannedAngle);
                robot.ahead(200);
            }
        }

        @Override
        public void onHitByBullet() {
            // Evasión fuerte: moverse en diagonal
            robot.turnBackRight(120, 60);
            // Buscar rápido al atacante
            robot.turnGunRight(360);
        }

        @Override
        public void onHitWall() {
            robot.alejarDeLaPared();
        }
    }



    public static class Basica extends Strategy {

        public Basica(R2D2Robot robot) {
            super(robot);
        }

        @Override
        public void run() {

                robot.ahead((robot.fieldHeight + robot.fieldWidth)/8);
                robot.turnRight(45);
                robot.turnGunRight(360);// búsqueda constante de enemigos

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

}