package com.reconocimiento.template.custom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CustomComponents {

    public static JLabel getCustomLabel(String text, int x, int y, int alto, int ancho) {
        JLabel label = new JLabel();

        label.setText(text);
        label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        label.setForeground(new Color(0, 0, 0));
        label.setBounds(x, y, alto, ancho);

        return label;
    }
    public static JTextField getCustomField(int x, int y, int alto, int ancho) {
        JTextField textField = new JTextField();

        textField.setText("Introduce tus datos");
        textField.setBorder(BorderFactory.createEmptyBorder());
        textField.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 12));
        textField.setForeground(new Color(202, 207, 210));
        textField.setBounds(x, y, alto, ancho);

        textField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals("Introduce tus datos")) {
                    textField.setText("");
                    textField.setForeground(new Color(0, 0, 0));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText("Introduce tus datos");
                    textField.setForeground(new Color(202, 207, 210));
                }
            }
        });
        return textField;
    }
}
