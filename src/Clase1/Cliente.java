package Clase1;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost",5500);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        Scanner sc = new Scanner(System.in);
        System.out.print("Número 1: ");
        int a = sc.nextInt();
        System.out.print("Operación (+,-,*,/): ");
        String op = sc.next();
        System.out.print("Número 2: ");
        int b = sc.nextInt();

        out.println(a + ";" + op + ";" + b);
        System.out.println("Resultado: " + in.readLine());

        socket.close();
    }
}
