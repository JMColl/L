package dao;

import java.util.List;

import model.Alumno;
import model.Curso;
import model.Profesor;

public interface IProfesor {
	List<Profesor> listaProfesor();

	Profesor datos(int idProfesor);

	int agregar(Profesor profesor);

	int actualizar(Profesor profesor);

	int eliminar(int idAlumno);
}
