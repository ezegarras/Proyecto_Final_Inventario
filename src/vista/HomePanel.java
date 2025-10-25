/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import utils.StyleManager;
import java.awt.Color;

/**
 *
 * @author Enrique Zegarra
 */
public class HomePanel extends JPanel {

    public HomePanel() {
        // Usamos un BorderLayout simple
        super(new BorderLayout());
        setBackground(Color.WHITE); // Fondo blanco
        
        JLabel lblBienvenida = new JLabel("Bienvenido al Sistema de Gestión");
        lblBienvenida.setFont(StyleManager.FONT_TITULO);
        lblBienvenida.setForeground(StyleManager.TEXTO_NEGRO);
        lblBienvenida.setHorizontalAlignment(SwingConstants.CENTER); // Centrar texto
        
        add(lblBienvenida, BorderLayout.CENTER);
    }
}
