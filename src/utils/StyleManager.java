/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.*;

/**
 *
 * @author Enrique Zegarra
 */
public class StyleManager {
    
// --- COLORES ---
    public static final Color AZUL_PRINCIPAL = new Color(25, 118, 210);
    public static final Color GRIS_CLARO = new Color(245, 245, 245);
    public static final Color TEXTO_NEGRO = new Color(51, 51, 51);
    
    // --- FUENTES ---
    public static final Font FONT_TITULO = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font FONT_BOTON = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONT_LABEL = new Font("Segoe UI", Font.PLAIN, 14);

    // --- MÉTODOS FACTORY ---
    
    public static JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        button.setFont(FONT_BOTON);
        button.setBackground(AZUL_PRINCIPAL);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        return button;
    }

    public static JTextField createStyledTextField() {
        JTextField field = new JTextField(20);
        field.setFont(FONT_LABEL);
        field.setForeground(TEXTO_NEGRO);
        // Borde con padding
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        return field;
    }
    
    public static JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField(20);
        field.setFont(FONT_LABEL);
        field.setForeground(TEXTO_NEGRO);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        return field;
    }
    
    public static JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_LABEL);
        label.setForeground(TEXTO_NEGRO);
        return label;
    }
}