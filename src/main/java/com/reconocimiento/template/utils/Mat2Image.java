package com.reconocimiento.template.utils;

import org.bytedeco.opencv.opencv_core.Mat;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class Mat2Image {

    public static BufferedImage bufferedImage (Mat mat) {
        int tipo = BufferedImage.TYPE_BYTE_GRAY;

        if (mat.channels() > 1) {
            tipo = BufferedImage.TYPE_3BYTE_BGR;
        }
        int bufferSize = mat.channels() * mat.cols() * mat.rows();
        byte[] bytes = new byte[bufferSize];
        mat.data().get(bytes);
        BufferedImage imagen = new BufferedImage(mat.cols(), mat.rows(), tipo);
        //imagen.getRaster().setDataElements(0, 0, mat.cols(), mat.rows(), bytes);
        final byte[] pixeles = ((DataBufferByte) imagen.getRaster().getDataBuffer()).getData();
        System.arraycopy(bytes, 0, pixeles, 0, bytes.length);
        return imagen;
    }
}
