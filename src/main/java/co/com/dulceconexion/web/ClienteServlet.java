package co.com.dulceconexion.web;

import co.com.dulceconexion.dao.ClienteDAO;
import co.com.dulceconexion.modelo.Cliente;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

/** Controlador web para las operaciones CRUD de clientes. */
@WebServlet(name = "ClienteServlet", urlPatterns = "/clientes")
public class ClienteServlet extends HttpServlet {
    private final ClienteDAO clienteDAO = new ClienteDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");
        try {
            if ("nuevo".equals(accion)) {
                mostrarFormulario(request, response, null);
            } else if ("editar".equals(accion)) {
                int id = leerId(request, response);
                if (id == -1) return;
                Optional<Cliente> cliente = clienteDAO.consultarPorId(id);
                if (cliente.isPresent()) {
                    mostrarFormulario(request, response, cliente.get());
                } else {
                    response.sendRedirect(request.getContextPath() + "/clientes?mensaje=no-encontrado");
                }
            } else if ("buscar".equals(accion)) {
                buscarCliente(request, response);
            } else {
                request.setAttribute("clientes", clienteDAO.consultarTodos());
                request.getRequestDispatcher("/WEB-INF/vistas/clientes.jsp").forward(request, response);
            }
        } catch (SQLException excepcion) {
            manejarError(request, response, excepcion);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");
        try {
            switch (accion == null ? "" : accion) {
                case "crear" -> crearCliente(request, response);
                case "actualizar" -> actualizarCliente(request, response);
                case "eliminar" -> eliminarCliente(request, response);
                default -> response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida.");
            }
        } catch (SQLException excepcion) {
            manejarError(request, response, excepcion);
        }
    }

    private void crearCliente(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        Cliente cliente = leerFormulario(request, 0);
        if (!validarNombre(cliente, request, response, "/WEB-INF/vistas/cliente-formulario.jsp")) return;
        clienteDAO.insertar(cliente);
        response.sendRedirect(request.getContextPath() + "/clientes?mensaje=creado");
    }

    private void actualizarCliente(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int id = leerId(request, response);
        if (id == -1) return;
        Cliente cliente = leerFormulario(request, id);
        if (!validarNombre(cliente, request, response, "/WEB-INF/vistas/cliente-formulario.jsp")) return;
        boolean actualizado = clienteDAO.actualizar(cliente);
        String mensaje = actualizado ? "actualizado" : "no-encontrado";
        response.sendRedirect(request.getContextPath() + "/clientes?mensaje=" + mensaje);
    }

    private void eliminarCliente(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        int id = leerId(request, response);
        if (id == -1) return;
        try {
            boolean eliminado = clienteDAO.eliminar(id);
            String mensaje = eliminado ? "eliminado" : "no-encontrado";
            response.sendRedirect(request.getContextPath() + "/clientes?mensaje=" + mensaje);
        } catch (SQLException excepcion) {
            manejarError(request, response, excepcion);
        }
    }

    private void buscarCliente(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        String valorId = request.getParameter("id");
        if (valorId == null || valorId.isBlank()) {
            request.setAttribute("error", "Escribe el ID que deseas buscar.");
        } else {
            try {
                int id = Integer.parseInt(valorId);
                if (id <= 0) throw new NumberFormatException();
                Optional<Cliente> resultado = clienteDAO.consultarPorId(id);
                request.setAttribute("clientes", resultado.map(java.util.List::of).orElseGet(java.util.List::of));
                if (resultado.isEmpty()) request.setAttribute("mensaje", "No se encontró un cliente con ese ID.");
            } catch (NumberFormatException excepcion) {
                request.setAttribute("error", "El ID debe ser un número entero mayor que cero.");
            }
        }
        request.getRequestDispatcher("/WEB-INF/vistas/clientes.jsp").forward(request, response);
    }

    private void mostrarFormulario(HttpServletRequest request, HttpServletResponse response, Cliente cliente)
            throws ServletException, IOException {
        request.setAttribute("cliente", cliente);
        request.getRequestDispatcher("/WEB-INF/vistas/cliente-formulario.jsp").forward(request, response);
    }

    private Cliente leerFormulario(HttpServletRequest request, int id) {
        return new Cliente(id,
                valorFormulario(request, "nombre"),
                valorFormulario(request, "telefono"),
                valorFormulario(request, "correo"));
    }

    private String valorFormulario(HttpServletRequest request, String campo) {
        String valor = request.getParameter(campo);
        return valor == null ? "" : valor.trim();
    }

    private boolean validarNombre(Cliente cliente, HttpServletRequest request,
            HttpServletResponse response, String vista) throws ServletException, IOException {
        if (!cliente.getNombre().isBlank()) return true;
        request.setAttribute("cliente", cliente);
        request.setAttribute("error", "El nombre es obligatorio.");
        request.getRequestDispatcher(vista).forward(request, response);
        return false;
    }

    private int leerId(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            if (id > 0) return id;
        } catch (NumberFormatException | NullPointerException ignorada) {
            // Se envía una respuesta 400 debajo.
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El ID debe ser un número mayor que cero.");
        return -1;
    }

    private void manejarError(HttpServletRequest request, HttpServletResponse response, SQLException excepcion)
            throws ServletException, IOException {
        getServletContext().log("Error al consultar la base de datos de clientes", excepcion);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        request.setAttribute("error", "No fue posible completar la operación. Verifica la conexión con MySQL.");
        request.getRequestDispatcher("/WEB-INF/vistas/error.jsp").forward(request, response);
    }
}
