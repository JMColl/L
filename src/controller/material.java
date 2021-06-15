package controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.IMaterial;
import daoImpl.MaterialDaoImpl;
import model.Material;
import model.Usuario;

/**
 * Servlet implementation class material
 */
@WebServlet("/material")
public class material extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private IMaterial m = new MaterialDaoImpl();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public material() {
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

		int idSesion = Integer.parseInt(request.getParameter("idSesion"));
		String nombre = request.getParameter("nombre");
		int idCurso = Integer.parseInt(request.getParameter("idCurso"));
		String curso = request.getParameter("curso");
		u.setIdUsuario((int) sesion.getAttribute("id_usuario"));
		u.setDatos((String) sesion.getAttribute("usuario"));
		u.setIdRol((int) sesion.getAttribute("rol"));

		request.setAttribute("usuario", u);
		request.setAttribute("idSesion", idSesion);
		request.setAttribute("nombre", nombre);
		request.setAttribute("idCurso", idCurso);
		request.setAttribute("curso", curso);

		int rpta = m.validarExiste(idSesion);

		if (rpta > 0) {
			request.getRequestDispatcher("lista_material.jsp").forward(request, response);
		} else {
			if (u.getIdRol() == 2) {
				request.getRequestDispatcher("material.jsp").forward(request, response);
			} else {
				request.getRequestDispatcher("black.jsp").forward(request, response);
			}

		}

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

		int idSession = Integer.parseInt(request.getParameter("idSesion"));
		String titulo = request.getParameter("titulo");
		String link = request.getParameter("link");
		String descripcion = request.getParameter("descripcion");
		String titulo_tarea = request.getParameter("titulo_tarea");
		String descripcion_tarea = request.getParameter("descripcion_tarea");
		String fecha_tarea = request.getParameter("fecha_tarea");

		int idCurso = Integer.parseInt(request.getParameter("idCurso"));
		String curso = request.getParameter("curso");

		int v = 0;

		try {
			v = m.validarExiste(idSession);
		} catch (Exception e) {
			e.getMessage();
		}

		if (v > 0) {
			request.getRequestDispatcher("lista_material.jsp").forward(request, response);
		} else {
			Material ma = new Material();
			ma.setIdSession(idSession);
			ma.setTitulo(titulo);
			ma.setLink(link);
			ma.setDescripcion(descripcion);
			ma.setTitulo_tarea(titulo_tarea);
			ma.setDescripcion_tarea(descripcion_tarea);
			ma.setFecha_tarea(fecha_tarea);
			int rpta = m.guardar(ma);

			if (rpta > 0) {

				String nombre = request.getParameter("nombre");
				u.setIdUsuario((int) sesion.getAttribute("id_usuario"));
				u.setDatos((String) sesion.getAttribute("usuario"));
				u.setIdRol((int) sesion.getAttribute("rol"));

				request.setAttribute("usuario", u);
				request.setAttribute("idSesion", idSession);
				request.setAttribute("nombre", nombre);
				request.setAttribute("idCurso", idCurso);
				request.setAttribute("curso", curso);
				request.getRequestDispatcher("lista_material.jsp").forward(request, response);
			} else {

				String nombre = request.getParameter("nombre");
				u.setIdUsuario((int) sesion.getAttribute("id_usuario"));
				u.setDatos((String) sesion.getAttribute("usuario"));
				u.setIdRol((int) sesion.getAttribute("rol"));

				request.setAttribute("usuario", u);
				request.setAttribute("idSesion", idSession);
				request.setAttribute("nombre", nombre);
				request.setAttribute("idCurso", idCurso);
				request.setAttribute("curso", curso);
				request.getRequestDispatcher("material.jsp").forward(request, response);
			}
		}

	}

}
