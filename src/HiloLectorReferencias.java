package src;

import java.util.List;

public class HiloLectorReferencias extends Thread{
    private List<Referencia> listaReferencias;
    private TablaPaginas tablaPaginas;
    private int hits;
    private int fallos;
    private boolean enEjecucion;
    private static final int REFERENCIAS_POR_LOTE = 10000;

    public HiloLectorReferencias(List<Referencia> listaReferencias, TablaPaginas tablaPaginas){
        this.listaReferencias = listaReferencias;
        this.tablaPaginas = tablaPaginas;
        this.hits = 0;
        this.fallos = 0;
        this.enEjecucion = false;
    }

    @Override
    public void run(){
        int contador = 0;
        for (Referencia ref : listaReferencias){
            if (!enEjecucion){
                break;
            }

            // Verificar si la página está en RAM
            boolean estaEnRam = tablaPaginas.verificarReferencia(ref);

            if (estaEnRam){
                hits++;
            } else {
                fallos++;
            }

            tablaPaginas.actualizarPagina(ref);
            contador++;

            if (contador % REFERENCIAS_POR_LOTE == 0){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Hilo lector de referencias ha terminado. Total referencias: "+
                (hits + fallos)+ ", Hits: "+ hits + ", Fallos: "+ fallos);
    }

    public void detener(){
        this.enEjecucion = false;
    }

    public int getHits(){
        return hits;
    }

    public int getFallos(){
        return fallos;
    }

    public boolean getEnEjecucion(){
        return enEjecucion;
    }
}
