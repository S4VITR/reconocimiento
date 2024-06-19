package com.reconocimiento.model.profesor;

import static com.reconocimiento.database.Conexion.conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfesorController {
    
    public void insertarProfesor(ProfesorModel profesorModel) {

        String sql = "INSERT INTO profesores(id_Empleado, nombre_completo, descripcion_puesto) VALUES (?,?,?)";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setString(1, profesorModel.getId_Empleado());
            preparedStatement.setString(2, profesorModel.getNombre_completo());
            preparedStatement.setString(3, profesorModel.getDescripcion_puesto());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void eliminarProfesor(String id_Empleado) {
        
        String sql = "DELETE FROM profesores WHERE id_Empleado=?";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setString(1, id_Empleado);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<String[]> buscadorDeProfesor(String filter) {

        String sql = "SELECT id_Empleado, nombre_completo, descripcion_puesto FROM profesores WHERE id_Empleado LIKE ? OR nombre_completo LIKE ?";
        
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
    public static List<String[]> devolverDatosProfesor() {

        String sql = "SELECT id_Empleado, nombre_completo, descripcion_puesto FROM profesores";
        
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
