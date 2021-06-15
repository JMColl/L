package dao;

import java.util.List;

import model.Sesion;

public interface Isesion {
	List<Sesion> listaSesion(int idCurso);
	int agregarSesion(Sesion sesion);
}
