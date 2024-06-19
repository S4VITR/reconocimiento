package com.reconocimiento.components.reconocimiento;

import java.awt.*;
import javax.swing.*;

public class CamaraInicializada extends JPanel {

    public static JLabel camera_capture;
    
    public CamaraInicializada() {
        camera_capture = new JLabel();

        this.setLayout(null);
        this.setBackground(new Color(255, 255, 255));

        this.setBounds(210, 50, 600, 600);
        this.setOpaque(false);

        camera_capture.setBounds(10, 10, 550, 550);
        this.add(camera_capture);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
    }
}
