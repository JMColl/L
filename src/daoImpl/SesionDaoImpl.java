package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.Isesion;
import model.Sesion;
import util.Conexion;

public class SesionDaoImpl implements Isesion {

	private Conexion con = new Conexion();
	private Connection connection;

	@Override
	public List<Sesion> listaSesion(int idCurso) {
		List<Sesion> lista = new ArrayList<>();
		try {
			connection = con.conectar();
			String query = "select id_sesion,nombre from sesiones where id_curso = ?";
			PreparedStatement sp = connection.prepareStatement(query);
			sp.setInt(1, idCurso);
			sp.executeQuery();

			ResultSet resultado = sp.getResultSet();
			while (resultado.next()) {
				Sesion c = new Sesion();
				c.setIdSesion(resultado.getInt(1));
				c.setNombre(resultado.getString(2));
				lista.add(c);
			}

			sp.close();
		} catch (SQLException e) {
			System.out.println("Error: lista sesion");
			e.printStackTrace();
		}

		return lista;
	}

	@Override
	public int agregarSesion(Sesion sesion) {
		int rpta = 0;
		try {
			connection = con.conectar();
			String query = "insert into sesiones (nombre,id_curso,id_usuario_reg) values (?,?,?);";
			PreparedStatement sp = connection.prepareStatement(query);
			sp.setString(1, sesion.getNombre());
			sp.setInt(2, sesion.getIdCurso());
			sp.setInt(3, sesion.getIdUsuarioReg());

			rpta = sp.executeUpdate();

			sp.close();
		} catch (SQLException e) {
			System.out.println("Error: lista sesion");
			e.printStackTrace();
		}

		return rpta;
	}

}
