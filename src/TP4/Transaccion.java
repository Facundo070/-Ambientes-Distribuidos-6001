package TP4;

public class Transaccion {
    private int idTransaccion;
    private String origen;
    private double monto;
    private long timestamp;

    public Transaccion(int idTransaccion, String origen, double monto, long timestamp) {
        this.idTransaccion = idTransaccion;
        this.origen = origen;
        this.monto = monto;
        this.timestamp = timestamp;
    }

    // Getters
    public int getIdTransaccion() { return idTransaccion; }
    public String getOrigen() { return origen; }
    public double getMonto() { return monto; }
    public long getTimestamp() { return timestamp; }
}