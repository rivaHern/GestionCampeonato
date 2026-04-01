package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class AdministradorController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private DatePicker DateBitacora;

	@FXML
	private DatePicker DatePartido;

	@FXML
	private AnchorPane bitacoraPane;

	@FXML
	private Button btnBitacora;

	@FXML
	private Button btnBitacoraBuscar;

	@FXML
	private Button btnBitacoraExportar;

	@FXML
	private Button btnCerrarSesion;

	@FXML
	private Button btnConsultas;

	@FXML
	private Button btnConsultasEjecutar;

	@FXML
	private Button btnEquipos;

	@FXML
	private Button btnEquiposActualizar;

	@FXML
	private Button btnEquiposAgregar;

	@FXML
	private Button btnEquiposBuscar;

	@FXML
	private Button btnEquiposEliminar;

	@FXML
	private Button btnJugadores;

	@FXML
	private Button btnJugadoresActualizar;

	@FXML
	private Button btnJugadoresAgregar;

	@FXML
	private Button btnJugadoresBuscar;

	@FXML
	private Button btnJugadoresEliminar;

	@FXML
	private Button btnPartidos;

	@FXML
	private Button btnPartidosActualizar;

	@FXML
	private Button btnPartidosAgregar;

	@FXML
	private Button btnPartidosBuscar;

	@FXML
	private Button btnPartidosEliminar;

	@FXML
	private Button btnReportes;

	@FXML
	private Button btnUsuario;

	@FXML
	private Button btnUsuarioActualizar;

	@FXML
	private Button btnUsuarioAgragar;

	@FXML
	private Button btnUsuarioBuscar;

	@FXML
	private Button btnUsuarioEliminar;

	@FXML
	private TableColumn<?, ?> colConfederacionGE;

	@FXML
	private TableColumn<?, ?> colCostoGJ;

	@FXML
	private TableColumn<?, ?> colDirectorTecnicoGE;

	@FXML
	private TableColumn<?, ?> colEdadGJ;

	@FXML
	private TableColumn<?, ?> colEquipoGJ;

	@FXML
	private TableColumn<?, ?> colEquiposGE;

	@FXML
	private TableColumn<?, ?> colEstadioPartidos;

	@FXML
	private TableColumn<?, ?> colEstaturaGJ;

	@FXML
	private TableColumn<?, ?> colFechaIngresoBitacora;

	@FXML
	private TableColumn<?, ?> colFechaPartidos;

	@FXML
	private TableColumn<?, ?> colFechaSalidaBitacora;

	@FXML
	private TableColumn<?, ?> colGrupoPartidos;

	@FXML
	private TableColumn<?, ?> colIDBitacora;

	@FXML
	private TableColumn<?, ?> colIDGE;

	@FXML
	private TableColumn<?, ?> colIDGJ;

	@FXML
	private TableColumn<?, ?> colIDPartidos;

	@FXML
	private TableColumn<?, ?> colIDUsuario;

	@FXML
	private TableColumn<?, ?> colLocalPartidos;

	@FXML
	private TableColumn<?, ?> colNombreGJ;

	@FXML
	private TableColumn<?, ?> colPesoGJ;

	@FXML
	private TableColumn<?, ?> colRolUsuario;

	@FXML
	private TableColumn<?, ?> colUsuario;

	@FXML
	private TableColumn<?, ?> colUsuarioBitacora;

	@FXML
	private TableColumn<?, ?> colVisitantePartidos;

	@FXML
	private ComboBox<?> combSeleccioneEstadio;

	@FXML
	private ComboBox<?> comboxEquipoConfederacion;

	@FXML
	private ComboBox<?> comboxEquipoGJ;

	@FXML
	private ComboBox<?> comboxPartidoEstadio;

	@FXML
	private ComboBox<?> comboxPartidoGrupo;

	@FXML
	private ComboBox<?> comboxPartidoLocal;

	@FXML
	private ComboBox<?> comboxPartidoVisitante;

	@FXML
	private ComboBox<?> comboxRol;

	@FXML
	private AnchorPane consultasPane;

	@FXML
	private AnchorPane equiposPane;

	@FXML
	private AnchorPane inicioPane;

	@FXML
	private AnchorPane jugadoresPane;

	@FXML
	private AnchorPane partidosPane;

	@FXML
	private RadioButton rdoConsulta1;

	@FXML
	private RadioButton rdoConsulta2;

	@FXML
	private RadioButton rdoConsulta3;

	@FXML
	private RadioButton rdoConsulta4;

	@FXML
	private AnchorPane root;

	@FXML
	private TableView<?> tabBitacora;

	@FXML
	private TableView<?> tabConsultas;

	@FXML
	private TableView<?> tabGE;

	@FXML
	private TableView<?> tabGJ;

	@FXML
	private TableView<?> tabPartidos;

	@FXML
	private TableView<?> tabUsuario;

	@FXML
	private PasswordField textContrasenia;

	@FXML
	private TextField textCostoGJ;

	@FXML
	private TextField textEdadGJ;

	@FXML
	private TextField textEquipoDirectorTecnico;

	@FXML
	private TextField textEquipoPais;

	@FXML
	private TextField textEstaturaGJ;

	@FXML
	private Text textHora;

	@FXML
	private TextField textNombreGJ;

	@FXML
	private TextField textParametroBusqueda;

	@FXML
	private TextField textPesoGJ;

	@FXML
	private TextField textUsuario;

	@FXML
	private Text textfecha;

	@FXML
	private ToggleGroup tgConsultas;

	@FXML
	private AnchorPane usuariosPane;

	@FXML
	void actualizarEquipos(ActionEvent event) {

	}

	@FXML
	void actualizarJugadores(ActionEvent event) {

	}

	@FXML
	void actualizarPartidos(ActionEvent event) {

	}

	@FXML
	void actualizarUsuario(ActionEvent event) {

	}

	@FXML
	void agregarUsuario(ActionEvent event) {

	}

	@FXML
	void agregarEquipos(ActionEvent event) {

	}

	@FXML
	void agregarJugadores(ActionEvent event) {

	}

	@FXML
	void agregarPartidos(ActionEvent event) {

	}

	@FXML
	void bitacoraBuscar(ActionEvent event) {

	}

	@FXML
	void bitacoraExportar(ActionEvent event) {

	}

	@FXML
	void buscarEquipos(ActionEvent event) {

	}

	@FXML
	void buscarJugadores(ActionEvent event) {

	}

	@FXML
	void buscarPartidos(ActionEvent event) {

	}

	@FXML
	void buscarUsuario(ActionEvent event) {

	}

	@FXML
	void cerrarSesion(ActionEvent event) {
		System.out.println("salir");
	}

	@FXML
	void ejecutarConsultas(ActionEvent event) {

	}

	@FXML
	void eliminarEquipos(ActionEvent event) {

	}

	@FXML
	void eliminarJugadores(ActionEvent event) {

	}

	@FXML
	void eliminarPartidos(ActionEvent event) {

	}

	@FXML
	void eliminarUsuario(ActionEvent event) {

	}

	@FXML
	void mostrarVentana(ActionEvent event) {
		// Ocultar todos los paneles
		inicioPane.setVisible(true);
		usuariosPane.setVisible(false);
		bitacoraPane.setVisible(false);
		equiposPane.setVisible(false);
		jugadoresPane.setVisible(false);
		partidosPane.setVisible(false);
		consultasPane.setVisible(false);
		// reportesPane.setVisible(false);

		if (event.getSource() == btnUsuario) {
			usuariosPane.setVisible(true);
			inicioPane.setVisible(false);
		} else if (event.getSource() == btnBitacora) {
			bitacoraPane.setVisible(true);
			inicioPane.setVisible(false);
		} else if (event.getSource() == btnEquipos) {
			equiposPane.setVisible(true);
			inicioPane.setVisible(false);
		} else if (event.getSource() == btnJugadores) {
			jugadoresPane.setVisible(true);
			inicioPane.setVisible(false);
		} else if (event.getSource() == btnPartidos) {
			partidosPane.setVisible(true);
			inicioPane.setVisible(false);
		} else if (event.getSource() == btnConsultas) {
			consultasPane.setVisible(true);
			inicioPane.setVisible(false);
		}
	}

	@FXML
	void initialize() {
		System.out.println("");
	}

}
