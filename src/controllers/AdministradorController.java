package controllers;

import application.Conexion;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.Phrase;
import java.io.FileOutputStream;
import java.io.File;
import javafx.stage.FileChooser;
import model.Bitacora;
import model.Usuario;
import model.Jugador;
import model.Equipo;
import model.Partido;
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

	// Parametros consultas
	@FXML private ComboBox<String> comboConfederacionConsulta;
	@FXML private ComboBox<String> comboEstadioConsulta;
	@FXML private ComboBox<String> comboPaisConsulta;
	@FXML private TextField textEdadConsulta;

	// Parametros consultas

	// Reportes
	@FXML private RadioButton rdoReporte1;
	@FXML private RadioButton rdoReporte2;
	@FXML private RadioButton rdoReporte3;
	@FXML private RadioButton rdoReporte4;
	@FXML private ToggleGroup tgReportes;
	@FXML private DatePicker dateReporte;
	@FXML private TextField textPesoReporte;
	@FXML private TextField textEstaturaReporte;
	@FXML private ComboBox<String> comboEquipoReporte;
	@FXML private ComboBox<String> comboConfederacionReporte;
	@FXML private AnchorPane reportesPane;

	// Labels de inicio
	@FXML private Text textNombreUsuario;
	@FXML
	private Text textfecha;
	@FXML
	private Text textHora;

	// Variables de control de la sesión
	private int idUsuarioActual;
	private String usernameActual;

	public static int staticIdUsuario = 0;
	public static String staticUsername = "";
	public static Rol staticRol = null;

	public static void setUsuarioActual(int id, String username, Rol rol) {
		staticIdUsuario = id;
		staticUsername = username;
		staticRol = rol;
	}

	@FXML
	void initialize_session() {
		idUsuarioActual = staticIdUsuario;
		usernameActual = staticUsername;
		if (textNombreUsuario != null) textNombreUsuario.setText(usernameActual);
	}
	private int idUsuarioSeleccionado = -1;

	@FXML
	void initialize() {
		initDateTime();
		initRoleCombo();
		initUsuarioTable();
		initBitacoraTable();
		initEquiposTable();
		initJugadoresTable();
		initPartidosTable();
		initialize_session();
		if (tgConsultas != null) {
			tgConsultas.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
				if (newVal != null) actualizarParametrosConsulta();
			});
		}
		showPane(inicioPane);

		// Ocultar paneles de admin si no es ADMINISTRADOR
		if (staticRol != Rol.ADMINISTRADOR) {
			if (usuariosPane != null) usuariosPane.setVisible(false);
			if (bitacoraPane != null) bitacoraPane.setVisible(false);
			if (btnUsuario != null) btnUsuario.setVisible(false);
			if (btnBitacora != null) btnBitacora.setVisible(false);
		}
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
			cargarComboConfederaciones();
			cargarEquipos();
		} else if (event.getSource() == btnJugadores) {
			showPane(jugadoresPane);
			cargarComboEquipos();
			cargarJugadores();
		} else if (event.getSource() == btnPartidos) {
			showPane(partidosPane);
			cargarCombosPartidos();
			cargarPartidos();
		} else if (event.getSource() == btnReportes) {
			showPane(reportesPane);
			cargarCombosReportes();
			inicializarCamposReportes();
} else if (event.getSource() == btnConsultas) {
			showPane(consultasPane);
			if (combSeleccioneEstadio != null) combSeleccioneEstadio.setVisible(false);
			if (textParametroBusqueda != null) textParametroBusqueda.setVisible(false);
			if (combSeleccioneEstadio != null) {
				ObservableList<String> estadios = FXCollections.observableArrayList();
				try (Connection conn = Conexion.getConexion();
				     PreparedStatement ps = conn.prepareStatement("SELECT nombre FROM Estadio ORDER BY nombre");
				     ResultSet rs = ps.executeQuery()) {
					while (rs.next()) estadios.add(rs.getString("nombre"));
				} catch (Exception ex) { ex.printStackTrace(); }
				((ComboBox<String>)combSeleccioneEstadio).setItems(estadios);
			}
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
		if (reportesPane != null) {
			reportesPane.setVisible(false);
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
	// ========== EQUIPOS ==========
	private int idEquipoSeleccionado = -1;

	@SuppressWarnings("unchecked")
	private void initEquiposTable() {
		if (tabGE == null || colIDGE == null) return;
		((TableColumn<Equipo, Integer>) colIDGE).setCellValueFactory(new PropertyValueFactory<>("id"));
		((TableColumn<Equipo, String>) colEquiposGE).setCellValueFactory(new PropertyValueFactory<>("nombre"));
		((TableColumn<Equipo, String>) colDirectorTecnicoGE).setCellValueFactory(new PropertyValueFactory<>("directorTecnico"));
		((TableColumn<Equipo, String>) colConfederacionGE).setCellValueFactory(new PropertyValueFactory<>("confederacion"));
	}

	@SuppressWarnings("unchecked")
	private void cargarEquipos() {
		if (tabGE == null) return;
		ObservableList<Equipo> lista = FXCollections.observableArrayList();
		try (Connection conn = Conexion.getConexion();
		     PreparedStatement ps = conn.prepareStatement(
			"SELECT e.id_equipo, e.nombre, c.nombre as confederacion, "
			+ "ISNULL(d.nombre + ' ' + d.apellido, 'Sin DT') as director "
			+ "FROM Equipo e "
			+ "JOIN Confederacion c ON e.id_confederacion = c.id_confederacion "
			+ "LEFT JOIN DirectorTecnico d ON e.id_equipo = d.id_equipo")) {
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					lista.add(new Equipo(
						rs.getInt("id_equipo"),
						rs.getString("nombre"),
						rs.getString("confederacion"),
						rs.getString("director")));
				}
			}
			TableView<Equipo> tab = (TableView<Equipo>) tabGE;
			tab.setItems(lista);
			tab.setOnMouseClicked(e -> {
				Equipo eq = tab.getSelectionModel().getSelectedItem();
				if (eq != null) {
					idEquipoSeleccionado = eq.getId();
					if (textEquipoPais != null) textEquipoPais.setText(eq.getNombre());
					if (textEquipoDirectorTecnico != null) textEquipoDirectorTecnico.setText(eq.getDirectorTecnico());
					if (comboxEquipoConfederacion != null) ((ComboBox<String>)comboxEquipoConfederacion).setValue(eq.getConfederacion());
				}
			});
		} catch (Exception e) {
			mostrarAlerta("Error", e.getMessage());
		}
	}

	private void cargarComboConfederaciones() {
		if (comboxEquipoConfederacion == null) return;
		ObservableList<String> confs = FXCollections.observableArrayList();
		try (Connection conn = Conexion.getConexion();
		     PreparedStatement ps = conn.prepareStatement("SELECT siglas FROM Confederacion ORDER BY siglas");
		     ResultSet rs = ps.executeQuery()) {
			while (rs.next()) confs.add(rs.getString("siglas"));
			((ComboBox<String>)comboxEquipoConfederacion).setItems(confs);
		} catch (Exception e) { e.printStackTrace(); }
	}

	private void limpiarCamposEquipos() {
		if (textEquipoPais != null) textEquipoPais.clear();
		if (textEquipoDirectorTecnico != null) textEquipoDirectorTecnico.clear();
		if (comboxEquipoConfederacion != null) ((ComboBox<String>)comboxEquipoConfederacion).setValue(null);
		idEquipoSeleccionado = -1;
	}

	@FXML
	void agregarEquipos(ActionEvent event) {
		String nombre = textEquipoPais.getText().trim();
		String confederacion = ((ComboBox<String>)comboxEquipoConfederacion).getValue();
		if (nombre.isEmpty() || confederacion == null) {
			mostrarAlerta("Error", "Complete nombre y confederacion.");
			return;
		}
		try (Connection conn = Conexion.getConexion()) {
			// Validación de duplicado
			try (PreparedStatement psChk = conn.prepareStatement(
				"SELECT COUNT(*) FROM Equipo WHERE nombre=?")) {
				psChk.setString(1, nombre);
				ResultSet rsChk = psChk.executeQuery();
				if (rsChk.next() && rsChk.getInt(1) > 0) {
					mostrarAlerta("Error", "Ya existe un equipo con ese nombre.");
					return;
				}
			}

			int idConf = 0;
			try (PreparedStatement ps2 = conn.prepareStatement("SELECT id_confederacion FROM Confederacion WHERE nombre = ? OR siglas = ?")) {
				ps2.setString(1, confederacion); ps2.setString(2, confederacion);
				ResultSet rs2 = ps2.executeQuery();
				if (rs2.next()) idConf = rs2.getInt(1);
			}
			try (PreparedStatement ps = conn.prepareStatement(
				"INSERT INTO Equipo (nombre, id_confederacion, id_pais, valor_total) VALUES (?,?,1,0)")) {
				ps.setString(1, nombre); ps.setInt(2, idConf);
				ps.executeUpdate();
			}
			limpiarCamposEquipos(); cargarEquipos();
			mostrarInfo("Exito", "Equipo agregado.");
		} catch (Exception e) {
			mostrarAlerta("Error", e.getMessage());
		}
	}

	@FXML
	void actualizarEquipos(ActionEvent event) {
		if (idEquipoSeleccionado == -1) { mostrarAlerta("Error", "Seleccione un equipo."); return; }
		String nombre = textEquipoPais.getText().trim();
		String director = textEquipoDirectorTecnico.getText().trim();
		// Separar nombre y apellido del director
		String[] partes = director.split(" ", 2);
		String nombreDT = partes[0];
		String apellidoDT = partes.length > 1 ? partes[1] : "";
		try (Connection conn = Conexion.getConexion()) {
			try (PreparedStatement ps = conn.prepareStatement("UPDATE Equipo SET nombre=? WHERE id_equipo=?")) {
				ps.setString(1, nombre); ps.setInt(2, idEquipoSeleccionado);
				ps.executeUpdate();
			}
			// Obtener la nacionalidad del equipo
			String nacionalidad = "";
			try (PreparedStatement psPais = conn.prepareStatement("SELECT p.nombre FROM Pais p JOIN Equipo e ON e.id_pais = p.id_pais WHERE e.id_equipo=?")) {
				psPais.setInt(1, idEquipoSeleccionado);
				ResultSet rsPais = psPais.executeQuery();
				if (rsPais.next()) nacionalidad = rsPais.getString("nombre");
			}
			// Verificar si existe director técnico
			int existeDT = 0;
			try (PreparedStatement psChk = conn.prepareStatement("SELECT COUNT(*) FROM DirectorTecnico WHERE id_equipo=?")) {
				psChk.setInt(1, idEquipoSeleccionado);
				ResultSet rsChk = psChk.executeQuery();
				if (rsChk.next()) existeDT = rsChk.getInt(1);
			}
			// Si existe, hacer UPDATE; si no, hacer INSERT
			if (existeDT > 0) {
				try (PreparedStatement ps = conn.prepareStatement("UPDATE DirectorTecnico SET nombre=?, apellido=?, nacionalidad=? WHERE id_equipo=?")) {
					ps.setString(1, nombreDT); ps.setString(2, apellidoDT); ps.setString(3, nacionalidad); ps.setInt(4, idEquipoSeleccionado);
					ps.executeUpdate();
				}
			} else {
				try (PreparedStatement ps = conn.prepareStatement("INSERT INTO DirectorTecnico (nombre, apellido, nacionalidad, id_equipo) VALUES (?, ?, ?, ?)")) {
					ps.setString(1, nombreDT); ps.setString(2, apellidoDT); ps.setString(3, nacionalidad); ps.setInt(4, idEquipoSeleccionado);
					ps.executeUpdate();
				}
			}
			limpiarCamposEquipos(); cargarEquipos();
			mostrarInfo("Exito", "Equipo actualizado.");
		} catch (Exception e) {
			mostrarAlerta("Error", e.getMessage());
		}
	}

	@FXML
	void buscarEquipos(ActionEvent event) {
		String buscar = textEquipoPais.getText().trim();
		ObservableList<Equipo> lista = FXCollections.observableArrayList();
		try (Connection conn = Conexion.getConexion();
		     PreparedStatement ps = conn.prepareStatement(
			"SELECT e.id_equipo, e.nombre, c.nombre as confederacion, "
			+ "ISNULL(d.nombre + ' ' + d.apellido, 'Sin DT') as director "
			+ "FROM Equipo e "
			+ "JOIN Confederacion c ON e.id_confederacion = c.id_confederacion "
			+ "LEFT JOIN DirectorTecnico d ON e.id_equipo = d.id_equipo "
			+ "WHERE e.nombre LIKE ?")) {
			ps.setString(1, "%" + buscar + "%");
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) lista.add(new Equipo(
					rs.getInt("id_equipo"), rs.getString("nombre"),
					rs.getString("confederacion"), rs.getString("director")));
			}
			((TableView<Equipo>)tabGE).setItems(lista);
		} catch (Exception e) {
			mostrarAlerta("Error", e.getMessage());
		}
	}

	@FXML
	void eliminarEquipos(ActionEvent event) {
		if (idEquipoSeleccionado == -1) { mostrarAlerta("Error", "Seleccione un equipo."); return; }
		try (Connection conn = Conexion.getConexion();
		     PreparedStatement ps = conn.prepareStatement("DELETE FROM Equipo WHERE id_equipo=?")) {
			ps.setInt(1, idEquipoSeleccionado);
			ps.executeUpdate();
			limpiarCamposEquipos(); cargarEquipos();
			mostrarInfo("Exito", "Equipo eliminado.");
		} catch (Exception e) {
			mostrarAlerta("Error", e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	private void initJugadoresTable() {
		System.out.println("initJugadoresTable tabGJ=" + tabGJ + " colIDGJ=" + colIDGJ);
		if (tabGJ == null || colIDGJ == null) return;
		((TableColumn<Jugador, Integer>) colIDGJ).setCellValueFactory(new PropertyValueFactory<>("id"));
		((TableColumn<Jugador, String>) colNombreGJ).setCellValueFactory(new PropertyValueFactory<>("nombre"));
		((TableColumn<Jugador, Double>) colPesoGJ).setCellValueFactory(new PropertyValueFactory<>("peso"));
		((TableColumn<Jugador, Double>) colEstaturaGJ).setCellValueFactory(new PropertyValueFactory<>("estatura"));
		((TableColumn<Jugador, Double>) colCostoGJ).setCellValueFactory(new PropertyValueFactory<>("valor"));
		((TableColumn<Jugador, String>) colEquipoGJ).setCellValueFactory(new PropertyValueFactory<>("nombreEquipo"));
		((TableColumn<Jugador, Integer>) colEdadGJ).setCellValueFactory(new PropertyValueFactory<>("edad"));
	}

	// ========== JUGADORES ==========
	private int idJugadorSeleccionado = -1;

	private void cargarJugadores() {
		if (tabGJ == null) return;
		ObservableList<Jugador> lista = FXCollections.observableArrayList();
		try (Connection conn = Conexion.getConexion();
		     PreparedStatement ps = conn.prepareStatement(
			"SELECT j.id_jugador, j.nombre, j.apellido, j.fecha_nacimiento, " +
			"j.peso, j.estatura, j.posicion, j.valor, j.id_equipo, e.nombre as nombre_equipo, " +
			"DATEDIFF(YEAR, j.fecha_nacimiento, GETDATE()) as edad " +
			"FROM Jugador j JOIN Equipo e ON j.id_equipo = e.id_equipo")) {
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					lista.add(new Jugador(
						rs.getInt("id_jugador"), rs.getString("nombre"), rs.getString("apellido"),
						rs.getDate("fecha_nacimiento") != null ? rs.getDate("fecha_nacimiento").toLocalDate() : null,
						rs.getDouble("peso"), rs.getDouble("estatura"), rs.getString("posicion"),
						rs.getDouble("valor"), rs.getInt("id_equipo"), rs.getString("nombre_equipo")));
						lista.get(lista.size()-1).setEdad(rs.getInt("edad"));
				}
			}
			TableView<Jugador> tab = (TableView<Jugador>) tabGJ;
			tab.setItems(lista);
			System.out.println("Jugadores cargados: " + lista.size());
			tab.setOnMouseClicked(e -> {
				Jugador j = tab.getSelectionModel().getSelectedItem();
				if (j != null) {
					idJugadorSeleccionado = j.getId();
					if (textNombreGJ != null) textNombreGJ.setText(j.getNombre());
					if (textPesoGJ != null) textPesoGJ.setText(String.valueOf(j.getPeso()));
					if (textEstaturaGJ != null) textEstaturaGJ.setText(String.valueOf(j.getEstatura()));
					if (textCostoGJ != null) textCostoGJ.setText(String.valueOf(j.getValor()));
					if (comboxEquipoGJ != null) ((ComboBox<String>)comboxEquipoGJ).setValue(j.getNombreEquipo());
					if (textEdadGJ != null) textEdadGJ.setText(String.valueOf(j.getEdad()));
				}
			});
		} catch (Exception e) {
			mostrarAlerta("Error", e.getMessage());
		}
	}

	private void cargarComboEquipos() {
		if (comboxEquipoGJ == null) return;
		ObservableList<String> equipos = FXCollections.observableArrayList();
		try (Connection conn = Conexion.getConexion();
		     PreparedStatement ps = conn.prepareStatement("SELECT nombre FROM Equipo ORDER BY nombre")) {
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) equipos.add(rs.getString("nombre"));
			}
			((ComboBox<String>)comboxEquipoGJ).setItems(equipos);
		} catch (Exception e) {
			mostrarAlerta("Error", e.getMessage());
		}
	}

	private void limpiarCamposJugadores() {
		if (textNombreGJ != null) textNombreGJ.clear();
		if (textPesoGJ != null) textPesoGJ.clear();
		if (textEstaturaGJ != null) textEstaturaGJ.clear();
		if (textCostoGJ != null) textCostoGJ.clear();
		if (textEdadGJ != null) textEdadGJ.clear();
		if (comboxEquipoGJ != null) ((ComboBox<String>)comboxEquipoGJ).setValue(null);
		idJugadorSeleccionado = -1;
	}

	@FXML
	void agregarJugadores(ActionEvent event) {
		String nombre = textNombreGJ.getText().trim();
		String pesoStr = textPesoGJ.getText().trim();
		String estaturaStr = textEstaturaGJ.getText().trim();
		String costoStr = textCostoGJ.getText().trim();
		String edadStr = textEdadGJ != null ? textEdadGJ.getText().trim() : "";
                String equipo = ((ComboBox<String>)comboxEquipoGJ).getValue();
		if (nombre.isEmpty() || pesoStr.isEmpty() || estaturaStr.isEmpty() || costoStr.isEmpty() || equipo == null) {
			mostrarAlerta("Error", "Complete todos los campos obligatorios.");
			return;
		}
		if (idJugadorSeleccionado != -1) {
			mostrarAlerta("Error", "Hay un jugador seleccionado. Deseleccione la tabla o use Actualizar.");
			return;
		}
		try (Connection conn = Conexion.getConexion()) {
			int idEquipo = 0;
			try (PreparedStatement ps2 = conn.prepareStatement("SELECT id_equipo FROM Equipo WHERE nombre = ?")) {
				ps2.setString(1, equipo);
				ResultSet rs2 = ps2.executeQuery();
				if (rs2.next()) idEquipo = rs2.getInt("id_equipo");
			}

			// Validación de duplicado
			try (PreparedStatement psChk = conn.prepareStatement(
				"SELECT COUNT(*) FROM Jugador WHERE nombre=? AND id_equipo=?")) {
				psChk.setString(1, nombre);
				psChk.setInt(2, idEquipo);
				ResultSet rsChk = psChk.executeQuery();
				if (rsChk.next() && rsChk.getInt(1) > 0) {
					mostrarAlerta("Error", "Ya existe un jugador con ese nombre en ese equipo.");
					return;
				}
			}

			try (PreparedStatement ps = conn.prepareStatement(
				"INSERT INTO Jugador (nombre, apellido, fecha_nacimiento, peso, estatura, posicion, valor, id_equipo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
				java.time.LocalDate fechaNac = (!edadStr.isEmpty() && edadStr.matches("\\d+"))
				    ? java.time.LocalDate.now().minusYears(Integer.parseInt(edadStr))
				    : null;
				ps.setString(1, nombre);
				ps.setString(2, nombre);
				if (fechaNac != null)
				    ps.setDate(3, java.sql.Date.valueOf(fechaNac));
				else
				    ps.setNull(3, java.sql.Types.DATE);
				ps.setDouble(4, Double.parseDouble(pesoStr));
				ps.setDouble(5, Double.parseDouble(estaturaStr));
				ps.setString(6, "Jugador");
				ps.setDouble(7, Double.parseDouble(costoStr));
				ps.setInt(8, idEquipo);
				ps.executeUpdate();
			}
			limpiarCamposJugadores();
			cargarJugadores();
			mostrarInfo("Éxito", "Jugador agregado correctamente.");
		} catch (Exception e) {
			mostrarAlerta("Error", "Error al agregar jugador: " + e.getMessage());
		}
	}

	@FXML
	void actualizarJugadores(ActionEvent event) {
		if (idJugadorSeleccionado == -1) {
			mostrarAlerta("Error", "Seleccione un jugador de la tabla.");
			return;
		}
		String edadStr = textEdadGJ != null ? textEdadGJ.getText().trim() : "";
                String equipo = ((ComboBox<String>)comboxEquipoGJ).getValue();
		try (Connection conn = Conexion.getConexion()) {
			int idEquipo = 0;
			try (PreparedStatement ps2 = conn.prepareStatement("SELECT id_equipo FROM Equipo WHERE nombre = ?")) {
				ps2.setString(1, equipo);
				ResultSet rs2 = ps2.executeQuery();
				if (rs2.next()) idEquipo = rs2.getInt("id_equipo");
			}
			try (PreparedStatement ps = conn.prepareStatement(
				"UPDATE Jugador SET nombre=?, peso=?, estatura=?, valor=?, id_equipo=? WHERE id_jugador=?")) {
				ps.setString(1, textNombreGJ.getText().trim());
				ps.setDouble(2, Double.parseDouble(textPesoGJ.getText().trim()));
				ps.setDouble(3, Double.parseDouble(textEstaturaGJ.getText().trim()));
				ps.setDouble(4, Double.parseDouble(textCostoGJ.getText().trim()));
				ps.setInt(5, idEquipo);
				ps.setInt(6, idJugadorSeleccionado);
				ps.executeUpdate();
			}
			limpiarCamposJugadores();
			cargarJugadores();
			mostrarInfo("Éxito", "Jugador actualizado correctamente.");
		} catch (Exception e) {
			mostrarAlerta("Error", "Error al actualizar: " + e.getMessage());
		}
	}

	@FXML
	void buscarJugadores(ActionEvent event) {
		String buscar = textNombreGJ.getText().trim();
		ObservableList<Jugador> lista = FXCollections.observableArrayList();
		try (Connection conn = Conexion.getConexion();
		     PreparedStatement ps = conn.prepareStatement(
			"SELECT j.id_jugador, j.nombre, j.apellido, j.fecha_nacimiento, j.peso, j.estatura, " +
			"j.posicion, j.valor, j.id_equipo, e.nombre as nombre_equipo " +
			"FROM Jugador j JOIN Equipo e ON j.id_equipo = e.id_equipo " +
			"WHERE j.nombre LIKE ? OR j.apellido LIKE ?")) {
			ps.setString(1, "%" + buscar + "%");
			ps.setString(2, "%" + buscar + "%");
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					lista.add(new Jugador(
						rs.getInt("id_jugador"), rs.getString("nombre"), rs.getString("apellido"),
						rs.getDate("fecha_nacimiento") != null ? rs.getDate("fecha_nacimiento").toLocalDate() : null,
						rs.getDouble("peso"), rs.getDouble("estatura"), rs.getString("posicion"),
						rs.getDouble("valor"), rs.getInt("id_equipo"), rs.getString("nombre_equipo")));
				}
			}
			((TableView<Jugador>)tabGJ).setItems(lista);
		} catch (Exception e) {
			mostrarAlerta("Error", e.getMessage());
		}
	}

	@FXML
	void eliminarJugadores(ActionEvent event) {
		if (idJugadorSeleccionado == -1) {
			mostrarAlerta("Error", "Seleccione un jugador de la tabla.");
			return;
		}
		try (Connection conn = Conexion.getConexion();
		     PreparedStatement ps = conn.prepareStatement("DELETE FROM Jugador WHERE id_jugador=?")) {
			ps.setInt(1, idJugadorSeleccionado);
			ps.executeUpdate();
			limpiarCamposJugadores();
			cargarJugadores();
			mostrarInfo("Éxito", "Jugador eliminado.");
		} catch (Exception e) {
			mostrarAlerta("Error", e.getMessage());
		}
	}

	// ========== PARTIDOS ==========
	private int idPartidoSeleccionado = -1;

	@SuppressWarnings("unchecked")
	private void initPartidosTable() {
		if (tabPartidos == null || colIDPartidos == null) return;
		((TableColumn<Partido, Integer>) colIDPartidos).setCellValueFactory(new PropertyValueFactory<>("id"));
		((TableColumn<Partido, String>) colGrupoPartidos).setCellValueFactory(new PropertyValueFactory<>("grupo"));
		((TableColumn<Partido, String>) colFechaPartidos).setCellValueFactory(new PropertyValueFactory<>("fecha"));
		((TableColumn<Partido, String>) colLocalPartidos).setCellValueFactory(new PropertyValueFactory<>("equipoLocal"));
		((TableColumn<Partido, String>) colVisitantePartidos).setCellValueFactory(new PropertyValueFactory<>("equipoVisitante"));
		((TableColumn<Partido, String>) colEstadioPartidos).setCellValueFactory(new PropertyValueFactory<>("estadio"));
	}

	private void cargarPartidos() {
		if (tabPartidos == null) return;
		ObservableList<Partido> lista = FXCollections.observableArrayList();
		try (Connection conn = Conexion.getConexion();
		     PreparedStatement ps = conn.prepareStatement(
			"SELECT p.id_partido, p.fecha, el.nombre as local, ev.nombre as visitante, "
			+ "e.nombre as estadio, g.nombre as grupo "
			+ "FROM Partido p "
			+ "JOIN Equipo el ON p.id_equipo_local = el.id_equipo "
			+ "JOIN Equipo ev ON p.id_equipo_visitante = ev.id_equipo "
			+ "JOIN Estadio e ON p.id_estadio = e.id_estadio "
			+ "JOIN Grupo g ON p.id_grupo = g.id_grupo")) {
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					java.sql.Timestamp ts = rs.getTimestamp("fecha");
					lista.add(new Partido(
						rs.getInt("id_partido"),
						ts != null ? ts.toLocalDateTime() : null,
						0, 0, 0, 0,
						rs.getString("local"),
						rs.getString("visitante"),
						rs.getString("estadio"),
						rs.getString("grupo")));
				}
			}
			TableView<Partido> tab = (TableView<Partido>) tabPartidos;
			tab.setItems(lista);
			tab.setOnMouseClicked(e -> {
				Partido p = tab.getSelectionModel().getSelectedItem();
				if (p != null) {
					idPartidoSeleccionado = p.getId();
					if (comboxPartidoLocal != null) ((ComboBox<String>)comboxPartidoLocal).setValue(p.getEquipoLocal());
					if (comboxPartidoVisitante != null) ((ComboBox<String>)comboxPartidoVisitante).setValue(p.getEquipoVisitante());
					if (comboxPartidoEstadio != null) ((ComboBox<String>)comboxPartidoEstadio).setValue(p.getEstadio());
					if (comboxPartidoGrupo != null) ((ComboBox<String>)comboxPartidoGrupo).setValue(p.getGrupo());
					if (DatePartido != null && p.getFecha() != null) DatePartido.setValue(p.getFecha().toLocalDate());
				}
			});
		} catch (Exception e) {
			mostrarAlerta("Error", e.getMessage());
		}
	}

	private void cargarCombosPartidos() {
		try (Connection conn = Conexion.getConexion()) {
			ObservableList<String> equipos = FXCollections.observableArrayList();
			try (PreparedStatement ps = conn.prepareStatement("SELECT nombre FROM Equipo ORDER BY nombre");
			     ResultSet rs = ps.executeQuery()) {
				while (rs.next()) equipos.add(rs.getString("nombre"));
			}
			if (comboxPartidoLocal != null) ((ComboBox<String>)comboxPartidoLocal).setItems(equipos);
			if (comboxPartidoVisitante != null) ((ComboBox<String>)comboxPartidoVisitante).setItems(FXCollections.observableArrayList(equipos));

			ObservableList<String> estadios = FXCollections.observableArrayList();
			try (PreparedStatement ps = conn.prepareStatement("SELECT nombre FROM Estadio ORDER BY nombre");
			     ResultSet rs = ps.executeQuery()) {
				while (rs.next()) estadios.add(rs.getString("nombre"));
			}
			if (comboxPartidoEstadio != null) ((ComboBox<String>)comboxPartidoEstadio).setItems(estadios);

			ObservableList<String> grupos = FXCollections.observableArrayList();
			try (PreparedStatement ps = conn.prepareStatement("SELECT nombre FROM Grupo ORDER BY nombre");
			     ResultSet rs = ps.executeQuery()) {
				while (rs.next()) grupos.add(rs.getString("nombre"));
			}
			if (comboxPartidoGrupo != null) ((ComboBox<String>)comboxPartidoGrupo).setItems(grupos);
		} catch (Exception e) {
			mostrarAlerta("Error", e.getMessage());
		}
	}

	private void limpiarCamposPartidos() {
		if (comboxPartidoLocal != null) ((ComboBox<String>)comboxPartidoLocal).setValue(null);
		if (comboxPartidoVisitante != null) ((ComboBox<String>)comboxPartidoVisitante).setValue(null);
		if (comboxPartidoEstadio != null) ((ComboBox<String>)comboxPartidoEstadio).setValue(null);
		if (comboxPartidoGrupo != null) ((ComboBox<String>)comboxPartidoGrupo).setValue(null);
		if (DatePartido != null) DatePartido.setValue(null);
		idPartidoSeleccionado = -1;
	}

	@FXML
	void agregarPartidos(ActionEvent event) {
		String local = ((ComboBox<String>)comboxPartidoLocal).getValue();
		String visitante = ((ComboBox<String>)comboxPartidoVisitante).getValue();
		String estadio = ((ComboBox<String>)comboxPartidoEstadio).getValue();
		String grupo = ((ComboBox<String>)comboxPartidoGrupo).getValue();
		if (local == null || visitante == null || estadio == null || grupo == null || DatePartido.getValue() == null) {
			mostrarAlerta("Error", "Complete todos los campos.");
			return;
		}

		// Validación: equipos no pueden ser iguales
		if (local.equals(visitante)) {
			mostrarAlerta("Error", "El equipo local y visitante no pueden ser el mismo.");
			return;
		}

		try (Connection conn = Conexion.getConexion()) {
			int idLocal = 0, idVisitante = 0, idEstadio = 0, idGrupo = 0;
			try (PreparedStatement ps = conn.prepareStatement("SELECT id_equipo FROM Equipo WHERE nombre=?")) {
				ps.setString(1, local); ResultSet rs = ps.executeQuery(); if (rs.next()) idLocal = rs.getInt(1);
			}
			try (PreparedStatement ps = conn.prepareStatement("SELECT id_equipo FROM Equipo WHERE nombre=?")) {
				ps.setString(1, visitante); ResultSet rs = ps.executeQuery(); if (rs.next()) idVisitante = rs.getInt(1);
			}
			try (PreparedStatement ps = conn.prepareStatement("SELECT id_estadio FROM Estadio WHERE nombre=?")) {
				ps.setString(1, estadio); ResultSet rs = ps.executeQuery(); if (rs.next()) idEstadio = rs.getInt(1);
			}
			try (PreparedStatement ps = conn.prepareStatement("SELECT id_grupo FROM Grupo WHERE nombre=?")) {
				ps.setString(1, grupo); ResultSet rs = ps.executeQuery(); if (rs.next()) idGrupo = rs.getInt(1);
			}

			// Validación de duplicado
			try (PreparedStatement psChk = conn.prepareStatement(
				"SELECT COUNT(*) FROM Partido WHERE id_equipo_local=? AND id_equipo_visitante=? AND CAST(fecha AS DATE)=CAST(? AS DATE)")) {
				psChk.setInt(1, idLocal);
				psChk.setInt(2, idVisitante);
				psChk.setTimestamp(3,
					java.sql.Timestamp.valueOf(DatePartido.getValue().atStartOfDay()));
				ResultSet rsChk = psChk.executeQuery();
				if (rsChk.next() && rsChk.getInt(1) > 0) {
					mostrarAlerta("Error", "Ya existe ese partido en esa fecha.");
					return;
				}
			}

			try (PreparedStatement ps = conn.prepareStatement(

				"INSERT INTO Partido (fecha, id_equipo_local, id_equipo_visitante, id_estadio, id_grupo) VALUES (?,?,?,?,?)")) {
				ps.setTimestamp(1, java.sql.Timestamp.valueOf(DatePartido.getValue().atStartOfDay()));
				ps.setInt(2, idLocal); ps.setInt(3, idVisitante);
				ps.setInt(4, idEstadio); ps.setInt(5, idGrupo);
				ps.executeUpdate();
			}
			limpiarCamposPartidos(); cargarPartidos();
			mostrarInfo("Éxito", "Partido agregado.");
		} catch (Exception e) {
			mostrarAlerta("Error", e.getMessage());
		}
	}

	@FXML
	void actualizarPartidos(ActionEvent event) {
		if (idPartidoSeleccionado == -1) { mostrarAlerta("Error", "Seleccione un partido."); return; }
		String local = ((ComboBox<String>)comboxPartidoLocal).getValue();
		String visitante = ((ComboBox<String>)comboxPartidoVisitante).getValue();
		String estadio = ((ComboBox<String>)comboxPartidoEstadio).getValue();
		String grupo = ((ComboBox<String>)comboxPartidoGrupo).getValue();
		try (Connection conn = Conexion.getConexion()) {
			int idLocal = 0, idVisitante = 0, idEstadio = 0, idGrupo = 0;
			try (PreparedStatement ps = conn.prepareStatement("SELECT id_equipo FROM Equipo WHERE nombre=?")) {
				ps.setString(1, local); ResultSet rs = ps.executeQuery(); if (rs.next()) idLocal = rs.getInt(1);
			}
			try (PreparedStatement ps = conn.prepareStatement("SELECT id_equipo FROM Equipo WHERE nombre=?")) {
				ps.setString(1, visitante); ResultSet rs = ps.executeQuery(); if (rs.next()) idVisitante = rs.getInt(1);
			}
			try (PreparedStatement ps = conn.prepareStatement("SELECT id_estadio FROM Estadio WHERE nombre=?")) {
				ps.setString(1, estadio); ResultSet rs = ps.executeQuery(); if (rs.next()) idEstadio = rs.getInt(1);
			}
			try (PreparedStatement ps = conn.prepareStatement("SELECT id_grupo FROM Grupo WHERE nombre=?")) {
				ps.setString(1, grupo); ResultSet rs = ps.executeQuery(); if (rs.next()) idGrupo = rs.getInt(1);
			}
			try (PreparedStatement ps = conn.prepareStatement(
				"UPDATE Partido SET fecha=?, id_equipo_local=?, id_equipo_visitante=?, id_estadio=?, id_grupo=? WHERE id_partido=?")) {
				ps.setTimestamp(1, DatePartido.getValue() != null ? java.sql.Timestamp.valueOf(DatePartido.getValue().atStartOfDay()) : null);
				ps.setInt(2, idLocal); ps.setInt(3, idVisitante);
				ps.setInt(4, idEstadio); ps.setInt(5, idGrupo);
				ps.setInt(6, idPartidoSeleccionado);
				ps.executeUpdate();
			}
			limpiarCamposPartidos(); cargarPartidos();
			mostrarInfo("Éxito", "Partido actualizado.");
		} catch (Exception e) {
			mostrarAlerta("Error", e.getMessage());
		}
	}

	@FXML
	void buscarPartidos(ActionEvent event) {
		String grupo = comboxPartidoGrupo != null ? ((ComboBox<String>)comboxPartidoGrupo).getValue() : null;
		ObservableList<Partido> lista = FXCollections.observableArrayList();
		try (Connection conn = Conexion.getConexion();
		     PreparedStatement ps = conn.prepareStatement(
			"SELECT p.id_partido, p.fecha, el.nombre as local, ev.nombre as visitante, "
			+ "e.nombre as estadio, g.nombre as grupo "
			+ "FROM Partido p "
			+ "JOIN Equipo el ON p.id_equipo_local = el.id_equipo "
			+ "JOIN Equipo ev ON p.id_equipo_visitante = ev.id_equipo "
			+ "JOIN Estadio e ON p.id_estadio = e.id_estadio "
			+ "JOIN Grupo g ON p.id_grupo = g.id_grupo "
			+ "WHERE g.nombre LIKE ?")) {
			ps.setString(1, "%" + (grupo != null ? grupo : "") + "%");
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					java.sql.Timestamp ts = rs.getTimestamp("fecha");
					lista.add(new Partido(rs.getInt("id_partido"),
						ts != null ? ts.toLocalDateTime() : null,
						0, 0, 0, 0,
						rs.getString("local"), rs.getString("visitante"),
						rs.getString("estadio"), rs.getString("grupo")));
				}
			}
			((TableView<Partido>)tabPartidos).setItems(lista);
		} catch (Exception e) {
			mostrarAlerta("Error", e.getMessage());
		}
	}

	@FXML
	void eliminarPartidos(ActionEvent event) {
		if (idPartidoSeleccionado == -1) { mostrarAlerta("Error", "Seleccione un partido."); return; }
		try (Connection conn = Conexion.getConexion();
		     PreparedStatement ps = conn.prepareStatement("DELETE FROM Partido WHERE id_partido=?")) {
			ps.setInt(1, idPartidoSeleccionado);
			ps.executeUpdate();
			limpiarCamposPartidos(); cargarPartidos();
			mostrarInfo("Éxito", "Partido eliminado.");
		} catch (Exception e) {
			mostrarAlerta("Error", e.getMessage());
		}
	}

	@FXML
	void ejecutarConsultas(ActionEvent event) {
		if (tabConsultas == null) return;
		RadioButton seleccionado = (RadioButton) tgConsultas.getSelectedToggle();
		if (seleccionado == null) { mostrarAlerta("Error", "Seleccione una consulta."); return; }
		if (seleccionado == rdoConsulta1) consultaJugadorMasCostosoPorConfederacion();
		else if (seleccionado == rdoConsulta2) consultaPartidosPorEstadio();
		else if (seleccionado == rdoConsulta3) consultaEquipoMasCostosoPorPaisAnfitrion();
		else if (seleccionado == rdoConsulta4) consultaJugadoresMenores21();
	}

	@SuppressWarnings("unchecked")
	private void consultaJugadorMasCostosoPorConfederacion() {
		String filtroConf = textParametroBusqueda != null && !textParametroBusqueda.getText().trim().isEmpty()
			? textParametroBusqueda.getText().trim() : null;
		ObservableList<javafx.beans.property.SimpleStringProperty[]> lista = FXCollections.observableArrayList();
		String sql = "SELECT c.nombre as confederacion, j.nombre + ' ' + j.apellido as jugador, MAX(j.valor) as valor "
			+ "FROM Jugador j "
			+ "JOIN Equipo e ON j.id_equipo = e.id_equipo "
			+ "JOIN Confederacion c ON e.id_confederacion = c.id_confederacion "
			+ "GROUP BY c.nombre, j.nombre, j.apellido "
			+ "ORDER BY c.nombre, valor DESC";
		ejecutarConsultaGenerica(sql, new String[]{"Confederación", "Jugador", "Valor"});
	}

	private void consultaPartidosPorEstadio() {
		String estadio = combSeleccioneEstadio != null ? (String)((ComboBox<String>)combSeleccioneEstadio).getValue() : null;
		if (estadio == null) { mostrarAlerta("Error", "Seleccione un estadio."); return; }
		String sql = "SELECT p.id_partido, el.nombre as local, ev.nombre as visitante, "
			+ "e.nombre as estadio, p.fecha "
			+ "FROM Partido p "
			+ "JOIN Equipo el ON p.id_equipo_local = el.id_equipo "
			+ "JOIN Equipo ev ON p.id_equipo_visitante = ev.id_equipo "
			+ "JOIN Estadio e ON p.id_estadio = e.id_estadio "
			+ "WHERE e.nombre = '" + estadio + "'";
		ejecutarConsultaGenerica(sql, new String[]{"ID", "Local", "Visitante", "Estadio", "Fecha"});
	}

	private void consultaEquipoMasCostosoPorPaisAnfitrion() {
		String filtroPais = textParametroBusqueda != null && !textParametroBusqueda.getText().trim().isEmpty()
			? textParametroBusqueda.getText().trim() : null;
		String wherePais = filtroPais != null
			? "WHERE p.nombre = '" + filtroPais + "' "
			: "WHERE p.nombre IN ('México', 'Estados Unidos', 'Canadá') ";
		String sql = "SELECT p.nombre as pais, e.nombre as equipo, SUM(j.valor) as valor_total "
			+ "FROM Jugador j "
			+ "JOIN Equipo e ON j.id_equipo = e.id_equipo "
			+ "JOIN Pais p ON e.id_pais = p.id_pais "
			+ wherePais
			+ "GROUP BY p.nombre, e.nombre "
			+ "ORDER BY p.nombre, valor_total DESC";
		ejecutarConsultaGenerica(sql, new String[]{"País", "Equipo", "Valor Total"});
	}

	private void consultaJugadoresMenores21() {
		int edad = 21;
		try {
			if (textParametroBusqueda != null && !textParametroBusqueda.getText().trim().isEmpty())
				edad = Integer.parseInt(textParametroBusqueda.getText().trim());
		} catch (NumberFormatException e) { edad = 21; }
		String sql = "SELECT e.nombre as equipo, COUNT(j.id_jugador) as cantidad "
			+ "FROM Jugador j "
			+ "JOIN Equipo e ON j.id_equipo = e.id_equipo "
			+ "WHERE DATEDIFF(YEAR, j.fecha_nacimiento, GETDATE()) < " + edad + " "
			+ "GROUP BY e.nombre "
			+ "ORDER BY cantidad DESC";
		ejecutarConsultaGenerica(sql, new String[]{"Equipo", "Jugadores < 21 años"});
	}

	@SuppressWarnings("unchecked")
	private void ejecutarConsultaGenerica(String sql, String[] columnas) {
		try (Connection conn = Conexion.getConexion();
		     PreparedStatement ps = conn.prepareStatement(sql);
		     ResultSet rs = ps.executeQuery()) {
			TableView<ObservableList<String>> tabla = (TableView<ObservableList<String>>) tabConsultas;
			tabla.getColumns().clear();
			for (int i = 0; i < columnas.length; i++) {
				final int idx = i;
				TableColumn<ObservableList<String>, String> col = new TableColumn<>(columnas[i]);
				col.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().get(idx)));
				tabla.getColumns().add(col);
			}
			ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
			int numCols = rs.getMetaData().getColumnCount();
			while (rs.next()) {
				ObservableList<String> fila = FXCollections.observableArrayList();
				for (int i = 1; i <= numCols; i++) {
					String val = rs.getString(i);
					fila.add(val != null ? val : "");
				}
				data.add(fila);
			}
			tabla.setItems(data);
		} catch (Exception e) {
			mostrarAlerta("Error", e.getMessage());
		}
	}

	// ========== REPORTES PDF ==========

	private void inicializarCamposReportes() {
		if (dateReporte != null) dateReporte.setVisible(false);
		if (textPesoReporte != null) textPesoReporte.setVisible(false);
		if (textEstaturaReporte != null) textEstaturaReporte.setVisible(false);
		if (comboEquipoReporte != null) comboEquipoReporte.setVisible(false);
		if (comboConfederacionReporte != null) comboConfederacionReporte.setVisible(false);
		// Agregar listener a los radio buttons
		if (tgReportes != null) {
			tgReportes.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
				if (newVal != null) onReporteSeleccionado(null);
			});
		}
	}

	private void actualizarParametrosConsulta() {
		// Ocultar todos
		if (combSeleccioneEstadio != null) combSeleccioneEstadio.setVisible(false);
		if (textParametroBusqueda != null) textParametroBusqueda.setVisible(false);

		RadioButton sel = (RadioButton) tgConsultas.getSelectedToggle();
		if (sel == rdoConsulta1) {
			// Confederacion — usar textParametroBusqueda como campo
			if (textParametroBusqueda != null) {
				textParametroBusqueda.setVisible(true);
				textParametroBusqueda.setPromptText("Confederación (UEFA, CONMEBOL...)");
			}
		} else if (sel == rdoConsulta2) {
			if (combSeleccioneEstadio != null) combSeleccioneEstadio.setVisible(true);
		} else if (sel == rdoConsulta3) {
			// País anfitrión — usar textParametroBusqueda
			if (textParametroBusqueda != null) {
				textParametroBusqueda.setVisible(true);
				textParametroBusqueda.setPromptText("País: México, Estados Unidos o Canadá");
			}
		} else if (sel == rdoConsulta4) {
			// Edad
			if (textParametroBusqueda != null) {
				textParametroBusqueda.setVisible(true);
				textParametroBusqueda.setPromptText("Edad máxima (ej: 21)");
			}
		}
	}


	private void cargarCombosReportes() {
		try (Connection conn = Conexion.getConexion()) {
			ObservableList<String> equipos = FXCollections.observableArrayList();
			try (PreparedStatement ps = conn.prepareStatement("SELECT nombre FROM Equipo ORDER BY nombre");
			     ResultSet rs = ps.executeQuery()) {
				while (rs.next()) equipos.add(rs.getString("nombre"));
			}
			if (comboEquipoReporte != null) comboEquipoReporte.setItems(equipos);

			ObservableList<String> confs = FXCollections.observableArrayList();
			try (PreparedStatement ps = conn.prepareStatement("SELECT nombre FROM Confederacion ORDER BY nombre");
			     ResultSet rs = ps.executeQuery()) {
				while (rs.next()) confs.add(rs.getString("nombre"));
			}
			if (comboConfederacionReporte != null) comboConfederacionReporte.setItems(confs);
		} catch (Exception e) { e.printStackTrace(); }
	}

	@FXML
	void onReporteSeleccionado(ActionEvent event) {
		if (dateReporte != null) dateReporte.setVisible(false);
		if (textPesoReporte != null) textPesoReporte.setVisible(false);
		if (textEstaturaReporte != null) textEstaturaReporte.setVisible(false);
		if (comboEquipoReporte != null) comboEquipoReporte.setVisible(false);
		if (comboConfederacionReporte != null) comboConfederacionReporte.setVisible(false);

		RadioButton sel = (RadioButton) tgReportes.getSelectedToggle();
		if (sel == rdoReporte1) {
			if (dateReporte != null) dateReporte.setVisible(true);
		} else if (sel == rdoReporte2) {
			if (textPesoReporte != null) textPesoReporte.setVisible(true);
			if (textEstaturaReporte != null) textEstaturaReporte.setVisible(true);
			if (comboEquipoReporte != null) comboEquipoReporte.setVisible(true);
		} else if (sel == rdoReporte3) {
			if (comboConfederacionReporte != null) comboConfederacionReporte.setVisible(true);
			if (comboEquipoReporte != null) comboEquipoReporte.setVisible(true);
		}
		// Reporte 4 no necesita parámetros
	}

	@FXML
	void generarReporte(ActionEvent event) {
		if (tgReportes == null || tgReportes.getSelectedToggle() == null) {
			mostrarAlerta("Error", "Seleccione un reporte.");
			return;
		}
		RadioButton selNombre = (RadioButton) tgReportes.getSelectedToggle();
		String nombreArchivo = "reporte.pdf";
		if (selNombre == rdoReporte1) nombreArchivo = "reporte_bitacora.pdf";
		else if (selNombre == rdoReporte2) nombreArchivo = "reporte_jugadores.pdf";
		else if (selNombre == rdoReporte3) nombreArchivo = "reporte_valor_equipos.pdf";
		else if (selNombre == rdoReporte4) nombreArchivo = "reporte_paises_anfitrion.pdf";

		FileChooser fc = new FileChooser();
		fc.setTitle("Guardar Reporte PDF");
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
		fc.setInitialFileName(generarNombreUnico(nombreArchivo));
		File archivo = fc.showSaveDialog(btnReportes.getScene().getWindow());
		if (archivo == null) return;

		try {
			RadioButton sel = (RadioButton) tgReportes.getSelectedToggle();
			if (sel == rdoReporte1) generarReporte1(archivo);
			else if (sel == rdoReporte2) generarReporte2(archivo);
			else if (sel == rdoReporte3) generarReporte3(archivo);
			else if (sel == rdoReporte4) generarReporte4(archivo);
		} catch (Exception e) {
			mostrarAlerta("Error", "Error generando PDF: " + e.getMessage());
		}
	}

	private void generarReporte1(File archivo) throws Exception {
		if (dateReporte.getValue() == null) { mostrarAlerta("Error", "Seleccione una fecha."); return; }
		Document doc = new Document();
		PdfWriter.getInstance(doc, new FileOutputStream(archivo));
		doc.open();
		doc.add(new Paragraph("REPORTE DE BITACORA - " + dateReporte.getValue(), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
		doc.add(new Paragraph(" "));
		PdfPTable tabla = new PdfPTable(4);
		tabla.setWidthPercentage(100);
		for (String h : new String[]{"ID", "Usuario", "Entrada", "Salida"}) {
			PdfPCell cell = new PdfPCell(new Phrase(h, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)));
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			tabla.addCell(cell);
		}
		try (Connection conn = Conexion.getConexion();
		     PreparedStatement ps = conn.prepareStatement(
			"SELECT b.id_bitacora, u.username, b.fecha_entrada, b.fecha_salida "
			+ "FROM Bitacora b JOIN Usuario u ON b.id_usuario = u.id_usuario "
			+ "WHERE CAST(b.fecha_entrada AS DATE) = ?")) {
			ps.setDate(1, java.sql.Date.valueOf(dateReporte.getValue()));
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					tabla.addCell(String.valueOf(rs.getInt("id_bitacora")));
					tabla.addCell(rs.getString("username"));
					tabla.addCell(rs.getString("fecha_entrada"));
					String salida = rs.getString("fecha_salida");
					tabla.addCell(salida != null ? salida : "En sesion");
				}
			}
		}
		doc.add(tabla);
		doc.close();
		mostrarInfo("Exito", "Reporte guardado en: " + archivo.getPath());
	}

	private void generarReporte2(File archivo) throws Exception {
		Document doc = new Document();
		PdfWriter.getInstance(doc, new FileOutputStream(archivo));
		doc.open();
		doc.add(new Paragraph("REPORTE DE JUGADORES", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
		doc.add(new Paragraph(" "));
		PdfPTable tabla = new PdfPTable(6);
		tabla.setWidthPercentage(100);
		for (String h : new String[]{"Nombre", "Apellido", "Peso", "Estatura", "Valor", "Equipo"}) {
			PdfPCell cell = new PdfPCell(new Phrase(h, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)));
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			tabla.addCell(cell);
		}
		String sql = "SELECT j.nombre, j.apellido, j.peso, j.estatura, j.valor, e.nombre as equipo "
			+ "FROM Jugador j JOIN Equipo e ON j.id_equipo = e.id_equipo WHERE 1=1";
		if (!textPesoReporte.getText().trim().isEmpty())
			sql += " AND j.peso <= " + textPesoReporte.getText().trim();
		if (!textEstaturaReporte.getText().trim().isEmpty())
			sql += " AND j.estatura <= " + textEstaturaReporte.getText().trim();
		if (comboEquipoReporte.getValue() != null)
			sql += " AND e.nombre = '" + comboEquipoReporte.getValue() + "'";
		try (Connection conn = Conexion.getConexion();
		     PreparedStatement ps = conn.prepareStatement(sql);
		     ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				tabla.addCell(rs.getString("nombre"));
				tabla.addCell(rs.getString("apellido"));
				tabla.addCell(rs.getString("peso"));
				tabla.addCell(rs.getString("estatura"));
				tabla.addCell(rs.getString("valor"));
				tabla.addCell(rs.getString("equipo"));
			}
		}
		doc.add(tabla);
		doc.close();
		mostrarInfo("Exito", "Reporte guardado en: " + archivo.getPath());
	}

	private void generarReporte3(File archivo) throws Exception {
		Document doc = new Document();
		PdfWriter.getInstance(doc, new FileOutputStream(archivo));
		doc.open();
		doc.add(new Paragraph("REPORTE VALOR TOTAL POR EQUIPO Y CONFEDERACION", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
		doc.add(new Paragraph(" "));
		PdfPTable tabla = new PdfPTable(3);
		tabla.setWidthPercentage(100);
		for (String h : new String[]{"Confederacion", "Equipo", "Valor Total"}) {
			PdfPCell cell = new PdfPCell(new Phrase(h, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)));
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			tabla.addCell(cell);
		}
		String whereClause = "";
		if (comboConfederacionReporte.getValue() != null)
			whereClause += "WHERE c.nombre = '" + comboConfederacionReporte.getValue() + "' ";
		if (comboEquipoReporte.getValue() != null)
			whereClause += (whereClause.isEmpty() ? "WHERE " : "AND ") + "e.nombre = '" + comboEquipoReporte.getValue() + "' ";
		String sql = "SELECT c.nombre as confederacion, e.nombre as equipo, SUM(j.valor) as total "
			+ "FROM Jugador j JOIN Equipo e ON j.id_equipo = e.id_equipo "
			+ "JOIN Confederacion c ON e.id_confederacion = c.id_confederacion "
			+ whereClause
			+ "GROUP BY c.nombre, e.nombre ORDER BY c.nombre, total DESC";
		try (Connection conn = Conexion.getConexion();
		     PreparedStatement ps = conn.prepareStatement(sql);
		     ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				tabla.addCell(rs.getString("confederacion"));
				tabla.addCell(rs.getString("equipo"));
				tabla.addCell(rs.getString("total"));
			}
		}
		doc.add(tabla);
		doc.close();
		mostrarInfo("Exito", "Reporte guardado en: " + archivo.getPath());
	}

	private void generarReporte4(File archivo) throws Exception {
		Document doc = new Document();
		PdfWriter.getInstance(doc, new FileOutputStream(archivo));
		doc.open();
		doc.add(new Paragraph("REPORTE PAISES POR SEDE ANFITRIONA", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
		doc.add(new Paragraph(" "));
		PdfPTable tabla = new PdfPTable(3);
		tabla.setWidthPercentage(100);
		for (String h : new String[]{"Pais Anfitrion", "Equipo Local", "Equipo Visitante"}) {
			PdfPCell cell = new PdfPCell(new Phrase(h, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)));
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			tabla.addCell(cell);
		}
		String sql = "SELECT p.nombre as pais, el.nombre as local, ev.nombre as visitante "
			+ "FROM Partido pt "
			+ "JOIN Estadio est ON pt.id_estadio = est.id_estadio "
			+ "JOIN Ciudad c ON est.id_ciudad = c.id_ciudad "
			+ "JOIN Pais p ON c.id_pais = p.id_pais "
			+ "JOIN Equipo el ON pt.id_equipo_local = el.id_equipo "
			+ "JOIN Equipo ev ON pt.id_equipo_visitante = ev.id_equipo "
			+ "WHERE p.nombre IN ('Mexico', 'Estados Unidos', 'Canada') "
			+ "ORDER BY p.nombre";
		try (Connection conn = Conexion.getConexion();
		     PreparedStatement ps = conn.prepareStatement(sql);
		     ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				tabla.addCell(rs.getString("pais"));
				tabla.addCell(rs.getString("local"));
				tabla.addCell(rs.getString("visitante"));
			}
		}
		doc.add(tabla);
		doc.close();
		mostrarInfo("Exito", "Reporte guardado en: " + archivo.getPath());
	}

	// ========== CERRAR SESION ==========
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


    private String generarNombreUnico(String nombreBase) {
        File carpeta = new File(System.getProperty("user.home"), "Downloads");
        String nombre = nombreBase.replace(".pdf", "");
        File archivo = new File(carpeta, nombreBase);
        int i = 1;
        while (archivo.exists()) {
            archivo = new File(carpeta, nombre + "_" + i + ".pdf");
            i++;
        }
        return archivo.getName();
    }
}
