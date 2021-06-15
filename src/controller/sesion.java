package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.Isesion;
import daoImpl.SesionDaoImpl;
import model.Sesion;
import model.Usuario;

/**
 * Servlet implementation class sesion
 */
@WebServlet("/sesion")
public class sesion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Isesion s = new SesionDaoImpl();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public sesion() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession sesion = request.getSession();
		Usuario u = new Usuario();

		int idCurso = Integer.parseInt(request.getParameter("idCurso"));
		String nombre = request.getParameter("nombre");
		u.setIdUsuario((int) sesion.getAttribute("id_usuario"));
		u.setDatos((String) sesion.getAttribute("usuario"));
		u.setIdRol((int) sesion.getAttribute("rol"));

		request.setAttribute("usuario", u);
		request.setAttribute("idCurso", idCurso);
		request.setAttribute("nombre", nombre);
		request.getRequestDispatcher("sesion.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// doGet(request, response);
		HttpSession sesion = request.getSession();
		Usuario u = new Usuario();

		u.setIdUsuario((int) sesion.getAttribute("id_usuario"));
		u.setDatos((String) sesion.getAttribute("usuario"));
		u.setIdRol((int) sesion.getAttribute("rol"));

		int idCurso = Integer.parseInt(request.getParameter("idCurso"));
		String nombreCurso = request.getParameter("nombre");
		String nombre = request.getParameter("session");

		Sesion se = new Sesion();
		se.setIdCurso(idCurso);
		se.setIdUsuarioReg(u.getIdUsuario());
		se.setNombre(nombre);

		s.agregarSesion(se);

		request.setAttribute("usuario", u);
		request.setAttribute("idCurso", idCurso);
		request.setAttribute("nombre", nombreCurso);
		request.getRequestDispatcher("sesion.jsp").forward(request, response);

	}

}
