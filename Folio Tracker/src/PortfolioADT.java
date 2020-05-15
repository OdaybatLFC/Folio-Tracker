import java.util.List;

public interface PortfolioADT {
	
	String getName();
	void addStock(StockADT st) throws DuplicateDataException;
	StockADT getStock(String symbol) throws NoSuchStockException;
	List<StockADT> getStocks();
	void delStock(StockADT st) throws NoSuchStockException;
	float getTotalChange();
	float getTotalValue();
}
