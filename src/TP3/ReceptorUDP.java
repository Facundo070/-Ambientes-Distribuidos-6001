package TP3;

import java.net.*;

public class ReceptorUDP {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(6000);
        socket.setSoTimeout(5000);
        byte[] buffer = new byte[1024];

        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(packet);
                String mensaje = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Recibido: " + mensaje);
            } catch (SocketTimeoutException e) {
                System.out.println(" No se recibió ningún datagrama en 5 segundos -.- ");
            }
        }
    }
}
