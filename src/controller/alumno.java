package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.IAlumno;
import daoImpl.AlumnoDaoImpl;
import model.Alumno;
import model.Usuario;

/**
 * Servlet implementation class alumno
 */
@WebServlet("/alumno")
public class alumno extends HttpServlet {
	private static final long serialVersionUID = 1L;

	IAlumno alumno = new AlumnoDaoImpl();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public alumno() {
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
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession sesion = request.getSession();
		Usuario u = new Usuario();

		u.setIdUsuario((int) sesion.getAttribute("id_usuario"));
		u.setDatos((String) sesion.getAttribute("usuario"));
		u.setIdRol((int) sesion.getAttribute("rol"));

		request.setAttribute("usuario", u);
		request.getRequestDispatcher("lista_alumno.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// doGet(request, response);

		String action = request.getParameter("accion");

		try {
			switch (action) {
			case "nuevo":
				nuevo(request, response);
				break;
			case "agregar":
				agregar(request, response);
				break;
			case "editar":
				editar(request, response);
				break;
			case "actualizar":
				actualizar(request, response);
				break;
			case "eliminar":
				eliminar(request, response);
				break;
			default:
				break;
			}
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

	private void nuevo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession sesion = request.getSession();
		Usuario u = new Usuario();

		u.setIdUsuario((int) sesion.getAttribute("id_usuario"));
		u.setDatos((String) sesion.getAttribute("usuario"));
		u.setIdRol((int) sesion.getAttribute("rol"));

		request.setAttribute("usuario", u);
		request.getRequestDispatcher("alumno.jsp").forward(request, response);
	}

	private void agregar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession sesion = request.getSession();
		Usuario u = new Usuario();

		u.setIdUsuario((int) sesion.getAttribute("id_usuario"));
		u.setDatos((String) sesion.getAttribute("usuario"));
		u.setIdRol((int) sesion.getAttribute("rol"));

		String dni = request.getParameter("dni");
		String apellidos = request.getParameter("apellidos");
		String nombres = request.getParameter("nombres");
		String correo = request.getParameter("correo");
                String año = request.getParameter("año");
		int grado = Integer.parseInt(request.getParameter("grado"));

		Alumno a = new Alumno();
		a.setDni(dni);
		a.setNombres(nombres);
		a.setApellidos(apellidos);
		a.setCorreo(correo);
                a.setAño(año);
		a.setIdGrado(grado);

		alumno.agregar(a);

		request.setAttribute("usuario", u);
		request.getRequestDispatcher("lista_alumno.jsp").forward(request, response);
	}

	private void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession sesion = request.getSession();
		Usuario u = new Usuario();

		u.setIdUsuario((int) sesion.getAttribute("id_usuario"));
		u.setDatos((String) sesion.getAttribute("usuario"));
		u.setIdRol((int) sesion.getAttribute("rol"));

		request.setAttribute("usuario", u);
		request.getRequestDispatcher("alumno-editar.jsp").forward(request, response);
	}

	private void actualizar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession sesion = request.getSession();
		Usuario u = new Usuario();

		u.setIdUsuario((int) sesion.getAttribute("id_usuario"));
		u.setDatos((String) sesion.getAttribute("usuario"));
		u.setIdRol((int) sesion.getAttribute("rol"));

		int idAlumno = Integer.parseInt(request.getParameter("idAlumno"));
		int idPersona = Integer.parseInt(request.getParameter("idPersona"));
		String apellidos = request.getParameter("apellidos");
		String nombres = request.getParameter("nombres");
		String correo = request.getParameter("correo");
                String año = request.getParameter("año");
		int grado = Integer.parseInt(request.getParameter("grado"));

		Alumno a = new Alumno();
		a.setIdAlumno(idAlumno);
		a.setIdPersona(idPersona);
		a.setNombres(nombres);
		a.setApellidos(apellidos);
		a.setCorreo(correo);
                a.setAño(año);
		a.setIdGrado(grado);

		alumno.actualizar(a);

		request.setAttribute("usuario", u);
		request.getRequestDispatcher("lista_alumno.jsp").forward(request, response);
	}

	private void eliminar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		int idAlumno = Integer.parseInt(request.getParameter("idAlumno"));

		int rpta = alumno.eliminar(idAlumno);

		out.print(rpta);
	}
}
