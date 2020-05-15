import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class StockTableModel extends AbstractTableModel {

    private String[] columnNames = {
            "Ticker Symbol",
            "Stock Name",
            "Shares",
            "Price per Share",
            "Max Price to Date",
            "Min Price to Date",
            "Current Trend",
            "Gains/Losses",
            "Total Value"
    };

    private List<StockADT> data = new ArrayList<>();

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        Object value = getValueAt(0, columnIndex);
        return value.getClass();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        StockADT stock = data.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return stock.getSymbol();
            case 1:
                return stock.getName();
            case 2:
                return stock.getShares();
            case 3:
                return stock.getPrice();
            case 4:
                return stock.getMax();
            case 5:
                return stock.getMin();
            case 6:
                return stock.getTrend();
            case 7:
                return stock.getChange();
            case 8:
                return stock.getValue();
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    }

    public void updateStocks(List<StockADT> stockList) {
        data = stockList;
        fireTableDataChanged();
    }

    public StockADT getStock(int row) {
        return data.get(row);
    }
}
