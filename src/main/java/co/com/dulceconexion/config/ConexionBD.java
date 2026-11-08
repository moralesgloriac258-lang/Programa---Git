package co.com.dulceconexion.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static final String URL = System.getenv().getOrDefault(
            "DB_URL", "jdbc:mysql://localhost:3306/dulce_conexion");
    private static final String USUARIO = System.getenv().getOrDefault("DB_USER", "root");
    private static final String CLAVE = System.getenv().getOrDefault("DB_PASSWORD", "");

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }
}
