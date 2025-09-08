package sanchezNievas;

import sanchezNievas.estrategas.*;
import sanchezNievas.estrategias.Strategy;
import robocode.JuniorRobot;

public class R2D2Robot extends JuniorRobot {

    // Estratega activo y estrategia activa
    public Strategy estrategia;
    private EstrategaBase estratega;

    @Override
    public void run() {
        // Colores del robot
        setColors(blue, gray, gray, white, orange);

        // Inicializamos el estratega (singleton público)
        estratega = Adaptativo.getInstance();

        // Primera evaluación: asigna estrategia al robot
        estratega.evaluar(this);

        // Bucle principal: delegamos el comportamiento a la estrategia
        while (true) {
            if (estrategia != null) {
                estrategia.run();
            }
            estratega.evaluar(this);
        }
    }

    @Override
    public void onScannedRobot() {
        // Permite al estratega decidir si cambia la estrategia
        estratega.evaluar(this);

        if (estrategia != null) {
            estrategia.onScannedRobot();
        }
    }

    @Override
    public void onHitByBullet() {
        estratega.evaluar(this);

        if (estrategia != null) {
            estrategia.onHitByBullet();
        }
    }

    @Override
    public void onHitWall() {
        estratega.evaluar(this);

        if (estrategia != null) {
            estrategia.onHitWall();
        }
    }

    // ================= Métodos de ayuda =================
    public void fireIfReady(double power) {
        if (gunReady) {
            fire(power);
        }
    }

    public void alejarDeLaPared() {
        turnTo(hitWallAngle + 180);
        ahead((fieldHeight + fieldWidth)/8);
    }
    public void setStrategy(Strategy nueva) {
        this.estrategia = nueva;
    }
}