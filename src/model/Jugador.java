package model;

import java.time.LocalDate;

public class Jugador {
    private int id;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private double peso;
    private double estatura;
    private String posicion;
    private double valor;
    private int idEquipo;
    private String nombreEquipo;
    private int edad;

    public Jugador() {}

    public Jugador(int id, String nombre, String apellido, LocalDate fechaNacimiento,
                   double peso, double estatura, String posicion, double valor,
                   int idEquipo, String nombreEquipo) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.peso = peso;
        this.estatura = estatura;
        this.posicion = posicion;
        this.valor = valor;
        this.idEquipo = idEquipo;
        this.nombreEquipo = nombreEquipo;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }
    public double getEstatura() { return estatura; }
    public void setEstatura(double estatura) { this.estatura = estatura; }
    public String getPosicion() { return posicion; }
    public void setPosicion(String posicion) { this.posicion = posicion; }
    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }
    public int getIdEquipo() { return idEquipo; }
    public void setIdEquipo(int idEquipo) { this.idEquipo = idEquipo; }
    public String getNombreEquipo() { return nombreEquipo; }
    public void setNombreEquipo(String nombreEquipo) { this.nombreEquipo = nombreEquipo; }
    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }
}
