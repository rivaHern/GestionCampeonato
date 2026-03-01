package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class MenuPrincipalAdministradorController {

	// Botones
	@FXML
	private Button btnUsuario;
	@FXML
	private Button btnBitacora;
	@FXML
	private Button btnEquipos;
	@FXML
	private Button btnJugadores;
	@FXML
	private Button btnPartidos;
	@FXML
	private Button btnConsultas;
	@FXML
	private Button btnReportes;
	@FXML
	private Button btnCerrarSesion;
	// ventanas
	@FXML
	private AnchorPane inicioPane;
	@FXML
	private AnchorPane admUsuariosPane;
	@FXML
	private AnchorPane bitacoraPane;
	@FXML
	private AnchorPane equiposPane;
	@FXML
	private AnchorPane jugadoresPane;
	@FXML
	private AnchorPane partidosPane;
	@FXML
	private AnchorPane consultasPane;
	@FXML
	private AnchorPane reportesPane;

//	@FXML
//	public void initialize() {
//		mostrarInicio();
//	}
//
//	private void mostrarInicio() {
//		inicioPane.setVisible(true);
//		admUsuariosPane.setVisible(false);
//		bitacoraPane.setVisible(false);
//		equiposPane.setVisible(false);
//		jugadoresPane.setVisible(false);
//		partidosPane.setVisible(false);
//		consultasPane.setVisible(false);
//		reportesPane.setVisible(false);
//	}

	  @FXML
	    public void mostrarVentana(ActionEvent event) {
	        // Ocultar todos los paneles
	        inicioPane.setVisible(true);
	        admUsuariosPane.setVisible(false);
//	        bitacoraPane.setVisible(false);
//	        equiposPane.setVisible(false);
//	        jugadoresPane.setVisible(false);
//	        partidosPane.setVisible(false);
//	        consultasPane.setVisible(false);
//	        reportesPane.setVisible(false);

	     
	        if (event.getSource() == btnUsuario) {
	            admUsuariosPane.setVisible(true);
	            inicioPane.setVisible(false);
	        } 
//	        else if (event.getSource() == btnBitacora) {
//	            bitacoraPane.setVisible(true);
//	        } else if (event.getSource() == btnEquipos) {
//	            equiposPane.setVisible(true);
//	        } else if (event.getSource() == btnJugadores) {
//	            jugadoresPane.setVisible(true);
//	        } else if (event.getSource() == btnPartidos) {
//	            partidosPane.setVisible(true);
//	        } else if (event.getSource() == btnConsultas) {
//	            consultasPane.setVisible(true);
//	        } else if (event.getSource() == btnReportes) {
//	            reportesPane.setVisible(true);
//	        }
	    }

}