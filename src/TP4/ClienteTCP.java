package TP4;

import java.io.*;
import java.net.*;
import java.util.Random;

public class ClienteTCP {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 5000);
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        Random rand = new Random();

        // Enviar 1000 transacciones en JSON
        long inicioJson = System.currentTimeMillis();
        int totalJsonBytes = 0;
        for (int i = 0; i < 1000; i++) {
            Transaccion t = new Transaccion(i, "NodoA", rand.nextDouble() * 1000, System.currentTimeMillis());
            String json = ParserMensajes.toJSON(t);
            byte[] data = json.getBytes();
            out.writeInt(data.length);
            out.write(data);
            totalJsonBytes += data.length;
        }
        long finJson = System.currentTimeMillis();

        // Enviar 1000 transacciones en Binario
        long inicioBin = System.currentTimeMillis();
        int totalBinBytes = 0;
        for (int i = 0; i < 1000; i++) {
            Transaccion t = new Transaccion(i, "NodoB", rand.nextDouble() * 1000, System.currentTimeMillis());
            byte[] data = ParserMensajes.toBinary(t);
            out.writeInt(data.length);
            out.write(data);
            totalBinBytes += data.length;
        }
        long finBin = System.currentTimeMillis();

        System.out.println("JSON: " + totalJsonBytes + " bytes, tiempo = " + (finJson - inicioJson) + " ms");
        System.out.println("Binario: " + totalBinBytes + " bytes, tiempo = " + (finBin - inicioBin) + " ms");

        socket.close();
    }
}
