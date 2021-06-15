package model;

public class Material {
	private int idMaterial;
	private int idSession;
	private String titulo;
	private String descripcion;
	private String link;
	private int id_tarea;
	private String titulo_tarea;
	private String descripcion_tarea;
	private String fecha_tarea;

	public int getIdMaterial() {
		return idMaterial;
	}

	public void setIdMaterial(int idMaterial) {
		this.idMaterial = idMaterial;
	}

	public int getIdSession() {
		return idSession;
	}

	public void setIdSession(int idSession) {
		this.idSession = idSession;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getTitulo_tarea() {
		return titulo_tarea;
	}

	public void setTitulo_tarea(String titulo_tarea) {
		this.titulo_tarea = titulo_tarea;
	}

	public String getDescripcion_tarea() {
		return descripcion_tarea;
	}

	public void setDescripcion_tarea(String descripcion_tarea) {
		this.descripcion_tarea = descripcion_tarea;
	}

	public String getFecha_tarea() {
		return fecha_tarea;
	}

	public void setFecha_tarea(String fecha_tarea) {
		this.fecha_tarea = fecha_tarea;
	}

	public int getId_tarea() {
		return id_tarea;
	}

	public void setId_tarea(int id_tarea) {
		this.id_tarea = id_tarea;
	}

}
