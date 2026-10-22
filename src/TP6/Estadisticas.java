package TP6;

import java.io.Serializable;

public class Estadisticas implements Serializable {
    private double promedio;
    private double maximo;
    private double minimo;
    private double desviacion;

    public Estadisticas(double promedio, double maximo, double minimo, double desviacion) {
        this.promedio = promedio;
        this.maximo = maximo;
        this.minimo = minimo;
        this.desviacion = desviacion;
    }

    public double getPromedio() { return promedio; }
    public double getMaximo() { return maximo; }
    public double getMinimo() { return minimo; }
    public double getDesviacion() { return desviacion; }
}
