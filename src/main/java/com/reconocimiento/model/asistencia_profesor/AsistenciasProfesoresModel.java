package com.reconocimiento.model.asistencia_profesor;

import java.sql.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor

public class AsistenciasProfesoresModel {

    private String id_Profesor;
    private Date fecha_registro;
    private Time hora_entrada;
    private Time hora_salida;
    private boolean asistencia;

    public String getIdProfesor() {
        return id_Profesor;
    }
    public void setIdProfesor(String id_Profesor) {
        this.id_Profesor = id_Profesor;
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
    public Boolean getAsistencia() {
        return asistencia;
    }
    public void setAsistencia(boolean asistencia) {
        this.asistencia = asistencia;
    }
}
