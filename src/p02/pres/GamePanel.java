package p02.pres;

import p02.game.Board;

import javax.swing.JPanel;
import javax.swing.JTable;
import java.awt.*;

public class GamePanel extends JPanel {
    private JTable table;
    private GameTableModel model;
    private Board board;

    public GamePanel(JTable table, GameTableModel model) {
        this.table = table;
        this.model = model;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int cellSize = 50;
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int tableWidth = table.getColumnCount() * cellSize;
        int tableHeight = table.getRowCount() * cellSize;
        int xOffset = (panelWidth - tableWidth) / 2;
        int yOffset = (panelHeight - tableHeight) / 2;

        for (int i = 0; i < table.getRowCount(); i++) {
            for (int j = 0; j < table.getColumnCount(); j++) {
                int value = (int) table.getValueAt(i, j);
                int yPosition = panelHeight - yOffset - (i + 1) * cellSize;

                if (i == 0 && j == model.getCarPosition()) {
                    g.setColor(board != null ? board.getCarColor() : Color.BLUE);
                } else if (value == 1) {
                    g.setColor(Color.BLACK);
                } else {
                    g.setColor(Color.LIGHT_GRAY);
                }
                g.fillRect(xOffset + j * cellSize, yPosition, cellSize, cellSize);
            }
        }

        for (int i = 0; i < table.getRowCount(); i++) {
            int yPosition = panelHeight - yOffset - (i + 1) * cellSize;
            boolean isEdgeGenerated = ((model.getTickCounter() + (i)) % 3 == 0);
            if (isEdgeGenerated) {
                g.setColor(Color.DARK_GRAY);
            } else {
                g.setColor(Color.GRAY);
            }
            g.fillRect(xOffset - cellSize, yPosition, cellSize, cellSize);
            g.fillRect(xOffset + tableWidth, yPosition, cellSize, cellSize);
        }
    }
}