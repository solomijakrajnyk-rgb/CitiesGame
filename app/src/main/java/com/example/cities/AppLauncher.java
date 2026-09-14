package com.example.cities;

import com.example.cities.ui.WelcomeWindow;

import javax.swing.SwingUtilities;

public class AppLauncher {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WelcomeWindow welcomeWindow = new WelcomeWindow();
            welcomeWindow.setVisible(true);
        });
    }
}
