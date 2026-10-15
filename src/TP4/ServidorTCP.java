package TP4;

import java.io.*;
import java.net.*;

public class ServidorTCP {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(5000);
        System.out.println("Servidor escuchando en puerto 5000...");

        while (true) {
            Socket cliente = server.accept();
            new Thread(() -> {
                try {
                    DataInputStream in = new DataInputStream(cliente.getInputStream());
                    while (true) {
                        int length = in.readInt(); // tamaño del mensaje
                        byte[] data = new byte[length];
                        in.readFully(data);

                        // Intentar parsear como JSON
                        String mensaje = new String(data);
                        if (mensaje.trim().startsWith("{")) {
                            Transaccion t = ParserMensajes.fromJSON(mensaje);
                            System.out.println("Recibido JSON: " + t.getIdTransaccion() + " - " + t.getOrigen());
                        } else {
                            Transaccion t = ParserMensajes.fromBinary(data);
                            System.out.println("Recibido Binario: " + t.getIdTransaccion() + " - " + t.getOrigen());
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Cliente desconectado.");
                }
            }).start();
        }
    }
}
