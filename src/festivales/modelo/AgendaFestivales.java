package festivales.modelo;

import java.util.*;


/**
 * Esta clase guarda una agenda con los festivales programados
 * en una serie de meses
 *
 * La agenda guardalos festivales en una colección map
 * La clave del map es el mes (un enumerado festivales.modelo.festivales.modelo.Mes)
 * Cada mes tiene asociados en una colección ArrayList
 * los festivales  de ese mes
 *
 * Solo aparecen los meses que incluyen algún festival
 *
 * Las claves se recuperan en orden alfabéico
 *
 */
public class AgendaFestivales {
    private TreeMap<Mes, ArrayList<Festival>> agenda;
    
    public AgendaFestivales() {
        this.agenda = new TreeMap<>();
    }

    /**
     * añade un nuevo festival a la agenda
     *
     * Si la clave (el mes en el que se celebra el festival)
     * no existe en la agenda se creará una nueva entrada
     * con dicha clave y la colección formada por ese único festival
     *
     * Si la clave (el mes) ya existe se añade el nuevo festival
     * a la lista de festivales que ya existe ese ms
     * insertándolo de forma que quede ordenado por nombre de festival.
     * Para este segundo caso usa el método de ayuda
     * obtenerPosicionDeInsercion()
     *
     */
    public void addFestival(Festival festival) {
        ArrayList<Festival> festivales;
        Mes mes = festival.getMes();

        if (agenda.containsKey(mes)){
            festivales = new ArrayList<>(agenda.get(mes));
        }
        else{
            festivales = new ArrayList<>();
        }
        festivales.add(festival);
        agenda.put(mes, festivales);
    }

    /**
     *
     * @param festivales una lista de festivales
     * @param festival
     * @return la posición en la que debería ir el nuevo festival
     * de forma que la lista quedase ordenada por nombre
     */
    private int obtenerPosicionDeInsercion(ArrayList<Festival> festivales, Festival festival) {
        int posicion;

       if (festivales.isEmpty()){
           return 1;
       }
       else {
           if (festivales.contains(festival)){
               posicion = festivales.indexOf(festival);
           }
           else{
               festivales.add(festival);
               festivales.sort(null);
               posicion = festivales.indexOf(festival);
               festivales.remove(festival);
           }

           return posicion;
       }
    }

    /**
     * Representación textual del festival
     * De forma eficiente
     *  Usa el conjunto de entradas para recorrer el map
     */
    @Override
    public String toString() {

        String cadena = "";

        ArrayList<Mes> meses = new ArrayList<>(agenda.keySet());
        ArrayList<Festival> festivales;

        for (Mes mes :meses){
            festivales = new ArrayList<>(agenda.get(mes));
            cadena = cadena.concat("\n\n" + mes + " (" + this.festivalesEnMes(mes) + " festival/es)" + "\n\n");
            for (Festival festival : festivales){
                cadena = cadena.concat(festival.toString());
            }
        }

        return cadena;


    }

    /**
     *
     * @param mes el mes a considerar
     * @return la cantidad de festivales que hay en ese mes
     * Si el mes no existe se devuelve -1
     */
    public int festivalesEnMes(Mes mes) {
        if (agenda.containsKey(mes)) {
            ArrayList<Festival> festivales = new ArrayList<>(agenda.get(mes));
            return festivales.size();
        }
        else{
            return -1;
        }
    }

    /**
     * Se trata de agrupar todos los festivales de la agenda
     * por estilo.
     * Cada estilo que aparece en la agenda tiene asociada una colección
     * que es el conjunto de nombres de festivales que pertenecen a ese estilo
     * Importa el orden de los nombres en el conjunto
     *
     * Identifica el tipo exacto del valor de retorno
     */
    public  TreeMap<Estilo, TreeSet<String>>   festivalesPorEstilo() {
        TreeMap<Estilo, TreeSet<String>> estilos = new TreeMap<>();
        ArrayList<Mes> meses = new ArrayList<>(agenda.keySet());
        ArrayList<Festival> festivales;

        for (Estilo estilo : Estilo.values()) {

            TreeSet<String> festival_a_anadir = new TreeSet<>();

            for (Mes mes : meses){
                festivales = new ArrayList<>(agenda.get(mes));

                for (Festival festival : festivales){
                    ArrayList<Estilo> lista_estilos_fest = new ArrayList<>(festival.getEstilos());

                    for(Estilo estilo_fest : lista_estilos_fest){
                        if (estilo == estilo_fest){
                            festival_a_anadir.add(festival.getNombre());
                        }
                    }
                }
            }
            if (!festival_a_anadir.isEmpty()){
                estilos.put(estilo, festival_a_anadir);
            }

        }
        return estilos;
    }

    /**
     * Se cancelan todos los festivales organizados en alguno de los
     * lugares que indica el conjunto en el mes indicado. Los festivales
     * concluidos o que no empezados no se tienen en cuenta
     * Hay que borrarlos de la agenda
     * Si el mes no existe se devuelve -1
     *
     * Si al borrar de un mes los festivales el mes queda con 0 festivales
     * se borra la entrada completa del map
     */
    public int cancelarFestivales(HashSet<String> lugares, Mes mes) {
        if (!agenda.containsKey(mes)){
            return -1;
        }
        else{
            ArrayList<Festival> festivales = new ArrayList<>(agenda.get(mes));

            for (Festival festival : festivales){
                if(!festival.haConcluido() && !festival.haEmpezado()) {
                    if (lugares.contains(festival.getLugar())) {
                        festivales.remove(festival);
                    }
                }
            }

            if (festivales.isEmpty()){
                agenda.remove(mes);
            }

            return 0;
        }
    }
}
