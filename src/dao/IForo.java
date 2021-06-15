package dao;

import java.util.List;

import model.Foro;

public interface IForo {
	List<Foro> listaComentario(int IdMaterial);

	int comentarAlumno(String comentario, int idMaterial, int idUsuario);

	int comentarProfesor(String comentario, int idMaterial, int idUsuario, int idPadre);

	Foro respuesta(int idForo);
}
