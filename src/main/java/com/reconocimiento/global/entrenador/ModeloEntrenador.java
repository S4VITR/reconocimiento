package com.reconocimiento.global.entrenador;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.IntBuffer;

import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.MatVector;
import org.bytedeco.opencv.opencv_core.Size;
import org.bytedeco.opencv.opencv_face.LBPHFaceRecognizer;

import static org.bytedeco.opencv.global.opencv_core.CV_32SC1;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;
import static org.bytedeco.opencv.global.opencv_imgproc.COLOR_BGRA2GRAY;

public class ModeloEntrenador {

    public static void entrenadorFacial() {
        File directorio = new File("src\\main\\java\\com\\reconocimiento\\resource\\image\\");

        FilenameFilter archivoFiltrado = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".jpg") || name.endsWith(".png");
            }
        };

        File[] archivos = directorio.listFiles(archivoFiltrado);
        if (archivos == null || archivos.length == 0) {
            System.err.println("No se encontraron archivos de imagen en el directorio especificado.");
            return;
        }

        MatVector imagenes = new MatVector(archivos.length);
        Mat etiquetas = new Mat(archivos.length, 1, CV_32SC1);
        IntBuffer etiquetasBuffer = etiquetas.createBuffer();

        int counter = 0;
        for (File imagen : archivos) {
            Mat img = imread(imagen.getAbsolutePath(), COLOR_BGRA2GRAY);
            int idP = Integer.parseInt(imagen.getName().split("\\.")[1]);
            opencv_imgproc.resize(img, img, new Size(160, 160));

            imagenes.put(counter, img);
            etiquetasBuffer.put(counter, idP);
            counter++;
        }

        LBPHFaceRecognizer lbph = LBPHFaceRecognizer.create(1, 8, 8, 8, 12);
        lbph.train(imagenes, etiquetas);

        lbph.save("src\\main\\java\\com\\reconocimiento\\resource\\module\\train_LBPHrecognizer.yml");
        System.err.println("Se entreno el Reconocedor Facial exitosamente");
    }
}
