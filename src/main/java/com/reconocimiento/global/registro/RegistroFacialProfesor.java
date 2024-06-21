package com.reconocimiento.global.registro;

import static com.reconocimiento.database.Conexion.conexion;

import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;

import com.reconocimiento.model.profesor.ProfesorController;
import com.reconocimiento.model.profesor.ProfesorModel;
import static com.reconocimiento.components.registro.RegistroDatosProfesores.*;
import static com.reconocimiento.event.Camara.*;
import static com.reconocimiento.global.entrenador.ModeloEntrenador.*;
import static com.reconocimiento.template.utils.Mat2Image.bufferedImage;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.sql.*;

/**
 * @author Eduardo Gómez
 */
public class RegistroFacialProfesor {

    private int fotosTomadas = 1;
    private boolean espera = false;
    
    /**
     * * Realiza el registro facial de un profesor mediante la captura de imágenes faciales. Este método captura imágenes faciales de un profesor y las guarda en un directorio especificado. Una vez que se capturan 26 imágenes, se llama a los métodos para insertar datos, entrenar el reconocedor facial y activar un temporizador.
     */
    public void registroFacialProfesor() {

        Mat frame = new Mat();
        Mat grayFrame = new Mat();
        RectVector rostrosDetectados = new RectVector();
        CascadeClassifier clasificador = new CascadeClassifier("src\\main\\java\\com\\reconocimiento\\resource\\lib\\haarcascade_frontalface_alt.xml");
        Rect capture = new Rect(150, 80, 300, 350);

        while (fotosTomadas < 26) {
            if (espera) continue;

            capturaDeVideo.read(frame);
            opencv_imgproc.cvtColor(frame, grayFrame, opencv_imgproc.COLOR_BGR2GRAY);
            opencv_imgproc.equalizeHist(grayFrame, grayFrame);

            if (!frame.empty()) {
                clasificador.detectMultiScale(grayFrame, rostrosDetectados, 1.1, 1, 1, new Size(150, 150), new Size(400, 400));
                opencv_imgproc.rectangle(frame, capture, new Scalar(0, 255, 0, 2), 1, 0, 0);

                for (int i = 0; i < rostrosDetectados.size(); i++) {
                    Rect rect = rostrosDetectados.get(i);

                    if (capture.contains(new Point(rect.x() + rect.width() / 2, rect.y() + rect.height() / 2))) {
                        opencv_imgproc.rectangle(frame, rect, new Scalar(255, 255, 0, 2), 3, 0, 0);
                        Mat faceROI = new Mat(grayFrame, rect);
                        opencv_imgproc.resize(faceROI, faceROI, new Size(160, 160));

                        //Captura de Evento del JButton para guardar las imagenes en el paquete Image y llamar a al método addData()
                        button.addActionListener(e -> {

                            if (fotosTomadas < 26) {
                                String indiceFotos = String.valueOf(fotosTomadas);
                                String imagePath = "src\\main\\java\\com\\reconocimiento\\resource\\image\\profesor." + id_ProfesorField.getText() + "." + indiceFotos + ".jpg";
                                opencv_imgcodecs.imwrite(imagePath, faceROI);
                                fotosTomadas++;

                                if (fotosTomadas >= 26) {
                                    insertarDatos();
                                    entrenadorFacial();
                                    standTimer();
                                }
                            }
                        });
                    }
                }
                BufferedImage ventana = bufferedImage(frame);
                video_capture.setIcon(new ImageIcon(ventana));

            } else {
                System.err.println("Error en la captura del rostro");
                clasificador.close();
                break;
            }
        }
    }
    /**
     * * Inserta los datos de un profesor en la base de datos. Este método crea una instancia de `ProfesorModel`, establece sus campos con los valores de los campos de texto correspondientes, y utiliza `ProfesorController` para insertar el registro del profesor en la base de datos.
     */
    private void insertarDatos() {

        ProfesorModel profesorModel = new ProfesorModel();

        profesorModel.setId_Empleado(id_ProfesorField.getText());
        profesorModel.setNombre_completo(nombreField.getText());
        profesorModel.setDescripcion_puesto(puestoField.getText());

        new ProfesorController().insertarProfesor(profesorModel);
    }
    
    /**
     * * Obtiene el ID del próximo pedido para un empleado Este método recupera el ID del empleado más reciente de la tabla `profesores`, lo incrementa en 1, y lo establece en el campo de texto `id_ProfesorField`. Luego, devuelve el nuevo ID de empleado.
     * 
     * @return el próximo ID de empleado.
     * @throws NumberFormatException si ocurre un error al convertir el ID a un entero.
     * @throws SQLException si ocurre un error al ejecutar la consulta SQL.
     */
    public int getOrderId() throws NumberFormatException, SQLException {

        int id_Empleado = 0;
        String sql = "SELECT * FROM profesores ORDER BY id_Empleado DESC LIMIT 1";
        ResultSet resultSet;

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                id_ProfesorField.setText(String.valueOf(resultSet.getInt("id_Empleado")));
                id_Empleado = Integer.parseInt(id_ProfesorField.getText());
                id_Empleado++;
                id_ProfesorField.setText(String.valueOf(id_Empleado));
            }
        }
        
        return id_Empleado;
    }

    /**
     * * Inicia un temporizador de espera de 3 segundos. Este método establece la variable `espera` en `true` y crea un nuevo hilo que espera 3 segundos antes de establecer `espera` en `false`. Esto es útil para introducir un retraso después de un evento de reconocimiento facial.
     */
    private void standTimer() {
        
        espera = true;
        //Temporizador de 5 segundos despues del reconocimiento
        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            espera = false;
        }).start();
    }    
}
