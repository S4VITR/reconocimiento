package com.reconocimiento.event;

import org.bytedeco.opencv.opencv_videoio.VideoCapture;

import com.reconocimiento.global.reconocedor.ReconocimientoFacialProfesor;
import com.reconocimiento.global.registro.RegistroFacialProfesor;

public class Camara {
    public static VideoCapture capturaDeVideo;
    //Método para inicializar la camara
    public void iniciarCamara() throws Exception {

        new RegistroFacialProfesor().getOrderId();

        capturaDeVideo = new VideoCapture();
        capturaDeVideo.open(1);

        if (capturaDeVideo.isOpened()) {
            // new RegistroFacialProfesor().registroFacialProfesor();
            new ReconocimientoFacialProfesor().reconocimientoFacialEmpleado();

        } else {
            System.err.println("Error al acceder a la camara");
        }
    }
}
