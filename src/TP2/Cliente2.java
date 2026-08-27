
package TP2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class Cliente2 {
    public Cliente2() {
    }

    public static void main(String[] args) throws IOException {
        int base = 1000; // tiempo base en ms (1 segundo)
        int maxIntentos = 5;
        int intentos = 0;
        long tiempoTotal = 0;
        boolean exito = false;

        Scanner sc = new Scanner(System.in);
        System.out.print("Número 1: ");
        int a = sc.nextInt();
        System.out.print("Operación (+,-,*,/): ");
        String op = sc.next();
        System.out.print("Número 2: ");
        int b = sc.nextInt();

        while (intentos < maxIntentos && !exito) {
            intentos++;
            try {
                long inicio = System.currentTimeMillis();

                Socket socket = new Socket("localhost", 5500);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                out.println(a + ";" + op + ";" + b);
                System.out.println("Resultado: " + in.readLine());

                socket.close();
                exito = true;

                tiempoTotal += (System.currentTimeMillis() - inicio);

            } catch (IOException e) {
                // cálculo de tiempo de espera con jitter
                int jitter = new Random().nextInt(500); // entre 0 y 500 ms
                int tiempoEsperado = (base * (int)Math.pow(2, intentos - 1)) + jitter;

                System.out.println("Fallo en intento " + intentos + ". Esperando " + tiempoEsperado + " ms...");
                try {
                    Thread.sleep(tiempoEsperado);
                    tiempoTotal += tiempoEsperado;
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        }


        System.out.println("Estado final: " + (exito ? "Éxito" : "Fallo definitivo"));
        System.out.println("Intentos realizados: " + intentos);
        System.out.println("Tiempo total: " + tiempoTotal + " ms");
    }
}
