package controller;

import com.google.gson.Gson;

import dao.IReporte;
import daoImpl.ReporteDaoImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Reporte;
import model.Usuario;

/**
 * Servlet implementation class reporte
 */
@WebServlet("/reporte")
public class reporte extends HttpServlet {
	private static final long serialVersionUID = 1L;

	IReporte reporte = new ReporteDaoImpl();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public reporte() {
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
		request.getRequestDispatcher("reporte.jsp").forward(request, response);
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
			case "cantidad":
				cantidad(request, response);
				break;
			case "alumnoGrado":
				alumnoGrado(request, response);
				break;
			case "promedio":
				promedio(request, response);
				break;
			case "puesto":
				puesto(request, response);
				break;
			default:
				break;
			}
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

	private void cantidad(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		List<Reporte> r = reporte.cantidad();

		String employeeJsonString = new Gson().toJson(r);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(employeeJsonString);
		out.flush();
	}

	private void alumnoGrado(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		List<Reporte> r = reporte.alumnoGrado();

		String employeeJsonString = new Gson().toJson(r);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(employeeJsonString);
		out.flush();
	}

	private void promedio(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		List<Reporte> r = reporte.promedio();

		String employeeJsonString = new Gson().toJson(r);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(employeeJsonString);
		out.flush();
	}

	private void puesto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		List<Reporte> r = reporte.puestos();

		String employeeJsonString = new Gson().toJson(r);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(employeeJsonString);
		out.flush();
	}
}
