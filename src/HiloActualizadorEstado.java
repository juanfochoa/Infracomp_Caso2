package src;

public class HiloActualizadorEstado extends Thread {
    private TablaPaginas tablaPaginas;
    private boolean enEjecucion;

    public HiloActualizadorEstado(TablaPaginas tablaPaginas){
        this.tablaPaginas = tablaPaginas;
        this.enEjecucion = true;
    }

    @Override
    public void run(){
        while(enEjecucion){
            // Ejecutar el algoritmo NRU (No Recientemente Usada)
            // Este método debe actualizar los bits de referencia cada cierto tiempo
            tablaPaginas.actualizarEstadoPaginas();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        System.out.println("Hilo actualizador de estado ha terminado.");
    }

    public void detener(){
        this.enEjecucion = false;
    }

}
