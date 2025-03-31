package src;

public class Referencia {
    private String descripcion;
    private int numeroPagina;
    private int dezplazamiento;
    private char accion;   // 'R' para lectura, 'W' para escritura

    public Referencia(String descripcion, int numeroPagina, int dezplazamiento, char accion) {
        this.descripcion = descripcion;
        this.numeroPagina = numeroPagina;
        this.dezplazamiento = dezplazamiento;
        this.accion = accion;
    }

    // Constructor para crear una referencia a partir de una línea del archivo
    public Referencia(String linea) {
        String[] partes = linea.split(",");
        if (partes.length == 4) {
            this.descripcion = partes[0];
            this.numeroPagina = Integer.parseInt(partes[1]);
            this.dezplazamiento = Integer.parseInt(partes[2]);
            this.accion = partes[3].charAt(0);
        } else {
            throw new IllegalArgumentException("Formato de línea inválido: " + linea);
        }
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getNumeroPagina() {
        return numeroPagina;
    }

    private int getDezplazamiento(){
        return dezplazamiento;
    }

    private char getAccion(){
        return accion;
    }

    public boolean esEscritura(){
        return accion == 'W';
    }

    @Override
    public String toString(){
        return descripcion + ","+numeroPagina+","+dezplazamiento+","+accion;
    }
}

