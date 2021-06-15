package dao;

import model.Material;

public interface IMaterial {
	int guardar(Material material);

	int validarExiste(int idSesion);

	Material verMaterial(int idSesion);

	int actualizar(Material material);

	int eliminar(int idMaterial);
}
