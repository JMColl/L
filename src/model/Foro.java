package model;

public class Foro {
	private int idForo;
	private String comentario;
	private String fecha;
	private String alumno;
	private int idPadre;

	public int getIdForo() {
		return idForo;
	}

	public void setIdForo(int idForo) {
		this.idForo = idForo;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getAlumno() {
		return alumno;
	}

	public void setAlumno(String alumno) {
		this.alumno = alumno;
	}

	public int getIdPadre() {
		return idPadre;
	}

	public void setIdPadre(int idPadre) {
		this.idPadre = idPadre;
	}

}
