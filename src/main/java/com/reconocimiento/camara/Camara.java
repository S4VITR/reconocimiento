package com.reconocimiento.camara;

import org.bytedeco.opencv.opencv_videoio.VideoCapture;

import com.reconocimiento.reconocedor.ReconocimientoFaciallProfesor;
import com.reconocimiento.registro.RegistroFacialProfesor;

public class Camara {
    public static VideoCapture capturaDeVideo;
    //Método para inicializar la camara
    public void iniciarCamara() throws Exception {

        new RegistroFacialProfesor().getOrderId();

        capturaDeVideo = new VideoCapture();
        capturaDeVideo.open(1);

        if (capturaDeVideo.isOpened()) {
            // new RegistroFacialProfesor().registroFacialProfesor();
            new ReconocimientoFaciallProfesor().reconocimientoFacialEmpleado();

        } else {
            System.err.println("Error al acceder a la camara");
        }
    }
}
