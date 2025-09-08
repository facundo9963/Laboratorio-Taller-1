package sanchezNievas.estrategas;

import sanchezNievas.R2D2Robot;

public class Conservador extends EstrategaBase {

    private static Conservador instancia;

    private Conservador() {}

    public static Conservador getInstance() {
        if (instancia == null) {
            instancia = new Conservador();
        }
        return instancia;
    }

    @Override
    public void evaluar(R2D2Robot robot) {
        if (robot.energy > 70) {
            robot.setStrategy(new AggressiveHunter(robot));
        } else {
            robot.setStrategy(new Defensive(robot));
        }
    }
}
