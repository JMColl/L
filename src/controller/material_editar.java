package controller;

import java.io.IOException;
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
 * Servlet implementation class material_editar
 */
@WebServlet("/material_editar")
public class material_editar extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private IMaterial m = new MaterialDaoImpl();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public material_editar() {
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
		u.setIdUsuario((int) sesion.getAttribute("id_usuario"));
		u.setDatos((String) sesion.getAttribute("usuario"));
		u.setIdRol((int) sesion.getAttribute("rol"));

		request.setAttribute("usuario", u);
		request.setAttribute("idSesion", idSesion);
		request.setAttribute("nombre", nombre);

		if (u.getIdRol() == 2) {
			request.getRequestDispatcher("material-editar.jsp").forward(request, response);
		} else {
			request.getRequestDispatcher("black.jsp").forward(request, response);
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
		int idTarea = Integer.parseInt(request.getParameter("idTarea"));
		int idMaterial = Integer.parseInt(request.getParameter("idMaterial"));

		Material ma = new Material();
		ma.setId_tarea(idTarea);
		ma.setIdMaterial(idMaterial);
		ma.setIdSession(idSession);
		ma.setTitulo(titulo);
		ma.setLink(link);
		ma.setDescripcion(descripcion);
		ma.setTitulo_tarea(titulo_tarea);
		ma.setDescripcion_tarea(descripcion_tarea);
		ma.setFecha_tarea(fecha_tarea);
		int rpta = m.actualizar(ma);

		if (rpta > 0) {

			String nombre = request.getParameter("nombre");
			u.setIdUsuario((int) sesion.getAttribute("id_usuario"));
			u.setDatos((String) sesion.getAttribute("usuario"));
			u.setIdRol((int) sesion.getAttribute("rol"));

			request.setAttribute("usuario", u);
			request.setAttribute("idSesion", idSession);
			request.setAttribute("nombre", nombre);
			request.getRequestDispatcher("lista_material.jsp").forward(request, response);
		} else {

			String nombre = request.getParameter("nombre");
			u.setIdUsuario((int) sesion.getAttribute("id_usuario"));
			u.setDatos((String) sesion.getAttribute("usuario"));
			u.setIdRol((int) sesion.getAttribute("rol"));

			request.setAttribute("usuario", u);
			request.setAttribute("idSesion", idSession);
			request.setAttribute("nombre", nombre);
			request.getRequestDispatcher("material_editar.jsp").forward(request, response);
		}

	}

}
