package application;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexion {

    public static Connection getConexion() {
        try {
            Properties props = new Properties();
            InputStream input = Conexion.class.getResourceAsStream("db.properties");
            props.load(input);

            String server = props.getProperty("db.server");
            String database = props.getProperty("db.name");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");

            String url = "jdbc:sqlserver://" + server + ":1433;"
                    + "databaseName=" + database + ";"
                    + "user=" + user + ";"
                    + "password=" + password + ";"
                    + "encrypt=true;"
                    + "trustServerCertificate=true;";

            Connection conn = DriverManager.getConnection(url);
            System.out.println("✅ Conexión exitosa a SQL Server");
            return conn;

        } catch (Exception e) {
            System.out.println("❌ Error de conexión: " + e.getMessage());
            return null;
        }
    }
}