package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage stage) {
		try {

			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/administrador.fxml"));
			//FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));

			Parent root = loader.load();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("Mundial 2026");
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// Probar conexión antes de lanzar la app
		Conexion.getConexion();
		launch(args);
	}
}