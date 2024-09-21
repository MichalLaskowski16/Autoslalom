package p02.pres;

import javax.swing.table.AbstractTableModel;

public class GameTableModel extends AbstractTableModel {
    private int[] data = new int[7];
    private int tickCounter = 0;

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return (data[rowIndex] & (1 << columnIndex)) != 0 ? 1 : 0;
    }

    public int[] getData() {
        return data;
    }

    public void setData(int[] data) {
        this.data = data;
        fireTableDataChanged();
    }

    public void reset() {
        for (int i = 0; i < data.length; i++) {
            data[i] = 0;
        }
        tickCounter = 0;
        fireTableDataChanged();
    }

    public void incrementTickCounter() {
        tickCounter++;
    }

    public int getTickCounter() {
        return tickCounter;
    }

    public int getCarPosition() {
        for (int i = 0; i < 3; i++) {
            if ((data[0] & (1 << i)) != 0) {
                return i;
            }
        }
        return -1;
    }
}