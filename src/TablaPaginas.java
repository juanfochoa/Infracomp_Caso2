package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TablaPaginas {
    private List<Pagina> paginas;
    private int[] marcosRam;
    private int numMarcos;

    public TablaPaginas(int numPaginas, int numMarcos){
        this.numMarcos = numMarcos;
        this.paginas = new ArrayList<>(numPaginas);
        this.marcosRam = new int[numMarcos];

        // Inicializar todas las páginas
        for (int i = 0; i<numPaginas; i++){
            paginas.add(new Pagina(i));
        }

        // Inicializar marcos como vacios (-1)
        Arrays.fill(marcosRam, -1);
    }

    public synchronized boolean verificarReferencia(Referencia ref){
        int numPagina = ref.getNumeroPagina();

        if (numPagina >= paginas.size()){
            System.err.println("Error: Número de página fuera de rango: "+ numPagina);
            return false;
        }
        return paginas.get(numPagina).isEnRam();
    }

    public synchronized void actualizarPagina(Referencia ref){
        int numPagina = ref.getNumeroPagina();
        Pagina pagina = paginas.get(numPagina);

        if (!pagina.isEnRam()){
            int marcoLibre = encontrarMarcoLibre();

            if (marcoLibre != -1){
                cargarPaginaEnMarco(numPagina, marcoLibre);
            } else {
                reemplazarPagina(numPagina);
            }
        }
        pagina.marcarComoUsada();

        if (ref.esEscritura()){
            pagina.marcarComoModificada();
        }
    }

    private int encontrarMarcoLibre(){
        for (int i = 0; i < marcosRam.length; i++){
            if (marcosRam[i] == -1){
                return i;
            }
        }
        return -1;
    }

    private void cargarPaginaEnMarco(int numPagina, int numMarco){
        marcosRam[numMarco] = numPagina;
        Pagina pagina = paginas.get(numPagina);
        pagina.setMarcoAsignado(numMarco);
    }

    public synchronized void reemplazarPagina(int nuevaPagina){
        // Buscar la mejor candidata según el algoritmo NRU
        int mejorClase = 4; // Mayor que cualquier clase posible
        int mejorCandidato = -1;

        for (int i=0; i<marcosRam.length; i++){
            int paginaActual = marcosRam[i];
            if (paginaActual != -1){
                Pagina pagina = paginas.get(paginaActual);
                int clase = pagina.getClaseNRU();

                if (clase < mejorClase) {
                    mejorClase = clase;
                    mejorCandidato = i;


                    if (mejorClase == 0) {
                        break;
                    }
                }
            }
        }

        if (mejorCandidato != -1){
            int paginaAntigua = marcosRam[mejorCandidato];
            paginas.get(paginaAntigua).setMarcoAsignado(-1);

            cargarPaginaEnMarco(nuevaPagina, mejorCandidato);
        }
    }


    public synchronized void actualizarEstadoPaginas() {
        for (Pagina pagina : paginas){
            pagina.reiniciarBitUso();
        }
    }

    public List<Pagina> getPaginas() {
        return new ArrayList<>(paginas);
    }

}
