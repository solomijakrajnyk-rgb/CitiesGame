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
        SwingUtilities.invokeLater(AppLauncher::startApplication);
    }

    private static void startApplication() {
        try {
            CityNameNormalizer cityNameNormalizer =
                    new CityNameNormalizer();
            CityRepository cityRepository =
                    new CityRepository(cityNameNormalizer);

            showWelcomeWindow(
                    cityRepository,
                    cityNameNormalizer
            );
        } catch (IllegalStateException exception) {
            JOptionPane.showMessageDialog(
                    null,
                    "Не вдалося завантажити список міст.\n"
                            + "Програма буде завершена.",
                    "Критична помилка",
                    JOptionPane.ERROR_MESSAGE
            );

            System.exit(1);
        }
    }

    private static void showWelcomeWindow(
            CityRepository cityRepository,
            CityNameNormalizer cityNameNormalizer) {

        WelcomeWindow welcomeWindow =
                new WelcomeWindow(() ->
                        startNewGame(
                                cityRepository,
                                cityNameNormalizer
                        ));

        welcomeWindow.setVisible(true);
    }

    private static void startNewGame(
            CityRepository cityRepository,
            CityNameNormalizer cityNameNormalizer) {

        CityGame cityGame =
                new CityGame(
                        cityRepository,
                        cityNameNormalizer
                );

        GameWindow gameWindow =
                new GameWindow(
                        cityGame,
                        () -> showWelcomeWindow(
                                cityRepository,
                                cityNameNormalizer
                        )
                );

        gameWindow.setVisible(true);
    }
}
