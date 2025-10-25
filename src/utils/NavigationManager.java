/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import javax.swing.JFrame;

/**
 *
 * @author Enrique Zegarra
 */
public class NavigationManager {

    public static void navigateTo(JFrame current, JFrame next) {
        next.setVisible(true);
        current.dispose();
    }
}
