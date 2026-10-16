package TP5;

public class Mensaje {
    private final int id;
    private final double valor;

    public Mensaje(int id, double valor) {
        this.id = id;
        this.valor = valor;
    }

    public int getId() { return id; }
    public double getValor() { return valor; }
}
