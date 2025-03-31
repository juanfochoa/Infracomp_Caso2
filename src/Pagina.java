package src;

public class Pagina {
    private int numeroPagina;
    private boolean usada;  //Bit R
    private boolean modificada;  // Bit M
    private boolean enRam;
    private int marcoAsignado;   // -1 si no está en RAM
    
    public Pagina(int numeroPagina){
        this.numeroPagina = numeroPagina;
        this.usada = false;
        this.modificada = false;
        this.enRam = false;
        this.marcoAsignado = -1;
    }
    
    public int getNumeroPagina(){
        return numeroPagina;
    }
    
    public boolean isUsada(){
        return usada;
    }
    
    public void setUsada(boolean usada){
        this.usada = usada;
    }

    public boolean isModificada(){
        return modificada;
    }

    public void setModificada(boolean modificada){
        this.modificada = modificada;
    }

    public boolean isEnRam(){
        return enRam;
    }

    public void setEnRam(boolean enRam){
        this.enRam = enRam;
    }

    public int getMarcoAsignado(){
        return marcoAsignado;
    }

    public void setMarcoAsignado(int marcoAsignado){
        this.marcoAsignado = marcoAsignado;
        this.enRam = (marcoAsignado != -1);
    }

    // Método para obtener la clase NRU (0-3)
    // Clase 0: No referenciada, No modificada
    // Clase 1: No referenciada, Modificada
    // Clase 2: Referenciada, No modificada
    // Clase 3: Referenciada, Modificada
    public int getClaseNRU(){
        int clase = 0;
        if (usada){
            clase += 2;
        }
        if (modificada){
            clase += 1;
        }
        return clase;
    }

    public void reiniciarBitUso(){
        this.usada = false;
    }

    public void marcarComoUsada(){
        this.usada = true;
    }

    public void marcarComoModificada(){
        this.modificada = true;
    }
}
