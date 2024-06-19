package com.reconocimiento.model.interfaces;

import java.sql.*;
import java.util.*;

public interface AsistenciaInterfaz {
    
    <T> void insertarInasistenciaEntrada(T object) throws SQLException;

    <T> void actualizarAsistenciaEntrada(T object) throws SQLException;

    <T> void actualizarAsistenciaSalida(T object) throws SQLException;

    List<String[]> mostrarDatosAsistencias() throws SQLException;

    List<String[]> buscadorDeAsistencias(String filter) throws SQLException;
}
