package src;

public class DatosImagen {
    private Color[][] matriz;
    private int ancho;
    private int alto;

    public DatosImagen(int ancho, int alto) {
        this.ancho = ancho;
        this.alto = alto;
        this.matriz = new Color[alto][ancho];

        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                this.matriz[i][j] = Color.BLACK;
            }
        }

    }

    public Color obtenerColor(int fila, int columna){
        return matriz[fila][columna];
    }

    public void establecerColor(int fila, int columna, Color color){
        matriz[fila][columna] = color;
    }

    public int obtenerAncho(){
        return ancho;
    }

    public int obtenerAlto(){
        return alto;
    }
}
