package com.reconocimiento.components.materias;

import com.reconocimiento.model.asistencia_profesor.AsistenciasProfesoresModel;
import com.reconocimiento.model.materias.MateriaController;
import com.reconocimiento.modules.custom.AbstractTable;

import javax.swing.*;

import static com.reconocimiento.modules.custom.CustomTable.*;
import static com.reconocimiento.database.Conexion.conexion;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// import static com.reconocimiento.model.materias.MateriasController.*;

public class TablaMaterias extends JPanel implements MateriaController {

    private AbstractTable abstractTable;
    private JScrollPane scrollPane;
    private JLabel title;

    public TablaMaterias() {
        this.setLayout(null);
        this.setBackground(new Color(255, 255, 255));

        title = new JLabel("Materias");
        title.setFont(new Font("Font.SANS_SERIF", Font.BOLD, 16));
        title.setForeground(new Color(0, 0, 153));
        title.setBounds(30, 25, 200, 20);

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            UIManager.put("nimbusBase", new Color(255, 255, 255));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
        String[] columnas = {"Nombre de las Materias"};
        // abstractTable = new AbstractTable(devolverDatosDeLasMaterias(), columnas);
        try {
            abstractTable = new AbstractTable(devolverDatos(), columnas);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        scrollPane = getPane(abstractTable, 300, 270);

        this.setBounds(630, 0, 350, 350);
        this.setOpaque(false);
        this.add(scrollPane);
        this.add(title);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
    }
    @Override
    public List<String[]> devolverDatos() throws SQLException {

        String sql = "SELECT nombre_materia FROM materias";
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
        } 
        return data;
    }
    @Override
    public void insertarAsistencia(Object object) throws SQLException {

        object = new AsistenciasProfesoresModel();

        if (object instanceof AsistenciasProfesoresModel) {
            AsistenciasProfesoresModel asistenciasProfesoresModel = (AsistenciasProfesoresModel) object;

            String sql = "INSERT INTO asistencias_profesores(id_Profesor, fecha_registro, hora_entrada, hora_salida, asistencia) VALUES (?,?,?,?,?)";

            try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
                preparedStatement.setString(1, asistenciasProfesoresModel.getIdProfesor());
                preparedStatement.setDate(2, asistenciasProfesoresModel.getFechaRegistro());
                preparedStatement.setTime(3, asistenciasProfesoresModel.getHoraEntrada());
                preparedStatement.setTime(4, asistenciasProfesoresModel.getHoraSalida());
                preparedStatement.setBoolean(5, asistenciasProfesoresModel.getAsistencia());
                preparedStatement.executeUpdate();
            }
        }
    }
    @Override
    public <T> void actualizarAsistencia(T object) throws SQLException {

        if (object instanceof AsistenciasProfesoresModel) {
            AsistenciasProfesoresModel asistenciasProfesoresModel = (AsistenciasProfesoresModel) object;

            String sql = "INSERT INTO asistencias_profesores(id_Profesor, fecha_registro, hora_entrada, hora_salida, asistencia) VALUES (?,?,?,?,?)";

            try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
                preparedStatement.setString(1, asistenciasProfesoresModel.getIdProfesor());
                preparedStatement.setDate(2, asistenciasProfesoresModel.getFechaRegistro());
                preparedStatement.setTime(3, asistenciasProfesoresModel.getHoraEntrada());
                preparedStatement.setTime(4, asistenciasProfesoresModel.getHoraSalida());
                preparedStatement.setBoolean(5, asistenciasProfesoresModel.getAsistencia());
                preparedStatement.executeUpdate();

            }
        }
    }
}
