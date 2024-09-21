package p02.game;

import p02.pres.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.EventObject;
import java.util.Random;

public class Board implements KeyListener, CustomEventListener {
    private GameThread gameThread;
    private boolean isRunning = false;
    private boolean collisionOccurred = false;
    private GameTableModel model;
    private SevenSegmentDigit units, tens, hundreds;
    private GamePanel gamePanel;
    private Random random = new Random();
    private Color carColor = Color.BLUE;

    public Board(GameTableModel model, SevenSegmentDigit units, SevenSegmentDigit tens, SevenSegmentDigit hundreds, GamePanel gamePanel) {
        this.model = model;
        this.units = units;
        this.tens = tens;
        this.hundreds = hundreds;
        this.gamePanel = gamePanel;

        units.addEventListener(tens);
        tens.addEventListener(hundreds);

        gameThread = GameThread.getInstance(this);
        reset();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_S:
                if (!isRunning) {
                    if (collisionOccurred) {
                        reset();
                        collisionOccurred = false;
                        isRunning = true;
                        new Thread(gameThread).start();
                    } else {
                        isRunning = true;
                        new Thread(gameThread).start();
                    }
                }
                break;
            case KeyEvent.VK_A:
                if (isRunning) {
                    moveCarLeft();
                    gamePanel.repaint();
                }
                break;
            case KeyEvent.VK_D:
                if (isRunning) {
                    moveCarRight();
                    gamePanel.repaint();
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    public void tick() {
        if (isRunning) {
            fireEvent(new TickEvent(this));
            if (checkCollision()) {
                System.out.println("Collision detected");
                carColor = Color.RED;
                gamePanel.repaint();
                isRunning = false;
                collisionOccurred = true;
                gameThread.stopRunning();
            } else {
                units.handleEvent(new PlusOneEvent(this));
                if (hundreds.getValue() == 9 && tens.getValue() == 9 && units.getValue() == 9) {
                    System.out.println("Player reached 999 points. Game over.");
                    isRunning = false;
                    gameThread.stopRunning();
                } else {
                    model.incrementTickCounter();
                    shiftDown();
                    gamePanel.repaint();
                }
            }
        }
    }

    public void reset() {
        model.reset();
        isRunning = false;
        gameThread.stopRunning();
        gameThread.resetInterval();
        int[] data = model.getData();
        for (int i = 0; i < data.length; i++) {
            data[i] = 0;
        }
        data[0] = 0b010;
        model.setData(data);
        carColor = Color.BLUE;
        gamePanel.repaint();
        fireEvent(new ResetEvent(this));
    }

    @Override
    public void handleEvent(EventObject event) {
        if (event instanceof ResetEvent) {
            reset();
        } else if (event instanceof TickEvent) {
            shiftDown();
        }
    }

    public void fireEvent(EventObject event) {
        units.handleEvent(event);
        tens.handleEvent(event);
        hundreds.handleEvent(event);
    }

    public Color getCarColor() {
        return carColor;
    }

    private void moveCarLeft() {
        int carPosition = getCarPosition();
        if (carPosition > 0) {
            carPosition--;
            updateCarPosition(carPosition);
            gamePanel.repaint();
        }
    }

    private void moveCarRight() {
        int carPosition = getCarPosition();
        if (carPosition < 2) {
            carPosition++;
            updateCarPosition(carPosition);
            gamePanel.repaint();
        }
    }

    private void shiftDown() {
        int[] data = model.getData();

        for (int i = 1; i < data.length - 1; i++) {
            data[i] = data[i + 1];
        }

        int emptyRows = 0;
        if (hundreds.getValue() == 0) emptyRows++;
        if (tens.getValue() == 0) emptyRows++;
        if (units.getValue() == 0) emptyRows++;

        if (model.getTickCounter() % (emptyRows + 1) == 0) {
            data[data.length - 1] = generateRow();
        } else {
            data[data.length - 1] = 0;
        }

        model.setData(data);
    }


    private int generateRow() {
        int newRow;
        do {
            newRow = random.nextInt(8);
        } while (!isRowValid(newRow));
        return newRow;
    }

    private boolean isRowValid(int newRow) {
        int[] data = model.getData();
        if (Integer.bitCount(newRow) > 2) {
            return false;
        }
        if ((newRow & data[data.length - 2]) != 0) {
            return false;
        }
        return true;
    }

    private boolean checkCollision() {
        int[] data = model.getData();
        boolean collision = (data[0] & data[1]) != 0;
        return collision;
    }

    private int getCarPosition() {
        int[] data = model.getData();
        for (int i = 0; i < 3; i++) {
            if ((data[0] & (1 << i)) != 0) {
                return i;
            }
        }
        return -1;
    }

    private void updateCarPosition(int carPosition) {
        int[] data = model.getData();
        data[0] = 1 << carPosition;
        model.setData(data);
    }
}