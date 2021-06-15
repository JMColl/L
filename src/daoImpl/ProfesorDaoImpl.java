package daoImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import dao.IProfesor;
import model.Profesor;
import util.Conexion;

public class ProfesorDaoImpl implements IProfesor {

	private Conexion con = new Conexion();
	private Connection connection;

	@Override
	public List<Profesor> listaProfesor() {
		List<Profesor> lista = new ArrayList<>();
		try {
			connection = con.conectar();
			String query = "select a.id_profesor,p.dni,p.apellidos,p.nombres,p.correo,p.año,g.nombre as grado from profesor a inner join usuario u on a.id_usuario = u.id_usuario inner join persona p on u.id_persona = p.id_persona inner join grado g on a.id_grado = g.id_grado where a.estado = 1";
			PreparedStatement sp = connection.prepareStatement(query);
			sp.executeQuery();

			ResultSet resultado = sp.getResultSet();
			while (resultado.next()) {
				Profesor c = new Profesor();
				c.setIdProfesor(resultado.getInt(1));
				c.setDni(resultado.getString(2));
				c.setApellidos(resultado.getString(3));
				c.setNombres(resultado.getString(4));
				c.setCorreo(resultado.getString(5));
				c.setAño(resultado.getString(6));
				c.setGrado(resultado.getString(7));
				lista.add(c);
			}

			sp.close();
		} catch (SQLException e) {
			System.out.println("Error: lista curso profesor");
			e.printStackTrace();
		}

		return lista;
	}

	@Override
	public int agregar(Profesor Profesor) {
		int respuesta = 0;

		try {
			connection = con.conectar();
			CallableStatement sp = connection.prepareCall("CALL agregarProfesor(?,?,?,?,?,?,?)");
			sp.setString("dni", Profesor.getDni());
			sp.setString("apellidos", Profesor.getApellidos());
			sp.setString("nombres", Profesor.getNombres());
			sp.setString("correo", Profesor.getCorreo());
                        sp.setString("año", Profesor.getAño());
			sp.setInt("idGrado", Profesor.getIdGrado());

			sp.registerOutParameter("respuesta", Types.INTEGER);
			sp.execute();
			respuesta = sp.getInt("respuesta");
			sp.close();
			con.cerrar();
		} catch (SQLException e) {
			System.out.println("Error: comentario Profesor");
			e.printStackTrace();
		}

		return respuesta;
	}

	@Override
	public int actualizar(Profesor Profesor) {
		int respuesta = 0;

		try {
			connection = con.conectar();
			CallableStatement sp = connection.prepareCall("CALL actualizarProfesor(?,?,?,?,?,?,?,?)");
			sp.setInt("idProfesor", Profesor.getIdProfesor());
			sp.setInt("idPersona", Profesor.getIdPersona());
			sp.setString("apellidos", Profesor.getApellidos());
			sp.setString("nombres", Profesor.getNombres());
			sp.setString("correo", Profesor.getCorreo());
                        sp.setString("año", Profesor.getAño());
			sp.setInt("idGrado", Profesor.getIdGrado());

			sp.registerOutParameter("respuesta", Types.INTEGER);
			sp.execute();
			respuesta = sp.getInt("respuesta");
			sp.close();
			con.cerrar();
		} catch (SQLException e) {
			System.out.println("Error: comentario Profesor");
			e.printStackTrace();
		}

		return respuesta;
	}

	@Override
	public int eliminar(int idProfesor) {
		int rpta = 0;
		try {
			connection = con.conectar();
			String query = "update profesor set estado = 0 where id_profesor = ?";
			PreparedStatement sp = connection.prepareStatement(query);
			sp.setInt(1, idProfesor);

			rpta = sp.executeUpdate();

			sp.close();
		} catch (SQLException e) {
			System.out.println("Error: lista sesion");
			e.printStackTrace();
		}

		return rpta;
	}

	@Override
	public Profesor datos(int idProfesor) {
		Profesor c = new Profesor();
		try {
			connection = con.conectar();
			String query = "select a.id_Profesor,p.dni,p.apellidos,p.nombres,p.correo,p.año,g.nombre as grado,p.id_persona from Profesor a inner join usuario u on a.id_usuario = u.id_usuario inner join persona p on u.id_persona = p.id_persona inner join grado g on a.id_grado = g.id_grado where a.id_Profesor = ?";
			PreparedStatement sp = connection.prepareStatement(query);
			sp.setInt(1, idProfesor);
			sp.executeQuery();

			ResultSet resultado = sp.getResultSet();
			while (resultado.next()) {
				c.setIdProfesor(resultado.getInt(1));
				c.setDni(resultado.getString(2));
				c.setApellidos(resultado.getString(3));
				c.setNombres(resultado.getString(4));
				c.setCorreo(resultado.getString(5));
				c.setAño(resultado.getString(6));
				c.setGrado(resultado.getString(7));
				c.setIdPersona(resultado.getInt(8));
			}

			sp.close();
		} catch (SQLException e) {
			System.out.println("Error: lista curso profesor");
			e.printStackTrace();
		}

		return c;
	}

}
