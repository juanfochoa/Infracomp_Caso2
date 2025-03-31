package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class SimuladorMemoria {
    private int numMarcos;
    private String archivoReferencias;
    private TablaPaginas tablaPaginas;
    private HiloLectorReferencias hiloLector;
    private HiloActualizadorEstado hiloActualizador;
    private int hits;
    private int fallos;

    public SimuladorMemoria(int numMarcos, String archivoReferencias){
        this.numMarcos = numMarcos;
        this.archivoReferencias = archivoReferencias;
    }

    public void simular(){
        try{
            // Cargar las referencias del archivo
            List<Referencia> referencias = ManejadorArchivos.leerReferencias(archivoReferencias);

            if (referencias.isEmpty()){
                System.out.println("No se encontraron referencias para simular.");
                return;
            }

            int numPaginas = obtenerNumPaginas(archivoReferencias);
            tablaPaginas = new TablaPaginas(numPaginas, numMarcos);

            hiloLector = new HiloLectorReferencias(referencias, tablaPaginas);
            hiloActualizador = new HiloActualizadorEstado(tablaPaginas);

            System.out.println("\n======= Iniciando simulación =======");
            System.out.println("Número de marcos: " + numMarcos);
            System.out.println("Número de páginas: " + numPaginas);
            System.out.println("Número de referencias: " + referencias.size());

            long tiempoInicio = System.nanoTime();

            hiloActualizador.start();
            hiloLector.start();

            //Esperar a que termine el hilo lector
            while (!hiloLector.getEnEjecucion()){
                Thread.yield();
            }

            // Detener el hilo actualizador
            hiloActualizador.detener();

            while(!hiloActualizador.getEnEjecucion()){
                Thread.yield();
            }

            long tiempoFin = System.nanoTime();

            // Calcular estadísticas
            this.hits = hiloLector.getHits();
            this.fallos = hiloLector.getFallos();

            // Calcular tiempos
            double tiempoReal = (tiempoFin-tiempoInicio) / 1_000_000.0;
            double tiempoSoloHits = hits * 50.0/1_000_000.0;
            double tiempoSoloFallos = fallos * 10.0;
            double tiempoTeorico = tiempoSoloHits + tiempoSoloFallos;
            double tiempoTodoRam = referencias.size() * 50.0 /1_000_000.0;
            double tiempoNadaRam = referencias.size() * 10.0;

            // Mostrar resultados
            System.out.println("\n===== Resultados de la simulación =====");
            System.out.println("Total referencias: " + (hits + fallos));
            System.out.println("Hits: " + hits + " (" + (hits * 100.0 / (hits + fallos)) + "%)");
            System.out.println("Fallos de página: " + fallos + " (" + (fallos * 100.0 / (hits + fallos)) + "%)");
            System.out.println("\n===== Tiempos =====");
            System.out.println("Tiempo real simulación: " + tiempoReal + " ms");
            System.out.println("Tiempo teórico (hits + fallos): " + tiempoTeorico + " ms");
            System.out.println("Tiempo si todo estuviera en RAM: " + tiempoTodoRam + " ms");
            System.out.println("Tiempo si nada estuviera en RAM: " + tiempoNadaRam + " ms");

        } catch (Exception e){
            System.err.println("Error: durante la simulación: "+ e.getMessage());
            e.printStackTrace();
        }
    }

    private int obtenerNumPaginas(String archivo){
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))){
            String linea;
            int lineaActual = 0;

            while ((linea = br.readLine()) != null && lineaActual < 5){
                if (lineaActual == 4) {
                    return Integer.parseInt(linea.split("=")[1]);
                }
                lineaActual++;
            }

        } catch (IOException e) {
            System.err.println("Error al leer el número de páginas: "+e.getMessage());
        }
        return 0;
    }

    public int getHits(){
        return hits;
    }

    public int getFallos(){
        return fallos;
    }
}
