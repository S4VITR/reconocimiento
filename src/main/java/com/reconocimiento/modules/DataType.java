package com.reconocimiento.modules;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DataType {
    
    //? Método para obtener la fecha actual
    public static Date devolverFechaActual() {
        LocalDate fechaActual = LocalDate.now();
        //String fecha = fechaActual.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return Date.valueOf(fechaActual);
    }
    //? Método para obtener la hora actual
    public static Time devolverHoraActual() {
        LocalTime horaActual = LocalTime.now();
        //String hora = horaActual.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        return Time.valueOf(horaActual);
    }
    //? Método para generar un ID Dinamico para cada registro
    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        //String uuidString = uuid.toString();
        return uuid.toString();
    }
    //? Método para devolver el dia de la semana
    public static String devolverDiaDeLaSemana() {
        
        LocalDate fecha_actual = LocalDate.now();
        String nombre_dia = fecha_actual.getDayOfWeek().name();

        Map<String, String> dia_semana = new HashMap<>();
        dia_semana.put("MONDAY", "Lunes");
        dia_semana.put("TUESDAY", "Martes");
        dia_semana.put("WEDNESDAY", "Miercoles");
        dia_semana.put("THURSDAY", "Jueves");
        dia_semana.put("FRIDAY", "Viernes");
        dia_semana.put("SATURDAY", "Sabado");

        String dia_hoy = dia_semana.get(nombre_dia);

        return dia_hoy;
    }
}
