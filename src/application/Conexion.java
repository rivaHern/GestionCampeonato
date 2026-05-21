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
            
            if (input == null) {
                System.out.println("❌ No se encontró db.properties en el classpath.");
                System.out.println("   Búsqueda: " + Conexion.class.getPackage().getName() + "/db.properties");
                System.out.println("   Verifica que bin/application/db.properties exista.");
                return null;
            }
            
            props.load(input);

            String server = props.getProperty("db.server");
            String port = props.getProperty("db.port", "1433");
            String database = props.getProperty("db.name");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");

            String url = "jdbc:sqlserver://" + server + ":" + port + ";"
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