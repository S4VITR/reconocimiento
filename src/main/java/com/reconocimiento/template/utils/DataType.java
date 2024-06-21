package com.reconocimiento.template.utils;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DataType {

    /**
     * * Devuelve la fecha actual como un objeto de tipo `Date`. Este método obtiene la fecha actual utilizando la clase `LocalDate` y la convierte a un objeto de tipo `Date`.
     * 
     * @return La fecha actual como un objeto `Date`.
     */
    public static Date devolverFechaActual() {
        LocalDate fechaActual = LocalDate.now();
    
        return Date.valueOf(fechaActual);
    }
    
    /**
     * * Devuelve la hora actual como un objeto de tipo `Time`. Este método obtiene la hora actual utilizando la clase `LocalTime` y la convierte a un objeto de tipo `Time`.
     * 
     * @return La hora actual como un objeto `Time`.
     */
    public static Time devolverHoraActual() {
        LocalTime horaActual = LocalTime.now();
        
        return Time.valueOf(horaActual);
    }
    
    /**
     * * Genera y devuelve un UUID (Universally Unique Identifier) en forma de cadena. Este método utiliza la clase `UUID` para generar un identificador único aleatorio y lo devuelve como una cadena.
     * 
     * @return Un UUID único en formato de cadena.
     */
    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
   
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

        String dia_hoy = dia_semana.get(nombre_dia);

        return dia_hoy;
    }
}
