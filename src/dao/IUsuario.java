package dao;

import model.Usuario;

public interface IUsuario {
	Usuario login(String usuario, String password);
}
