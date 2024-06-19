package com.reconocimiento.model.materias;

import java.sql.SQLException;
import java.util.List;

public interface MateriaController {

    List<String[]> devolverDatos() throws SQLException;

    void insertarAsistencia(Object object) throws SQLException;

    <T> void actualizarAsistencia(T object) throws SQLException;
}
