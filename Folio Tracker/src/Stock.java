import java.io.Serializable;

public class Stock implements StockADT, Serializable {
	
	private String symbol;
	private String name;
	private int shares;
	private float price;
	private float minPrice;
	private float maxPrice;
	private float initialPrice;
	private float earnings;
	private float percentageChange;

	public Stock(String symbol, String name, int shares, float price) {
		this.symbol = symbol;
		this.name = name;
		this.shares = shares;
		this.price = price;
		minPrice = price;
		maxPrice = price;
		initialPrice = price;
		earnings = 0;
		percentageChange = 0;
	}

	public String getSymbol() {
		return symbol;
	}

	public String getName() {
		return name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float updatedPrice) {

		percentageChange = ( (updatedPrice - price) / price ) * 100;
		price = updatedPrice;

		if(price < minPrice){
			minPrice = price;
		}
		if(price > maxPrice){
			maxPrice = price;
		}
	}
	
	public int getShares() {
		return shares;
	}

	public void setShares(int updatedShares) {

		earnings = earnings + (getValue() - shares * initialPrice);
		initialPrice = price;
		shares = updatedShares;
	}

	public float getValue() {
		return price * shares;
	}

	public String getTrend() {
		String trend =  String.format("%.3f", percentageChange) + "%";
		if (percentageChange < 0)
				trend = " ↓ " + trend;
		else
			trend = " ↑ " + trend;
		return trend;
	}
	public float getChange() {
		return earnings + (getValue() - initialPrice * shares);
	}

	public float getMin() {
		return minPrice;
	}

	public float getMax() {
		return maxPrice;
	}
}
