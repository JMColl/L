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
import model.Curso;
import model.Curso;
import model.Curso;
import util.Conexion;

public class CursoDaoImpl implements ICurso {

	private Conexion con = new Conexion();
	private Connection connection;

	@Override
	public List<Curso> listaCurso(int idUsuario, int idRol) {
		List<Curso> lista = new ArrayList<>();
		try {
			connection = con.conectar();
			String query = "";
			if (idRol == 2) {
				query = "select c.id_curso,c.nombre,c.año from curso c inner join profesor p on c.id_profesor = p.id_profesor where p.id_usuario = ?";
			} else if (idRol == 3) {
				query = "select c.id_curso,c.nombre,c.año from curso c inner join grado g on c.id_grado = g.id_grado inner join alumno a on g.id_grado = a.id_grado where a.id_usuario = ?";
			}
			System.out.println(query);
			PreparedStatement sp = connection.prepareStatement(query);
			sp.setInt(1, idUsuario);
			sp.executeQuery();

			ResultSet resultado = sp.getResultSet();
			while (resultado.next()) {
				Curso c = new Curso();
				c.setIdCurso(resultado.getInt(1));
				c.setNombre(resultado.getString(2));
                                c.setAño(resultado.getString(3));
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
	public List<Curso> listaCursos() {
		List<Curso> lista = new ArrayList<>();
		try {
			connection = con.conectar();
			String query = "select c.id_curso,c.nombre,c.año as curso,g.id_grado,g.nombre as grado,p.id_profesor,concat(pe.nombres,' ',pe.apellidos) as profesor from curso c inner join grado g on c.id_grado = g.id_grado inner join profesor p on p.id_profesor = c.id_profesor inner join usuario u on p.id_usuario = u.id_usuario inner join persona pe on pe.id_persona = u.id_persona";
			PreparedStatement sp = connection.prepareStatement(query);
			sp.executeQuery();

			ResultSet resultado = sp.getResultSet();
			while (resultado.next()) {
				Curso c = new Curso();
				c.setIdCurso(resultado.getInt(1));
				c.setNombre(resultado.getString(2));
                                c.setAño(resultado.getString(3));
				c.setIdGrado(resultado.getInt(4));
				c.setGrado(resultado.getString(5));
				c.setIdProfesor(resultado.getInt(6));
				c.setProfesor(resultado.getString(7));
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
	public Curso datos(int idCurso) {
		Curso c = new Curso();
		try {
			connection = con.conectar();
			String query = "select nombre,año,id_grado,id_profesor from curso where id_curso = ?";
			PreparedStatement sp = connection.prepareStatement(query);
			sp.setInt(1, idCurso);
			sp.executeQuery();

			ResultSet resultado = sp.getResultSet();
			while (resultado.next()) {
				c.setNombre(resultado.getString(1));
                                c.setAño(resultado.getString(2));
				c.setIdGrado(resultado.getInt(3));
				c.setIdProfesor(resultado.getInt(4));
			}

			sp.close();
		} catch (SQLException e) {
			System.out.println("Error: lista curso profesor");
			e.printStackTrace();
		}

		return c;
	}

	@Override
	public int agregar(Curso curso) {
		int respuesta = 0;

		try {
			connection = con.conectar();
			CallableStatement sp = connection.prepareCall("CALL agregarCurso(?,?,?,?,?)");
			sp.setString("nombre", curso.getNombre());
                        sp.setString("año", curso.getAño());
			sp.setInt("idGrado", curso.getIdGrado());
			sp.setInt("idProfesor", curso.getIdProfesor());

			sp.registerOutParameter("respuesta", Types.INTEGER);
			sp.execute();
			respuesta = sp.getInt("respuesta");
			sp.close();
			con.cerrar();
		} catch (SQLException e) {
			System.out.println("Error: comentario Curso");
			e.printStackTrace();
		}

		return respuesta;
	}

	@Override
	public int actualizar(Curso curso) {
		int respuesta = 0;

		try {
			connection = con.conectar();
			CallableStatement sp = connection.prepareCall("CALL actualizarCurso(?,?,?,?,?,?)");
			sp.setInt("idCurso", curso.getIdCurso());
			sp.setString("nombre", curso.getNombre());
                        sp.setString("año", curso.getAño());
			sp.setInt("idGrado", curso.getIdGrado());
			sp.setInt("idProfesor", curso.getIdProfesor());

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
	public int eliminar(int idCurso) {
		int rpta = 0;
		try {
			connection = con.conectar();
			String query = "delete from curso where id_curso = ?";
			PreparedStatement sp = connection.prepareStatement(query);
			sp.setInt(1, idCurso);

			rpta = sp.executeUpdate();

			sp.close();
		} catch (SQLException e) {
			System.out.println("Error: lista sesion");
			e.printStackTrace();
		}

		return rpta;
	}

}
