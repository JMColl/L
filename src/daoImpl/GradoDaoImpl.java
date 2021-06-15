package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.ICurso;
import dao.IGrado;
import model.Curso;
import model.Grado;
import util.Conexion;

public class GradoDaoImpl implements IGrado {

	private Conexion con = new Conexion();
	private Connection connection;

	@Override
	public List<Grado> listaGrado() {
		List<Grado> lista = new ArrayList<>();
		try {
			connection = con.conectar();
			String query = "select id_grado,nombre from grado";
			PreparedStatement sp = connection.prepareStatement(query);
			sp.executeQuery();

			ResultSet resultado = sp.getResultSet();
			while (resultado.next()) {
				Grado c = new Grado();
				c.setIdGrado(resultado.getInt(1));
				c.setNombre(resultado.getString(2));
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
