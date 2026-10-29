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
            System.out.println(mostrarNombre + " | " + intentosFallidos + "/" + intentosTotales + " | N/D | " + estado);
        }
    }

    // Método para asignar un valor numérico al estado y poder compararlos
    int prioridad() {
        String estado = evaluarEstado();
        if (estado.equals("BLOQUEADO")) {
            return 3;
        } else if (estado.equals("ALERTA")) {
            return 2;
        } else if (estado.equals("NORMAL")) {
            return 1;
        } else {
            return 0;   // DATOS INVALIDOS
        }
    }
}

public class Main {

    // Método que compara los dos servidores e imprime cuál requiere atención prioritaria
    static void compararServidores(Servidor s1, Servidor s2) {
        if (!s1.datosValidos() || !s2.datosValidos()) {
            System.out.println("COMPARACIÓN NO DISPONIBLE");
        } else {
            int prioridad1 = s1.prioridad();
            int prioridad2 = s2.prioridad();

            if (prioridad1 > prioridad2) {
                System.out.println("ATENCIÓN PRIORITARIA: " + s1.nombre.trim());
            } else if (prioridad2 > prioridad1) {
                System.out.println("ATENCIÓN PRIORITARIA: " + s2.nombre.trim());
            } else {
                System.out.println("IGUAL PRIORIDAD");
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Creación y lectura del primer servidor
        Servidor servidor1 = new Servidor();
        System.out.print("Nombre del servidor 1: ");
        servidor1.nombre = sc.nextLine();
        System.out.print("Origen (interno/externo): ");
        servidor1.origen = sc.nextLine();
        System.out.print("Critico (si/no): ");
        servidor1.critico = sc.nextLine();
        System.out.print("Intentos totales: ");
        servidor1.intentosTotales = sc.nextInt();
        System.out.print("Intentos fallidos: ");
        servidor1.intentosFallidos = sc.nextInt();
        sc.nextLine(); // Limpiar salto de linea pendiente

        // Creación y lectura del segundo servidor
        Servidor servidor2 = new Servidor();
        System.out.print("Nombre del servidor 2: ");
        servidor2.nombre = sc.nextLine();
        System.out.print("Origen (interno/externo): ");
        servidor2.origen = sc.nextLine();
        System.out.print("Critico (si/no): ");
        servidor2.critico = sc.nextLine();
        System.out.print("Intentos totales: ");
        servidor2.intentosTotales = sc.nextInt();
        System.out.print("Intentos fallidos: ");
        servidor2.intentosFallidos = sc.nextInt();

        // Salida del reporte de seguridad
        System.out.println();
        System.out.println("========== REPORTE DE SEGURIDAD ==========");
        servidor1.mostrarResumen();
        servidor2.mostrarResumen();
        compararServidores(servidor1, servidor2);

        sc.close();
    }
}

/*
PREGUNTAS CONCEPTUALES

1. ¿Por qué dos objetos de la misma clase pueden almacenar valores diferentes en sus atributos?
Porque cada objeto tiene sus propios atributos. Aunque los dos se creen a partir de la clase Servidor, a cada uno se le pueden asignar datos distintos, pues la clase no deja de ser una plantilla.

2. ¿Qué diferencia hay entre Servidor b = a; y Servidor b = new Servidor();?
Con Servidor b = a; las dos variables hacen referencia al mismo objeto. En cambio, con new Servidor() se crea un objeto nuevo e independiente.

3. ¿Por qué es importante comprobar datosValidos() antes de calcular el porcentaje?
Porque podría haber datos incorrectos o imposibles, como un total de cero intentos. La validación evita calcular porcentajes con datos inválidos y muestra N/D, evitando así errores en el programa por operaciones matemáticas imposibles por ejemplo.
*/
