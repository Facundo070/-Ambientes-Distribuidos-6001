package TP6;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.regex.Pattern;

public class Servidor implements ServicioRemoto {

    @Override
    public Estadisticas calcularEstadisticas(List<Double> datos) throws RemoteException {
        double suma = 0;
        double max = Double.MIN_VALUE;
        double min = Double.MAX_VALUE;

        for (double d : datos) {
            suma += d;
            if (d > max) max = d;
            if (d < min) min = d;
        }

        double promedio = suma / datos.size();

        double sumVar = 0;
        for (double d : datos) {
            sumVar += Math.pow(d - promedio, 2);
        }
        double desviacion = Math.sqrt(sumVar / datos.size());

        return new Estadisticas(promedio, max, min, desviacion);
    }

    @Override
    public boolean validarCadena(String dato) throws RemoteException {
        // Ejemplo: validar si es un DNI de 8 dígitos
        return dato.matches("\\d{8}");
    }

    @Override
    public List<String> filtrarPorPatron(List<String> textos, String patron) throws RemoteException {
        Pattern p = Pattern.compile(patron);
        return textos.stream().filter(t -> p.matcher(t).find()).toList();
    }

    public static void main(String[] args) {
        try {
            Servidor obj = new Servidor();
            ServicioRemoto stub = (ServicioRemoto) UnicastRemoteObject.exportObject(obj, 0);

            Registry registry = LocateRegistry.createRegistry(1099); // puerto por defecto
            registry.rebind("ServicioRemoto", stub);

            System.out.println("Servidor RMI listo y escuchando...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
