package controllers;

import application.Conexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;
import java.io.FileOutputStream;
import java.io.File;
import javafx.stage.FileChooser;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Usuario;
import model.Bitacora;
import emun.Rol;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



public class MenuPrincipalAdministradorController {

    // Botones menú
    @FXML private Button btnUsuario;
    @FXML private Button btnBitacora;
    @FXML private Button btnEquipos;
    @FXML private Button btnJugadores;
    @FXML private Button btnPartidos;
    @FXML private Button btnConsultas;
    @FXML private Button btnReportes;
    @FXML private Button btnCerrarSesion;

    // Paneles
    @FXML private StackPane contentPane;
    @FXML private AnchorPane inicioPane;
    @FXML private AnchorPane admUsuariosPane;
    @FXML private AnchorPane admBitacoraPane;
    @FXML private AnchorPane admEquiposPane;
    @FXML private AnchorPane admJugadoresPane;
    @FXML private AnchorPane admPartidosPane;
    @FXML private AnchorPane admConsultasPane;
    @FXML private AnchorPane admReportesPane; 

    // Labels inicio
    @FXML private Text textUsuario;
    @FXML private Text textUsuarioBienvenido;
    @FXML private Text textfecha;
    @FXML private Text textHora;

    // Usuarios - campos
    @FXML private TextField textAdmUsuario;
    @FXML private PasswordField textAdmContrasena;
    @FXML private ComboBox<String> comboAdmRol;

    // Usuarios - tabla
    @FXML private TableView<Usuario> tabAdmGU;
    @FXML private TableColumn<Usuario, Integer> colIDAdmGU;
    @FXML private TableColumn<Usuario, String> colUsuarioAdmGU;
    @FXML private TableColumn<Usuario, String> colRolAdmGU;

    // Bitácora
    @FXML private TableView<Bitacora> tabAdmBitacora;
    @FXML private TableColumn<Bitacora, Integer> colIDAdmBitacora;
    @FXML private TableColumn<Bitacora, String> colUsuarioAdmBitacora;
    @FXML private TableColumn<Bitacora, String> colFechaIngresoAdmBitacora;
    @FXML private TableColumn<Bitacora, String> colFechaSalidaAdmBitacora;
    @FXML private DatePicker DateAdmBitacora;

    // Equipos - campos
@FXML private TextField textAdmEquipoPais;
@FXML private TextField textAdmEquipoDirectorTecnico;
@FXML private ComboBox<String> comboAdmEquipoConfederacion;

// Equipos - tabla
@FXML private TableView<Equipo> tabAdmGE;
@FXML private TableColumn<Equipo, Integer> colIDAdmGE;
@FXML private TableColumn<Equipo, String> colEquiposAdmGE;
@FXML private TableColumn<Equipo, String> colDirectorTecnicoAdmGE;
@FXML private TableColumn<Equipo, String> colConfederacionAdmGE;

    // Usuario actual
    private static int idUsuarioActual;
    private static String usernameActual;
    private int idUsuarioSeleccionado = -1;

    public static void setUsuarioActual(int id, String username) {
        idUsuarioActual = id;
        usernameActual = username;
    }

    @FXML
    public void initialize() {
        if (textUsuario != null)
            textUsuario.setText(usernameActual != null ? usernameActual : "usuario");

        LocalDateTime ahora = LocalDateTime.now();
        if (textfecha != null)
            textfecha.setText(ahora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        if (textHora != null)
            textHora.setText(ahora.format(DateTimeFormatter.ofPattern("HH:mm:ss")));

        // Cargar roles en combo
        if (comboAdmRol != null)
            comboAdmRol.setItems(FXCollections.observableArrayList("ADMINISTRADOR", "TRADICIONAL", "ESPORADICO"));

        // Configurar columnas tabla usuarios
        if (colIDAdmGU != null) {
            colIDAdmGU.setCellValueFactory(new PropertyValueFactory<>("id"));
            colUsuarioAdmGU.setCellValueFactory(new PropertyValueFactory<>("username"));
            colRolAdmGU.setCellValueFactory(new PropertyValueFactory<>("rol"));
            cargarUsuarios();
        }

        // Configurar columnas bitácora
        if (colIDAdmBitacora != null) {
            colIDAdmBitacora.setCellValueFactory(new PropertyValueFactory<>("id"));
            colUsuarioAdmBitacora.setCellValueFactory(new PropertyValueFactory<>("usuario"));
            colFechaIngresoAdmBitacora.setCellValueFactory(new PropertyValueFactory<>("fechaEntrada"));
            colFechaSalidaAdmBitacora.setCellValueFactory(new PropertyValueFactory<>("fechaSalida"));
        }

        if (textUsuarioBienvenido != null)
            textUsuarioBienvenido.setText(usernameActual != null ? usernameActual : "usuario");
if (colIDAdmGE != null) {
    colIDAdmGE.setCellValueFactory(new PropertyValueFactory<>("id"));
    colEquiposAdmGE.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    colDirectorTecnicoAdmGE.setCellValueFactory(new PropertyValueFactory<>("director"));
    colConfederacionAdmGE.setCellValueFactory(new PropertyValueFactory<>("confederacion"));
    cargarEquipos();
}

if (comboAdmEquipoConfederacion != null)
    comboAdmEquipoConfederacion.setItems(FXCollections.observableArrayList("UEFA", "CONMEBOL", "CONCACAF", "CAF", "AFC", "OFC"));
        if (contentPane != null && inicioPane != null)
            cambiarVista(inicioPane);
    }

    // ========== NAVEGACIÓN ==========
    @FXML
    public void mostrarVentana(ActionEvent event) {
        if (event.getSource() == btnUsuario) {
            cambiarVista(admUsuariosPane);
            cargarUsuarios();
        } else if (event.getSource() == btnBitacora) {
            cambiarVista(admBitacoraPane);
        } else if (event.getSource() == btnEquipos) {
            cambiarVista(admEquiposPane);
        } else if (event.getSource() == btnJugadores) {
    cambiarVista(admJugadoresPane);
        } else if (event.getSource() == btnConsultas) {
            cambiarVista(consultasPane);
            cargarComboEstadiosConsulta();
            if (tgConsultas != null) tgConsultas.selectedToggleProperty().addListener((o,ov,nv) -> { if(nv!=null) actualizarParamsConsulta(); });
        } else if (event.getSource() == btnReportes) {
            cambiarVista(reportesPane);
            cargarCombosReportesAdmin();
            inicializarCamposReportesAdmin();
            if (tgReportes != null) tgReportes.selectedToggleProperty().addListener((o,ov,nv) -> { if(nv!=null) onReporteSeleccionadoAdmin(null); });
} else if (event.getSource() == btnPartidos) {
    cambiarVista(admPartidosPane);
} else if (event.getSource() == btnConsultas) {
    cambiarVista(admConsultasPane);
} else if (event.getSource() == btnReportes) {
    cambiarVista(admReportesPane);
} else {
            cambiarVista(inicioPane);
        }
    }
private void cambiarVista(AnchorPane pane) {
    AnchorPane[] todos = {inicioPane, admUsuariosPane, admBitacoraPane, admEquiposPane, admJugadoresPane, consultasPane, reportesPane};
    for (AnchorPane p : todos) {
        if (p != null) p.setVisible(false);
    }
    if (pane != null) pane.setVisible(true);
}
    

    // ========== CRUD USUARIOS ==========
    private void cargarUsuarios() {
        ObservableList<Usuario> lista = FXCollections.observableArrayList();
        try {
            Connection conn = Conexion.getConexion();
            ResultSet rs = conn.createStatement().executeQuery("SELECT id_usuario, username, rol FROM Usuario WHERE activo = 1");
            while (rs.next()) {
                lista.add(new Usuario(rs.getInt("id_usuario"), rs.getString("username"), rs.getString("rol")));
            }
            tabAdmGU.setItems(lista);

            // Seleccionar fila y llenar campos
            tabAdmGU.setOnMouseClicked(e -> {
                Usuario u = tabAdmGU.getSelectionModel().getSelectedItem();
                if (u != null) {
                    idUsuarioSeleccionado = u.getId();
                    textAdmUsuario.setText(u.getUsername());
                    comboAdmRol.setValue(u.getRol());
                    textAdmContrasena.clear();
                }
            });
            conn.close();
        } catch (Exception e) {
            mostrarAlerta("Error", e.getMessage());
        }
    }

    @FXML
    void AdmAgragar(ActionEvent event) {
        String usuario = textAdmUsuario.getText().trim();
        String clave = textAdmContrasena.getText().trim();
        String rol = comboAdmRol.getValue();

        if (usuario.isEmpty() || clave.isEmpty() || rol == null) {
            mostrarAlerta("Error", "Todos los campos son obligatorios.");
            return;
        }
        try {
            Connection conn = Conexion.getConexion();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO Usuario (username, password, rol, activo) VALUES (?, ?, ?, 1)");
            ps.setString(1, usuario);
            ps.setString(2, clave);
            ps.setString(3, rol);
            ps.executeUpdate();
            conn.close();
            limpiarCamposUsuario();
            cargarUsuarios();
            mostrarInfo("Éxito", "Usuario agregado correctamente.");
        } catch (Exception e) {
            mostrarAlerta("Error", e.getMessage());
        }
    }

    @FXML
    void AdmActualizar(ActionEvent event) {
        if (idUsuarioSeleccionado == -1) {
            mostrarAlerta("Error", "Seleccione un usuario de la tabla.");
            return;
        }
        String usuario = textAdmUsuario.getText().trim();
        String clave = textAdmContrasena.getText().trim();
        String rol = comboAdmRol.getValue();

        try {
            Connection conn = Conexion.getConexion();
            String sql = clave.isEmpty()
                ? "UPDATE Usuario SET username=?, rol=? WHERE id_usuario=?"
                : "UPDATE Usuario SET username=?, rol=?, password=? WHERE id_usuario=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, rol);
            if (!clave.isEmpty()) {
                ps.setString(3, clave);
                ps.setInt(4, idUsuarioSeleccionado);
            } else {
                ps.setInt(3, idUsuarioSeleccionado);
            }
            ps.executeUpdate();
            conn.close();
            limpiarCamposUsuario();
            cargarUsuarios();
            mostrarInfo("Éxito", "Usuario actualizado correctamente.");
        } catch (Exception e) {
            mostrarAlerta("Error", e.getMessage());
        }
    }

    @FXML
    void AdmEliminar(ActionEvent event) {
        if (idUsuarioSeleccionado == -1) {
            mostrarAlerta("Error", "Seleccione un usuario de la tabla.");
            return;
        }
        try {
            Connection conn = Conexion.getConexion();
            PreparedStatement ps = conn.prepareStatement("UPDATE Usuario SET activo=0 WHERE id_usuario=?");
            ps.setInt(1, idUsuarioSeleccionado);
            ps.executeUpdate();
            conn.close();
            limpiarCamposUsuario();
            cargarUsuarios();
            mostrarInfo("Éxito", "Usuario eliminado correctamente.");
        } catch (Exception e) {
            mostrarAlerta("Error", e.getMessage());
        }
    }

    @FXML
    void AdmGUBuscar(ActionEvent event) {
        String buscar = textAdmUsuario.getText().trim();
        ObservableList<Usuario> lista = FXCollections.observableArrayList();
        try {
            Connection conn = Conexion.getConexion();
            PreparedStatement ps = conn.prepareStatement("SELECT id_usuario, username, rol FROM Usuario WHERE username LIKE ? AND activo=1");
            ps.setString(1, "%" + buscar + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Usuario(rs.getInt("id_usuario"), rs.getString("username"), rs.getString("rol")));
            }
            tabAdmGU.setItems(lista);
            conn.close();
        } catch (Exception e) {
            mostrarAlerta("Error", e.getMessage());
        }
    }

    private void limpiarCamposUsuario() {
        textAdmUsuario.clear();
        textAdmContrasena.clear();
        comboAdmRol.setValue(null);
        idUsuarioSeleccionado = -1;
    }

    // ========== BITÁCORA ==========
    @FXML
    void AdmBitacoraBuscar(ActionEvent event) {
        if (DateAdmBitacora.getValue() == null) {
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
            ps.setDate(1, java.sql.Date.valueOf(DateAdmBitacora.getValue()));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Bitacora(
                    rs.getInt("id_bitacora"),
                    rs.getString("username"),
                    rs.getString("fecha_entrada"),
                    rs.getString("fecha_salida")
                ));
            }
            tabAdmBitacora.setItems(lista);
            conn.close();
        } catch (Exception e) {
            mostrarAlerta("Error", e.getMessage());
        }
    }

    @FXML
    void AdmBitacoraExportar(ActionEvent event) {
        System.out.println("pendiente implementar - exportar PDF bitácora");
    }

    // ========== MÉTODOS PENDIENTES ==========
  // ========== CRUD EQUIPOS ==========
private int idEquipoSeleccionado = -1;

    // Consultas
    @FXML private RadioButton rdoConsulta1;
    @FXML private RadioButton rdoConsulta2;
    @FXML private RadioButton rdoConsulta3;
    @FXML private RadioButton rdoConsulta4;
    @FXML private ToggleGroup tgConsultas;
    @FXML private TextField textParametroBusqueda;
    @FXML private ComboBox<String> combSeleccioneEstadio;
    @FXML private TableView<?> tabConsultas;
    @FXML private AnchorPane consultasPane;

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


private void cargarEquipos() {
    if (tabAdmGE == null) return;
    ObservableList<Equipo> lista = FXCollections.observableArrayList();
    try (Connection conn = Conexion.getConexion();
         Statement st = conn.createStatement();
         ResultSet rs = st.executeQuery(
             "SELECT e.id_equipo, e.nombre, ISNULL(dt.nombre,'Sin DT') as director, c.siglas " +
             "FROM Equipo e " +
             "LEFT JOIN DirectorTecnico dt ON dt.id_equipo = e.id_equipo " +
             "JOIN Confederacion c ON c.id_confederacion = e.id_confederacion")) {
        while (rs.next()) {
            lista.add(new Equipo(rs.getInt("id_equipo"), rs.getString("nombre"),
                rs.getString("director"), rs.getString("siglas")));
        }
        tabAdmGE.setItems(lista);
        tabAdmGE.setOnMouseClicked(e -> {
            Equipo eq = tabAdmGE.getSelectionModel().getSelectedItem();
            if (eq != null) {
                idEquipoSeleccionado = eq.getId();
                if (textAdmEquipoPais != null) textAdmEquipoPais.setText(eq.getNombre());
                if (textAdmEquipoDirectorTecnico != null) textAdmEquipoDirectorTecnico.setText(eq.getDirector());
                if (comboAdmEquipoConfederacion != null) comboAdmEquipoConfederacion.setValue(eq.getConfederacion());
            }
        });
    } catch (Exception e) { mostrarAlerta("Error", e.getMessage()); }
}

@FXML void AdmEquiposAgregar(ActionEvent event) {
    if (textAdmEquipoPais == null || comboAdmEquipoConfederacion == null) return;
    String nombre = textAdmEquipoPais.getText().trim();
    String conf = comboAdmEquipoConfederacion.getValue();
    if (nombre.isEmpty() || conf == null) { mostrarAlerta("Error", "Complete todos los campos."); return; }
    try (Connection conn = Conexion.getConexion();
         PreparedStatement ps = conn.prepareStatement(
             "INSERT INTO Equipo (nombre, id_confederacion, id_pais, valor_total) " +
             "SELECT ?, id_confederacion, (SELECT TOP 1 id_pais FROM Pais WHERE nombre=?), 0 FROM Confederacion WHERE siglas=?")) {
        ps.setString(1, nombre); ps.setString(2, nombre); ps.setString(3, conf);
        ps.executeUpdate();
        cargarEquipos();
        mostrarInfo("Éxito", "Equipo agregado.");
    } catch (Exception e) { mostrarAlerta("Error", e.getMessage()); }
}

@FXML void AdmEquiposActualizar(ActionEvent event) {
    if (idEquipoSeleccionado == -1) { mostrarAlerta("Error", "Seleccione un equipo."); return; }
    String nombre = textAdmEquipoPais.getText().trim();
    String conf = comboAdmEquipoConfederacion.getValue();
    try (Connection conn = Conexion.getConexion();
         PreparedStatement ps = conn.prepareStatement(
             "UPDATE Equipo SET nombre=?, id_confederacion=(SELECT id_confederacion FROM Confederacion WHERE siglas=?) WHERE id_equipo=?")) {
        ps.setString(1, nombre); ps.setString(2, conf); ps.setInt(3, idEquipoSeleccionado);
        ps.executeUpdate();
        cargarEquipos();
        mostrarInfo("Éxito", "Equipo actualizado.");
    } catch (Exception e) { mostrarAlerta("Error", e.getMessage()); }
}

@FXML void AdmEquiposBuscar(ActionEvent event) {
    if (textAdmEquipoPais == null) return;
    String buscar = textAdmEquipoPais.getText().trim();
    ObservableList<Equipo> lista = FXCollections.observableArrayList();
    try (Connection conn = Conexion.getConexion();
         PreparedStatement ps = conn.prepareStatement(
             "SELECT e.id_equipo, e.nombre, ISNULL(dt.nombre,'Sin DT') as director, c.siglas " +
             "FROM Equipo e LEFT JOIN DirectorTecnico dt ON dt.id_equipo=e.id_equipo " +
             "JOIN Confederacion c ON c.id_confederacion=e.id_confederacion WHERE e.nombre LIKE ?")) {
        ps.setString(1, "%" + buscar + "%");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) lista.add(new Equipo(rs.getInt("id_equipo"), rs.getString("nombre"),
            rs.getString("director"), rs.getString("siglas")));
        tabAdmGE.setItems(lista);
    } catch (Exception e) { mostrarAlerta("Error", e.getMessage()); }
}

@FXML void AdmEquiposEliminar(ActionEvent event) {
    if (idEquipoSeleccionado == -1) { mostrarAlerta("Error", "Seleccione un equipo."); return; }
    try (Connection conn = Conexion.getConexion();
         PreparedStatement ps = conn.prepareStatement("DELETE FROM Equipo WHERE id_equipo=?")) {
        ps.setInt(1, idEquipoSeleccionado);
        ps.executeUpdate();
        idEquipoSeleccionado = -1;
        cargarEquipos();
        mostrarInfo("Éxito", "Equipo eliminado.");
    } catch (Exception e) { mostrarAlerta("Error", e.getMessage()); }
}

    // ========== CERRAR SESIÓN ==========
    @FXML
    void cerrarSesion(javafx.event.ActionEvent event) {
        CerrarSesion(event);
    }

    @FXML
    void CerrarSesion(ActionEvent event) {
        try {
            Connection conn = Conexion.getConexion();
            PreparedStatement ps = conn.prepareStatement("UPDATE Bitacora SET fecha_salida = GETDATE() WHERE id_usuario = ? AND fecha_salida IS NULL");
            ps.setInt(1, idUsuarioActual);
            ps.executeUpdate();
            conn.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnCerrarSesion.getScene().getWindow();
            stage.setScene(new Scene(root));
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

    // ========== CLASES MODELO ==========
    public static class Usuario {
        private int id;
        private String username, rol;
        public Usuario(int id, String username, String rol) {
            this.id = id; this.username = username; this.rol = rol;
        }
        public int getId() { return id; }
        public String getUsername() { return username; }
        public String getRol() { return rol; }
    }

    public static class Bitacora {
        private int id;
        private String usuario, fechaEntrada, fechaSalida;
        public Bitacora(int id, String usuario, String fechaEntrada, String fechaSalida) {
            this.id = id; this.usuario = usuario;
            this.fechaEntrada = fechaEntrada; this.fechaSalida = fechaSalida;
        }
        public int getId() { return id; }
        public String getUsuario() { return usuario; }
        public String getFechaEntrada() { return fechaEntrada; }
        public String getFechaSalida() { return fechaSalida; }
    }
    // ========== CLASE MODELO EQUIPO ==========
public static class Equipo {
    private int id;
    private String nombre, director, confederacion;
    public Equipo(int id, String nombre, String director, String confederacion) {
        this.id = id; this.nombre = nombre; this.director = director; this.confederacion = confederacion;
    }
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDirector() { return director; }
    public String getConfederacion() { return confederacion; }
}

    private void cargarComboEstadiosConsulta() {
        if (combSeleccioneEstadio == null) return;
        javafx.collections.ObservableList<String> est = javafx.collections.FXCollections.observableArrayList();
        try (java.sql.Connection conn = application.Conexion.getConexion();
             java.sql.PreparedStatement ps = conn.prepareStatement("SELECT nombre FROM Estadio ORDER BY nombre");
             java.sql.ResultSet rs = ps.executeQuery()) {
            while (rs.next()) est.add(rs.getString("nombre"));
            combSeleccioneEstadio.setItems(est);
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void actualizarParamsConsulta() {
        if (combSeleccioneEstadio != null) combSeleccioneEstadio.setVisible(false);
        if (textParametroBusqueda != null) textParametroBusqueda.setVisible(false);
        RadioButton sel = (RadioButton) tgConsultas.getSelectedToggle();
        if (sel == rdoConsulta1) {
            if (textParametroBusqueda != null) { textParametroBusqueda.setVisible(true); textParametroBusqueda.setPromptText("Confederacion (UEFA, CONMEBOL...)"); }
        } else if (sel == rdoConsulta2) {
            if (combSeleccioneEstadio != null) combSeleccioneEstadio.setVisible(true);
        } else if (sel == rdoConsulta3) {
            if (textParametroBusqueda != null) { textParametroBusqueda.setVisible(true); textParametroBusqueda.setPromptText("Pais: Mexico, Estados Unidos o Canada"); }
        } else if (sel == rdoConsulta4) {
            if (textParametroBusqueda != null) { textParametroBusqueda.setVisible(true); textParametroBusqueda.setPromptText("Edad maxima (ej: 21)"); }
        }
    }

    @FXML
    void ejecutarConsultas(javafx.event.ActionEvent event) {
        if (tgConsultas == null || tgConsultas.getSelectedToggle() == null) { mostrarAlerta("Error", "Seleccione una consulta."); return; }
        RadioButton sel = (RadioButton) tgConsultas.getSelectedToggle();
        String sql = ""; String[] cols = {};
        if (sel == rdoConsulta1) {
            String f = textParametroBusqueda != null ? textParametroBusqueda.getText().trim() : "";
            sql = "SELECT c.nombre as confederacion, j.nombre + ' ' + j.apellido as jugador, MAX(j.valor) as valor FROM Jugador j JOIN Equipo e ON j.id_equipo = e.id_equipo JOIN Confederacion c ON e.id_confederacion = c.id_confederacion " + (f.isEmpty() ? "" : "WHERE c.siglas = '" + f + "' ") + "GROUP BY c.nombre, j.nombre, j.apellido ORDER BY c.nombre, valor DESC";
            cols = new String[]{"Confederacion", "Jugador", "Valor"};
        } else if (sel == rdoConsulta2) {
            String est = combSeleccioneEstadio != null ? (String)combSeleccioneEstadio.getValue() : null;
            if (est == null) { mostrarAlerta("Error", "Seleccione un estadio."); return; }
            sql = "SELECT p.id_partido, el.nombre as local, ev.nombre as visitante, e.nombre as estadio, p.fecha FROM Partido p JOIN Equipo el ON p.id_equipo_local = el.id_equipo JOIN Equipo ev ON p.id_equipo_visitante = ev.id_equipo JOIN Estadio e ON p.id_estadio = e.id_estadio WHERE e.nombre = '" + est + "'";
            cols = new String[]{"ID", "Local", "Visitante", "Estadio", "Fecha"};
        } else if (sel == rdoConsulta3) {
            String f = textParametroBusqueda != null ? textParametroBusqueda.getText().trim() : "";
            String w = f.isEmpty() ? "WHERE p.nombre IN ('Mexico', 'Estados Unidos', 'Canada')" : "WHERE p.nombre = '" + f + "'";
            sql = "SELECT p.nombre as pais, e.nombre as equipo, SUM(j.valor) as valor_total FROM Jugador j JOIN Equipo e ON j.id_equipo = e.id_equipo JOIN Pais p ON e.id_pais = p.id_pais " + w + " GROUP BY p.nombre, e.nombre ORDER BY p.nombre, valor_total DESC";
            cols = new String[]{"Pais", "Equipo", "Valor Total"};
        } else if (sel == rdoConsulta4) {
            int edad = 21;
            try { if (textParametroBusqueda != null && !textParametroBusqueda.getText().trim().isEmpty()) edad = Integer.parseInt(textParametroBusqueda.getText().trim()); } catch (Exception ex) {}
            sql = "SELECT e.nombre as equipo, COUNT(j.id_jugador) as cantidad FROM Jugador j JOIN Equipo e ON j.id_equipo = e.id_equipo WHERE DATEDIFF(YEAR, j.fecha_nacimiento, GETDATE()) < " + edad + " GROUP BY e.nombre ORDER BY cantidad DESC";
            cols = new String[]{"Equipo", "Cantidad < " + edad + " anos"};
        }
        ejecutarConsultaGenericaAdmin(sql, cols);
    }

    @SuppressWarnings("unchecked")
    private void ejecutarConsultaGenericaAdmin(String sql, String[] columnas) {
        try (java.sql.Connection conn = application.Conexion.getConexion();
             java.sql.PreparedStatement ps = conn.prepareStatement(sql);
             java.sql.ResultSet rs = ps.executeQuery()) {
            javafx.scene.control.TableView<javafx.collections.ObservableList<String>> tabla = (javafx.scene.control.TableView<javafx.collections.ObservableList<String>>) tabConsultas;
            tabla.getColumns().clear();
            for (int i = 0; i < columnas.length; i++) {
                final int idx = i;
                javafx.scene.control.TableColumn<javafx.collections.ObservableList<String>, String> col = new javafx.scene.control.TableColumn<>(columnas[i]);
                col.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().get(idx)));
                tabla.getColumns().add(col);
            }
            javafx.collections.ObservableList<javafx.collections.ObservableList<String>> data = javafx.collections.FXCollections.observableArrayList();
            int numCols = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                javafx.collections.ObservableList<String> fila = javafx.collections.FXCollections.observableArrayList();
                for (int i = 1; i <= numCols; i++) { String v = rs.getString(i); fila.add(v != null ? v : ""); }
                data.add(fila);
            }
            tabla.setItems(data);
        } catch (Exception e) { mostrarAlerta("Error", e.getMessage()); }
    }

    private void inicializarCamposReportesAdmin() {
        if (dateReporte != null) dateReporte.setVisible(false);
        if (textPesoReporte != null) textPesoReporte.setVisible(false);
        if (textEstaturaReporte != null) textEstaturaReporte.setVisible(false);
        if (comboEquipoReporte != null) comboEquipoReporte.setVisible(false);
        if (comboConfederacionReporte != null) comboConfederacionReporte.setVisible(false);
    }

    @FXML
    void onReporteSeleccionadoAdmin(javafx.event.ActionEvent event) {
        inicializarCamposReportesAdmin();
        RadioButton sel = (RadioButton) tgReportes.getSelectedToggle();
        if (sel == rdoReporte1) { if (dateReporte != null) dateReporte.setVisible(true); }
        else if (sel == rdoReporte2) {
            if (textPesoReporte != null) textPesoReporte.setVisible(true);
            if (textEstaturaReporte != null) textEstaturaReporte.setVisible(true);
            if (comboEquipoReporte != null) comboEquipoReporte.setVisible(true);
        } else if (sel == rdoReporte3) {
            if (comboConfederacionReporte != null) comboConfederacionReporte.setVisible(true);
            if (comboEquipoReporte != null) comboEquipoReporte.setVisible(true);
        }
    }

    private void cargarCombosReportesAdmin() {
        try (java.sql.Connection conn = application.Conexion.getConexion()) {
            javafx.collections.ObservableList<String> equipos = javafx.collections.FXCollections.observableArrayList();
            try (java.sql.PreparedStatement ps = conn.prepareStatement("SELECT nombre FROM Equipo ORDER BY nombre"); java.sql.ResultSet rs = ps.executeQuery()) { while (rs.next()) equipos.add(rs.getString("nombre")); }
            if (comboEquipoReporte != null) comboEquipoReporte.setItems(equipos);
            javafx.collections.ObservableList<String> confs = javafx.collections.FXCollections.observableArrayList();
            try (java.sql.PreparedStatement ps = conn.prepareStatement("SELECT nombre FROM Confederacion ORDER BY nombre"); java.sql.ResultSet rs = ps.executeQuery()) { while (rs.next()) confs.add(rs.getString("nombre")); }
            if (comboConfederacionReporte != null) comboConfederacionReporte.setItems(confs);
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    void generarReporte(javafx.event.ActionEvent event) {
        if (tgReportes == null || tgReportes.getSelectedToggle() == null) { mostrarAlerta("Error", "Seleccione un reporte."); return; }
        FileChooser fc = new FileChooser();
        fc.setTitle("Guardar Reporte PDF");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
        RadioButton sel = (RadioButton) tgReportes.getSelectedToggle();
        String nombre = sel == rdoReporte1 ? "reporte_bitacora.pdf" : sel == rdoReporte2 ? "reporte_jugadores.pdf" : sel == rdoReporte3 ? "reporte_valor_equipos.pdf" : "reporte_paises_anfitrion.pdf";
        fc.setInitialFileName(nombre);
        File archivo = fc.showSaveDialog(btnReportes.getScene().getWindow());
        if (archivo == null) return;
        try {
            if (sel == rdoReporte1) generarR1Admin(archivo);
            else if (sel == rdoReporte2) generarR2Admin(archivo);
            else if (sel == rdoReporte3) generarR3Admin(archivo);
            else generarR4Admin(archivo);
        } catch (Exception e) { mostrarAlerta("Error", "Error PDF: " + e.getMessage()); }
    }

    private void generarR1Admin(File f) throws Exception {
        if (dateReporte == null || dateReporte.getValue() == null) { mostrarAlerta("Error", "Seleccione fecha."); return; }
        Document doc = new Document(); PdfWriter.getInstance(doc, new FileOutputStream(f)); doc.open();
        doc.add(new Paragraph("REPORTE BITACORA - " + dateReporte.getValue(), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
        doc.add(new Paragraph(" "));
        PdfPTable t = new PdfPTable(4); t.setWidthPercentage(100);
        for (String h : new String[]{"ID","Usuario","Entrada","Salida"}) { PdfPCell c = new PdfPCell(new Phrase(h, FontFactory.getFont(FontFactory.HELVETICA_BOLD,10))); c.setBackgroundColor(BaseColor.LIGHT_GRAY); t.addCell(c); }
        try (java.sql.Connection conn = application.Conexion.getConexion(); java.sql.PreparedStatement ps = conn.prepareStatement("SELECT b.id_bitacora, u.username, b.fecha_entrada, b.fecha_salida FROM Bitacora b JOIN Usuario u ON b.id_usuario = u.id_usuario WHERE CAST(b.fecha_entrada AS DATE) = ?")) {
            ps.setDate(1, java.sql.Date.valueOf(dateReporte.getValue()));
            try (java.sql.ResultSet rs = ps.executeQuery()) { while (rs.next()) { t.addCell(String.valueOf(rs.getInt(1))); t.addCell(rs.getString(2)); t.addCell(rs.getString(3)); String s = rs.getString(4); t.addCell(s!=null?s:"En sesion"); } }
        }
        doc.add(t); doc.close(); mostrarAlerta2("Exito", "PDF guardado: " + f.getPath());
    }

    private void generarR2Admin(File f) throws Exception {
        Document doc = new Document(); PdfWriter.getInstance(doc, new FileOutputStream(f)); doc.open();
        doc.add(new Paragraph("REPORTE JUGADORES", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14))); doc.add(new Paragraph(" "));
        PdfPTable t = new PdfPTable(6); t.setWidthPercentage(100);
        for (String h : new String[]{"Nombre","Apellido","Peso","Estatura","Valor","Equipo"}) { PdfPCell c = new PdfPCell(new Phrase(h, FontFactory.getFont(FontFactory.HELVETICA_BOLD,10))); c.setBackgroundColor(BaseColor.LIGHT_GRAY); t.addCell(c); }
        String sql = "SELECT j.nombre, j.apellido, j.peso, j.estatura, j.valor, e.nombre FROM Jugador j JOIN Equipo e ON j.id_equipo = e.id_equipo WHERE 1=1";
        if (textPesoReporte != null && !textPesoReporte.getText().trim().isEmpty()) sql += " AND j.peso <= " + textPesoReporte.getText().trim();
        if (textEstaturaReporte != null && !textEstaturaReporte.getText().trim().isEmpty()) sql += " AND j.estatura <= " + textEstaturaReporte.getText().trim();
        if (comboEquipoReporte != null && comboEquipoReporte.getValue() != null) sql += " AND e.nombre = '" + comboEquipoReporte.getValue() + "'";
        try (java.sql.Connection conn = application.Conexion.getConexion(); java.sql.PreparedStatement ps = conn.prepareStatement(sql); java.sql.ResultSet rs = ps.executeQuery()) {
            while (rs.next()) { for (int i=1;i<=6;i++) t.addCell(rs.getString(i)); }
        }
        doc.add(t); doc.close(); mostrarAlerta2("Exito", "PDF guardado.");
    }

    private void generarR3Admin(File f) throws Exception {
        Document doc = new Document(); PdfWriter.getInstance(doc, new FileOutputStream(f)); doc.open();
        doc.add(new Paragraph("VALOR TOTAL POR EQUIPO", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14))); doc.add(new Paragraph(" "));
        PdfPTable t = new PdfPTable(3); t.setWidthPercentage(100);
        for (String h : new String[]{"Confederacion","Equipo","Valor Total"}) { PdfPCell c = new PdfPCell(new Phrase(h, FontFactory.getFont(FontFactory.HELVETICA_BOLD,10))); c.setBackgroundColor(BaseColor.LIGHT_GRAY); t.addCell(c); }
        String w = ""; 
        if (comboConfederacionReporte != null && comboConfederacionReporte.getValue() != null) w += "WHERE c.nombre = '" + comboConfederacionReporte.getValue() + "' ";
        if (comboEquipoReporte != null && comboEquipoReporte.getValue() != null) w += (w.isEmpty()?"WHERE ":"AND ") + "e.nombre = '" + comboEquipoReporte.getValue() + "' ";
        String sql = "SELECT c.nombre, e.nombre, SUM(j.valor) FROM Jugador j JOIN Equipo e ON j.id_equipo = e.id_equipo JOIN Confederacion c ON e.id_confederacion = c.id_confederacion " + w + "GROUP BY c.nombre, e.nombre ORDER BY c.nombre";
        try (java.sql.Connection conn = application.Conexion.getConexion(); java.sql.PreparedStatement ps = conn.prepareStatement(sql); java.sql.ResultSet rs = ps.executeQuery()) {
            while (rs.next()) { for (int i=1;i<=3;i++) t.addCell(rs.getString(i)!=null?rs.getString(i):"0"); }
        }
        doc.add(t); doc.close(); mostrarAlerta2("Exito", "PDF guardado.");
    }

    private void generarR4Admin(File f) throws Exception {
        Document doc = new Document(); PdfWriter.getInstance(doc, new FileOutputStream(f)); doc.open();
        doc.add(new Paragraph("PAISES POR SEDE ANFITRIONA", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14))); doc.add(new Paragraph(" "));
        PdfPTable t = new PdfPTable(3); t.setWidthPercentage(100);
        for (String h : new String[]{"Pais Anfitrion","Local","Visitante"}) { PdfPCell c = new PdfPCell(new Phrase(h, FontFactory.getFont(FontFactory.HELVETICA_BOLD,10))); c.setBackgroundColor(BaseColor.LIGHT_GRAY); t.addCell(c); }
        String sql = "SELECT p.nombre, el.nombre, ev.nombre FROM Partido pt JOIN Estadio est ON pt.id_estadio = est.id_estadio JOIN Ciudad c ON est.id_ciudad = c.id_ciudad JOIN Pais p ON c.id_pais = p.id_pais JOIN Equipo el ON pt.id_equipo_local = el.id_equipo JOIN Equipo ev ON pt.id_equipo_visitante = ev.id_equipo WHERE p.nombre IN ('Mexico', 'Estados Unidos', 'Canada') ORDER BY p.nombre";
        try (java.sql.Connection conn = application.Conexion.getConexion(); java.sql.PreparedStatement ps = conn.prepareStatement(sql); java.sql.ResultSet rs = ps.executeQuery()) {
            while (rs.next()) { for (int i=1;i<=3;i++) t.addCell(rs.getString(i)!=null?rs.getString(i):""); }
        }
        doc.add(t); doc.close(); mostrarAlerta2("Exito", "PDF guardado.");
    }

    private void mostrarAlerta2(String titulo, String msg) {
        javafx.scene.control.Alert a = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        a.setTitle(titulo); a.setContentText(msg); a.showAndWait();
    }

}