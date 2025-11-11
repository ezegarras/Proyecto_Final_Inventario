/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package utils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Enrique Zegarra
 */

/**
 * Servicio central (Singleton inyectado) que maneja los Observadores..
 */
public class DataUpdateNotifier {

    private final List<DataUpdateListener> listeners = new ArrayList<>();

    public void addListener(DataUpdateListener listener) {
        listeners.add(listener);
    }

    public void removeListener(DataUpdateListener listener) {
        listeners.remove(listener);
    }

    /**
     * Llama a onDataChanged() en todos los suscriptores.
     */
    public void notifyListeners() {
        // Usamos .forEach() de Java 8+
        listeners.forEach(DataUpdateListener::onDataChanged);
    }
}
