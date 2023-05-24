// Importando las clases necesarias
import org.jpl7.*;

import java.util.Map;
import java.util.Scanner;
import java.io.IOException; 

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        // Ruta a tu programa Prolog
        String[] p = {"-q", "-f", "E:\\Casa\\Fabricio\\TEC\\Lenguajes\\Prolog\\Prolog\\src\\proyecto2.pl"};

        // Creando una consulta que consultará el programa Prolog
        Query q1 = new Query("consult", new Term[] {new Atom("E:\\Casa\\Fabricio\\TEC\\Lenguajes\\Prolog\\Prolog\\src\\proyecto2.pl")});

        // Si la consulta tiene una solución, imprimirá 'consult succeeded'. Si no, imprimirá 'consult failed'.
        System.out.println("consult " + (q1.hasSolution() ? "succeeded" : "failed"));

        // Creando un objeto Scanner para leer la entrada del usuario
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        // Bucle principal del programa
        while (choice != 0) {
            // Mostrar el menú principal
            displayMenu();
            // Leer la elección del usuario
            choice = scanner.nextInt();
            scanner.nextLine(); // Consumir el carácter de nueva línea

            // Realizar la acción correspondiente a la elección del usuario
            switch (choice) {
                case 1:
                    // Diagnostica un problema en base a la entrada del usuario
                    diagnoseIssue(scanner);
                    pressEnterToContinue();
                    break;
                case 2:
                    // Ejecuta una consulta Prolog personalizada en base a la entrada del usuario
                    executeQuery(scanner);
                    pressEnterToContinue();
                    break;
                case 0:
                    // Salir del programa
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    // Si la elección del usuario no fue válida, imprimir un mensaje y pedir otra elección
                    System.out.println("Opción invalida. Por favor, selecciona una opción valida.");
                    pressEnterToContinue();
                    break;
            }
        }
    }

    public static void displayMenu() {
    	// Limpiar la pantalla
    	clearScreen();
        // Imprimir el menú principal
        System.out.println("----- Menu Principal -----");
        System.out.println("1. Diagnosticar problema");
        System.out.println("2. Consultar base de conocimiento");
        System.out.println("0. Salir");
        System.out.print("Selecciona una opcion: ");
    }

    public static void diagnoseIssue(Scanner scanner) {
        // Pedir al usuario que introduzca los atributos del problema
        System.out.print("Ingrese los atributos del problema separados por comas (por ejemplo, lenta,sin_encender): ");
        String input = scanner.nextLine();
        String[] attributes = input.split(",");

        // Formar una consulta Prolog basada en la entrada del usuario
        String query = "diagnosticar([";

        for (int i = 0; i < attributes.length; i++) {
            query += "'" + attributes[i].trim() + "'";
            if (i < attributes.length - 1) {
                query += ",";
            }
        }

        query += "]).";


        // Ejecutar la consulta Prolog
        Query q2 = new Query(query);

        // Limpiar la pantalla
        clearScreen();

        // Iterar sobre las soluciones de la consulta
        while (q2.hasMoreSolutions()) {
            java.util.Map<String, Term> solution = q2.nextSolution();
            Term problema = solution.get("Problema");
            Term respuesta = solution.get("Respuesta");
            if (problema != null && respuesta != null) {
                System.out.println("Diagnostico: " + problema);
                System.out.println("Solucion: " + respuesta);
                System.out.println();
            }
        }
        q2.close();  // Cerrar la consulta después de procesar todas las soluciones
    }

    public static void executeQuery(Scanner scanner) {
        // Pedir al usuario que introduzca la consulta en Prolog
        System.out.print("Ingrese la consulta en Prolog: ");
        String query = scanner.nextLine();

        // Crear y ejecutar la consulta Prolog
        Query q = new Query(query);

        // Limpiar la pantalla
        clearScreen();

        // Iterar sobre las soluciones de la consulta
        while (q.hasMoreSolutions()) {
            java.util.Map<String, Term> solution = q.nextSolution();

            for (Map.Entry<String, Term> entry : solution.entrySet()) {
                String varName = entry.getKey();
                Term value = entry.getValue();
                System.out.println(varName + " = " + value);
            }
        }
        q.close();  // Cerrar la consulta después de procesar todas las soluciones
    }

    public static void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            // Ignorar cualquier excepción
        }
    }

    public static void pressEnterToContinue() {
        System.out.println("\n Presiona Enter para volver al menu principal...");
        try {
            System.in.read();
        } catch (Exception e) {
            // Ignorar cualquier excepción
        }
    }
}


/*ejemplos
 * Diagnostico:
 * lenta, sin_encender
 * conexion_interrumpida, velocidad_lenta
 * sin_imagen, parpadeo
 * sin_audio, distorsionado
 * 
 * Consulta:
 * diagnostico(Problema, Atributos, Solucion).
 * diagnostico(Problema, Atributos, Solucion), member(Atributo, Atributos).
 */




