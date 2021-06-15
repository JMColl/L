package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ICurso;
import daoImpl.CursoDaoImpl;
import model.Curso;
import model.Usuario;

/**
 * Servlet implementation class curso
 */
@WebServlet("/curso")
public class curso extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ICurso curso = new CursoDaoImpl();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public curso() {
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
		request.getRequestDispatcher("lista_curso.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
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
		request.getRequestDispatcher("curso.jsp").forward(request, response);
	}

	private void agregar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession sesion = request.getSession();
		Usuario u = new Usuario();

		u.setIdUsuario((int) sesion.getAttribute("id_usuario"));
		u.setDatos((String) sesion.getAttribute("usuario"));
		u.setIdRol((int) sesion.getAttribute("rol"));

		String nombre = request.getParameter("curso");
                String año = request.getParameter("año");
		int grado = Integer.parseInt(request.getParameter("grado"));
		int profesor = Integer.parseInt(request.getParameter("profesor"));

		Curso a = new Curso();
		a.setNombre(nombre);
                a.setAño(año);
		a.setIdGrado(grado);
		a.setIdProfesor(profesor);

		curso.agregar(a);

		request.setAttribute("usuario", u);
		request.getRequestDispatcher("lista_curso.jsp").forward(request, response);
	}

	private void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession sesion = request.getSession();
		Usuario u = new Usuario();

		u.setIdUsuario((int) sesion.getAttribute("id_usuario"));
		u.setDatos((String) sesion.getAttribute("usuario"));
		u.setIdRol((int) sesion.getAttribute("rol"));

		request.setAttribute("usuario", u);
		request.getRequestDispatcher("curso-editar.jsp").forward(request, response);
	}

	private void actualizar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession sesion = request.getSession();
		Usuario u = new Usuario();

		u.setIdUsuario((int) sesion.getAttribute("id_usuario"));
		u.setDatos((String) sesion.getAttribute("usuario"));
		u.setIdRol((int) sesion.getAttribute("rol"));

		int idCurso = Integer.parseInt(request.getParameter("idCurso"));
		String nombre = request.getParameter("curso");
                String año = request.getParameter("año");
		int idGrado = Integer.parseInt(request.getParameter("grado"));
		int idProfesor = Integer.parseInt(request.getParameter("profesor"));

		Curso a = new Curso();
		a.setIdCurso(idCurso);
		a.setNombre(nombre);
                a.setAño(año);
		a.setIdGrado(idGrado);
		a.setIdProfesor(idProfesor);

		curso.actualizar(a);

		request.setAttribute("usuario", u);
		request.getRequestDispatcher("lista_curso.jsp").forward(request, response);
	}

	private void eliminar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		int idCurso = Integer.parseInt(request.getParameter("idCurso"));

		int rpta = curso.eliminar(idCurso);

		out.print(rpta);
	}

}
