package daoImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import dao.ICurso;
import dao.ITarea;
import model.Curso;
import model.Material;
import model.Tarea;
import util.Conexion;

public class TareaDaoImpl implements ITarea {

	private Conexion con = new Conexion();
	private Connection connection;

	@Override
	public int entregarTarea(int idTarea, int idUsuario, String path,String archivo) {
		int respuesta = 0;

		try {
			connection = con.conectar();
			CallableStatement sp = connection.prepareCall("CALL engregarTarea(?,?,?,?,?)");
			sp.setInt("idTarea", idTarea);
			sp.setInt("idUsuario", idUsuario);
			sp.setString("archivo", archivo);
			sp.setString("path", path);

			sp.registerOutParameter("respuesta", Types.INTEGER);
			sp.execute();
			respuesta = sp.getInt("respuesta");
			sp.close();
			con.cerrar();
		} catch (SQLException e) {
			System.out.println("Error: Clase OrdenServicioDao, método registrar");
			e.printStackTrace();
		}

		return respuesta;
	}

	@Override
	public Tarea verTarea(int idTarea, int idUsuario) {
		Tarea m = new Tarea();
		try {
			connection = con.conectar();
			String query = "select rt.id_respuesta,rt.archivo,rt.fecha,rt.nota,concat(p.nombres,' ',p.apellidos) as alumno,rt.respuesta_tarea from respuestas_tarea rt inner join alumno a on rt.id_alumno = a.id_alumno inner join usuario u on a.id_usuario = u.id_usuario inner join persona p on u.id_persona = p.id_persona where rt.id_tarea = ? and u.id_usuario = ?";
			PreparedStatement sp = connection.prepareStatement(query);
			sp.setInt(1, idTarea);
			sp.setInt(2, idUsuario);
			sp.executeQuery();

			ResultSet resultado = sp.getResultSet();
			while (resultado.next()) {
				m.setId_respuesta(resultado.getInt(1));
				m.setArchivo(resultado.getString(2));
				m.setFecha(resultado.getString(3));
				m.setNota(resultado.getDouble(4));
				m.setAlumno(resultado.getString(5));
				m.setRespuesta_tarea(resultado.getString(6));
			}

			sp.close();
		} catch (SQLException e) {
			System.out.println("Error: lista sesion");
			e.printStackTrace();
		}

		return m;
	}

	@Override
	public List<Tarea> listaTarea(int idTarea) {
		List<Tarea> lista = new ArrayList<>();
		try {
			connection = con.conectar();
			String query = "select rt.id_respuesta,rt.archivo,rt.fecha,rt.nota,concat(p.nombres,' ',p.apellidos) as alumno,rt.respuesta_tarea from respuestas_tarea rt inner join alumno a on rt.id_alumno = a.id_alumno inner join usuario u on a.id_usuario = u.id_usuario inner join persona p on u.id_persona = p.id_persona where rt.id_tarea = ?";
			PreparedStatement sp = connection.prepareStatement(query);
			sp.setInt(1, idTarea);
			sp.executeQuery();

			ResultSet resultado = sp.getResultSet();
			while (resultado.next()) {
				Tarea m = new Tarea();
				m.setId_respuesta(resultado.getInt(1));
				m.setArchivo(resultado.getString(2));
				m.setFecha(resultado.getString(3));
				m.setNota(resultado.getDouble(4));
				m.setAlumno(resultado.getString(5));
				m.setRespuesta_tarea(resultado.getString(6));
				lista.add(m);
			}

			sp.close();
		} catch (SQLException e) {
			System.out.println("Error: lista sesion");
			e.printStackTrace();
		}

		return lista;
	}

	@Override
	public int calificarTarea(int idRespuesta, Double nota) {
		int rpta = 0;
		try {
			connection = con.conectar();
			String query = "update respuestas_tarea set nota = ? where id_respuesta = ?";
			PreparedStatement sp = connection.prepareStatement(query);
			sp.setDouble(1, nota);
			sp.setInt(2, idRespuesta);

			rpta = sp.executeUpdate();

			sp.close();
		} catch (SQLException e) {
			System.out.println("Error: lista sesion");
			e.printStackTrace();
		}

		return rpta;
	}

}
