package com.reconocimiento.components.asistencia;

import java.util.List;

import javax.swing.event.DocumentListener;

import com.reconocimiento.global.reconocedor.ReconocimientoFacialProfesor;
import com.reconocimiento.template.custom.AbstractTable;

import javax.swing.*;
import javax.swing.event.DocumentEvent;

import static com.reconocimiento.template.custom.CustomTable.*;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;

public class TablaAsistenciaProfesores extends JPanel {

    private AbstractTable abstractTable;
    private JScrollPane scrollPane;
    private JTextField textField;
    private JLabel title;

    private final ControladorTablaAsistenciaProfesores controlador = new ControladorTablaAsistenciaProfesores();

    public TablaAsistenciaProfesores() throws SQLException, Exception {
        this.setLayout(null);
        this.setBackground(new Color(255, 255, 255));

        ImageIcon s = new ImageIcon("src\\main\\java\\com\\reconocimiento\\resource\\icons\\busqueda.png");
        JLabel icono = new JLabel(s);
        icono.setBounds(765, 25, 20, 20);

        title = new JLabel("Gestión de Asistencias de Profesores");
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        title.setForeground(new Color(0, 0, 153));
        title.setBounds(25, 25, 350, 20);

        textField = new JTextField("Search...");
        textField.setBorder(BorderFactory.createEmptyBorder());
        textField.setForeground(new Color(128, 128, 128));
        textField.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 14));
        textField.setBounds(795, 20, 150, 30);

        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        UIManager.put("nimbusBase", new Color(255, 255, 255));
       

        //!Inicializar el método devolverDatosDelHorario() para la inserción de inasistencia
        new ReconocimientoFacialProfesor().validacionDatosDelHorario();


        String[] columnas = {"ID del Profesor", "Fecha de Registro", "Hora de Entrada", "Hora de Salida", "Asistencia"};
        abstractTable = new AbstractTable(controlador.buscadorDeAsistencias(""), columnas);
    
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
                try {
                    updateTable();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                try {
                    updateTable();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                try {
                    updateTable();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            private void updateTable() throws SQLException {
                String filter = textField.getText();
                List<String[]> data = controlador.buscadorDeAsistencias(filter);

                abstractTable.setData(data);
            }
        });

        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals("Search...")) {
                    textField.setForeground(new Color(0, 0, 0));
                    textField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(new Color(128, 128, 128));
                    textField.setText("Search...");

                    List<String[]> data;
                    try {
                        data = controlador.mostrarDatosAsistencias();
                        abstractTable.setData(data);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        return textField;
    }
}
