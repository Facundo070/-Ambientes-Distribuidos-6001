package TP4;

import com.google.gson.Gson;
import java.io.*;

public class ParserMensajes {
    private static final Gson gson = new Gson();

    // Serializar a JSON
    public static String toJSON(Transaccion t) {
        return gson.toJson(t);
    }

    // Deserializar desde JSON
    public static Transaccion fromJSON(String json) {
        return gson.fromJson(json, Transaccion.class);
    }

    // Serializar a Binario
    public static byte[] toBinary(Transaccion t) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(t.getIdTransaccion());
        dos.writeUTF(t.getOrigen());
        dos.writeDouble(t.getMonto());
        dos.writeLong(t.getTimestamp());
        return baos.toByteArray();
    }

    // Deserializar desde Binario
    public static Transaccion fromBinary(byte[] data) throws IOException {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(data));
        int id = dis.readInt();
        String origen = dis.readUTF();
        double monto = dis.readDouble();
        long timestamp = dis.readLong();
        return new Transaccion(id, origen, monto, timestamp);
    }
}
