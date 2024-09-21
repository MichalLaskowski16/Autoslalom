package p02.pres;

import p02.game.PlusOneEvent;
import p02.game.ResetEvent;
import p02.game.StartEvent;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.util.EventListener;
import java.util.EventObject;
import java.awt.Polygon;

public class SevenSegmentDigit extends JPanel implements EventListener {
    private int value = 0;
    private EventListener listener;
    private static final int MARGIN_TOP = 20;
    private static final int MARGIN_BOTTOM = 20;

    public SevenSegmentDigit() {
        setPreferredSize(new Dimension(50, 170));
    }

    public void setValue(int value) {
        this.value = value;
        repaint();
    }

    public int getValue() {
        return value;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.RED);

        boolean[] segments = getSegments(value);
        int w = getWidth() / 6;
        int h = (getHeight() - MARGIN_TOP - MARGIN_BOTTOM) / 12;
        int[] xPoints, yPoints;

        if (segments[0]) {
            xPoints = new int[]{w, getWidth() - w, getWidth() - 2 * w, w * 2, w};
            yPoints = new int[]{MARGIN_TOP, MARGIN_TOP, MARGIN_TOP + h, MARGIN_TOP + h, MARGIN_TOP};
            g.fillPolygon(new Polygon(xPoints, yPoints, 5));
        }
        if (segments[3]) {
            xPoints = new int[]{w, getWidth() - w, getWidth() - 2 * w, w * 2, w};
            yPoints = new int[]{MARGIN_TOP + 7 * h, MARGIN_TOP + 7 * h, MARGIN_TOP + 8 * h, MARGIN_TOP + 8 * h, MARGIN_TOP + 7 * h};
            g.fillPolygon(new Polygon(xPoints, yPoints, 5));
            yPoints = new int[]{MARGIN_TOP + 7 * h, MARGIN_TOP + 7 * h, MARGIN_TOP + 6 * h, MARGIN_TOP + 6 * h, MARGIN_TOP + 7 * h};
            g.fillPolygon(new Polygon(xPoints, yPoints, 5));
        }
        if (segments[6]) {
            xPoints = new int[]{w, getWidth() - w, getWidth() - 2 * w, w * 2, w};
            yPoints = new int[]{MARGIN_TOP + 14 * h, MARGIN_TOP + 14 * h, MARGIN_TOP + 13 * h, MARGIN_TOP + 13 * h, MARGIN_TOP + 14 * h};
            g.fillPolygon(new Polygon(xPoints, yPoints, 5));
        }

        if (segments[1]) {
            xPoints = new int[]{getWidth() - w, getWidth() - w, getWidth() - 2 * w, getWidth() - 2 * w};
            yPoints = new int[]{MARGIN_TOP + h, MARGIN_TOP + 6 * h, MARGIN_TOP + 5 * h, MARGIN_TOP + 2 * h};
            g.fillPolygon(new Polygon(xPoints, yPoints, 4));
        }
        if (segments[2]) {
            xPoints = new int[]{getWidth() - w, getWidth() - w, getWidth() - 2 * w, getWidth() - 2 * w};
            yPoints = new int[]{MARGIN_TOP + 8 * h, MARGIN_TOP + 13 * h, MARGIN_TOP + 12 * h, MARGIN_TOP + 9 * h};
            g.fillPolygon(new Polygon(xPoints, yPoints, 4));
        }
        if (segments[4]) {
            xPoints = new int[]{w, w, 2 * w, 2 * w};
            yPoints = new int[]{MARGIN_TOP + 8 * h, MARGIN_TOP + 13 * h, MARGIN_TOP + 12 * h, MARGIN_TOP + 9 * h};
            g.fillPolygon(new Polygon(xPoints, yPoints, 4));
        }
        if (segments[5]) {
            xPoints = new int[]{w, w, 2 * w, 2 * w};
            yPoints = new int[]{MARGIN_TOP + h, MARGIN_TOP + 6 * h, MARGIN_TOP + 5 * h, MARGIN_TOP + 2 * h};
            g.fillPolygon(new Polygon(xPoints, yPoints, 4));
        }
    }

    private boolean[] getSegments(int digit) {
        switch (digit) {
            case 0:
                return new boolean[]{true, true, true, false, true, true, true};
            case 1:
                return new boolean[]{false, true, true, false, false, false, false};
            case 2:
                return new boolean[]{true, true, false, true, true, false, true};
            case 3:
                return new boolean[]{true, true, true, true, false, false, true};
            case 4:
                return new boolean[]{false, true, true, true, false, true, false};
            case 5:
                return new boolean[]{true, false, true, true, false, true, true};
            case 6:
                return new boolean[]{true, false, true, true, true, true, true};
            case 7:
                return new boolean[]{true, true, true, false, false, false, false};
            case 8:
                return new boolean[]{true, true, true, true, true, true, true};
            case 9:
                return new boolean[]{true, true, true, true, false, true, true};
            default:
                return new boolean[]{false, false, false, false, false, false, false};
        }
    }

    public void handleEvent(EventObject event) {
        if (event instanceof StartEvent) {
            setValue(0);
        } else if (event instanceof PlusOneEvent) {
            increment();
        } else if (event instanceof ResetEvent) {
            setValue(0);
        }
    }

    private void increment() {
        if (value < 9) {
            setValue(value + 1);
        } else {
            setValue(0);
            if (listener != null && listener instanceof SevenSegmentDigit) {
                ((SevenSegmentDigit) listener).increment();
            }
        }
    }

    public void addEventListener(EventListener listener) {
        this.listener = listener;
    }
}