package laboratorio;

import estrategias.AggressiveHunter;
import estrategias.Basica;
import estrategias.Defensive;
import estrategias.Strategy;
import robocode.*;

/**
 * LaboRobot - Un humilde tanquecito.
 * <p>
 * Es un tanquecito agresivo
 *
 */
public class R2D2Robot extends JuniorRobot {
    private Strategy estrategia;
    private Estratega estratega;

    public void setEstrategia(Strategy estrategia) {
        this.estrategia = estrategia;
    }

    @Override
    public void run() {
        this.setColors(this.blue, this.gray, this.gray, this.white, this.orange);

        estratega = new Estratega();
        estratega.evaluar();

        while (true) {
            estrategia.run();
        }
    }

    @Override
    public void onScannedRobot() {
        estratega.evaluar();
        estrategia.onScannedRobot();
    }

    @Override
    public void onHitByBullet() {
        estratega.evaluar();
        estrategia.onHitByBullet();
    }

    @Override
    public void onHitWall() {
        estratega.evaluar();
        estrategia.onHitWall();
    }

    public void fireIfReady(double power) {
        if (gunReady) {
            fire(power);
        }
    }

    public void alejarDeLaPared() {
        turnTo(hitWallAngle + 180);
        ahead((fieldHeight + fieldWidth)/8);
    }

    private class Estratega {
        public void evaluar() {
            Strategy nuevaEstrategia;
            if (energy < 20) {
                nuevaEstrategia = new Defensive(R2D2Robot.this);
            } else if (others <= 3) {
                nuevaEstrategia = new AggressiveHunter(R2D2Robot.this);
            } else {
                nuevaEstrategia = new Basica(R2D2Robot.this);
            }

            if (estrategia == null || !estrategia.getClass().equals(nuevaEstrategia.getClass())) {
                System.out.println(nuevaEstrategia.getClass());
                setEstrategia(nuevaEstrategia);
            }
        }
    }
}
