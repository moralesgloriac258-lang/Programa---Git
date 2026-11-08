package co.com.dulceconexion.main;

import co.com.dulceconexion.dao.ClienteDAO;
import co.com.dulceconexion.modelo.Cliente;

public class Principal {
    public static void main(String[] args) {
        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cliente = new Cliente();
        cliente.setNombre("Gloria Morales");
        cliente.setTelefono("3001234567");
        cliente.setCorreo("gloria@gmail.com");
        clienteDAO.insertar(cliente);
        clienteDAO.consultarTodos().forEach(c -> System.out.println(c.getNombre()));
        cliente.setId(1);
        cliente.setNombre("Gloria Cecilia Morales");
        clienteDAO.actualizar(cliente);
        clienteDAO.eliminar(1);
    }
}
