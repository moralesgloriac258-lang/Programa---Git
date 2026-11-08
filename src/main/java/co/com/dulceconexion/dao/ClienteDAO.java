package co.com.dulceconexion.dao;

import co.com.dulceconexion.config.ConexionBD;
import co.com.dulceconexion.modelo.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** Acceso a datos de clientes mediante JDBC. */
public class ClienteDAO {
    public Cliente insertar(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO cliente (nombre, telefono, correo) VALUES (?, ?, ?)";
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            sentencia.setString(1, cliente.getNombre());
            sentencia.setString(2, cliente.getTelefono());
            sentencia.setString(3, cliente.getCorreo());
            sentencia.executeUpdate();
            try (ResultSet claves = sentencia.getGeneratedKeys()) {
                if (claves.next()) {
                    cliente.setId(claves.getInt(1));
                }
            }
            return cliente;
        }
    }

    public List<Cliente> consultarTodos() throws SQLException {
        String sql = "SELECT id, nombre, telefono, correo FROM cliente ORDER BY id";
        List<Cliente> clientes = new ArrayList<>();
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql);
             ResultSet resultados = sentencia.executeQuery()) {
            while (resultados.next()) {
                clientes.add(mapearCliente(resultados));
            }
        }
        return clientes;
    }

    public Optional<Cliente> consultarPorId(int id) throws SQLException {
        String sql = "SELECT id, nombre, telefono, correo FROM cliente WHERE id = ?";
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setInt(1, id);
            try (ResultSet resultados = sentencia.executeQuery()) {
                if (resultados.next()) {
                    return Optional.of(mapearCliente(resultados));
                }
            }
        }
        return Optional.empty();
    }

    public boolean actualizar(Cliente cliente) throws SQLException {
        String sql = "UPDATE cliente SET nombre = ?, telefono = ?, correo = ? WHERE id = ?";
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setString(1, cliente.getNombre());
            sentencia.setString(2, cliente.getTelefono());
            sentencia.setString(3, cliente.getCorreo());
            sentencia.setInt(4, cliente.getId());
            return sentencia.executeUpdate() == 1;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM cliente WHERE id = ?";
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setInt(1, id);
            return sentencia.executeUpdate() == 1;
        }
    }

    private Cliente mapearCliente(ResultSet resultado) throws SQLException {
        return new Cliente(
                resultado.getInt("id"),
                resultado.getString("nombre"),
                resultado.getString("telefono"),
                resultado.getString("correo"));
    }
}
