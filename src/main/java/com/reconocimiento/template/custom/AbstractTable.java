package com.reconocimiento.template.custom;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class AbstractTable extends AbstractTableModel {
    
    private List<String[]> data;
    private String[] columnNames;

    public AbstractTable(List<String[]> data, String[] columnNames) {
        this.data = data;
        this.columnNames = columnNames;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex)[columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public void setData(List<String[]> data) {
        this.data = data;
        fireTableDataChanged();
    }
}
