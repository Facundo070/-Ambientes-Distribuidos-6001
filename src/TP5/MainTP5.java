package TP5;

public class MainTP5 {
    public static void main(String[] args) {
        ActorProcesador procesador = new ActorProcesador();
        Thread hiloProcesador = new Thread(procesador);
        hiloProcesador.start();

        ActorSensor sensor = new ActorSensor(procesador, 500); // ráfaga de 500 mensajes
        Thread hiloSensor = new Thread(sensor);
        hiloSensor.start();
    }
}
