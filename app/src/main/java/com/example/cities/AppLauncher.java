package com.example.cities;

import com.example.cities.game.CityGame;
import com.example.cities.game.CityNameNormalizer;
import com.example.cities.game.CityRepository;
import com.example.cities.ui.GameWindow;
import com.example.cities.ui.WelcomeWindow;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class AppLauncher {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AppLauncher::showWelcomeWindow);
    }

    private static void showWelcomeWindow() {
        WelcomeWindow welcomeWindow =
                new WelcomeWindow(AppLauncher::startNewGame);

        welcomeWindow.setVisible(true);
    }

    private static void startNewGame() {
        try {
            CityNameNormalizer cityNameNormalizer =
                    new CityNameNormalizer();
            CityRepository cityRepository =
                    new CityRepository(cityNameNormalizer);
            CityGame cityGame =
                    new CityGame(cityRepository);

            GameWindow gameWindow =
                    new GameWindow(
                            cityGame,
                            AppLauncher::showWelcomeWindow
                    );

            gameWindow.setVisible(true);
        } catch (IllegalStateException exception) {
            JOptionPane.showMessageDialog(
                    null,
                    "Не вдалося завантажити список міст.\n"
                            + "Спробуйте перезапустити програму.",
                    "Помилка запуску",
                    JOptionPane.ERROR_MESSAGE
            );

            showWelcomeWindow();
        }
    }
}
