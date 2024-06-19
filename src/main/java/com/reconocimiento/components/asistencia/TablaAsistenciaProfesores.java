package com.reconocimiento.components.asistencia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.reconocimiento.database.Conexion.conexion;

import javax.swing.event.DocumentListener;

import com.reconocimiento.modules.custom.AbstractTable;
import com.reconocimiento.reconocedor.ReconocimientoFaciallProfesor;

import javax.swing.*;
import javax.swing.event.DocumentEvent;

import static com.reconocimiento.modules.custom.CustomTable.*;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;

public class TablaAsistenciaProfesores extends JPanel {

    private AbstractTable abstractTable;
    private JScrollPane scrollPane;
    private JTextField textField;
    private JLabel title;

    public TablaAsistenciaProfesores() {
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

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            UIManager.put("nimbusBase", new Color(255, 255, 255));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //!Inicializar el método devolverDatosDelHorario() para la inserción de inasistencia
        try {
            new ReconocimientoFaciallProfesor().devolverDatosDelHorario();   
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String[] columnas = {"ID del Profesor", "Fecha de Registro", "Hora de Entrada", "Hora de Salida", "Asistencia"};
        abstractTable = new AbstractTable(buscadorDeAsistencia(""), columnas);

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
                List<String[]> data = buscadorDeAsistencia(filter);

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

                    List<String[]> data = devolverDatosAsistencia();
                    abstractTable.setData(data);
                }
            }
        });

        return textField;
    }
    private static List<String[]> buscadorDeAsistencia(String filter) {

        String sql = "SELECT id_Profesor, fecha_registro, hora_entrada, hora_salida, asistencia FROM asistencias_profesores WHERE id_Profesor LIKE? OR fecha_registro LIKE? OR asistencia LIKE?";
        
        ResultSet resultSet;
        List<String[]> data = new ArrayList<>();

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + filter + "%");
            preparedStatement.setString(2, "%" + filter + "%");
            preparedStatement.setString(3, "%" + filter + "%");
            
            resultSet = preparedStatement.executeQuery();


            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int cantidadColumnas = resultSetMetaData.getColumnCount();

            while (resultSet.next()) {
                String[] fila = new String[cantidadColumnas];
                for (int i = 1; i <= cantidadColumnas; i++) {
                    fila[i - 1] = resultSet.getString(i);
                }
                data.add(fila);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return data;
    }
    private static List<String[]> devolverDatosAsistencia() {

        String sql = "SELECT id_Profesor, fecha_registro, hora_entrada, hora_salida, asistencia FROM asistencias_profesores";
        
        ResultSet resultSet;
        List<String[]> data = new ArrayList<>();

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            resultSet = preparedStatement.executeQuery();

            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int cantidadColumnas = resultSetMetaData.getColumnCount();

            while (resultSet.next()) {
                String[] fila = new String[cantidadColumnas];
                for (int i = 1; i <= cantidadColumnas; i++) {
                    fila[i - 1] = resultSet.getString(i);
                }
                data.add(fila);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return data;
    }
}
