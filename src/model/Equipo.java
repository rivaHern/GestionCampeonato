package model;

public class Equipo {
    private int id;
    private String nombre;
    private String confederacion;
    private String directorTecnico;

    public Equipo() {}

    public Equipo(int id, String nombre, String confederacion, String directorTecnico) {
        this.id = id;
        this.nombre = nombre;
        this.confederacion = confederacion;
        this.directorTecnico = directorTecnico;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getConfederacion() { return confederacion; }
    public void setConfederacion(String confederacion) { this.confederacion = confederacion; }
    public String getDirectorTecnico() { return directorTecnico; }
    public void setDirectorTecnico(String directorTecnico) { this.directorTecnico = directorTecnico; }
}
