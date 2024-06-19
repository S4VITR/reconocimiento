package com.reconocimiento.modules.custom;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class CustomTable {

    private static JScrollPane scrollPane;
    
    public static JScrollPane getPane(AbstractTable modelo, int alto, int ancho) {
        JTable table = getTableCustom(modelo);

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (hasFocus) {
                    // Deshabilitar el borde de enfoque
                    setBorder(null);
                }
                return component;
            }
        };
        // Aplicar el renderizador a todas las columnas de la tabla
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }
        // Personalizar el renderizador del encabezado para cambiar el color de fondo
        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(header.getWidth(), 40)); // Establecer la altura del encabezado
        header.setDefaultRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = new JLabel(value.toString());
                label.setOpaque(true);
                label.setBackground(new Color(0, 0, 153)); // Color de fondo personalizado
                label.setForeground(new Color(255, 255, 255)); // Color del texto
                label.setFont(new Font("sansserif", Font.BOLD, 12)); // Fuente personalizada
                label.setHorizontalAlignment(SwingUtilities.CENTER);
                label.setPreferredSize(new Dimension(label.getWidth(), 40));
                return label;
            }
        });
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(25, 60, alto, ancho);

        return scrollPane;
    }
    private static JTable getTableCustom(AbstractTable modelo) {
        JTable table = new JTable(modelo);

        Font customFont = new Font(Font.SANS_SERIF, Font.ITALIC, 12);
        table.setFont(customFont); // Cambiar la fuente de la tabla
        table.setForeground(new Color(0, 0, 0)); // Cambiar el color del texto
        table.setBackground(new Color(255, 255, 255));// Cambiar el color de fondo de la tabla
        table.setSelectionBackground(new Color(255, 255, 255)); // Cambiar el color de fondo de la selección
        table.setSelectionForeground(new Color(0, 0, 153));// Cambiar el color del texto seleccionado
        table.setShowGrid(false); // Mostrar la cuadrícula
        table.setRowHeight(50); // Cambiar la altura de las filas
        return table;
    }
}
