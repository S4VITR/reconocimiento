package com.reconocimiento.model.asistencia_alumno;

import java.sql.*;

public class AsistenciaAlumnoModel {
    
    private String id_Estudiante;
    private Date fecha_registro;
    private Time hora_entrada;
    private Time hora_salida;
    private boolean asistencia;

    public AsistenciaAlumnoModel() {}

    public AsistenciaAlumnoModel(String id_Estudiante, Date fecha_registro, Time hora_entrada, Time hora_salida, boolean asistencia) {

        this.id_Estudiante = id_Estudiante;
        this.fecha_registro = fecha_registro;
        this.hora_entrada = hora_entrada;
        this.hora_salida = hora_salida;
        this.asistencia = asistencia;
    }
    public String getIdEstudiante() {
        return id_Estudiante;
    }
    public void setIdEstudiante(String id_Estudiante) {
        this.id_Estudiante = id_Estudiante;
    }
    public Date getFechaRegistro() {
        return fecha_registro;
    }
    public void setFechaRegistro(Date fecha_registro) {
        this.fecha_registro = fecha_registro;
    }
    public Time getHoraEntrada() {
        return hora_entrada;
    }
    public void setHoraEntrada(Time hora_entrada) {
        this.hora_entrada = hora_entrada;
    }
    public Time getHoraSalida() {
        return hora_salida;
    }
    public void setHoraSalida(Time hora_salida) {
        this.hora_salida = hora_salida;
    }
    public boolean getAsistencia() {
        return asistencia;
    }
    public void setAsistencia(boolean asistencia) {
        this.asistencia = asistencia;
    }
}
