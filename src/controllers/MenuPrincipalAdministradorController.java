package controllers;

import application.Conexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MenuPrincipalAdministradorController {

    // Botones
    @FXML private Button btnUsuario;
    @FXML private Button btnBitacora;
    @FXML private Button btnEquipos;
    @FXML private Button btnJugadores;
    @FXML private Button btnPartidos;
    @FXML private Button btnConsultas;
    @FXML private Button btnReportes;
    @FXML private Button btnCerrarSesion;

    // ventanas
    @FXML private AnchorPane inicioPane;
    @FXML private AnchorPane admUsuariosPane;
    @FXML private AnchorPane admBitacoraPane;
    @FXML private AnchorPane equiposPane;
    @FXML private AnchorPane jugadoresPane;
    @FXML private AnchorPane partidosPane;
    @FXML private AnchorPane consultasPane;
    @FXML private AnchorPane reportesPane;

    // Labels de bienvenida
    @FXML private Text textUsuario;
    @FXML private Text textfecha;
    @FXML private Text textHora;

    // Datos del usuario logueado
    private static int idUsuarioActual;
    private static String usernameActual;

    public static void setUsuarioActual(int id, String username) {
        idUsuarioActual = id;
        usernameActual = username;
    }

    @FXML
    public void initialize() {
        // Mostrar nombre de usuario
        if (textUsuario != null) {
            textUsuario.setText(usernameActual != null ? usernameActual : "usuario");
        }

        // Mostrar fecha y hora actual
        LocalDateTime ahora = LocalDateTime.now();
        if (textfecha != null) {
            textfecha.setText(ahora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
        if (textHora != null) {
            textHora.setText(ahora.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        }
    }

    @FXML
    public void mostrarVentana(ActionEvent event) {
        inicioPane.setVisible(true);
        admUsuariosPane.setVisible(false);
        admBitacoraPane.setVisible(false);

        if (event.getSource() == btnUsuario) {
            admUsuariosPane.setVisible(true);
            inicioPane.setVisible(false);
        } else if (event.getSource() == btnBitacora) {
            admBitacoraPane.setVisible(true);
            inicioPane.setVisible(false);
        }
    }

    @FXML
    void CerrarSesion(ActionEvent event) {
        try {
            // Registrar salida en bitácora
            Connection conn = Conexion.getConexion();
            String sql = "UPDATE Bitacora SET fecha_salida = GETDATE() WHERE id_usuario = ? AND fecha_salida IS NULL";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idUsuarioActual);
            ps.executeUpdate();
            conn.close();

            // Volver al login
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnCerrarSesion.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}