package controllers;

import application.Conexion;
import model.Bitacora;
import model.Usuario;
import emun.Rol;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AdministradorController {

	@FXML
	private ResourceBundle resources;
	@FXML
	private URL location;

	// Paneles
	@FXML
	private AnchorPane bitacoraPane;
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
	private AnchorPane root;
	@FXML
	private AnchorPane usuariosPane;

	// Botones Menú
	@FXML
	private Button btnBitacora;
	@FXML
	private Button btnCerrarSesion;
	@FXML
	private Button btnConsultas;
	@FXML
	private Button btnEquipos;
	@FXML
	private Button btnJugadores;
	@FXML
	private Button btnPartidos;
	@FXML
	private Button btnReportes;
	@FXML
	private Button btnUsuario;

	// Botones Bitacora
	@FXML
	private Button btnBitacoraBuscar;
	@FXML
	private Button btnBitacoraExportar;
	@FXML
	private DatePicker DateBitacora;

	// Botones Equipos
	@FXML
	private Button btnEquiposActualizar;
	@FXML
	private Button btnEquiposAgregar;
	// Botones Consultas
	@FXML
	private Button btnConsultasEjecutar;
	@FXML
	private Button btnEquiposBuscar;
	@FXML
	private Button btnEquiposEliminar;

	// Botones Jugadores
	@FXML
	private Button btnJugadoresActualizar;
	@FXML
	private Button btnJugadoresAgregar;
	@FXML
	private Button btnJugadoresBuscar;
	@FXML
	private Button btnJugadoresEliminar;

	// Botones Partidos
	@FXML
	private Button btnPartidosActualizar;
	@FXML
	private Button btnPartidosAgregar;
	@FXML
	private Button btnPartidosBuscar;
	@FXML
	private Button btnPartidosEliminar;
	@FXML
	private DatePicker DatePartido;

	// Botones Usuarios
	@FXML
	private Button btnUsuarioActualizar;
	@FXML
	private Button btnUsuarioAgragar;
	@FXML
	private Button btnUsuarioBuscar;
	@FXML
	private Button btnUsuarioEliminar;

	// Tablas y Columnas Equipos
	@FXML
	private TableView<?> tabGE;
	@FXML
	private TableColumn<?, ?> colConfederacionGE;
	@FXML
	private TableColumn<?, ?> colDirectorTecnicoGE;
	@FXML
	private TableColumn<?, ?> colEquiposGE;
	@FXML
	private TableColumn<?, ?> colIDGE;

	// Tablas y Columnas Jugadores
	@FXML
	private TableView<?> tabGJ;
	@FXML
	private TableColumn<?, ?> colCostoGJ;
	@FXML
	private TableColumn<?, ?> colEdadGJ;
	@FXML
	private TableColumn<?, ?> colEquipoGJ;
	@FXML
	private TableColumn<?, ?> colEstaturaGJ;
	@FXML
	private TableColumn<?, ?> colIDGJ;
	@FXML
	private TableColumn<?, ?> colNombreGJ;
	@FXML
	private TableColumn<?, ?> colPesoGJ;

	// Tablas y Columnas Partidos
	@FXML
	private TableView<?> tabPartidos;
	@FXML
	private TableColumn<?, ?> colEstadioPartidos;
	@FXML
	private TableColumn<?, ?> colFechaPartidos;
	@FXML
	private TableColumn<?, ?> colGrupoPartidos;
	@FXML
	private TableColumn<?, ?> colIDPartidos;
	@FXML
	private TableColumn<?, ?> colLocalPartidos;
	@FXML
	private TableColumn<?, ?> colVisitantePartidos;

	// Tablas y Columnas Bitacora
	@FXML
	private TableView<Bitacora> tabBitacora;
	@FXML
	private TableColumn<Bitacora, Integer> colIDBitacora;
	@FXML
	private TableColumn<Bitacora, String> colUsuarioBitacora;
	@FXML
	private TableColumn<Bitacora, LocalDateTime> colFechaIngresoBitacora;
	@FXML
	private TableColumn<Bitacora, LocalDateTime> colFechaSalidaBitacora;

	// Tablas y Columnas Usuarios
	@FXML
	private TableView<Usuario> tabUsuario;
	@FXML
	private TableColumn<Usuario, Integer> colIDUsuario;
	@FXML
	private TableColumn<Usuario, String> colUsuario;
	@FXML
	private TableColumn<Usuario, String> colRolUsuario;

	// Tablas y Columnas Consultas
	@FXML
	private TableView<?> tabConsultas;

	// ComboBoxes y TextFields
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
	private ComboBox<String> comboxRol;

	// Consultas RadioButtons
	@FXML
	private RadioButton rdoConsulta1;
	@FXML
	private RadioButton rdoConsulta2;
	@FXML
	private RadioButton rdoConsulta3;
	@FXML
	private RadioButton rdoConsulta4;
	@FXML
	private ToggleGroup tgConsultas;

	// Entradas de texto - Equipos / Jugadores
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
	private TextField textNombreGJ;
	@FXML
	private TextField textParametroBusqueda;
	@FXML
	private TextField textPesoGJ;

	// Entradas de texto - Usuarios
	@FXML
	private TextField textUsuario;
	@FXML
	private PasswordField textContrasenia;

	// Labels de inicio
	@FXML
	private Text textfecha;
	@FXML
	private Text textHora;

	// Variables de control de la sesión
	private int idUsuarioActual;
	private String usernameActual;
	private int idUsuarioSeleccionado = -1;

	@FXML
	void initialize() {
		initDateTime();
		initRoleCombo();
		initUsuarioTable();
		initBitacoraTable();
		showPane(inicioPane);
	}

	private void initDateTime() {
		LocalDateTime ahora = LocalDateTime.now();
		if (textfecha != null) {
			textfecha.setText(ahora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		}
		if (textHora != null) {
			textHora.setText(ahora.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
		}
	}

	private void initRoleCombo() {
		if (comboxRol != null) {
			comboxRol.setItems(FXCollections.observableArrayList(
				Arrays.stream(Rol.values()).map(Rol::name).collect(Collectors.toList())
			));
		}
	}

	private void initUsuarioTable() {
		if (tabUsuario == null || colIDUsuario == null || colUsuario == null || colRolUsuario == null) {
			return;
		}
		colIDUsuario.setCellValueFactory(new PropertyValueFactory<>("id"));
		colUsuario.setCellValueFactory(new PropertyValueFactory<>("username"));
		colRolUsuario.setCellValueFactory(new PropertyValueFactory<>("rol"));
		cargarUsuarios();
	}

	private void initBitacoraTable() {
		if (tabBitacora == null || colIDBitacora == null || colUsuarioBitacora == null
				|| colFechaIngresoBitacora == null || colFechaSalidaBitacora == null) {
			return;
		}

		colIDBitacora.setCellValueFactory(new PropertyValueFactory<>("id"));
		colUsuarioBitacora.setCellValueFactory(new PropertyValueFactory<>("usuario"));
		colFechaIngresoBitacora.setCellValueFactory(new PropertyValueFactory<>("fechaEntrada"));
		colFechaSalidaBitacora.setCellValueFactory(new PropertyValueFactory<>("fechaSalida"));

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		colFechaIngresoBitacora.setCellFactory(column -> new TableCell<Bitacora, LocalDateTime>() {
			@Override
			protected void updateItem(LocalDateTime item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty || item == null ? null : item.format(formatter));
			}
		});
		colFechaSalidaBitacora.setCellFactory(column -> new TableCell<Bitacora, LocalDateTime>() {
			@Override
			protected void updateItem(LocalDateTime item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty || item == null ? null : item.format(formatter));
			}
		});
	}

	@FXML
	void mostrarVentana(ActionEvent event) {
		if (event == null) {
			return;
		}

		if (event.getSource() == btnUsuario) {
			showPane(usuariosPane);
			cargarUsuarios();
		} else if (event.getSource() == btnBitacora) {
			showPane(bitacoraPane);
		} else if (event.getSource() == btnEquipos) {
			showPane(equiposPane);
		} else if (event.getSource() == btnJugadores) {
			showPane(jugadoresPane);
		} else if (event.getSource() == btnPartidos) {
			showPane(partidosPane);
		} else if (event.getSource() == btnConsultas) {
			showPane(consultasPane);
		} else {
			showPane(inicioPane);
		}
	}

	private void showPane(AnchorPane pane) {
		if (inicioPane != null) {
			inicioPane.setVisible(false);
		}
		if (usuariosPane != null) {
			usuariosPane.setVisible(false);
		}
		if (bitacoraPane != null) {
			bitacoraPane.setVisible(false);
		}
		if (equiposPane != null) {
			equiposPane.setVisible(false);
		}
		if (jugadoresPane != null) {
			jugadoresPane.setVisible(false);
		}
		if (partidosPane != null) {
			partidosPane.setVisible(false);
		}
		if (consultasPane != null) {
			consultasPane.setVisible(false);
		}
		if (pane != null) {
			pane.setVisible(true);
		}
	}

	// ========== CRUD USUARIOS ==========
	private void cargarUsuarios() {
		if (tabUsuario == null)
			return;
		ObservableList<Usuario> lista = FXCollections.observableArrayList();
		try (Connection conn = Conexion.getConexion();
			 Statement statement = conn.createStatement();
			 ResultSet rs = statement.executeQuery("SELECT id_usuario, username, rol FROM Usuario WHERE activo = 1")) {
			while (rs.next()) {
				lista.add(new Usuario(rs.getInt("id_usuario"), rs.getString("username"),
						Rol.valueOf(rs.getString("rol"))));
			}
			tabUsuario.setItems(lista);

			// Seleccionar fila y llenar campos
			tabUsuario.setOnMouseClicked(e -> {
				Usuario u = tabUsuario.getSelectionModel().getSelectedItem();
				if (u != null) {
					idUsuarioSeleccionado = u.getId();
					if (textUsuario != null)
						textUsuario.setText(u.getUsername());
					if (comboxRol != null)
						comboxRol.setValue(u.getRol().name());
					if (textContrasenia != null)
						textContrasenia.clear();
				}
			});
		} catch (Exception e) {
			mostrarAlerta("Error", e.getMessage());
		}
	}

	@FXML
	void agregarUsuario(ActionEvent event) {
		if (textUsuario == null || textContrasenia == null || comboxRol == null)
			return;

		String usuario = textUsuario.getText().trim();
		String clave = textContrasenia.getText().trim();
		String rol = comboxRol.getValue();

		if (usuario.isEmpty() || clave.isEmpty() || rol == null) {
			mostrarAlerta("Error", "Todos los campos son obligatorios.");
			return;
		}
		try (Connection conn = Conexion.getConexion();
			 PreparedStatement ps = conn
					.prepareStatement("INSERT INTO Usuario (username, password, rol, activo) VALUES (?, ?, ?, 1)")) {
			ps.setString(1, usuario);
			ps.setString(2, clave);
			ps.setString(3, rol);
			ps.executeUpdate();
			limpiarCamposUsuario();
			cargarUsuarios();
			mostrarInfo("Éxito", "Usuario agregado correctamente.");
		} catch (Exception e) {
			mostrarAlerta("Error", "Error al agregar usuario: " + e.getMessage());
		}
	}

	@FXML
	void actualizarUsuario(ActionEvent event) {
		if (idUsuarioSeleccionado == -1) {
			mostrarAlerta("Error", "Seleccione un usuario de la tabla.");
			return;
		}
		String usuario = textUsuario.getText().trim();
		String clave = textContrasenia.getText().trim();
		String rol = comboxRol.getValue();

		String sql = clave.isEmpty()
				? "UPDATE Usuario SET username=?, rol=? WHERE id_usuario=?"
				: "UPDATE Usuario SET username=?, rol=?, password=? WHERE id_usuario=?";
		try (Connection conn = Conexion.getConexion();
			 PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, usuario);
			ps.setString(2, rol);
			if (!clave.isEmpty()) {
				ps.setString(3, clave);
				ps.setInt(4, idUsuarioSeleccionado);
			} else {
				ps.setInt(3, idUsuarioSeleccionado);
			}
			ps.executeUpdate();
			limpiarCamposUsuario();
			cargarUsuarios();
			mostrarInfo("Éxito", "Usuario actualizado correctamente.");
		} catch (Exception e) {
			mostrarAlerta("Error", e.getMessage());
		}
	}

	@FXML
	void eliminarUsuario(ActionEvent event) {
		if (idUsuarioSeleccionado == -1) {
			mostrarAlerta("Error", "Seleccione un usuario de la tabla.");
			return;
		}
		try (Connection conn = Conexion.getConexion();
			 PreparedStatement ps = conn.prepareStatement("UPDATE Usuario SET activo=0 WHERE id_usuario=?")) {
			ps.setInt(1, idUsuarioSeleccionado);
			ps.executeUpdate();
			limpiarCamposUsuario();
			cargarUsuarios();
			mostrarInfo("Éxito", "Usuario eliminado correctamente.");
		} catch (Exception e) {
			mostrarAlerta("Error", e.getMessage());
		}
	}

	@FXML
	void buscarUsuario(ActionEvent event) {
		if (textUsuario == null || tabUsuario == null)
			return;
		String buscar = textUsuario.getText().trim();
		ObservableList<Usuario> lista = FXCollections.observableArrayList();
		try (Connection conn = Conexion.getConexion();
			 PreparedStatement ps = conn.prepareStatement(
					"SELECT id_usuario, username, rol FROM Usuario WHERE username LIKE ? AND activo=1")) {
			ps.setString(1, "%" + buscar + "%");
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					lista.add(new Usuario(rs.getInt("id_usuario"), rs.getString("username"),
							Rol.valueOf(rs.getString("rol"))));
				}
			}
			tabUsuario.setItems(lista);
		} catch (Exception e) {
			mostrarAlerta("Error", e.getMessage());
		}
	}

	private void limpiarCamposUsuario() {
		if (textUsuario != null)
			textUsuario.clear();
		if (textContrasenia != null)
			textContrasenia.clear();
		if (comboxRol != null)
			comboxRol.setValue(null);
		idUsuarioSeleccionado = -1;
	}

	// ========== BITÁCORA ==========
	@FXML
	void bitacoraBuscar(ActionEvent event) {
		if (DateBitacora == null || DateBitacora.getValue() == null) {
			mostrarAlerta("Error", "Seleccione una fecha.");
			return;
		}
		ObservableList<Bitacora> lista = FXCollections.observableArrayList();
		try {
			Connection conn = Conexion.getConexion();
			PreparedStatement ps = conn.prepareStatement(
					"SELECT b.id_bitacora, u.username, b.fecha_entrada, b.fecha_salida " +
							"FROM Bitacora b JOIN Usuario u ON b.id_usuario = u.id_usuario " +
							"WHERE CAST(b.fecha_entrada AS DATE) = ?");
			ps.setDate(1, java.sql.Date.valueOf(DateBitacora.getValue()));
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				java.sql.Timestamp tsEntrada = rs.getTimestamp("fecha_entrada");
				java.sql.Timestamp tsSalida = rs.getTimestamp("fecha_salida");
				lista.add(new Bitacora(
						rs.getInt("id_bitacora"),
						rs.getString("username"),
						tsEntrada != null ? tsEntrada.toLocalDateTime() : null,
						tsSalida != null ? tsSalida.toLocalDateTime() : null));
			}
			if (tabBitacora != null)
				tabBitacora.setItems(lista);
			conn.close();
		} catch (Exception e) {
			mostrarAlerta("Error", e.getMessage());
		}
	}

	@FXML
	void exportarBitacora(ActionEvent event) {
		System.out.println("pendiente implementar - exportar PDF bitácora");
	}

	// ========== MÉTODOS PENDIENTES ==========
	@FXML
	void agregarEquipos(ActionEvent event) {
		System.out.println("pendiente - agregarEquipos");
	}

	@FXML
	void actualizarEquipos(ActionEvent event) {
		System.out.println("pendiente - actualizarEquipos");
	}

	@FXML
	void buscarEquipos(ActionEvent event) {
		System.out.println("pendiente - buscarEquipos");
	}

	@FXML
	void eliminarEquipos(ActionEvent event) {
		System.out.println("pendiente - eliminarEquipos");
	}

	@FXML
	void agregarJugadores(ActionEvent event) {
	}

	@FXML
	void actualizarJugadores(ActionEvent event) {
	}

	@FXML
	void buscarJugadores(ActionEvent event) {
	}

	@FXML
	void eliminarJugadores(ActionEvent event) {
	}

	@FXML
	void agregarPartidos(ActionEvent event) {
	}

	@FXML
	void actualizarPartidos(ActionEvent event) {
	}

	@FXML
	void buscarPartidos(ActionEvent event) {
	}

	@FXML
	void eliminarPartidos(ActionEvent event) {
	}

	@FXML
	void ejecutarConsultas(ActionEvent event) {
	}

	// ========== CERRAR SESIÓN ==========
	@FXML
	void cerrarSesion(ActionEvent event) {
		try {
			// Check in case user wasn't properly initialized
			if (idUsuarioActual > 0) {
				Connection conn = Conexion.getConexion();
				PreparedStatement ps = conn.prepareStatement(
						"UPDATE Bitacora SET fecha_salida = GETDATE() WHERE id_usuario = ? AND fecha_salida IS NULL");
				ps.setInt(1, idUsuarioActual);
				ps.executeUpdate();
				conn.close();
			}

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
			Parent rootPane = loader.load();
			Stage stage = (Stage) btnCerrarSesion.getScene().getWindow();
			stage.setScene(new Scene(rootPane));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ========== UTILIDADES ==========
	private void mostrarAlerta(String titulo, String mensaje) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(titulo);
		alert.setContentText(mensaje);
		alert.showAndWait();
	}

	private void mostrarInfo(String titulo, String mensaje) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(titulo);
		alert.setContentText(mensaje);
		alert.showAndWait();
	}

}
