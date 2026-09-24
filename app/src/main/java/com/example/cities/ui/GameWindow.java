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
    private final JLabel computerAnswerLabel;
    private final JLabel scoreLabel;
    private final JTextField cityInput;

    public GameWindow(CityGame game) {
        this.game = game;

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

        switch (result.getStatus()) {
            case VALID:
                computerAnswerLabel.setText(
                        "Комп'ютер: " + result.getComputerCity()
                );
                updateScore();
                clearInput();
                break;

            case INVALID_CITY:
            case ALREADY_USED:
            case WRONG_LETTER:
                showError(result.getMessage());
                break;

            case PLAYER_WON:
            case COMPUTER_WON:
                updateScore();
                showGameOver(result.getMessage());
                break;

            case GAME_OVER:
                showError(result.getMessage());
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
                + "\n\nДякуємо за гру!";

        JOptionPane.showMessageDialog(
                this,
                fullMessage,
                "Гра завершена",
                JOptionPane.INFORMATION_MESSAGE
        );

        dispose();
    }

    private Image loadIcon() {
        ImageIcon icon = new ImageIcon(
                getClass().getResource("/cities-icon.png")
        );

        return icon.getImage().getScaledInstance(
                32,
                32,
                Image.SCALE_SMOOTH
        );
    }
}
