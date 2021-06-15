package daoImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import dao.IMaterial;
import dao.Isesion;
import model.Material;
import model.Sesion;
import util.Conexion;

public class MaterialDaoImpl implements IMaterial {

	private Conexion con = new Conexion();
	private Connection connection;

	@Override
	public int guardar(Material material) {
		int respuesta = 0;

		try {
			connection = con.conectar();
			CallableStatement sp = connection.prepareCall("CALL crearMaterial(?,?,?,?,?,?,?,?)");
			sp.setInt("idSession", material.getIdSession());
			sp.setString("titulo", material.getTitulo());
			sp.setString("descripcion", material.getDescripcion());
			sp.setString("link", material.getLink());
			sp.setString("titulo_tarea", material.getTitulo_tarea());
			sp.setString("descripcion_tarea", material.getDescripcion_tarea());
			sp.setString("fecha_tarea", material.getFecha_tarea());

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
	public int validarExiste(int idSesion) {
		int rpta = 0;
		try {
			connection = con.conectar();
			String query = "select count(*) as existe from materiales where estado = 1 and id_session = ?";
			PreparedStatement sp = connection.prepareStatement(query);
			sp.setInt(1, idSesion);
			sp.executeQuery();

			ResultSet resultado = sp.getResultSet();
			while (resultado.next()) {
				rpta = resultado.getInt(1);
			}

			sp.close();
		} catch (SQLException e) {
			System.out.println("Error: lista sesion");
			e.printStackTrace();
		}

		return rpta;
	}

	@Override
	public Material verMaterial(int idSesion) {
		Material m = new Material();
		try {
			connection = con.conectar();
			String query = "select m.id_material,m.id_session,m.titulo,m.descripcion,m.link,t.titulo as titulo_tarea,t.descripcion as descripcion_tarea,t.fecha_limite as fecha_tarea,t.id_tarea from materiales m inner join tareas t on m.id_material = t.id_material where m.id_session = ?";
			PreparedStatement sp = connection.prepareStatement(query);
			sp.setInt(1, idSesion);
			sp.executeQuery();

			ResultSet resultado = sp.getResultSet();
			while (resultado.next()) {
				m.setIdMaterial(resultado.getInt(1));
				m.setIdSession(resultado.getInt(2));
				m.setTitulo(resultado.getString(3));
				m.setDescripcion(resultado.getString(4));
				m.setLink(resultado.getString(5));
				m.setTitulo_tarea(resultado.getString(6));
				m.setDescripcion_tarea(resultado.getString(7));
				m.setFecha_tarea(resultado.getString(8));
				m.setId_tarea(resultado.getInt(9));
			}

			sp.close();
		} catch (SQLException e) {
			System.out.println("Error: lista sesion");
			e.printStackTrace();
		}

		return m;
	}

	@Override
	public int actualizar(Material material) {
		int respuesta = 0;

		try {
			connection = con.conectar();
			CallableStatement sp = connection.prepareCall("CALL editarMaterial(?,?,?,?,?,?,?,?,?)");
			sp.setInt("idMaterial", material.getIdMaterial());
			sp.setString("titulo", material.getTitulo());
			sp.setString("descripcion", material.getDescripcion());
			sp.setString("link", material.getLink());
			sp.setInt("idTarea", material.getId_tarea());
			sp.setString("titulo_tarea", material.getTitulo_tarea());
			sp.setString("descripcion_tarea", material.getDescripcion_tarea());
			sp.setString("fecha_tarea", material.getFecha_tarea());

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
	public int eliminar(int idMaterial) {
		int rpta = 0;
		try {
			connection = con.conectar();
			String query = "update materiales set estado = 0 where id_material = ?";
			PreparedStatement sp = connection.prepareStatement(query);
			sp.setInt(1, idMaterial);

			rpta = sp.executeUpdate();

			sp.close();
		} catch (SQLException e) {
			System.out.println("Error: lista sesion");
			e.printStackTrace();
		}

		return rpta;

	}

}
