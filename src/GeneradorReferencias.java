package src;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class GeneradorReferencias {

    private int tamanoPagina;
    private String rutaImagen;  // Ejemplo: "imagenes/nombre.bmp"

    public GeneradorReferencias(int tamanoPagina, String rutaImagen) { //genera referencias a la memoria virtual
        this.tamanoPagina = tamanoPagina;
        this.rutaImagen = rutaImagen;
    }
    public void generarReferencias() {
        try {

            Imagen img = new Imagen(rutaImagen);
            int NF = img.getAlto();
            int NC = img.getAncho();

            int sizeImagen = NF * NC * 3;
            int sizeFiltro = 3 * 3 * 4;
            int sizeFiltros = sizeFiltro * 2;
            int sizeRespuesta = NF * NC * 3;

            int totalBytes = sizeImagen + sizeFiltros + sizeRespuesta;
            int NP = (int) Math.ceil((double) totalBytes / tamanoPagina);

            List<String> referencias = new ArrayList<>();

            int baseImagen = 0;
            int baseFiltroX = sizeImagen;
            int baseFiltroY = baseFiltroX + sizeFiltro;
            int baseRespuesta = baseFiltroY + sizeFiltro;

            for (int i = 1; i <= NF - 2; i++) {
                for (int j = 1; j <= NC - 2; j++) {
                    // 1. Referencias para el vecindario 3x3 de la matriz Imagen (3 canales por píxel)
                    for (int di = -1; di <= 1; di++) {
                        for (int dj = -1; dj <= 1; dj++) {
                            int row = i + di;
                            int col = j + dj;
                            char[] canales = {'r', 'g', 'b'};
                            for (int c = 0; c < 3; c++) {
                                // Calcular offset en la matriz Imagen (almacenada en row-major order)
                                int offset = ((row * NC) + col) * 3 + c;
                                int pagina = offset / tamanoPagina;
                                int desp = offset % tamanoPagina;
                                String ref = "Imagen[" + row + "][" + col + "]." + canales[c] + ","
                                        + pagina + "," + desp + ",R";
                                referencias.add(ref);
                            }
                        }
                    }
                    // 2. Referencias para la matriz SOBEL_X: 3x3, cada elemento accedido 3 veces
                    for (int fi = 0; fi < 3; fi++) {
                        for (int fj = 0; fj < 3; fj++) {
                            for (int rep = 0; rep < 3; rep++) {
                                int offset = baseFiltroX + ((fi * 3 + fj) * 4);
                                int pagina = offset / tamanoPagina;
                                int desp = offset % tamanoPagina;
                                String ref = "SOBEL_X[" + fi + "][" + fj + "],"
                                        + pagina + "," + desp + ",R";
                                referencias.add(ref);
                            }
                        }
                    }
                    // 3. Referencias para la matriz SOBEL_Y: 3x3, cada elemento accedido 3 veces
                    for (int fi = 0; fi < 3; fi++) {
                        for (int fj = 0; fj < 3; fj++) {
                            for (int rep = 0; rep < 3; rep++) {
                                int offset = baseFiltroY + ((fi * 3 + fj) * 4);
                                int pagina = offset / tamanoPagina;
                                int desp = offset % tamanoPagina;
                                String ref = "SOBEL_Y[" + fi + "][" + fj + "],"
                                        + pagina + "," + desp + ",R";
                                referencias.add(ref);
                            }
                        }
                    }
                    // 4. Referencias para escribir el píxel resultado en la matriz Respuesta (Rta): 3 canales
                    char[] canales = {'r', 'g', 'b'};
                    for (int c = 0; c < 3; c++) {
                        int offset = baseRespuesta + ((i * NC) + j) * 3 + c;
                        int pagina = offset / tamanoPagina;
                        int desp = offset % tamanoPagina;
                        String ref = "Rta[" + i + "][" + j + "]." + canales[c] + ","
                                + pagina + "," + desp + ",W";
                        referencias.add(ref);
                    }
                }
            }

            // Número total de referencias generadas
            int NR = referencias.size();

            // Escribir el archivo de referencias ("referencias.txt")
            PrintWriter out = new PrintWriter(new FileWriter("referencias.txt"));
            out.println("TP=" + tamanoPagina);
            out.println("NF=" + NF);
            out.println("NC=" + NC);
            out.println("NR=" + NR);
            out.println("NP=" + NP);
            for (String ref : referencias) {
                out.println(ref);
            }
            out.close();

            System.out.println("Se generaron " + NR + " referencias y se escribió el archivo 'referencias.txt'.");

        } catch (Exception e) {
            System.out.println("Error en la generación de referencias: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
