package TP6;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServicioRemoto extends Remote {

    Estadisticas calcularEstadisticas(List<Double> datos) throws RemoteException;

    boolean validarCadena(String dato) throws RemoteException;

    List<String> filtrarPorPatron(List<String> textos, String patron) throws RemoteException;
}
