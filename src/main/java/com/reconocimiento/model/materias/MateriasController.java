package com.reconocimiento.model.materias;

import static com.reconocimiento.database.Conexion.conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MateriasController {
    
    public static List<String[]> devolverDatosDeLasMaterias() {

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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return data;
    }
}
