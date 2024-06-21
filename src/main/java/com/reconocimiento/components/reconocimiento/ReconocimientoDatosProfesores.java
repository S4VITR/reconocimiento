package com.reconocimiento.components.reconocimiento;

import static com.reconocimiento.template.custom.CustomComponents.*;

import java.awt.*;
import javax.swing.*;

public class ReconocimientoDatosProfesores extends JPanel {

    public static JLabel id_ProfesorJLabel;
    public static JLabel name_ProfesorJLabel;

    public ReconocimientoDatosProfesores() {
        this.setLayout(null);
        this.setBackground(new Color(255, 255, 255));

        JLabel idLabel = getCustomLabel("ID: ", 100, 100, 150, 20);

        id_ProfesorJLabel = new JLabel();
        id_ProfesorJLabel.setText("");
        id_ProfesorJLabel.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 12));
        id_ProfesorJLabel.setForeground(new Color(0, 0,0));
        id_ProfesorJLabel.setBounds(140, 100, 50, 20);

        JLabel nameLabel = getCustomLabel("Nombre: ", 70, 150, 150, 20);

        name_ProfesorJLabel = new JLabel();
        name_ProfesorJLabel.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 12));
        name_ProfesorJLabel.setForeground(new Color(0, 0,0));
        name_ProfesorJLabel.setBounds(140, 150, 150, 20);

        this.setBounds(820, 100, 350, 500);
        this.setOpaque(false);

        this.add(id_ProfesorJLabel);
        this.add(name_ProfesorJLabel);
        this.add(idLabel);
        this.add(nameLabel);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
    }
}
