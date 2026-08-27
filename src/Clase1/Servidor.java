package Clase1;

import java.io.*;
import java.net.*;

public class Servidor {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(5500);
        System.out.println("Servidor esperando...");

        while (true) {
            Socket socket = server.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

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
