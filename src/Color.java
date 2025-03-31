package src;

public class Color {

    public static final Color BLACK = new Color(0,0,0);

    private int rojo;
    private int verde;
    private int azul;

    public Color(int rojo,int verde,int azul){
        this.rojo = rojo;
        this.verde = verde;
        this.azul = azul;
    }

    public int obtenerRojo() {
        return rojo;
    }

    public int obtenerVerde(){
        return verde;
    }

    public int obtenerAzul() {
        return azul;
    }

    public void establecerRojo(int rojo) {
        this.rojo = rojo;
    }

    public void establecerVerde(int verde) {
        this.verde = verde;
    }

    public void establecerAzul(int azul) {
        this.azul = azul;
    }
}
