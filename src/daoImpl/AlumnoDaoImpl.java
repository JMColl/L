package daoImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import dao.IAlumno;
import dao.ICurso;
import model.Alumno;
import model.Curso;
import model.Grado;
import util.Conexion;

public class AlumnoDaoImpl implements IAlumno {

	private Conexion con = new Conexion();
	private Connection connection;

	@Override
	public List<Alumno> listaAlumno() {
		List<Alumno> lista = new ArrayList<>();
		try {
			connection = con.conectar();
			String query = "select a.id_alumno,p.dni,p.apellidos,p.nombres,p.correo,p.año,g.nombre as grado from alumno a inner join usuario u on a.id_usuario = u.id_usuario inner join persona p on u.id_persona = p.id_persona inner join grado g on a.id_grado = g.id_grado where a.estado = 1";
			PreparedStatement sp = connection.prepareStatement(query);
			sp.executeQuery();

			ResultSet resultado = sp.getResultSet();
			while (resultado.next()) {
				Alumno c = new Alumno();
				c.setIdAlumno(resultado.getInt(1));
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
	public int agregar(Alumno alumno) {
		int respuesta = 0;

		try {
			connection = con.conectar();
			CallableStatement sp = connection.prepareCall("CALL agregarAlumno(?,?,?,?,?,?,?)");
			sp.setString("dni", alumno.getDni());
			sp.setString("apellidos", alumno.getApellidos());
			sp.setString("nombres", alumno.getNombres());
			sp.setString("correo", alumno.getCorreo());
                        sp.setString("año", alumno.getAño());
			sp.setInt("idGrado", alumno.getIdGrado());

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
	public int actualizar(Alumno alumno) {
		int respuesta = 0;

		try {
			connection = con.conectar();
			CallableStatement sp = connection.prepareCall("CALL actualizarAlumno(?,?,?,?,?,?,?,?)");
			sp.setInt("idAlumno", alumno.getIdAlumno());
			sp.setInt("idPersona", alumno.getIdPersona());
			sp.setString("apellidos", alumno.getApellidos());
			sp.setString("nombres", alumno.getNombres());
			sp.setString("correo", alumno.getCorreo());
                        sp.setString("año", alumno.getAño());
			sp.setInt("idGrado", alumno.getIdGrado());

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
	public int eliminar(int idAlumno) {
		int rpta = 0;
		try {
			connection = con.conectar();
			String query = "update alumno set estado = 0 where id_alumno = ?";
			PreparedStatement sp = connection.prepareStatement(query);
			sp.setInt(1, idAlumno);

			rpta = sp.executeUpdate();

			sp.close();
		} catch (SQLException e) {
			System.out.println("Error: lista sesion");
			e.printStackTrace();
		}

		return rpta;
	}

	@Override
	public Alumno datos(int idAlumno) {
		Alumno c = new Alumno();
		try {
			connection = con.conectar();
			String query = "select a.id_alumno,p.dni,p.apellidos,p.nombres,p.correo,g.nombre as grado,p.id_persona from alumno a inner join usuario u on a.id_usuario = u.id_usuario inner join persona p on u.id_persona = p.id_persona inner join grado g on a.id_grado = g.id_grado where a.id_alumno = ?";
			PreparedStatement sp = connection.prepareStatement(query);
			sp.setInt(1, idAlumno);
			sp.executeQuery();

			ResultSet resultado = sp.getResultSet();
			while (resultado.next()) {
				c.setIdAlumno(resultado.getInt(1));
				c.setDni(resultado.getString(2));
				c.setApellidos(resultado.getString(3));
				c.setNombres(resultado.getString(4));
				c.setCorreo(resultado.getString(5));
				c.setGrado(resultado.getString(6));
				c.setIdPersona(resultado.getInt(7));
			}

			sp.close();
		} catch (SQLException e) {
			System.out.println("Error: lista curso profesor");
			e.printStackTrace();
		}

		return c;
	}

}
