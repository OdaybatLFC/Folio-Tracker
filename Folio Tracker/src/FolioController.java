import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public class FolioController {

    private FolioView folioView;
    private FolioTrackerADT folioTracker;

    private StockADT popupStock;
    private PortfolioADT popupFolio;

    public FolioController() {

        folioView = new FolioView();

        folioTracker = new FolioTracker();

        updateFolios();

        folioView.refresh.addActionListener(e -> {
            refreshStocks();
            folioView.refreshTimer.restart();
        });

        folioView.refreshTimer = new Timer(300000, e -> refreshStocks());
        folioView.refreshTimer.start();

        folioView.saveFolios.addActionListener(e -> {
            folioTracker.saveFolios();
        });

        folioView.addFolioButton.addActionListener(e -> {
            folioView.addFolioModal.showModal();
        });

        //Adding new folios
        folioView.addFolioModal.addBtn.addActionListener(e -> {
            addFolio();
        });

        folioView.folioTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = folioView.folioTable.rowAtPoint(e.getPoint());
                if (SwingUtilities.isRightMouseButton(e)) {
                    popupFolio = folioView.folioTableModel.getFolio(row);
                    folioView.folioPopupMenu.show(folioView.mainView, e.getX(), e.getY());
                }
            }
        });

        folioView.deleteFolio.addActionListener(e -> {
            delFolio();
        });

        folioView.addStockBtn.addActionListener(e -> {
            folioView.addStockModal.showModal();
        });

        folioView.addStockModal.addBtn.addActionListener(e -> {
            addStock();
        });

        folioView.stocksTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = folioView.stocksTable.rowAtPoint(e.getPoint());
                if (SwingUtilities.isRightMouseButton(e)) {
                    popupStock = folioView.stockTableModel.getStock(row);
                    folioView.stockPopupMenu.show(folioView.folioView, e.getX(), e.getY());
                }
            }
        });

        folioView.editStock.addActionListener(e -> {
            folioView.editStockModal.updateStock(popupStock);
            folioView.editStockModal.showModal();
        } );

        folioView.editStockModal.saveBtn.addActionListener(x -> {
            editStock();
        });

        folioView.deleteStock.addActionListener(e -> {
            delStock();
        });

        folioView.backBtn.addActionListener(e -> folioView.showMainView(getFolios()));

        folioView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                folioTracker.saveFolios();
                super.windowClosing(e);
            }
        });

        folioView.repaint();
        folioView.revalidate();
    }

    private List<PortfolioADT> getFolios() {
        return folioTracker.getFolios();
    }

    private void updateFolios() {
        List<PortfolioADT> folios = getFolios();
        folioView.folioTableModel.updateFolios(folios);
        folioView.repaint();
        folioView.revalidate();
    }

    private void addFolio() {
        String folioName = folioView.addFolioModal.nameInput.getText();
        String message;

        if (folioName.equals("")) {
            message = "Please give the folio a name.";
            folioView.addFolioModal.updateErrorMsg(message);
            return;
        }

        PortfolioADT newFolio = new Portfolio(folioName);
        try {
            folioTracker.addFolio(newFolio);
        } catch (DuplicateDataException e) {
            message = "A folio with this name already exists.";
            folioView.addFolioModal.updateErrorMsg(message);
            return;
        }

        updateFolios();
        folioView.addFolioModal.setVisible(false);
        folioView.addFolioModal.resetForm();
    }

    private void delFolio() {
        int confirm = folioView.confirmDialog("Are you sure you want to delete this folio and sell all stocks within it?");
        if (confirm == 0) {
            try {
                folioTracker.delFolio(popupFolio);
            } catch (NoSuchFolioException e) {
                folioView.errorDialog("Error: Selected folio not found for deletion.");
            }
            updateFolios();
        }
    }

    private void updateStocks() {
        folioView.stockTableModel.updateStocks(folioView.getCurrentFolio().getStocks());
        folioView.repaint();
        folioView.revalidate();
    }

    private void addStock() {
        String name = folioView.addStockModal.nameInput.getText();
        String symbol = folioView.addStockModal.symbolInput.getText();
        String amount = folioView.addStockModal.sharesInput.getText();
        float price;
        String message;
        int shares = 0;

        if (name.equals("")) {
            message = "Please give the stock a name.";
            folioView.addStockModal.updateErrorMsg(message);
            return;
        }

        try {
            price = folioTracker.getStockPrice(symbol);
        } catch (NoSuchTickerException e) {
            message = "Please write a valid ticker symbol.";
            folioView.addStockModal.updateErrorMsg(message);
            return;
        } catch (WebsiteDataException e) {
            folioView.addStockModal.setVisible(false);
            folioView.addStockModal.resetForm();
            message = "Could not connect to the server. \nPlease check your connection and try again.";
            folioView.errorDialog(message);
            return;
        }

        try {
            shares = Integer.parseInt(amount);
        }
        catch (NumberFormatException error) {
            message = "Please write a valid whole number for shares.";
            folioView.addStockModal.updateErrorMsg(message);
            return;
        }

        if (shares < 1) {
            message = "Please write a valid positive number for shares.";
            folioView.addStockModal.updateErrorMsg(message);
            return;
        }

        Stock newStock = new Stock(symbol, name, shares, price);

        try {
            folioView.getCurrentFolio().addStock(newStock);
        } catch (DuplicateDataException e) {
            folioView.addStockModal.updateErrorMsg("<html>A stock with this symbol already exists. <br>Edit it to buy more.");
            return;
        }

        updateStocks();
        folioView.addStockModal.setVisible(false);
        folioView.addStockModal.resetForm();
    }

    private void editStock() {

        String amount = folioView.editStockModal.sharesInput.getText();

        int shares = 0;
        String message;

        try {
            shares = Integer.parseInt(amount);
        }
        catch (NumberFormatException error) {
            message = "Please write a valid whole number for shares.";
            folioView.editStockModal.updateErrorMsg(message);
            return;
        }

        if (shares < 0) {
            message = "Please write a valid positive number for shares.";
            folioView.editStockModal.updateErrorMsg(message);
            return;
        }

        if (shares == 0) {
            folioView.editStockModal.setVisible(false);
            folioView.editStockModal.resetForm();
            delStock();
            return;
        }

        try {
            folioView.getCurrentFolio().getStock(folioView.editStockModal.stock.getSymbol()).setShares(shares);
        } catch (Exception ignored) {}

        updateStocks();
        folioView.editStockModal.setVisible(false);
        folioView.editStockModal.resetForm();
    }

    private void delStock() {
        int confirm = folioView.confirmDialog("Are you sure you want to sell all your "+ popupStock.getSymbol() +" stocks?");
        if (confirm == 0) {
            try {
                folioView.getCurrentFolio().delStock(popupStock);
            } catch (NoSuchStockException e) {
                folioView.errorDialog("Error: Selected stock not found for deletion.");
            }
            updateStocks();
        }
    }

    private void refreshStocks() {
        SwingWorker<Boolean, Void> refreshWorker = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() {

                try {
                    folioTracker.refresh();
                } catch (NoSuchTickerException e) {
                    folioView.errorDialog("Error: Ticker symbol not found during refresh.");
                } catch (WebsiteDataException e) {
                    folioView.errorDialog("Could not connect to the server during refresh. \nPlease check your connection and try again.");
                }
                return null;
            }

            @Override
            protected void done() {
                folioView.repaint();
                folioView.revalidate();
            }
        };
        refreshWorker.execute();
    }
}
