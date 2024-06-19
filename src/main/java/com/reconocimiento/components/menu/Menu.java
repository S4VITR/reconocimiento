package com.reconocimiento.components.menu;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Menu extends JPanel {

    public static JButton dashboard;
    public static JButton data;
    public static JButton recognize;
    public static JButton exit;
    public static JButton minimize;
    public static JButton maximize;
    
    public Menu() {
        this.setLayout(null);
        this.setBackground(new Color(0, 0, 153));

        ImageIcon e = new ImageIcon("src\\main\\java\\com\\reconocimiento\\resource\\icons\\botonE.png");
        ImageIcon mi = new ImageIcon("src\\main\\java\\com\\reconocimiento\\resource\\icons\\botonMa.png");
        ImageIcon ma = new ImageIcon("src\\main\\java\\com\\reconocimiento\\resource\\icons\\botonMi.png");

        exit = new JButton();
        exit = eventMenu(e, 10, 10, 20, 20);
        minimize = new JButton();
        minimize = eventMenu(mi, 35, 10, 20, 20);
        maximize = new JButton();
        maximize = eventMenu(ma, 60, 10, 20, 20);

        ImageIcon dash = new ImageIcon("src\\main\\java\\com\\reconocimiento\\resource\\icons\\1.png");
        ImageIcon dat = new ImageIcon("src\\main\\java\\com\\reconocimiento\\resource\\icons\\2.png");
        ImageIcon re = new ImageIcon("src\\main\\java\\com\\reconocimiento\\resource\\icons\\3.png");

        dashboard = new JButton();
        data = new JButton();
        recognize = new JButton();

        dashboard = settingButtons("Inicio", dash, -50, 50, 250, 50);
        data = settingButtons("Profesores", dat, -30, 100, 250, 50);
        recognize = settingButtons("Alumnos", re, -35, 150, 250, 50);

        ArrayList<JButton> buttons = new ArrayList<>();
        buttons.add(dashboard);
        buttons.add(data);
        buttons.add(recognize);

        buttons.forEach(b -> {
            b.setBorderPainted(false);
            b.setFocusPainted(false);
            b.setContentAreaFilled(true);
            b.setHorizontalTextPosition(SwingConstants.RIGHT);
        });

        colorsButtons();

        this.setBounds(0, 0, 200, 700);
    }
    private void colorsButtons() {
        dashboard.addActionListener(e -> {
            dashboard.setBackground(new Color(0, 0,255));
            data.setBackground(new Color(0, 0, 153));
            recognize.setBackground(new Color(0, 0, 153));
        });
        data.addActionListener(e -> {
            dashboard.setBackground(new Color(0, 0, 153));
            data.setBackground(new Color(0, 0,255));
            recognize.setBackground(new Color(0, 0, 153));
        });
        recognize.addActionListener(e -> {
            dashboard.setBackground(new Color(0, 0, 153));
            data.setBackground(new Color(0, 0, 153));
            recognize.setBackground(new Color(0, 0,255));
        });
    }
    private JButton settingButtons(String text, ImageIcon icon, int x, int y, int alto, int ancho) {
        JButton button = new JButton(text, icon);

        button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        button.setBackground(new Color(0, 0, 153));
        button.setForeground(new Color(202, 207, 210));
        button.setBounds(x, y, alto, ancho);

        this.add(button);
        return button;
    }
    private JButton eventMenu(ImageIcon icon, int x, int y, int alto, int ancho) {
        JButton button = new JButton(icon);

        button.setBounds(x, y, alto, ancho);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);

        this.add(button);
        return button;
    }
}
