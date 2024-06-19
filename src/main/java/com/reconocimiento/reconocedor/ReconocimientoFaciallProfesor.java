package com.reconocimiento.reconocedor;

import static com.reconocimiento.camara.Camara.capturaDeVideo;
import static com.reconocimiento.components.reconocimiento.CamaraInicializada.camera_capture;
import static com.reconocimiento.components.reconocimiento.ReconocimientoDatosProfesores.id_ProfesorJLabel;
import static com.reconocimiento.components.reconocimiento.ReconocimientoDatosProfesores.name_ProfesorJLabel;
import static com.reconocimiento.database.Conexion.conexion;
import static com.reconocimiento.modules.DataType.devolverDiaDeLaSemana;
import static com.reconocimiento.modules.DataType.devolverFechaActual;
import static com.reconocimiento.modules.DataType.devolverHoraActual;
import static com.reconocimiento.modules.Mat2Image.bufferedImage;

import java.awt.image.BufferedImage;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

import javax.swing.ImageIcon;

import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Point;
import org.bytedeco.opencv.opencv_core.Rect;
import org.bytedeco.opencv.opencv_core.RectVector;
import org.bytedeco.opencv.opencv_core.Scalar;
import org.bytedeco.opencv.opencv_core.Size;
import org.bytedeco.opencv.opencv_face.LBPHFaceRecognizer;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;

import com.reconocimiento.model.asistencia_profesor.AsistenciasProfesoresModel;
import com.reconocimiento.model.controlador.ControladorAsistencias;

/**
 * @author Eduardo Gómez 
 * */
public class ReconocimientoFaciallProfesor {

    //? Obtener la fecha actual
    private final Date fecha = devolverFechaActual();
    //? Obtener la hora actual
    private final Time hora = devolverHoraActual();
    //? Variable para guardar el ID que se proporcione durante el reconocimiento facial
    private int id_Empleado;

    /**
     * * Realiza el reconocimiento facial de empleados utilizando un clasificador Haar y un reconocedor LBPH.
     * 
     * @throws Exception si ocurre un error durante el proceso de reconocimiento facial.
     */
    public void reconocimientoFacialEmpleado() throws Exception {

        Mat frame = new Mat();
        Mat grayFrame = new Mat();
        RectVector rostrosDetectados = new RectVector();
        CascadeClassifier clasificador = new CascadeClassifier("src\\main\\java\\com\\reconocimiento\\resource\\lib\\haarcascade_frontalface_alt.xml");
        Rect capture = new Rect(150, 80, 300, 350);

        LBPHFaceRecognizer recognizer = LBPHFaceRecognizer.create();
        recognizer.read("src\\main\\java\\com\\reconocimiento\\resource\\module\\train_LBPHrecognizer.yml");
        recognizer.setThreshold(80);

        Set<Integer> dataInsert = new HashSet<>();
        boolean espera = false;

        while (capturaDeVideo.read(frame)) {
            if (espera) continue;

            opencv_imgproc.cvtColor(frame, grayFrame, opencv_imgproc.COLOR_BGR2GRAY);
            opencv_imgproc.equalizeHist(grayFrame, grayFrame);

            if (!frame.empty()) {
                clasificador.detectMultiScale(grayFrame, rostrosDetectados, 1.1, 2, 0, new Size(150, 150), new Size(400, 400));
                Scalar color = new Scalar(255, 0, 0, 0);

                for (int i = 0; i < rostrosDetectados.size(); i++) {
                    Rect rect = rostrosDetectados.get(i);

                    if (capture.contains(new Point(rect.x() + rect.width() / 2, rect.y() + rect.height() / 2))) {
                        color = new Scalar(0, 255, 0, 0);

                        opencv_imgproc.rectangle(frame, rect, new Scalar(0, 255, 0, 3), 3, 0, 0);
                        Mat faceROI = new Mat(grayFrame, rect);
                        opencv_imgproc.resize(faceROI, faceROI, new Size(160, 160));

                        IntPointer pointer = new IntPointer(1);
                        DoublePointer confidence = new DoublePointer(1);
                        recognizer.predict(faceROI, pointer, confidence);
                        int predictedLabel = pointer.get(0);

                        if (predictedLabel == -1) {
                            opencv_imgproc.rectangle(frame, rect, new Scalar(0, 0, 255, 3), 3, 0, 0);
                            id_Empleado = 0;
                            id_ProfesorJLabel.setText("");
                            name_ProfesorJLabel.setText("Desconocido");
                        } else {
                            opencv_imgproc.rectangle(frame, rect, new Scalar(0, 255, 0, 3), 3, 0, 0);
                            id_Empleado = predictedLabel;
                            consultarDatosProfesor();

                            if (!dataInsert.contains(id_Empleado)) {
                                registrarAsistenciaProfesor(id_Empleado);
                                dataInsert.add(id_Empleado);
                            }
                        }
                    }
                }
                opencv_imgproc.rectangle(frame, capture, color);

                BufferedImage ventana = bufferedImage(frame);
                camera_capture.setIcon(new ImageIcon(ventana));

            } else {
                System.err.println("Error en la captura del rostro");
                clasificador.close();
                break;
            }
        }
    }

    /**
     * * Este método realiza una consulta los datos del profesor en una base de datos y actualiza la interfaz de usuario con la información obtenida. Este método se ejecuta en un hilo separado para evitar bloquear la interfaz de usuario.
     * 
     * @throws SQLException si ocurre un error durante el proceso de consulta.
     */
    private void consultarDatosProfesor() {

        new Thread(() -> {
            String sql = "SELECT * FROM profesores WHERE id_Empleado=?";
            ResultSet resultSet;
            try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
                preparedStatement.setString(1, String.valueOf(id_Empleado));
                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    id_ProfesorJLabel.setText(resultSet.getString("id_Empleado"));
                    name_ProfesorJLabel.setText(resultSet.getString("nombre_completo"));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    /**
     * * Este método registra la asistencia de un profesor en la base de datos. Si el registro de entrada ya existe, se actualiza la hora de entrada. Si el registro de salida ya existe, se actualiza la hora de salida.
     * 
     * @param id_Empleado El ID del empleado cuyo registro de asistencia se va a actualizar.
     * @throws Exception si ocurre un error durante el proceso de registro.
     */
    private void registrarAsistenciaProfesor(int id_Empleado) throws Exception {

        String id = String.valueOf(id_Empleado);
        Time hora_registro = Time.valueOf("00:00:00");

        if (existenciaDelRegistroDeEntrada(id, hora_registro)) {
            actualizarHoraEntradaProfesor(id);
            System.out.println("Se actualizo la hora de entrada");

        } else if (existenciaDelRegistroDeSalida(id, hora_registro)) {
            actualizarHoraSalidaProfesor(id);
            System.out.println("Se actualizo la hora de salida");
        }
    }
    
    /**
     * * Método que genera una consulta y procesa los datos del horario de profesores para el día de la semana actual. Si un profesor no tiene un registro de asistencia para el día actual, se registra como inasistencia.
     * 
     * @throws SQLException si ocurre un error durante la ejecución de la consulta SQL.
     */
    public void devolverDatosDelHorario() throws SQLException {

        String sql = "SELECT * FROM horario_profesores WHERE dia_semana = ?";
        ResultSet resultSet;
        
        String dia_semana = devolverDiaDeLaSemana();

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setString(1, dia_semana);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                String profesor_id = resultSet.getString("profesor_id");

                if (!existenciaDelRegistro(profesor_id)) {
                    insertarInasistenciaDelProfesor(profesor_id);
                }
            }
        }
    }
    
    /**
     * * Este método verifica la existencia de un registro de asistencia para un profesor en una fecha específica
     * 
     * @param id_Profesor El ID del profesor cuyo registro de asistencia se desea verificar.
     * @return true si existe un registro de asistencia para el profesor en la fecha especificada, de lo contrario false.
     * @throws SQLException si ocurre un error durante la ejecución de la consulta SQL.
     */
    private boolean existenciaDelRegistro(String id_Profesor) throws SQLException {

        String sql = "SELECT COUNT(*) FROM asistencias_profesores WHERE id_Profesor = ? AND fecha_registro = ?";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setString(1, id_Profesor);
            preparedStatement.setDate(2, fecha);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return resultSet.getInt(1) > 0;
            } 
        } 
    }
    
    /**
     * * Este método inserta un registro de inasistencia para un profesor en la base de datos.
     * 
     * @param id_Profesor El ID del profesor para el cual se va a registrar la inasistencia.
     * @throws SQLException si ocurre un error durante la ejecución de la inserción.
     */
    private void insertarInasistenciaDelProfesor(String id_Profesor) throws SQLException {

        AsistenciasProfesoresModel asistenciasProfesoresModel = new AsistenciasProfesoresModel();

        asistenciasProfesoresModel.setIdProfesor(id_Profesor);
        asistenciasProfesoresModel.setFechaRegistro(fecha);
        asistenciasProfesoresModel.setHoraEntrada(Time.valueOf("00:00:00"));
        asistenciasProfesoresModel.setHoraSalida(Time.valueOf("00:00:00"));
        asistenciasProfesoresModel.setAsistencia(false);
        
        new ControladorAsistencias().insertarInasistenciaEntrada(asistenciasProfesoresModel);
    }
    
    /**
     * * Este método verifica la existencia de un registro de entrada para un profesor en una fecha y hora específicas.
     * 
     * @param id_Profesor El ID del profesor cuyo registro de entrada se desea verificar.
     * @param hora_entrada La hora de entrada que se desea verificar.
     * @return true si existe un registro de entrada para el profesor en la fecha y hora especificadas, de lo contrario false.
     * @throws SQLException si ocurre un error durante la ejecución de la consulta SQL.
     */
    private boolean existenciaDelRegistroDeEntrada(String id_Profesor, Time hora_entrada) throws SQLException {

        String sql = "SELECT COUNT(*) FROM asistencias_profesores WHERE id_Profesor = ? AND fecha_registro = ? AND hora_entrada = ?";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setString(1, id_Profesor);
            preparedStatement.setDate(2, fecha);
            preparedStatement.setTime(3, hora_entrada);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return resultSet.getInt(1) > 0;
            } 
        }
    }
    
    /**
     * * Método que actualiza la hora de entrada de un profesor en la base de datos.
     * 
     * @param id El ID del profesor para el cual se va a registrar la hora de entrada.
     * @throws Exception si ocurre un error durante la inserción de la hora de entrada.
     */
    private void actualizarHoraEntradaProfesor(String id) throws Exception {

        AsistenciasProfesoresModel asistenciasProfesoresModel = new AsistenciasProfesoresModel();

        asistenciasProfesoresModel.setIdProfesor(id);
        asistenciasProfesoresModel.setFechaRegistro(fecha);
        asistenciasProfesoresModel.setHoraEntrada(hora);
        asistenciasProfesoresModel.setAsistencia(false);

        new ControladorAsistencias().actualizarAsistenciaEntrada(asistenciasProfesoresModel);
    }

    /**
     * * Este método verifica la existencia de un registro de salida para un profesor en una fecha y hora específicas.
     * 
     * @param id_Profesor El ID del profesor cuyo registro de entrada se desea verificar.
     * @param hora_salida La hora de salida que se desea verificar.
     * @return true si existe un registro de salida para el profesor en la fecha y hora especificadas, de lo contrario false.
     * @throws SQLException si ocurre un error durante la ejecución de la consulta SQL.
     */
    private boolean existenciaDelRegistroDeSalida(String id_Profesor, Time hora_salida) throws SQLException {

        String sql = "SELECT COUNT(*) FROM asistencias_profesores WHERE id_Profesor = ? AND fecha_registro = ? AND hora_salida = ?";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setString(1, id_Profesor);
            preparedStatement.setDate(2, fecha);
            preparedStatement.setTime(3, hora_salida);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return resultSet.getInt(1) > 0;
            }
        }
    }
    
    /**
     * * Método que actualiza la hora de salida de un profesor en la base de datos.
     * 
     * @param id El ID del profesor para el cual se va a registrar la hora de entrada.
     * @throws Exception si ocurre un error durante la inserción de la hora de entrada.
     */
    private void actualizarHoraSalidaProfesor(String id) throws Exception {

        AsistenciasProfesoresModel asistenciasProfesoresModel = new AsistenciasProfesoresModel();

        asistenciasProfesoresModel.setIdProfesor(id);
        asistenciasProfesoresModel.setFechaRegistro(fecha);
        asistenciasProfesoresModel.setHoraSalida(hora);
        asistenciasProfesoresModel.setAsistencia(true);

        new ControladorAsistencias().actualizarAsistenciaSalida(asistenciasProfesoresModel);
    }
}
