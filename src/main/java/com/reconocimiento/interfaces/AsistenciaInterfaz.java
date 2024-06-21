package com.reconocimiento.interfaces;

import java.sql.*;
import java.util.*;

/**
 * * Interfaz que define los métodos relacionados con la gestión de asistencias. Esta interfaz establece un contrato para las operaciones de gestión de asistencias, incluyendo inserción, actualización, y búsqueda de registros de asistencia.
 * 
 * @author Eduardo G´ómez
 */
public interface AsistenciaInterfaz {
    
    /**
     * * Inserta un registro de inasistencia en la base de datos.
     * 
     * @param <T> El tipo del objeto que representa la inasistencia. Generalmente, este será un modelo de datos específico.
     * @param object El objeto que contiene los detalles de la inasistencia que se va a insertar.
     * @throws SQLException si ocurre un error durante la ejecución de la inserción.
     */
    <T> void insertarInasistenciaEntrada(T object) throws SQLException;

    /**
     * * Actualiza el registro de asistencia de entrada de un profesor en la base de datos.
     * 
     * @param <T> El tipo del objeto que representa la asistencia. Generalmente, este será un modelo de datos específico.
     * @param object El objeto que contiene los detalles de la asistencia que se va a actualizar.
     * @throws SQLException si ocurre un error durante la ejecución de la actualización.
     */
    <T> void actualizarAsistenciaEntrada(T object) throws SQLException;

    /**
     * * Actualiza el registro de asistencia de salida de un profesor en la base de datos.
     * 
     * @param <T> El tipo del objeto que representa la asistencia. Generalmente, este será un modelo de datos específico.
     * @param object El objeto que contiene los detalles de la asistencia que se va a actualizar.
     * @throws SQLException si ocurre un error durante la ejecución de la actualización.
     */
    <T> void actualizarAsistenciaSalida(T object) throws SQLException;

    /**
     * * Recupera y muestra los datos de asistencia de los profesores desde la base de datos.
     * 
     * @return Una lista de arreglos de cadenas, donde cada arreglo representa una fila de los datos de asistencia.
     * @throws SQLException si ocurre un error durante la ejecución de la consulta SQL.
     */
    List<String[]> mostrarDatosAsistencias() throws SQLException;

    /**
     * * Busca y filtra los datos de asistencia de los profesores según el criterio especificado.
     * 
     * @param filter El criterio de búsqueda para filtrar los datos de asistencia.
     * @return Una lista de arreglos de cadenas, donde cada arreglo representa una fila de los datos de asistencia que coinciden con el filtro.
     * @throws SQLException si ocurre un error durante la ejecución de la consulta SQL.
     */
    List<String[]> buscadorDeAsistencias(String filter) throws SQLException;
}
