package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.ICurso;
import dao.IReporte;
import model.Alumno;
import model.Curso;
import model.Reporte;
import util.Conexion;

public class ReporteDaoImpl implements IReporte {

	private Conexion con = new Conexion();
	private Connection connection;

	@Override
	public List<Reporte> cantidad() {
		List<Reporte> lista = new ArrayList<>();
		try {
			connection = con.conectar();
			String query = "select (select count(*) from alumno where estado = 1) as alumnos,(select count(*) from profesor where estado = 1) as profesores";
			PreparedStatement sp = connection.prepareStatement(query);
			sp.executeQuery();

			ResultSet resultado = sp.getResultSet();
			while (resultado.next()) {
				Reporte c = new Reporte();
				c.setTitulo(resultado.getString(1));
				c.setCantidad(resultado.getDouble(2));
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
	public List<Reporte> alumnoGrado() {
		List<Reporte> lista = new ArrayList<>();
		try {
			connection = con.conectar();
			String query = "select g.nombre as grado,(select count(*) from alumno where estado = 1 and id_grado = g.id_grado) as alumnos from grado g";
			PreparedStatement sp = connection.prepareStatement(query);
			sp.executeQuery();

			ResultSet resultado = sp.getResultSet();
			while (resultado.next()) {
				Reporte c = new Reporte();
				c.setTitulo(resultado.getString(1));
				c.setCantidad(resultado.getDouble(2));
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
	public List<Reporte> promedio() {
		List<Reporte> lista = new ArrayList<>();
		try {
			connection = con.conectar();
			String query = "select \r\n" + "	c.nombre as curso,\r\n"
					+ "	ROUND((sum(rt.nota) / count(rt.nota))) as promedio\r\n" + "from curso c\r\n"
					+ "inner join sesiones s on c.id_curso = s.id_curso\r\n"
					+ "inner join materiales m on s.id_sesion = m.id_session\r\n"
					+ "inner join tareas t on m.id_material = t.id_material\r\n"
					+ "inner join respuestas_tarea rt on t.id_tarea = rt.id_tarea\r\n" + "group by c.nombre";
			PreparedStatement sp = connection.prepareStatement(query);
			sp.executeQuery();

			ResultSet resultado = sp.getResultSet();
			while (resultado.next()) {
				Reporte c = new Reporte();
				c.setTitulo(resultado.getString(1));
				c.setCantidad(resultado.getDouble(2));
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
	public List<Reporte> puestos() {
		List<Reporte> lista = new ArrayList<>();
		try {
			connection = con.conectar();
			String query = "select concat(p.nombres,' ',p.apellidos) as alumno,\r\n"
					+ "ROUND((sum(rt.nota) / count(rt.nota))) as promedio\r\n" + "from alumno a \r\n"
					+ "inner join respuestas_tarea rt on a.id_alumno = rt.id_alumno\r\n"
					+ "inner join usuario u on a.id_usuario = u.id_usuario\r\n"
					+ "inner join persona p on u.id_persona = p.id_persona\r\n" + "group by p.nombres,p.apellidos";
			PreparedStatement sp = connection.prepareStatement(query);
			sp.executeQuery();

			ResultSet resultado = sp.getResultSet();
			while (resultado.next()) {
				Reporte c = new Reporte();
				c.setTitulo(resultado.getString(1));
				c.setCantidad(resultado.getDouble(2));
				lista.add(c);
			}

			sp.close();
		} catch (SQLException e) {
			System.out.println("Error: lista curso profesor");
			e.printStackTrace();
		}

		return lista;
	}

}
