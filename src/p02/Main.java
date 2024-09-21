package p02;

import p02.game.Board;
import p02.pres.GamePanel;
import p02.pres.GameTableModel;
import p02.pres.SevenSegmentDigit;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Autoslalom");
        GameTableModel model = new GameTableModel();
        JTable table = new JTable(model);
        GamePanel gamePanel = new GamePanel(table, model);

        SevenSegmentDigit units = new SevenSegmentDigit();
        SevenSegmentDigit tens = new SevenSegmentDigit();
        SevenSegmentDigit hundreds = new SevenSegmentDigit();

        Board board = new Board(model, units, tens, hundreds, gamePanel);
        gamePanel.setBoard(board);

        units.addEventListener(tens);
        tens.addEventListener(hundreds);

        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel autoslalomLabel = new JLabel("Автослалом");
        autoslalomLabel.setFont(new Font("SansSerif", Font.BOLD, 48));
        autoslalomLabel.setForeground(Color.RED);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0);
        frame.add(autoslalomLabel, gbc);

        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new GridLayout(1, 3));
        scorePanel.add(hundreds);
        scorePanel.add(tens);
        scorePanel.add(units);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 2;
        gbc.insets = new Insets(0, 5, 0, 5);
        frame.add(scorePanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        frame.add(gamePanel, gbc);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 800);
        frame.addKeyListener(board);
        frame.setFocusable(true);
        frame.requestFocusInWindow();
        frame.setVisible(true);
    }
}