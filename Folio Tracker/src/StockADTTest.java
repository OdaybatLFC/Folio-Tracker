import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StockADTTest {

    private StockADT stock;

    @BeforeEach
    void setUp() {
        stock = new Stock("TEST", "Test", 10, 100);
    }

    @Test
    void getName() {
        assertEquals("Test", stock.getName());
    }

    @Test
    void getSymbol() {
        assertEquals("TEST", stock.getSymbol());
    }

    @Test
    void getPrice() {
        assertEquals(100, stock.getPrice());
    }

    @Test
    void setPrice() {
        stock.setPrice(150);
        assertEquals(150, stock.getPrice());
    }

    @Test
    void getShares() {
        assertEquals(10, stock.getShares());
    }

    @Test
    void setShares() {
        stock.setShares(5);
        assertEquals(5, stock.getShares());
    }

    @Test
    void getValue() {
        assertEquals(1000, stock.getValue());
    }

    @Test
    void getTrend() {
        assertEquals(" ↑ 0.000%", stock.getTrend());
        stock.setPrice(125);
        assertEquals(" ↑ 25.000%", stock.getTrend());
        stock.setPrice((float) 62.5);
        assertEquals(" ↓ -50.000%", stock.getTrend());
    }


    @Test
    void getChange() {
        stock.setPrice(150);
        assertEquals(500, stock.getChange());
        stock.setShares(5);
        assertEquals(500, stock.getChange());
        stock.setPrice(100);
        assertEquals(250, stock.getChange());
    }

    @Test
    void getMin() {
        assertEquals(100, stock.getMin());
        stock.setPrice(50);
        assertEquals(50, stock.getMin());
    }

    @Test
    void getMax() {
        assertEquals(100, stock.getMax());
        stock.setPrice(150);
        assertEquals(150, stock.getMax());
    }
}