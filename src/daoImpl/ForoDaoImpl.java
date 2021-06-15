package daoImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import dao.IForo;
import model.Foro;
import util.Conexion;

public class ForoDaoImpl implements IForo {

	private Conexion con = new Conexion();
	private Connection connection;

	@Override
	public List<Foro> listaComentario(int IdMaterial) {
		List<Foro> lista = new ArrayList<>();
		try {
			connection = con.conectar();
			String query = "select f.id_foro,f.comentario,f.fecha,concat(p.nombres,' ',p.apellidos) as alumno from foros f inner join usuario u on f.id_usuario = u.id_usuario inner join persona p on u.id_persona = p.id_persona where f.id_material = ? and f.id_padre = 0";
			PreparedStatement sp = connection.prepareStatement(query);
			sp.setInt(1, IdMaterial);
			sp.executeQuery();

			ResultSet resultado = sp.getResultSet();
			while (resultado.next()) {
				Foro f = new Foro();
				f.setIdForo(resultado.getInt(1));
				f.setComentario(resultado.getString(2));
				f.setFecha(resultado.getString(3));
				f.setAlumno(resultado.getString(4));
				lista.add(f);
			}

			sp.close();
		} catch (SQLException e) {
			System.out.println("Error: lista sesion");
			e.printStackTrace();
		}

		return lista;
	}

	@Override
	public int comentarAlumno(String comentario, int idMaterial, int idUsuario) {
		int respuesta = 0;

		try {
			connection = con.conectar();
			CallableStatement sp = connection.prepareCall("CALL comentarioAlumno(?,?,?,?)");
			sp.setInt("idUsuario", idUsuario);
			sp.setInt("idMaterial", idMaterial);
			sp.setString("comentario", comentario);

			sp.registerOutParameter("respuesta", Types.INTEGER);
			sp.execute();
			respuesta = sp.getInt("respuesta");
			sp.close();
			con.cerrar();
		} catch (SQLException e) {
			System.out.println("Error: comentario alumno");
			e.printStackTrace();
		}

		return respuesta;
	}

	@Override
	public int comentarProfesor(String comentario, int idMaterial, int idUsuario, int idPadre) {
		int respuesta = 0;

		try {
			connection = con.conectar();
			CallableStatement sp = connection.prepareCall("CALL comentarioProfesor(?,?,?,?,?)");
			sp.setInt("idUsuario", idUsuario);
			sp.setInt("idMaterial", idMaterial);
			sp.setInt("idPadre", idPadre);
			sp.setString("comentario", comentario);

			sp.registerOutParameter("respuesta", Types.INTEGER);
			sp.execute();
			respuesta = sp.getInt("respuesta");
			sp.close();
			con.cerrar();
		} catch (SQLException e) {
			System.out.println("Error: comentario alumno");
			e.printStackTrace();
		}

		return respuesta;
	}

	@Override
	public Foro respuesta(int idForo) {
		Foro f = new Foro();
		try {
			connection = con.conectar();
			String query = "select id_foro,comentario,fecha from foros where id_padre = ?";
			PreparedStatement sp = connection.prepareStatement(query);
			sp.setInt(1, idForo);
			sp.executeQuery();

			ResultSet resultado = sp.getResultSet();
			while (resultado.next()) {
				f.setIdForo(resultado.getInt(1));
				f.setComentario(resultado.getString(2));
				f.setFecha(resultado.getString(3));
			}

			sp.close();
		} catch (SQLException e) {
			System.out.println("Error: lista sesion");
			e.printStackTrace();
		}

		return f;
	}

}
