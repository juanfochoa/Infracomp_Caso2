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

}
