import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FolioTrackerADTTest {

    FolioTrackerADT folios;
    PortfolioADT folio1;
    PortfolioADT folio2;
    StockADT stock1;
    StockADT stock2;
    StockADT stock3;
    StockADT stock4;

    @BeforeEach
    void setUp() {
        folios = new FolioTracker();

        folio1 = new Portfolio("Test1");
        stock1 = new Stock("TEST1", "Test1", 10, 100);
        stock2 = new Stock("TEST2", "Test2", 20, 200);

        folio2 = new Portfolio("Test2");
        stock3 = new Stock("TEST3", "Test3", 10, 100);
        stock4 = new Stock("TEST4", "Test4", 20, 200);

        try {
            folio1.addStock(stock1);
            folio1.addStock(stock2);
            folios.addFolio(folio1);

            folio2.addStock(stock3);
            folio2.addStock(stock4);
            folios.addFolio(folio2);
        } catch (DuplicateDataException e) {
            System.out.println(folios.getFolios());
        }
    }

    @Test
    void addFolio() {
        PortfolioADT folio3 = new Portfolio("Test3");

        String message;
        try {
            folios.addFolio(folio2);
            message = "Passed";
        } catch (DuplicateDataException e) {
            message = "Failed";
        }
        assertEquals("Failed", message);

        try {
            folios.addFolio(folio3);
            message = "Passed";
        } catch (DuplicateDataException e) {
            message = "Failed";
        }
        assertEquals("Passed", message);
        assertTrue(folios.getFolios().contains(folio3));
    }

    @Test
    void getFolios() {
        assertTrue(folios.getFolios().contains(folio1));
        assertTrue(folios.getFolios().contains(folio2));
    }

    @Test
    void delFolio() {

        PortfolioADT folio3 = new Portfolio("Test3");

        String message;
        try {
            folios.delFolio(folio3);
            message = "Passed";
        } catch (NoSuchFolioException e) {
            message = "Failed";
        }
        assertEquals("Failed", message);

        try {
            folios.delFolio(folio2);
            message = "Passed";
        } catch (NoSuchFolioException e) {
            message = "Failed";
        }
        assertEquals("Passed", message);

        try {
            folios.delFolio(folio2);
            message = "Passed";
        } catch (NoSuchFolioException e) {
            message = "Failed";
        }
        assertEquals("Failed", message);
        assertFalse(folios.getFolios().contains(folio2));
    }

    @Test
    void saveReadFolios() {

        folios.saveFolios();

        try {
            folios.delFolio(folio1);
            folios.delFolio(folio2);
        } catch (NoSuchFolioException ignored) {}
        assertFalse(folios.getFolios().contains(folio1));
        assertFalse(folios.getFolios().contains(folio1));
        System.out.println(folios.getFolios());

        folios.readFolios();

        boolean isFolio1 = false;
        boolean isFolio2 = false;
        for (int i = 0; i < folios.getFolios().size(); i++) {
            if(folios.getFolios().get(i).getName().equals(folio1.getName())) {
                isFolio1 = true;
                try {
                    folios.delFolio(folios.getFolios().get(i));
                } catch (NoSuchFolioException e) {}
            }
            if(folios.getFolios().get(i).getName().equals(folio2.getName())) {
                isFolio2 = true;
                try {
                    folios.delFolio(folios.getFolios().get(i));
                } catch (NoSuchFolioException e) {}
            }
        }
        assertTrue(isFolio1);
        assertTrue(isFolio2);
        folios.saveFolios();
    }

    @Test
    void getStockPrice() {
        float price = 0;
        String message = "";
        try {
            price = folios.getStockPrice("NOTVALID");
            message = "Passed";
        } catch (WebsiteDataException e) {
            System.out.println("Test failed due to connection problems.");
        } catch (NoSuchTickerException e) {
            message = "Failed";
        }
        assertEquals("Failed", message);
        assertEquals(0.0, price);

        try {
            price = folios.getStockPrice("AAPL");
            message = "Passed";
        } catch (WebsiteDataException e) {
            System.out.println("Test failed due to connection problems.");
        } catch (NoSuchTickerException e) {
            message = "Failed";
        }
        assertEquals("Passed", message);
        assertTrue(price > 0.0);
    }
}