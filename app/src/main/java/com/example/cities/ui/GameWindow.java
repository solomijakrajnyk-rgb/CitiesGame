package com.example.cities.ui;

import com.example.cities.game.CityGame;
import com.example.cities.game.MoveResult;
import com.example.cities.game.MoveStatus;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

public class GameWindow extends JFrame {

    private final CityGame game;
    private final Runnable newGameAction;
    private final JLabel computerAnswerLabel;
    private final JLabel scoreLabel;
    private final JTextField cityInput;

    public GameWindow(
            CityGame game,
            Runnable newGameAction
    ) {
        this.game = game;
        this.newGameAction = newGameAction;

        setTitle("Міста");
        setIconImage(loadIcon());
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JLabel titleLabel = new JLabel("Гра «Міста»", JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));

        computerAnswerLabel = new JLabel(
                "Комп'ютер: —",
                JLabel.CENTER
        );
        computerAnswerLabel.setFont(
                new Font("SansSerif", Font.PLAIN, 16)
        );

        scoreLabel = new JLabel(
                "Рахунок: 0 : 0",
                JLabel.CENTER
        );
        scoreLabel.setFont(
                new Font("SansSerif", Font.BOLD, 14)
        );

        JPanel topPanel = new JPanel(
                new GridLayout(3, 1, 5, 5)
        );
        topPanel.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 10, 15)
        );
        topPanel.add(titleLabel);
        topPanel.add(computerAnswerLabel);
        topPanel.add(scoreLabel);

        JLabel inputLabel = new JLabel("Введіть назву міста:");
        inputLabel.setFont(
                new Font("SansSerif", Font.PLAIN, 14)
        );

        cityInput = new JTextField();
        cityInput.setPreferredSize(new Dimension(250, 30));

        JButton moveButton = new JButton("Зробити хід");
        moveButton.setPreferredSize(
                new Dimension(130, 30)
        );
        moveButton.addActionListener(event -> makeMove());

        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );
        inputPanel.add(inputLabel);
        inputPanel.add(cityInput);
        inputPanel.add(moveButton);

        add(topPanel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);

        cityInput.addActionListener(event -> makeMove());
    }

    private void makeMove() {
        String playerCity = cityInput.getText();

        MoveResult result = game.processPlayerMove(playerCity);

        switch (result.status()) {
            case VALID:
                computerAnswerLabel.setText(
                        "Комп'ютер: " + result.computerCity()
                );
                updateScore();
                clearInput();
                break;

            case INVALID_CITY:
            case ALREADY_USED:
            case WRONG_LETTER:
                showError(result.message());
                break;

            case PLAYER_WON:
            case COMPUTER_WON:
                updateScore();
                showGameOver(result.message());
                break;

            case GAME_OVER:
                showError(result.message());
                break;
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Помилка",
                JOptionPane.WARNING_MESSAGE
        );

        cityInput.requestFocusInWindow();
    }

    private void clearInput() {
        cityInput.setText("");
        cityInput.requestFocusInWindow();
        cityInput.setCaretPosition(0);
    }

    private void updateScore() {
        scoreLabel.setText(
                "Рахунок: " + game.getPlayerScore()
                        + " : " + game.getComputerScore()
        );
    }

    private void showGameOver(String message) {
        String fullMessage = message
                + "\n\nВаш рахунок: " + game.getPlayerScore()
                + "\nРахунок комп'ютера: " + game.getComputerScore()
                + "\n\nБажаєте зіграти ще раз?";

        int choice = JOptionPane.showOptionDialog(
                this,
                fullMessage,
                "Гра завершена",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new Object[]{"Нова гра", "Вийти"},
                "Нова гра"
        );

        dispose();

        if (choice == JOptionPane.YES_OPTION) {
            newGameAction.run();
        }
    }

    private Image loadIcon() {
        var iconUrl = getClass().getResource("/cities-icon.png");

        if (iconUrl == null) {
            return null;
        }

        ImageIcon icon = new ImageIcon(iconUrl);

        return icon.getImage().getScaledInstance(
                32,
                32,
                Image.SCALE_SMOOTH
        );
    }
}
