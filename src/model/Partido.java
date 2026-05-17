package model;

import java.time.LocalDateTime;

public class Partido {
    private int id;
    private LocalDateTime fecha;
    private int idEquipoLocal;
    private int idEquipoVisitante;
    private int idEstadio;
    private int idGrupo;
    private String equipoLocal;
    private String equipoVisitante;
    private String estadio;
    private String grupo;

    public Partido() {}

    public Partido(int id, LocalDateTime fecha, int idEquipoLocal, int idEquipoVisitante,
                   int idEstadio, int idGrupo, String equipoLocal, String equipoVisitante,
                   String estadio, String grupo) {
        this.id = id; this.fecha = fecha;
        this.idEquipoLocal = idEquipoLocal; this.idEquipoVisitante = idEquipoVisitante;
        this.idEstadio = idEstadio; this.idGrupo = idGrupo;
        this.equipoLocal = equipoLocal; this.equipoVisitante = equipoVisitante;
        this.estadio = estadio; this.grupo = grupo;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public int getIdEquipoLocal() { return idEquipoLocal; }
    public void setIdEquipoLocal(int idEquipoLocal) { this.idEquipoLocal = idEquipoLocal; }
    public int getIdEquipoVisitante() { return idEquipoVisitante; }
    public void setIdEquipoVisitante(int idEquipoVisitante) { this.idEquipoVisitante = idEquipoVisitante; }
    public int getIdEstadio() { return idEstadio; }
    public void setIdEstadio(int idEstadio) { this.idEstadio = idEstadio; }
    public int getIdGrupo() { return idGrupo; }
    public void setIdGrupo(int idGrupo) { this.idGrupo = idGrupo; }
    public String getEquipoLocal() { return equipoLocal; }
    public void setEquipoLocal(String equipoLocal) { this.equipoLocal = equipoLocal; }
    public String getEquipoVisitante() { return equipoVisitante; }
    public void setEquipoVisitante(String equipoVisitante) { this.equipoVisitante = equipoVisitante; }
    public String getEstadio() { return estadio; }
    public void setEstadio(String estadio) { this.estadio = estadio; }
    public String getGrupo() { return grupo; }
    public void setGrupo(String grupo) { this.grupo = grupo; }
}
