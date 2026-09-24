package com.example.cities.ui;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

public class WelcomeWindow extends JFrame {

    private final GameWindow gameWindow;

    public WelcomeWindow(GameWindow gameWindow) {
        this.gameWindow = gameWindow;

        setTitle("Міста");
        setIconImage(loadIcon());
        setSize(400, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JLabel welcomeLabel = new JLabel(
                "Вітаємо вас у грі дитинства!",
                JLabel.CENTER
        );
        welcomeLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JButton startButton = new JButton("Старт");
        startButton.setPreferredSize(new Dimension(100, 30));

        startButton.addActionListener(event -> {
            dispose();
            gameWindow.setVisible(true);
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(
                BorderFactory.createEmptyBorder(0, 0, 5, 0)
        );
        buttonPanel.add(startButton);

        add(welcomeLabel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
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
