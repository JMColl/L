package dao;

import java.util.List;

import model.Curso;
import model.Tarea;

public interface ITarea {
	int entregarTarea(int idTarea, int idUsuario, String path, String archivo);

	Tarea verTarea(int idTarea, int idUsuario);

	List<Tarea> listaTarea(int idTarea);

	int calificarTarea(int idRespuesta, Double nota);
}
