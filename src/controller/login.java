package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.IUsuario;
import daoImpl.UsuarioDaoImpl;
import model.Usuario;

/**
 * Servlet implementation class login
 */
@WebServlet("/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IUsuario dao = new UsuarioDaoImpl();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public login() {
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
		sesion.invalidate();
		request.setAttribute("msg", null);
		request.getRequestDispatcher("index.jsp").forward(request, response);
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

		String usuario = request.getParameter("usuario");
		String password = request.getParameter("password");

		Usuario u = dao.login(usuario, password);

		if (u.getDatos() != null) {
			sesion.setAttribute("id_usuario", u.getIdUsuario());
			sesion.setAttribute("usuario", u.getDatos());
			sesion.setAttribute("rol", u.getIdRol());
			request.setAttribute("usuario", u);
			if (u.getIdRol() == 1) {
				request.getRequestDispatcher("lista_profesor.jsp").forward(request, response);
			} else {
				request.getRequestDispatcher("main.jsp").forward(request, response);
			}
		} else {
			sesion.invalidate();
			u.setMgs("Usuario o contraseña incorrecta");
			request.setAttribute("msg", u);
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}

	}

}
