package model;

import java.time.LocalDateTime;

public class Bitacora {

	private int id;
	private String usuario;
	private LocalDateTime fechaEntrada, fechaSalida;

	public Bitacora(int id, String usuario, LocalDateTime fechaEntrada, LocalDateTime fechaSalida) {
		this.id = id;
		this.usuario = usuario;
		this.fechaEntrada = fechaEntrada;
		this.fechaSalida = fechaSalida;
	}

	public void setFechaEntrada(LocalDateTime fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}

	public void setFechaSalida(LocalDateTime fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public int getId() {
		return id;
	}

	public String getUsuario() {
		return usuario;
	}

	public LocalDateTime getFechaEntrada() {
		return fechaEntrada;
	}

	public LocalDateTime getFechaSalida() {
		return fechaSalida;
	}
}