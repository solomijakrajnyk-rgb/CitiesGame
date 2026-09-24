package com.example.cities;

import com.example.cities.game.CityGame;
import com.example.cities.game.CityRepository;
import com.example.cities.ui.GameWindow;
import com.example.cities.ui.WelcomeWindow;

import javax.swing.SwingUtilities;

public class AppLauncher {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CityRepository cityRepository = new CityRepository();
            CityGame cityGame = new CityGame(cityRepository);
            GameWindow gameWindow = new GameWindow(cityGame);
            WelcomeWindow welcomeWindow = new WelcomeWindow(gameWindow);

            welcomeWindow.setVisible(true);
        });
    }
}
