package co.com.dulceconexion.dao;

import co.com.dulceconexion.config.ConexionBD;
import co.com.dulceconexion.modelo.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    public void insertar(Cliente cliente) {
        String sql = "INSERT INTO cliente(nombre, telefono, correo) VALUES (?,?,?)";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getTelefono());
            ps.setString(3, cliente.getCorreo());
            ps.executeUpdate();
            System.out.println("Cliente registrado correctamente");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Cliente> consultarTodos() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM cliente";
        try (Connection con = ConexionBD.obtenerConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setCorreo(rs.getString("correo"));
                lista.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void actualizar(Cliente cliente) {
        String sql = "UPDATE cliente SET nombre=?, telefono=?, correo=? WHERE id=?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getTelefono());
            ps.setString(3, cliente.getCorreo());
            ps.setInt(4, cliente.getId());
            ps.executeUpdate();
            System.out.println("Cliente actualizado");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM cliente WHERE id=?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Cliente eliminado");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
