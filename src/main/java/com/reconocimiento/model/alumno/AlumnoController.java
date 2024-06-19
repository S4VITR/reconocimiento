package com.reconocimiento.model.alumno;

import static com.reconocimiento.database.Conexion.conexion;
import java.sql.*;
import java.util.*;

public class AlumnoController {
    public void insertarAlumno(AlumnoModel alumnoModel) {
        
        String sql = "INSERT INTO alumnos(id_Estudiante, matricula, nombre, area) VALUES (?,?,?,?)";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setString(1, alumnoModel.getId_Estudiante());
            preparedStatement.setString(2, alumnoModel.getMatricula());
            preparedStatement.setString(3, alumnoModel.getNombre());
            preparedStatement.setString(4, alumnoModel.getArea());
            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void eliminarAlumno(String id_Estudiante) {

        String sql = "DELETE FROM alumnos WHERE id_Estudiante = ?";
        
        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setString(1, id_Estudiante);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<String[]> buscadorDeDatosAlumno(String filter) {

        String sql = "SELECT id_Estudiante, matricula, nombre, area FROM alumnos WHERE matricula LIKE ? OR nombre LIKE ?";

        ResultSet resultSet;
        List<String[]> data = new ArrayList<>();

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + filter + "%");
            preparedStatement.setString(2, "%" + filter + "%");
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
    public static List<String[]> devolverDeDatosAlumnos() {

        String sql = "SELECT id_Estudiante, matricula, nombre, area FROM alumnos";

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
