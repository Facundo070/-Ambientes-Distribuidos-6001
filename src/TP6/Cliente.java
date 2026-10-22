package TP6;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            ServicioRemoto servicio = (ServicioRemoto) registry.lookup("ServicioRemoto");

            Scanner sc = new Scanner(System.in);
            int opcion;

            do {
                System.out.println("=== Menú Cliente RMI ===");
                System.out.println("1. Calcular estadísticas");
                System.out.println("2. Validar cadena");
                System.out.println("3. Filtrar por patrón");
                System.out.println("0. Salir");
                opcion = sc.nextInt();
                sc.nextLine();

                switch (opcion) {
                    case 1 -> {
                        List<Double> datos = Arrays.asList(10.0, 20.0, 30.0, 40.0);
                        Estadisticas est = servicio.calcularEstadisticas(datos);
                        System.out.println("Promedio: " + est.getPromedio());
                        System.out.println("Máximo: " + est.getMaximo());
                        System.out.println("Mínimo: " + est.getMinimo());
                        System.out.println("Desviación: " + est.getDesviacion());
                    }
                    case 2 -> {
                        System.out.print("Ingrese cadena a validar: ");
                        String dato = sc.nextLine();
                        System.out.println("¿Es válido? " + servicio.validarCadena(dato));
                    }
                    case 3 -> {
                        List<String> textos = Arrays.asList("hola mundo", "middleware", "hola RMI", "RPC test");
                        System.out.print("Ingrese patrón: ");
                        String patron = sc.nextLine();
                        List<String> filtrados = servicio.filtrarPorPatron(textos, patron);
                        System.out.println("Coincidencias: " + filtrados);
                    }
                }
            } while (opcion != 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
