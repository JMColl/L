package dao;

import java.util.List;

import model.Curso;
import model.Reporte;

public interface IReporte {
	List<Reporte> cantidad();

	List<Reporte> alumnoGrado();

	List<Reporte> promedio();

	List<Reporte> puestos();
}
