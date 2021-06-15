package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import dao.IForo;
import dao.IMaterial;
import dao.ITarea;
import daoImpl.ForoDaoImpl;
import daoImpl.MaterialDaoImpl;
import daoImpl.TareaDaoImpl;
import model.Tarea;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Servlet implementation class foro
 */
@WebServlet("/foro")
@MultipartConfig
public class foro extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger("mx.com.hash.newexcel.ExcelOOXML");
    IForo foro = new ForoDaoImpl();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public foro() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        // response.getWriter().append("Served at: ").append(request.getContextPath());

        int idTarea = Integer.parseInt(request.getParameter("idTarea"));

        ITarea tarea = new TareaDaoImpl();

        response.setHeader("Content-Disposition", "inline; filename=reporte-notas.xlsx");
        response.setContentType("application/vnd.ms-excel");

        XSSFWorkbook workbook = new XSSFWorkbook();

        // La hoja donde pondremos los datos
        Sheet pagina = workbook.createSheet("Reporte");
        // Creamos el estilo paga las celdas del encabezado
        CellStyle style = workbook.createCellStyle();
        // Indicamos que tendra un fondo azul aqua
        // con patron solido del color indicado
        style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        
        String[] titulos = {"Alumno", "Fecha", "Nota", "Archivo"};

        // Double[] datos = { 1.0, 10.0, 45.5, 25.50 };
        // Creamos una fila en la hoja en la posicion 0
        Row fila = pagina.createRow(0);

        // Creamos el encabezado
        
        for (int i = 0; i < titulos.length; i++) {
            // Creamos una celda en esa fila, en la posicion
            // indicada por el contador del ciclo
            Cell celda = fila.createCell(i);

            // Indicamos el estilo que deseamos
            // usar en la celda, en este caso el unico
            // que hemos creado
            celda.setCellStyle(style);
            celda.setCellValue(titulos[i]);
        }

        // Ahora creamos una fila en la posicion 1
        // Y colocamos los datos en esa fila
        int cont = 1;
        for (Tarea contrato : tarea.listaTarea(idTarea)) {
            Row dataRow = pagina.createRow(cont);
            
            Cell cell0 = dataRow.createCell(0);
            cell0.setCellValue(contrato.getAlumno());

            Cell cell1 = dataRow.createCell(1);
            cell1.setCellValue(contrato.getFecha());

            Cell cell2 = dataRow.createCell(2);
            cell2.setCellValue(contrato.getNota());
            
            Cell cell3 = dataRow.createCell(3);
            cell3.setCellValue(contrato.getRespuesta_tarea());
            cont++;

        }

        // Ahora guardaremos el archivo
        try {
            // Creamos el flujo de salida de datos,
            // apuntando al archivo donde queremos
            // almacenar el libro de Excel
            // OutputStream salida = new OutputStream(archivo);

            ServletOutputStream outputStream = response.getOutputStream();
            // Write to the output stream
            // worksheet.getWorkbook().write(outputStream);
            // Flush the stream
            outputStream.flush();
            // Almacenamos el libro de
            // Excel via ese
            // flujo de datos
            workbook.write(outputStream);

            // Cerramos el libro para concluir operaciones
            workbook.close();

            LOGGER.log(Level.INFO, "Archivo creado existosamente en {0}");

        } catch (FileNotFoundException ex) {
            LOGGER.log(Level.SEVERE, "Archivo no localizable en sistema de archivos");
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Error de entrada/salida");
        }

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        // doGet(request, response);

        String action = request.getParameter("accion");

        try {
            switch (action) {
                case "comentario":
                    comentar(request, response);
                    break;
                case "respuesta":
                    respuesta(request, response);
                    break;
                case "tarea":
                    subirArchivo(request, response);
                    break;
                case "calificar":
                    calificar(request, response);
                    break;
                case "eliminarMaterial":
                    eliminar(request, response);
                    break;
                default:
                    break;
            }
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    private void comentar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        int idUsuario = Integer.parseInt(request.getParameter("id_usuario"));
        int idMaterial = Integer.parseInt(request.getParameter("id_metrial"));
        String comentario = request.getParameter("comentario");

        int rpta = foro.comentarAlumno(comentario, idMaterial, idUsuario);

        out.print(rpta);
    }

    private void respuesta(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        int idUsuario = Integer.parseInt(request.getParameter("id_usuario"));
        int idMaterial = Integer.parseInt(request.getParameter("id_metrial"));
        int idPadre = Integer.parseInt(request.getParameter("id_padre"));
        String comentario = request.getParameter("comentario");

        int rpta = foro.comentarProfesor(comentario, idMaterial, idUsuario, idPadre);

        out.print(rpta);
    }

    private void subirArchivo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        Part filePart = request.getPart("archivo"); // Obtiene el archivo
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.

        int idTarea = Integer.parseInt(request.getParameter("id_tarea"));
        int idUsuario = Integer.parseInt(request.getParameter("id_usuario"));

        String path = "D:/archivos/";
        File uploads = new File(path); // Carpeta donde se guardan los archivos
        uploads.mkdirs(); // Crea los directorios necesarios
        File file = File.createTempFile("Tarea-" + idTarea + "-", "-" + fileName, uploads);

        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

        ITarea t = new TareaDaoImpl();
        int rpta = t.entregarTarea(idTarea, idUsuario, file.getPath().toString(), file.getName());

        out.print(rpta);
    }

    private void calificar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        int idRespuesta = Integer.parseInt(request.getParameter("id_respuesta"));
        Double nota = Double.parseDouble(request.getParameter("nota"));

        ITarea t = new TareaDaoImpl();
        int rpta = t.calificarTarea(idRespuesta, nota);

        out.print(rpta);
    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        IMaterial m = new MaterialDaoImpl();

        int idMaterial = Integer.parseInt(request.getParameter("idMaterial"));

        int rpta = m.eliminar(idMaterial);

        out.print(rpta);
    }
}
