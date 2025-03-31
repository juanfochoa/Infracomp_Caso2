package src;

import java.util.Scanner;

public class SimuladorMemoriaVirtual {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            mostrarMenu();
            String opcion = sc.nextLine().trim();

            switch (opcion) {
                case "1":
                    opcionGeneracionReferencias();
                    break;
                case "2":
                    // Aquí se llamaría a la funcionalidad de la opción 2 (simulación de memoria)
                    System.out.println("Opción 2 seleccionada (Funcionalidad en desarrollo).");
                    break;
                case "0":
                    System.out.println("Saliendo del programa...");
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
                    break;
            }
        }
        sc.close();
    }

    /**
     * Muestra el menú de opciones al usuario.
     */
    private static void mostrarMenu() {
        System.out.println("\n----- Menú -----");
        System.out.println("1. Generación de las referencias");
        System.out.println("2. Simulación de memoria");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    /**
     * Funcionalidad para la Opción 1: Generación de las referencias.
     * Solicita el tamaño de página y el nombre del archivo de imagen (sin la ruta),
     * construye la ruta relativa y llama a la generación de referencias.
     */
    private static void opcionGeneracionReferencias() {
        Scanner sc = new Scanner(System.in);

        try {
            // Solicitar parámetros para la opción 1
            System.out.print("Ingrese el tamaño de página (en bytes): ");
            int tamanoPagina = Integer.parseInt(sc.nextLine().trim());

            System.out.print("Ingrese el nombre del archivo de imagen (solo el nombre, sin ruta): ");
            String nombreImagen = sc.nextLine().trim();

            // Construir la ruta relativa; se asume que las imágenes se encuentran en la carpeta "imagenes"
            String rutaImagen = "imagenes/" + nombreImagen;

            // Crear instancia del generador de referencias y ejecutar la funcionalidad
            GeneradorReferencias generador = new GeneradorReferencias(tamanoPagina, rutaImagen);
            generador.generarReferencias();

            System.out.println("Archivo de referencias generado correctamente.");
        } catch (NumberFormatException e) {
            System.out.println("Error: El tamaño de página debe ser un número entero.");
        } catch (Exception e) {
            System.out.println("Ocurrió un error durante la generación de referencias: " + e.getMessage());
        }
    }
}
