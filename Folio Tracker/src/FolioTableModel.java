import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class FolioTableModel extends AbstractTableModel {
    private String[] columnNames = {
            "Folio Name",
            "No. of Stocks",
            "Total Loss/Gain",
            "Total Value"
    };
    private List<PortfolioADT> data = new ArrayList<>();

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
        PortfolioADT folio = data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return folio.getName();
            case 1:
                return folio.getStocks().size();
            case 2:
                return folio.getTotalChange();
            case 3:
                return folio.getTotalValue();
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    }

    public void updateFolios(List<PortfolioADT> folioList) {
        data = folioList;
        fireTableDataChanged();
    }

    public PortfolioADT getFolio(int row) {
        return data.get(row);
    }
}
