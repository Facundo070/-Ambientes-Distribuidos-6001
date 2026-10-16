package TP5;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ActorProcesador implements Runnable {
    private final BlockingQueue<Mensaje> mailbox = new LinkedBlockingQueue<>();
    private int contador = 0;
    private double suma = 0;

    public void enviar(Mensaje m) {
        mailbox.offer(m); // asincrónico, no bloquea
    }

    @Override
    public void run() {
        try {
            while (true) {
                Mensaje m = mailbox.take(); // procesa en orden FIFO
                contador++;
                suma += m.getValor();
                double promedio = suma / contador;
                System.out.println("Procesado mensaje " + m.getId() + " valor=" + m.getValor() + " promedio=" + promedio);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
