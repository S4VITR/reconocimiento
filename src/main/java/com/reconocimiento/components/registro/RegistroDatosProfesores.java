package com.reconocimiento.components.registro;

import javax.swing.*;

import static com.reconocimiento.template.custom.CustomComponents.*;

import java.awt.*;

public class RegistroDatosProfesores extends JPanel {

    public static JLabel video_capture;
    public static JButton button;

    public static JLabel id_ProfesorLabel;
    public static JLabel id_ProfesorField;
    private JLabel nombreLabel;
    public static JTextField nombreField;
    private JLabel puestoLabel;
    public static JTextField puestoField;

    public RegistroDatosProfesores() {
        this.setLayout(null);
        this.setBackground(new Color(255, 255, 255));

        id_ProfesorLabel = new JLabel();
        id_ProfesorField = new JLabel();
        nombreLabel = new JLabel();
        nombreField = new JTextField();
        puestoLabel = new JLabel();
        puestoField = new JTextField();

        id_ProfesorLabel = getCustomLabel("ID: ", 600, 100, 100, 20);
        id_ProfesorField = getCustomLabel("1", 720, 100, 100, 20);

        nombreLabel = getCustomLabel("Nombre completo: ", 600, 140, 100, 20);
        nombreField = getCustomField(750, 140, 180, 20);

        puestoLabel = getCustomLabel("Puesto: ", 600, 180, 100, 20);
        puestoField = getCustomField(750, 180, 180, 20);

        video_capture = new JLabel();
        video_capture.setBounds(10, 10, 550, 550);

        button = new JButton("Capturar");
        button.setBounds(700, 300, 100, 30);

        this.setBounds(210, 50, 950, 600);
        this.setOpaque(false);

        this.add(video_capture);
        this.add(button);
        this.add(id_ProfesorLabel);
        this.add(id_ProfesorField);
        this.add(nombreLabel);
        this.add(nombreField);
        this.add(puestoLabel);
        this.add(puestoField);    
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
    }
}
