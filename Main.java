import java.util.Scanner;

class Servidor {
    String nombre;          // nombre del servidor
    String origen;          // interno o externo
    String critico;         // si o no
    int intentosTotales;    // total de intentos
    int intentosFallidos;   // intentos fallidos

    // Comprueba todas las reglas de validacion del apartado 3
    boolean datosValidos() {
        if (nombre == null || origen == null || critico == null) {
            return false;
        }

        String nombreLimpio = nombre.trim();
        String origenLimpio = origen.trim();
        String criticoLimpio = critico.trim();
        boolean nombreValido = nombreLimpio.length() > 0;
        boolean origenValido = origenLimpio.equalsIgnoreCase("interno") || origenLimpio.equalsIgnoreCase("externo");
        boolean criticoValido = criticoLimpio.equalsIgnoreCase("si") || criticoLimpio.equalsIgnoreCase("no");
        boolean totalesValidos = intentosTotales > 0;
        boolean fallidosValidos = intentosFallidos >= 0 && intentosFallidos <= intentosTotales;

        return nombreValido && origenValido && criticoValido && totalesValidos && fallidosValidos;
    }
}

