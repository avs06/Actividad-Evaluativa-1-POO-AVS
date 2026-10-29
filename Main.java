import java.util.Scanner;

class Servidor {
    String nombre;          // nombre del servidor
    String origen;          // interno o externo
    String critico;         // si o no
    int intentosTotales;    // total de intentos
    int intentosFallidos;   // intentos fallidos

    // Comprueba todas las reglas de validación
    boolean datosValidos() {
        if (nombre == null || origen == null || critico == null) {
            return false;
        }

        String nombreLimpio = nombre.trim();    // Elimina espacios al inicio y al final
        String origenLimpio = origen.trim();
        String criticoLimpio = critico.trim();
        boolean nombreValido = nombreLimpio.length() > 0;   
        boolean origenValido = origenLimpio.equalsIgnoreCase("interno") || origenLimpio.equalsIgnoreCase("externo");    // Ignora mayúsculas y minúsculas
        boolean criticoValido = criticoLimpio.equalsIgnoreCase("si") || criticoLimpio.equalsIgnoreCase("no");
        boolean totalesValidos = intentosTotales > 0;
        boolean fallidosValidos = intentosFallidos >= 0 && intentosFallidos <= intentosTotales;

        return nombreValido && origenValido && criticoValido && totalesValidos && fallidosValidos;
    }

    // Devuelve el estado del servidor según el orden de prioridad
    String evaluarEstado() {
        if (!datosValidos()) {
            return "DATOS INVALIDOS";
        }

        boolean esExterno = origen.trim().equalsIgnoreCase("externo");
        boolean esCritico = critico.trim().equalsIgnoreCase("si");

        // BLOQUEADO
        if (intentosFallidos >= 5 || (esCritico && esExterno && intentosFallidos >= 3)) {
            return "BLOQUEADO";
        }
        // ALERTA (si no está bloqueado)
        else if ((intentosFallidos == 3 || intentosFallidos == 4) || (esExterno && intentosFallidos >= 2)) {
            return "ALERTA";
        }
        // NORMAL
        else {
            return "NORMAL";
        }
    }

    // Calcula el porcentaje de fallos sobre el total de intentos
    double porcentajeFallos() {
        return (intentosFallidos * 100.0) / intentosTotales;
    }

    // Muestra por consola la linea de resumen del servidor
    void mostrarResumen() {
        String mostrarNombre = nombre.trim();
        String estado = evaluarEstado();

        if (datosValidos()) {
            double porcentaje = porcentajeFallos();
            System.out.println(mostrarNombre + " | " + intentosFallidos + "/" + intentosTotales + " | " + porcentaje + "% | " + estado);
        } else {
            System.out.println(mostrarNombre + " | " + intentosFallidos + "/" + intentosTotales + " | No porcentaje | " + estado);
        }
    }
}

