package TP3;

import java.io.*;
import java.net.*;
import java.util.*;

public class ServidorChat {
    private static List<PrintWriter> clientes = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(5000);
        System.out.println("Servidor iniciado en puerto 5000");


        while (true) {
            Socket socket = server.accept();
            System.out.println("Cliente conectado desde " + socket.getInetAddress());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            clientes.add(out);


            new Thread(() -> {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    String mensaje;
                    while ((mensaje = in.readLine()) != null) {
                        // Mostrar en consola del servidor
                        System.out.println("Mensaje recibido: " + mensaje);

                        // Difundir a todos los clientes conectados
                        synchronized (clientes) {
                            for (PrintWriter cliente : clientes) {
                                cliente.println(mensaje);
                            }
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Cliente desconectado: " + socket.getInetAddress());
                }
            }).start();
        }
    }
}
