package TP3;

import java.io.*;
import java.net.*;

public class ClienteChat {
    public static void main(String[] args) throws IOException {

        String nombre = args.length > 0 ? args[0] : "Cliente";

        Socket socket = new Socket("localhost", 5000);
        BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Hilo para escuchar mensajes del servidor
        new Thread(() -> {
            try {
                String mensaje;
                while ((mensaje = in.readLine()) != null) {
                    System.out.println(">> " + mensaje);
                }
            } catch (IOException e) {
                System.out.println("Desconectado del servidor");
            }
        }).start();

        String texto;
        while ((texto = teclado.readLine()) != null) {
            out.println(nombre + ": " + texto);
        }
    }
}
