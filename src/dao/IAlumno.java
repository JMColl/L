package dao;

import java.util.List;

import model.Alumno;
import model.Curso;

public interface IAlumno {
	List<Alumno> listaAlumno();

	Alumno datos(int idAlumno);

	int agregar(Alumno alumno);

	int actualizar(Alumno alumno);

	int eliminar(int idAlumno);
}
