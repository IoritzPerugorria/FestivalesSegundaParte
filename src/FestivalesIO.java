import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

/**
 * La clase contiene méodos estáticos que permiten
 * cargar la agenda de festivales leyendo los datos desde
 * un fichero
 * @author Ioritz Perugorria Aldako
 */
public class FestivalesIO {


    public static void cargarFestivales(AgendaFestivales agenda) {
        Scanner sc = null;
        try {
            sc = new Scanner(FestivalesIO.class.getResourceAsStream("/festivales.csv"));
            while (sc.hasNextLine()) {
                String lineaFestival = sc.nextLine();
                Festival festival = parsearLinea(lineaFestival);
                agenda.addFestival(festival);

            }
        } finally {
            if (sc != null) {
                sc.close();
            }
        }

    }

    /**
     * se parsea la línea extrayendo sus datos y creando y
     * devolviendo un objeto Festival
     * @param lineaFestival los datos de un festival
     * @return el festival creado
     */
    public static Festival parsearLinea(String lineaFestival) {
        String nombre = "";
        String lugar = "";
        LocalDate fecha = null;
        int dias = 0;
        HashSet<Estilo> estilos = new HashSet<>();

        String[] splited = lineaFestival.split(":");

        for (int rep = 0; rep < splited.length; rep ++){
            switch (rep){
                case 0:
                    splited[rep] = eliminarEspacios(splited[rep]);
                    splited[rep] = primeraMayuscula(splited[rep]);
                    nombre = nombre.concat(splited[rep]);

                case 1:
                    splited[rep] = eliminarEspacios(splited[rep]);
                    splited[rep] = splited[rep].toUpperCase();
                    lugar = lugar.concat(splited[rep]);

                case 2:
                    splited[rep] = eliminarEspacios(splited[rep]);
                    String[] date = splited[rep].split("-");

                    fecha = LocalDate.of(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0]));

                case 3:
                    splited[rep] = eliminarEspacios(splited[rep]);
                    dias = Integer.parseInt(splited[rep]);

                case 4:
                    for (int num = 4; num < splited.length; num ++){
                        splited[rep] = eliminarEspacios(splited[rep]);
                        splited[rep] = splited[rep].toUpperCase();
                        
                        Estilo estilo = Estilo.valueOf(splited[rep]);
                        
                        estilos.add(estilo);
                    }
            }
        }
        return new Festival(nombre, lugar, fecha, dias, estilos);
    }

    /**
     * Este metodo se usa en parsearLinea() para eliminar los
     * espacios al principio y al final de cada substring.
     * Primero se usa indexOf(" "), y si se encuentra
     * el espacio al rpincipio o al final del String, lo
     * elimina. Si encuentra un espacio que no esta al
     * principio o al final, lo ignora.
     */
    public static String eliminarEspacios(String texto){
        int posicion;

        while (true){
            posicion = texto.indexOf(" ");
            if (posicion == 0){
                texto = texto.substring(posicion);
            }
            else if (posicion == texto.length() - 1){
                texto = texto.substring(0, texto.length() - 1);
            }
            else{
                break;
            }
        }
        return texto;
    }

    /**
     * El metodo convierte la primera letra de cada
     * palabra del String texto en mayuscula. Tambien,
     * elimina cualquier espacio excesivo que pueda haber
     * entre las palabras;
     */
    public static String primeraMayuscula(String texto){
        String[] substrings = texto.split(" ");
        String resultado = "";

            for (int rep = 0; rep < substrings.length; rep++) {
                substrings[rep] = eliminarEspacios(substrings[rep]);

                substrings[rep] = substrings[rep].substring(0,1).toUpperCase() +
                                  substrings[rep].substring(1).toLowerCase();

                resultado = resultado.concat(substrings[rep]);
                resultado = resultado.concat(" ");
            }
        return resultado;
    }
}
