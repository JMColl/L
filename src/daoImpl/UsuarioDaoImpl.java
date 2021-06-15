package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.IUsuario;
import model.Usuario;
import util.Conexion;

public class UsuarioDaoImpl implements IUsuario {

	private Conexion con = new Conexion();
	private Connection connection;

	@Override
	public Usuario login(String usuario, String password) {
		Usuario u = new Usuario();
		try {
			connection = con.conectar();
			String query = "select u.id_usuario,p.nombres,u.id_rol from usuario u inner join persona p on u.id_persona = p.id_persona where u.usuario = ? and u.password = ?";
			PreparedStatement sp = connection.prepareStatement(query);
			sp.setString(1, usuario);
			sp.setString(2, password);
			sp.executeQuery();

			ResultSet resultado = sp.getResultSet();
			while (resultado.next()) {
				u.setIdUsuario(resultado.getInt(1));
				u.setDatos(resultado.getString(2));
				u.setIdRol(resultado.getInt(3));
			}

			sp.close();
		} catch (SQLException e) {
			System.out.println("Error: validando usuario");
			e.printStackTrace();
		}

		return u;
	}

}
