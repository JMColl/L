package model;

public class Sesion {
	private int idSesion;
	private String nombre;
	private int idCurso;
	private int idUsuarioReg;

	public int getIdSesion() {
		return idSesion;
	}

	public void setIdSesion(int idSesion) {
		this.idSesion = idSesion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(int idCurso) {
		this.idCurso = idCurso;
	}

	public int getIdUsuarioReg() {
		return idUsuarioReg;
	}

	public void setIdUsuarioReg(int idUsuarioReg) {
		this.idUsuarioReg = idUsuarioReg;
	}

}
