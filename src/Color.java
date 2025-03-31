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

    public int getRojo() {
        return rojo;
    }

    public int getVerde(){
        return verde;
    }

    public int getAzul() {
        return azul;
    }

}
