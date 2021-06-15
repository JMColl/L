package model;

public class Tarea {
	private int id_respuesta;
	private int id_tarea;
	private String alumno;
	private String respuesta_tarea;
	private String archivo;
	private String fecha;
	private Double nota;

	public int getId_respuesta() {
		return id_respuesta;
	}

	public void setId_respuesta(int id_respuesta) {
		this.id_respuesta = id_respuesta;
	}

	public int getId_tarea() {
		return id_tarea;
	}

	public void setId_tarea(int id_tarea) {
		this.id_tarea = id_tarea;
	}

	public String getAlumno() {
		return alumno;
	}

	public void setAlumno(String alumno) {
		this.alumno = alumno;
	}

	public String getRespuesta_tarea() {
		return respuesta_tarea;
	}

	public void setRespuesta_tarea(String respuesta_tarea) {
		this.respuesta_tarea = respuesta_tarea;
	}

	public String getArchivo() {
		return archivo;
	}

	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public Double getNota() {
		return nota;
	}

	public void setNota(Double nota) {
		this.nota = nota;
	}

}
