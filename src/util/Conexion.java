package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	private static Connection cnx = null;

	public static Connection conectar() {

		try {
			if (cnx == null || cnx.isClosed()) {
				Class.forName("com.mysql.cj.jdbc.Driver").newInstance(); 
				cnx = DriverManager.getConnection("jdbc:mysql://localhost/platform1?serverTimezone=America/Lima", "root", "");
			}

			if (cnx != null) {
				System.out.println("Conectado");
			}
		} catch (Exception e) {
			System.out.println("No se pudo conectar a la base de datos");
			e.printStackTrace();
		}

		return cnx;
	}

	public static void cerrar() throws SQLException {
		if (cnx != null && !cnx.isClosed()) {
			cnx.close();
		}
	}
}
