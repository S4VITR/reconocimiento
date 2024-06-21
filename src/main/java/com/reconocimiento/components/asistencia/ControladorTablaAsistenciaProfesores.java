package com.reconocimiento.components.asistencia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.reconocimiento.model.interfaces.AdaptadorAsistencias;

import static com.reconocimiento.database.Conexion.conexion;

/**
 *  * Clase `ControladorTablaAsistenciaProfesores` que extiende de `AdaptadorAsistencias`.
 * 
 * Esta clase actúa como un controlador para la gestión de la tabla de asistencias de los profesores.
 * Hereda los métodos y propiedades de `AdaptadorAsistencias` y puede sobrescribirlos o añadir nuevos comportamientos específicos.
 *
 * @author Eduardo Gómez
 */
public class ControladorTablaAsistenciaProfesores extends AdaptadorAsistencias {

    /**
     * * Busca y filtra los datos de asistencia de los profesores según el criterio especificado. Este método ejecuta una consulta SQL para buscar registros de asistencia en la base de datos que coincidan con el filtro proporcionado. Los resultados se devuelven como una lista de arreglos de cadenas.
     * 
     * @param filter El criterio de búsqueda para filtrar los datos de asistencia.
     * @return Una lista de arreglos de cadenas, donde cada arreglo representa una fila de los datos de asistencia que coinciden con el filtro.
     * @throws SQLException si ocurre un error durante la ejecución de la consulta SQL.
     */
    @Override
    public List<String[]> buscadorDeAsistencias(String filter) throws SQLException {

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
        }
        return data;
    }

    /**
     * * Recupera y muestra todos los datos de asistencia de los profesores desde la base de datos. Este método ejecuta una consulta SQL para obtener todos los registros de asistencia de los profesores en la base de datos. Los resultados se devuelven como una lista de arreglos de cadenas.
     * 
     * @return Una lista de arreglos de cadenas, donde cada arreglo representa una fila de los datos de asistencia.
     * @throws SQLException si ocurre un error durante la ejecución de la consulta SQL.
     */
    @Override
    public List<String[]> mostrarDatosAsistencias() throws SQLException {

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
        }
        return data;
    }
}
