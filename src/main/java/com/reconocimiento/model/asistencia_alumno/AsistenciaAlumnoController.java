package com.reconocimiento.model.asistencia_alumno;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.reconocimiento.database.Conexion.conexion;

public class AsistenciaAlumnoController {

    public void insertarAsistenciaEstudianteEntrada(AsistenciaAlumnoModel asistenciaAlumnoModel) {

        String sql = "INSERT INTO asistencias_estudiantes(id_Estudiante, fecha_registro, hora_entrada, hora_salida, asistencia) VALUES (?,?,?,?,?)";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setString(1, asistenciaAlumnoModel.getIdEstudiante());
            preparedStatement.setDate(2, asistenciaAlumnoModel.getFechaRegistro());
            preparedStatement.setTime(3, asistenciaAlumnoModel.getHoraEntrada());
            preparedStatement.setTime(4, asistenciaAlumnoModel.getHoraSalida());
            preparedStatement.setBoolean(5, asistenciaAlumnoModel.getAsistencia());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void actualizarAsistenciaEstudianteEntrada(AsistenciaAlumnoModel asistenciaAlumnoModel) {

        String sql = "UPDATE asistencias_estudiantes SET hora_entrada = ?, asistencia = ? WHERE id_Estudiante = ? AND fecha_registro = ?";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setTime(1, asistenciaAlumnoModel.getHoraEntrada());
            preparedStatement.setBoolean(2, asistenciaAlumnoModel.getAsistencia());
            preparedStatement.setString(3, asistenciaAlumnoModel.getIdEstudiante());
            preparedStatement.setDate(4, asistenciaAlumnoModel.getFechaRegistro());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void actualizarAsistenciaEstudianteSalida(AsistenciaAlumnoModel asistenciaAlumnoModel) {

        String sql = "UPDATE asistencias_estudiantes SET hora_salida = ?, asistencia = ? WHERE id_Estudiante = ? AND fecha_registro = ?";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setTime(1, asistenciaAlumnoModel.getHoraSalida());
            preparedStatement.setBoolean(2, asistenciaAlumnoModel.getAsistencia());
            preparedStatement.setString(3, asistenciaAlumnoModel.getIdEstudiante());
            preparedStatement.setDate(4, asistenciaAlumnoModel.getFechaRegistro());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void insertarInasistenciaEstudiante(AsistenciaAlumnoModel asistenciaAlumnoModel) {

        String sql = "INSERT INTO asistencias_estudiantes(id_Estudiante, fecha_registro, hora_entrada, hora_salida, asistencia) VALUES (?,?,?,?,?)";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setString(1, asistenciaAlumnoModel.getIdEstudiante());
            preparedStatement.setDate(2, asistenciaAlumnoModel.getFechaRegistro());
            preparedStatement.setTime(3, asistenciaAlumnoModel.getHoraEntrada());
            preparedStatement.setTime(4, asistenciaAlumnoModel.getHoraSalida());
            preparedStatement.setBoolean(5, asistenciaAlumnoModel.getAsistencia());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<String[]> buscadorDeAsistencia(String filter) {

        String sql = "SELECT id_Estudiante, fecha_registro, hora_entrada, hora_salida, asistencia FROM asistencias_estudiantes WHERE id_Estudiante LIKE? OR fecha_registro LIKE? OR asistencia LIKE?";
        
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
    public static List<String[]> devolverDatosAsistencia() {

        String sql = "SELECT id_Estudiante, fecha_registro, hora_entrada, hora_salida, asistencia FROM asistencias_estudiantes";
        
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
