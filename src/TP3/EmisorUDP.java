package TP3;

import java.net.*;

public class EmisorUDP {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress direccion = InetAddress.getByName("localhost");

        while (true) {
            String mensaje = "Alerta desde cliente UDP '-' ";
            byte[] buffer = mensaje.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, direccion, 6000);
            socket.send(packet);
            System.out.println("Enviado: " + mensaje);
            Thread.sleep(2000); // cada 2 segundos
        }
    }
}
