package sanchezNievas.estrategas;

import sanchezNievas.R2D2Robot;

public class Adaptativo extends EstrategaBase {

    private static Adaptativo instancia;

    private Adaptativo() {}

    public static Adaptativo getInstance() {
        if (instancia == null) {
            instancia = new Adaptativo();
        }
        return instancia;
    }

    @Override
    public void evaluar(R2D2Robot robot) {
        if (robot.energy < 50) {
            robot.setStrategy(new AggressiveHunter(robot));
        } else if (robot.others <= 2) {
            robot.setStrategy(new AggressiveHunter(robot));
        } else {
            robot.setStrategy(new Basica(robot));
        }
    }
}