package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ManejadorArchivos {

    public static List<Referencia> leerReferencias(String archivo){
        List<Referencia> referencias = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))){
            String linea;
            int lineaActual = 0;
            int tamanioPagina = 0;
            int numFilas = 0;
            int numColumnas = 0;
            int numReferencias = 0;
            int numPaginas = 0;

            // Leer las primeras 5 líneas de metadatos
            while ((linea = br.readLine()) != null && lineaActual <5){
                switch (lineaActual){
                    case 0: // TP
                        tamanioPagina = Integer.parseInt(linea.split("=")[1]);
                        break;
                    case 1: // NF
                        numFilas = Integer.parseInt(linea.split("=")[1]);
                        break;
                    case 2: // NC
                        numColumnas = Integer.parseInt(linea.split("=")[1]);
                        break;
                    case 3: // NR
                        numReferencias = Integer.parseInt(linea.split("=")[1]);
                        break;
                    case 4: //NP
                        numPaginas = Integer.parseInt(linea.split("=")[1]);
                        break;
                }
                lineaActual++;
            }

            // Cargar las referencias
            while ((linea = br.readLine()) != null){
                referencias.add(new Referencia(linea));
            }

            System.out.println("Archivo leído: "+archivo);
            System.out.println("Tamaño de página: "+tamanioPagina+" bytes");
            System.out.println("Imagen: "+numFilas+"x"+numColumnas+" píxeles");
            System.out.println("Total Referencias: "+numReferencias);
            System.out.println("Total páginas virtuales: "+numPaginas);
            System.out.println("Referencias cargadas: "+referencias.size());

        } catch (IOException e) {
            System.err.println("Error al leer el archivo de referencias: " + e.getMessage());
        }

        return referencias;
    }

}
