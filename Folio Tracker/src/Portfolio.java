import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Portfolio implements PortfolioADT, Serializable {
	
	private String name;
	private List<StockADT> stocks;

	public Portfolio() {}

	public Portfolio(String name) {
		this.name = name;
		stocks = new ArrayList<>();
	}
	
	public String getName() {
		return name;
	}
	
	public void addStock(StockADT st) throws DuplicateDataException {
		if (stocks.stream().anyMatch(x -> x.getSymbol().equals(st.getSymbol()))) {
			throw new DuplicateDataException();
		}
		stocks.add(st);
	}
	
	public StockADT getStock(String symbol) throws NoSuchStockException {

		StockADT st = stocks.stream().filter(x -> x.getSymbol() == symbol).findFirst().orElse(null);
		if (st == null)
			throw new NoSuchStockException();
		return st;
	}
	
	public List<StockADT> getStocks() {
		return stocks;
	}
	
	public void delStock(StockADT st) throws NoSuchStockException {
		StockADT stock = stocks.stream().filter(x -> x.getSymbol() == st.getSymbol()).findFirst().orElse(null);
		if(stock == null)
			throw new NoSuchStockException();
		else
			stocks.remove(stock);

	}

	public float getTotalChange() {
		float total = 0;
		for(StockADT s : stocks) {
			total += s.getChange();
		}
		return total;
	}
	
	public float getTotalValue() {
		float total = 0;
		for(StockADT element: stocks) {
			total += element.getValue();
		}
		return total;
	}
}