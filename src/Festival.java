import java.time.LocalDate;
import java.util.HashSet;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Un objeto de esta clase almacena los datos de un
 * festival.
 * Todos los festivales tiene un nombre, se celebra en un lugar
 * en una determinada fecha, dura una serie de d&iacute;as y
 * se engloba en un conjunto determinado de estilos
 *
 */
public class Festival {
    private final String nombre;
    private final String lugar;
    private final LocalDate fechaInicio;
    private final int duracion;
    private final HashSet<Estilo> estilos;


    public Festival(String nombre, String lugar, LocalDate fechaInicio, int duracion, HashSet<Estilo> estilos) {
        this.nombre = nombre;
        this.lugar = lugar;
        this.fechaInicio = fechaInicio;
        this.duracion = duracion;
        this.estilos = estilos;

    }

    public String getNombre() {
        return nombre;
    }

    public String getLugar() {
        return lugar;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public int getDuracion() {
        return duracion;
    }

    public HashSet<Estilo> getEstilos() {
        return estilos;
    }

    public void addEstilo(Estilo estilo) {
        this.estilos.add(estilo);
    }

    /**
     * devuelve el mes de celebración del festival, como
     * valor enumerado
     */
    public Mes getMes() {
        int month = fechaInicio.getMonthValue();
        Mes mes = null;

        switch (month){
            case 1:
                mes = Mes.ENERO;
                break;
            case 2:
                mes = Mes.FEBRERO;
                break;
            case 3:
                mes = Mes.MARZO;
                break;
            case 4:
                mes = Mes.ABRIL;
                break;
            case 5:
                mes = Mes.MAYO;
                break;
            case 6:
                mes = Mes.JUNIO;
                break;
            case 7:
                mes = Mes.JULIO;
                break;
            case 8:
                mes = Mes.AGOSTO;
                break;
            case 9:
                mes = Mes.SEPTIEMBRE;
                break;
            case 10:
                mes = Mes.OCTUBRE;
                break;
            case 11:
                mes = Mes.NOVIEMBRE;
                break;
            case 12:
                mes = Mes.DICIEMBRE;
                break;
        }
        return mes;
    }

    /**
     * @param otro el festival a comparar
     * @return true si el festival actual empieza
     * en un fecha anterior a otro
     */
    public boolean empiezaAntesQue(Festival otro) {
        return fechaInicio.isBefore(otro.getFechaInicio());
    }

    /**
     *
     * @param otro el festival a comparar
     * @return true si el festival actual empieza
     * en un fecha posteior a otro
     */
    public boolean empiezaDespuesQue(Festival otro) {
        return fechaInicio.isAfter(otro.getFechaInicio());
    }

    /**
     * @return true si el festival ya ha concluido
     */
    public boolean haConcluido() {

        return fechaInicio.isBefore(LocalDate.now());
    }

    /**
     * Representación textual del festival, exactamente
     * como se indica en el enunciado
     */
    @Override
    public String toString() {
        String cadena = "";

        cadena = cadena.concat(nombre + "\t\t\t" + estilos.toString()) + "\n";
        cadena = cadena.concat(lugar + "\n");

        if (fechaInicio.isAfter(LocalDate.now()) || LocalDate.now().isAfter(fechaInicio.plusDays(duracion))){
            LocalDate fechaFin = null;
            if(duracion == 1){
                fechaFin = fechaInicio;
            }
            else {
                int dia = fechaInicio.getDayOfMonth();
                String mes = String.valueOf(this.getMes());
                cadena = cadena.concat(dia + " " + mes + " - ");
                fechaFin = fechaInicio.plusDays(duracion);
            }
            if (this.haConcluido()) {
                cadena = cadena.concat(String.valueOf(fechaFin.getDayOfMonth()) + " " + this.getMes() + " " + fechaFin.getYear() + " (concluido)");
            } else {
                cadena = cadena.concat(fechaFin + " (ON)");
            }
        }
        else if(fechaInicio.isAfter(LocalDate.now())) {
            cadena = cadena.concat(String.valueOf(fechaInicio) + "(quedan " + DAYS.between(fechaInicio, LocalDate.now()) + " dias)");
        }

        cadena = cadena.concat("\n-----------------------------------" );


        return cadena;
    }

    /**
     * Código para probar la clase Festival
     *
     */
    public static void main(String[] args) {
        System.out.println("Probando clase Festival");
        String datosFestival = "Gazpatxo Rock : " +
                "valencia: 28-02-2022  :1  :rock" +
                ":punk " +
                ": hiphop ";
        Festival f1 = FestivalesIO.parsearLinea(datosFestival);
        System.out.println(f1);

        datosFestival = "black sound fest:badajoz:05-02-2022:  21" +
                ":rock" + ":  blues";
        Festival f2 = FestivalesIO.parsearLinea(datosFestival);
        System.out.println(f2);

        datosFestival = "guitar bcn:barcelona: 28-01-2022 :  170" +
                ":indie" + ":pop:fusion";
        Festival f3 = FestivalesIO.parsearLinea(datosFestival);
        System.out.println(f3);

        datosFestival = "  benidorm fest:benidorm:26-01-2022:3" +
                ":indie" + ": pop  :rock";
        Festival f4 = FestivalesIO.parsearLinea(datosFestival);
        System.out.println(f4);


        System.out.println("\nProbando empiezaAntesQue() empiezaDespuesQue()" +
                "\n");
        if (f1.empiezaAntesQue(f2)) {
            System.out.println(f1.getNombre() + " empieza antes que " + f2.getNombre());
        } else if (f1.empiezaDespuesQue(f2)) {
            System.out.println(f1.getNombre() + " empieza después que " + f2.getNombre());
        } else {
            System.out.println(f1.getNombre() + " empieza el mismo día que " + f2.getNombre());
        }

        System.out.println("\nProbando haConcluido()\n");
        System.out.println(f4);
        System.out.println(f4.getNombre() + " ha concluido? " + f4.haConcluido());
        System.out.println(f1);
        System.out.println(f1.getNombre() + " ha concluido? " + f1.haConcluido());

        System.out.println(LocalDate.now());
    }
}
