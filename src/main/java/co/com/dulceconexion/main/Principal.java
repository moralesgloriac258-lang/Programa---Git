package co.com.dulceconexion.main;

import co.com.dulceconexion.dao.ClienteDAO;
import co.com.dulceconexion.modelo.Cliente;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/** Menú de consola para administrar clientes. */
public class Principal {
    private final Scanner entrada = new Scanner(System.in);
    private final ClienteDAO clienteDAO = new ClienteDAO();

    public static void main(String[] args) {
        new Principal().iniciar();
    }

    private void iniciar() {
        System.out.println("=== Dulce Conexión | Gestión de clientes ===");
        boolean continuar = true;
        while (continuar) {
            mostrarMenu();
            String opcion = entrada.nextLine().trim();
            try {
                switch (opcion) {
                    case "1" -> registrarCliente();
                    case "2" -> listarClientes();
                    case "3" -> buscarCliente();
                    case "4" -> actualizarCliente();
                    case "5" -> eliminarCliente();
                    case "0" -> continuar = false;
                    default -> System.out.println("Opción no válida. Elige una opción del menú.");
                }
            } catch (SQLException excepcion) {
                System.out.println("No se pudo completar la operación en la base de datos.");
                System.out.println("Detalle: " + excepcion.getMessage());
                System.out.println("Verifica MySQL y la configuración DB_URL, DB_USER y DB_PASSWORD.");
            }
        }
        System.out.println("Hasta pronto.");
    }

    private void mostrarMenu() {
        System.out.println("\n1. Registrar cliente");
        System.out.println("2. Consultar todos los clientes");
        System.out.println("3. Buscar cliente por ID");
        System.out.println("4. Actualizar cliente");
        System.out.println("5. Eliminar cliente");
        System.out.println("0. Salir");
        System.out.print("Selecciona una opción: ");
    }

    private void registrarCliente() throws SQLException {
        Cliente cliente = leerDatosCliente(0);
        Cliente guardado = clienteDAO.insertar(cliente);
        System.out.println("Cliente registrado. ID asignado: " + guardado.getId());
    }

    private void listarClientes() throws SQLException {
        List<Cliente> clientes = clienteDAO.consultarTodos();
        if (clientes.isEmpty()) {
            System.out.println("Todavía no hay clientes registrados.");
            return;
        }
        System.out.println("\n--- Clientes registrados ---");
        clientes.forEach(System.out::println);
    }

    private void buscarCliente() throws SQLException {
        int id = leerId();
        Optional<Cliente> cliente = clienteDAO.consultarPorId(id);
        System.out.println(cliente.map(Object::toString).orElse("No se encontró un cliente con ese ID."));
    }

    private void actualizarCliente() throws SQLException {
        int id = leerId();
        Optional<Cliente> encontrado = clienteDAO.consultarPorId(id);
        if (encontrado.isEmpty()) {
            System.out.println("No se encontró un cliente con ese ID.");
            return;
        }
        System.out.println("Ingresa los nuevos datos:");
        Cliente cliente = leerDatosCliente(id);
        System.out.println(clienteDAO.actualizar(cliente)
                ? "Cliente actualizado correctamente."
                : "No se realizaron cambios.");
    }

    private void eliminarCliente() throws SQLException {
        int id = leerId();
        Optional<Cliente> encontrado = clienteDAO.consultarPorId(id);
        if (encontrado.isEmpty()) {
            System.out.println("No se encontró un cliente con ese ID.");
            return;
        }
        System.out.println(encontrado.get());
        System.out.print("¿Confirmas que deseas eliminarlo? (s/n): ");
        if (entrada.nextLine().trim().equalsIgnoreCase("s")) {
            System.out.println(clienteDAO.eliminar(id)
                    ? "Cliente eliminado correctamente."
                    : "No se realizaron cambios.");
        } else {
            System.out.println("Eliminación cancelada.");
        }
    }

    private Cliente leerDatosCliente(int id) {
        System.out.print("Nombre: ");
        String nombre = entrada.nextLine().trim();
        while (nombre.isBlank()) {
            System.out.print("El nombre es obligatorio. Intenta de nuevo: ");
            nombre = entrada.nextLine().trim();
        }
        System.out.print("Teléfono (opcional): ");
        String telefono = entrada.nextLine().trim();
        System.out.print("Correo (opcional): ");
        String correo = entrada.nextLine().trim();
        return new Cliente(id, nombre, telefono, correo);
    }

    private int leerId() {
        while (true) {
            System.out.print("ID del cliente: ");
            try {
                int id = Integer.parseInt(entrada.nextLine().trim());
                if (id > 0) return id;
            } catch (NumberFormatException ignorada) {
                // Se vuelve a solicitar el dato con un mensaje claro.
            }
            System.out.println("Escribe un ID numérico mayor que cero.");
        }
    }
}
