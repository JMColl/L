package dao;

import java.util.List;

import model.Curso;

public interface ICurso {
	List<Curso> listaCurso(int idProfesor, int idRol);

	List<Curso> listaCursos();

	Curso datos(int idCurso);

	int agregar(Curso Curso);

	int actualizar(Curso Curso);

	int eliminar(int idCurso);
}
