import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PortfolioADTTest {

    private PortfolioADT folio;
    private StockADT stock1;
    private StockADT stock2;

    @BeforeEach
    void setUp() {
        folio = new Portfolio("Test");
        stock1 = new Stock("TEST1", "Test1", 10, 100);
        stock2 = new Stock("TEST2", "Test2", 20, 200);
        try {
            folio.addStock(stock1);
            folio.addStock(stock2);
        } catch (DuplicateDataException ignored) {}
    }

    @Test
    void getName() {
        assertEquals("Test", folio.getName());
    }

    @Test
    void addStock() {
        StockADT stock3 = new Stock("TEST3", "Test3", 30, 300);

        String message;
        try {
            folio.addStock(stock2);
            message = "Passed";
        } catch (DuplicateDataException e) {
            message = "Failed";
        }
        assertEquals("Failed", message);

        try {
            folio.addStock(stock3);
            message = "Passed";
        } catch (DuplicateDataException e) {
            message = "Failed";
        }
        assertEquals("Passed", message);
    }

    @Test
    void getStock() {
        StockADT stock3 = new Stock("TEST3", "Test3", 30, 300);

        String message;
        try {
            folio.getStock(stock2.getSymbol());
            message = "Passed";
        } catch (NoSuchStockException e) {
            message = "Failed";
        }
        assertEquals("Passed", message);

        try {
            folio.getStock(stock3.getSymbol());
            message = "Passed";
        } catch (NoSuchStockException e) {
            message = "Failed";
        }
        assertEquals("Failed", message);
    }

    @Test
    void getStocks() {
        List stocks = new ArrayList<PortfolioADT>();
        stocks.add(stock1);
        stocks.add(stock2);

        assertEquals(stocks, folio.getStocks());
    }

    @Test
    void delStock() {
        StockADT stock3 = new Stock("TEST3", "Test3", 30, 300);

        String message;
        try {
            folio.delStock(stock3);
            message = "Passed";
        } catch (NoSuchStockException e) {
            message = "Failed";
        }
        assertEquals("Failed", message);

        try {
            folio.delStock(stock2);
            message = "Passed";
        } catch (NoSuchStockException e) {
            message = "Failed";
        }
        assertEquals("Passed", message);

        try {
            folio.delStock(stock2);
            message = "Passed";
        } catch (NoSuchStockException e) {
            message = "Failed";
        }
        assertEquals("Failed", message);
    }

    @Test
    void getTotalChange() {
        assertEquals(0.0, folio.getTotalChange());
        stock1.setPrice(200);
        stock2.setPrice(175);
        assertEquals(500.0, folio.getTotalChange());
    }

    @Test
    void getTotalValue() {
        StockADT stock3 = new Stock("TEST3", "Test3", 30, 300);

        assertEquals(5000, folio.getTotalValue());

        try {
            folio.addStock(stock3);
        } catch (DuplicateDataException ignored) {}

        assertEquals(14000, folio.getTotalValue());

        try {
            folio.delStock(stock2);
        } catch (NoSuchStockException ignored) {}

        assertEquals(10000, folio.getTotalValue());
    }
}