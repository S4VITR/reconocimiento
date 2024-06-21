package com.reconocimiento.model.controller;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.reconocimiento.model.asistencia_alumno.AsistenciaAlumnoModel;
import com.reconocimiento.model.asistencia_profesor.AsistenciasProfesoresModel;
import com.reconocimiento.model.interfaces.AdaptadorAsistencias;

import static com.reconocimiento.database.Conexion.conexion;

/**
 * * La clase ControladorAsistencias extiende de la Clase AdaptadorAsistencias
 * {@link com.reconocimiento.model.interfaces#AdaptadorAsistencias}
 * 
 * @author Eduardo Gómez
 */
public class ControladorAsistencias extends AdaptadorAsistencias {

    /**
     * * El método 'insertarInasistenciaEntrada' inserta registros de asistencia de profesores o estudiantes en sus respectivas tablas de bases de datos según el tipo de objeto pasado.
     * 
     * @param object El parámetro 'object' en el método 'insertarInasistenciaEntrada' es un tipo genérico '<T>''. Se
     *               utiliza para recibir un objeto de cualquier tipo y luego, según el tipo de objeto, el método
     *               realiza la inserción de registros de asistencia para profesores o estudiantes.
     * @throws SQLException Devuelve una excepción durante la inserción de registros en la base de datos.
     */
    @Override
    public <T> void insertarInasistenciaEntrada(T object) throws SQLException {

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
        } else if (object instanceof AsistenciaAlumnoModel) {
            AsistenciaAlumnoModel asistenciaAlumnoModel = (AsistenciaAlumnoModel) object;

            String sql = "INSERT INTO asistencias_estudiantes(id_Estudiante, fecha_registro, hora_entrada, hora_salida, asistencia) VALUES (?,?,?,?,?)";

            try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
                preparedStatement.setString(1, asistenciaAlumnoModel.getIdEstudiante());
                preparedStatement.setDate(2, asistenciaAlumnoModel.getFechaRegistro());
                preparedStatement.setTime(3, asistenciaAlumnoModel.getHoraEntrada());
                preparedStatement.setTime(4, asistenciaAlumnoModel.getHoraSalida());
                preparedStatement.setBoolean(5, asistenciaAlumnoModel.getAsistencia());
                preparedStatement.executeUpdate();
            }
        } else {
            System.err.println("Ocurrio un error, al momento de realizar Casting de la instancia del Objeto.");
        }
    }

    /**
     * * La función 'actualizarAsistenciaEntrada' actualiza la entrada de asistencia de un profesor o un estudiante en la base de datos según el tipo de objeto proporcionado.
     * 
     * @param object El método 'actualizarAsistenciaEntrada' es un método genérico que actualiza la entrada de
     *               asistencia de un profesor o de un estudiante según el tipo de objeto que se le pasa.
     * @throws SQLException Devuelve una excepción durante la actualización de registros en la base de datos.
     */
    @Override
    public <T> void actualizarAsistenciaEntrada(T object) throws SQLException {
        
        if (object instanceof AsistenciasProfesoresModel) {
            AsistenciasProfesoresModel asistenciasProfesoresModel = (AsistenciasProfesoresModel) object;

            String sql = "UPDATE asistencias_profesores SET hora_entrada = ?, asistencia = ? WHERE id_Profesor = ? AND fecha_registro = ?";

            try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
                preparedStatement.setTime(1, asistenciasProfesoresModel.getHoraEntrada());
                preparedStatement.setBoolean(2, asistenciasProfesoresModel.getAsistencia());
                preparedStatement.setString(3, asistenciasProfesoresModel.getIdProfesor());
                preparedStatement.setDate(4, asistenciasProfesoresModel.getFechaRegistro());
                preparedStatement.executeUpdate();
            }
        } else if (object instanceof AsistenciaAlumnoModel) {
            AsistenciaAlumnoModel asistenciaAlumnoModel = (AsistenciaAlumnoModel) object;

            String sql = "UPDATE asistencias_estudiantes SET hora_entrada = ?, asistencia = ? WHERE id_Estudiante = ? AND fecha_registro = ?";

            try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
                preparedStatement.setTime(1, asistenciaAlumnoModel.getHoraEntrada());
                preparedStatement.setBoolean(2, asistenciaAlumnoModel.getAsistencia());
                preparedStatement.setString(3, asistenciaAlumnoModel.getIdEstudiante());
                preparedStatement.setDate(4, asistenciaAlumnoModel.getFechaRegistro());
                preparedStatement.executeUpdate();
            }
        } else {
            System.err.println("Ocurrio un error, al momento de realizar Casting de la instancia del Objeto.");
        }
    }

    /**
     * * La función 'actualizarAsistenciaSalida' actualiza la salida de asistencia de un profesor o un estudiante en la base de datos según el tipo de objeto proporcionado.
     * 
     * @param object El método 'actualizarAsistenciaSalida' es un método genérico que actualiza la salida de
     *               asistencia de un profesor o de un estudiante según el tipo de objeto que se le pasa.
     * @throws SQLException Devuelve una excepción durante la actualización de registros en la base de datos.
     */
    @Override
    public <T> void actualizarAsistenciaSalida(T object) throws SQLException {
        
        if (object instanceof AsistenciasProfesoresModel) {
            AsistenciasProfesoresModel asistenciasProfesoresModel = (AsistenciasProfesoresModel) object;

            String sql = "UPDATE asistencias_profesores SET hora_salida = ?, asistencia = ? WHERE id_Profesor = ? AND fecha_registro = ?";

            try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
                preparedStatement.setTime(1, asistenciasProfesoresModel.getHoraSalida());
                preparedStatement.setBoolean(2, asistenciasProfesoresModel.getAsistencia());
                preparedStatement.setString(3, asistenciasProfesoresModel.getIdProfesor());
                preparedStatement.setDate(4, asistenciasProfesoresModel.getFechaRegistro());
                preparedStatement.executeUpdate();
            }
        } else if (object instanceof AsistenciaAlumnoModel) {
            AsistenciaAlumnoModel asistenciaAlumnoModel = (AsistenciaAlumnoModel) object;

            String sql = "UPDATE asistencias_estudiantes SET hora_salida = ?, asistencia = ? WHERE id_Estudiante = ? AND fecha_registro = ?";

            try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
                preparedStatement.setTime(1, asistenciaAlumnoModel.getHoraSalida());
                preparedStatement.setBoolean(2, asistenciaAlumnoModel.getAsistencia());
                preparedStatement.setString(3, asistenciaAlumnoModel.getIdEstudiante());
                preparedStatement.setDate(4, asistenciaAlumnoModel.getFechaRegistro());
                preparedStatement.executeUpdate();
            }
        } else {
            System.err.println("Ocurrio un error, al momento de realizar Casting de la instancia del Objeto.");
        }
    }
}
