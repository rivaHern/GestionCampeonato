package controllers;

import application.Conexion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import emun.Rol;

public class LoginController {

	@FXML
	private TextField usuarioLogin;
	@FXML
	private PasswordField claveLogin;
	@FXML
	private Button btnIngresar;

	@FXML
	public void ingresarLogin() {
		String usuario = usuarioLogin.getText().trim();
		String clave = claveLogin.getText().trim();

		if (usuario.isEmpty() || clave.isEmpty()) {
			mostrarAlerta("Error", "Por favor ingrese usuario y contraseña.");
			return;
		}

		try {
			Connection conn = Conexion.getConexion();
			String sql = "SELECT * FROM Usuario WHERE username = ? AND password = ? AND activo = 1";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, usuario);
			ps.setString(2, clave);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				String rolString = rs.getString("rol");
				Rol rol = Rol.valueOf(rolString);
				String nombreVista = "";

				if (rol == Rol.ADMINISTRADOR) {
    nombreVista = "/views/Administrador.fxml";
} else if (rol == Rol.TRADICIONAL) {
    nombreVista = "/views/Tradicional.fxml";
} else if (rol == Rol.ESPORADICO) {
    nombreVista = "/views/Esporadico.fxml";
				}

				// Registrar entrada en bitácora
				String sqlBitacora = "INSERT INTO Bitacora (id_usuario, fecha_entrada) VALUES (?, GETDATE())";
				PreparedStatement psBitacora = conn.prepareStatement(sqlBitacora);
				psBitacora.setInt(1, rs.getInt("id_usuario"));
				psBitacora.executeUpdate();

				// Pasar datos del usuario al menú
				controllers.AdministradorController.setUsuarioActual(rs.getInt("id_usuario"), rs.getString("username"), rol);

				// Abrir menú según rol
				FXMLLoader loader = new FXMLLoader(getClass().getResource(nombreVista));
				Parent root = loader.load();
				Stage stage = (Stage) btnIngresar.getScene().getWindow();
				stage.setScene(new Scene(root));
				stage.show();

			} else {
				mostrarAlerta("Error", "Usuario o contraseña incorrectos.");
			}

			conn.close();

		} catch (Exception e) {
			mostrarAlerta("Error", "Error al conectar: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void mostrarAlerta(String titulo, String mensaje) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(titulo);
		alert.setContentText(mensaje);
		alert.showAndWait();
	}
}
