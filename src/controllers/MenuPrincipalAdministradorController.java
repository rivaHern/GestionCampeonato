package controllers;

import application.Conexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    @FXML private DatePicker DateAdmBitacora;
    @FXML private TableView<Bitacora> tabAdmBitacora;
    @FXML private TableColumn<Bitacora, Integer> colIDAdmBitacora;
    @FXML private TableColumn<Bitacora, String> colUsuarioAdmBitacora;
    @FXML private TableColumn<Bitacora, String> colFechaIngresoAdmBitacora;
    @FXML private TableColumn<Bitacora, String> colFechaSalidaAdmBitacora;

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
    AnchorPane[] todos = {inicioPane, admUsuariosPane, admBitacoraPane, admEquiposPane, admJugadoresPane};
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
    @FXML void AdmEquiposAgregar(ActionEvent event) { System.out.println("pendiente - AdmEquiposAgregar"); }
    @FXML void AdmEquiposActualizar(ActionEvent event) { System.out.println("pendiente - AdmEquiposActualizar"); }
    @FXML void AdmEquiposBuscar(ActionEvent event) { System.out.println("pendiente - AdmEquiposBuscar"); }
    @FXML void AdmEquiposEliminar(ActionEvent event) { System.out.println("pendiente - AdmEquiposEliminar"); }

    // ========== CERRAR SESIÓN ==========
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
}