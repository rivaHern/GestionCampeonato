package model;

import emun.Rol;

public class Usuario {
	
    private int id;
    private String username;
    private Rol rol;

    public Usuario(int id, String username, Rol rol) {
        this.id = id;
        this.username = username;
        this.rol = rol;
    }

    public Usuario() {
    }

    public String getUsernameActual() {
        return username;
    }

    public void setUsernameActual(String username) {
        this.username = username;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
    
    
}
