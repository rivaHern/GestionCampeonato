package model;
import java.time.LocalDateTime;
public class Bitacora {
    private int id;
    private String usuario;
    private LocalDateTime fechaEntrada, fechaSalida;
    public Bitacora(int id, String usuario, LocalDateTime fechaEntrada, LocalDateTime fechaSalida) {
        this.id = id; this.usuario = usuario;
        this.fechaEntrada = fechaEntrada; this.fechaSalida = fechaSalida;
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public LocalDateTime getFechaEntrada() { return fechaEntrada; }
    public void setFechaEntrada(LocalDateTime fechaEntrada) { this.fechaEntrada = fechaEntrada; }
    public LocalDateTime getFechaSalida() { return fechaSalida; }
    public void setFechaSalida(LocalDateTime fechaSalida) { this.fechaSalida = fechaSalida; }
}
