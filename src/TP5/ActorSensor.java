package TP5;

import java.util.Random;

public class ActorSensor implements Runnable {
    private final ActorProcesador procesador;
    private final int cantidad;
    private final Random rand = new Random();

    public ActorSensor(ActorProcesador procesador, int cantidad) {
        this.procesador = procesador;
        this.cantidad = cantidad;
    }

    @Override
    public void run() {
        for (int i = 0; i < cantidad; i++) {
            double valor = 20 + rand.nextDouble() * 10; // ejemplo: temperatura
            Mensaje m = new Mensaje(i, valor);
            procesador.enviar(m);
        }
    }
}
