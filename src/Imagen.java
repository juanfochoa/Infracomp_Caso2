package src;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Imagen {
    byte[] header = new byte[54];
    private DatosImagen datosImagen;
    private int alto;
    private int ancho; // en pixeles
    private int padding;


    /***
     * Método para crear una matriz imagen a partir de un archivo.
     * @param nombre: nombre del archivo. El formato debe ser BMP de 24 bits de bit depth
     * @pos la matriz imagen tiene los valores correspondientes a la imagen
     * almacenada en el archivo.
     * */


    public Imagen(String nombre) {
        try {
            FileInputStream fis = new FileInputStream(nombre);
            fis.read(header);

            // Extraer el ancho y alto de la imagen desde la cabecera
            // Almacenados en little endian
            ancho = ((header[21] & 0xFF) << 24) | ((header[20] & 0xFF) << 16) |
                    ((header[19] & 0xFF) << 8) | (header[18] & 0xFF);
            alto = ((header[25] & 0xFF) << 24) | ((header[24] & 0xFF) << 16) |
                    ((header[23] & 0xFF) << 8) | (header[22] & 0xFF);
            System.out.println("Ancho: " + ancho + " px, Alto: " + alto + " px");

            datosImagen = new DatosImagen(ancho, alto);

            // El tamaño de la fila debe ser múltiplo de 4 bytes
            int rowSizeSinPadding = ancho * 3;
            padding = (4 - (rowSizeSinPadding % 4)) % 4;

            // Leer y modificar los datos de los píxeles
            // (en formato RGB, pero almacenados en orden BGR)
            byte[] pixel = new byte[3];
            for (int i = 0; i < alto; i++) {
                for (int j = 0; j < ancho; j++) {
                    // Leer los 3 bytes del píxel (B, G, R)
                    fis.read(pixel);
                    Color color = new Color(pixel[2] & 0xFF, pixel[1] & 0xFF, pixel[0] & 0xFF);
                    datosImagen.setColor(i, j, color);
                }
                fis.skip(padding);
            }
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método para escribir una imagen a un archivo en formato BMP
     *
     * @param output: nombre del archivo donde se almacenará la imagen.
     *                Se espera que se invoque para almacenar la imagen modificada.
     * @pre la matriz imagen debe haber sido inicializada con una imagen
     * @pos se creó el archivo en formato bmp con la información de la matriz imagen
     */
    public void escribirImagen(String output) {
        byte pad = 0;
        try {
            FileOutputStream fos = new FileOutputStream(output);
            fos.write(header);

            for (int i = 0; i < alto; i++) {
                for (int j = 0; j < ancho; j++) {
                    // Leer los 3 bytes del píxel (B, G, R)
                    Color color = datosImagen.getColor(i,j);
                    byte[] pixel = new byte[3];
                    pixel[0] = (byte) color.getAzul();
                    pixel[1] = (byte) color.getVerde();
                    pixel[2] = (byte) color.getRojo();
                    fos.write(pixel);
                }
                for (int k = 0; k < padding; k++) {
                    fos.write(pad);
                }
            }
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DatosImagen getDatosImagen(){
        return datosImagen;
    }

    public int getAncho(){
        return ancho;
    }

    public int getAlto(){
        return alto;
    }
}