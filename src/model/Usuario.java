package model;
import emun.Rol;
public class Usuario {
    private int id;
    private String username;
    private Rol rol;
    public Usuario(int id, String username, Rol rol) {
        this.id = id; this.username = username; this.rol = rol;
    }
    public Usuario() {}
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }
}
