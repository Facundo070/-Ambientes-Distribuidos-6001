package TP2;

import java.io.*;
import java.net.*;
import java.util.Random;

public class Servidor2 {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(5500);
        System.out.println("Servidor2 esperando...");

        Random random = new Random();

        while (true) {
            Socket socket = server.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);


            if (random.nextInt(100) < 30) {
                System.out.println("⚠️ Simulando fallo del servidor2...");
                out.println("ERROR: Fallo simulado en el servidor2");
                socket.close();
                continue;
            }

            String[] datos = in.readLine().split(";");
            int a = Integer.parseInt(datos[0]);
            String op = datos[1];
            int b = Integer.parseInt(datos[2]);

            String resultado;
            if (op.equals("+")) resultado = "" + (a + b);
            else if (op.equals("-")) resultado = "" + (a - b);
            else if (op.equals("*")) resultado = "" + (a * b);
            else if (op.equals("/")) resultado = (b == 0) ? "ERROR: Division por cero" : "" + (a / b);
            else resultado = "ERROR: Operación inválida";

            out.println(resultado);
            socket.close();
        }
    }
}
