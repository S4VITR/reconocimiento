package com.reconocimiento.components.inicio;

import java.util.List;

import javax.swing.event.DocumentListener;

import com.reconocimiento.template.custom.AbstractTable;

import javax.swing.*;
import javax.swing.event.DocumentEvent;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import static com.reconocimiento.model.profesor.ProfesorController.*;
import static com.reconocimiento.template.custom.CustomTable.*;

public class TablaProfesores extends JPanel {

    private AbstractTable abstractTable;
    private JScrollPane scrollPane;
    private JTextField textField;
    private JLabel title;

    public TablaProfesores() {
        this.setLayout(null);
        this.setBackground(new Color(255, 255, 255));

        ImageIcon s = new ImageIcon("src\\main\\java\\com\\reconocimiento\\resource\\icons\\busqueda.png");
        JLabel icono = new JLabel(s);
        icono.setBounds(765, 25, 20, 20);

        title = new JLabel("Profesores");
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        title.setForeground(new Color(0, 0, 153));
        title.setBounds(25, 25, 200, 20);

        textField = new JTextField("Search...");
        textField.setBorder(BorderFactory.createEmptyBorder());
        textField.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 14));
        textField.setBounds(795, 20, 150, 30);

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            UIManager.put("nimbusBase", new Color(255, 255, 255));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String[] columnas = {"ID del Profesor", "Nombre Completo", "Descripcion de Puesto"};
        abstractTable = new AbstractTable(buscadorDeProfesor(""), columnas);

        getDocumentListener();

        scrollPane = getPane(abstractTable, 930, 230);

        this.setBounds(0, 360, 980, 320);
        this.setOpaque(false);
        this.add(scrollPane);
        this.add(textField);
        this.add(title);
        this.add(icono);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
    }
    private JTextField getDocumentListener() {

        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateTable();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                updateTable();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                updateTable();
            }
            private void updateTable() {
                String filter = textField.getText();
                List<String[]> data = buscadorDeProfesor(filter);

                abstractTable.setData(data);
            }
        });

        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals("Search...")) {
                    textField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText("Search...");

                    List<String[]> data = devolverDatosProfesor();
                    abstractTable.setData(data);
                }
            }
        });

        return textField;
    }
}
